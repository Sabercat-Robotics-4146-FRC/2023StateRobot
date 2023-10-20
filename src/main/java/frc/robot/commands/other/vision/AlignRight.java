package frc.robot.commands.other.vision;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.DrivetrainSubsystem;
import frc.robot.subsystems.VisionSubsystem;

public class AlignRight extends CommandBase {
    private VisionSubsystem visionSubsystem;
    private DrivetrainSubsystem drivetrainSubsystem;

    private PIDController lateral;
    private PIDController longitudinal;
    private PIDController rotational;

    private boolean longitudinalFinished;
    private boolean lateralFinished;
    private boolean rotationalFinished;

    private boolean noTarget;
    private Timer timer;

    private SlewRateLimiter slrLateral;
    private SlewRateLimiter slrLongitudinal;
    
    public AlignRight(RobotContainer container) {
        this.visionSubsystem = container.getVisionSubsystem();
        this.drivetrainSubsystem = container.getDrivetrainSubsystem();

        addRequirements(drivetrainSubsystem);
    }

    @Override
    public void initialize() {
        timer = new Timer();
        timer.stop();
        
        longitudinal = new PIDController(0.22, 0.1, 0.1);
        longitudinal.setSetpoint(5.7);
        longitudinal.setTolerance(0.1);

        lateral = new PIDController(0.22, 0.1, 0.1);
        lateral.setSetpoint(2.3);
        lateral.setTolerance(.05);

        rotational = new PIDController(.1, 0, 0);
        rotational.setSetpoint(0.0);
        rotational.setTolerance(1);

        slrLateral = new SlewRateLimiter(3.2);
        slrLongitudinal = new SlewRateLimiter(3.2);

        longitudinalFinished = false;
        lateralFinished = false;
        rotationalFinished = false;
        noTarget = false;
    }


    @Override
    public void execute() {
        if(visionSubsystem.getAprilTagID() != -1) {
            timer.reset();

            double[] targetPose = visionSubsystem.getTargetPose();

            double longitudinalValue = longitudinal.calculate(targetPose[2]);
            double lateralValue = lateral.calculate(targetPose[0]);
            double rotationalValue = rotational.calculate(drivetrainSubsystem.gyroscope.getAngle() - Math.round(drivetrainSubsystem.gyroscope.getAngle()/180)*180);

            longitudinalValue = -Math.copySign(MathUtil.clamp(Math.abs(longitudinalValue), 0.01, 2), longitudinalValue);
            lateralValue = Math.copySign(MathUtil.clamp(Math.abs(lateralValue), 0.01, 2), lateralValue);
            rotationalValue = -Math.copySign(MathUtil.clamp(Math.abs(rotationalValue), 0.01, 2), rotationalValue);

            if(longitudinal.atSetpoint() || targetPose[2] < longitudinal.getSetpoint()) longitudinalFinished = true;
            if(lateral.atSetpoint()) lateralFinished = true;
            if(rotational.atSetpoint()) rotationalFinished = true;

            if(longitudinalFinished) longitudinalValue = 0;
            if(lateralFinished) lateralValue = 0;
            if(rotationalFinished) rotationalValue = 0;

            SmartDashboard.putNumber("dx", lateralValue);
            SmartDashboard.putNumber("dy", longitudinalValue);

            drivetrainSubsystem.drive(
                new Translation2d(
                    -slrLongitudinal.calculate(longitudinalValue),
                    -slrLateral.calculate(lateralValue)), 
                rotationalValue);
        } else {
            if(timer.get() == 0) timer.restart();
            else if(timer.get() > 0.5) noTarget = true;
        }
    }

    @Override
    public void end(boolean interrupted) {
        drivetrainSubsystem.drive(new Translation2d(0, 0), 0);
    }

    @Override
    public boolean isFinished() {
        return noTarget || (longitudinalFinished && lateralFinished && rotationalFinished);
    }
}
