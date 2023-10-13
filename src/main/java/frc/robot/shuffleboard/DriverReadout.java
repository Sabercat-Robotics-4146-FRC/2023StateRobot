package frc.robot.shuffleboard;

import java.util.Map;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;

public class DriverReadout {
    ShuffleboardTab tab;

    private GenericEntry team;

    public DriverReadout() {
        tab = Shuffleboard.getTab("Main");
        
        team = Shuffleboard.getTab("My Tab")
            .add("My Number", 0)
            .withWidget(BuiltInWidgets.kComboBoxChooser)
            .withProperties(Map.of("Blue", "Blue", "Red", "Red"))
            .getEntry();
    }

    public String getTeam() {
        return team.getString("Red");
    }

}
