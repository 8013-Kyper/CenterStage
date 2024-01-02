package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.openftc.easyopencv.OpenCvCamera;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;


@Autonomous

public class redclose extends driveConstant {

    double teamElementPos;
    double distancex =5;



    Pose2d startPose = new Pose2d(23,-70, Math.toRadians(90));

    ElapsedTime time = new ElapsedTime();

    public void runOpMode() {
        initrobot(); //init motors

        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap); //init motors

        //init camera
        int cameraMonitorViewId = hardwareMap.appContext
                .getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        webcam = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "Webcam"), cameraMonitorViewId);
        Pipeline_red detector = new Pipeline_red(telemetry);

        webcam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened() {
                webcam.startStreaming(320, 240, OpenCvCameraRotation.UPRIGHT);
            }

            @Override
            public void onError(int errorCode) {

            }
        });

        //set starting point
        drive.setPoseEstimate(startPose);

        //trajectorys
        TrajectorySequence right = drive.trajectorySequenceBuilder(startPose)
                .splineTo(new Vector2d(23, -40), Math.toRadians(90))
                .addDisplacementMarker(()->{
                    //deliverPurple(100,.2);
                })
                .waitSeconds(1)
                .addDisplacementMarker(()->{
                    //resetIntake();
                    Crane.setTargetPosition(-1000);
                    Crane.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    Crane.setPower(.8);
                })
                .splineToSplineHeading(new Pose2d(46, -46, Math.toRadians(180)), Math.toRadians(0))
                .splineTo(new Vector2d(49, -46), Math.toRadians(0))
                .addDisplacementMarker(()->{
                    Crane.setTargetPosition(-2000);
                })
                .strafeRight(3)
                .waitSeconds(.5)
                .addDisplacementMarker(()->{
                    drop();
                })
                .waitSeconds(.5)
                .addDisplacementMarker(()->{
                    retract();
                })
                .strafeTo(new Vector2d(49,-58))
                .back(8)
                .build();

        TrajectorySequence left = drive.trajectorySequenceBuilder(startPose)
                .splineTo(new Vector2d(23, -40), Math.toRadians(90))
                .strafeTo(new Vector2d(6.7,-40))
                .addDisplacementMarker(()->{
                    deliverPurple(100,.2);
                })
                .waitSeconds(1)
                .addDisplacementMarker(()->{
                    resetIntake();
                    Crane.setTargetPosition(-1000);
                    Crane.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    Crane.setPower(.8);
                })
                .splineToSplineHeading(new Pose2d(46, -46, Math.toRadians(180)), Math.toRadians(0))
                .splineTo(new Vector2d(49, -46), Math.toRadians(0))
                .addDisplacementMarker(()->{
                    Crane.setTargetPosition(-2000);
                })
                .strafeRight(16)
                .waitSeconds(.5)
                .addDisplacementMarker(()->{
                    drop();
                })
                .waitSeconds(.5)
                .addDisplacementMarker(()->{
                    retract();
                })
                .strafeTo(new Vector2d(49,-58))
                .back(8)
                .build();

        TrajectorySequence center = drive.trajectorySequenceBuilder(startPose)
                .splineTo(new Vector2d(23, -35), Math.toRadians(90))
                .strafeTo(new Vector2d(10,-35))
                .addDisplacementMarker(()->{
                    deliverPurple(100,.2);
                })
                .waitSeconds(1)
                .addDisplacementMarker(()->{
                    resetIntake();
                    Crane.setTargetPosition(-1000);
                    Crane.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    Crane.setPower(.8);
                })
                .splineToSplineHeading(new Pose2d(46, -46, Math.toRadians(180)), Math.toRadians(0))
                .splineTo(new Vector2d(49, -46), Math.toRadians(0))
                .addDisplacementMarker(()->{
                    Crane.setTargetPosition(-2000);
                })
                .strafeRight(12)
                .waitSeconds(.5)
                .addDisplacementMarker(()->{
                    drop();

                })
                .waitSeconds(.5)
                .addDisplacementMarker(()->{
                    retract();

                })
                .strafeTo(new Vector2d(49,-58))
                .back(8)
                .build();

        waitForStart();


        if (opModeIsActive()){

            //find game element
            switch (detector.getLocation()) {
                case LEFT:
                    teamElementPos = 1;
                    break;
                case RIGHT:
                    teamElementPos = 2;
                    break;
                case MIDDLE:
                    teamElementPos = 3;
                    break;
                case NOT_FOUND:
                    teamElementPos = 2;//should be 4
                    break;


            }


            if (teamElementPos == 2) {

                drive.followTrajectorySequence(right);


            }
            if (teamElementPos == 1) {

                //drive.followTrajectorySequence(left);

            }
            if (teamElementPos == 3) {

                //drive.followTrajectorySequence(center);

            }
            if (teamElementPos == 4) {


            }
        }
    }

}