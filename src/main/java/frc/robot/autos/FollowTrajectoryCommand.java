package frc.robot.autos;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.DrivetrainSubsystem;

public class FollowTrajectoryCommand extends CommandBase {


    Translation2d lastPosition;
    Trajectory.State lastState;
    Trajectory trajectory;

    DrivetrainSubsystem drivetrain;

    Timer timer;
    
    public FollowTrajectoryCommand(RobotContainer container, Trajectory trajectory) {
        this.trajectory = trajectory;
        this.drivetrain = container.getDrivetrainSubsystem();

        lastPosition = new Translation2d(0,0);

        timer = new Timer();
        timer.restart();
    }


    @Override
    public void execute() {
       // timer.start(); // if the timer is stopped, restart it
        var desiredState = trajectory.sample(timer.get()); // the next state, or, the state we are attempting to acheive 
        if(lastState == null) lastState = desiredState;

        Translation2d curPosition = new Translation2d(
            desiredState.getPose().getX(),
            desiredState.getPose().getY());

        Translation2d desiredTranslation = new Translation2d(
            (curPosition.getX() - lastPosition.getX()) / (desiredState.time - lastState.time),
            (curPosition.getY() - lastPosition.getY()) / (desiredState.time - lastState.time)
        );

        lastPosition = curPosition;
        lastState = desiredState;

        SmartDashboard.putNumber("TEST HDHH", desiredTranslation.getX());

        drivetrain.drive(desiredTranslation, 0, true);
    } 

    @Override
    public boolean isFinished() {
        return timer.hasElapsed(trajectory.trajectory.get(trajectory.trajectory.size()-1).time);
    }
}
