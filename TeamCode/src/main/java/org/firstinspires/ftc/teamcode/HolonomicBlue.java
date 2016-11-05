package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

/**
 * Created by Steven on 10/15/2016.
 */
@TeleOp( name = "BLUE" )
public class HolonomicBlue extends HolonomicRed {
    @Override
    public void init(){
        Robot.init( hardwareMap, Robot.Alliance.BLUE );
    }
}
