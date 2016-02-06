package org.ftcbootstrap.demos.demobot.opmodes;

import com.qualcomm.robotcore.hardware.DcMotorController;

import org.ftcbootstrap.demos.demobot.DemoBot;

import org.ftcbootstrap.ActiveOpMode;
import org.ftcbootstrap.components.ColorSensorComponent;
import org.ftcbootstrap.components.operations.motors.TankDriveToEncoder;
import org.ftcbootstrap.components.operations.motors.TankDriveToODS;
import org.ftcbootstrap.components.utils.DriveDirection;

/**
 * Note: This Exercise assumes that you have used your Robot Controller App to "scan" your hardware and
 * saved the configuration named: "DemoBot" and creating a class by the same name: {@link DemoBot}.
 * <p/>
 * Note:  It is assumed that the proper registry is used for this set of demos. To confirm please
 * search for "Enter your custom registry here"  in  {@link com.qualcomm.ftcrobotcontroller.FtcRobotControllerActivity}
 * <p/>
 * Summary:
 * <p/>
 * Advanced Opmode that shows how to use multiple operations
 * 1) Drive a vehicle using motor encoders
 * 2) Drive a vehicle using and ODS sensor
 * 3) Use the color sensor component
 */
public class DemoBotAdvancedOpMode extends ActiveOpMode {

    private DemoBot robot;
    private TankDriveToODS tankDriveToODS;
    private TankDriveToEncoder tankDriveToEncoder;
    private ColorSensorComponent colorSensorComponent;

    private int step;


    /**
     * Implement this method to define the code to run when the Init button is pressed on the Driver station.
     */
    @Override
    protected void onInit() {

        //specify configuration name save from scan operation
        robot = DemoBot.newConfig(hardwareMap, getTelemetryUtil());

        tankDriveToEncoder = new TankDriveToEncoder(this, robot.getMotor1(), robot.getMotor2());
        tankDriveToODS = new TankDriveToODS(this, robot.getOds1(), robot.getMotor1(), robot.getMotor2());
        colorSensorComponent = new ColorSensorComponent(this, robot.getMrColor1(), ColorSensorComponent.ColorSensorDevice.MODERN_ROBOTICS_I2C);
        colorSensorComponent.enableLed(false);

        getTelemetryUtil().addData("Init", getClass().getSimpleName() + " initialized.");
        getTelemetryUtil().sendTelemetry();

    }

    @Override
    protected void onStart() throws InterruptedException {
        super.onStart();
        step = 1;

        colorSensorComponent.enableLed(true);

    }

    /**
     * Implement this method to define the code to run when the Start button is pressed on the Driver station.
     * This method will be called on each hardware cycle just as the loop() method is called for event based Opmodes
     *
     * @throws InterruptedException
     */
    @Override
    protected void activeLoop() throws InterruptedException {

        checkForKillSwitch();

        getTelemetryUtil().addData("Current Step: ", step);

        if (step <= 3) {
            getTelemetryUtil().addData("step" + step + ": DriveCloseToLocation", "in progress");
            driveCloseToLocation();
        } else if (step == 4) {
            getTelemetryUtil().addData("step" + step + ": DriveCloseToLocation", "task completed");
            step++;
        } else if (step <= 6) {
            getTelemetryUtil().addData("step" + step + ": driveToProximityAndCheckColor", "in progress");
            driveToProximityAndCheckColor();
        } else if (step == 99) {
            getTelemetryUtil().addData("step" + step + ": Opmode Status", "Robot Stopped.  Kill switch activated");
            setOperationsCompleted();
        } else {
            setOperationsCompleted();
        }

        //send any telemetry that may have been added in the above operations
        getTelemetryUtil().sendTelemetry();

    }


    private void driveCloseToLocation() throws InterruptedException {

        boolean targetReached = false;

        switch (step) {
            case 1:
                //full power , forward for 20000
                targetReached =  tankDriveToEncoder.runToTarget(1, 20000,
                        DriveDirection.DRIVE_FORWARD,DcMotorController.RunMode.RUN_USING_ENCODERS);
                if (targetReached) {
                    step++;
                }
                break;

            case 2:
                //turn 90 degrees (note : 5000 will need to be changed
                targetReached =  tankDriveToEncoder.runToTarget(0.5, 5000,
                        DriveDirection.SPIN_LEFT,DcMotorController.RunMode.RUN_USING_ENCODERS );
                if (targetReached) {
                    step++;
                }
                break;

            case 3:
                //full power , forward for 4000
                targetReached =  tankDriveToEncoder.runToTarget(1, 4000,
                        DriveDirection.DRIVE_FORWARD,DcMotorController.RunMode.RUN_USING_ENCODERS  );
                if (targetReached) {
                    step++;
                }
                break;

            default:
                break;
        }

    }


    private void driveToProximityAndCheckColor() throws InterruptedException {

        boolean targetReached = false;

        switch (step) {
            case 5:
                // 1/4 power , forward until target proximity >= .15
                double power = 0.25;
                double proximityStrength = 0.15;
                targetReached = tankDriveToODS.runToTarget(power, proximityStrength,
                        DriveDirection.DRIVE_FORWARD);
                if (targetReached) {
                    step++;
                }
                break;

            case 6:
                boolean isRed = colorSensorComponent.isRed(6, 0, 0);
                getTelemetryUtil().addData("isRed", isRed);
                break;

            default:
                break;
        }
    }




    private void checkForKillSwitch() throws InterruptedException {
        if (robot.getTouch1().isPressed()) {

            if (tankDriveToEncoder.isDriving()) {
                tankDriveToEncoder.stop();
            } else if (tankDriveToODS.isDriving()) {
                tankDriveToODS.stop();
            }
            step = 99;

        }

    }

}
