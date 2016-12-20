package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;

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

        Robot.intake.setPower( Range.clip( gamepad2.left_stick_y, 0, 1.0 ) + Range.clip( gamepad2.right_stick_y, -1.0, 0 ) );

        if( state != State.FIELD_CONTROL && (Math.abs( drivey ) + Math.abs( drivex ) > 0.1 || Math.abs( turn ) > 0.1 ) ){
            state = State.DRIVER_CONTROL;
            Robot.resetButtonServos();
        }

        switch( state ){
            case DRIVER_CONTROL: Robot.drive( drivex, drivey, turn ); break;
            case BEACON_CAPTURE_FORWARDS: captureForwards(); break;
            case BEACON_CAPTURE_BACKWARDS: captureBackwards(); break;
            case FIELD_CONTROL: Robot.driveFieldOriented( drivex, drivey, turn * 0.5 );
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
        Robot.init( hardwareMap, Robot.Alliance.RED );
    }

    @Override
    public void init_loop() {
        Robot.addTelemetry( telemetry );
        Robot.sayInitData();
    }

    @Override
    public void onButtonPress(GamepadEvents.Button button) {
        switch( button ) {
            //GAMEPAD1 Buttons
            case GAMEPAD1_X:
                Robot.setGyroTarget();
                Robot.say( "Gyro Target Set" );
            case GAMEPAD1_Y:
                Robot.resetButtonServos();
                state = State.BEACON_CAPTURE_FORWARDS;
                Robot.say( "Mode is now " + state.toString() );
                break;
            case GAMEPAD1_A:
                Robot.resetButtonServos();
                state = State.BEACON_CAPTURE_BACKWARDS;
                Robot.say( "Mode is now " + state.toString() );
                break;
            case GAMEPAD1_START:
                if( this.state == State.FIELD_CONTROL ){
                    state = State.DRIVER_CONTROL;
                }else if( state == State.DRIVER_CONTROL ){
                    state = State.FIELD_CONTROL;
                }
                Robot.say( "Mode is now " + state.toString() );
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
            case GAMEPAD2_X:
                Robot.claimBeacons();
        }
    }

    protected enum State{
        DRIVER_CONTROL,
        FIELD_CONTROL,
        BEACON_CAPTURE_FORWARDS,
        BEACON_CAPTURE_BACKWARDS,
    }
}
