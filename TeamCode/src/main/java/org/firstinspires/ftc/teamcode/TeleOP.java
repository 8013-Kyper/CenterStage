package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;                //imports from FIRST
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.hardware.rev.Rev2mDistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;


import org.firstinspires.ftc.robotcore.internal.camera.delegating.DelegatingCaptureSequence;

@TeleOp
public class TeleOP extends driveConstant {
        @Override
        public void init() {
            initrobot();
        }
        @Override
        public void loop() {
            double turn;
            double throttle;
            boolean strafeLeft;
            boolean strafeRight;

            boolean retract;
            boolean extend;

            boolean windshieldwiper;

            boolean intakeClose;                                   //setting varibles from conteroler imputs
            boolean intakeOpen;

            double intake2Close;
            double intake2Open;


            double cranepower;
            double Intakepower;




            //setting controls on controller (initializing variables)
            throttle = gamepad1.left_stick_y;
            turn = gamepad1.right_stick_x;
            strafeLeft = gamepad1.left_bumper;
            strafeRight = gamepad1.right_bumper;

            cranepower = gamepad2.right_stick_y;

            intakeClose = gamepad2.right_bumper;
            intakeOpen = gamepad2.left_bumper;

            intake2Close = gamepad2.right_trigger;
            intake2Open = gamepad2.left_trigger;

            windshieldwiper = gamepad2.y;

            extend = gamepad2.a;
            retract = gamepad2.b;

            Intakepower = gamepad2.left_stick_y;


            if (strafeRight) {
                frontLeft.setPower(-1);
                frontRight.setPower(1);                         //conecting motor varibles to controler inputs
                backLeft.setPower(1);
                backRight.setPower(-1);
            }
            if (strafeLeft) {
                frontLeft.setPower(1);
                frontRight.setPower(-1);
                backLeft.setPower(-1);
                backRight.setPower(1);
            }


            frontLeft.setPower(throttle);
            frontRight.setPower(throttle*.97);
            backLeft.setPower(throttle);
            backRight.setPower(throttle*.97);

            frontLeft.setPower(-turn);
            frontRight.setPower(turn);
            backLeft.setPower(-turn);
            backRight.setPower(turn);


            Crane.setPower(cranepower);


            if (intakeClose){
                leftServo.setPosition(0);
            }
            if (intakeOpen){
                leftServo.setPosition(.5);
            }

            if (intake2Close>0){
                rightServo.setPosition(0);
            }

            if (intake2Open>0){
                rightServo.setPosition(.5);
            }

            if(windshieldwiper){
                rightServo.setPosition(0);
                mRuntime.reset();
                if (mRuntime.seconds() >2)
                    rightServo.setPosition(1);
            }






            if(extend){
                Crane.setPower(-1);
                Crane.setTargetPosition(-2290);
                Crane.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            }

            if(retract) {
                Crane.setPower(1);
                Crane.setTargetPosition(-10);
                Crane.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            }

            Intake.setPower(Intakepower*.90);


            telemetry.addData("fL",frontLeft.getCurrentPosition());
            telemetry.addData("fR",frontRight.getCurrentPosition());
            telemetry.addData("bL",backLeft.getCurrentPosition());
            telemetry.addData("bR",backRight.getCurrentPosition());
            telemetry.addData("crane",Crane.getCurrentPosition());
            telemetry.update();




        }
    public ElapsedTime mRuntime = new ElapsedTime();

}
