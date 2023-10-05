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

    public DriverReadout() {
        tab = Shuffleboard.getTab("Main");
        
        armPosition = tab.add("Test", false) 
            .withWidget(BuiltInWidgets.kTextView)
            .withSize(2, 2)
            .getEntry();

        armPosition.setString("Top");
    }
    
    public ArmPositionConstants getSelectedArmPosition() {
        return ArmConstants.ARM_POSITIONS.get(armPosition.getString("Top"));
    }

    public void setArmPosition(String str) {
        armPosition.setString(str);
    }
}
