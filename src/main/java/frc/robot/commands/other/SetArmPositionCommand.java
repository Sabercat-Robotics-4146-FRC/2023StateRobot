package frc.robot.commands.other;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.RobotContainer;
import frc.robot.subsystems.ArmSubsystem;

public class SetArmPositionCommand extends InstantCommand {
    RobotContainer container;
    ArmSubsystem armSubsystem;

    public SetArmPositionCommand(RobotContainer container) {
        this.container = container;
        this.armSubsystem = container.getArmSubsystem();

        addRequirements(armSubsystem);
    }

    @Override
    public void execute() {
        armSubsystem.setSetpoint(6000);
        //container.getArmSubsystem().setRotationPosition(0);
        container.getArmSubsystem().setExtensionPosition(6000);
    }

    @Override
    public boolean isFinished() {
        return Math.abs(armSubsystem.extensionMotor.getSelectedSensorPosition() - armSubsystem.getSetpoint()) < 500;
    }
}
