package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

/**
 * Created by Steven on 8/23/2016.
 */
@TeleOp( name = "RED" )
public class HolonomicRed extends OpMode implements GamepadEvents.Handler {
    private final GamepadEvents gamepadEvents = new GamepadEvents( this );
    private State state = State.DRIVER_CONTROL;

    private void captureForwards(){
        if( Robot.detectsLine() ){
            Robot.claimBeacon();
        }else{
            Robot.drive( -0.1, 0.4 , 0 );
        }
    }

    private void captureBackwards(){
        if( Robot.detectsLine() ){
            Robot.claimBeacon();
        }else{
            Robot.drive( -0.1, -0.4, 0 );
        }
    }

    @Override
    public void loop(){
        double drivey = Math.pow( -gamepad1.right_stick_y, 3 );
        double drivex = Math.pow( gamepad1.right_stick_x, 3 );
        double turn = gamepad1.right_trigger - gamepad1.left_trigger;

        Robot.intake.setPower( gamepad2.left_stick_y );

        if( Math.abs( drivey ) + Math.abs( drivex ) > 0.1 || Math.abs( turn ) > 0.1 ){
            state = State.DRIVER_CONTROL;
            Robot.resetButtonServos();
        }

        switch( state ){
            case DRIVER_CONTROL: Robot.drive( drivex, drivey, turn ); break;
            case BEACON_CAPTURE_FORWARDS: captureForwards(); break;
            case BEACON_CAPTURE_BACKWARDS: captureBackwards(); break;
            //case BALL_LAUNCH: Robot.launchBall(); break;
        }

        gamepadEvents.handleEvents();
        telemetry.addData( "State", state.toString() );
        telemetry.addData( "drivey", drivey );
        telemetry.addData( "drivex", drivex );
        telemetry.addData( "Turn", turn );
        Robot.addTelemetry( telemetry );
        Robot.beat();
    }

    @Override
    public void init(){
        Robot.init( hardwareMap, Robot.Alliance.RED );
    }

    @Override
    public void init_loop() {
        Robot.addTelemetry( telemetry );
        Robot.beat();
    }

    @Override
    public void onButtonPress(GamepadEvents.Button button) {
        switch( button ) {
            //GAMEPAD1 Buttons
            case GAMEPAD1_LEFT_BUMPER:
                Robot.gyroTarget -= 20;
                break;
            case GAMEPAD1_RIGHT_BUMPER:
                Robot.gyroTarget += 20;
                break;
            case GAMEPAD1_Y:
                Robot.resetButtonServos();
                state = State.BEACON_CAPTURE_FORWARDS;
                break;
            case GAMEPAD1_A:
                Robot.resetButtonServos();
                state = State.BEACON_CAPTURE_BACKWARDS;
                break;
            case GAMEPAD1_START:
                Robot.toggleGyroAssist();
                break;
            //GAMEPAD2 values
            case GAMEPAD2_A:
                Robot.claimBeacon();
                break;
            case GAMEPAD2_Y:
                Robot.resetButtonServos();
                break;
            case GAMEPAD2_B:
                Robot.launchBall();
                break;
        }
    }

    protected enum State{
        DRIVER_CONTROL,
        BEACON_CAPTURE_FORWARDS,
        BEACON_CAPTURE_BACKWARDS,
        //BALL_LAUNCH,
    }
}
