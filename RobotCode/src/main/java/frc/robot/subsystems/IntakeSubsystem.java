package frc.robot.subsystems;


import java.util.function.BooleanSupplier;

import javax.swing.plaf.TreeUI;

import com.ctre.phoenix6.configs.MotionMagicConfigs;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.controls.MotionMagicDutyCycle;
import com.ctre.phoenix6.hardware.TalonFX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkBase.FaultID;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkLowLevel.PeriodicFrame;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.ArmConstants;
import frc.robot.constants.ClawConstants;
import frc.robot.constants.ElevatorConstants;
import frc.robot.constants.IntakeConstants;
import frc.robot.constants.Ports;

public class IntakeSubsystem extends SubsystemBase {
	// This makes variables for the 3 essential motors
	private TalonFX intakeMotorLeft, intakeMotorRight;
	private CANSparkMax intakeFeedMotor;
	private double desiredRotation, desiredVelocity;
	private MotionMagicDutyCycle motorControlRequest;

	private IntakeSubsystem intake;
	private ClawSubsystem claw;
	private boolean limitSwitchPressed;

	private ArmSubsytem arm;
	private ElevatorSubsystem elevator;

	BooleanSupplier limitSwitch;

	

	public IntakeSubsystem() {
		// This sets up the 3 essential motors
		intakeMotorLeft = new TalonFX(Ports.INTAKE_LEFT_MOTOR);
		intakeMotorRight = new TalonFX(Ports.INTAKE_RIGHT_MOTOR);
		intakeFeedMotor = new CANSparkMax(Ports.INTAKE_FEED, MotorType.kBrushless);

		desiredRotation = 0;
		
		intakeMotorLeft.setControl(new Follower(intakeMotorRight.getDeviceID(), true));
		
		TalonFXConfiguration motorConfigs = new TalonFXConfiguration();
		Slot0Configs pidSlot0Configs = motorConfigs.Slot0;
		pidSlot0Configs.kP = IntakeConstants.INTAKE_KP;
		pidSlot0Configs.kI = IntakeConstants.INTAKE_KI;
		pidSlot0Configs.kD = IntakeConstants.INTAKE_KD;
		pidSlot0Configs.kS = IntakeConstants.INTAKE_KS;
		pidSlot0Configs.kV = IntakeConstants.INTAKE_KV;
		pidSlot0Configs.kA = IntakeConstants.INTAKE_KA;

		MotionMagicConfigs motionMagicVelocityConfigs = motorConfigs.MotionMagic;
		motionMagicVelocityConfigs.MotionMagicCruiseVelocity = IntakeConstants.INTAKE_CRUISE_VELOCITY;
		motionMagicVelocityConfigs.MotionMagicAcceleration = IntakeConstants.INTAKE_ACCELERATION;
		
		motorControlRequest = new MotionMagicDutyCycle(desiredRotation);
		intakeMotorRight.setControl(motorControlRequest.withPosition(desiredRotation));
		intakeMotorRight.getConfigurator().apply(motorConfigs);


		
	}	
	public void periodic(){
		if(intakeMotorLeft.hasResetOccurred()||intakeMotorRight.hasResetOccurred()){
			intakeMotorLeft.optimizeBusUtilization();
			intakeMotorRight.optimizeBusUtilization();
			intakeMotorRight.getRotorPosition().setUpdateFrequency(20);
		}
		if(intakeFeedMotor.getStickyFault(FaultID.kHasReset)){
			intakeFeedMotor.setPeriodicFramePeriod(PeriodicFrame.kStatus0, 20);
			intakeFeedMotor.setPeriodicFramePeriod(PeriodicFrame.kStatus1, 20);
		}
		
	}
	public void setRotation(double desiredRotation){
		this.desiredRotation = desiredRotation;
		intakeMotorRight.setControl(motorControlRequest.withPosition(desiredRotation));
	}
	public void setVelocity(double desiredVelocity)
	{
		this.desiredVelocity = desiredVelocity;
		intakeFeedMotor.set(IntakeConstants.INTAKE_KV + desiredVelocity);
	}
	public Command initalizeIntake(){
		return Commands.runOnce(() -> {
		intake.setVelocity(IntakeConstants.INTAKING_VELOCITY);
		intake.setRotation(IntakeConstants.INTAKE_OUT_ROTATION);
		elevator.setTargetPosition(ElevatorConstants.INTAKE_HEIGHT);
		claw.setClawSpeed(ClawConstants.CLAW_INTAKE_VELOCITY);
		arm.setTargetRotation(ArmConstants.ARM_INTAKE_POSITION);
		});
	}



	public Command intakeCommand(double desiredVelocity) {
		return Commands.run(() -> {
			limitSwitchPressed = claw.getLimitSwitch();
			if(limitSwitchPressed){
				claw.setClawSpeed(0);
				intake.setVelocity(desiredVelocity);
				intake.setRotation(IntakeConstants.INTAKE_IN_ROTATION);
			}
	
		}, this).beforeStarting(initalizeIntake());
	}

}

	






