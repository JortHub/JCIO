package nl.jortenmilo.error;

import nl.jortenmilo.console.Console;
import nl.jortenmilo.console.ConsoleUser;
import nl.jortenmilo.event.EventHandler;
import nl.jortenmilo.utils.defaults.SystemUtils;

/**
 * This error is thrown when the main class in the plugin.jcio wasn't found.
 * @see Error
 */
public class ClassNotFoundError extends Error {
	
	private String value1;
	private String value2;
	
	public ClassNotFoundError(String value1, String value2) {
		this.value1 = value1;
		this.value2 = value2;
	}
	
	@Override
	public void print() {
		Console.debug("ERROR [" + new SystemUtils().getTime() + "][ClassNotFound][" + value1 + ", " + value2 + "]");
		
		Console.println(ConsoleUser.Error, "ClassNotFound: The class '" + value1 + "' is not found!");
		Console.println(ConsoleUser.Error, "Caused by: " + value2);
		Console.println(ConsoleUser.Error, "If you don't know what to do, please contact us at: goo.gl/1ROGMh.");
		
		ErrorThrownEvent event = new ErrorThrownEvent();
		event.setError(this);
		
		for(EventHandler handler : getEventManager().getHandlers(event.getClass())) {
			handler.execute(event);
		}
	}

}
