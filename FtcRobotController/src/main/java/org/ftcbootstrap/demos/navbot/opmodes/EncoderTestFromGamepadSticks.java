package org.ftcbootstrap.demos.navbot.opmodes;

import com.qualcomm.robotcore.hardware.DcMotorController;

import org.ftcbootstrap.ActiveOpMode;
import org.ftcbootstrap.components.operations.motors.GamePadTankDrive;
import org.ftcbootstrap.components.operations.motors.MotorToEncoder;
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
public class EncoderTestFromGamepadSticks extends ActiveOpMode {

    private NavBot robot;

    private MotorToEncoder leftMotorToEncoder;
    private MotorToEncoder rightMotorToEncoder;
    private GamePadTankDrive tankDrive;
    private int step;
    private int stepCounter;

    /**
     * Implement this method to define the code to run when the Init button is pressed on the Driver station.
     */
    @Override
    protected void onInit() {

        //specify configuration name save from scan operation
        robot = NavBot.newConfig(hardwareMap, getTelemetryUtil());

        leftMotorToEncoder = new MotorToEncoder(this, robot.getLeftDrive());
        leftMotorToEncoder.setName("left runToTarget"  );
        rightMotorToEncoder = new MotorToEncoder(this, robot.getRightDrive());
        rightMotorToEncoder.setName("right runToTarget"  );

        getTelemetryUtil().addData("Init", getClass().getSimpleName() + " initialized.");
        getTelemetryUtil().sendTelemetry();



    }

    @Override
    protected void onStart() throws InterruptedException  {
        super.onStart();
        //set up tank runToTarget operation to use the gamepad joysticks
        tankDrive =  new GamePadTankDrive( this, gamepad1,robot.getLeftDrive(), robot.getRightDrive());
        tankDrive.startRunMode(DcMotorController.RunMode.RUN_USING_ENCODERS);

        step = 1;

        //getTelemetryUtil().setSortByTime(true);
    }

    /**
     * Implement this method to define the code to run when the Start button is pressed on the Driver station.
     * This method will be called on each hardware cycle just as the loop() method is called for event based Opmodes
     *  @throws InterruptedException
     */
    @Override
    protected void activeLoop() throws InterruptedException {

        tankDrive.update();

        getTelemetryUtil().addData("Left Position", robot.getLeftDrive().getCurrentPosition());
        getTelemetryUtil().addData("RightPosition", robot.getRightDrive().getCurrentPosition());


        //send any telemetry that may have been added in the above operations
        getTelemetryUtil().sendTelemetry();


    }

}
