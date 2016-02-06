package org.ftcbootstrap.components.operations.motors;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;

import org.ftcbootstrap.ActiveOpMode;
import org.ftcbootstrap.components.ODSComponent;
import org.ftcbootstrap.components.OpModeComponent;
import org.ftcbootstrap.components.utils.DriveDirection;


/**
 * Operation to assist with Tank Driving while using an ODS for
 * stopping on a line, line following, stopping before a wall
 */
public class TankDriveToODS extends OpModeComponent {

    private TankDrive tankDrive;
    private ODSComponent odsComponent;
    private Boolean headingToLine;
    private double startTime;

    /**
     * Constructor for operation
     * Telemetry enabled by default.
     * @param opMode
     * @param ods
     * @param leftMotor DcMotor
     * @param rightMotor DcMotor
     */
    public TankDriveToODS(ActiveOpMode opMode,
                          OpticalDistanceSensor ods,
                          DcMotor leftMotor,
                          DcMotor rightMotor) {


        super(opMode);
        this.odsComponent = new ODSComponent(opMode , ods);
        this.tankDrive = new TankDrive(opMode, leftMotor, rightMotor);

    }


    /**
     *
     * @param power
     * @param targetProximity
     * @param driveDirection  {@link DriveDirection}
     * @return boolean target reached
     * @throws InterruptedException
     */
    public boolean runToTarget(double power, double targetProximity, DriveDirection driveDirection)
            throws InterruptedException {

        if (this.isDriving()) {

            boolean reached =  this.targetReached();
            if ( reached) {
                stop();
            }
            return reached;
        }

        tankDrive.ensureNotRunToPosition();

        this.odsComponent.setTarget( targetProximity);
        tankDrive.drive( power, driveDirection);

        return false;


    }



    /**
     *
     * @param power
     * @param targetProximity
     * @param targetTime in seconds
     * @param directionToLine  {@link DriveDirection}
     * @return boolean target time reached
     * @throws InterruptedException
     */
    public boolean lineFollowForTime(double power, double targetProximity, double targetTime,  DriveDirection directionToLine)
            throws InterruptedException {

        addTelemetry("LF 01: ", "line following in progress");
        addTelemetry("LF 02  left motor power : ", tankDrive.getLeftMotorPower());
        addTelemetry("LF 03  right motor power : ", tankDrive.getRightMotorPower());
        addTelemetry("LF 04  ground ods : ", this.odsComponent.getOdsReading());

        if (this.isDriving()) {

            if ( getOpMode().getRuntime() - startTime >= targetTime) {
                addTelemetry("LF 05: ", "Target time Reached");
                this.stop();
                return true;
            }

            boolean reached =  this.lineTargetReached();
            if ( reached) {
                if ( tankDrive.getCurrentDirection() == DriveDirection.PIVOT_FORWARD_RIGHT) {
                    tankDrive.drive( power, DriveDirection.PIVOT_FORWARD_LEFT);
                }
                else {
                    tankDrive.drive( power, DriveDirection.PIVOT_FORWARD_RIGHT);
                }

                //check if rolled past line
                if ( headingToLine ) {
                        //Handle potent of robot rolling past line.   Allow time to get
                        //back to or to the correct side of the line.
                        Thread.sleep(200);
                }
                headingToLine = !headingToLine;
            }
            return false;
        }

        tankDrive.ensureNotRunToPosition();

        this.startTime = getOpMode().getRuntime();;
        this.headingToLine = true;
        this.odsComponent.setTarget( targetProximity);
        tankDrive.drive( power, directionToLine);

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


    private boolean targetReached() {

            return odsComponent.targetReached();
    }

    private boolean lineTargetReached() {

        if (  ! this.headingToLine) {
            return ! odsComponent.targetReached();
        }
        else {
            return odsComponent.targetReached();
        }
    }



    /**
     *
     * @return DriveDirection current direction of vehicle
     */
    public DriveDirection getCurrentDirection() {
        return tankDrive.getCurrentDirection();
    }




}
