package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

/**
 * Created by Muskies on 9/20/2016.
 */

@SuppressWarnings("WeakerAccess")
@TeleOp( name = "TestOp" )
@Disabled
public class TestOp extends OpMode {
    @Override
    public void init() {
        Robot.init(hardwareMap, Robot.Alliance.RED);
    }

    @Override
    public void loop() {
        Robot.frontRight.setPower( -gamepad1.right_stick_y );
        Robot.backRight.setPower( -gamepad1.right_stick_y );

        Robot.frontLeft.setPower( gamepad1.left_stick_y );
        Robot.backLeft.setPower( gamepad1.left_stick_y );
    }
}
