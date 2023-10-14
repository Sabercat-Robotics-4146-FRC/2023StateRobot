package frc.robot.commands.other;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.DrivetrainSubsystem;
import frc.robot.subsystems.VisionSubsystem;

public class MoveToApriltag extends CommandBase {
    private VisionSubsystem visionSubsystem;
    private DrivetrainSubsystem drivetrainSubsystem;

    PIDController lateral;
    PIDController longitudinal;
    PIDController rotational;

    SlewRateLimiter slrLateral;
    SlewRateLimiter slrLongitudinal;
    
    public MoveToApriltag(RobotContainer container) {
        this.visionSubsystem = container.getVisionSubsystem();
        this.drivetrainSubsystem = container.getDrivetrainSubsystem();

        addRequirements(drivetrainSubsystem);
    }

    @Override
    public void initialize() {
        longitudinal = new PIDController(1, 0.1, 0);
        longitudinal.setSetpoint(6.0);

        lateral = new PIDController(0.7, 0, 0);
        lateral.setSetpoint(0.0);

        rotational = new PIDController(1, 0, 0);
        rotational.setSetpoint(0.0);

        slrLateral = new SlewRateLimiter(2.2);
        slrLongitudinal = new SlewRateLimiter(1.5);
    }

    @Override
    public void execute() {
        double[] targetPose = visionSubsystem.getTargetPose();

        double longitudinalValue = longitudinal.atSetpoint() ? 0 : longitudinal.calculate(targetPose[2]);
        double lateralValue = lateral.atSetpoint() ? 0 : lateral.calculate(targetPose[0]);
        double rotationalValue = rotational.atSetpoint() ? 0 : rotational.calculate(drivetrainSubsystem.gyroscope.getAngle());

        if(longitudinal.atSetpoint()) longitudinalValue = 0;
        if(lateral.atSetpoint()) lateralValue = 0;

        longitudinalValue = -Math.copySign(MathUtil.clamp(Math.abs(longitudinalValue), 0.01, 2), longitudinalValue)/10;
        lateralValue = Math.copySign(MathUtil.clamp(Math.abs(lateralValue), 0.01, 2), lateralValue)/10;
        rotationalValue = Math.copySign(MathUtil.clamp(Math.abs(rotationalValue), 0.01, 2), rotationalValue)/10;

        SmartDashboard.putNumber("dx", lateralValue);
        SmartDashboard.putNumber("dy", longitudinalValue);

        drivetrainSubsystem.drive(
            new Translation2d(
                slrLongitudinal.calculate(longitudinalValue),
                slrLateral.calculate(lateralValue)), 
            rotationalValue);
    }

    @Override
    public void end(boolean interrupted) {
        drivetrainSubsystem.drive(new Translation2d(0, 0), 0);
    }

    @Override
    public boolean isFinished() {
        return visionSubsystem.getAprilTagID() == -1 || (longitudinal.atSetpoint() && lateral.atSetpoint() && rotational.atSetpoint());
    }
}
