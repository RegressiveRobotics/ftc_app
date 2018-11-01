package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by johnb on 10/23/2018.
 */


@TeleOp(name="DriveTrain", group="TeleOp")
public class DriveTrainTeleOp extends LinearOpMode{

    //Declare OpMode members
    private ElapsedTime runtime = new ElapsedTime();

    private DcMotor leftInnerDrive = null;
    private DcMotor leftOuterDrive = null;

    private DcMotor rightInnerDrive = null;
    private DcMotor rightOuterDrive = null;


    //TeleOp Driving Settings
    double speedScale = 0.33;


    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();


        //Initialize hardware variables
        leftInnerDrive = hardwareMap.get(DcMotor.class, "leftInnerDrive");
        leftOuterDrive = hardwareMap.get(DcMotor.class, "leftOuterDrive");

        rightInnerDrive = hardwareMap.get(DcMotor.class, "rightInnerDrive");
        rightOuterDrive = hardwareMap.get(DcMotor.class, "rightOuterDrive");


        //Set Motor Polarities
        leftInnerDrive.setDirection(DcMotor.Direction.REVERSE);
        leftOuterDrive.setDirection(DcMotor.Direction.FORWARD);

        rightInnerDrive.setDirection(DcMotor.Direction.FORWARD);
        rightOuterDrive.setDirection(DcMotor.Direction.REVERSE);

        //wait for driver to press PLAY
        waitForStart();
        runtime.reset();

        //run until driver presses STOP
        while (opModeIsActive()) {

            //DRIVING
            {
                //Drive power variables
                double leftInnerPower;
                double leftOuterPower;
                double rightInnerPower;
                double rightOuterPower;

                //Tank mode controls

                //Take sum of values of joysticks and triggers, then clip them to 1.00
                leftInnerPower = Range.clip(-gamepad1.left_stick_y + -gamepad1.left_trigger + gamepad1.right_trigger, -1.0, 1.0);
                leftOuterPower = Range.clip(-gamepad1.left_stick_y + -gamepad1.left_trigger + gamepad1.right_trigger, -1.0, 1.0);

                rightInnerPower = Range.clip(-gamepad1.right_stick_y + -gamepad1.left_trigger + gamepad1.right_trigger, -1.0, 1.0);
                rightOuterPower = Range.clip(-gamepad1.right_stick_y + -gamepad1.left_trigger + gamepad1.right_trigger, -1.0, 1.0);


                //Scale power value for more controllable driving
                leftInnerPower = Range.scale(leftInnerPower,-1.00,1.00, -speedScale, speedScale);
                leftOuterPower = Range.scale(leftOuterPower,-1.00,1.00, -speedScale, speedScale);

                rightInnerPower = Range.scale(rightInnerPower,-1.00,1.00, -speedScale, speedScale);
                rightOuterPower = Range.scale(rightOuterPower,-1.00,1.00, -speedScale, speedScale);


                //Set Power
                leftInnerDrive.setPower(leftInnerPower);
                leftOuterDrive.setPower(leftOuterPower);

                rightInnerDrive.setPower(rightInnerPower);
                rightOuterDrive.setPower(rightOuterPower);


                //Runtime, and Power Variables being sent to motor
                telemetry.addData("Status", "Run Time: " + runtime.toString());
                telemetry.addData("Motors", "leftInner (%.2f), leftOuter (%.2f), rightInner (%.2f), rightOuter (%.2f)", leftInnerPower,leftOuterPower,rightInnerPower,rightOuterPower);
                telemetry.update();
            } //Driving END

        }

    }

}
