package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ClawConstants;

public class Claw extends SubsystemBase {
  public CANSparkMax clawMotor;
  private boolean clawEnabled;

  public Claw() {
    clawMotor = new CANSparkMax(ClawConstants.CLAW_ID, MotorType.kBrushless);

    clawEnabled = false;

    clawMotor.setSmartCurrentLimit(80);
    clawMotor.enableVoltageCompensation(12);
    clawMotor.setOpenLoopRampRate(.5);
  }

  public void toggleClaw() {
    clawEnabled = !clawEnabled;
    if(clawEnabled) clawMotor.setVoltage(6);
    else clawMotor.stopMotor();
  }

  @Override
  public void periodic() {
      // if motor hits spike limit, lower voltage to hold in place
      if(clawEnabled && clawMotor.getOutputCurrent() > 20) {
        clawMotor.setVoltage(0.5);
      }
      SmartDashboard.putNumber("current", clawMotor.getOutputCurrent());
  }

}
