package frc.robot.commands.defaults;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.Constants.ArmConstants;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.utils.Axis;

public class ArmCommand extends CommandBase {
    RobotContainer container;
    ArmSubsystem armSubsystem;
    Axis extension, retraction, rotationAxis;
    double target;

    PIDController pid;

    public ArmCommand(RobotContainer container, Axis extension, Axis retraction, Axis rotationAxis) {
        this.container = container;
        armSubsystem = container.getArmSubsystem();
        this.extension = extension;
        this.retraction = retraction;
        this.rotationAxis = rotationAxis;

        addRequirements(armSubsystem);
    }

    @Override
    public void initialize() {
        pid = new PIDController(2.5, 0.5, 0);
        pid.setTolerance(0.01);

        target = armSubsystem.getRotation();
    }

    @Override
    public void execute() {
        //if(Math.abs(rotationAxis.get()) >= 0.05) {
            armSubsystem.setRotationVelocity(rotationAxis.get() * ArmConstants.ROTATION_MAX_VELOCITY);
            target = armSubsystem.getRotation();
        //} else {
            //double velocity = pid.calculate(armSubsystem.getRotation(), target);
            SmartDashboard.putNumber("VelOCITY", target);
            //armSubsystem.setRotationVelocity(velocity);
       // }

        // extension gear diameter: 40mm
        if(extension.get() > 0) {
            armSubsystem.setExtensionVelocity(-extension.get() * ArmConstants.EXTENSION_MAX_VELOCITY);
        } else if(retraction.get() > 0) {
            armSubsystem.setExtensionVelocity(retraction.get() * ArmConstants.EXTENSION_MAX_VELOCITY);
        } else {
            armSubsystem.setExtensionVelocity(0);
        }


    }
}
