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
import nl.jortenmilo.error.SyntaxError;

public class PluginLoader {
	
	public void loadAll(PluginManager pm) {
		File folder = new File("plugins");
		File[] files = folder.listFiles();
		
		for(File f : files) {
			if(f.getName().endsWith(".jar")) {
				load(f, pm);
			}
		}
	}
	
	@SuppressWarnings("resource")
	public void load(File f, PluginManager pm) {
		try {
			InputStream is = getInputStream(f, "plugin.jcio");
			
			if(is == null) {
				new MissingFileError(f.getName(), "plugin.jcio").print();
			} 
			else {
				LoadedPlugin lp = new LoadedPlugin();
				lp.setPath(f.getPath());
				
				Scanner scr = new Scanner(is);
				
				while(scr.hasNext()) {
					String line = scr.nextLine();
					
					if(line.startsWith("#")) {
						continue;
					}
					else if(line.startsWith("path: ")) {
						String path = line.substring(6);
						JarFile jarFile = new JarFile(f.getPath());
						Enumeration<JarEntry> e = jarFile.entries();
						URL[] urls = { new URL("jar:file:" + f.getPath() +"!/") };
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
						String name = line.substring(6);
						lp.setName(name);
					}
					else {
						new SyntaxError(line, "the plugin.jcio file (Plugin: " + f.getName() + ")").print();
					}
				}
				
				if(lp.getName()==null) {
					new MissingFileError(f.getName(), "name").print();
				}
				
				else if(lp.getPlugin()==null) {
					new MissingFileError(f.getName(), "path").print();
				}
				
				pm.addPlugin(lp);
				lp.getPlugin().load();
			}
		} 
		catch(Error | Exception e) {
			new nl.jortenmilo.error.UnknownError(e.toString(), e.getMessage()).print();
		}
	}
	
	public void unloadAll(PluginManager pm) {
		for(LoadedPlugin plugin : pm.getPlugins()) {
			unload(plugin);
			
			pm.removePlugin(plugin);
			plugin.getPlugin().unload();
		}
	}
	
	public void unload(LoadedPlugin plugin) {
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
