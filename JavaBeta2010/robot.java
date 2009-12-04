/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.Accelerometer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SimpleRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.DriverStationLCD;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.PWM;
import edu.wpi.first.wpilibj.Victor;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the SimpleRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class RobotTemplate extends SimpleRobot {

    RobotDrive drive;
    GenericHID leftStick;
    GenericHID rightStick;
    DriverStationLCD DriveStationOut;
    DriverStation DriveStation;
    Jaguar BeltRoller, ShootingRoller;
    Accelerometer accel;
    private static Gyro g;

    /**
     * 
     */
    public RobotTemplate() {
        drive = new RobotDrive(1, 2);//constructs and object of the drivetrain, with motors on channel 1 and 2.
        leftStick = new Joystick(1);//constructs a new object that controls the joystick in usb 1 on the driver station.
        rightStick = new Joystick(2);//constructs a new object that controls the joystick in usb 2 on the driver station.
        BeltRoller = new Jaguar(3);
        ShootingRoller = new Jaguar(4);
        drive.setInvertedMotor(RobotDrive.MotorType.kRearRight, true);
        accel = new Accelerometer(1);
        g = new Gyro(2);
        getWatchdog().setEnabled(false);

    }

    /**
     * This function is called once each time the robot enters autonomous mode.
     */
    public void autonomous() {
        for (int counter = 0; counter < 2; counter++) {
            BeltRoller.set(-1.0);
            drive.drive(0.5, 0.5);
            Timer.delay(1);
            drive.drive(-1.0, 0.75);
            Timer.delay(1);
            drive.drive(0.0, 0.0);
        }
        BeltRoller.set(0);
    }

    /**
     * This function is called once each time the robot enters operator control.
     */
    public void operatorControl() {

        
            boolean goBelt = false;
            boolean pressed = false;
            boolean AccelCheck = true;
            boolean GyroCheck = true;
            while(true)
            {
            
               System.out.println("Gyro Y" + accel.getAcceleration());
                // drive w/joysticks
                drive.tankDrive(leftStick, rightStick);
               //if else conditions act as event listiner for top button
                if (rightStick.getTop()) {
                    if (!pressed) {
                        if (goBelt) {
                            BeltRoller.set(0.0);
                        } else {
                            BeltRoller.set(-1.0);
                        }
                        pressed = true;
                    }
                } else {
                    if (pressed) {
                        goBelt = !goBelt;
                    }
                    pressed = false;
                }



                //another group of condition statements that turn top roller on and off
                if (rightStick.getTrigger()) {
                    ShootingRoller.set(-1.0);
                } else {
                    ShootingRoller.set(0.0);
                }

                if (accel.getAcceleration() > .1) {
                    AccelCheck = false;
                }
                if (g.getAngle() > 30) {
                    GyroCheck = false;
                }

                //delay for .005 seconds
                Timer.delay(0.005);
            }
        
    }
}
