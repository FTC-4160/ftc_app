package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Created by Muskies on 12/1/2016.
 */

@Autonomous( name = "Red Beacon with Ultrasonic" )
public class AutoBeaconRedUltrasonic extends LinearOpMode {
    private void claim(){
        Robot.claimBeacon();
        sleep( 2500 );
        Robot.resetButtonServos();
        sleep( 1000 );
    }

    void move(double x, double y){
        Robot.drive_straight_gyro( x, y );
    }

    void initRobot(){
        Robot.init( hardwareMap, Robot.Alliance.RED );
    }

    @Override
    public void runOpMode() {
        initRobot();
        waitForStart();
        ElapsedTime time = new ElapsedTime();
        //move to the wall
        while( opModeIsActive() && time.seconds() < 4.5 && Robot.getDistanceFromTarget() >= 0 ){
            move( -0.5, 0.5 );
        }
        //drive to the line
        while( opModeIsActive() && !Robot.detectsLine() ) {
            double walldist = Robot.getDistanceFromTarget();
            move( walldist * 0.1, 0.5 );
            sleep( 50 );
        }
        Robot.stop();
        claim();
        time.reset();
        /*
        //move off the line
        while( opModeIsActive() && time.seconds() < 2 ){
            move( (Robot.ULTRASONIC_TARGET - Robot.ultrasonicSensor.getUltrasonicLevel()) * 0.1, 0.5 );
        }
        //drive to the second line
        while( opModeIsActive() && !Robot.detectsLine() ) {
            move( (Robot.ULTRASONIC_TARGET - Robot.ultrasonicSensor.getUltrasonicLevel()) * 0.1, 0.5 );
        }
        Robot.stop();
        claim();
        move( 0.5, 0 );
        sleep( 250 );
        Robot.stop();
        */
    }
    @Override
    public void handleLoop(){
        Robot.addTelemetry( telemetry );
        Robot.sayInitData();
        updateTelemetry( telemetry );
        super.handleLoop();
    }
}
