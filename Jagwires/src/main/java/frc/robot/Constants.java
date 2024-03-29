// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
  public static class OperatorConstants {
    public static final int kDriverControllerPort = 0;
  }
  
  public static final int kJoystickPort = 0;
  public static final int kJoystickPort2 = 1;


  public static final double kOffBalanceThresholdDegrees = 10.0;

  public static final double kBalancePowerMultiplier = 5.0;
  public static final double kBalanceMaxPower = 0.05;

  public static final double kMetersPerRevolution = 0.0391761383859716;

  public static final double kLiftMaxPosition = 66.0;

  public static final double kLiftSlowModePosition = 20.0;

  public static final double kSlowModePowerMultiplier = 0.25;

  public static final double kMaxSpeed = 5691.0;  

  public static final double kMinWristPosition = -14.0;

  public static final double kRampRate = 0.5;

  public static final double kHighLift = 64.0;

  public static final double kMidLift = 50.0;
 
  public static final double kLowLift = 0.0;


}
