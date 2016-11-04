package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Created by Muskies on 10/6/2016.
 */
@Autonomous( name = "RED BEACON CAPTURE" )
public class AutonomousBeaconPressRed extends LinearOpMode {
     protected void claim(){
        Robot.claimBeaconRed();
        sleep( 500 );
        Robot.resetButtonServos();
        sleep( 50 );
    }

    protected void move(double x, double y ){
        Robot.drive( x, y, 0 );
    }

    @Override
    public void runOpMode() {
        Robot.init( hardwareMap );
        waitForStart();
        ElapsedTime time = new ElapsedTime();
        //move to the wall
        while( opModeIsActive() && time.seconds() < 4.5 ){
            move( -0.25, 0.25 );
        }
        //disable gyro assistance
        Robot.toggleGyroAssist();
        //drive to the line
        while( opModeIsActive() && !Robot.detectsLine() ) {
            move( -0.1, 0.4 );
        }
        claim();
        time.reset();
        //move off the line
        while( opModeIsActive() && time.seconds() < 2 ){
            move( -0.1, 0.4 );
        }
        //drive to the second line
        while( opModeIsActive() && !Robot.detectsLine() ) {
            move( -0.1, 0.4 );
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
