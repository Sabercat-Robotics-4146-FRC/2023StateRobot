package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ClawConstants;

public class ClawSubsystem extends SubsystemBase {
  public CANSparkMax clawMotor;
  public boolean clawEnabled;

  public ClawSubsystem() {
    clawMotor = new CANSparkMax(ClawConstants.CLAW_ID, MotorType.kBrushless);

    clawEnabled = false;

    clawMotor.setSmartCurrentLimit(80);
    clawMotor.enableVoltageCompensation(12);
    clawMotor.setOpenLoopRampRate(.5);
  }

  public void toggleClaw() {
    clawEnabled = !clawEnabled;

    if(clawEnabled) clawMotor.setVoltage(ClawConstants.HIGH_VOLTAGE);
    else clawMotor.stopMotor();
  }

  @Override
  public void periodic() {
      // if motor hits spike limit, lower voltage to hold in place
      if(clawEnabled && clawMotor.getOutputCurrent() > ClawConstants.CURRENT_LIMIT) {
        clawMotor.setVoltage(ClawConstants.LOW_VOLTAGE);
      }

      SmartDashboard.putNumber("Claw Current", clawMotor.getOutputCurrent());
  }

}
