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

import frc.robot.Constants;
import frc.robot.commands.*;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.hal.SimDouble;
import edu.wpi.first.hal.simulation.SimDeviceDataJNI;
import edu.wpi.first.math.VecBuilder;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.Timer;
// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;
import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;
import edu.wpi.first.wpilibj.simulation.DifferentialDrivetrainSim;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import java.util.function.DoubleSupplier;

import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.kauailabs.navx.frc.AHRS;
// END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS
import com.revrobotics.CANSparkMax;
import com.revrobotics.REVPhysicsSim;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax.ControlType;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

/**
 *
 */
public class DriveTrain extends SubsystemBase {
    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS
    public static final double wheelDiameter = 6.00;
    public double kP, kI, kD, kIz, kFF, kMaxOutput, kMinOutput;
    private SparkMaxPIDController m_pidControllerLeft;
    private SparkMaxPIDController m_pidControllerRight;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

    private CANSparkMax leftFront;
    private CANSparkMax leftRear;
    private CANSparkMax rightFront;
    private CANSparkMax rightRear;
    private Field2d m_field = new Field2d();

    private boolean m_slowMode = false;

    private DoubleSupplier m_liftHeight = null;

    private Timer safetyTimer = new Timer();

    /**
    *
    */
    public DriveTrain() {
        leftFront = new CANSparkMax(13, MotorType.kBrushless);
        leftFront.setInverted(false);
        leftFront.setOpenLoopRampRate(Constants.kRampRate);
        leftFront.setClosedLoopRampRate(Constants.kRampRate);

        leftRear = new CANSparkMax(10, MotorType.kBrushless);
        leftRear.setInverted(false);
        leftRear.setOpenLoopRampRate(Constants.kRampRate);
        leftRear.setClosedLoopRampRate(Constants.kRampRate);
        leftRear.follow(leftFront);

        rightFront = new CANSparkMax(11, MotorType.kBrushless);
        rightFront.setInverted(true);
        rightFront.setOpenLoopRampRate(Constants.kRampRate);
        rightFront.setClosedLoopRampRate(Constants.kRampRate);

        rightRear = new CANSparkMax(12, MotorType.kBrushless);
        rightRear.setInverted(true);
        rightRear.setOpenLoopRampRate(Constants.kRampRate);
        rightRear.setClosedLoopRampRate(Constants.kRampRate);
        rightRear.follow(rightFront);

        leftFront.getEncoder().setPosition(0);
        rightFront.getEncoder().setPosition(0);
        leftRear.getEncoder().setPosition(0);
        rightRear.getEncoder().setPosition(0);

        leftFront.getEncoder().setPositionConversionFactor(Constants.kMetersPerRevolution);
        rightFront.getEncoder().setPositionConversionFactor(Constants.kMetersPerRevolution);
        leftRear.getEncoder().setPositionConversionFactor(Constants.kMetersPerRevolution);
        rightRear.getEncoder().setPositionConversionFactor(Constants.kMetersPerRevolution);

        SmartDashboard.putData("Field", m_field);



        m_odometry = new DifferentialDriveOdometry(
                m_navx.getRotation2d(),
                leftFront.getEncoder().getPosition(),
                rightFront.getEncoder().getPosition());

        // set pose
        Pose2d initialPosition = new Pose2d(2.0, 4.0, new Rotation2d(0));
        m_field.setRobotPose(initialPosition);

        m_odometry.resetPosition(initialPosition.getRotation(), 0, 0, initialPosition);

        setIdleMode(IdleMode.kCoast);

        // PID coefficients
        kP = 0.0002; 
        kI = 0.0000005;
        kD = 0.0; 
        kIz = 0; 
        kFF = 0; 
        kMaxOutput = 1; 
        kMinOutput = -1;

        // set PID coefficients
        m_pidControllerLeft = leftFront.getPIDController();
        m_pidControllerRight = rightFront.getPIDController();

        m_pidControllerLeft.setP(kP);
        m_pidControllerLeft.setI(kI);
        m_pidControllerLeft.setD(kD);
        m_pidControllerLeft.setIZone(kIz);
        m_pidControllerLeft.setFF(kFF);
        m_pidControllerLeft.setOutputRange(kMinOutput, kMaxOutput);

        m_pidControllerRight.setP(kP);
        m_pidControllerRight.setI(kI);
        m_pidControllerRight.setD(kD);
        m_pidControllerRight.setIZone(kIz);
        m_pidControllerRight.setFF(kFF);
        m_pidControllerRight.setOutputRange(kMinOutput, kMaxOutput);


    }

    private DifferentialDrivetrainSim m_driveSim = new DifferentialDrivetrainSim(
            DCMotor.getNEO(2), // 2 NEO motors on each side of the drivetrain.
            7.29, // 7.29:1 gearing reduction.
            7.5, // MOI of 7.5 kg m^2 (from CAD model).
            60.0, // The mass of the robot is 60 kg.
            Units.inchesToMeters(3), // The robot uses 3" radius wheels.
            0.7112, // The track width is 0.7112 meters.

            // The standard deviations for measurement noise:
            // x and y: 0.001 m
            // heading: 0.001 rad
            // l and r velocity: 0.1 m/s
            // l and r position: 0.005 m
            VecBuilder.fill(0.001, 0.001, 0.001, 0.1, 0.1, 0.005, 0.005));

    private AHRS m_navx = new AHRS();

    private DifferentialDriveOdometry m_odometry;

    @Override
    public void periodic() {
        // This method will be called once per scheduler run

        // Cut power if not updated recently
        if (safetyTimer.get() > 0.1) {
            POVdrive(0, 0);
            System.out.println("Hit safety timeout. Cutting power");
        }

        m_odometry.update(
                m_navx.getRotation2d(),
                leftFront.getEncoder().getPosition(),
                rightFront.getEncoder().getPosition());

        m_field.setRobotPose(m_odometry.getPoseMeters());

        SmartDashboard.putNumber("diffDrive/leftEncoder", leftFront.getEncoder().getPosition());
        SmartDashboard.putNumber("diffDrive/rightEncoder", rightFront.getEncoder().getPosition());
        SmartDashboard.putNumber("diffDrive/leftSpeed", leftFront.getEncoder().getVelocity());
        SmartDashboard.putNumber("diffDrive/rightSpeed", rightFront.getEncoder().getVelocity());
    }

    @Override
    public void simulationPeriodic() {
        m_driveSim.setInputs(
                leftFront.get() * RobotController.getInputVoltage(),
                rightFront.get() * RobotController.getInputVoltage());

        m_driveSim.update(0.02);

        leftFront.getEncoder().setPosition(m_driveSim.getLeftPositionMeters());
        // How do I set left velocity?
        rightFront.getEncoder().setPosition(m_driveSim.getRightPositionMeters());
        // How do I set right velocity?

        setNavxAngle(m_driveSim.getHeading().getDegrees());
    }

    private void setNavxAngle(double angle)
    {
        int dev = SimDeviceDataJNI.getSimDeviceHandle("navX-Sensor[0]");
        SimDouble angleObj = new SimDouble(SimDeviceDataJNI.getSimValueHandle(dev, "Yaw"));
        angleObj.set(-angle);
    }

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void POVdrive(double forwardPower, double rotation) {
        double powerMultiplier = 0.75;
        double leftSpeed = 0.0;
        double rightSpeed = 0.0;

        safetyTimer.reset();

        if (m_slowMode == true ||
            (m_liftHeight != null && m_liftHeight.getAsDouble() >= Constants.kLiftSlowModePosition)) {
            powerMultiplier = Constants.kSlowModePowerMultiplier;
        }

        leftSpeed = powerMultiplier * (forwardPower * Constants.kMaxSpeed - rotation * Constants.kMaxSpeed);
        rightSpeed = powerMultiplier * (forwardPower * Constants.kMaxSpeed + rotation * Constants.kMaxSpeed);

        if(Math.abs(leftSpeed) > 10)
        {
            m_pidControllerLeft.setReference(leftSpeed, ControlType.kVelocity);
        }
        else
        {
            // dead zone
            leftFront.set(0);
        }

        if(Math.abs(rightSpeed) > 10)
        {
            m_pidControllerRight.setReference(rightSpeed, ControlType.kVelocity);
        }
        else
        {
            // dead zone
            rightFront.set(0);
        }

        
        SmartDashboard.putNumber("diffDrive/leftSetSpeed", leftSpeed);
        SmartDashboard.putNumber("diffDrive/rightSetSpeed", rightSpeed);
    }

    public double getPitch() {
        return m_navx.getPitch();
    }

    public double getDistance(){
        return leftFront.getEncoder().getPosition();

    }

    public void setIdleMode(IdleMode idleMode) {
        rightRear.setIdleMode(idleMode);
        rightFront.setIdleMode(idleMode);
        leftRear.setIdleMode(idleMode);
        leftFront.setIdleMode(idleMode);
    }

    public void simulationInit() {
        REVPhysicsSim.getInstance().addSparkMax(leftFront, DCMotor.getNEO(1));
        REVPhysicsSim.getInstance().addSparkMax(leftRear, DCMotor.getNEO(1));
        REVPhysicsSim.getInstance().addSparkMax(rightFront, DCMotor.getNEO(1));
        REVPhysicsSim.getInstance().addSparkMax(rightFront, DCMotor.getNEO(1));
    }

    public void setSlowMode(boolean b) {
        m_slowMode = b;
    }

    public boolean getSlowMode()
    {
        return m_slowMode;
    }

    public void setLiftHeightSupplier(DoubleSupplier liftHeight) {
        m_liftHeight= liftHeight;
    }

    public void encoderReset(){

       leftFront.getEncoder().setPosition(0);
       leftRear.getEncoder().setPosition(0); 
       rightFront.getEncoder().setPosition(0); 
       rightRear.getEncoder().setPosition(0); 


    }
}
