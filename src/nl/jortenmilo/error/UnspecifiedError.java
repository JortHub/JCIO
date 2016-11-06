package nl.jortenmilo.error;

import nl.jortenmilo.console.Console;
import nl.jortenmilo.console.Console.ConsoleUser;
import nl.jortenmilo.main.CloseManager;

public class UnspecifiedError extends Error {
	
	private String value1;
	private String value2;
	
	public UnspecifiedError(String value1, String value2) {
		this.value1 = value1;
		this.value2 = value2;
	}
	
	@Override
	public void print() {
		Console.println(ConsoleUser.Error, "UnspecifiedError: The plugin '" + value1 + "' has no '" + value2  + "' specified!");
		StackTraceElement[] es = Thread.currentThread().getStackTrace();
		StackTraceElement[] es2 = new StackTraceElement[es.length-2];
		for(int i = 0; i < es2.length; i++) {
			es2[i] = es[i+2];
		}
		for(StackTraceElement e : es2) {
			Console.println(ConsoleUser.Error, "   at: " + e.getClassName() + "." + e.getMethodName() + " (Line: " + e.getLineNumber() + " in " + e.getFileName() + ")");
		}
		CloseManager.close();
	}
	
}