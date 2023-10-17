package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.commands.defaults.*;
import frc.robot.commands.other.AlignLeft;
import frc.robot.commands.other.AlignRight;
import frc.robot.commands.other.AlignZero;
import frc.robot.commands.other.MoveToApriltag;
import frc.robot.commands.other.SetArmPositionCommand;
import frc.robot.commands.other.SetArmRotationCommand;
import frc.robot.shuffleboard.DriverReadout;
import frc.robot.subsystems.*;
import frc.robot.utils.Axis;
import frc.robot.utils.CommandUtil;


public class RobotContainer {
    public final CommandXboxController primaryController = new CommandXboxController(0);
    public final CommandXboxController secondaryController = new CommandXboxController(1);

    private final DrivetrainSubsystem drivetrainSubsystem = new DrivetrainSubsystem();
    private final ArmSubsystem armSubsystem = new ArmSubsystem();
    private final ClawSubsystem clawSubsystem = new ClawSubsystem();
    private final VisionSubsystem visionSubsystem = new VisionSubsystem();
    private final DriverReadout driverReadout = new DriverReadout();

    public RobotContainer() {
        CommandScheduler.getInstance().registerSubsystem(drivetrainSubsystem);
        CommandScheduler.getInstance().registerSubsystem(armSubsystem);
        CommandScheduler.getInstance().registerSubsystem(clawSubsystem);
        CommandScheduler.getInstance().registerSubsystem(visionSubsystem);


        drivetrainSubsystem.setDefaultCommand(
            new DriveCommand(
                drivetrainSubsystem, 
                new Axis(() -> primaryController.getLeftY(), 3.25),
                new Axis(() -> primaryController.getLeftX(), 3.25),
                new Axis(() -> primaryController.getRightX(), 1.5)
            )
        );

        armSubsystem.setDefaultCommand(
            new ArmCommand(
                this,
                new Axis(() -> secondaryController.getRightTriggerAxis()),
                new Axis(() -> secondaryController.getLeftTriggerAxis()),
                new Axis(() -> secondaryController.getRightY())
            )
        );

        clawSubsystem.setDefaultCommand(
            new ClawCommand(
                clawSubsystem,
                secondaryController.getHID()
            )
        );
 
        configureButtonBindings();
    }

    private void configureButtonBindings() {
        primaryController.a().onTrue(Commands.runOnce(drivetrainSubsystem::toggleFieldOriented));
        primaryController.start().onTrue(Commands.runOnce(drivetrainSubsystem.gyroscope::reset));
        secondaryController.a().onTrue(new SetArmPositionCommand(this));
        secondaryController.y().onTrue(new SetArmRotationCommand(this));
        secondaryController.povUp().onTrue(
            new AlignZero(this)
                .andThen(new MoveToApriltag(this)));
        secondaryController.povLeft().onTrue(
            new AlignZero(this)
                .andThen(new MoveToApriltag(this))
                .andThen(new AlignLeft(this)));
        secondaryController.povRight().onTrue(
            new AlignZero(this)
                .andThen(new AlignRight(this)));

    }
 
    public Command getAutonomousCommand() {
        return CommandUtil.getInstance().getCommand(this, "frc.robot.autos.AutoCommand");
    }

    public DrivetrainSubsystem getDrivetrainSubsystem() {
        return this.drivetrainSubsystem;
    }

    public ArmSubsystem getArmSubsystem() {
        return this.armSubsystem;
    }

    public VisionSubsystem getVisionSubsystem() {
        return visionSubsystem;
    }

    public DriverReadout getDriverReadout() {
        return driverReadout;
    }
}
