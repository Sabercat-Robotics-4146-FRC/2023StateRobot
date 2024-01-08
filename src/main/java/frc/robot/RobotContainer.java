package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.autos.AutoCommand;
import frc.robot.commands.defaults.*;
import frc.robot.commands.other.arm.SetArmPositionCommand;
import frc.robot.commands.other.arm.SetArmRotationCommand;
import frc.robot.commands.other.auto.PickupPiece;
import frc.robot.commands.other.auto.ScoreNoRotation;
import frc.robot.commands.other.vision.AlignLeft;
import frc.robot.commands.other.vision.AlignRight;
import frc.robot.commands.other.vision.AlignZero;
import frc.robot.commands.other.vision.MoveToApriltag;
import frc.robot.shuffleboard.AutoTab;
import frc.robot.shuffleboard.DriverReadout;
import frc.robot.subsystems.*;
import frc.robot.utils.Axis;
 

public class RobotContainer {
    public final CommandXboxController primaryController = new CommandXboxController(0);
    public final CommandXboxController secondaryController = new CommandXboxController(1);

    private final DrivetrainSubsystem drivetrainSubsystem = new DrivetrainSubsystem();
    private final ArmSubsystem armSubsystem = new ArmSubsystem();
    private final ClawSubsystem clawSubsystem = new ClawSubsystem();
    private final VisionSubsystem visionSubsystem = new VisionSubsystem();
    private final DriverReadout driverReadout = new DriverReadout();
    private final RotationSubsystem rotationSubsystem = new RotationSubsystem();

    private final AutoTab autoTab = new AutoTab();

    private static RobotContainer instance;
    public static synchronized RobotContainer getInstance() {
    if (instance == null) {
        instance = new RobotContainer();
    }
        return instance;
    }

    public RobotContainer() {
        CommandScheduler.getInstance().registerSubsystem(drivetrainSubsystem);
        CommandScheduler.getInstance().registerSubsystem(armSubsystem);
        CommandScheduler.getInstance().registerSubsystem(clawSubsystem);
        CommandScheduler.getInstance().registerSubsystem(visionSubsystem);
        CommandScheduler.getInstance().registerSubsystem(rotationSubsystem);

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
                new Axis(() -> secondaryController.getLeftTriggerAxis())
            )
        );

        rotationSubsystem.setDefaultCommand(
            new RotationCommand(
                this,
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
        // primaryController.back().onTrue(Commands.runOnce(drivetrainSubsystem::resetGyro180));
        // secondaryController.a().onTrue(new SetArmPositionCommand(this));
        // secondaryController.y().onTrue(new PickupPiece(this));
        // secondaryController.x().onTrue(new ScoreNoRotation(this));
        // primaryController.povUp().onTrue(
        //     new AlignZero(this)
        //         .andThen(new MoveToApriltag(this)));
        // primaryController.povLeft().onTrue(
        //     new AlignZero(this)
        //         .andThen(new MoveToApriltag(this))
        //         .andThen(new AlignLeft(this)).andThen(new AlignZero(this)));
        // primaryController.povRight().onTrue(
        //     new AlignZero(this)
        //         .andThen(new AlignRight(this)));

    }
 
    public Command getAutonomousCommand() {
        return new AutoCommand(autoTab.getSelectedPath());
    }

    public DrivetrainSubsystem getDrivetrainSubsystem() {
        return this.drivetrainSubsystem;
    }

    public ArmSubsystem getArmSubsystem() {
        return this.armSubsystem;
    }

    public ClawSubsystem getClawSubsystem() {
        return this.clawSubsystem;
    }

    public VisionSubsystem getVisionSubsystem() {
        return visionSubsystem;
    }

    public DriverReadout getDriverReadout() {
        return driverReadout;
    }

    public RotationSubsystem getRotationSubsystem() { 
        return rotationSubsystem;
    }

    public AutoTab getAutoTab() {
        return autoTab;
    }
}
