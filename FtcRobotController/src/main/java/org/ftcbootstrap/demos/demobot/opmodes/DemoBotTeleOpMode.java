package org.ftcbootstrap.demos.demobot.opmodes;

import org.ftcbootstrap.demos.demobot.DemoBot;
import org.ftcbootstrap.ActiveOpMode;
import org.ftcbootstrap.components.operations.motors.GamePadTankDrive;
import org.ftcbootstrap.components.operations.servos.GamePadServo;

/**
 * Note: This Exercise assumes that you have used your Robot Controller App to "scan" your hardware and
 * saved the configuration named: "DemoBot" and creating a class by the same name: {@link DemoBot}.
 * <p/>
 * Note:  It is assumed that the proper registry is used for this set of demos. To confirm please
 * search for "Enter your custom registry here"  in  {@link com.qualcomm.ftcrobotcontroller.FtcRobotControllerActivity}
 * <p/>
 * Summary:
 * <p/>
 * Opmode demonstrates using multiple gamepad components
 * <p/>
 * 1) Drive a vehicle using the gamepad1 joysticks
 * <p/>
 * 2) Operating a servo using the gamepad2 buttons
 */
public class DemoBotTeleOpMode extends ActiveOpMode {

    private GamePadTankDrive tankDrive;
    private GamePadServo servoDrive;

    private DemoBot robot;

    /**
     * Implement this method to define the code to run when the Init button is pressed on the Driver station.
     */
    @Override
    protected void onInit() {

        robot = DemoBot.newConfig(hardwareMap, getTelemetryUtil());


        getTelemetryUtil().addData("Init", getClass().getSimpleName() + " initialized.");
        getTelemetryUtil().sendTelemetry();

    }

    @Override
    protected void onStart() throws InterruptedException  {
        super.onStart();

        tankDrive = new GamePadTankDrive(this,gamepad1, robot.getMotor1(), robot.getMotor2());

        //GamePadServo.Control use the Y and A buttons for up and down and the  X and B buttons for left and right
        servoDrive = new GamePadServo(this, gamepad2, robot.getServo1(), GamePadServo.Control.Y_A, 0.5);

    }

    /**
     * Implement this method to define the code to run when the Start button is pressed on the Driver station.
     * This method will be called on each hardware cycle just as the loop() method is called for event based Opmodes
     *
     * @throws InterruptedException
     */
    @Override
    protected void activeLoop() throws InterruptedException {

        tankDrive.update();
        servoDrive.update();

        //send any telemetry that may have been added in the above operations
        getTelemetryUtil().sendTelemetry();

    }

}
