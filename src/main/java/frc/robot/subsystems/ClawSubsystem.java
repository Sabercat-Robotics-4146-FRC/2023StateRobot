package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ClawConstants;

public class ClawSubsystem extends SubsystemBase {
  public CANSparkMax clawMotor;
  public boolean clawEnabled;

  private int state;

  public ClawSubsystem() {
    clawMotor = new CANSparkMax(ClawConstants.CLAW_ID, MotorType.kBrushless);

    clawEnabled = false;

    state = 0;

    clawMotor.setSmartCurrentLimit(60);
    clawMotor.enableVoltageCompensation(12);
    clawMotor.setOpenLoopRampRate(.5);
  }

  public void toggleClaw(int state) {
    this.state = state;
    double[] map = {-0.75, ClawConstants.HIGH_VOLTAGE, ClawConstants.LOW_VOLTAGE};
    
    clawMotor.setVoltage(map[state]);
  }

  public int getState() {
    return state;
  }

  public void setState(int state){
    this.state = state;
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("Claw Current", clawMotor.getOutputCurrent());
    SmartDashboard.putNumber("Claw Voltage", clawMotor.getAppliedOutput());
  }

}
