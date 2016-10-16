package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

/**
 * Created by Steven on 10/16/2016.
 */
@TeleOp( name = "BetaHolonomic" )
public class BetaHolonomic extends OpMode {
    @Override
    public void init() {
        Robot.init( hardwareMap );
    }

    @Override
    public void loop() {
        double joyx = gamepad1.right_stick_x;
        double joyy = gamepad1.right_stick_y;

        double magnitude = Math.hypot( joyx, joyy ); //get the magnitude of the vector
        double angle = Math.atan2( joyy, joyx ) + Math.PI / 4; //rotate by 45 degrees

        double unitx = Math.cos( angle ); //find the new x coordinate on the unit circle
        double unity = Math.sin( angle ); //find the new y coordinate on the unit circle

        double scale = Math.abs( magnitude / Math.max( unitx, unity ) ); //find which one is the largest; at magnitude 1.0 we want to have either x or y be 1

        double motorx = unitx * scale;
        double motory = unity * scale;
        Robot.backLeft.setPower( -motorx );
        Robot.frontRight.setPower( motorx );
        Robot.backLeft.setPower( -motory );
        Robot.backRight.setPower( motory );
    }
}
