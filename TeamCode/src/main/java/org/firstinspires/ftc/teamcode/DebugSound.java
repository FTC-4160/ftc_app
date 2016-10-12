package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;

/**
 * Created by Steven on 8/21/2016.
 */

@TeleOp( name = "Debug Sound" )
public class DebugSound extends OpMode {
    private TonePlayer tonePlayer;
    private int toneToPlay = 0;
    private Gamepad oldgamepad1, oldgamepad2;
    @Override
    public void loop(){
        if( gamepad1.start && !oldgamepad1.start ){
            tonePlayer.playTone( toneToPlay ); //plays a tone equivalent to (toneToPlay + 1) * 100 Hz for 1s
        }

        if( gamepad1.y && !oldgamepad1.y && toneToPlay < 9 ) {
            toneToPlay++; //increase the Hz by 100 (up to 1000)
        }else if( gamepad1.b && !oldgamepad1.b && toneToPlay > 0 ){
            toneToPlay--; //decrease the Hz by 100 (down to 100)
        }

        telemetry.addData( "Tone To Play", toneToPlay );
        try {
            oldgamepad1.copy(gamepad1);
            oldgamepad2.copy(gamepad2);
        } catch( Exception e ){
            telemetry.addData( "Gamepad Status", "Failure" );
        } finally {
            telemetry.addData( "Gamepad Status", "Success" );
        }
    }
    @Override
    public void init(){
        oldgamepad1 = new Gamepad();
        oldgamepad2 = new Gamepad();
        tonePlayer = new TonePlayer( hardwareMap );
    }
}
