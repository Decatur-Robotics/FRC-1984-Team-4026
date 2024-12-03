package frc.robot.Commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.constants.IntakeConstants;
import frc.robot.subsystems.IntakeSubsystem;

public class IntakeCommand extends Command
{
	private IntakeSubsystem intake;
	private double desiredVelocity;
	private double desiredRotation;
	public IntakeCommand(IntakeSubsystem intake, double desiredVelocity, double desiredRotation){
		this.intake = intake;
		this.desiredVelocity = desiredVelocity;
		this.desiredRotation = desiredRotation;
		addRequirements(intake);
	}
	@Override
	public void initialize(){
		intake.setVelocity(this.desiredVelocity);
		intake.setRotation(this.desiredRotation);
	}
}

