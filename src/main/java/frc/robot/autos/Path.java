package frc.robot.autos;

import java.util.List;

import com.google.gson.annotations.Expose;

public class Path {
    @Expose
    public List<Waypoint> waypoints;
    @Expose
    public List<Marker> markers;
}
