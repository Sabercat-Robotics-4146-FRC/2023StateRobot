package frc.robot.commands.other;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.VisionSubsystem;

public class AlignWithApriltag extends CommandBase {
    public RobotContainer container;
    public VisionSubsystem visionSubsystem;

    public AlignWithApriltag(RobotContainer container) {
        this.container = container;
        this.visionSubsystem = container.getVisionSubsystem();
    }

    @Override
    public void execute() {
        
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
