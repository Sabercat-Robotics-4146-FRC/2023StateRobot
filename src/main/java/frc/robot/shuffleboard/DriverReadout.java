package frc.robot.shuffleboard;

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import frc.robot.Constants.ArmConstants;
import frc.robot.Constants.ArmConstants.ArmPositionConstants;

public class DriverReadout {
    ShuffleboardTab tab;

    private int selected;

    private String[] options;

    public DriverReadout() {
        tab = Shuffleboard.getTab("Main");

        selected = 0;

        options = new String[]{"Top","Middle","Bottom"};

       tab.addString("Test", () -> options[selected]);
    }
    
    public ArmPositionConstants getSelectedArmPosition() {
        return ArmConstants.ARM_POSITIONS.get(options[selected]);
    }

    public void setArmPosition(int dir) {
        selected = Math.max(0, Math.min(selected+dir, 2));
    }
}
