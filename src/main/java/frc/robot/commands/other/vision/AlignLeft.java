package frc.robot.commands.other.vision;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.DrivetrainSubsystem;

public class AlignLeft extends CommandBase {
    DrivetrainSubsystem drivetrainSubsystem;

    PIDController pid;
    PIDController rotational;

    boolean rotationalFinished;

    SlewRateLimiter slr;

    public AlignLeft(RobotContainer robotContainer) {
        this.drivetrainSubsystem = robotContainer.getDrivetrainSubsystem();
    }

    @Override
    public void initialize() {
        pid = new PIDController(2, 0, 0);
        pid.setSetpoint(drivetrainSubsystem.getPose().getY() + 0.65);
        pid.setTolerance(.1);

        rotational = new PIDController(.05, 0.001, 0);
        rotational.setSetpoint(0.0);
        rotational.setTolerance(1.5);

        slr = new SlewRateLimiter(3.2);
        rotationalFinished = false;
    }
    
    @Override 
    public void execute() { 
        double velocity = pid.calculate(drivetrainSubsystem.getPose().getY());

        velocity = Math.copySign(MathUtil.clamp(Math.abs(velocity), 0.01, 2), velocity);

        double rotationalValue = rotational.calculate(drivetrainSubsystem.gyroscope.getAngle() - Math.round(drivetrainSubsystem.gyroscope.getAngle()/180)*180);
        rotationalValue = -Math.copySign(MathUtil.clamp(Math.abs(rotationalValue), 0.01, 2), rotationalValue);
        if(rotational.atSetpoint()) rotationalFinished = true;
        if(rotationalFinished) rotationalValue = 0;

        drivetrainSubsystem.drive(new Translation2d(0, slr.calculate(velocity)), 0, true);
    }

    @Override
    public void end(boolean interrupted) {
        drivetrainSubsystem.zeroDrive();
    }

    @Override
    public boolean isFinished() {
        return pid.atSetpoint();
    }
}
