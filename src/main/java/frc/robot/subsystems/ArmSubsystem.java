package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ArmConstants;
import frc.robot.commands.other.SetArmPositionCommand;

public class ArmSubsystem extends SubsystemBase {
    public TalonFX rotationMotorLeft, rotationMotorRight, extensionMotor;
    public AnalogPotentiometer potentiometer;
    public DigitalInput extLimitSwitch, retLimitSwitch;

    public double extLimit = Integer.MIN_VALUE;
    public double retLimit = Integer.MAX_VALUE;

    private Timer timer;

    private double setpoint;

    public ArmSubsystem() {
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

        extensionMotor = new TalonFX(ArmConstants.EXTENSION_ID);
        extensionMotor.setNeutralMode(NeutralMode.Brake);

        extensionMotor.config_kF(0, 0.04485011, 30);
        extensionMotor.config_kP(0, 0, 30);
        extensionMotor.config_kI(0, 0, 30);
        extensionMotor.config_kD(0, 0, 30);

        extensionMotor.config_kF(1, 0, 30);
        extensionMotor.config_kP(1, .090, 30);
        extensionMotor.config_kI(1, 0, 30);
        extensionMotor.config_kD(1, 0, 30);

        setpoint = 0;

        //potentiometer = new AnalogPotentiometer(ArmConstants.ROTATION_POT_CHANNEL);

        extLimitSwitch = new DigitalInput(ArmConstants.EXTENSION_LIMIT_CHANNEL);
        retLimitSwitch = new DigitalInput(ArmConstants.RETRACTION_LIMIT_CHANNEL);

        timer = new Timer();

        ShuffleboardTab tab = Shuffleboard.getTab("Claw");

        tab.addNumber("Position", () -> potentiometer.get());
        tab.addNumber("Extension limit", () -> extLimit);
        tab.addNumber("Retraction Limit", () -> retLimit);
        tab.addNumber("Arm Rotation Position", () -> rotationMotorLeft.getSelectedSensorPosition());
        tab.addBoolean("Extension Limit Switch", () -> extLimitSwitch.get());
        tab.addBoolean("Retraction Limit Switch", () -> retLimitSwitch.get());
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

    public void setExtensionVelocity(double ms) {
        double pos = extensionMotor.getSelectedSensorPosition(0);
        // convert to units/100ms from meters/second
        // steps per rotation: 2048
        // gear ratio: 15
        // diameter of gear: 40mm

        double rv = 15 * ((ms / (.040 * Math.PI)) * 2048) / 10;

        // zero the velocity
        if(((pos - extLimit < 1000 || pos < extLimit) && ms < 0) || ((retLimit-pos < 1000 || pos > retLimit) && ms > 0)) {
            rv = 0;
        }

        extensionMotor.selectProfileSlot(0, 0);
        extensionMotor.set(ControlMode.Velocity, rv == 0 ? timer.hasElapsed(2000) ? -0.05 : 0 : rv);
        SmartDashboard.putNumber("Extension Velocity", extensionMotor.getSelectedSensorVelocity(0));
        SmartDashboard.putNumber("Target EV", rv);
        SmartDashboard.putNumber("Position", pos);
    }

    public void setExtensionPosition() {
        if(setpoint < extLimit) {
            setSetpointAbsolute(extLimit);
        } else if(setpoint > retLimit) {
            setSetpointAbsolute(retLimit);
        }

        extensionMotor.selectProfileSlot(1, 0);
        extensionMotor.set(ControlMode.Position, setpoint);        
    }
    
    public double getRotation() {
        return potentiometer.get();
    }

    public double getSetpoint() {
        return setpoint;
    }

    public void setSetpoint(double s) {
        this.setpoint = retLimit - s;
    }

    public void setSetpointAbsolute(double s) {
        this.setpoint = s;
    }

    @Override
    public void periodic() {
        if(extLimitSwitch.get()) {
            extLimit = extensionMotor.getSelectedSensorPosition(0);
            if(this.getCurrentCommand().getClass() == SetArmPositionCommand.class) {
                this.getCurrentCommand().cancel();
            } 
        }
        if(retLimitSwitch.get()) retLimit = extensionMotor.getSelectedSensorPosition(0);
    }
}