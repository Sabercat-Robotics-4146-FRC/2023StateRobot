package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ArmConstants;

public class ArmSubsystem extends SubsystemBase {
    public TalonFX rotationMotorLeft;
    public TalonFX rotationMotorRight;
    public TalonFX extensionMotor;
    public AnalogPotentiometer potentiometer;

    public DigitalInput extLimit;
    public DigitalInput retLimit;

    public ArmSubsystem() {
        rotationMotorLeft = new TalonFX(ArmConstants.ROTATION_LEFT_ID);
        rotationMotorLeft.setNeutralMode(NeutralMode.Brake);
        rotationMotorRight = new TalonFX(ArmConstants.ROTATION_RIGHT_ID);
        rotationMotorRight.setNeutralMode(NeutralMode.Brake);
        rotationMotorRight.setInverted(true);

        rotationMotorLeft.setNeutralMode(NeutralMode.Brake);
        rotationMotorRight.setNeutralMode(NeutralMode.Brake);

        rotationMotorLeft.config_kP(0, .01, 30);
        rotationMotorLeft.config_kI(0, 0, 30);
        rotationMotorLeft.config_kD(0, 0, 30);
        rotationMotorLeft.config_kF(0, .05, 30);

        rotationMotorRight.config_kP(0, .01, 30);
        rotationMotorRight.config_kI(0, 0, 30);
        rotationMotorRight.config_kD(0, 0, 30);
        rotationMotorRight.config_kF(0, .05, 30);

        extensionMotor = new TalonFX(ArmConstants.EXTENSION_ID);
        extensionMotor.setNeutralMode(NeutralMode.Brake);

        extensionMotor.config_kF(0, 0.04485011, 30);
        extensionMotor.config_kP(0,0, 30);
        extensionMotor.config_kI(0, 0, 30);
        extensionMotor.config_kD(0, 0, 30);
        

        potentiometer = new AnalogPotentiometer(ArmConstants.ROTATION_POT_CHANNEl);

        extLimit = new DigitalInput(ArmConstants.EXTENSION_LIMIT_CHANNEL);
        retLimit = new DigitalInput(ArmConstants.RETRACTION_LIMIT_CHANNEL);
    }

    /**
     * Sets the velocity of the arm's rotation. For manual or timed control
     * @param v target velocity in rotations per second
     */
    public void setRotationVelocity(double v) {
        //if(potentiometer.get() > )
        // convert to units/100ms
        // steps per rotation: 2048
        // gear ratio: 100 * (58/24)

        double rv = (100 * (58/24)) * (v*2048) / 10;
        SmartDashboard.putNumber("Target Velocity", rv);
        SmartDashboard.putNumber("Actual Velocity", rotationMotorRight.getSelectedSensorVelocity(0));

        rotationMotorLeft.set(ControlMode.Velocity, rv);
        rotationMotorRight.set(ControlMode.Velocity, rv);
    }

    public void setExtensionVelocity(double ms) {
        if((extLimit.get() && ms > 0) || (retLimit.get() && ms < 0)) {
            extensionMotor.set(ControlMode.Velocity, 0);
            return;
        }

        double rv = 15 * ((ms / (.040 * Math.PI)) * 2048) /10;
        // gear ratio: 15

        extensionMotor.set(ControlMode.Velocity, rv);
        SmartDashboard.putNumber("Extension Velocity", extensionMotor.getSelectedSensorVelocity(0));
        SmartDashboard.putNumber("Target EV", rv);

    }

    public void setRotationPosition() {

    }
}
