package org.ftc7729;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;

import org.ftcbootstrap.RobotConfiguration;
import org.ftcbootstrap.components.utils.TelemetryUtil;

/**
 * FTCTeamRobot Saved Configuration
 * <p/>
 * It is assumed that there is a configuration on the phone running the Robot Controller App with the same name as this class and
 * that  configuration is the one that is marked 'activated' on the phone.
 * It is also assumed that the device names in the 'init()' method below are the same  as the devices named for the
 * saved configuration on the phone.
 */
public class FTCTeamRobot extends RobotConfiguration {

    //sensors
    private OpticalDistanceSensor ods;


    //motors
    private DcMotor driveLeft;
    private DcMotor driveRight;

    //windshield wiper
    private Servo servo;


    /**
     * Factory method for this class
     *
     * @param hardwareMap
     * @param telemetryUtil
     * @return
     */
    public static FTCTeamRobot newConfig(HardwareMap hardwareMap, TelemetryUtil telemetryUtil) {

        FTCTeamRobot config = new FTCTeamRobot();
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

        servo = (Servo) getHardwareOn("servo", hardwareMap.servo);
        driveLeft = (DcMotor) getHardwareOn("left_drive", hardwareMap.dcMotor);
        driveRight = (DcMotor) getHardwareOn("right_drive", hardwareMap.dcMotor);
        ods = (OpticalDistanceSensor) getHardwareOn("lightSensor", hardwareMap.opticalDistanceSensor);
        //driveLeft.setDirection(DcMotor.Direction.REVERSE);

    }


    /**
     * @return OpticalDistanceSensor
     */
    public OpticalDistanceSensor getOpticalDistanceSensor() {
        return ods;
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
    public DcMotor getDriveLeft() {
        return driveLeft;
    }

    /**
     * @return DcMotor
     */
    public DcMotor getDriveRight() {
        return driveRight;
    }



}
