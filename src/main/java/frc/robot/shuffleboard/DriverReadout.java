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

    private GenericEntry team;

    private int selected;
    private String[] options;

    public DriverReadout() {
        tab = Shuffleboard.getTab("Main");
        
        team = Shuffleboard.getTab("Main")
            .add("Team", 0)
            .withWidget(BuiltInWidgets.kComboBoxChooser)
            .withProperties(Map.of("Blue", "Blue", "Red", "Red"))
            .getEntry();

        selected = 0;
        options = new String[]{"Top","Middle","Bottom"};

        tab.addString("Test", () -> options[selected]);
    }

    public String getTeam() {
        return team.getString("Red");
    }

    public ArmPositionConstants getSelectedArmPosition() {
        return ArmConstants.ARM_POSITIONS.get(options[selected]);
    }

    public void setArmPosition(int dir) {
        selected = Math.max(0, Math.min(selected+dir, 2));
    }

}
