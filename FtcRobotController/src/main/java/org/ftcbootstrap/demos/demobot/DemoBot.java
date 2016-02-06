package org.ftcbootstrap.demos.demobot;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;

import org.ftcbootstrap.components.utils.TelemetryUtil;
import org.ftcbootstrap.RobotConfiguration;

/**
 * DemoBot Saved Configuration
 * <p/>
 * It is assumed that there is a configuration on the phone running the Robot Controller App with the same name as this class and
 * that  configuration is the one that is marked 'activated' on the phone.
 * It is also assumed that the device names in the 'init()' method below are the same  as the devices named for the
 * saved configuration on the phone.
 */
public class DemoBot extends RobotConfiguration {

    //sensors
    private OpticalDistanceSensor ods1;
    private TouchSensor touch1;
    private ColorSensor mrColor1;

    //motors
    private DcMotor motor1;
    private DcMotor motor2;
    private Servo servo1;

    /**
     * Factory method for this class
     *
     * @param hardwareMap
     * @param telemetryUtil
     * @return
     */
    public static DemoBot newConfig(HardwareMap hardwareMap, TelemetryUtil telemetryUtil) {

        DemoBot config = new DemoBot();
        config.init(hardwareMap, telemetryUtil);
        return config;

    }

    /**
     * Assign your class instance variables to the saved device names in the hardware map
     *
     * @param hardwareMap
     * @param telemetryUtil
     */
    @Override
    protected void init(HardwareMap hardwareMap, TelemetryUtil telemetryUtil) {

        setTelemetry(telemetryUtil);

        ods1 = (OpticalDistanceSensor) getHardwareOn("ods1", hardwareMap.opticalDistanceSensor);
        touch1 = (TouchSensor) getHardwareOn("touch1", hardwareMap.touchSensor);
        mrColor1 = (ColorSensor) getHardwareOn("mrColor1", hardwareMap.colorSensor);
        // turn the LED on in the beginning, just so user will know that the sensor is active.
        mrColor1.enableLed(true);

        servo1 = (Servo) getHardwareOn("servo1", hardwareMap.servo);
        motor1 = (DcMotor) getHardwareOn("motor1", hardwareMap.dcMotor);
        motor2 = (DcMotor) getHardwareOn("motor2", hardwareMap.dcMotor);

        motor2.setDirection(DcMotor.Direction.REVERSE);


    }

    /**
     * @return OpticalDistanceSensor
     */
    public OpticalDistanceSensor getOds1() {
        return ods1;
    }

    /**
     * @return TouchSensor
     */
    public TouchSensor getTouch1() {
        return touch1;
    }

    /**
     * @return Servo
     */
    public Servo getServo1() {
        return servo1;
    }

    public ColorSensor getMrColor1() {
        return mrColor1;
    }

    /**
     * @return DcMotor
     */
    public DcMotor getMotor1() {
        return motor1;
    }

    /**
     * @return DcMotor
     */
    public DcMotor getMotor2() {
        return motor2;
    }


}
