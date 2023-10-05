package frc.robot.shuffleboard;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import frc.robot.Constants.ArmConstants;
import frc.robot.Constants.ArmConstants.ArmPositionConstants;

public class DriverReadout {
    ShuffleboardTab tab;

    GenericEntry armPosition;

    private int selected;

    private String[] options;

    public DriverReadout() {
        tab = Shuffleboard.getTab("Main");
        
        armPosition = tab.add("Test", false) 
            .withWidget(BuiltInWidgets.kTextView)
            .withSize(2, 2)
            .getEntry();

        selected = 0;
        options = (String[]) ArmConstants.ARM_POSITIONS.keySet().toArray();

        armPosition.setString(options[selected]);
    }
    
    public ArmPositionConstants getSelectedArmPosition() {
        return ArmConstants.ARM_POSITIONS.get(armPosition.getString(options[selected]));
    }

    public void setArmPosition(int dir) {
        selected = Math.max(0, Math.min(selected+dir, 2));
        armPosition.setString(options[selected]);
    }
}
