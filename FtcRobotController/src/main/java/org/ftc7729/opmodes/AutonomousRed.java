package org.ftc7729.opmodes;

import android.util.Log;

import com.qualcomm.ftcrobotcontroller.FtcRobotControllerActivity;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.ftc7729.FTCTeamRobot;
import org.ftcbootstrap.ActiveOpMode;
import org.ftcbootstrap.components.operations.motors.GamePadTankDrive;
import org.ftcbootstrap.components.operations.motors.TankDrive;
import org.ftcbootstrap.components.operations.motors.TankDriveToODS;
import org.ftcbootstrap.components.operations.motors.TankDriveToTime;
import org.ftcbootstrap.components.utils.DriveDirection;

/**
 * Created by Jeremy Schonfeld on 1/29/2016.
 */
public class AutonomousRed extends ActiveOpMode {
    private FTCTeamRobot robot;
    private TankDriveToODS driveODS;
    private TankDriveToTime driveTime;
    private int step;


    private int index = 100000;
    private float localDirection;

    static{ System.loadLibrary("opencv_java3"); }

    /**
     * Implement this method to define the code to run when the Init button is pressed on the Driver station.
     */
    @Override
    protected void onInit() {

        robot = FTCTeamRobot.newConfig(hardwareMap, getTelemetryUtil());
        //Note The Telemetry Utility is designed to let you organize all telemetry data before sending it to
        //the Driver station via the sendTelemetry command
        getTelemetryUtil().addData("Init", getClass().getSimpleName() + " initialized.");
        getTelemetryUtil().sendTelemetry();

    }

    @Override
    protected void onStart() throws InterruptedException {
        super.onStart();
        step = 3;

    }

    /**
     * Implement this method to define the code to run when the Start button is pressed on the Driver station.
     * This method will be called on each hardware cycle just as the loop() method is called for event based Opmodes
     *
     * @throws InterruptedException
     */
    @Override
    protected void activeLoop() throws InterruptedException {

        getTelemetryUtil().addData("step", step);

        switch(step) {
            case 0: //drive to white line
                if (driveODS == null) {
                    driveODS = new TankDriveToODS(this, robot.getOpticalDistanceSensor(), robot.getDriveLeft(), robot.getDriveRight());
                    driveODS.runToTarget(0.3, 0.0, DriveDirection.DRIVE_FORWARD);
                } else {
                    if (!driveODS.isDriving()) {
                        driveODS = null;
                        step++;
                    }
                }
                break;
            case 1: //drive past white line
                if (driveTime == null) {
                    driveTime = new TankDriveToTime(this, robot.getDriveLeft(), robot.getDriveRight());
                    driveTime.runToTarget(0.3, 1.0, DriveDirection.DRIVE_FORWARD);
                } else {
                    if (!driveTime.isDriving()) {
                        driveTime = null;
                        step++;
                    }
                }
                break;
            case 2: //turn to align with white line
                if (driveODS == null) {
                    driveODS = new TankDriveToODS(this, robot.getOpticalDistanceSensor(), robot.getDriveLeft(), robot.getDriveRight());
                    driveODS.runToTarget(0.0, 0.0, DriveDirection.SPIN_RIGHT);
                } else {
                    driveODS = null;
                    step++;
                }
                break;
            case 3: //image analysis
                if(index >0){
                    localDirection =((FtcRobotControllerActivity) hardwareMap.appContext).getDirection();
                    if (localDirection > 0) {
                        Log.i("RJG", "direction =" + localDirection);
                        steer(((float).5 -  localDirection) / 10);
                    }else {
                        index = 0;
                    }
                } else {
                    step++;
                }
                return;
                //break;
            case 4: //back up
                if (driveTime == null) {
                    driveTime = new TankDriveToTime(this, robot.getDriveLeft(), robot.getDriveRight());
                    driveTime.runToTarget(0.3, 1.0, DriveDirection.DRIVE_BACKWARD);
                } else {
                    if (!driveTime.isDriving()) {
                        driveTime = null;
                        step++;
                    }
                }
                break;
            case 5: //turn towards mountain
                if (driveTime == null) {
                    driveTime = new TankDriveToTime(this, robot.getDriveLeft(), robot.getDriveRight());
                    driveTime.runToTarget(0.3, 1.0, DriveDirection.DRIVE_FORWARD);
                } else {
                    if (!driveTime.isDriving()) {
                        driveTime = null;
                        step++;
                    }
                }
                break;
            case 6: //
                if (driveTime == null) {
                    driveTime = new TankDriveToTime(this, robot.getDriveLeft(), robot.getDriveRight());
                    driveTime.runToTarget(0.3, 1.0, DriveDirection.DRIVE_FORWARD);
                } else {
                    if (!driveTime.isDriving()) {
                        driveTime = null;
                        step++;
                    }
                }
                break;
        }


        //send any telemetry that may have been added in the above operations
        getTelemetryUtil().sendTelemetry();



    }

    public void steer(float direction) {
        robot.getDriveRight().setPower(.1+direction);
        robot.getDriveLeft().setPower(.1-direction);
    }
}
