package frc.robot.commands.other;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.DrivetrainSubsystem;
import frc.robot.subsystems.VisionSubsystem;

public class AlignWithApriltag extends CommandBase {
    private VisionSubsystem visionSubsystem;
    private DrivetrainSubsystem drivetrainSubsystem;

    private Translation2d translation;
    private double time;
    private double id;

    private Timer timer;

    public AlignWithApriltag(RobotContainer container) {
        this.visionSubsystem = container.getVisionSubsystem();
        this.drivetrainSubsystem = container.getDrivetrainSubsystem();

        addRequirements(drivetrainSubsystem);
    }

    @Override
    public void initialize() {
        double[] pose = visionSubsystem.getTargetPose();

        // distance to target (chooses closest target)
        double d = Math.abs(pose[0]) <= (56/2) ? pose[0] : (56-Math.abs(pose[0])) * Math.copySign(1, pose[0]);

        double dx = 2; // target x velocity
        double dy = dx * (pose[2]/pose[0]); // target y velocity

        translation = new Translation2d(dx, dy);

        time = (d/dx); // time takes to reach target
        timer.restart();
    }

    @Override
    public void execute() {
        drivetrainSubsystem.drive(translation, 0, true);
    }

    @Override
    public boolean isFinished() {
        return timer.hasElapsed(time) || id == 0 || id == 4 || id == 5; // finish if reached target or invalid ID
    }
}
