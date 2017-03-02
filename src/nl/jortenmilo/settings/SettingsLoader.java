package nl.jortenmilo.settings;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import nl.jortenmilo.error.NullableParameterError;

public class SettingsLoader {

	public void load(File file, SettingsManager manager) throws IOException {
		if(file == null) {
			new NullableParameterError("File", "file").print();
			return;
		}
		if(manager == null) {
			new NullableParameterError("SettingsManager", "manager").print();
			return;
		}
		
		BufferedReader br = new BufferedReader(new FileReader(file));
		String line = "";
		
		while((line = br.readLine()) != null) {
			if(line.startsWith("#") || line.equals("")) {
				continue;
			} 
			
			byte[] bytes = line.getBytes();
			String key = "";
			String value = "";
			
			for(int i = 0; i < bytes.length; i++) {
				if(bytes[i]==58) {
					key = line.substring(0, i);
					value = line.substring(i+2);
				} 
			}
			
			manager.create(key);
			manager.set(key, value);
		}
		
		br.close();
	}
	
	public void save(File file, SettingsManager manager) throws IOException {
		if(file == null) {
			new NullableParameterError("File", "file").print();
			return;
		}
		if(manager == null) {
			new NullableParameterError("SettingsManager", "manager").print();
			return;
		}
		
		BufferedWriter bw = new BufferedWriter(new FileWriter(file));
		
		for(String key : manager.getSettings().keySet()) {
			String value = manager.get(key);
			bw.write(key + ": " + value);
			bw.newLine();
		}
		
		bw.close();
	}
	
}
