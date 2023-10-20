package frc.robot.commands.other.auto;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.RobotContainer;
import frc.robot.commands.other.arm.SetArmPositionCommand;

public class ScoreNoRotation extends SequentialCommandGroup {
    public ScoreNoRotation(RobotContainer container) {
        addCommands(
            new SetArmPositionCommand(container),
            new InstantCommand(() -> container.getClawSubsystem().toggleClaw(0)) // released claw
        );
    }
}
