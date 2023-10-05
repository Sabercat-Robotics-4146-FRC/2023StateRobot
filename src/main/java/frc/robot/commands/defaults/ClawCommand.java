package frc.robot.commands.defaults;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.ClawConstants;
import frc.robot.subsystems.ClawSubsystem;

public class ClawCommand extends CommandBase{
    private ClawSubsystem clawSubsystem;
    private XboxController HID;

    private int lower_threshold, upper_threshold;
    private double cur_max;

    private Timer timer;

    private int state; // 0 == reverse, 1 == high, 2 == low

    public ClawCommand(ClawSubsystem clawSubsystem, XboxController HID) {
        this.clawSubsystem = clawSubsystem;
        this.HID = HID;

        state = 0;
        lower_threshold = 10;
        upper_threshold = 100;
        cur_max = lower_threshold;

        timer = new Timer();

        addRequirements(clawSubsystem);
    }

    @Override
    public void execute() {
        if(HID.getAButtonPressed()) {
            if(state == 0) {
                timer.restart();
                state = 1;
            } else state = 0;
        }

        SmartDashboard.putNumber("State", state);
        
        // record the max value of a spike, and if it is not within the range of the normal spike, discard it
        double outputCurrent = clawSubsystem.clawMotor.getOutputCurrent();
        if(state == 1) {
            if(cur_max > lower_threshold && outputCurrent > lower_threshold) {
                if(timer.get() == 0) timer.start();
                cur_max = Math.max(cur_max, outputCurrent);
            } else {
                if(timer.hasElapsed(1) && cur_max > lower_threshold && cur_max < upper_threshold && cur_max > ClawConstants.CURRENT_LIMIT) state++;
                cur_max = outputCurrent;
            }
        } else timer.stop();

        clawSubsystem.toggleClaw(Math.min(state,2)); // just in case state somehow gets over 2.
    }
}
