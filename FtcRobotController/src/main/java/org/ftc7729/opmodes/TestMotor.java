/* Copyright (c) 2015 Qualcomm Technologies Inc
All rights reserved.
Redistribution and use in source and binary forms, with or without modification,
are permitted (subject to the limitations in the disclaimer below) provided that
the following conditions are met:
Redistributions of source code must retain the above copyright notice, this list
of conditions and the following disclaimer.
Redistributions in binary form must reproduce the above copyright notice, this
list of conditions and the following disclaimer in the documentation and/or
other materials provided with the distribution.
Neither the name of Qualcomm Technologies Inc nor the names of its contributors
may be used to endorse or promote products derived from this software without
specific prior written permission.
NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
"AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE. */

package org.ftc7729.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * A simple example of a linear op mode that will approach an IR beacon
 */
public class TestMotor extends LinearOpMode {

    final static double motor_power = 1.00; // Higher values will cause the robot to move faster

    DcMotor motorRight;
    DcMotor motorLeft;

    DcMotor motorTape;
    DcMotor motorAim;

    DcMotor motorHinges;

    double position = 0.38;
    double motorMaxPercentage = .05;


    @Override
    public void runOpMode() throws InterruptedException {

        // set up the hardware devices we are going to use
        motorRight = hardwareMap.dcMotor.get("right_drive");
        motorLeft = hardwareMap.dcMotor.get("left_drive");
        motorTape = hardwareMap.dcMotor.get("tape_lift");
        motorAim = hardwareMap.dcMotor.get("tape_aim");
        motorHinges = hardwareMap.dcMotor.get("hit_climbers");

        motorLeft.setDirection(DcMotor.Direction.REVERSE);
        motorAim.setDirection(DcMotor.Direction.REVERSE);
        motorHinges.setDirection(DcMotor.Direction.REVERSE);

        // wait for the start button to be pressed
        waitForStart();
        motorRight.setPower(motor_power);
        sleep(1000);
        motorRight.setPower(-motor_power);
        sleep(1000);
        motorRight.setPower(0.00);
        sleep(1000);

        motorLeft.setPower(motor_power);
        sleep(1000);
        motorLeft.setPower(-motor_power);
        sleep(1000);
        motorLeft.setPower(0.00);
        sleep(1000);

        motorTape.setPower(motor_power / 4);
        sleep(1000);
        motorTape.setPower(-motor_power / 4);
        sleep(1000);
        motorTape.setPower(0.00);
        sleep(1000);

        motorAim.setPower(motor_power * motorMaxPercentage);
        sleep(1000);
        motorAim.setPower(-motor_power * motorMaxPercentage);
        sleep(1000);
        motorAim.setPower(0.00);
        sleep(1000);

        motorHinges.setPower(.5);
        sleep(500);
        motorHinges.setPower(-.5);
        sleep(500);
        motorHinges.setPower(0.00);
        sleep(1000);

    }

    public void stopRobot() throws InterruptedException {
        // stop the motors
        motorRight.setPower(0);
        motorLeft.setPower(0);
    }

    public void turnLeft(int i) throws InterruptedException {
        for (int p = 0; p < i; p++) {
            motorLeft.setPower(-motor_power);
            motorRight.setPower(motor_power);
            sleep(1);
            telemetry.addData("right tgt pwr", "right pwr: " + String.format("%.2f", motor_power));
        }
    }

    public void turnRight(int i) throws InterruptedException {
        for (int p = 0; p < i; p++) {
            motorLeft.setPower(motor_power);
            motorRight.setPower(-motor_power);
            sleep(1);
            telemetry.addData("right tgt pwr", "right pwr: " + String.format("%.2f", motor_power));
        }
    }

    public void goStraight(int i) throws InterruptedException {
        for(int p = 0; p < i; p++) {
            motorRight.setPower(motor_power);
            motorLeft.setPower(motor_power);
            sleep(1);
            telemetry.addData("right tgt pwr", "right pwr: " + String.format("%.2f", motor_power));
        }
    }

}