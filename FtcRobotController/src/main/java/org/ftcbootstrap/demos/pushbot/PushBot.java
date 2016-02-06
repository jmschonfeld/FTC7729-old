package org.ftcbootstrap.demos.pushbot;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IrSeekerSensor;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;

import org.ftcbootstrap.RobotConfiguration;
import org.ftcbootstrap.components.utils.TelemetryUtil;

/**
 * PushBot Saved Configuration
 * <p/>
 * It is assumed that there is a configuration on the phone running the Robot Controller App with the same name as this class and
 * that  configuration is the one that is marked 'activated' on the phone.
 * <p/>
 * It is also assumed that the device names in the 'init()' method below are the same  as the devices named for the
 * saved configuration on the phone.
 */
public class PushBot extends RobotConfiguration {

    //motors
    private DcMotor leftDrive;
    private DcMotor rightDrive;
    private DcMotor leftArm;

    private Servo leftHand;
    private Servo rightHand;

    private OpticalDistanceSensor ods;
    private TouchSensor touchSensor;
    private ColorSensor mrColor;

    /**
     * Factory method for this class
     *
     * @param hardwareMap
     * @param telemetryUtil
     * @return
     */
    public static PushBot newConfig(HardwareMap hardwareMap, TelemetryUtil telemetryUtil) {

        PushBot config = new PushBot();
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

        leftDrive = (DcMotor) getHardwareOn("left_drive", hardwareMap.dcMotor);
        rightDrive = (DcMotor) getHardwareOn("right_drive", hardwareMap.dcMotor);
        rightDrive.setDirection(DcMotor.Direction.REVERSE);

        leftArm = (DcMotor) getHardwareOn("left_arm", hardwareMap.dcMotor);

        leftHand = (Servo) getHardwareOn("left_hand", hardwareMap.servo);
        rightHand = (Servo) getHardwareOn("right_hand", hardwareMap.servo);

        ods = (OpticalDistanceSensor) getHardwareOn("sensor_ods", hardwareMap.opticalDistanceSensor);
        touchSensor = (TouchSensor) getHardwareOn("sensor_touch", hardwareMap.touchSensor);
        mrColor = (ColorSensor) getHardwareOn("mrColor", hardwareMap.colorSensor);
        //irSeekerSensor = (IrSeekerSensor) getHardwareOn("sensor_ir", hardwareMap.irSeekerSensor);

    }

    /**
     * @return DcMotor
     */
    public DcMotor getLeftDrive() {
        return leftDrive;
    }

    /**
     * @return DcMotor
     */
    public DcMotor getRightDrive() {
        return rightDrive;
    }

    /**
     * @return DcMotor
     */
    public DcMotor getLeftArm() {
        return leftArm;
    }

    /**
     * @return Servo
     */
    public Servo getLeftHand() {
        return leftHand;
    }

    /**
     * @return Servo
     */
    public Servo getRightHand() {
        return rightHand;
    }

    /**
     * @return OpticalDistanceSensor
     */
    public OpticalDistanceSensor getOds() {
        return ods;
    }


    /**
     * @return TouchSensor
     */
    public TouchSensor getTouchSensor() {
        return touchSensor;
    }

    public ColorSensor getMrColor() {
        return mrColor;
    }
}
