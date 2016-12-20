package org.firstinspires.ftc.teamcode;

import android.support.annotation.FloatRange;
import android.support.annotation.Nullable;

import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by Steven on 12/3/2016.
 */
public class LEDStrip12v {
    private @Nullable DcMotor red, green, blue;

    public LEDStrip12v( @Nullable DcMotor red, @Nullable DcMotor green, @Nullable DcMotor blue ){
        if( red != null ) {
            red.setMode( DcMotor.RunMode.RUN_WITHOUT_ENCODER );
        }
        if( blue != null ){
            blue.setMode( DcMotor.RunMode.RUN_WITHOUT_ENCODER );
        }
        if( green != null ){
            green.setMode( DcMotor.RunMode.RUN_WITHOUT_ENCODER );
        }
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    /**
     * This method sets the red, green, and blue values of the strip and supports color mixing
     * If you initialized the strip without a red, green, or blue motor port it will ignore the value given
     * @param r the amount of red to be shown (0.0 minimum, 1.0 maximum)
     * @param g the amount of green to be shown (0.0 minimum, 1.0 maximum)
     * @param b the amount of blue to be shown (0.0 minimum, 1.0 maximum)
     */
    public void setColorRGB(@FloatRange( from = 0.0, to = 1.0 ) float r, @FloatRange( from = 0.0, to = 1.0 ) float g, @FloatRange( from = 0.0, to = 1.0 ) float b ){
        if (red != null) {
            red.setPower( r );
        }
        if (blue != null) {
            blue.setPower( b );
        }
        if (green != null) {
            green.setPower( g );
        }
    }
}
