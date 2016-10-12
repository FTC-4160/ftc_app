package org.firstinspires.ftc.teamcode.Tasks;

import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by Steven on 9/10/2016.
 */
public class DriveTask extends Task {
    private DcMotor motor;
    private double power;
    private int target;

    public DriveTask( DcMotor motor, double power, int target ){
        this.motor = motor;
        this.power = power;
        this.target = target;
    }

    @Override
    public void init(){
        this.motor.setPower( this.power );
    }

    @Override
    public boolean loop(){
        if( this.motor.getCurrentPosition() < target ){
            return true;
        }
        return false;
    }

    @Override
    public void stop(){
        this.motor.setPower( 0 );
    }
}
