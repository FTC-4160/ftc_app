package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by Muskies on 9/20/2016.
 */

@TeleOp( name = "TestOp" )
public class TestOp extends OpMode {
    DcMotor rightFront, leftFront, rightBack, leftBack;
    @Override
    public void init() {
        rightFront = hardwareMap.dcMotor.get("rf");
        leftFront = hardwareMap.dcMotor.get( "lf" );
        rightBack = hardwareMap.dcMotor.get( "rb" );
        leftBack = hardwareMap.dcMotor.get( "lb" );
    }

    @Override
    public void loop() {
        rightFront.setPower( -gamepad1.right_stick_y );
        rightBack.setPower( -gamepad1.right_stick_y );

        leftFront.setPower( gamepad1.left_stick_y );
        leftBack.setPower( gamepad1.left_stick_y );
    }
}
