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
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.PS4Controller;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.POVButton;
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
private final GenericHID driveController = new GenericHID(Constants.kJoystickPort);
private final GenericHID intakeController = new GenericHID(Constants.kJoystickPort2);


    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS

  
  // A chooser for autonomous commands
  SendableChooser<Command> m_chooser = new SendableChooser<>();

  private double lessSensitive(double power) {

    return Math.copySign(power*power, power);
  }

  /**
  * The container for the robot.  Contains subsystems, OI devices, and commands.
  */
  private RobotContainer() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=SMARTDASHBOARD
    // Smartdashboard Subsystems

    m_driveTrain.setLiftHeightSupplier(
      () -> m_upAndDown.getLiftPosition());

    

    // SmartDashboard Buttons
    SmartDashboard.putData("Autonomous Command", new AutonomousCommand());
    //SmartDashboard.putData("Balance", new Balance(m_driveTrain));
    SmartDashboard.putData("DisplayCone", new DisplayCone(m_cargoDisplay));
    SmartDashboard.putData("DisplayCube", new DisplayCube(m_cargoDisplay));
    SmartDashboard.putData("Green", new CargoDisplayCommand("G", m_cargoDisplay));
    SmartDashboard.putData("Rainbow", new CargoDisplayCommand("r", m_cargoDisplay));
    SmartDashboard.putData("No Cargo", new CargoDisplayCommand("b", m_cargoDisplay));
    SmartDashboard.putData("Test Eject Auto", new EjectAuto(m_intake));

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=SMARTDASHBOARD
    // Configure the button bindings
    configureDriveButtonBindings();
    configureIntakeButtonBindings();

    // Configure default commands
    m_driveTrain.setDefaultCommand(
      new Teleop(
        () -> -driveController.getRawAxis(1),
        () -> -0.5 * driveController.getRawAxis(4),
        () -> m_upAndDown.getLiftPosition(),
        m_driveTrain));

    m_upAndDown.setDefaultCommand(
      new TeleopUpAndDown(
        () ->lessSensitive(intakeController.getRawAxis(3) - intakeController.getRawAxis(2)), m_upAndDown));

   // m_wrist.setDefaultCommand(new TeleopWrist(
     // () -> 0.2 *lessSensitive(driveController.getRawAxis(5)), m_wrist));

    // Configure autonomous sendable chooser
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=AUTONOMOUS

    m_chooser.setDefaultOption("Do Nothing Auto", new DoNothingAuto());
    m_chooser.addOption("Drive Forward Auto", new DriveForwardAuto(m_driveTrain));
    m_chooser.addOption("Charge Station Auto", new ChargeStationAuto(m_driveTrain));
    m_chooser.addOption("Eject Auto", new EjectAuto(m_intake).andThen(new DriveForwardAuto(m_driveTrain)));
    //TODO: Ad buttons for each level to score
    //auto: 1. score highest level 2. balance
    m_chooser.addOption("Lift High Auto", new AutoLift(m_upAndDown, Constants.kHighLift));
    
  

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
  private void configureDriveButtonBindings() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=BUTTONS
// Create some buttons
final JoystickButton pS4Button1 = new JoystickButton(driveController, PS4Controller.Button.kSquare.value);        
pS4Button1.onTrue(new Eject(m_intake).withInterruptBehavior(InterruptionBehavior.kCancelSelf));
                        


    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=BUTTONS
    final JoystickButton crossButton = new JoystickButton(driveController, 3);        
    /*crossButton.whileTrue(new TeleopPoleTracking(
        () -> -driveController.getRawAxis(1),
        m_driveTrain, m_vision));*/



    
        final JoystickButton leftBumper = new JoystickButton(driveController, 5);
        final JoystickButton rightBumper = new JoystickButton(driveController, 6);
        
        leftBumper.whileTrue(new Pickup(m_intake));
        rightBumper.whileTrue(new Eject(m_intake));

        
        final JoystickButton aButton = new JoystickButton(driveController, 1);
        final JoystickButton bButton = new JoystickButton(driveController, 2);
        final JoystickButton yButton = new JoystickButton(driveController, 4);

        yButton.toggleOnTrue(new SlowMode(m_driveTrain));
        aButton.onTrue(Commands.parallel(new Teleop(() -> 0.1, m_driveTrain), new Pickup(m_intake))
          .withTimeout(1.0));

        POVButton povUp = new POVButton(driveController, 0);
        povUp.whileTrue(new WristUp(m_wrist));
        POVButton povDown = new POVButton(driveController, 180);
        povDown.whileTrue(new WristDown(m_wrist));

        
        POVButton povLeft = new POVButton(driveController, 270);
        povLeft.whileTrue(new Retract(m_upAndDown));
        POVButton povRight = new POVButton(driveController, 90);
        povRight.whileTrue(new Extend(m_upAndDown));
  }

  private void configureIntakeButtonBindings() {
    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=BUTTONS
// Create some buttons
    GenericHID controller = intakeController;
final JoystickButton pS4Button1 = new JoystickButton(controller, PS4Controller.Button.kSquare.value);        
pS4Button1.onTrue(new Eject(m_intake).withInterruptBehavior(InterruptionBehavior.kCancelSelf));
                    


// END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=BUTTONS
final JoystickButton crossButton = new JoystickButton(controller, 3);        
crossButton.whileTrue(new TeleopPoleTracking(
    () -> -driveController.getRawAxis(1),
    m_driveTrain, m_vision));


    final JoystickButton leftBumper = new JoystickButton(controller, 5);
    final JoystickButton rightBumper = new JoystickButton(controller, 6);
    
    leftBumper.whileTrue(new Pickup(m_intake));
    rightBumper.whileTrue(new Eject(m_intake));

    
    final JoystickButton aButton = new JoystickButton(controller, 1);
    aButton.onTrue(Commands.parallel(new Teleop(() -> 0.1, m_driveTrain), new Pickup(m_intake))
      .withTimeout(1.0));
      
    final JoystickButton bButton = new JoystickButton(controller, 2);
    final JoystickButton yButton = new JoystickButton(controller, 4);

    yButton.toggleOnTrue(new SlowMode(m_driveTrain));

    POVButton povUp = new POVButton(controller, 0);
    povUp.whileTrue(new WristUp(m_wrist));
    POVButton povDown = new POVButton(controller, 180);
    povDown.whileTrue(new WristDown(m_wrist));


}

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=FUNCTIONS
public GenericHID getPS4Controller1() {
      return driveController;
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

