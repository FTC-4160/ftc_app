package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

/**
 * Created by Steven on 11/3/2016.
 */

@SuppressWarnings("WeakerAccess")
@Autonomous( name = "BLUE BEACON CAPTURE" )
public class AutoBeaconBlue extends AutoBeaconRed {
    @Override
    protected void initRobot(){
        Robot.init( hardwareMap, Robot.Alliance.BLUE );
    }

    @Override
    protected void move( double x, double y ){
        Robot.drive( x, -y, 0 );
    }
}
