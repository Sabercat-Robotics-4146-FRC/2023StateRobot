package frc.robot.commands.other.auto;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.RobotContainer;
import frc.robot.commands.other.arm.SetArmPositionCommand;
import frc.robot.commands.other.arm.SetArmRotationCommand;

public class ScoreNoRotation extends SequentialCommandGroup {
    public ScoreNoRotation(RobotContainer container) {
        addCommands(
            new SetArmRotationCommand(container, .32),
            new SetArmPositionCommand(container),
            new InstantCommand(() -> container.getClawSubsystem().toggleClaw(0)), // released claw
            new WaitCommand(1),
            new SetArmPositionCommand(container, container.getArmSubsystem().retLimit)
        );
    }
}
