package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.revrobotics.*;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ClawConstants;

public class Claw extends SubsystemBase {
  public CANSparkMax clawMotor;
  private boolean clawEnabled;

  public Claw() {
    clawMotor = new TalonFX(ClawConstants.CLAW_ID);

    clawEnabled = false;
  }

  public void toggleClaw(){
    if(!clawEnabled) {
        clawMotor.set(ControlMode.PercentOutput, 0);
    } else {
        clawMotor.set(ControlMode.PercentOutput, 0.6);
    }

    clawEnabled = !clawEnabled;
  }

}
