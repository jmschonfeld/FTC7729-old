package org.ftcbootstrap.demos.navbot.opmodes;

import com.qualcomm.robotcore.hardware.DcMotorController;

import org.ftcbootstrap.ActiveOpMode;
import org.ftcbootstrap.components.operations.motors.GamePadMotor;
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
 *
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
public class EncoderTestFromGamepadButtons extends ActiveOpMode {

    private NavBot robot;
    private GamePadMotor yGamePadMotorLeft;
    private GamePadMotor yGamePadMotorRight;
    private GamePadMotor xGamePadMotorLeft;
    private GamePadMotor xGamePadMotorRight;
    private GamePadMotor aGamePadMotorLeft;
    private GamePadMotor aGamePadMotorRight;
    private GamePadMotor bGamePadMotorLeft;
    private GamePadMotor bGamePadMotorRight;

    private MotorToEncoder leftMotorToEncoder;
    private MotorToEncoder rightMotorToEncoder;


    private int step;
    private int stepCounter;
    private GamePadMotor.Control currentControl;

    /**
     * Implement this method to define the code to run when the Init button is pressed on the Driver station.
     */
    @Override
    protected void onInit() {

        //specify configuration name save from scan operation
        robot = NavBot.newConfig(hardwareMap, getTelemetryUtil());

        //these are only used to set the encoder
        leftMotorToEncoder = new MotorToEncoder(this, robot.getLeftDrive());
        leftMotorToEncoder.setName("left runToTarget" );
        rightMotorToEncoder = new MotorToEncoder( this, robot.getRightDrive());
        rightMotorToEncoder.setName("right runToTarget" );
        currentControl =  GamePadMotor.Control.Y_BUTTON;

        getTelemetryUtil().addData("Init", getClass().getSimpleName() + " initialized.");
        getTelemetryUtil().sendTelemetry();

    }

    @Override
    protected void onStart() throws InterruptedException  {
        super.onStart();

        float power = 0.65f;
        //2 GamePadMotors instances to drive the robot forward
        yGamePadMotorLeft = new GamePadMotor(this,gamepad1, robot.getLeftDrive() , GamePadMotor.Control.Y_BUTTON ,power);
        yGamePadMotorRight = new GamePadMotor(this,gamepad1, robot.getRightDrive() , GamePadMotor.Control.Y_BUTTON ,power);

        //2 GamePadMotors instances to drive the robot backward
        aGamePadMotorLeft = new GamePadMotor(this,gamepad1, robot.getLeftDrive() , GamePadMotor.Control.A_BUTTON , -power);
        aGamePadMotorRight = new GamePadMotor(this,gamepad1, robot.getRightDrive() , GamePadMotor.Control.A_BUTTON ,- power);

        //2 GamePadMotors instances to spin the robot Left
        xGamePadMotorLeft = new GamePadMotor(this,gamepad1, robot.getLeftDrive() , GamePadMotor.Control.X_BUTTON , 0);
        xGamePadMotorRight = new GamePadMotor(this,gamepad1, robot.getRightDrive() , GamePadMotor.Control.X_BUTTON, power);

        //2 GamePadMotors instances to spin the robot right
        bGamePadMotorLeft = new GamePadMotor(this,gamepad1, robot.getLeftDrive() , GamePadMotor.Control.B_BUTTON , power);
        bGamePadMotorRight = new GamePadMotor(this,gamepad1, robot.getRightDrive() , GamePadMotor.Control.B_BUTTON ,0);

        step = 1;

        yGamePadMotorLeft.startRunMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        yGamePadMotorRight.startRunMode(DcMotorController.RunMode.RUN_USING_ENCODERS);

        //getTelemetryUtil().setSortByTime(true);
    }

    /**
     * Implement this method to define the code to run when the Start button is pressed on the Driver station.
     * This method will be called on each hardware cycle just as the loop() method is called for event based Opmodes
     *  @throws InterruptedException
     */
    @Override
    protected void activeLoop() throws InterruptedException {

        if ( gamepad2.y) {
            currentControl =  GamePadMotor.Control.Y_BUTTON;
        }
        else if ( gamepad2.x) {
            currentControl =  GamePadMotor.Control.X_BUTTON;
        }
        else if ( gamepad2.a) {
            currentControl =  GamePadMotor.Control.A_BUTTON;
        }
        else if ( gamepad2.b) {
            currentControl =  GamePadMotor.Control.B_BUTTON;
        }

        switch (currentControl) {
            case Y_BUTTON:
                yGamePadMotorRight.update();
                yGamePadMotorLeft.update();
                break;
            case X_BUTTON:
                xGamePadMotorLeft.update();
                xGamePadMotorRight.update();
                break;
            case A_BUTTON:
                aGamePadMotorLeft.update();
                aGamePadMotorRight.update();
                break;
            case B_BUTTON:
                bGamePadMotorLeft.update();
                bGamePadMotorRight.update();
                break;
        }

        getTelemetryUtil().addData("Left Position", robot.getLeftDrive().getCurrentPosition());
        getTelemetryUtil().addData("RightPosition", robot.getRightDrive().getCurrentPosition());


        //send any telemetry that may have been added in the above operations
        getTelemetryUtil().sendTelemetry();


    }

}
