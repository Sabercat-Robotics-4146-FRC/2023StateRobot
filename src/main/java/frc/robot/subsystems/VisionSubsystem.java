package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.Constants.LimelightConstants;

public class VisionSubsystem implements Subsystem {
  public static NetworkTable mLime;

  public VisionSubsystem() {
    mLime = NetworkTableInstance.getDefault().getTable("limelight");

    Shuffleboard.getTab("Vision").addNumber("April Tag ID", () -> getAprilTagID());
  }

  //test

  public double getDistanceToTarget(double targetHeight) {
    double angle = getVerticalOffset() + LimelightConstants.LIMELIGHT_ANGLE;
    return (targetHeight - LimelightConstants.LIMELIGHT_HEIGHT) / Math.tan(Math.toRadians(angle));
  }

  public boolean getSeesTarget() {
    return mLime.getEntry("tv").getBoolean(true);
  }

  public double getHorizontalOffset() {
    return mLime.getEntry("tx").getDouble(0.0);
  }

  public double getVerticalOffset() {
    return mLime.getEntry("ty").getDouble(0.0);
  }

  public double getTargetArea() {
    return mLime.getEntry("ta").getDouble(0.0);
  }

  public double getAprilTagID() {
    return mLime.getEntry("tid").getDouble(0.0);
  }

  public double[] getTargetPose() {
    // X, Y, Z, Roll, Pitch, Yaw. The important ones are the first 3
    return mLime.getEntry("targetpose_cameraspace").getDoubleArray(new double[6]);
  }

  public double[] getCameraPose() {
    // X, Y, Z, Roll, Pitch, Yaw. The important ones are the first 3
    return mLime.getEntry("camerapose_targetspace").getDoubleArray(new double[6]);
  }
}