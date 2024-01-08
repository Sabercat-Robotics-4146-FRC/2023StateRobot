
package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.lib.CTREConfigs;
import frc.robot.autos.AutoCommand;
import frc.robot.shuffleboard.AutoTab;


public class Robot extends TimedRobot {
  public static CTREConfigs ctreConfigs;

  private AutoCommand m_autonomousCommand;
  private RobotContainer m_robotContainer;
  private AutoTab m_autoTab;

  @Override
  public void robotInit() {
    ctreConfigs = new CTREConfigs();

    m_robotContainer = RobotContainer.getInstance();

    m_autonomousCommand = (AutoCommand) m_robotContainer.getAutonomousCommand();
    m_autoTab = m_robotContainer.getAutoTab();
  }

  @Override
  public void robotPeriodic() {
    CommandScheduler.getInstance().run();

    /*
     * - check when the autonomous command is to be updated by comparing
     *   the currently loaded autonomous path with the path currently
     *   selected in shuffleboard.
     * 
     * - check if the command is scheduled and do not update the command
     *   if this is the case. 
     */
    if(!m_autonomousCommand.isScheduled() && !m_autonomousCommand.getPath().equals(m_autoTab.getSelectedPath())) {
      m_autonomousCommand = (AutoCommand) m_robotContainer.getAutonomousCommand();
    }
  }

  @Override
  public void autonomousInit() {

    if (m_autonomousCommand != null) {
      m_autonomousCommand.schedule();
    }
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {}

  @Override
  public void teleopInit() {

    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
  }

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {}

  @Override
  public void testInit() {
    // Cancels all running commands at the start of test mode.
    CommandScheduler.getInstance().cancelAll();
  }

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {}
}
