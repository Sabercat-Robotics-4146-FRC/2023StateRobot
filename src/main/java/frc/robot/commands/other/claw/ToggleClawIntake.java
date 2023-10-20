package frc.robot.commands.other.claw;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.ClawConstants;
import frc.robot.subsystems.ClawSubsystem;
import frc.robot.RobotContainer;

public class ToggleClawIntake extends CommandBase {
    public ClawSubsystem clawSubsystem;

    private int state;
    private double cur_max;

    private Timer timer;

    public ToggleClawIntake(RobotContainer container) { 
        this.clawSubsystem = container.getClawSubsystem();
    }

    @Override
    public void initialize() {
        state = 1;
        cur_max = ClawConstants.LOWER_THRESHOLD;

        timer = new Timer();
        timer.start();
    }

    @Override
    public void execute() {
         // record the max value of a spike, and if it is not within the range of the normal spike, discard it
         double outputCurrent = clawSubsystem.clawMotor.getOutputCurrent();
         if(state == 1) {
             if(cur_max > ClawConstants.LOWER_THRESHOLD && outputCurrent > ClawConstants.LOWER_THRESHOLD) {
                 if(timer.get() == 0) timer.start();
                 cur_max = Math.max(cur_max, outputCurrent);
             } else if(timer.hasElapsed(ClawConstants.MIN_RUNTIME)) {
                 if(cur_max > ClawConstants.LOWER_THRESHOLD && cur_max < ClawConstants.UPPER_THRESHOLD && cur_max > ClawConstants.CURRENT_LIMIT) state++;
                 cur_max = outputCurrent;
             }
         } else timer.stop();

        clawSubsystem.toggleClaw(Math.min(state,2)); // just in case state somehow gets over 2.
    }

    @Override
    public boolean isFinished() {
        return state == 2;
    }
}
