package frc.robot.commands.other;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.Constants.ArmConstants.ArmPositionConstants;
import frc.robot.shuffleboard.DriverReadout;
import frc.robot.subsystems.ArmSubsystem;

public class SetArmRotationCommand extends CommandBase {
    private ArmSubsystem armSubsystem;
    private DriverReadout driverReadout;

    private PIDController pid;
    private ArmPositionConstants position;

    private SlewRateLimiter slr;

    public SetArmRotationCommand(RobotContainer container) {
        this.armSubsystem = container.getArmSubsystem();
        this.driverReadout = container.getDriverReadout();

        addRequirements(this.armSubsystem);
    }

    @Override
    public void initialize() {
        position = driverReadout.getSelectedArmPosition();

        pid = new PIDController(1.9, 0.5, 0.1);
        pid.setSetpoint(position.ROTATION_POSITION);
        pid.setTolerance(0.01);

        slr = new SlewRateLimiter(.2);
    }

    @Override
    public void execute() {
       double velocity = slr.calculate(pid.calculate(armSubsystem.getRotation())); // get velocity in terms of rotations/sec
       SmartDashboard.putNumber("VELOCITY", velocity);
       armSubsystem.setRotationVelocity(velocity);
    }

    @Override 
    public boolean isFinished() {
        return pid.atSetpoint();
    }
}


