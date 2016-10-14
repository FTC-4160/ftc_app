package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

/**
 * Created by Steven on 8/23/2016.
 */
@TeleOp( name = "OmniDirectional" )
public class OmniDirectional extends OpMode implements GamepadEvents.Handler {
    @Override
    public void loop(){
        double drivey = -gamepad1.left_stick_y;
        double drivex = gamepad1.left_stick_x;
        double turn = gamepad1.right_trigger - gamepad1.left_trigger;

        Robot.drive( drivey, drivex, turn );

        telemetry.addData( "drivey", drivey );
        telemetry.addData( "drivex", drivex );
        telemetry.addData( "Turn", turn );
    }

    @Override
    public void init(){
        Robot.init( hardwareMap );
    }

    @Override
    public void init_loop() {
        telemetry.addData( "Gyro Calibration", Robot.gyro.isCalibrating() ? "Calibrating..." : "Complete" );
    }

    @Override
    public void onButtonPress(GamepadEvents.Button button) {
        switch( button ){
            case GAMEPAD1_LEFT_BUMPER:
                Robot.gyroTarget -= 10;
                break;
            case GAMEPAD1_RIGHT_BUMPER:
                Robot.gyroTarget += 10;
                break;
        }
    }

    @Override
    public void onButtonRelease(GamepadEvents.Button button) {

    }
}
