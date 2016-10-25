package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.modernrobotics.*;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.I2cAddr;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by Steven on 9/10/2016.
 */
//We need to build a wall
class Robot {
    //This class stores our hardware
    static DcMotor frontLeft, frontRight, backLeft, backRight;
    static Servo leftButton, rightButton;
    static ModernRoboticsI2cGyro gyro;
    static ModernRoboticsI2cColorSensor colorRight, colorLeft;
    static ModernRoboticsAnalogOpticalDistanceSensor leftLineDetector, rightLineDetector;
    static ModernRoboticsDigitalTouchSensor touchSensor;
    static final int NEVEREST_TICKS_PER_SECOND = 2240;
    static final double LIGHT_THRESHOLD = 0.075; //correction
    static int gyroTarget = 0;
    private static boolean gyroOff;

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
        leftButton = hardwareMap.servo.get( "lservo" );
        rightButton = hardwareMap.servo.get( "rservo" );
        resetButtonServos();
        gyro = (ModernRoboticsI2cGyro)hardwareMap.gyroSensor.get( "gyro" );
        colorRight = (ModernRoboticsI2cColorSensor)hardwareMap.colorSensor.get( "rcolor" );
        colorLeft = (ModernRoboticsI2cColorSensor)hardwareMap.colorSensor.get( "lcolor" );
        colorRight.setI2cAddress( I2cAddr.create7bit(0x1e) );
        colorLeft.setI2cAddress( I2cAddr.create7bit(0x26) );
        colorRight.enableLed( false );
        colorLeft.enableLed( false );
        leftLineDetector = (ModernRoboticsAnalogOpticalDistanceSensor)hardwareMap.opticalDistanceSensor.get( "lods" );
        rightLineDetector = (ModernRoboticsAnalogOpticalDistanceSensor)hardwareMap.opticalDistanceSensor.get( "rods" );
        touchSensor = (ModernRoboticsDigitalTouchSensor)hardwareMap.touchSensor.get( "touchSensor" );
        gyro.calibrate();
        leftLineDetector.enableLed( true );
        leftLineDetector.enableLed( true );
    }

    public static void drive( double drivex, double drivey, double turn ) {
        if (Math.abs(turn) < 0.1) {
            if( gyroOff ){
                gyroTarget = gyro.getIntegratedZValue();
            }
            turn -= ((gyro.getIntegratedZValue() - gyroTarget) * 0.01);
            gyroOff = false;
        } else {
            gyroOff = true;
        }
        double rightFront = zeroRangeClip( drivey - drivex - turn );
        double leftFront = zeroRangeClip( -drivey - drivex - turn );
        double rightBack = zeroRangeClip( drivey + drivex - turn );
        double leftBack = zeroRangeClip( -drivey + drivex - turn );

        frontLeft.setPower(leftFront);
        frontRight.setPower(rightFront);
        backLeft.setPower(leftBack);
        backRight.setPower(rightBack);
    }

    public static void stop(){
        frontLeft.setPower( 0 );
        frontRight.setPower( 0 );
        backLeft.setPower( 0 );
        backRight.setPower( 0 );
    }

    public static boolean detectsLine(){
        return rightLineDetector.getLightDetected() > LIGHT_THRESHOLD || leftLineDetector.getLightDetected() > LIGHT_THRESHOLD;
    }

    public static void claimBeaconRed(){
        (colorLeft.red() > colorRight.red() ? leftButton : rightButton).setPosition( 0.5 );
    }

    public static void claimBeaconBlue(){
        (colorLeft.blue() > colorRight.blue() ? leftButton : rightButton).setPosition( 0.5 );
    }

    public static void resetButtonServos(){
        leftButton.setPosition( 0 );
        rightButton.setPosition( 1.0 );
    }

    private static double zeroRangeClip( double input ){
        if( Math.abs( input ) < 0.1 ){
            return 0.0;
        }
        return Range.clip( input, -1, 1 );
    }
}
