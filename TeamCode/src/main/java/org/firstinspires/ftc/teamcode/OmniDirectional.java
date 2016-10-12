package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by Steven on 8/23/2016.
 */
@TeleOp( name = "OmniDirectional" )
public class OmniDirectional extends OpMode {

    @Override
    public void loop(){
        double turn = gamepad1.left_trigger - gamepad1.right_trigger;
        double drivey = -gamepad1.right_stick_y;
        double drivex = -gamepad1.right_stick_x;
        double rightFront = Range.clip( drivey  - drivex + turn, -1, 1 );
        double leftFront =  Range.clip( -drivey - drivex + turn, -1, 1 );
        double rightBack =  Range.clip( -drivey + drivex + turn, -1, 1 );
        double leftBack =   Range.clip( drivey  + drivex + turn, -1, 1 );

        Robot.frontLeft.setPower( leftFront );
        Robot.frontRight.setPower( rightFront );
        Robot.backLeft.setPower( leftBack );
        Robot.backRight.setPower( rightBack );

        telemetry.addData( "drivey", drivey );
        telemetry.addData( "drivex", drivex );
        telemetry.addData( "Turn", turn );
    }
    @Override
    public void init(){
        Robot.init( hardwareMap );
    }
}
