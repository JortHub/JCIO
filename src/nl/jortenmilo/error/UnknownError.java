package nl.jortenmilo.error;

import nl.jortenmilo.console.Console;
import nl.jortenmilo.console.ConsoleUser;
import nl.jortenmilo.utils.defaults.SystemUtils;

/**
 * This error is thrown when a error is thrown, but it isn't casted to a specific error.
 * @see Error
 */
public class UnknownError extends Error {
	
	private String value1;
	private String value2;
	
	public UnknownError(String value1, String value2) {
		this.value1 = value1;
		this.value2 = value2;
	}
	
	@Override
	public void print() {
		Console.debug("ERROR [" + new SystemUtils().getTime() + "][UnknownError][" + value1 + ", " + value2 + "]");
		
		Console.println(ConsoleUser.Error, value1);
		Console.println(" (" + value2 + ")");
		this.printStackTrace();
		this.printHelp();
		this.event();
	}
	
}
