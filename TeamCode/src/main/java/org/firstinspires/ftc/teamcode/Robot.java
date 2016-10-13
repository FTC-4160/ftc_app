package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.modernrobotics.*;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by Steven on 9/10/2016.
 */
//We need to build a wall
class Robot {
    //This class stores our hardware
    static DcMotor frontLeft, frontRight, backLeft, backRight;
    static ModernRoboticsI2cGyro gyro;
    static ModernRoboticsI2cColorSensor colorRight, colorLeft;
    static ModernRoboticsAnalogOpticalDistanceSensor leftLineFollow, rightLineFollow;
    static ModernRoboticsDigitalTouchSensor touchSensor;
    static final int NEVEREST_TICKS_PER_SECOND = 2240;
    static int gyroTarget = 0;

    public static void init( HardwareMap hardwareMap ){
        frontLeft = hardwareMap.dcMotor.get( "frontLeft" );
        frontRight = hardwareMap.dcMotor.get( "frontRight" );
        backLeft = hardwareMap.dcMotor.get( "backLeft" );
        backRight = hardwareMap.dcMotor.get( "backRight" );
        frontLeft.setMode( DcMotor.RunMode.RUN_USING_ENCODER );
        frontRight.setMode( DcMotor.RunMode.RUN_USING_ENCODER );
        backLeft.setMode( DcMotor.RunMode.RUN_USING_ENCODER );
        backRight.setMode( DcMotor.RunMode.RUN_USING_ENCODER );
        frontLeft.setMaxSpeed( NEVEREST_TICKS_PER_SECOND );
        frontRight.setMaxSpeed( NEVEREST_TICKS_PER_SECOND );
        backLeft.setMaxSpeed( NEVEREST_TICKS_PER_SECOND );
        backRight.setMaxSpeed( NEVEREST_TICKS_PER_SECOND );
        frontLeft.setDirection( DcMotor.Direction.REVERSE );
        frontRight.setDirection( DcMotor.Direction.REVERSE );
        backLeft.setDirection( DcMotor.Direction.REVERSE );
        backRight.setDirection( DcMotor.Direction.REVERSE );
        gyro = (ModernRoboticsI2cGyro)hardwareMap.gyroSensor.get( "gyro" );
        colorRight = (ModernRoboticsI2cColorSensor)hardwareMap.colorSensor.get( "rcolor" );
        colorLeft = (ModernRoboticsI2cColorSensor)hardwareMap.colorSensor.get( "lcolor" );
        leftLineFollow = (ModernRoboticsAnalogOpticalDistanceSensor)hardwareMap.opticalDistanceSensor.get( "lods" );
        rightLineFollow = (ModernRoboticsAnalogOpticalDistanceSensor)hardwareMap.opticalDistanceSensor.get( "rods" );
        touchSensor = (ModernRoboticsDigitalTouchSensor)hardwareMap.touchSensor.get( "touchSensor" );
        gyro.calibrate();
        leftLineFollow.enableLed( true );
        rightLineFollow.enableLed( true );
    }

    public static void drive( double drivex, double drivey, double turn ) {
        if (Math.abs(turn) < 0.1) {
            turn += ((Robot.gyro.getIntegratedZValue() - gyroTarget) * 0.01);
        } else {
            gyroTarget = Robot.gyro.getIntegratedZValue();
        }
        double rightFront = zeroRangeClip( drivey + drivex - turn );
        double leftFront = zeroRangeClip( -drivey + drivex - turn );
        double rightBack = zeroRangeClip( drivey - drivex - turn );
        double leftBack = zeroRangeClip( -drivey - drivex - turn );

        frontLeft.setPower(leftFront);
        frontRight.setPower(rightFront);
        backLeft.setPower(leftBack);
        backRight.setPower(rightBack);
    }

    private static double zeroRangeClip( double input ){
        if( Math.abs( input ) < 0.2 ){
            return 0.0;
        }
        return Range.clip( input, -1, 1 );
    }
}
