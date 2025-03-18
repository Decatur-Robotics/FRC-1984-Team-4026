package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.lib.core.LogitechControllerButtons;
import frc.robot.constants.ElevatorPosition;
import frc.robot.constants.IntakeConstants;
import frc.robot.subsystems.ArmSubsytem;
import frc.robot.subsystems.ClawSubsystem;
import frc.robot.subsystems.ElevatorSubsystem;
import frc.robot.subsystems.IntakeSubsystem;

/**
 * The container for the robot. Contains subsystems, OI devices, and commands.
 */
public class RobotContainer
{

	private static RobotContainer instance;

	private final ShuffleboardTab ShuffleboardTab;

	private ArmSubsytem armSubsytem;
	private ClawSubsystem clawSubsystem;
	private IntakeSubsystem intakeSubsystem;
	private ElevatorSubsystem elevatorSubsystem;

	

	/**
	 * The container for the robot. Contains subsystems, OI devices, and commands.
	 */
	public RobotContainer()
	{
		instance = this;

		ShuffleboardTab = Shuffleboard.getTab("Tab 1");

		// Instantiate subsystems
		armSubsytem = new ArmSubsytem();
		clawSubsystem = new ClawSubsystem();
		intakeSubsystem = new IntakeSubsystem();
		elevatorSubsystem = new ElevatorSubsystem();

		// Configure the button bindings
		configurePrimaryBindings();
		configureSecondaryBindings();
	}

	private void configurePrimaryBindings()
	{

		final Joystick controller = new Joystick(0);

		// TODO: REGISTER SUBSYSTEMS and I think make them final (check github)

		final JoystickButton LeftTrigger = new JoystickButton(controller,
				LogitechControllerButtons.triggerLeft);
		final JoystickButton RightTrigger = new JoystickButton(controller,
				LogitechControllerButtons.triggerRight);
		final JoystickButton LeftBumper = new JoystickButton(controller,
				LogitechControllerButtons.bumperLeft);
		final JoystickButton BumperRight = new JoystickButton(controller,
				LogitechControllerButtons.bumperRight);
		final JoystickButton AButton = new JoystickButton(controller, LogitechControllerButtons.a);
		final JoystickButton BButton = new JoystickButton(controller, LogitechControllerButtons.b);
		final JoystickButton XButton = new JoystickButton(controller, LogitechControllerButtons.x);
		final JoystickButton YButton = new JoystickButton(controller, LogitechControllerButtons.y);
		final JoystickButton UpButton = new JoystickButton(controller,
				LogitechControllerButtons.up);
		final JoystickButton DownButton = new JoystickButton(controller,
				LogitechControllerButtons.down);
		final JoystickButton LeftButton = new JoystickButton(controller,
				LogitechControllerButtons.left);
		final JoystickButton RightButton = new JoystickButton(controller,
				LogitechControllerButtons.right);

		// Claw buttons
		RightTrigger.whileTrue(clawSubsystem.clawCommand(0));

		BumperRight.whileTrue(clawSubsystem.clawCommand(1.0));

		// Elevator buttons
		YButton.whileTrue(elevatorSubsystem.elevatorCommand(ElevatorPosition.UPPER));

		XButton.whileTrue(elevatorSubsystem.elevatorCommand(ElevatorPosition.MIDDLE));
		AButton.whileTrue(elevatorSubsystem.elevatorCommand(ElevatorPosition.LOWER));

		// Intake buttons
		LeftTrigger.whileTrue(intakeSubsystem.intakeCommand(IntakeConstants.INTAKING_VELOCITY));

		BButton.whileTrue(intakeSubsystem.intakeCommand(IntakeConstants.INTAKING_VELOCITY));

		LeftBumper.whileTrue(intakeSubsystem.intakeCommand(IntakeConstants.INTAKING_VELOCITY));

		// Arm buttons
		DownButton.whileTrue(armSubsytem.armCommand(-1));
		UpButton.whileTrue(armSubsytem.armCommand(1));
		LeftButton.whileTrue(armSubsytem.armCommand(0));
		RightButton.whileTrue(armSubsytem.armCommand(0));

	}

	private void configureSecondaryBindings()
	{}

	@SuppressWarnings("unused")
	public static ShuffleboardTab getShuffleboardTab()
	{
		return instance.ShuffleboardTab;
	}

}
