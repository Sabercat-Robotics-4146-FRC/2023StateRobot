package frc.robot.utils;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.RobotContainer;
import frc.robot.autos.AutoCommand;
import frc.robot.autos.Marker;
import frc.robot.autos.StopEvent;

public class CommandUtil {
    public static Command getCommand(RobotContainer container, StopEvent stopEvent) {
        return new AutoCommand(container);
    }
}
