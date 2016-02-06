package org.ftcbootstrap.demos.beginner;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;

import org.ftcbootstrap.RobotConfiguration;
import org.ftcbootstrap.components.utils.TelemetryUtil;

/**
 * MyFirstBot Saved Configuration
 * <p/>
 * It is assumed that there is a configuration on the phone running the Robot Controller App with the same name as this class and
 * that  configuration is the one that is marked 'activated' on the phone.
 * It is also assumed that the device names in the 'init()' method below are the same  as the devices named for the
 * saved configuration on the phone.
 */
public class MyFirstBot extends RobotConfiguration {

    //sensors
    private TouchSensor touch;

    //motors
    private DcMotor motor1;
    private DcMotor motor2;
    private Servo servo;

    /**
     * Factory method for this class
     *
     * @param hardwareMap
     * @param telemetryUtil
     * @return
     */
    public static MyFirstBot newConfig(HardwareMap hardwareMap, TelemetryUtil telemetryUtil) {

        MyFirstBot config = new MyFirstBot();
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

        touch = (TouchSensor) getHardwareOn("touch", hardwareMap.touchSensor);
        servo = (Servo) getHardwareOn("servo", hardwareMap.servo);
        motor1 = (DcMotor) getHardwareOn("motor1", hardwareMap.dcMotor);
        motor2 = (DcMotor) getHardwareOn("motor2", hardwareMap.dcMotor);
        motor2.setDirection(DcMotor.Direction.REVERSE);


    }


    /**
     * @return TouchSensor
     */
    public TouchSensor getTouch() {
        return touch;
    }

    /**
     * @return Servo
     */
    public Servo getServo() {
        return servo;
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
