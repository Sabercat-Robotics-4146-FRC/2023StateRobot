package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.autos.AutoCommand;
import frc.robot.commands.defaults.*;
import frc.robot.commands.other.SetArmPositionCommand;
import frc.robot.subsystems.*;
import frc.robot.utils.Axis;
import frc.robot.utils.CommandUtil;


public class RobotContainer {
    public final CommandXboxController primaryController = new CommandXboxController(0);
    public final CommandXboxController secondaryController = new CommandXboxController(1);

    private final DrivetrainSubsystem drivetrainSubsystem = new DrivetrainSubsystem();
    private final ArmSubsystem armSubsystem = new ArmSubsystem();
    private final ClawSubsystem clawSubsystem = new ClawSubsystem();

    public RobotContainer() {
        CommandScheduler.getInstance().registerSubsystem(drivetrainSubsystem);
        CommandScheduler.getInstance().registerSubsystem(armSubsystem);
        CommandScheduler.getInstance().registerSubsystem(clawSubsystem);


        drivetrainSubsystem.setDefaultCommand(
            new DriveCommand(
                drivetrainSubsystem, 
                new Axis(() -> primaryController.getLeftY(), 2.25),
                new Axis(() -> primaryController.getLeftX(), 2.25),
                new Axis(() -> primaryController.getRightX(), 1.5)
            )
        );

        armSubsystem.setDefaultCommand(
            new ArmCommand(
                armSubsystem,
                new Axis(() -> secondaryController.getRightTriggerAxis()),
                new Axis(() -> secondaryController.getLeftTriggerAxis()),
                new Axis(() -> secondaryController.getRightY())
            )
        );

        configureButtonBindings();
    }

    private void configureButtonBindings() {
        primaryController.a().onTrue(Commands.runOnce(drivetrainSubsystem::toggleFieldOriented));
        primaryController.start().onTrue(Commands.runOnce(drivetrainSubsystem.gyroscope::reset));
        secondaryController.b().onTrue(Commands.runOnce(clawSubsystem::toggleClaw));
        secondaryController.a().onTrue(new SetArmPositionCommand(this));
    }
 
    public Command getAutonomousCommand() {
        return CommandUtil.getInstance().getCommand(this, "AutoCommand");
    }

    public DrivetrainSubsystem getDrivetrainSubsystem() {
        return this.drivetrainSubsystem;
    }

    public ArmSubsystem getArmSubsystem() {
        return this.armSubsystem;
    }

    public ClawSubsystem getClawSubsystem() { 
        return clawSubsystem;
    }
}
