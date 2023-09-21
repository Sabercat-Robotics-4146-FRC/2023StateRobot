package frc.robot;

import java.io.IOException;
import java.nio.file.Path;

import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryGenerator;
import edu.wpi.first.math.trajectory.TrajectoryUtil;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInLayouts;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.autos.AutoCommand;
import frc.robot.commands.*;
import frc.robot.subsystems.*;
import frc.robot.utils.Axis;


public class RobotContainer {

    public final CommandXboxController primaryController = new CommandXboxController(0);

    private final DrivetrainSubsystem drivetrainSubsystem = new DrivetrainSubsystem();

    public RobotContainer() {
        drivetrainSubsystem.setDefaultCommand(
            new DriveCommand(
                drivetrainSubsystem, 
                new Axis(() -> primaryController.getLeftY(), 2.25),
                new Axis(() -> primaryController.getLeftX(), 2.25),
                new Axis(() -> primaryController.getRightX(), 1.5)
            )
        );
        
        // adds button to drive forward for three seconds and then stop
        Shuffleboard.getTab("Telemetry").add(
            "Drive Forward",
            Commands.deadline(
                new WaitCommand(3.0),
                new DriveCommand(
                    drivetrainSubsystem,
                    new Axis(() -> 1.0, 2.25),
                    new Axis(() -> 0.0, 2.25),
                    new Axis(() -> 0.0, 1.5)
                )
            ).andThen(
                new DriveCommand(
                    drivetrainSubsystem,
                    new Axis(() -> 0.0),
                    new Axis(() -> 0.0),
                    new Axis(() -> 0.0)
                )
            )
        );

        configureButtonBindings();
    }

    private void configureButtonBindings() {
        primaryController.a().onTrue(Commands.runOnce(drivetrainSubsystem::toggleFieldOriented));
        primaryController.start().onTrue(Commands.runOnce(drivetrainSubsystem.gyroscope::reset));
        
    }
 
    public Command getAutonomousCommand() {
        try {
            Path path = Filesystem.getDeployDirectory().toPath().resolve("pathplanner/generatedJSON/New Path.wpilib.json");
            Trajectory traj = TrajectoryUtil.fromPathweaverJson(path);
            return new AutoCommand(drivetrainSubsystem, traj);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            DriverStation.reportError(e.getMessage(), e.getStackTrace());
        }
        return null;
    }
}
