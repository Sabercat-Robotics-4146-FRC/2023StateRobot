package frc.robot.autos;

import java.util.List;

import com.google.gson.annotations.Expose;

import edu.wpi.first.math.geometry.Pose2d;

public class Trajectory {
    @Expose
    public List<Trajectory.State> trajectory;

    


    public class State {
        @Expose 
        public double time;
        @Expose 
        public Pose pose;
        @Expose 
        public double velocity;
        @Expose
        public double acceleration;
        @Expose
        public double curverature;
        @Expose 
        public double holonomicRotation;
        @Expose
        public double angularVelocity;
        @Expose
        public double holonomicAngularVelocity;
    }

    public class Pose {
        @Expose 
        public double radians;
        @Expose
        public double x;
        @Expose
        public double y;
    }
}
