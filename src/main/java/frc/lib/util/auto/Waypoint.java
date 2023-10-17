package frc.lib.util.auto;

import java.util.Map;

import com.google.gson.annotations.Expose;

public class Waypoint {
    @Expose
    public Map<String, Double> anchorPoint;
    @Expose
    public double holonomicAngle;
    @Expose
    public boolean isStopPoint;
    @Expose 
    public StopEvent stopEvent;
}
