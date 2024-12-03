package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.lib.core.LogitechControllerButtons;
import frc.robot.commands.ArmCommand;
import frc.robot.commands.ClawCommand;
import frc.robot.commands.ElevateCommand;
import frc.robot.commands.IntakeCommand;
import frc.robot.constants.ElevatorPosition;
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

	public ArmSubsytem armSubsytem;
	public ClawSubsystem clawSubsystem;
	public IntakeSubsystem intakeSubsystem;
	public ElevatorSubsystem elevatorSubsystem;

	/**
	 * The container for the robot. Contains subsystems, OI devices, and commands.
	 */
	public RobotContainer() {
		instance = this;

		ShuffleboardTab = Shuffleboard.getTab("Tab 1");

		// Instantiate subsystems

		// Configure the button bindings
		configurePrimaryBindings();
		configureSecondaryBindings();
	}

	private void configurePrimaryBindings() {

		final Joystick controller = new Joystick(0);

		// TODO: REGISTER SUBSYSTEMS and I think make them final (check github)


		new JoystickButton(controller, LogitechControllerButtons.triggerRight)
			.onTrue(new ClawCommand(clawSubsystem, 0));
			
		new JoystickButton(controller, LogitechControllerButtons.bumperRight)
			.onTrue(new ClawCommand(clawSubsystem, 1.0));

		
		new JoystickButton(controller, LogitechControllerButtons.y)
			.onTrue(new ElevateCommand(elevatorSubsystem, ElevatorPosition.UPPER));
		new JoystickButton(controller, LogitechControllerButtons.x)
			.onTrue(new ElevateCommand(elevatorSubsystem, ElevatorPosition.MIDDLE));
		new JoystickButton(controller, LogitechControllerButtons.a)
			.onTrue(new ElevateCommand(elevatorSubsystem, ElevatorPosition.LOWER));

			
		new JoystickButton(controller, LogitechControllerButtons.triggerLeft)
			.onTrue(new IntakeCommand(intakeSubsystem, 1.0, 0));

		new JoystickButton(controller, LogitechControllerButtons.b)
			.onTrue(new IntakeCommand(intakeSubsystem, 0, 1));
		
		new JoystickButton(controller, LogitechControllerButtons.bumperLeft)
			.onTrue(new IntakeCommand(intakeSubsystem, -1.0, 0));

		new JoystickButton(controller, LogitechControllerButtons.down)
			.onTrue(new ArmCommand(armSubsytem, -1));
		new JoystickButton(controller, LogitechControllerButtons.up).onTrue(new ArmCommand(armSubsytem, 1));
		new JoystickButton(controller, LogitechControllerButtons.left)
			.onTrue(new ArmCommand(armSubsytem, 0));
		new JoystickButton(controller, LogitechControllerButtons.right)
			.onTrue(new ArmCommand(armSubsytem, 0));

	}

	private void configureSecondaryBindings() {}

	@SuppressWarnings("unused")
	public static ShuffleboardTab getShuffleboardTab() {
		return instance.ShuffleboardTab;
	}

}
