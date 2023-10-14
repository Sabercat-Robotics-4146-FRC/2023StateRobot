package frc.robot.autos;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.math.trajectory.Trajectory;
import frc.robot.RobotContainer;
import frc.robot.subsystems.DrivetrainSubsystem;

public class FollowWPILIBTrajectoryCommand extends CommandBase {
    Translation2d lastPosition;
    Trajectory.State lastState;
    Trajectory trajectory;

    DrivetrainSubsystem drivetrain;

    Timer timer;
    
    public FollowWPILIBTrajectoryCommand(RobotContainer container, Trajectory trajectory) {
        this.trajectory = trajectory;
        this.drivetrain = container.getDrivetrainSubsystem();

        lastPosition = new Translation2d(0,0);

        timer = new Timer();

        addRequirements(container.getDrivetrainSubsystem());
    }

    @Override
    public void initialize() {
        timer.restart();
    }

    @Override
    public void execute() {
        var desiredState = trajectory.sample(timer.get()); // the next state, or, the state we are attempting to acheive 
        if(lastState == null) lastState = desiredState;

        Translation2d curPosition = new Translation2d(
            desiredState.poseMeters.getX(),
            desiredState.poseMeters.getY());

        Translation2d desiredTranslation = new Translation2d(
            (curPosition.getX() - lastPosition.getX()) / (desiredState.timeSeconds - lastState.timeSeconds),
            (curPosition.getY() - lastPosition.getY()) / (desiredState.timeSeconds - lastState.timeSeconds)
        );

        lastPosition = curPosition;
        lastState = desiredState;

        drivetrain.drive(desiredTranslation, 0, true);
    } 

    @Override
    public boolean isFinished() {
        return timer.hasElapsed(trajectory.getTotalTimeSeconds());
    }
}
