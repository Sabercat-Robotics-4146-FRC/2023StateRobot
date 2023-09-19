package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ClawConstants;

public class ClawSubsystem extends SubsystemBase {
  public CANSparkMax clawMotor;
  public boolean clawEnabled;
  Timer timer;

  public ClawSubsystem() {
    clawMotor = new CANSparkMax(ClawConstants.CLAW_ID, MotorType.kBrushless);

    clawEnabled = false;

    clawMotor.setSmartCurrentLimit(80);
    clawMotor.enableVoltageCompensation(12);
    clawMotor.setOpenLoopRampRate(.5);

    timer = new Timer();
  }

  public void toggleClaw() {
    clawEnabled = !clawEnabled;

    if(clawEnabled) {
      clawMotor.setVoltage(ClawConstants.HIGH_VOLTAGE);
      timer.restart();
    } else clawMotor.setVoltage(-0.75);
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("Claw Current", clawMotor.getOutputCurrent());
    
    // if motor hits spike limit, lower voltage to hold in place
    if(timer.hasElapsed(0.3) && clawEnabled && clawMotor.getOutputCurrent() > ClawConstants.CURRENT_LIMIT) {
      clawMotor.setVoltage(ClawConstants.LOW_VOLTAGE);
      SmartDashboard.putBoolean("Lower", true);
    }
  }

}
