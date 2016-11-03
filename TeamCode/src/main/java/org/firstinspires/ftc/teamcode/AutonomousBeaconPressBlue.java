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
        Robot.claimBeaconBlue();
        sleep(500);
        Robot.resetButtonServos();
        sleep(50);
    }

    @Override
    public void runOpMode() {
        Robot.init(hardwareMap);
        waitForStart();
        ElapsedTime time = new ElapsedTime();
        //move to the wall
        while( opModeIsActive() && time.seconds() < 4.5 ) {
            Robot.drive(-0.25, -0.25, 0);
        }
        //disable gyro assistance
        Robot.toggleGyroAssist();
        //drive to the line
        while (opModeIsActive() && !Robot.detectsLine()) {
            Robot.drive(-0.1, -0.4, 0);
        }
        claim();
        time.reset();
        //move off the line
        while (opModeIsActive() && time.seconds() < 2) {
            Robot.drive(-0.1, -0.4, 0);
        }
        //drive to the second line
        while (opModeIsActive() && !Robot.detectsLine()) {
            Robot.drive(-0.1, -0.4, 0);
        }
        claim();
    }

    @Override
    public void handleLoop() {
        Robot.addTelemetry(telemetry);
        updateTelemetry(telemetry);
        super.handleLoop();
    }
}