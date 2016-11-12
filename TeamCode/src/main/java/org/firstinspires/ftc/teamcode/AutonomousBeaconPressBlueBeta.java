package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

/**
 * Created by Steven on 11/3/2016.
 */

@Autonomous( name = "BLUE BEACON CAPTURE BETA" )
public class AutonomousBeaconPressBlueBeta extends AutonomousBeaconPressRed {
    @Override
    protected void initRobot(){
        Robot.init( hardwareMap, Robot.Alliance.BLUE );
    }

    @Override
    protected void move( double x, double y ){
        Robot.drive( x, -y, 0 );
    }
}
