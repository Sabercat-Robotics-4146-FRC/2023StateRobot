package frc.robot.commands.other;

import edu.wpi.first.math.controller.PIDController;
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

    public SetArmRotationCommand(RobotContainer container) {
        this.armSubsystem = container.getArmSubsystem();
        this.driverReadout = container.getDriverReadout();
    }

    @Override
    public void initialize() {
        position = driverReadout.getSelectedArmPosition();

        pid = new PIDController(1, 0, 0);
        pid.setSetpoint(position.ROTATION_POSITION);
    }

    @Override
    public void execute() {
        double velocity = pid.calculate(armSubsystem.getRotation())/1024; // get velocity in terms of rotations/sec
        armSubsystem.setRotationVelocity(velocity);
    }

    @Override 
    public boolean isFinished() {
        return pid.atSetpoint();
    }
}


