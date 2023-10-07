package frc.robot.commands.other;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.RobotContainer;
import frc.robot.Constants.ArmConstants.ArmPositionConstants;
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
        ArmPositionConstants constants = container.getDriverReadout().getSelectedArmPosition();

        SmartDashboard.putNumber("test 5", constants.EXTENSION_POSITION);

        armSubsystem.setSetpoint(constants.EXTENSION_POSITION);
        //container.getArmSubsystem().setRotationPosition(0);
        container.getArmSubsystem().setExtensionPosition();
    }

    @Override
    public boolean isFinished() {
        return Math.abs(armSubsystem.extensionMotor.getSelectedSensorPosition() - armSubsystem.getSetpoint()) < 500;
    }
}
