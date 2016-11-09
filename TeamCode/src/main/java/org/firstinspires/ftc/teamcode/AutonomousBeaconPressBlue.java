package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Created by Steven on 10/15/2016.
 */
@Autonomous( name = "BLUE BEACON CAPTURE" )
public class AutonomousBeaconPressBlue extends LinearOpMode {
    private void claim() {
        Robot.claimBeacon();
        sleep(1000);
        Robot.resetButtonServos();
        sleep(1000);
    }

    protected void initRobot(){
        Robot.init( hardwareMap, Robot.Alliance.BLUE );
    }

    @Override
    public void runOpMode() {
        initRobot();
        Robot.init(hardwareMap, Robot.Alliance.BLUE );
        waitForStart();
        ElapsedTime time = new ElapsedTime();
        //move to the wall
        while( opModeIsActive() && time.seconds() < 4.5 ) {
            Robot.drive(-0.5, -0.5, 0);
        }
        //disable gyro assistance
        Robot.toggleGyroAssist();
        //drive to the line
        while (opModeIsActive() && !Robot.detectsLine()) {
            Robot.drive(-0.1, -0.8, 0);
        }
        claim();
        time.reset();
        //move off the line
        while (opModeIsActive() && time.seconds() < 2) {
            Robot.drive(-0.1, -0.8, 0);
        }
        //drive to the second line
        while (opModeIsActive() && !Robot.detectsLine()) {
            Robot.drive(-0.1, -0.8, 0);
        }
        claim();
        Robot.stop();
    }

    @Override
    public void handleLoop() {
        Robot.addTelemetry(telemetry);
        super.handleLoop();
    }
}