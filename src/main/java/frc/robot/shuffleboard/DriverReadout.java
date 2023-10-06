package frc.robot.shuffleboard;

import java.util.Map;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import frc.robot.Constants.ArmConstants;
import frc.robot.Constants.ArmConstants.ArmPositionConstants;

public class DriverReadout {
    ShuffleboardTab tab;

    private int selected;
    private GenericEntry team;

    private String[] options;

    public DriverReadout() {
        tab = Shuffleboard.getTab("Main");

        selected = 0;

        options = new String[]{"Top","Middle","Bottom"};

        tab.addString("Scoring Level", () -> options[selected]);
        
        team = Shuffleboard.getTab("My Tab")
            .add("My Number", 0)
            .withWidget(BuiltInWidgets.kComboBoxChooser)
            .withProperties(Map.of("Blue", "Blue", "Red", "Red"))
            .getEntry();
    }
    
    public ArmPositionConstants getSelectedArmPosition() {
        return ArmConstants.ARM_POSITIONS.get(options[selected]);
    }

    public void setArmPosition(int dir) {
        selected = Math.max(0, Math.min(selected+dir, 2));
    }

    public String getTeam() {
        return team.getString("Red");
    }

}
