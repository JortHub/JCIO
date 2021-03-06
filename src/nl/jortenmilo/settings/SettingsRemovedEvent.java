package nl.jortenmilo.settings;

public class SettingsRemovedEvent extends SettingsEvent {
	
	private String key;
	private String value;
	
	public String getKey() {
		return key;
	}
	
	protected void setKey(String key) {
		this.key = key;
	}
	
	public String getValue() {
		return value;
	}
	
	protected void setValue(String value) {
		this.value = value;
	}

	@Override
	public String getName() {
		return "SettingsRemovedEvent";
	}
	
}
