package frc.robot.commands.defaults;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.Constants.ArmConstants;
import frc.robot.subsystems.RotationSubsystem;
import frc.robot.utils.Axis;

public class RotationCommand extends CommandBase {
    RobotContainer container;
    RotationSubsystem rotationSubsystem;
    Axis rotationAxis;
    double target;

    PIDController pid;

    public RotationCommand(RobotContainer container, Axis rotationAxis) {
        this.container = container;
        this.rotationSubsystem = container.getRotationSubsystem();
        this.rotationAxis = rotationAxis;

        addRequirements(rotationSubsystem);
    }

    @Override
    public void initialize() {
        pid = new PIDController(2.5, 0.5, 0);
        pid.setTolerance(0.02);

        target = rotationSubsystem.getRotation();
    }

    @Override
    public void execute() {
      //  if(Math.abs(rotationAxis.get()) >= 0.05) {
            rotationSubsystem.setRotationVelocity(rotationAxis.get() * ArmConstants.ROTATION_MAX_VELOCITY);
         //   target = rotationSubsystem.getRotation();
        // } else {
        //     double velocity = pid.calculate(rotationSubsystem.getRotation(), target);
        //     SmartDashboard.putNumber("VelOCITY", target);
        //    // rotationSubsystem.setRotationVelocity(velocity);
        // }
    }
}
