package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

/**
 * Created by Muskies on 10/6/2016.
 */
@Autonomous( name = "Beacon Press Red" )
public class AutonomousBeaconPressRed extends LinearOpMode {

    private void claim(){
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
            Robot.drive( -0.25, 0.25, 0 );
        }
        claim();
        while( opModeIsActive() && !Robot.detectsLine() ) {
            Robot.drive( -0.25, 1, 0 );
        }
        claim();
    }
}
