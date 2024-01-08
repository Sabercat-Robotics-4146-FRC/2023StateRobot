package frc.robot.shuffleboard;

import java.io.File;
import java.nio.file.Path;

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import frc.robot.Constants;

public class AutoTab {
    private SendableChooser<String> path;
    private SendableChooser<String> team;

    public AutoTab() {
        ShuffleboardTab tab = Shuffleboard.getTab("Auto");

        team = new SendableChooser<>();
        path = new SendableChooser<>();

        team.setDefaultOption("Blue", "Blue");
        team.addOption("Red", "Red");

        path.setDefaultOption("None", null);
        updatePathOptions();

        tab.add("Team", team)
            .withSize(1, 1);

        tab.add("Auto", path)
            .withSize(1, 1);

    }

    public void updatePathOptions() {
        Path path = Constants.AutoConstants.AUTO_DIR;
        String team = this.team.getSelected();

        for(File file : path.toFile().listFiles()) {
            String name = file.getName();

            if(!name.endsWith(".auto")) continue;
            if(!(name = name.substring(0, name.lastIndexOf("."))).endsWith(team)) continue;
            
            this.path.addOption(name.substring(0, name.lastIndexOf(team)).trim(), file.getPath().toString());
        }
    }

    public String getSelectedPath() {
        return path.getSelected();
    }

    public String getSelectedTeam() {
        return team.getSelected();
    }

}
