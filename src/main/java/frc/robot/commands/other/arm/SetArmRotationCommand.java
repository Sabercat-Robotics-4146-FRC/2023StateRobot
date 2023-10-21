package frc.robot.commands.other.arm;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.Constants.ArmConstants.ArmPositionConstants;
import frc.robot.shuffleboard.DriverReadout;
import frc.robot.subsystems.RotationSubsystem;

public class SetArmRotationCommand extends CommandBase {
    private RotationSubsystem rotationSubsystem;
    private DriverReadout driverReadout;

    private PIDController pid;
    private ArmPositionConstants position;

    private SlewRateLimiter slr;

    private double target = Double.NaN;

    public SetArmRotationCommand(RobotContainer container) {
        this.rotationSubsystem = container.getRotationSubsystem();
        this.driverReadout = container.getDriverReadout();

        addRequirements(this.rotationSubsystem);
    }

    public SetArmRotationCommand(RobotContainer container, double target) {
        this.rotationSubsystem = container.getRotationSubsystem();
        this.driverReadout = container.getDriverReadout();
        this.target = target;

        addRequirements(this.rotationSubsystem);
    }

    @Override
    public void initialize() {
        position = driverReadout.getSelectedArmPosition();

        pid = new PIDController(2.5, 0.5, 0.0);
        pid.setSetpoint(Double.isNaN(target) ? position.ROTATION_POSITION : target);
        pid.setTolerance(0.01);

        slr = new SlewRateLimiter(.3);
    }

    @Override
    public void execute() {
       double velocity = slr.calculate(pid.calculate(rotationSubsystem.getRotation())); // get velocity in terms of rotations/sec
       SmartDashboard.putNumber("VELOCITY", velocity);
       rotationSubsystem.setRotationVelocity(velocity);
    }

    @Override
    public void end(boolean interrupted) { 
        rotationSubsystem.setRotationVelocity(0);
    }

    @Override 
    public boolean isFinished() {
        return pid.atSetpoint();
    }
}


