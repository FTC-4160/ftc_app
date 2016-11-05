package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

/**
 * Created by Steven on 11/3/2016.
 */

@Autonomous( name = "BLUE BEACON CAPTURE BETA" )
public class AutonomouseBeaconPressBlueBeta extends AutonomousBeaconPressRed {
    @Override
    protected void claim(){
        Robot.claimBeaconBlue();
        sleep( 500 );
        Robot.resetButtonServos();
        sleep( 50 );
    }

    @Override
    protected void move( double x, double y ){
        Robot.drive( x, -y, 0 );
    }
}
