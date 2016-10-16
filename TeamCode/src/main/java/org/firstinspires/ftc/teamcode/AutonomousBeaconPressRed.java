package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

/**
 * Created by Muskies on 10/6/2016.
 */
@SuppressWarnings("ALL")
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
        while( opModeIsActive() && !Robot.detectsLine() ) {
            Robot.drive( -0.25, 0.25 * direction, 0 );
        }
        claim();
        while( opModeIsActive() && !Robot.detectsLine() ) {
            Robot.drive( -0.25, direction, 0 );
        }
        claim();
    }
}
