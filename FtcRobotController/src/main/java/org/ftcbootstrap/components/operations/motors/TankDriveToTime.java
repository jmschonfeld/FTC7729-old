package org.ftcbootstrap.components.operations.motors;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;

import org.ftcbootstrap.ActiveOpMode;
import org.ftcbootstrap.components.ODSComponent;
import org.ftcbootstrap.components.OpModeComponent;
import org.ftcbootstrap.components.utils.DriveDirection;


/**
 * Operation to assist with Tank Driving while for a specific time duration
 */
public class TankDriveToTime extends OpModeComponent {

    private TankDrive tankDrive;
    private ODSComponent odsComponent;
    private double startTime;
    private double targetTime;

    /**
     * Constructor for operation
     * Telemetry enabled by default.
     * @param opMode
     * @param leftMotor DcMotor
     * @param rightMotor DcMotor
     */
    public TankDriveToTime(ActiveOpMode opMode,
                           DcMotor leftMotor,
                           DcMotor rightMotor) {


        super(opMode);
        this.tankDrive = new TankDrive(opMode, leftMotor, rightMotor);

    }


    /**
     *
     * @param power
     * @param targetTime
     * @param driveDirection  {@link DriveDirection}
     * @return boolean target reached
     * @throws InterruptedException
     */
    public boolean runToTarget( double power, double targetTime, DriveDirection driveDirection)
            throws InterruptedException {

        if (this.isDriving()) {

            boolean reached =  this.targetReached();
            if ( reached) {
                stop();
            }
            return reached;
        }

        this.targetTime = targetTime;
        this.startTime = getOpMode().getRuntime();

        tankDrive.ensureNotRunToPosition();

        tankDrive.drive(power, driveDirection);
        tankDrive.setName("driving for time");

        return false;


    }

    /**
     * Check if the operation is running.   Do this before calling drive()
     * @return boolean
     */
    public boolean isDriving() {
        return this.tankDrive.isDriving();
    }


    /**
     * stop the motors
     */
    public void stop()   {

        this.tankDrive.stop();

    }


    /**
     * check if current ods reading registers as close to target or as matched target brightness
     * @return boolean
     */
    public boolean targetReached() {

        return getOpMode().getRuntime() - startTime >= targetTime;

    }


    /**
     *
     * @return DriveDirection current direction of vehicle
     */
    public DriveDirection getCurrentDirection() {
        return tankDrive.getCurrentDirection();
    }




}
