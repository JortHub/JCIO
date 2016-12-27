package nl.jortenmilo.command;

public class CommandPreExecuteEvent extends CommandEvent {
	
	private String[] arguments;
	
	/**
	 * Returns the arguments that were used when this event was created.
	 * @return The arguments
	 */
	public String[] getArguments() {
		return arguments;
	}

	protected void setArguments(String[] arguments) {
		this.arguments = arguments;
	}
	
}
