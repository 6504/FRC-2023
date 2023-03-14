// RobotBuilder Version: 5.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.

// ROBOTBUILDER TYPE: Command.

package frc.robot.commands;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.DriveTrain;

import java.util.function.DoubleSupplier;

import com.revrobotics.CANSparkMax.IdleMode;

// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS

/**
 *
 */
public class Balance extends CommandBase {

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_DECLARATIONS
 
    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_DECLARATIONS

    private DriveTrain m_DriveTrain;

    private Timer m_timer = new Timer();
    private double m_startPosition = 0;

    public Balance(DriveTrain driveTrain) {
        m_DriveTrain = driveTrain;
        addRequirements(m_DriveTrain);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        // Get starting position for safety
        m_startPosition = m_DriveTrain.getDistance();

        // Start timer
        m_timer.reset();
        m_timer.start();
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        double pitchAngleDegrees = m_DriveTrain.getPitch();
        
        
        if (Math.abs(pitchAngleDegrees) > Constants.kOffBalanceThresholdDegrees)
        { 
            double pitchAngleRadians = pitchAngleDegrees * (Math.PI / 180.0);
            double xAxisRate = Math.sin(pitchAngleRadians) * -Constants.kBalancePowerMultiplier;
            final double positivePower = Math.min(Math.abs(xAxisRate), Constants.kBalanceMaxPower);
            final double power = Math.copySign(positivePower, xAxisRate);
            m_DriveTrain.POVdrive(power, 0);
        }
        else
        {
            m_DriveTrain.POVdrive(0, 0);
        }
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        m_DriveTrain.POVdrive(0, 0);
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {

        // End command if we drove too far
        if (Math.abs(m_DriveTrain.getDistance() - m_startPosition) > 2.0) {
            System.out.println("Balance Drove too far: " + m_DriveTrain.getDistance());
            return true;
        }

        // End command if the angle is too big
        if (Math.abs(m_DriveTrain.getPitch()) > 45.0) {
            System.out.println("Angle too big: " + m_DriveTrain.getPitch());
            return true;
        }

        // End command if we took too long
        if (m_timer.hasElapsed(10.0)) {
            System.out.println("Time too long: " + m_timer.get());
            return true;
        }

        return false;
    }

    @Override
    public boolean runsWhenDisabled() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DISABLED
        return false;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DISABLED
    }
}
