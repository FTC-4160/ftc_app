package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Created by Muskies on 10/6/2016.
 */
@SuppressWarnings("WeakerAccess")
@Autonomous( name = "RED BEACON CAPTURE" )
public class AutonomousBeaconPressRed extends LinearOpMode {
     private void claim(){
        Robot.claimBeacon();
        sleep( 500 );
        Robot.resetButtonServos();
        sleep( 50 );
    }

    void move(double x, double y){
        Robot.drive( x, y, 0 );
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
        while( opModeIsActive() && time.seconds() < 4.5 ){
            move( -0.5, 0.5 );
        }
        //disable gyro assistance
        Robot.toggleGyroAssist();
        //drive to the line
        while( opModeIsActive() && !Robot.detectsLine() ) {
            move( -0.1, 0.8 );
        }
        claim();
        time.reset();
        //move off the line
        while( opModeIsActive() && time.seconds() < 2 ){
            move( -0.1, 0.8 );
        }
        //drive to the second line
        while( opModeIsActive() && !Robot.detectsLine() ) {
            move( -0.1, 0.8 );
        }
        claim();
        Robot.stop();
    }
    @Override
    public void handleLoop(){
        Robot.addTelemetry( telemetry );
        updateTelemetry( telemetry );
        super.handleLoop();
    }
}
