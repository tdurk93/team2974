/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.templates;


import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SimpleRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.DriverStationLCD;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.Jaguar;




/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the SimpleRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class RobotTemplate extends SimpleRobot
{
    
    RobotDrive drive;
    Joystick leftStick;
    Joystick rightStick;
    DriverStationLCD DriveStationOut ;
    DriverStation DriveStation;
    Jaguar BeltRoller,ShootingRoller;
    
    /**
     * 
     */
    public RobotTemplate()
    {
        drive = new RobotDrive(1,2);//constructs and object of the drivetrain, with motors on channel 1 and 2.
        leftStick = new Joystick(1);//constructs a new object that controls the joystick in usb 1 on the driver station.
        rightStick = new Joystick(2);//constructs a new object that controls the joystick in usb 2 on the driver station.
        BeltRoller = new Jaguar(3);
        ShootingRoller = new Jaguar(4);
        DriveStationOut = DriverStationLCD.getInstance();
        drive.setInvertedMotor(RobotDrive.MotorType.kRearRight, true);

    }
    /**
     * This function is called once each time the robot enters autonomous mode.
     */
        public void autonomous()
        {
            for (int counter=0; counter<2; counter++)
            {
                BeltRoller.set(-1.0);
                drive.drive(0.5,0.5);
                Timer.delay(1);
                drive.drive(-1.0,0.75);
                Timer.delay(1);
                drive.drive(0.0,0.0);
            }
        }

    /**
     * This function is called once each time the robot enters operator control.
     */
    public void operatorControl()
    {
        DriveStationOut.println(DriverStationLCD.Line.kUser5, 1, "joystick 1 xvalue="+leftStick.getX());
        DriveStationOut.println(DriverStationLCD.Line.kUser6, 1,"Num of joysticks="+DriverStation.kJoystickPorts );
        boolean goBelt = false;
        boolean pressed = false;
        while (true) // loop forever
        {
            if (rightStick.getTop())
            {
                if(!pressed)
                {
                    if (goBelt)
                    {
                        BeltRoller.set(0.0);
                        goBelt=!goBelt;
                    }
                    else
                    {
                        BeltRoller.set(-1.0);
                        goBelt=!goBelt;
                    }
                    pressed=true;
                }
            }
            else
            {
                pressed=false;
            }
            DriveStationOut.updateLCD();
            drive.tankDrive(leftStick,rightStick );// drive w/joysticks
            if (rightStick.getTrigger())
            {
                ShootingRoller.set(-1.0);
            }
            else
            {
                ShootingRoller.set(0.0);
            }
            

            
            Timer.delay(0.005);
        }
    }
}
