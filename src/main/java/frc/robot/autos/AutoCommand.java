package frc.robot.autos;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.google.gson.reflect.TypeToken;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryUtil;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.SwerveControllerCommand;
import frc.robot.RobotContainer;
import frc.robot.Constants.AutoConstants;
import frc.robot.Constants.DriveConstants;
import frc.robot.subsystems.DrivetrainSubsystem;

public class AutoCommand extends CommandBase {
    private final Timer timer = new Timer();
    
    DrivetrainSubsystem drivetrain;
    Trajectory trajectory;

    Translation2d lastPosition;

    Trajectory.State lastState;

    Queue<Waypoint> waypoints;
    Queue<Marker> markers;

    RobotContainer container;

    public AutoCommand(RobotContainer container) {
        this.drivetrain = container.getDrivetrainSubsystem();
        this.container = container;

        Path path = Filesystem.getDeployDirectory().toPath().resolve("pathplanner/generatedJSON/New Path.wpilib.json");
        try {
            this.trajectory = TrajectoryUtil.fromPathweaverJson(path);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            this.trajectory = new Trajectory(); // create empty trajectory
        }

        lastPosition = new Translation2d(0, 0);

        addRequirements(drivetrain);
    }

    @Override
    public void initialize() {
        drivetrain.resetOdometry(trajectory.getInitialPose());
        drivetrain.getGyro().reset();
        drivetrain.setBrakeMode();

        String json = "";
        try {
            json = Files.readString(Filesystem.getDeployDirectory().toPath().resolve("pathplanner/New Path.path"), StandardCharsets.UTF_8);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // Convert JSON file into an ArrayList of type State
        // This creates an array list of all the waypoints, with
        // State representing the values within each waypoint.
        Gson gson = new GsonBuilder()
                        .excludeFieldsWithoutExposeAnnotation()
                        .create();

        Type type = new TypeToken<frc.robot.autos.Path>() {}.getType();
        frc.robot.autos.Path path = gson.fromJson(json, type);

        waypoints = new LinkedList<>(path.waypoints);
        markers = new LinkedList<>(path.markers);

        timer.restart();
    }

    @Override
    public void execute() {
        // run stop point commands sequentially
        // if(waypoint.isStopPoint) {
        //     drivetrain.drive(new Translation2d(0, 0), 0, false); // stop the drivetrain
        //     waypoint.isStopPoint = false;
        //     SequentialCommandGroup sequentialCommandGroup = new SequentialCommandGroup();
        //     StopEvent stopEvent = waypoint.stopEvent;
        //     for(String str : stopEvent.names) {
        //         sequentialCommandGroup.addCommands(container.getCommand(str));
        //     }
        //     sequentialCommandGroup.execute();
        // }

        var desiredState = trajectory.sample(timer.get()); // the next state, or, the state we are attempting to acheive 
        if(lastState == null) lastState = desiredState;

        Translation2d curPosition = new Translation2d(
            desiredState.poseMeters.getX(),
            desiredState.poseMeters.getY());

        Translation2d desiredTranslation = new Translation2d(
            (curPosition.getX() - lastPosition.getX()) / (desiredState.timeSeconds - lastState.timeSeconds),
            (curPosition.getY() - lastPosition.getY()) / (desiredState.timeSeconds - lastState.timeSeconds)
        );

        //double desiredRotation = 

        lastPosition = curPosition;
        lastState = desiredState;

        drivetrain.drive(desiredTranslation, desiredRotation, true);
    } 

    @Override
    public boolean isFinished() {
        return timer.hasElapsed(trajectory.getTotalTimeSeconds());
    }  
}
