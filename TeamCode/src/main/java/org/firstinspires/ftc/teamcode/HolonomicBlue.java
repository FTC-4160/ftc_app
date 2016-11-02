package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

/**
 * Created by Steven on 10/15/2016.
 */
@TeleOp( name = "HolonomicBlue" )
public class HolonomicBlue extends HolonomicRed {
    @Override
    protected void captureForwards(){
        if( Robot.detectsLine() ){
            Robot.claimBeaconBlue();
        }else{
            Robot.drive( -0.1, 0.4 , 0 );
        }
    }
    @Override
    protected void captureBackwards(){
        if( Robot.detectsLine() ){
            Robot.claimBeaconBlue();
        }else{
            Robot.drive( -0.1, -0.4, 0 );
        }
    }
}
