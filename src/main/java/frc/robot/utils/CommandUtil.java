package frc.robot.utils;

import java.lang.reflect.Constructor;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.autos.AutoCommand;
import frc.robot.RobotContainer;

public class CommandUtil {
    private static CommandUtil instance;
    public static synchronized CommandUtil getInstance() {
      if (instance == null) {
        instance = new CommandUtil();
      }
      return instance;
    }
    
    public Command getCommand(RobotContainer container, String str) {
        Command command = null;
        try {
            Class<?> c = Class.forName(str);
            Constructor<?> con = c.getDeclaredConstructor(RobotContainer.class);
            con.setAccessible(true);
            command = (Command) con.newInstance(container);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        
        
        return command;
    }
}
