package org.firstinspires.ftc.teamcode.Tasks;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Steven on 9/10/2016.
 */
public class MultiTask extends Task {
    private ArrayList<Task> tasks;
    private Mode mode;
    public MultiTask(ArrayList<Task> tasks, Mode mode){
        this.mode = mode;
        this.tasks = tasks;
    }

    @Override
    public void init(){
        for (Task t : tasks) {
            t.init();
        }
    }

    @Override
    public boolean loop(){
        Iterator<Task> iter = tasks.listIterator();
        while( iter.hasNext() ){
            Task t = iter.next();
            boolean complete = t.loop();
            if( complete && mode == Mode.WaitForAny ){
                stopAllTasks();
                return true;
            }else if( complete && mode == Mode.WaitForAll ){
                t.stop();
                tasks.remove( t );
            }
        }
        return tasks.size() == 0;
    }

    @Override
    public void stop(){
        /*
        this does nothing because stopping is done in the loop() method
         */
    }

    private void stopAllTasks(){
        for (Task t : tasks) {
            t.stop();
        }
    }

    public enum Mode {
        WaitForAll,
        WaitForAny
    }
}
