// RobotBuilder Version: 5.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.

// ROBOTBUILDER TYPE: RobotContainer.

package frc.robot;

import frc.robot.commands.*;
import frc.robot.subsystems.*;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command.InterruptionBehavior;

// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.PS4Controller;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.subsystems.*;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS


/**
 * This class is where the bulk of the robot should be declared.  Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {

  private static RobotContainer m_robotContainer = new RobotContainer();

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
// The robot's subsystems
    public final Intake m_intake = new Intake();
    public final UpAndDown m_upAndDown = new UpAndDown();
    public final Wrist m_wrist = new Wrist();
    public final CargoDisplay m_cargoDisplay = new CargoDisplay();
    public final Vision m_vision = new Vision();
    public final DriveTrain m_driveTrain = new DriveTrain();

// Joysticks
//private final PS4Controller pS4Controller1 = new PS4Controller(0);
// private final XboxController pS4Controller1 = new XboxController(0);
private final GenericHID pS4Controller1 = new GenericHID(Constants.kJoystickPort);


    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS

  
  // A chooser for autonomous commands
  SendableChooser<Command> m_chooser = new SendableChooser<>();

  /**
  * The container for the robot.  Contains subsystems, OI devices, and commands.
  */
  private RobotContainer() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=SMARTDASHBOARD
    // Smartdashboard Subsystems


    // SmartDashboard Buttons
    SmartDashboard.putData("Autonomous Command", new AutonomousCommand());
    SmartDashboard.putData("Balance", new Balance(m_driveTrain));
    SmartDashboard.putData("Pickup", new Pickup());
    SmartDashboard.putData("Extend", new Extend());
    SmartDashboard.putData("Retract", new Retract());
    SmartDashboard.putData("AlignWithPole", new AlignWithPole());
    SmartDashboard.putData("PlaceCone", new PlaceCone());
    SmartDashboard.putData("PlaceCube", new PlaceCube());
    SmartDashboard.putData("DisplayCone", new DisplayCone());
    SmartDashboard.putData("DisplayCube", new DisplayCube());

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=SMARTDASHBOARD
    // Configure the button bindings
    configureButtonBindings();

    // Configure default commands
    m_driveTrain.setDefaultCommand(
      new Teleop(
        () -> -pS4Controller1.getRawAxis(1),
        () -> -pS4Controller1.getRawAxis(4),
        m_driveTrain));

    m_upAndDown.setDefaultCommand(
      new TeleopUpAndDown(
        () -> (pS4Controller1.getRawAxis(3) - pS4Controller1.getRawAxis(2)), m_upAndDown));



    // Configure autonomous sendable chooser
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=AUTONOMOUS

    m_chooser.setDefaultOption("Autonomous Command", new DriveForwardAuto(m_driveTrain));

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=AUTONOMOUS

    SmartDashboard.putData("Auto Mode", m_chooser);
  }

  public static RobotContainer getInstance() {
    return m_robotContainer;
  }

  /**
   * Use this method to define your button->command mappings.  Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a
   * {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=BUTTONS
// Create some buttons
final JoystickButton pS4Button1 = new JoystickButton(pS4Controller1, PS4Controller.Button.kSquare.value);        
pS4Button1.onTrue(new Pickup().withInterruptBehavior(InterruptionBehavior.kCancelSelf));
                        


    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=BUTTONS
    final JoystickButton crossButton = new JoystickButton(pS4Controller1, 3);        
    crossButton.whileTrue(new TeleopPoleTracking(
        () -> -pS4Controller1.getRawAxis(1),
        m_driveTrain, m_vision));
  }

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=FUNCTIONS
public GenericHID getPS4Controller1() {
      return pS4Controller1;
    }


    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=FUNCTIONS

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
  */
  public Command getAutonomousCommand() {
    // The selected command will be run in autonomous
    return m_chooser.getSelected();
  }

  public void simulationInit() {
    m_driveTrain.simulationInit();
    m_upAndDown.simulationInit();
    m_vision.simulationInit();
  }
  

}

