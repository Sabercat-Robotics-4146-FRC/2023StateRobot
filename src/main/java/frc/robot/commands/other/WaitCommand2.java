package frc.robot.commands.other;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.DrivetrainSubsystem;

public class WaitCommand2 extends CommandBase {
    DrivetrainSubsystem drivetrain;

    double startRotation;

    public WaitCommand2(RobotContainer container) {
        drivetrain = container.getDrivetrainSubsystem(); 

        startRotation = drivetrain.getPose().getRotation().getDegrees();

        addRequirements(drivetrain);
    }

    @Override
    public void execute() {
        SmartDashboard.putBoolean("Test", true);
        drivetrain.drive(new Translation2d(0,0), 0.5, true);
    }

    @Override
    public void end(boolean interrupted) {
        // TODO Auto-generated method stub
        drivetrain.drive(new Translation2d(0,0), 0, true);
    }

    @Override
    public boolean isFinished() {
        return drivetrain.getPose().getRotation().getDegrees() - 90 > startRotation;
    }
}
