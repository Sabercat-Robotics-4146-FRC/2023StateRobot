package frc.robot;

import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.trajectory.TrajectoryConfig;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.math.util.Units;
import frc.lib.util.COTSFalconSwerveConstants;
import frc.lib.util.SwerveModuleConstants;

public final class Constants {
    public static final double stickDeadband = 0.05;

    public static final class DriveConstants {
        public static final int pigeonID = 13;

        public static final COTSFalconSwerveConstants chosenModule = 
            COTSFalconSwerveConstants.SDSMK4(COTSFalconSwerveConstants.driveGearRatios.SDSMK4_L2);

        public static final double trackWidth = Units.inchesToMeters(20.0); 
        public static final double wheelBase = Units.inchesToMeters(21.0); 
        public static final double wheelCircumference = chosenModule.wheelCircumference;

        public static final SwerveDriveKinematics swerveKinematics = new SwerveDriveKinematics(
            new Translation2d(wheelBase / 2.0, trackWidth / 2.0),
            new Translation2d(wheelBase / 2.0, -trackWidth / 2.0),
            new Translation2d(-wheelBase / 2.0, trackWidth / 2.0),
            new Translation2d(-wheelBase / 2.0, -trackWidth / 2.0));

        public static final double driveGearRatio = chosenModule.driveGearRatio;
        public static final double angleGearRatio = chosenModule.angleGearRatio;

        public static final boolean canCoderInvert = chosenModule.canCoderInvert;

        public static final int angleContinuousCurrentLimit = 25;
        public static final int anglePeakCurrentLimit = 40;
        public static final double anglePeakCurrentDuration = 0.1;
        public static final boolean angleEnableCurrentLimit = true;

        public static final int driveContinuousCurrentLimit = 35;
        public static final int drivePeakCurrentLimit = 50;
        public static final double drivePeakCurrentDuration = 0.1;
        public static final boolean driveEnableCurrentLimit = true;

        public static final double openLoopRamp = 0.5;
        public static final double closedLoopRamp = 0.0;


        public static final double[] anglePIDF = 
            {chosenModule.angleKP, chosenModule.angleKI, chosenModule.angleKD, chosenModule.angleKF};

        public static final double[] drivePIDF = 
            {0.05, 0.0, 0.0, 0.0};
        
        public static final double[] driveSVA = 
            {(0.70067 / 12), (2.2741 / 12), (0.16779 / 12)};



        public static final double maxSpeed = 3; 
        public static final double maxAngularVelocity = 5.0; 


        public static final NeutralMode angleNeutralMode = NeutralMode.Brake;
        public static final NeutralMode driveNeutralMode = NeutralMode.Brake;


        public static final class FrontLeft {
            public static final boolean isInverted = true;
            public static final int driveMotorID = 1;
            public static final int angleMotorID = 2;
            public static final int canCoderID = 3;
            // 144.676208496 2023 COMP BOT
            // 282.85675048828125 - 2.2796630859375 DRIVE BOT
            public static final Rotation2d angleOffset = 
                Rotation2d.fromDegrees(282.85675048828125 - 2.2796630859375);
            public static final SwerveModuleConstants constants = 
                new SwerveModuleConstants(driveMotorID, angleMotorID, canCoderID, angleOffset, isInverted);
        }


        public static final class FrontRight { 
            public static final boolean isInverted = true;
            public static final int driveMotorID = 4;
            public static final int angleMotorID = 5;
            public static final int canCoderID = 6;
            // -39.298095703125 2023 COMP BOT
            // 142.92938232421875 - 1.92260742187 DRIVE BOT
            public static final Rotation2d angleOffset = 
                Rotation2d.fromDegrees(1142.92938232421875 - 1.92260742187 + 90 - 20 + 4);
            public static final SwerveModuleConstants constants = 
                new SwerveModuleConstants(driveMotorID, angleMotorID, canCoderID, angleOffset, isInverted);
        }
        

        public static final class BackLeft { 
            public static final boolean isInverted = true;
            public static final int driveMotorID = 7;
            public static final int angleMotorID = 8;
            public static final int canCoderID = 9;
            // -185.0372314453125 2023 COMP BOT
            // 62.1826171875 - 0.8514404296875 DRIVE BOT
            public static final Rotation2d angleOffset = 
                Rotation2d.fromDegrees(62.1826171875 - 0.8514404296875 + 2.8);
            public static final SwerveModuleConstants constants = 
                new SwerveModuleConstants(driveMotorID, angleMotorID, canCoderID, angleOffset, isInverted);
        }


        public static final class BackRight { 
            public static final boolean isInverted = true;
            public static final int driveMotorID = 10;
            public static final int angleMotorID = 11;
            public static final int canCoderID = 12;
            // 261.419677734375 2023 COMP BOT
            // 161.71875 + 180 - 1.00250244140625 DRIVE BOT
            public static final Rotation2d angleOffset = 
                Rotation2d.fromDegrees(161.71875 + 180 - 1.00250244140625 -2.74658203125);
            public static final SwerveModuleConstants constants = 
                new SwerveModuleConstants(driveMotorID, angleMotorID, canCoderID, angleOffset, isInverted);
        }
    }

    public static final class AutoConstants { 
        public static final double maxSpeed = 2;
        public static final double maxAcceleration = 1;

        public static final double maxAngularSpeed = Math.PI/2;
        public static final double maxAngularAcceleration = Math.PI/4;
    
    
        public static final TrapezoidProfile.Constraints rotationalConstraints =
            new TrapezoidProfile.Constraints(maxAngularSpeed, maxAngularAcceleration);

        public static final TrajectoryConfig trajectoryConfig =
            new TrajectoryConfig(
                Constants.AutoConstants.maxSpeed,
                Constants.AutoConstants.maxAcceleration)
                .setKinematics(DriveConstants.swerveKinematics);
    
    }

    // public static class ClawConstants {
    //     public static final int CLAW_ID = 24;
    //     public static final int CLAW_POT_CHANNEL = 1;
    
    //     public static final double CLAW_CONE_MID = 796.0 / 1024.0;
    //     public static final double CLAW_CUBE = 0;
    //     public static final double CLAW_FULLY_OPENED = 0;
    //     public static final double CLAW_FULLY_CLOSED = 0;
    //   }
}
