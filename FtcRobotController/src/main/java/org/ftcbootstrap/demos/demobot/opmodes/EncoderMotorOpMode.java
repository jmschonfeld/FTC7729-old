package org.ftcbootstrap.demos.demobot.opmodes;

import com.qualcomm.robotcore.hardware.DcMotorController;

import org.ftcbootstrap.demos.demobot.DemoBot;
import org.ftcbootstrap.ActiveOpMode;
import org.ftcbootstrap.components.operations.motors.MotorToEncoder;
import org.ftcbootstrap.components.utils.MotorDirection;

/**
 * Note: This Exercise assumes that you have used your Robot Controller App to "scan" your hardware and
 * saved the configuration named: "DemoBot" and creating a class by the same name: {@link DemoBot}.
 * <p/>
 * Note:  It is assumed that the proper registry is used for this set of demos. To confirm please
 * search for "Enter your custom registry here"  in  {@link com.qualcomm.ftcrobotcontroller.FtcRobotControllerActivity}
 * <p/>
 * Summary:
 * <p/>
 * Opmode demonstrates running a motor from and encoder
 */
public class EncoderMotorOpMode extends ActiveOpMode {

    private DemoBot robot;

    private MotorToEncoder motorToEncoder;
    private int step;

    /**
     * Implement this method to define the code to run when the Init button is pressed on the Driver station.
     */
    @Override
    protected void onInit() {

        //specify configuration name save from scan operation
        robot = DemoBot.newConfig(hardwareMap, getTelemetryUtil());

        motorToEncoder = new MotorToEncoder(  this, robot.getMotor1());
        motorToEncoder.setName("motor1");

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

        getTelemetryUtil().addData("step: " + step , "current");

        boolean targetReached = false;

        switch (step) {
            case 1:

                //full power , forward for 8880
                targetReached = motorToEncoder.runToTarget(1, 8880,
                        MotorDirection.MOTOR_FORWARD, DcMotorController.RunMode.RUN_USING_ENCODERS);
                if (targetReached) {
                    step++;
                }
                break;

            case 2:
                //  1/4 power backward for 1000
                targetReached = motorToEncoder.runToTarget(0.25, 1000,
                        MotorDirection.MOTOR_BACKWARD, DcMotorController.RunMode.RUN_USING_ENCODERS);
                if (targetReached) {
                    step++;
                }
                break;

            case 99:
                getTelemetryUtil().addData("step" + step + " Opmode Status", "Robot Stopped.  Kill switch activated");
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

            if (motorToEncoder.isRunning()) {
                motorToEncoder.stop();
            } else {
                robot.getMotor1().setPower(0);
                robot.getMotor2().setPower(0);
            }
            step = 99;

        }
    }

}
