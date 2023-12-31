package frc.robot.commands.other.auto;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.RobotContainer;
import frc.robot.commands.other.arm.SetArmPositionCommand;
import frc.robot.commands.other.arm.SetArmRotationCommand;
import frc.robot.commands.other.claw.ToggleClawIntake;

/**
 * Command to automatically pick up a piece from the 
 * ground, as long as the robot is in a desirable position.
 */
public class PickupPiece extends SequentialCommandGroup {
    public PickupPiece(RobotContainer container) { 
        addCommands(
            new SetArmRotationCommand(container, 0.52), // find setpoint
            new ParallelCommandGroup(
                new ToggleClawIntake(container),
                new SetArmPositionCommand(container, 35000)), // find setpoint
            new WaitCommand(1),
            new SetArmRotationCommand(container, 0.35)  // move back off the ground, find setpoint
        );
    }
}
