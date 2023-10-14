package frc.robot.commands.other;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.DrivetrainSubsystem;

public class AlignZero extends CommandBase {
    private DrivetrainSubsystem drivetrainSubsystem;

    private PIDController pid;


    public AlignZero(RobotContainer container) {
        this.drivetrainSubsystem = container.getDrivetrainSubsystem();
    }

    @Override
    public void initialize() {
        pid = new PIDController(.05, 0, 0.0005);
        pid.setSetpoint(0);
        pid.setTolerance(1);

    }

    @Override
    public void execute() {
        double velocity = pid.calculate(drivetrainSubsystem.gyroscope.getAngle() - Math.round(drivetrainSubsystem.gyroscope.getAngle()/180)*180);
        velocity = -Math.copySign(MathUtil.clamp(Math.abs(velocity), 0.01, 2), velocity);

        drivetrainSubsystem.drive(new Translation2d(0, 0), velocity, true);

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
