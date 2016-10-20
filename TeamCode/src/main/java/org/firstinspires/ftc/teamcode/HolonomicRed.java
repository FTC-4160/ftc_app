package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

/**
 * Created by Steven on 8/23/2016.
 */
@TeleOp( name = "HolonomicRed" )
public class HolonomicRed extends OpMode implements GamepadEvents.Handler {
    final GamepadEvents gamepadEvents = new GamepadEvents( this );
    State state = State.DRIVER_CONTROL;

    @Override
    public void loop(){
        double drivey = -gamepad1.right_stick_y;
        double drivex = gamepad1.right_stick_x;
        double turn = gamepad1.right_trigger - gamepad1.left_trigger;

        switch( state ){
            case DRIVER_CONTROL: Robot.drive( drivex, drivey, turn ); break;
            case BEACON_CAPTURE_FORWARDS:
                if( Robot.detectsLine() ){
                    Robot.stop();
                    Robot.claimBeaconRed();
                }else{
                    Robot.drive( 0.25, 1 , 0 );
                }
                break;
            case BEACON_CAPTURE_BACKWARDS:
                if( Robot.detectsLine() ){
                    Robot.stop();
                    Robot.claimBeaconRed();
                }else{
                    Robot.drive( 0.25, -1, 0 );
                }
                break;
        }

        gamepadEvents.handleEvents();
        telemetry.addData( "State", state.toString() );
        telemetry.addData( "drivey", drivey );
        telemetry.addData( "drivex", drivex );
        telemetry.addData( "Turn", turn );
        telemetry.addData( "ODS Right", Robot.rightLineDetector.getLightDetected() );
        telemetry.addData( "ODS Left", Robot.leftLineDetector.getLightDetected() );
    }

    @Override
    public void init(){
        Robot.init( hardwareMap );
    }

    @Override
    public void init_loop() {
        telemetry.addData( "Gyro Calibration", Robot.gyro.isCalibrating() ? "Calibrating..." : "Complete" );
        gamepadEvents.handleEvents();
    }

    @Override
    public void onButtonPress(GamepadEvents.Button button) {
        switch( button ){
            case GAMEPAD1_LEFT_BUMPER:
                Robot.gyroTarget -= 20;
                break;
            case GAMEPAD1_RIGHT_BUMPER:
                Robot.gyroTarget += 20;
                break;
            case GAMEPAD1_Y:
                state = State.BEACON_CAPTURE_FORWARDS;
                break;
            case GAMEPAD1_A:
                state = State.BEACON_CAPTURE_BACKWARDS;
                break;
            case GAMEPAD1_X:
                Robot.resetButtonServos();
                state = State.DRIVER_CONTROL;
        }
    }

    protected enum State{
        DRIVER_CONTROL,
        BEACON_CAPTURE_FORWARDS,
        BEACON_CAPTURE_BACKWARDS,
    }
}
