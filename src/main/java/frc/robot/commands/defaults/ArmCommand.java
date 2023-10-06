package frc.robot.commands.defaults;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.Constants.ArmConstants;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.utils.Axis;

public class ArmCommand extends CommandBase {
    RobotContainer container;
    ArmSubsystem armSubsystem;
    Axis extension, retraction, rotationAxis;

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
        // TODO Auto-generated method stub
        super.initialize();
    }

    @Override
    public void execute() {
        armSubsystem.setRotationVelocity(rotationAxis.get() * ArmConstants.ROTATION_MAX_VELOCITY);

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
