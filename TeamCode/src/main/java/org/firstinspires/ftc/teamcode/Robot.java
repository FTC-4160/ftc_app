package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.modernrobotics.*;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Created by Steven on 9/10/2016.
 */
//We need to build a wall
public class Robot {
    //This class stores our hardware
    static DcMotor frontLeft, frontRight, backLeft, backRight;
    static ModernRoboticsI2cGyro gyro;
    static ModernRoboticsI2cColorSensor colorRight, colorLeft;
    static ModernRoboticsAnalogOpticalDistanceSensor leftLineFollow, rightLineFollow;
    static ModernRoboticsDigitalTouchSensor touchSensor;
    static final int NEVEREST_TICKS_PER_SECOND = 2240;

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

    public static void move( double leftPower, double rightPower ) {
        frontLeft.setPower(leftPower);
        frontRight.setPower(leftPower);
        backLeft.setPower(rightPower);
        frontRight.setPower(rightPower);
    }
}
