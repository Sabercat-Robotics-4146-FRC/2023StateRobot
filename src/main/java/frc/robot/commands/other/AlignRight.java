package frc.robot.commands.other;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.DrivetrainSubsystem;

public class AlignRight extends CommandBase {
    DrivetrainSubsystem drivetrainSubsystem;

    PIDController pid;

    public AlignRight(RobotContainer robotContainer) {
        this.drivetrainSubsystem = robotContainer.getDrivetrainSubsystem();
    }

    @Override
    public void initialize() {
        pid = new PIDController(1, 0, 0);
        pid.setSetpoint(drivetrainSubsystem.getPose().getX() + 1);
    }
    
    @Override 
    public void execute() { 
        double velocity = pid.calculate(drivetrainSubsystem.getPose().getX());

        velocity = MathUtil.clamp(velocity, 0.51, 2);

        drivetrainSubsystem.drive(new Translation2d(velocity, 0), 0, true);
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
