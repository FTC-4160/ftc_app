package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

/**
 * Created by Steven on 10/15/2016.
 */
@TeleOp( name = "HolonomicBlue" )
public class HolonomicBlue extends HolonomicRed {
    @Override
    public void loop(){
        double drivey = -gamepad1.left_stick_y;
        double drivex = gamepad1.left_stick_x;
        double turn = gamepad1.right_trigger - gamepad1.left_trigger;

        switch( state ){
            case DRIVER_CONTROL: Robot.drive( drivey, drivex, turn ); break;
            case BEACON_CAPTURE_FORWARDS:
                if( Robot.detectsLine() ){
                    Robot.stop();
                    Robot.claimBeaconBlue();
                }else{
                    Robot.drive( 0.25, 1 , 0 );
                }
                break;
            case BEACON_CAPTURE_BACKWARDS:
                if( Robot.detectsLine() ){
                    Robot.stop();
                    Robot.claimBeaconBlue();
                }else{
                    Robot.drive( 0.25, -1, 0 );
                }
                break;
        }

        telemetry.addData( "State", state.toString() );
        telemetry.addData( "drivey", drivey );
        telemetry.addData( "drivex", drivex );
        telemetry.addData( "Turn", turn );
    }
}
