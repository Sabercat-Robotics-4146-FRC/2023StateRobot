package frc.robot.commands.other;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.RobotContainer;

public class SetArmPositionCommand extends InstantCommand {
    RobotContainer container;

    public SetArmPositionCommand(RobotContainer container) {
        this.container = container;
    }

    @Override
    public void execute() {
        container.getArmSubsystem().setRotationPosition(0);
        container.getArmSubsystem().setExtensionPosition(0);
    }
}
