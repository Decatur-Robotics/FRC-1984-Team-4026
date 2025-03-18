package frc.robot.Commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.constants.ArmConstants;
import frc.robot.constants.ClawConstants;
import frc.robot.constants.ElevatorConstants;
import frc.robot.constants.IntakeConstants;
import frc.robot.subsystems.ArmSubsytem;
import frc.robot.subsystems.ElevatorSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.ClawSubsystem;

public class IntakeCommand extends Command {

	private IntakeSubsystem intake;
	private ArmSubsytem arm;
	private ElevatorSubsystem elevator;
	private ClawSubsystem claw;
	private boolean limitSwitchPressed;
	private double desiredVelocity;


	public IntakeCommand(IntakeSubsystem intake, double desiredVelocity) {
		this.intake = intake;
		this.desiredVelocity = desiredVelocity;

		addRequirements(intake);
	}
	
	@Override
	public void initialize() {
		
		intake.setVelocity(IntakeConstants.INTAKING_VELOCITY);
		intake.setRotation(IntakeConstants.INTAKE_OUT_ROTATION);
		elevator.setTargetPosition(ElevatorConstants.INTAKE_HEIGHT);
		claw.setClawSpeed(ClawConstants.CLAW_INTAKE_VELOCITY);
		arm.setTargetRotation(ArmConstants.ARM_INTAKE_POSITION);


	}
	
	@Override
	public void execute() {
		limitSwitchPressed = claw.getLimitSwitch();
		if(limitSwitchPressed){
			claw.setClawSpeed(0);
			intake.setVelocity(desiredVelocity);
			intake.setRotation(IntakeConstants.INTAKE_IN_ROTATION);
		}
	}
	
	@Override
	public boolean isFinished() {
		return limitSwitchPressed;
	}
	
	
}

