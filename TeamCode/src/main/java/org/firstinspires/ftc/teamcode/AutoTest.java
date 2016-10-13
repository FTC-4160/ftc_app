package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.Tasks.*;

import java.util.ArrayList;

/**
 * Created by Steven on 9/10/2016.
 */
@Autonomous( name = "AutoTest" )
public class AutoTest extends OpMode {
    private final ArrayList<Task> tasks = new ArrayList<>();
    private int maxTasks;
    @Override
    public void init() {
        Robot.init( hardwareMap );
        //add tasks here
        maxTasks = tasks.size();
        tasks.get( 1 ).init();
    }

    @Override
    public void loop() {
        if( tasks.size() > 0 && tasks.get( 1 ).loop() ){
            tasks.remove( 1 );
            if( tasks.size() > 0 ){
                tasks.get( 1 ).init();
            }
        }
        telemetry.addData( "Current Task", maxTasks - tasks.size() + 1 );
    }
}
