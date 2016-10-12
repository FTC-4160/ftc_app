package org.firstinspires.ftc.teamcode;

import android.support.annotation.NonNull;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Gamepad;

import java.util.HashMap;

/**
 * Created by Steven on 10/9/2016.
 */
public class GamepadEvents {
    private HashMap<Button, Boolean> buttonstate = new HashMap<>();
    private OpMode eventOpMode;
    public <EventOpMode extends OpMode & Handler> GamepadEvents( EventOpMode handler ){
        this.eventOpMode = handler;
        for(Button b: Button.values()){
            buttonstate.put( b, false );
        }
    }

    //This makes the whole thing run.  Add a call to this method in loop()
    public void handleEvents(){
        for (Button b: Button.values()) {
            boolean current = b.fromGamepads( eventOpMode.gamepad1, eventOpMode.gamepad2 );
            if( current != buttonstate.get( b ) ){
                buttonstate.put(b, current );
                if( current ){
                    ((Handler)eventOpMode).onButtonPress( b );
                }else{
                    ((Handler)eventOpMode).onButtonRelease( b );
                }
            }
        }
    }

    protected interface Handler {
        void onButtonPress(Button button);
        void onButtonRelease(Button button);
    }
    // A "Button" is anything on a gamepad that registers as a boolean value (true / false)
    protected enum Button {
        GAMEPAD1_A,
        GAMEPAD2_A,
        GAMEPAD1_B,
        GAMEPAD2_B,
        GAMEPAD1_X,
        GAMEPAD2_X,
        GAMEPAD1_Y,
        GAMEPAD2_Y,
        GAMEPAD1_LEFT_BUMPER,
        GAMEPAD1_RIGHT_BUMPER,
        GAMEPAD2_LEFT_BUMPER,
        GAMEPAD2_RIGHT_BUMPER,
        GAMEPAD1_START,
        GAMEPAD2_START,
        GAMEPAD1_BACK;
        @NonNull Boolean fromGamepads(Gamepad gamepad1, Gamepad gamepad2 ){
            switch( this ){
                case GAMEPAD1_A: return gamepad1.a;
                case GAMEPAD2_A: return gamepad2.a;
                case GAMEPAD1_B: return gamepad1.b;
                case GAMEPAD2_B: return gamepad2.b;
                case GAMEPAD1_X: return gamepad1.x;
                case GAMEPAD2_X: return gamepad2.x;
                case GAMEPAD1_Y: return gamepad1.y;
                case GAMEPAD2_Y: return gamepad2.y;
                case GAMEPAD1_LEFT_BUMPER: return gamepad1.left_bumper;
                case GAMEPAD1_RIGHT_BUMPER: return gamepad1.right_bumper;
                case GAMEPAD2_LEFT_BUMPER: return gamepad2.left_bumper;
                case GAMEPAD2_RIGHT_BUMPER: return gamepad2.right_bumper;
                case GAMEPAD1_BACK: return gamepad1.back;
                case GAMEPAD1_START: return gamepad1.start;
                case GAMEPAD2_START: return gamepad2.start;
            }
            return false;
        }
    }
}
/*
Example usage of class:
@TeleOp( name = "ExampleEventOp" )
public class ExampleEventOp extends OpMode implements GamepadEvents.Handler { //notice it implements the handler
    private GamepadEvents gamepadEvents = new GamepadEvents( this ); //create a new instance of this class
    @Override
    public void init() {

    }

    @Override
    public void loop() {
        //do other things here
        gamepadEvents.handleEvents(); //call the handle events method (this will call onButtonPress / onButtonRelease when necessary)
    }

    @Override
    public void onButtonPress( Button button ){
        //do things when a Button is pressed (ei state changes to true)
    }

    @Override
    public void onButtonRelease( Button button ){
        //do things when a Button is release (ei state changes to false)
    }
}
*/