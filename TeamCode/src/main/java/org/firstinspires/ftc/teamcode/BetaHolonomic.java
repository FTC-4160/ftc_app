package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.matrices.VectorF;

/**
 * Created by Steven on 10/16/2016.
 * Using a Rotation Matrix
 * x' = xcosΘ - ysinΘ
 * y' = ysinΘ + ycosΘ
 */
@TeleOp( name = "BetaHolonomic" )
public class BetaHolonomic extends OpMode {
    private static double ANGLE_45 = Math.sqrt( 2.0D ) / 2.0D; //sin and cos are the same so we only need one value

    @Override
    public void init() {
        Robot.init( hardwareMap );
    }

    @Override
    public void loop() {
        double joyx = gamepad1.right_stick_x;
        double joyy = gamepad1.right_stick_y;

        double magnitude = Math.hypot( joyx, joyy ); //get the magnitude of the vector

        double unitx = joyx * ANGLE_45 - joyy * ANGLE_45; //find the new x coordinate on the unit circle
        double unity = joyx * ANGLE_45 + joyy * ANGLE_45; //find the new y coordinate on the unit circle

        double scale = magnitude / Math.max( Math.abs( unitx ), Math.abs( unity ) ); //figure out how to scale for if the magnitude is 1 a motor is always 1

        double motorx = unitx * scale;
        double motory = unity * scale;
        Robot.backLeft.setPower( -motorx );
        Robot.frontRight.setPower( motorx );
        Robot.backLeft.setPower( -motory );
        Robot.backRight.setPower( motory );
    }
}
