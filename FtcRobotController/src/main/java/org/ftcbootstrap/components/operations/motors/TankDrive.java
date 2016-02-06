package org.ftcbootstrap.components.operations.motors;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;

import org.ftcbootstrap.ActiveOpMode;
import org.ftcbootstrap.components.OpModeComponent;
import org.ftcbootstrap.components.utils.DriveDirection;
import org.ftcbootstrap.components.utils.MotorDirection;


/**
 * Operation to assist with Tank Driving
 */

public class TankDrive extends OpModeComponent {

   private DcMotor leftMotor;
   private DcMotor rightMotor;
    private boolean driving;

    private DriveDirection currentDirection;

    /**
     * Constructor for operation
     * Telemetry enabled by default.
     *
     * @param opMode
     * @param leftMotor  DcMotor
     * @param rightMotor DcMotor
     */
    public TankDrive(ActiveOpMode opMode,
                     DcMotor leftMotor,
                     DcMotor rightMotor) {


        super(opMode);
        this.leftMotor = leftMotor;
        this.rightMotor = rightMotor;

    }


    /**
     * @param power
     * @param driveDirection {@link DriveDirection}
     * @throws InterruptedException
     */
    public void drive( double power, DriveDirection driveDirection)
            throws InterruptedException {

        this.driving = true;
        this.currentDirection = driveDirection;

        MotorDirection motorDirection = TankDrive.leftMotorDirectionOn(driveDirection);
        double powerWithDirection = TankDrive.leftPowerWithDirection(motorDirection, driveDirection, power);
        if (getLeftMotor() != null) {

            addTelemetry("Starting left motor", powerWithDirection);

            getLeftMotor().setPower(powerWithDirection);
        }

        motorDirection = TankDrive.rightMotorDirectionOn(driveDirection);
        powerWithDirection = TankDrive.rightPowerWithDirection(motorDirection, driveDirection, power);


        if (driveDirection == DriveDirection.PIVOT_FORWARD_RIGHT || driveDirection == DriveDirection.PIVOT_BACKWARD_RIGHT) {
            powerWithDirection = 0f;
        }

        if (getRightMotor() != null) {
            addTelemetry("Starting right motor", powerWithDirection);

            getRightMotor().setPower(powerWithDirection);
        }
    }


    /**
     * Check if the operation is running.   Do this before calling drive()
     *
     * @return boolean
     */
    public boolean isDriving() {
        return driving;
    }


    /**
     * stop the motors
     */
    public void stop() {

        getLeftMotor().setPower(0.f);
        getRightMotor().setPower(0.f);
        driving = false;

    }


    public DcMotor getLeftMotor() {
        return leftMotor;
    }

    public DcMotor getRightMotor() {
        return rightMotor;
    }

    /**
     * Helper method to derive the left motor direction from the DriveDirection
     *
     * @param driveDirection {@link DriveDirection}
     * @return
     */
    public static MotorDirection leftMotorDirectionOn(DriveDirection driveDirection) {

        MotorDirection motorDirection = MotorDirection.MOTOR_FORWARD;
        if (driveDirection == DriveDirection.DRIVE_BACKWARD ||
                driveDirection == DriveDirection.SPIN_LEFT ||
                driveDirection == DriveDirection.PIVOT_BACKWARD_LEFT) {
            motorDirection = MotorDirection.MOTOR_BACKWARD;
        }
        return motorDirection;
    }

    /**
     * Helper method to derive the right motor direction from the  DriveDirection
     *
     * @param driveDirection {@link DriveDirection}
     * @return MotorDirection {@link MotorDirection}
     */
    public static MotorDirection rightMotorDirectionOn(DriveDirection driveDirection) {

        MotorDirection motorDirection = MotorDirection.MOTOR_FORWARD;
        if (driveDirection == DriveDirection.DRIVE_BACKWARD ||
                driveDirection == DriveDirection.SPIN_RIGHT||
                driveDirection == DriveDirection.PIVOT_BACKWARD_RIGHT) {
            motorDirection = MotorDirection.MOTOR_BACKWARD;
        }
        return motorDirection;
    }

    /**
     * Helper method to derive the left motor power the DriveDirection
     * Based of the vehicle Drive direction, calculate the value of a single motor to see if should move forward
     * backward , remaing in the same direction of the other motor, or come to a stop
     * <p/>
     * Examples:
     * Drive Forward = both motors move forward
     * Drive Forward = both motors move backward
     * SPIN = One motor moves forward while the other backward
     * PIVOT = One motor moves is stop  while the other moves
     *
     * @param motorDirection current left motor direction {@link MotorDirection}
     * @param driveDirection current vehicle drive direction {@link DriveDirection}
     * @param power          current absolute power
     * @return double current absolute power as a positive or negative number
     */
    public static double leftPowerWithDirection(MotorDirection motorDirection, DriveDirection driveDirection, double power) {

        double powerWithDirection = (motorDirection == MotorDirection.MOTOR_FORWARD) ? power : -power;
        if (driveDirection == DriveDirection.PIVOT_FORWARD_LEFT || driveDirection == DriveDirection.PIVOT_BACKWARD_LEFT) {
            powerWithDirection = 0f;
        }
        return powerWithDirection;
    }


    /**
     * Helper method to derive the right motor power the DriveDirection
     * Based of the vehicle Drive direction, calculate the value of a single motor to see if should move forward
     * backward , remaing in the same direction of the other motor, or come to a stop
     * <p/>
     * Examples:
     * Drive Forward = both motors move forward
     * Drive Forward = both motors move backward
     * SPIN = One motor moves forward while the other backward
     * PIVOT = One motor moves is stop  while the other moves
     *
     * @param motorDirection current right motor direction {@link MotorDirection}
     * @param driveDirection current vehicle drive direction  {@link DriveDirection}
     * @param power          current absolute power
     * @return double current absolute power as a positive or negative number or zero value
     */
    public static double rightPowerWithDirection(MotorDirection motorDirection, DriveDirection driveDirection, double power) {

        double powerWithDirection = (motorDirection == MotorDirection.MOTOR_FORWARD) ? power : -power;
        if (driveDirection == DriveDirection.PIVOT_FORWARD_RIGHT || driveDirection == DriveDirection.PIVOT_BACKWARD_RIGHT) {
            powerWithDirection = 0f;
        }
        return powerWithDirection;
    }


    /**
     * @return DriveDirection current direction of vehicle  {@link DriveDirection}
     */
    public DriveDirection getCurrentDirection() {
        return currentDirection;
    }



    public double getLeftMotorPower() {
        return leftMotor.getPower();
    }

    public double getRightMotorPower() {
        return rightMotor.getPower();
    }


    public void ensureNotRunToPosition() throws InterruptedException {

        DcMotor motor = getLeftMotor();
        boolean modeChanged = false;
        if (motor.getMode() == DcMotorController.RunMode.RUN_TO_POSITION ) {
            motor.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
            modeChanged = true;
        }
        motor = getRightMotor();
        if (motor.getMode() == DcMotorController.RunMode.RUN_TO_POSITION ) {
            motor.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
            modeChanged = true;
        }
        if ( modeChanged) {
            getOpMode().waitOneFullHardwareCycle();
        }
    }

}
