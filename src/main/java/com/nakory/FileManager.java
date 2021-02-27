package com.nakory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Optional;

import com.google.gson.Gson;

public class FileManager {

	private Gson gson = new Gson();
	
	private File ROOT_DIR = new File("nakory");
	private File MODULES_DIR = new File(ROOT_DIR, "modules");
	
	public void init() {
		if (!ROOT_DIR.exists()) ROOT_DIR.mkdirs();
		if (!MODULES_DIR.exists()) MODULES_DIR.mkdirs();
	}
	
	public Gson getGson() {
		return gson;
	}
	
	public File getRootDirectory() {
		return ROOT_DIR;
	}
	
	public File getModulesDirectory() {
		return MODULES_DIR;
	}
	
	public boolean writJsonToFile(File file, Object object) {
		
			try {
				if (!file.exists()) {
					file.createNewFile();
				}
				
				FileOutputStream outputStream = new FileOutputStream(file);
				outputStream.write(gson.toJson(object).getBytes());
				outputStream.flush();
				outputStream.close();
				return true;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
	}
	
	public <T extends Object> Optional<T> readFromJson(File file, Class<T> type) {
		try {
			
			if (!file.exists()) return Optional.empty();
			
			FileInputStream inputStream = new FileInputStream(file);
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			
			StringBuilder builder = new StringBuilder();
			String line;
			
			while((line = bufferedReader.readLine()) != null) {
				builder.append(line);
			}
			
			bufferedReader.close();
			inputStreamReader.close();
			inputStream.close();
			
			return Optional.of(gson.fromJson(builder.toString(), type));
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return Optional.empty();
		}
	}
}
