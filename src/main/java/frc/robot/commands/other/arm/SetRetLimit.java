package frc.robot.commands.other.arm;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.ArmSubsystem;

public class SetRetLimit extends CommandBase {
    private ArmSubsystem armSubsystem;

    public SetRetLimit(RobotContainer container) {
        this.armSubsystem = container.getArmSubsystem();

        addRequirements(armSubsystem);
    }

    @Override
    public void execute() { 
        armSubsystem.setExtensionVelocity(0.1);
    }

    @Override
    public void end(boolean interrupted) {
        armSubsystem.setExtensionVelocity(0);
    }

    @Override 
    public boolean isFinished() { 
        return armSubsystem.retLimitSwitch.get();
    }
}
