package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsAnalogOpticalDistanceSensor;
import com.qualcomm.hardware.modernrobotics.ModernRoboticsDigitalTouchSensor;
import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cColorSensor;
import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

/**
 * Created by Steven on 10/1/2016.
 */
@TeleOp( name="SensorTest" )
public class SensorTest extends OpMode {
    ModernRoboticsI2cColorSensor colorSensor;
    ModernRoboticsI2cGyro gyroSensor;
    ModernRoboticsAnalogOpticalDistanceSensor distanceSensor;
    ModernRoboticsDigitalTouchSensor touchSensor;

    @Override
    public void init() {
        Common.init( hardwareMap );
        colorSensor = Common.colorLeft;
        gyroSensor = Common.gyro;
        distanceSensor = Common.leftLineFollow;
        touchSensor = Common.touchSensor;
    }

    @Override
    public void init_loop(){
        telemetry.addData( "Calibrated", !gyroSensor.isCalibrating() );
    }

    @Override
    public void loop() {
        telemetry.addData( "Color Sensor Red", colorSensor.red() );
        telemetry.addData( "Color Sensor Blue", colorSensor.blue() );
        telemetry.addData( "Color Sensor Alpha", colorSensor.alpha() );
        telemetry.addData( "Distance Sensor Light Detected", distanceSensor.getLightDetected() );
        telemetry.addData( "Gyro Sensor Heading", gyroSensor.getHeading() );
        telemetry.addData( "Gyro Sensor IntegratedZValue", gyroSensor.getIntegratedZValue() );
        telemetry.addData( "Touch Sensor isPressed", touchSensor.isPressed() );
        telemetry.addData( "Touch Sensor Value", touchSensor.getValue() );
    }
}