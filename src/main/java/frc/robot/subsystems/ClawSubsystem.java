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

    clawMotor.setSmartCurrentLimit(60);
    clawMotor.enableVoltageCompensation(12);
    clawMotor.setOpenLoopRampRate(.5);
  }

  public void toggleClaw(int state) {
    double[] map = {-0.75, ClawConstants.HIGH_VOLTAGE, ClawConstants.LOW_VOLTAGE};
    
    clawMotor.setVoltage(map[state]);
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("Claw Current", clawMotor.getOutputCurrent());
  }

}
