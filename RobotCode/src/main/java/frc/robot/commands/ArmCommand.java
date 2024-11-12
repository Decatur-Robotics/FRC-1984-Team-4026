package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.constants.ArmConstants;
import frc.robot.subsystems.ArmSubsytem;

public class ArmCommand extends Command
{
	public ArmSubsytem arm;
	public double targetAngle;

	public ArmCommand()
	{		
		targetAngle = ArmConstants.ARM_INITIAL_POSITION;

		addRequirements(arm);
	
	}

	public void initialize(){
		arm.setTargetRotation(targetAngle);
	}
}
