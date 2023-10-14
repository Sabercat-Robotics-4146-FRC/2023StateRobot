package frc.robot.commands.other;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.DrivetrainSubsystem;

public class AlignLeft extends CommandBase {
    DrivetrainSubsystem drivetrainSubsystem;

    PIDController pid;

    SlewRateLimiter slr;

    public AlignLeft(RobotContainer robotContainer) {
        this.drivetrainSubsystem = robotContainer.getDrivetrainSubsystem();
    }

    @Override
    public void initialize() {
        pid = new PIDController(2, 0, 0);
        pid.setSetpoint(drivetrainSubsystem.getPose().getY() - 0.65);
        pid.setTolerance(.1);

        slr = new SlewRateLimiter(3.2);
    }
    
    @Override 
    public void execute() { 
        double velocity = pid.calculate(drivetrainSubsystem.getPose().getY());

        velocity = Math.copySign(MathUtil.clamp(Math.abs(velocity), 0.01, 2), velocity);

        SmartDashboard.putNumber("TEST BLAH", velocity);

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
