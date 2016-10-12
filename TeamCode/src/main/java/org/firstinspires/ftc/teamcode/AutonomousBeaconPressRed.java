package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

/**
 * Created by Muskies on 10/6/2016.
 */
@Autonomous( name = "Beacon Press Red" )
public class AutonomousBeaconPressRed extends LinearOpMode {
    @Override
    public void runOpMode() {
        Robot.init( hardwareMap );
        waitForStart();
        Robot.move( 0.75, 0.75 );
        sleep( 1000 );
    }
}
