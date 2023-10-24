package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ArmConstants;

public class RotationSubsystem extends SubsystemBase {
    public TalonFX rotationMotorLeft, rotationMotorRight;
    public AnalogPotentiometer potentiometer;


    public RotationSubsystem() { 
        rotationMotorLeft = new TalonFX(ArmConstants.ROTATION_LEFT_ID);
        rotationMotorRight = new TalonFX(ArmConstants.ROTATION_RIGHT_ID);
        rotationMotorRight.setInverted(true);

        for(TalonFX motor : new TalonFX[] {rotationMotorRight, rotationMotorLeft}) {
            motor.setNeutralMode(NeutralMode.Brake);
            motor.config_kP(0, .01, 30);
            motor.config_kI(0, 0, 30);
            motor.config_kD(0, 0, 30);
            motor.config_kF(0, .05, 30);
        }

        potentiometer = new AnalogPotentiometer(ArmConstants.ROTATION_POT_CHANNEL);

        Shuffleboard.getTab("Arm").addNumber("POS", () -> potentiometer.get());
    }

     /**
     * Sets the velocity of the arm's rotation. For manual or timed control
     * @param v target velocity in rotations per second
     */
    public void setRotationVelocity(double v) {
        // convert to units/100ms
        // steps per rotation: 2048
        // gear ratio: 100 * (58/24)

        double rv = (100 * (58/24)) * (v*2048) / 10;
        SmartDashboard.putNumber("Target Velocity", rv);
        SmartDashboard.putNumber("Actual Velocity", rotationMotorRight.getSelectedSensorVelocity(0));

        rotationMotorLeft.set(ControlMode.Velocity, rv);
        rotationMotorRight.set(ControlMode.Velocity, rv);
    }

    public double getRotation() {
        return potentiometer.get();
    }
}
