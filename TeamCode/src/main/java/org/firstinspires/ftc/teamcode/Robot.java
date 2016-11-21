package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.hitechnic.HiTechnicNxtUltrasonicSensor;
import com.qualcomm.hardware.modernrobotics.ModernRoboticsAnalogOpticalDistanceSensor;
import com.qualcomm.hardware.modernrobotics.ModernRoboticsDigitalTouchSensor;
import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cColorSensor;
import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cRangeSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.I2cAddr;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * Created by Steven on 9/10/2016.
 */
@SuppressWarnings("WeakerAccess")
class Robot {
    //This class stores our hardware
    static DcMotor frontLeft, frontRight, backLeft, backRight, launcher, intake;
    static Servo leftButton, rightButton, feeder, heartbeat;
    static ModernRoboticsI2cGyro gyro;
    static ModernRoboticsI2cColorSensor colorRight, colorLeft;
    static ModernRoboticsAnalogOpticalDistanceSensor leftLineDetector, rightLineDetector;
    static ModernRoboticsDigitalTouchSensor touchSensor;
    static final int NEVEREST_TICKS_PER_SECOND = 2240;
    static final double LIGHT_THRESHOLD = 0.075;
    static int gyroTarget = 0;
    private static boolean gyroAssistEnabled = true;
    private static ElapsedTime time;
    private static double launchTime = 0;
    private static boolean isInitialized = false;
    private static Alliance alliance;
    private static final double ANGLE_45 = Math.sqrt( 2 ) / 2;

    public static void addTelemetry( Telemetry t ){
        if( !isInitialized ){
            return;
        }
        t.addData( "Left Front Motor Power", frontLeft.getPower() );
        t.addData( "Left Front Motor Position", frontLeft.getCurrentPosition() );
        t.addData( "Right Front Motor Power", frontRight.getPower() );
        t.addData( "Right Front Motor Position", frontRight.getCurrentPosition() );
        t.addData( "Left Back Motor Power", backLeft.getPower() );
        t.addData( "Left Back Motor Position", backLeft.getCurrentPosition() );
        t.addData( "Right Back Motor Power", backRight.getPower() );
        t.addData( "Right Back Motor Position", backRight.getCurrentPosition() );
        t.addData( "Intake Power", intake.getPower() );
        t.addData( "Launcher Power", launcher.getPower() );

        t.addData( "Servo Button Left", leftButton.getPosition() );
        t.addData( "Servo Button Right", rightButton.getPosition() );

        t.addData( "Gyro Sensor", gyro.getIntegratedZValue() );
        t.addData( "Gyro Calibration", gyro.isCalibrating() ? "Calibrating" : "Complete" );
        t.addData( "More Red", colorLeft.red() > colorRight.red() ? "Left" : "Right" );
        t.addData( "More Blue", colorLeft.blue() > colorRight.blue() ? "Left" : "Right" );
        t.addData( "ODS Left", leftLineDetector.getLightDetected() );
        t.addData( "ODS Right", rightLineDetector.getLightDetected() );
        t.addData( "ODS Detects Line", detectsLine() );

        t.addData( "Gyro Target", gyroTarget );
        t.addData( "Gyro Enabled", gyroAssistEnabled );
        t.update();
    }

    public static void toggleGyroAssist(){
        gyroAssistEnabled = !gyroAssistEnabled;
    }

    public static void init( HardwareMap hardwareMap, Alliance alliance ){
        Robot.alliance = alliance;
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
        intake = hardwareMap.dcMotor.get( "intake" );
        intake.setZeroPowerBehavior( DcMotor.ZeroPowerBehavior.FLOAT );
        intake.setMode( DcMotor.RunMode.RUN_WITHOUT_ENCODER );
        intake.setDirection( DcMotor.Direction.REVERSE );
        launcher = hardwareMap.dcMotor.get( "launcher" );
        launcher.setZeroPowerBehavior( DcMotor.ZeroPowerBehavior.BRAKE );
        launcher.setMaxSpeed( NEVEREST_TICKS_PER_SECOND );
        launcher.setMode( DcMotor.RunMode.RUN_TO_POSITION );
        launcher.setPower( 1.0 );
        launcher.setTargetPosition( launcher.getCurrentPosition() );
        time = new ElapsedTime( ElapsedTime.Resolution.MILLISECONDS );
        Robot.isInitialized = true;
        heartbeat = hardwareMap.servo.get( "heartbeat" );
    }

    public static void driveFieldOriented( double drivex, double drivey, double turn ){
        double target = Math.toRadians( (gyroTarget + 45)%360 );

        double magnitude = Math.hypot( drivex, drivey );
        //get the rotated point
        double unitx = drivex * Math.cos( target ) + drivey * Math.sin( target );
        double unity = -drivex * Math.sin( target ) + drivey * Math.cos( target );
        //find the scale factor which will allow one motor to run at magnitude
        //this way we can get full power at angles, ex: 45 degrees would be (1, 1) not (sqrt(2)/2, sqrt(2)/2)
        double scale = 1.0;
        if( Math.abs( drivex ) + Math.abs( drivey ) > 0.0 ) {
            scale = Math.abs(magnitude / Math.max(Math.abs(unitx), Math.abs(unity)));
        }

        //clip & round the final values, subtracting the turn factor
        double motorx = unitx * scale;
        double motory = unity * scale ;

        frontLeft.setPower( zeroRangeClip( -motorx - turn ) );
        backRight.setPower( zeroRangeClip( motorx - turn ) );
        frontRight.setPower( zeroRangeClip( motory - turn ) );
        backLeft.setPower( zeroRangeClip( -motory - turn ) );
    }

    public static void drive( double drivex, double drivey, double turn ) {

        //gyro assistance
        /*
        if( gyroAssistEnabled && Math.abs( turn ) < 0.1 ){
            turn -= gyro.getIntegratedZValue() - gyroTarget;
        }else{
            gyroTarget = gyro.getIntegratedZValue();
        }
        */

        //get the magnitude of the vector
        //equivalent to sqrt( x^2 + y^2 )
        double magnitude = Math.hypot( drivex, drivey );
        //get the rotated point
        double unitx = drivex * ANGLE_45 + drivey * ANGLE_45;
        double unity = -drivex * ANGLE_45 + drivey * ANGLE_45;
        //find the scale factor which will allow one motor to run at magnitude
        //this way we can get full power at angles, ex: 45 degrees would be (1, 1) not (sqrt(2)/2, sqrt(2)/2)
        double scale = 1.0;

        if( Math.abs( drivex ) + Math.abs( drivey ) > 0.1 ) {
            scale = Math.abs(magnitude / Math.max(Math.abs(unitx), Math.abs(unity)));
        }

        //clip & round the final values, subtracting the turn factor
        double motorx = unitx * scale;
        double motory = unity * scale ;

        frontLeft.setPower( zeroRangeClip( -motorx - turn ) );
        backRight.setPower( zeroRangeClip( motorx - turn ) );
        frontRight.setPower( zeroRangeClip( motory - turn ) );
        backLeft.setPower( zeroRangeClip( -motory - turn ) );
    }

    public static void launchBall(){
        Robot.stop();
        launcher.setTargetPosition( launcher.getCurrentPosition() + 1140 );
    }

    public static void stop(){
        frontRight.setPower( 0 );
        frontLeft.setPower( 0 );
        backLeft.setPower( 0 );
        backRight.setPower( 0 );
    }

    public static boolean detectsLine(){
        return rightLineDetector.getLightDetected() > LIGHT_THRESHOLD || leftLineDetector.getLightDetected() > LIGHT_THRESHOLD;
    }

    public static void claimBeacon(){
        Robot.drive( -0.5, 0, 0 );
        if( alliance == Alliance.RED && colorLeft.red() > colorRight.red() || alliance == Alliance.BLUE && colorLeft.blue() > colorRight.blue() ){
            leftButton.setPosition( 1.0 );
        }else{
            rightButton.setPosition( 0.0 );
        }
    }

    public static void resetButtonServos(){
        leftButton.setPosition( 0.0 );
        rightButton.setPosition( 1.0 );
    }

    public static double zeroRangeClip( double input ){
        if( Math.abs( input ) < 0.1 ){
            return 0.0;
        }
        //round to the nearest 20th
        return (int)(Range.clip( input, -1, 1 ) * 10) * 0.1;
    }

    public static void beat(){
        heartbeat.setPosition( toNearestTenth(time.time() - Math.floor( time.time() )) );
    }

    private static double toNearestTenth( double input ){
        return 0.1 * (int)(input * 10);
    }

    enum Alliance { BLUE, RED }
}
