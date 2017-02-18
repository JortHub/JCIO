package nl.jortenmilo.config;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import nl.jortenmilo.error.MissingConfigObjectError;

/**
 * This class is the base of a config. See it like the parent of all objects.
 */
public class ConfigFile {
	
	private File file;
	private List<ConfigObject> objects = new ArrayList<ConfigObject>();
	
	protected ConfigFile() {}
	
	/**
	 * Returns the file this config is in.
	 * @return The file
	 */
	public File getFile() {
		return file;
	}
	
	/**
	 * Sets the file the config will be in. When you change this when the config is already loaded, nothing will happen.
	 * @param The file
	 */
	public void setFile(File file) {
		this.file = file;
	}
	
	/**
	 * Sets a ConfigObject to the specified path. When the path contains a dot it will look into the object that was specified before that dot.
	 * @param object The object you want to add to the specified path
	 * @param path The path
	 */
	//TODO: It looks like a path that doesn't contain a dot won't look through the list of objects.
	public void set(ConfigObject object, String path) {
		if(!path.contains(".")) {
			objects.add(object);
		} 
		else {
			String key = path.substring(0, path.indexOf("."));
			
			for(ConfigObject o : objects) {
				if(o.getName().equals(key)) {
					o.set(object, path.substring(path.indexOf(".") + 1, path.length()));
					return;
				}
			}
			
			new MissingConfigObjectError(key, path).print();
		}
	}
	
	/**
	 * Returns the list of all the objects in this file.
	 * @return The list of ConfigObjects
	 */
	public List<ConfigObject> getObjects() {
		return objects;
	}
	
	/**
	 * Returns the object that is from the specified path. 
	 * When the path contains a dot it will look into the object that was specified before that dot.
	 * If the path is unknown it will throw a MissingConfigObjectError.
	 * @param path The path were the object is from
	 * @return The object
	 */
	//TODO: It'll immediately look into specified paths, but it may not contain a dot.
	public ConfigObject get(String path) {
		String key = path.substring(0, path.indexOf("."));
		for(ConfigObject o : objects) {
			if(o.getName().equals(key)) {
				if(!path.contains(".")) {
					return o;
				} 
				else {
					return o.get(path.substring(path.indexOf(".") + 1, path.length()));
				}
			}
		}
		
		new MissingConfigObjectError(key, path).print();
		return null;
	}
	
}
