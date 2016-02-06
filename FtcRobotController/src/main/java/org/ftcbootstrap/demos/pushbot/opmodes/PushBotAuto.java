package org.ftcbootstrap.demos.pushbot.opmodes;

import com.qualcomm.robotcore.hardware.DcMotorController;

import org.ftcbootstrap.ActiveOpMode;
import org.ftcbootstrap.components.operations.motors.TankDriveToEncoder;
import org.ftcbootstrap.demos.pushbot.PushBot;
import org.ftcbootstrap.components.utils.DriveDirection;

/**
 * Note: This Exercise assumes that you have used your Robot Controller App to "scan" your hardware and
 * saved the configuration named: "Pushbot" and creating a class by the same name: {@link PushBot}.
 * <p/>
 * Note:  It is assumed that the proper registry is used for this set of demos. To confirm please
 * search for "Enter your custom registry here"  in  {@link com.qualcomm.ftcrobotcontroller.FtcRobotControllerActivity}
 * <p/>
 * Summary:
 * <p/>
 * Refactored from the original Qualcomm PushBot examples to demonstrate the use of the latest
 * reusable components and operations
 * See:
 * <p/>
 * {@link org.ftcbootstrap.components},
 * <p/>
 * {@link org.ftcbootstrap.components.operations.servos},
 * <p/>
 * {@link org.ftcbootstrap.components.operations.motors}
 * <p/>
 * Also see: {@link PushBot} for the saved configuration
 */
public class PushBotAuto extends ActiveOpMode {

    private PushBot robot;

    private TankDriveToEncoder tankDriveToEncoder;

    private int step;

    /**
     * Implement this method to define the code to run when the Init button is pressed on the Driver station.
     */
    @Override
    protected void onInit() {

        //specify configuration name save from scan operation
        robot = PushBot.newConfig(hardwareMap, getTelemetryUtil());

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
        getTelemetryUtil().addData("left motor position: ", lp);
        getTelemetryUtil().addData("right motor position: ",  rp);
        getTelemetryUtil().addData("dif: ",  lp - rp);

        boolean targetReached = false;

        switch (step) {
            case 1:
                //full power  forward
                getTelemetryUtil().addData("step" + step + ": handleDriveOperation", "DRIVE_FORWARD");
                targetReached = tankDriveToEncoder.runToTarget(.80, 10000,
                        DriveDirection.DRIVE_FORWARD, DcMotorController.RunMode.RUN_USING_ENCODERS);
                if (targetReached) {
                    step++;
                }
                break;

            case 2:
                //turn right
                getTelemetryUtil().addData("step" + step + ": handleDriveOperation", "SPIN_RIGHT");
                targetReached = tankDriveToEncoder.runToTarget(0.5, 3380,
                        DriveDirection.SPIN_RIGHT, DcMotorController.RunMode.RUN_USING_ENCODERS);
                if (targetReached) {
                    step++;
                }
                break;

            case 3:
                //full power  forward
                getTelemetryUtil().addData("step" + step + ": handleDriveOperation", "DRIVE_FORWARD");
                targetReached = tankDriveToEncoder.runToTarget(.65, 5000,
                        DriveDirection.DRIVE_FORWARD, DcMotorController.RunMode.RUN_USING_ENCODERS);
                if (targetReached) {
                    step++;
                }
                break;

            case 4:
                //turn right
                getTelemetryUtil().addData("step" + step + ": handleDriveOperation", "SPIN_RIGHT");
                targetReached = tankDriveToEncoder.runToTarget(0.4, 3380,
                        DriveDirection.SPIN_RIGHT, DcMotorController.RunMode.RUN_USING_ENCODERS);
                if (targetReached) {
                    step++;
                }
                break;

            case 5:
                //full power  forward
                getTelemetryUtil().addData("step" + step + ": handleDriveOperation", "DRIVE_FORWARD");
                targetReached = tankDriveToEncoder.runToTarget(.65, 10000,
                        DriveDirection.DRIVE_FORWARD, DcMotorController.RunMode.RUN_USING_ENCODERS);
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
