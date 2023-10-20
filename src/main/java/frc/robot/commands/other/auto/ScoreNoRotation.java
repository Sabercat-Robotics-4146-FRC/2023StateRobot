package frc.robot.commands.other.auto;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.RobotContainer;
import frc.robot.commands.other.arm.SetArmPositionCommand;
import frc.robot.commands.other.arm.SetArmRotationCommand;
import frc.robot.commands.other.vision.AlignLeft;
import frc.robot.commands.other.vision.AlignZero;
import frc.robot.commands.other.vision.MoveToApriltag;

public class ScoreNoRotation extends SequentialCommandGroup {
    public ScoreNoRotation(RobotContainer container) {
        addCommands(
            new SetArmRotationCommand(container, .3),
            new AlignZero(container),
            new MoveToApriltag(container),
            new AlignLeft(container),
            new SetArmRotationCommand(container, .3),
            new SetArmPositionCommand(container),
            new InstantCommand(() -> container.getClawSubsystem().toggleClaw(0)) // released claw
        );
    }
}
