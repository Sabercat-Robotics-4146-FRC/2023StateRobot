package frc.robot.autos;

import java.util.Map;

import frc.lib.util.auto.Auto;
import frc.lib.util.auto.CommandObject;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj2.command.*;

public class AutoCommand extends SequentialCommandGroup {    
    public AutoCommand() {
        Auto auto = new Auto(Filesystem.getDeployDirectory().toPath().resolve("pathplanner/autos/New Auto.auto"));

        CommandObject c = new CommandObject((String) auto.command.get("type"), (Map<String, Object>) auto.command.get("data"));

        // add the evaluated auto command
        addCommands(c.eval());
    }
}
