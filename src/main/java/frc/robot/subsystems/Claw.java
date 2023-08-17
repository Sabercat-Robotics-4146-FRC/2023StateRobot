// package frc.robot.subsystems;

// import com.ctre.phoenix.motorcontrol.ControlMode;
// import com.ctre.phoenix.motorcontrol.can.TalonSRX;
// import edu.wpi.first.wpilibj2.command.SubsystemBase;
// import frc.robot.Constants.ClawConstants;

// public class Claw extends SubsystemBase {
//   public TalonSRX clawMotor;
//   boolean toggle;

//   public Claw() {
//     clawMotor = new TalonSRX(ClawConstants.CLAW_ID);

//     clawMotor.config_kP(3, 0.3, 30);
//     clawMotor.config_kI(3, 0, 30);
//     clawMotor.config_kD(3, 0, 30);
//   }

//   public void toggle() {
//     toggle = !toggle;
//   }

//   public void toggle(boolean b) {
//     toggle = b;
//   }

//   @Override
//   public void periodic() {
//     if(toggle) {
//         clawMotor.set(ControlMode.PercentOutput, 1);
//     } else {
//         clawMotor.set(ControlMode.PercentOutput, 0);
//     }
//   }

// }
