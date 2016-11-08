package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

/**
 * Created by Steven on 11/6/2016.
 */
@TeleOp( name="BETA DRIVE" )
public class HolonomicBeta extends OpMode {
    @Override
    public void init() {
        Robot.init( hardwareMap, Robot.Alliance.RED );
    }

    @Override
    public void init_loop(){
        Robot.addTelemetry( telemetry );
    }

    @Override
    public void loop() {
        double drivex = Math.pow( gamepad1.right_stick_x, 3 );
        double drivey = Math.pow( -gamepad1.right_stick_y, 3 );
        double turn = gamepad1.right_trigger - gamepad1.left_trigger;
        drive( drivex, drivey, turn  );
        Robot.addTelemetry( telemetry );
    }

    private double ANGLE_45 = Math.sqrt( 2 ) / 2;

    public void drive( double x, double y, double turn ){
        double magnitude = Math.hypot( x, y ); //get the magnitude of the vector

        double unitx = x * ANGLE_45 - y * -ANGLE_45; //find the new x coordinate on the unit circle
        double unity = x * -ANGLE_45 + y * ANGLE_45; //find the new y coordinate on the unit circle

        double scale = Math.abs( magnitude / Math.max( unitx, unity ) ); //figure out how to scale for if the magnitude is 1 a motor is always 1

        double motorx = Robot.zeroRangeClip( unitx * scale );
        double motory = Robot.zeroRangeClip( unity * scale );
        Robot.frontLeft.setPower( -motorx );
        Robot.backRight.setPower( motorx );
        Robot.frontRight.setPower( motory );
        Robot.backLeft.setPower( -motory );
    }
}
