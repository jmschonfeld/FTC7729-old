package org.ftc7729.opmodes;

import com.qualcomm.ftcrobotcontroller.FtcRobotControllerActivity;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.ftc7729.FTCTeamRobot;
import org.ftcbootstrap.ActiveOpMode;
import org.ftcbootstrap.components.operations.motors.TankDriveToODS;
import org.ftcbootstrap.components.operations.motors.TankDriveToTime;
import org.ftcbootstrap.components.utils.DriveDirection;

/**
 * Created by Jeremy Schonfeld on 1/29/2016.
 */
public class AutonomousBlue extends ActiveOpMode {
    private FTCTeamRobot robot;
    private TankDriveToODS driveODS;
    private TankDriveToTime driveTime;
    private int step, side;
    private long startTime = 0;


    private int index = 100000;

    //loads the OpenCV library into the program
    static{ System.loadLibrary("opencv_java3"); }

    /**
     * Implement this method to define the code to run when the Init button is pressed on the Driver station.
     */
    @Override
    protected void onInit() {

        robot = FTCTeamRobot.newConfig(hardwareMap, getTelemetryUtil());
        //Note The Telemetry Utility is designed to let you organize all telemetry data before sending it to
        //the Driver station via the sendTelemetry command
        robot.getDriveLeft().setDirection(DcMotor.Direction.FORWARD);
        robot.getDriveRight().setDirection(DcMotor.Direction.REVERSE);
        getTelemetryUtil().addData("Init", getClass().getSimpleName() + " initialized.");
        getTelemetryUtil().sendTelemetry();

    }

    @Override
    protected void onStart() throws InterruptedException {
        super.onStart();
        step = 3;
        startTime = System.currentTimeMillis();
    }

    private TankDriveToODS getDriveODS() {
        //reverse because robot drives backwards
        return new TankDriveToODS(this, robot.getOpticalDistanceSensor(), robot.getDriveRight(), robot.getDriveLeft());
    }

    private TankDriveToTime getDriveTime() {
        //reverse because robot drives backwards
        return new TankDriveToTime(this, robot.getDriveRight(), robot.getDriveLeft());
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
        getTelemetryUtil().addData("runtime", (int)(((double)(System.currentTimeMillis()-startTime))/1000.0));

        switch(step) {
            case 0: //drive to white line
                if (driveODS == null) {
                    driveODS = getDriveODS();
                } else {
                    if (driveODS.runToTarget(0.8, 0.03, DriveDirection.DRIVE_FORWARD)) {
                        driveODS = null;
                        step++;
                    }
                }
                break;
            case 1: //drive past white line
                if (driveTime == null) {
                    driveTime = getDriveTime();
                } else {
                    if (driveTime.runToTarget(0.5, 0.4, DriveDirection.DRIVE_FORWARD)) {
                        driveTime = null;
                        step++;
                    }
                }
                break;
            case 2: //turn to align with white line
                if (driveODS == null) {
                    driveODS = getDriveODS();
                } else {
                    if (driveODS.runToTarget(0.25, 0.03, DriveDirection.SPIN_RIGHT)) {
                        driveODS = null;
                        step++;
                    }
                }
                break;
            case 3: //spin towards the correct button
                if (driveTime == null) {
                    driveTime = getDriveTime();
                    side = ((FtcRobotControllerActivity)hardwareMap.appContext).getDetector().getSide();
                } else {
                    //reversed directions because the camera calibrates to the red color (ex. red on left, go right)
                    if (driveTime.runToTarget(0.25, 0.3, side == 0 ? DriveDirection.SPIN_RIGHT : DriveDirection.SPIN_LEFT)) {
                        driveTime = null;
                        step++;
                    }
                }
                break;
            case 4: //move forward towards button
                if (driveTime == null) {
                    driveTime = getDriveTime();
                } else {
                    if (driveTime.runToTarget(0.25, 1, DriveDirection.DRIVE_FORWARD)) {
                        driveTime = null;
                        step++;
                    }
                }
                break;
            case 5: //move backwards away button
                if (driveTime == null) {
                    driveTime = getDriveTime();
                } else {
                    if (driveTime.runToTarget(0.25, 1, DriveDirection.DRIVE_BACKWARD)) {
                        driveTime = null;
                        step++;
                    }
                }
                break;
            case 6: //spin to align with white line
                if (driveTime == null) {
                    driveTime = getDriveTime();
                } else {
                    if (driveTime.runToTarget(0.25, 0.3, side == 1 ? DriveDirection.SPIN_RIGHT : DriveDirection.SPIN_LEFT)) {
                        driveTime = null;
                        step++;
                    }
                }
                break;
            case 7: //move backwards
                if (driveTime == null) {
                    driveTime = getDriveTime();
                } else {
                    if (driveTime.runToTarget(0.5, 2, DriveDirection.DRIVE_BACKWARD)) {
                        driveTime = null;
                        step++;
                    }
                }
                break;
            case 8: //turn towards mountain
                if (driveTime == null) {
                    driveTime = getDriveTime();
                } else {
                    if (driveTime.runToTarget(0.5, 1.25, DriveDirection.SPIN_LEFT)) {
                        driveTime = null;
                        step++;
                    }
                }
                break;
            case 9: //drive up mountain
                if (driveTime == null) {
                    driveTime = getDriveTime();
                } else {
                    if (driveTime.runToTarget(1, 10, DriveDirection.DRIVE_BACKWARD)) {
                        driveTime = null;
                        step++;
                    }
                }
                break;
            default:
                getTelemetryUtil().addData("info", "PROCESS COMPLETE");
                break;
        }


        //send any telemetry that may have been added in the above operations
        getTelemetryUtil().sendTelemetry();



    }

    /*public void steer(double direction, double precision, double speed) {

        if (direction >= .5 - precision && direction <= .5 + precision) {
            robot.getDriveLeft().setPower(speed);
            robot.getDriveRight().setPower(speed);
        } else if (direction < .5 - precision) {
            robot.getDriveRight().setPower(0);
        } else {
            robot.getDriveLeft().setPower(0);
            robot.getDriveRight().setPower(0.5);
        }


        //direction = ( 0.5f -  direction) / 2;
        //getTelemetryUtil().addData("dirMap", direction);

        //by now, direction will have been mapped to a value between -0.25 and 0.25 ( (.5 - dir) / 2 )

        /*
        This is something that hughes attempted to use instead of the line above
        double adjust;
        double scaleFactor;
        double basePower = .5;

        adjust = 0.5 - direction;
        scaleFactor = .85;

        robot.getDriveRight().setPower(basePower - adjust*scaleFactor);
        robot.getDriveLeft().setPower(basePower + adjust*scaleFactor);
        getTelemetryUtil().addData("powerLeft", .5-direction);
        getTelemetryUtil().addData("powerRight", .5+direction);
        robot.getDriveLeft().setPower(.5-direction); //initialy left was + and right was -
        robot.getDriveRight().setPower(.5+direction);
    }*/
}
