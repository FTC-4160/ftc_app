package org.firstinspires.ftc.teamcode;

import android.content.Context;
import android.support.annotation.IntRange;

import com.qualcomm.ftccommon.SoundPlayer;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Created by Steven on 8/21/2016.
 */
public class TonePlayer {
    private SoundPlayer soundPlayer = SoundPlayer.getInstance();
    private Context context;
    public int[] tones = new int[10];

    public TonePlayer( HardwareMap hardwareMap ){
        context = hardwareMap.appContext;
        tones[ 0 ] = R.raw.tone_100;
        tones[ 1 ] = R.raw.tone_200;
        tones[ 2 ] = R.raw.tone_300;
        tones[ 3 ] = R.raw.tone_400;
        tones[ 4 ] = R.raw.tone_500;
        tones[ 5 ] = R.raw.tone_600;
        tones[ 6 ] = R.raw.tone_700;
        tones[ 7 ] = R.raw.tone_800;
        tones[ 8 ] = R.raw.tone_900;
        tones[ 9 ] = R.raw.tone_1000;
    }

    public void playDownUpDown( @IntRange( from=0, to=8 ) int tone ){
        playTone( tone );
        playTone( tone + 1 );
        playTone( tone );
    }

    public void playTone( @IntRange( from=0, to=9) int tone ){
        soundPlayer.play( context, tone );
    }
}
