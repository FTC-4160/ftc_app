package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

/**
 * Created by Steven on 10/15/2016.
 */
@Autonomous( name = "Beacon Press Blue" )
@Disabled
public class AutonomousBeaconPressBlue extends AutonomousBeaconPressRed {
    protected int direction = -1;

    @Override
    protected void claim(){
        Robot.claimBeaconBlue();
        sleep( 500 );
        Robot.resetButtonServos();
        sleep( 50 );
    }
}
