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

    private double cur_max;

    private Timer timer;

    public ClawCommand(ClawSubsystem clawSubsystem, XboxController HID) {
        this.clawSubsystem = clawSubsystem;
        this.HID = HID;

        addRequirements(clawSubsystem);
    }

    @Override
    public void initialize() {
        cur_max = ClawConstants.LOWER_THRESHOLD;

        timer = new Timer();
    }

    @Override
    public void execute() {
        int state = clawSubsystem.getState();
        if(HID.getBButtonPressed()) {
            if(state == 0) {
                timer.restart();
                clawSubsystem.setState(1);
            } else clawSubsystem.setState(0);
        }

        SmartDashboard.putNumber("State", state);
        SmartDashboard.putNumber("Cur Max", cur_max);

        // record the max value of a spike, and if it is not within the range of the normal spike, discard it
        double outputCurrent = clawSubsystem.clawMotor.getOutputCurrent();
        if(state == 1) {
            if(cur_max >= ClawConstants.LOWER_THRESHOLD && outputCurrent > ClawConstants.LOWER_THRESHOLD) {
                if(timer.get() == 0) timer.start();
                cur_max = Math.max(cur_max, outputCurrent);
            } else if(timer.hasElapsed(ClawConstants.MIN_RUNTIME)) {
                if(cur_max > ClawConstants.LOWER_THRESHOLD && cur_max < ClawConstants.UPPER_THRESHOLD && cur_max > ClawConstants.CURRENT_LIMIT) {
                    clawSubsystem.setState(2);
                }
                cur_max = outputCurrent;
            }
        } else timer.stop();

        clawSubsystem.toggleClaw(Math.min(clawSubsystem.getState(), 2)); // just in case state somehow gets over 2.
    }
}
