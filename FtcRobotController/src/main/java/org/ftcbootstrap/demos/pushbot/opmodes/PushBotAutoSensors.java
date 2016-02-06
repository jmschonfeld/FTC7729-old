package org.ftcbootstrap.demos.pushbot.opmodes;

import com.qualcomm.robotcore.hardware.DcMotorController;

import org.ftcbootstrap.ActiveOpMode;
import org.ftcbootstrap.demos.pushbot.PushBot;
import org.ftcbootstrap.components.operations.motors.MotorToTouch;
import org.ftcbootstrap.components.operations.motors.TankDriveToEncoder;
import org.ftcbootstrap.components.operations.motors.TankDriveToODS;
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
public class PushBotAutoSensors extends ActiveOpMode {

    private PushBot robot;
    private TankDriveToODS tankDriveToODS;
    private TankDriveToEncoder tankDriveToEncoder;
    private MotorToTouch motorToTouch;
    private boolean okToExtendArm;
    private boolean armFullyExtended;

    private int step;

    /**
     * Implement this method to define the code to run when the Init button is pressed on the Driver station.
     */
    @Override
    protected void onInit() {

        okToExtendArm = false;
        armFullyExtended = false;

        //specify configuration name save from scan operation
        robot = PushBot.newConfig(hardwareMap, getTelemetryUtil());

        tankDriveToEncoder = new TankDriveToEncoder( this, robot.getLeftDrive(), robot.getRightDrive());
        tankDriveToODS = new TankDriveToODS( this, robot.getOds(), robot.getLeftDrive(), robot.getRightDrive() );
        motorToTouch  = new MotorToTouch("left arm" , this, robot.getLeftArm(), robot.getTouchSensor());

        getTelemetryUtil().addData("Init", getClass().getSimpleName() + " initialized.");
        getTelemetryUtil().sendTelemetry();

    }


    @Override
    protected void onStart() throws InterruptedException  {
        super.onStart();
        step = 1;

       // getTelemetryUtil().setSortByTime(true);

    }


    /**
     * Implement this method to define the code to run when the Start button is pressed on the Driver station.
     * This method will be called on each hardware cycle just as the loop() method is called for event based Opmodes
     *  @throws InterruptedException
     */
    @Override
    protected void activeLoop()  throws InterruptedException {


        getTelemetryUtil().addData("Current Step: ", step);

        boolean targetReached = false;

        switch (step) {
            case 1:
                //full power , forward for 2880
                getTelemetryUtil().addData("step" + step + ": handleEncodedDrive", "forward");
                targetReached =  tankDriveToEncoder.runToTarget(1, 2880,
                        DriveDirection.DRIVE_FORWARD,DcMotorController.RunMode.RUN_USING_ENCODERS);
                if (targetReached) {
                    step++;
                }
                break;

            case 2:
                //turn left
                getTelemetryUtil().addData("step" + step + ": handleEncodedDrive", "left");
                targetReached =  tankDriveToEncoder.runToTarget(1, 2300,
                        DriveDirection.SPIN_LEFT,DcMotorController.RunMode.RUN_USING_ENCODERS);
                if (targetReached) {
                    step++;
                }
                break;


            case 3:
                //this can happen independent of any step
                okToExtendArm = true;

                //brightness assumes fixed distance from the target
                //i.e. line follow or stop on white line
                double targetBrightness = 0.6;
                //  full power , forward until ODS target brightness >= .6
                double power =1;
                targetReached =   tankDriveToODS.runToTarget( power, targetBrightness, DriveDirection.DRIVE_FORWARD);
                if (targetReached) {
                    step++;
                }

                getTelemetryUtil().addData("step" + step + ": handleDriveToODS", "in progress");

                break;


            default:
                getTelemetryUtil().addData("step" + step + ": armFullyExtended", "in progress");
                if (armFullyExtended) {
                    setOperationsCompleted();
                }
                break;
        }

        //this can happen independent of any step
        if (okToExtendArm) {
            if( armFullyExtended) {
                return;
            }
            targetReached = motorToTouch.runToTarget(1);
            if (targetReached) {
                armFullyExtended = true;
            }
        }

        //send any telemetry that may have been added in the above operations
        getTelemetryUtil().sendTelemetry();

    }







}
