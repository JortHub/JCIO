package nl.jortenmilo.config;

import nl.jortenmilo.event.Event;

/**
 * This is the general ConfigEvent. All ConfigEvents instantiate this class since it contains the general information about a ConfigEvent.
 * @see ConfigManager
 */
public abstract class ConfigEvent extends Event {
	
	private ConfigFile config;
	
	/**
	 * Returns the config this event is executed for.
	 * @return the config
	 */
	public ConfigFile getConfig() {
		return config;
	}

	protected void setConfig(ConfigFile config) {
		this.config = config;
	}
}
