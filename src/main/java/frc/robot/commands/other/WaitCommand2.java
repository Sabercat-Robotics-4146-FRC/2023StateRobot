package frc.robot.commands.other;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class WaitCommand2 extends SequentialCommandGroup {
    public WaitCommand2() {
        addCommands(new WaitCommand(10));
    }
}
