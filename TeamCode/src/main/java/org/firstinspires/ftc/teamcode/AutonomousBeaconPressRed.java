package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

/**
 * Created by Muskies on 10/6/2016.
 */
@Autonomous( name = "Beacon Press Red" )
public class AutonomousBeaconPressRed extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Robot.init( hardwareMap );
        waitForStart();
        Robot.move( 0.75, 0.75 );
        wait( 1000 );
    }
}
