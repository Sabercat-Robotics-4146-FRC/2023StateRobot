package frc.robot.autos;

import frc.lib.util.auto.AutoObject;
import edu.wpi.first.wpilibj2.command.*;

public class AutoCommand extends SequentialCommandGroup {  
    String path; 

    public AutoCommand(String path) {
        this.path = path; 

        // basic null check
        if(path == null) return; 

        // create autonomous object with the path
        AutoObject auto = new AutoObject(path);

        // add the evaluated auto command
        addCommands(auto.getCommand().eval());
    }

    public String getPath() {
        return path;
    }
}
