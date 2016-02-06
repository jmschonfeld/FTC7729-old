package org.ftcbootstrap.demos.navbot.opmodes;

import com.qualcomm.robotcore.hardware.DcMotorController;

import org.ftcbootstrap.ActiveOpMode;
import org.ftcbootstrap.components.operations.motors.TankDriveToEncoder;
import org.ftcbootstrap.components.utils.DriveDirection;
import org.ftcbootstrap.demos.navbot.NavBot;

/**
 * Note: This Exercise assumes that you have used your Robot Controller App to "scan" your hardware and
 * saved the configuration named: "NavBot" and creating a class by the same name: {@link NavBot}.
 * <p/>
 * Note:  It is assumed that the proper registry is used for this set of demos. To confirm please
 * search for "Enter your custom registry here"  in  {@link com.qualcomm.ftcrobotcontroller.FtcRobotControllerActivity}
 * <p/>
 * Summary:
 * <p/>
 * See:
 * <p/>
 * {@link org.ftcbootstrap.components},
 * <p/>
 * {@link org.ftcbootstrap.components.operations.servos},
 * <p/>
 * {@link org.ftcbootstrap.components.operations.motors}
 * <p/>
 * Also see: {@link NavBot} for the saved configuration
 */
public class NavigateWithEncoders extends ActiveOpMode {

    private NavBot robot;

    private TankDriveToEncoder tankDriveToEncoder;

    private int step;

    /**
     * Implement this method to define the code to run when the Init button is pressed on the Driver station.
     */
    @Override
    protected void onInit() {

        //specify configuration name save from scan operation
        robot = NavBot.newConfig(hardwareMap, getTelemetryUtil());

        tankDriveToEncoder = new TankDriveToEncoder(this, robot.getLeftDrive(), robot.getRightDrive());
        tankDriveToEncoder.setOpModeLogLevel(0);

        getTelemetryUtil().addData("Init", getClass().getSimpleName() + " initialized.");
        getTelemetryUtil().sendTelemetry();

    }

    @Override
    protected void onStart() throws InterruptedException {
        super.onStart();
        step = 1;
        // getTelemetryUtil().setSortByTime(true);

    }


    /**
     * Implement this method to define the code to run when the Start button is pressed on the Driver station.
     * This method will be called on each hardware cycle just as the loop() method is called for event based Opmodes
     *
     * @throws InterruptedException
     */
    @Override
    protected void activeLoop() throws InterruptedException {

      //  getTelemetryUtil().addData("Current Step: ", step);

        int lp = robot.getLeftDrive().getCurrentPosition();
        int rp = robot.getRightDrive().getCurrentPosition();
        getTelemetryUtil().addData("left motor position in step:" + step, lp);
        getTelemetryUtil().addData("right motor position in step:" + step, rp);
        getTelemetryUtil().addData("dif in step:" + step,  lp - rp);
        boolean targetReached = false;

        switch (step) {
            case 1:
                //full power  forward
                getTelemetryUtil().addData("step" + step + ": handleDriveOperation", "DRIVE_FORWARD");
                targetReached = tankDriveToEncoder.runToTarget(.65, 10000,
                        DriveDirection.DRIVE_FORWARD,DcMotorController.RunMode.RUN_TO_POSITION);
                if (targetReached) {
                    step++;
                }
                break;

            case 2:
                //turn right
                getTelemetryUtil().addData("step" + step + ": handleDriveOperation", "PIVOT_RIGHT");
                targetReached = tankDriveToEncoder.runToTarget(0.4, 5050,
                        DriveDirection.PIVOT_FORWARD_RIGHT,DcMotorController.RunMode.RUN_TO_POSITION);
                if (targetReached) {
                    step++;
                   // step = 7;
                }
                break;

            case 3:
                //full power  forward
                getTelemetryUtil().addData("step" + step + ": handleDriveOperation", "DRIVE_FORWARD");
                targetReached = tankDriveToEncoder.runToTarget(.65, 5000,
                        DriveDirection.DRIVE_FORWARD,DcMotorController.RunMode.RUN_TO_POSITION);
                if (targetReached) {
                    step++;
                }
                break;

            case 4:
                //turn right
                getTelemetryUtil().addData("step" + step + ": handleDriveOperation", "PIVOT_RIGHT");
                targetReached = tankDriveToEncoder.runToTarget(0.4, 5050,
                        DriveDirection.PIVOT_FORWARD_RIGHT,DcMotorController.RunMode.RUN_TO_POSITION);
                if (targetReached) {
                    step++;
                }
                break;

            case 5:
                //full power  forward
                getTelemetryUtil().addData("step" + step + ": handleDriveOperation", "DRIVE_FORWARD");
                targetReached = tankDriveToEncoder.runToTarget(.65, 10000,
                        DriveDirection.DRIVE_FORWARD,DcMotorController.RunMode.RUN_TO_POSITION);
                if (targetReached) {
                    step++;
                }
                break;

            default:
                setOperationsCompleted();
                break;

        }

        //send any telemetry that may have been added in the above operations
        getTelemetryUtil().sendTelemetry();

    }



}
