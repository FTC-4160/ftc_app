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
        while( opModeIsActive() && Robot.getUltrasonicLevel() > 10 && !Robot.detectsLine() ){
            move( -0.3, 0.4 );
        }
        //drive to the line
        while( opModeIsActive() && !Robot.detectsLine() ) {
            move( 0, 0.3 );
            sleep( 10 );
        }
        //drive to the beacon
        while( opModeIsActive() && Robot.getUltrasonicLevel() > 5 ){
            move( -0.3, 0.0 );
        }

        Robot.stop();
        claim();

        while( opModeIsActive() && Robot.getUltrasonicLevel() < 10 ){
            move( 0.3, 0.0 );
        }

        time.reset();
        //move off the line
        while( opModeIsActive() && time.seconds() < 2 ){
            move( 0, 0.5 );
        }
        //drive to the second line
        while( opModeIsActive() && !Robot.detectsLine() ) {
            move( 0, 0.5 );
        }
        Robot.stop();
        claim();
        move( 0.5, 0 );
        sleep( 250 );
        Robot.stop();
    }
    @Override
    public void handleLoop(){
        Robot.addTelemetry( telemetry );
        Robot.sayInitData();
        updateTelemetry( telemetry );
        super.handleLoop();
    }
}
