package frc.robot.autos;

import java.util.List;

import com.google.gson.annotations.Expose;

public abstract class Marker {
    @Expose
    public double position;
    @Expose
    public List<String> names;
       
}
