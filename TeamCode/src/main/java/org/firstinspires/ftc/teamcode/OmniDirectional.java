package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by Steven on 8/23/2016.
 */
@TeleOp( name = "OmniDirectional" )
public class OmniDirectional extends OpMode {
    private DcMotor fl, fr, bl, br;

    @Override
    public void loop(){
        double turn = gamepad1.left_trigger - gamepad1.right_trigger;
        double rightFront = Range.clip( -gamepad1.right_stick_y - gamepad1.left_stick_x + turn, -1, 1 );
        double leftFront = Range.clip( gamepad1.right_stick_y - gamepad1.left_stick_x + turn, -1, 1 );
        double rightBack = Range.clip( gamepad1.right_stick_y + gamepad1.left_stick_x + turn, -1, 1 );
        double leftBack = Range.clip( -gamepad1.right_stick_y + gamepad2.left_stick_x + turn, -1, 1 );

        fl.setPower( leftFront );
        fr.setPower( rightFront );
        bl.setPower( leftBack );
        br.setPower( rightBack );

        telemetry.addData( "Turn", turn );
    }
    @Override
    public void init(){
        fl = hardwareMap.dcMotor.get( "fl" );
        fr = hardwareMap.dcMotor.get( "fr" );
        bl = hardwareMap.dcMotor.get( "bl" );
        br = hardwareMap.dcMotor.get( "br" );
    }
}
