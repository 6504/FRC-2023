// RobotBuilder Version: 5.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.

// ROBOTBUILDER TYPE: Subsystem.

package frc.robot.subsystems;


import frc.robot.commands.*;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.hal.SimDouble;
import edu.wpi.first.hal.simulation.SimDeviceDataJNI;
import edu.wpi.first.math.VecBuilder;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotController;
// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;
import edu.wpi.first.wpilibj.motorcontrol.PWMVictorSPX;
import edu.wpi.first.wpilibj.simulation.DifferentialDrivetrainSim;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.kauailabs.navx.frc.AHRS;
// END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS
import com.revrobotics.CANSparkMax;
import com.revrobotics.REVPhysicsSim;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

/**
 *
 */
public class DriveTrain extends SubsystemBase {
    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS
public static final double wheelDiameter = 6.00;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

private CANSparkMax leftFront;
private CANSparkMax leftRear;
private MotorControllerGroup left;
private CANSparkMax rightFront;
private CANSparkMax rightRear;
private MotorControllerGroup right;
private DifferentialDrive differentialDrive1;
private Field2d m_field = new Field2d();


    
    /**
    *
    */
    public DriveTrain() {
leftFront = new CANSparkMax(0, MotorType.kBrushless);
 leftFront.setInverted(false);

leftRear = new CANSparkMax(1, MotorType.kBrushless);
 leftRear.setInverted(false);

left = new MotorControllerGroup(leftFront, leftRear  );
 addChild("Left",left);
 

rightFront = new CANSparkMax(2, MotorType.kBrushless);
 rightFront.setInverted(true);

rightRear = new CANSparkMax(3, MotorType.kBrushless);
 rightRear.setInverted(true);

right = new MotorControllerGroup(rightFront, rightRear  );
 addChild("Right",right);
 

differentialDrive1 = new DifferentialDrive(left, right);
 addChild("Differential Drive 1",differentialDrive1);
 differentialDrive1.setSafetyEnabled(true);
differentialDrive1.setExpiration(0.1);
differentialDrive1.setMaxOutput(1.0);

// TODO: determine real conversion factor from ticks to meters!
leftFront.getEncoder().setPositionConversionFactor(0.0001);
rightFront.getEncoder().setPositionConversionFactor(0.0001);
leftRear.getEncoder().setPositionConversionFactor(0.0001);
rightRear.getEncoder().setPositionConversionFactor(0.0001);

SmartDashboard.putData("Field", m_field);
SmartDashboard.putData("diffDrive", differentialDrive1);

m_odometry = new DifferentialDriveOdometry(
    m_navx.getRotation2d(),
    leftFront.getEncoder().getPosition(),
    rightFront.getEncoder().getPosition()
    );

    }

    private DifferentialDrivetrainSim m_driveSim = new DifferentialDrivetrainSim(
        DCMotor.getNEO(2),       // 2 NEO motors on each side of the drivetrain.
        7.29,                    // 7.29:1 gearing reduction.
        7.5,                     // MOI of 7.5 kg m^2 (from CAD model).
        60.0,                    // The mass of the robot is 60 kg.
        Units.inchesToMeters(3), // The robot uses 3" radius wheels.
        0.7112,                  // The track width is 0.7112 meters.
      
        // The standard deviations for measurement noise:
        // x and y:          0.001 m
        // heading:          0.001 rad
        // l and r velocity: 0.1   m/s
        // l and r position: 0.005 m
        VecBuilder.fill(0.001, 0.001, 0.001, 0.1, 0.1, 0.005, 0.005)
      );

        private AHRS m_navx = new AHRS();

        private DifferentialDriveOdometry m_odometry;

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
        m_odometry.update(
            m_navx.getRotation2d(),
            leftFront.getEncoder().getPosition(),
            rightFront.getEncoder().getPosition()
          );

        m_field.setRobotPose(m_odometry.getPoseMeters());
    }

    @Override
    public void simulationPeriodic() {
      m_driveSim.setInputs(
        left.get() * RobotController.getInputVoltage(),
        right.get() * RobotController.getInputVoltage());
      
      m_driveSim.update(0.02);
  
      leftFront.getEncoder().setPosition(m_driveSim.getLeftPositionMeters());
      // How do I set left velocity?
      rightFront.getEncoder().setPosition(m_driveSim.getRightPositionMeters());
      // How do I set right velocity?
      
      int dev = SimDeviceDataJNI.getSimDeviceHandle("navX-Sensor[0]");
      SimDouble angle = new SimDouble(SimDeviceDataJNI.getSimValueHandle(dev, "Yaw"));
      angle.set(-m_driveSim.getHeading().getDegrees());
    }

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void drive(double leftPower, double rightPower) {
        left.set(leftPower);
        right.set(rightPower);

    }
    public void POVdrive(double speed, double rotation){
        differentialDrive1.arcadeDrive(speed, rotation);
    }

    public void simulationInit() {
        REVPhysicsSim.getInstance().addSparkMax(leftFront, DCMotor.getNEO(1));
        REVPhysicsSim.getInstance().addSparkMax(leftRear, DCMotor.getNEO(1));
        REVPhysicsSim.getInstance().addSparkMax(rightFront, DCMotor.getNEO(1));
        REVPhysicsSim.getInstance().addSparkMax(rightFront, DCMotor.getNEO(1));
    }

}

