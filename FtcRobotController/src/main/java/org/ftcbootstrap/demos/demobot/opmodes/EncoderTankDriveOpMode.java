package org.ftcbootstrap.demos.demobot.opmodes;

import com.qualcomm.robotcore.hardware.DcMotorController;

import org.ftcbootstrap.demos.demobot.DemoBot;
import org.ftcbootstrap.ActiveOpMode;
import org.ftcbootstrap.components.operations.motors.TankDriveToEncoder;
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
 * Opmode demonstrates how to runToTarget a vehicle using motor encoders
 */
public class EncoderTankDriveOpMode extends ActiveOpMode {

    private DemoBot robot;

    private TankDriveToEncoder tankDriveToEncoder;

    private int step;

    /**
     * Implement this method to define the code to run when the Init button is pressed on the Driver station.
     */
    @Override
    protected void onInit() {

        //specify configuration name save from scan operation
        robot = DemoBot.newConfig(hardwareMap, getTelemetryUtil());

        tankDriveToEncoder = new TankDriveToEncoder(this, robot.getMotor1(), robot.getMotor2());

        getTelemetryUtil().addData("Init", getClass().getSimpleName() + " initialized.");
        getTelemetryUtil().sendTelemetry();

    }

    @Override
    protected void onStart() throws InterruptedException  {
        super.onStart();
        step = 1;
    }


    /**
     * Implement this method to define the code to run when the Start button is pressed on the Driver station.
     * This method will be called on each hardware cycle just as the loop() method is called for event based Opmodes
     *  @throws InterruptedException
     */
    @Override
    protected void activeLoop() throws InterruptedException {

        checkForKillSwitch();

        getTelemetryUtil().addData("Current Step: ", step);

        boolean targetReached = false;

        switch (step) {
            case 1:
                //full power , forward for 20000
                getTelemetryUtil().addData("step" + step + ": handleDriveToEncoder", "DRIVE_FORWARD");
                targetReached  = tankDriveToEncoder.runToTarget(0.8, 2000 ,
                        DriveDirection.DRIVE_FORWARD,DcMotorController.RunMode.RUN_USING_ENCODERS);
                if (targetReached) {
                    step++;
                }
                break;

            case 2:
                //turn left at half power
                getTelemetryUtil().addData("step" + step + ": handleDriveToEncoder", "SPIN_LEFT");
                targetReached  = tankDriveToEncoder.runToTarget(0.5, 5000 ,
                        DriveDirection.SPIN_LEFT,DcMotorController.RunMode.RUN_USING_ENCODERS);
                if (targetReached) {
                    step++;
                }
                break;

            case 3:
                //full power , forward for 4000
                getTelemetryUtil().addData("step" + step + ": handleDriveToEncoder", "DRIVE_FORWARD");
                targetReached  = tankDriveToEncoder.runToTarget(0.8, 4000 ,
                        DriveDirection.DRIVE_FORWARD,DcMotorController.RunMode.RUN_USING_ENCODERS);
                if (targetReached) {
                    step++;
                }
                break;

            case 99:
                getTelemetryUtil().addData("step" + step + ": Opmode Status", "Robot Stopped.  Kill switch activated");
                setOperationsCompleted();
                break;

            default:
                setOperationsCompleted();
                break;

        }

        //send any telemetry that may have been added in the above operations
        getTelemetryUtil().sendTelemetry();

    }




    private void checkForKillSwitch() throws InterruptedException {
        if (robot.getTouch1().isPressed()) {

            if (tankDriveToEncoder.isDriving()) {
                tankDriveToEncoder.stop();
            } else {
                robot.getMotor1().setPower(0);
                robot.getMotor2().setPower(0);
            }
            step = 99;

        }
    }

}
