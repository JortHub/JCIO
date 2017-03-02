package nl.jortenmilo.plugin;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.Scanner;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import nl.jortenmilo.error.MissingFileError;
import nl.jortenmilo.error.NullableParameterError;
import nl.jortenmilo.error.SyntaxError;

public class PluginLoader {
	
	public void loadAll(PluginManager manager) {
		if(manager == null) {
			new NullableParameterError("PluginManager", "manager").print();
			return;
		}
		
		File folder = new File("plugins");
		File[] files = folder.listFiles();
		
		for(File f : files) {
			if(f.getName().endsWith(".jar")) {
				load(f, manager);
			}
		}
	}
	
	@SuppressWarnings("resource")
	public void load(File file, PluginManager manager) {
		if(manager == null) {
			new NullableParameterError("PluginManager", "manager").print();
			return;
		}
		if(file == null) {
			new NullableParameterError("File", "File").print();
			return;
		}
		
		try {
			InputStream is = getInputStream(file, "plugin.jcio");
			
			if(is == null) {
				new MissingFileError(file.getName(), "plugin.jcio").print();
			} 
			else {
				LoadedPlugin lp = new LoadedPlugin();
				lp.setPath(file.getPath());
				
				Scanner scr = new Scanner(is);
				
				while(scr.hasNext()) {
					String line = scr.nextLine();
					
					if(line.startsWith("#")) {
						continue;
					}
					else if(line.startsWith("path: ")) {
						String path = line.substring(6);
						JarFile jarFile = new JarFile(file.getPath());
						Enumeration<JarEntry> e = jarFile.entries();
						URL[] urls = { new URL("jar:file:" + file.getPath() +"!/") };
						URLClassLoader cl = new URLClassLoader(urls, this.getClass().getClassLoader());
						
						while (e.hasMoreElements()) {
							JarEntry je = e.nextElement();
						
							if(je.isDirectory() || !je.getName().endsWith(".class")){
								continue;
							}
							
							String className = je.getName().substring(0,je.getName().length()-6);
							className = className.replace('/', '.');
							Class<?> c = cl.loadClass(className);
							
							if(c.getName().equals(path)) {
								Constructor<?> ctor = c.getConstructor();
								
								lp.setPlugin((Plugin)ctor.newInstance());
								lp.setClassLoader(cl);
								break;
							}
						}
					}
					else if(line.startsWith("name: ")) {
						String name = line.replaceAll("name: ", "");
						lp.setName(name);
					}
					else if(line.startsWith("author: ")) {
						String author = line.replaceAll("author: ", "");
						lp.setAuthor(author);
					}
					else if(line.startsWith("website: ")) {
						String website = line.replaceAll("website: ", "");
						lp.setWebsite(website);
					}
					else {
						new SyntaxError(line, "the plugin.jcio file (Plugin: " + file.getName() + ")").print();
					}
				}
				
				if(lp.getName()==null) {
					new MissingFileError(file.getName(), "name").print();
				}
				
				else if(lp.getPlugin()==null) {
					new MissingFileError(file.getName(), "path").print();
				}
				
				manager.addPlugin(lp);
				lp.getPlugin().load();
			}
		} 
		catch(Error | Exception e) {
			new nl.jortenmilo.error.UnknownError(e.toString(), e.getMessage()).print();
		}
	}
	
	public void unloadAll(PluginManager manager) {
		if(manager == null) {
			new NullableParameterError("PluginManager", "manager").print();
			return;
		}
		
		for(LoadedPlugin plugin : manager.getPlugins()) {
			unload(plugin);
			
			manager.removePlugin(plugin);
			plugin.getPlugin().unload();
		}
	}
	
	public void unload(LoadedPlugin plugin) {
		if(plugin == null) {
			new NullableParameterError("LoadedPlugin", "plugin").print();
			return;
		}
		
		URLClassLoader cl = plugin.getClassLoader();
		
		try {
			cl.close();
		} 
		catch(Error | Exception e) {
			new nl.jortenmilo.error.UnknownError(e.toString(), e.getMessage()).print();
		}
	}
	
	private InputStream getInputStream(File zip, String entry) throws IOException {
		ZipInputStream zin = new ZipInputStream(new FileInputStream(zip));
	
		for (ZipEntry e; (e = zin.getNextEntry()) != null;) {
			if (e.getName().equals(entry)) {
				return zin;
			}
		}
	
		return null;
	}
	
}
