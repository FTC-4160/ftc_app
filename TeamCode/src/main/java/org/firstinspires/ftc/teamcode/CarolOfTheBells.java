package org.firstinspires.ftc.teamcode;

import android.media.MediaPlayer;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

/**
 * Created by Steven on 12/8/2016.
 */
@TeleOp( name = "Carol of the bells" )
public class CarolOfTheBells extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        waitForStart();
        MediaPlayer mediaPlayer = MediaPlayer.create( hardwareMap.appContext, R.raw.carolofthebells );
        mediaPlayer.start();
        while( opModeIsActive() && mediaPlayer.isPlaying() ){
            sleep( 500 );
        }
    }
}
