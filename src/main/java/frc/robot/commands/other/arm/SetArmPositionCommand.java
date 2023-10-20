package frc.robot.commands.other.arm;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.RobotContainer;
import frc.robot.Constants.ArmConstants.ArmPositionConstants;
import frc.robot.subsystems.ArmSubsystem;

public class SetArmPositionCommand extends InstantCommand {
    private RobotContainer container;
    private ArmSubsystem armSubsystem;

    private double target = Double.NaN;

    public SetArmPositionCommand(RobotContainer container) {
        this.container = container;
        this.armSubsystem = container.getArmSubsystem();

        addRequirements(armSubsystem);
    }

    public SetArmPositionCommand(RobotContainer container, double target) {
        this.container = container;
        this.armSubsystem = container.getArmSubsystem();
        this.target = target;

        addRequirements(armSubsystem);
    }

    @Override
    public void execute() {
        ArmPositionConstants constants = container.getDriverReadout().getSelectedArmPosition();

        armSubsystem.setSetpoint(Double.isNaN(target) ? constants.EXTENSION_POSITION : target);
        container.getArmSubsystem().setExtensionPosition();
    }

    @Override
    public boolean isFinished() {
        return Math.abs(armSubsystem.extensionMotor.getSelectedSensorPosition() - armSubsystem.getSetpoint()) < 500;
    }
}
