package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Created by Muskies on 10/6/2016.
 */
@Autonomous( name = "Beacon Press Red" )
public class AutonomousBeaconPressRed extends LinearOpMode {
    protected int direction = 1;
    protected void claim(){
        Robot.stop();
        sleep( 250 );
        Robot.claimBeaconRed();
        sleep( 500 );
        Robot.resetButtonServos();
        sleep( 250 );
    }

    @Override
    public void runOpMode() {
        Robot.init( hardwareMap );
        waitForStart();
        ElapsedTime time = new ElapsedTime();
        while( opModeIsActive() && time.seconds() < 4 ){
            Robot.drive( -0.25, 0.25 * direction, 0 );
        }
        while( opModeIsActive() && !Robot.detectsLine() ) {
            Robot.drive( -0.25, direction, 0 );
        }
        claim();
        while( opModeIsActive() && !Robot.detectsLine() ) {
            Robot.drive( -0.25, direction, 0 );
        }
        claim();
    }
    @Override
    public void handleLoop(){
        telemetry.addData( "Detects line", Robot.detectsLine() );
        updateTelemetry( telemetry );
    }
}
