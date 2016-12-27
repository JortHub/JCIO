package nl.jortenmilo.settings;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import nl.jortenmilo.console.Console;
import nl.jortenmilo.error.ExistingSettingError;
import nl.jortenmilo.error.UnknownSettingError;
import nl.jortenmilo.settings.SettingsEvent.SettingsEventListener;

public class SettingsManager {
	
	private HashMap<String, String> settings = new HashMap<String, String>();
	private List<String> keys = new ArrayList<String>();
	private List<SettingsEventListener> listeners = new ArrayList<SettingsEventListener>();
	private SettingsLoader loader = new SettingsLoader();
	
	public String get(String key) {
		if(!settings.containsKey(key)) {
			new UnknownSettingError(key).print();
		}
		
		return settings.get(key);
	}
	
	public void set(String key, String value) {
		if(!keys.contains(key)) {
			new UnknownSettingError(key).print();
		}
		
		settings.put(key, value);
		
		SettingsChangedEvent event = new SettingsChangedEvent();
		event.setKey(key);
		event.setValue(value);
		
		for(SettingsEventListener listener : listeners) {
			try {
				listener.onSettingsChanged(event);
			} catch(Error | Exception e2) {
				new nl.jortenmilo.error.UnknownError(e2.getMessage()).print();
			}
		}
		
		Console.update();
	}
	
	public void create(String key) {
		if(keys.contains(key)) {
			new ExistingSettingError(key).print();
		}
		
		keys.add(key);
		settings.put(key, "");
		
		SettingsCreatedEvent event = new SettingsCreatedEvent();
		event.setKey(key);
		event.setValue(settings.get(key));
		
		for(SettingsEventListener listener : listeners) {
			try {
				listener.onSettingsCreated(event);
			} catch(Error | Exception e2) {
				new nl.jortenmilo.error.UnknownError(e2.getMessage()).print();
			}
		}
	}
	
	public boolean contains(String key) {
		return keys.contains(key);
	}
	
	public void remove(String key) {
		if(!keys.contains(key)) {
			new UnknownSettingError(key).print();
		}
		
		keys.remove(key);
		
		SettingsRemovedEvent event = new SettingsRemovedEvent();
		event.setKey(key);
		event.setValue(settings.get(key));
		
		for(SettingsEventListener listener : listeners) {
			try {
				listener.onSettingsRemoved(event);
			} catch(Error | Exception e2) {
				new nl.jortenmilo.error.UnknownError(e2.getMessage()).print();
			}
		}
	}
	
	public HashMap<String, String> getSettings() {
		return settings;
	}
	
	public void reset() {
		keys.clear();
		settings.clear();
		
		create("time");
		set("time", "true");
		
		create("log");
		set("log", "true");
		
		create("foreground");
		set("foreground", "192_192_192");
		
		create("background");
		set("background", "0_0_0");
		
		create("default_width");
		set("default_width", "1600");
		
		create("default_height");
		set("default_height", "800");
		
		create("default_title");
		set("default_title", "JCIO");
		
		SettingsResetEvent event = new SettingsResetEvent();
		
		for(SettingsEventListener listener : listeners) {
			try {
				listener.onSettingsReset(event);
			} catch(Error | Exception e2) {
				new nl.jortenmilo.error.UnknownError(e2.getMessage()).print();
			}
		}
	}
	
	public void addListener(SettingsEventListener listener) {
		listeners.add(listener);
	}
	
	public void save() {
		try {
			loader.save(new File("settings.jcio"), this);
			
			SettingsSavedEvent event = new SettingsSavedEvent();
			
			for(SettingsEventListener listener : listeners) {
				try {
					listener.onSettingsSaved(event);
				} catch(Error | Exception e2) {
					new nl.jortenmilo.error.UnknownError(e2.getMessage()).print();
				}
			}
			
		} catch(Error | Exception e) {
			new nl.jortenmilo.error.UnknownError(e.getMessage()).print();
		}
	}
	
	public void load() {
		try {
			loader.load(new File("settings.jcio"), this);
			
			SettingsLoadedEvent event = new SettingsLoadedEvent();
			
			for(SettingsEventListener listener : listeners) {
				try {
					listener.onSettingsLoaded(event);
				} catch(Error | Exception e2) {
					new nl.jortenmilo.error.UnknownError(e2.getMessage()).print();
				}
			}
			
		} catch(Error | Exception e) {
			new nl.jortenmilo.error.UnknownError(e.getMessage()).print();
		}
	}
	
}
