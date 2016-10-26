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
    protected boolean robotIsStopped = false;

    protected void captureForwards(){
        if( Robot.detectsLine() ){
            robotIsStopped = true;
            Robot.claimBeaconRed();
        }else{
            Robot.drive( -0.1, 0.4 , 0 );
        }
        if( robotIsStopped ){
            Robot.stop();
        }
    }

    protected void captureBackwards(){
        if( Robot.detectsLine() ){
            robotIsStopped = true;
            Robot.claimBeaconRed();
        }else{
            Robot.drive( -0.1, -0.4, 0 );
        }
        if( robotIsStopped ){
            Robot.stop();
        }
    }

    @Override
    public void loop(){
        double drivey = -gamepad1.right_stick_y;
        double drivex = gamepad1.right_stick_x;
        double turn = gamepad1.right_trigger - gamepad1.left_trigger;

        if( Math.abs( drivey ) + Math.abs( drivex ) > 0.1 || Math.abs( turn ) > 0.1 ){
            state = State.DRIVER_CONTROL;
            Robot.resetButtonServos();
            robotIsStopped = false;
        }

        switch( state ){
            case DRIVER_CONTROL: Robot.drive( drivex, drivey, turn ); break;
            case BEACON_CAPTURE_FORWARDS: captureForwards(); break;
            case BEACON_CAPTURE_BACKWARDS: captureBackwards(); break;
        }

        gamepadEvents.handleEvents();
        telemetry.addData( "State", state.toString() );
        telemetry.addData( "drivey", drivey );
        telemetry.addData( "drivex", drivex );
        telemetry.addData( "Turn", turn );
        Robot.addTelemetry( telemetry );
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
        switch( button ) {
            case GAMEPAD1_LEFT_BUMPER:
                Robot.gyroTarget -= 20;
                break;
            case GAMEPAD1_RIGHT_BUMPER:
                Robot.gyroTarget += 20;
                break;
            case GAMEPAD1_Y:
                Robot.resetButtonServos();
                state = State.BEACON_CAPTURE_FORWARDS;
                robotIsStopped = false;
                break;
            case GAMEPAD1_A:
                Robot.resetButtonServos();
                state = State.BEACON_CAPTURE_BACKWARDS;
                robotIsStopped = false;
                break;
            case GAMEPAD1_X:
                Robot.resetButtonServos();
                robotIsStopped = false;
                state = State.DRIVER_CONTROL;
                break;
            case GAMEPAD1_START:
                Robot.toggleGyroAssist();
                break;
        }
    }

    protected enum State{
        DRIVER_CONTROL,
        BEACON_CAPTURE_FORWARDS,
        BEACON_CAPTURE_BACKWARDS,
    }
}
