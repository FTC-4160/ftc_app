package org.firstinspires.ftc.teamcode.Tasks;

import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by Steven on 9/10/2016.
 */
public class DriveTask extends Task {
    private final DcMotor motor;
    private final double power;
    private final int target;

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
        return this.motor.getCurrentPosition() < target;
    }

    @Override
    public void stop(){
        this.motor.setPower( 0 );
    }
}
