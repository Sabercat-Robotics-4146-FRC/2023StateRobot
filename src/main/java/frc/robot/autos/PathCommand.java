package frc.robot.autos;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.lib.util.auto.PathObject;
import frc.lib.util.auto.Trajectory;
import frc.robot.RobotContainer;
import frc.robot.Constants.AutoConstants;

public class PathCommand extends CommandBase {
    private RobotContainer container;
    private PathObject path;
    private Map<Double, Command> commandMap;
    private List<Command> currentCommands;
    private FollowTrajectoryCommand trajectoryCommand;

    public PathCommand(RobotContainer container, String path) {
        this(container, Path.of(path));
    }

    public PathCommand(RobotContainer container, Path path) {
        this(container, new PathObject(path, path.getFileName().toString().replace(".path", "")));
    }

    public PathCommand(RobotContainer container, PathObject path) {
        this.container = container;
        this.path = path;

        commandMap = new HashMap<>();

        for(PathObject.EventMarker marker : path.eventMarkers) {
            commandMap.put(marker.waypointRelativePos, marker.command.eval());
        }
    }

    @Override
    public void initialize() {
        Trajectory trajectory = new Trajectory(AutoConstants.TRAJECTORY_DIR.toString() + ("/" + path.pathName + " Red.json"));

        System.out.println(AutoConstants.TRAJECTORY_DIR.toString() + ("/" + path.pathName + " Red.json"));
        System.out.println(AutoConstants.TRAJECTORY_DIR.resolve(path.pathName + " Red.json"));

        trajectoryCommand = new FollowTrajectoryCommand(container, trajectory);
        currentCommands = new ArrayList<>();
    }

    @Override
    public void execute() {
        /*
         * schedule trajectory command if it has not been scheduled yet
         */
        if(!trajectoryCommand.isScheduled() && !trajectoryCommand.isFinished()) {
            trajectoryCommand.schedule();
        } 

        // // get the next command 
        // Command nextCommand = commandMap.get(Math.round(trajectoryCommand.getPosition() * 20) / 20.0);

        // /*
        //  * loop through all current commands and evaluate
        //  * each command
        //  */
        // for(Command command : currentCommands) {
        //     // if the command is finished, remove it from current commands
        //     if(command.isFinished()) {
        //         currentCommands.remove(command);
        //         continue;
        //     }

        //     // check if next command has overlapping subsystems
        //     // if it does, end the commands that overlap
        //     if(nextCommand != null && !currentCommands.contains(nextCommand) && !Collections.disjoint(command.getRequirements(), nextCommand.getRequirements())) {
        //         command.end(false);
        //         currentCommands.remove(command);
        //     }   
        // }

        // /*
        //  * If the current command is not null, meaning it exists,
        //  * schedule it and add it to the currentCommands list
        //  */
        // if(nextCommand != null && !currentCommands.contains(nextCommand)) {
        //     currentCommands.add(nextCommand);
        //     nextCommand.schedule();
        // }
    }

    @Override
    public void end(boolean interrupted) {
        for(Command command : currentCommands) {
            command.end(true);
        }
    }

    @Override
    public boolean isFinished() {
        return trajectoryCommand.isFinished();
    }
}
