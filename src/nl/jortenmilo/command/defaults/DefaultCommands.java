package nl.jortenmilo.command.defaults;

import nl.jortenmilo.command.Command;
import nl.jortenmilo.command.CommandManager;
import nl.jortenmilo.keyboard.KeyboardManager;

public class DefaultCommands {
	
	public void create(CommandManager cm, KeyboardManager keyboard) {
		Command c1 = new Command();
		c1.setCommand("exit");
		c1.setDescription("This exits the program.");
		c1.setCommandExecutor(new ExitCommand(keyboard));
		
		cm.addCommand(c1);
		
		Command c2 = new Command();
		c2.setCommand("help");
		c2.setDescription("This displays all the commands.");
		c2.setCommandExecutor(new HelpCommand(cm));
		
		cm.addCommand(c2);
		
		Command c3 = new Command();
		c3.setCommand("clear");
		c3.setDescription("Clears the display.");
		c3.setCommandExecutor(new ClearCommand(keyboard));
		
		cm.addCommand(c3);
	}
	
}
