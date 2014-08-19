package com.simpletasker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

import com.simpletasker.lang.Executor;

public class Lib {
	
	/**
	 * The current version of the program. This is automatically changed every
	 * build: so do not touch this variable!
	 */
	public static final String VERSION = "0.3.0";

	/**
	 * The current major version of the program. This is automatically changed
	 * every build: so do not touch this variable!
	 */
	public static final String VERSION_MAJOR = "0";

	/**
	 * The current minor version of the program. This is automatically changed
	 * every build: so do not touch this variable!
	 */
	public static final String VERSION_MINOR = "3";

	/**
	 * The current buildnumber of the program. This is automatically changed every
	 * build: so do not touch this variable!
	 */
	public static final String BUILDNUMBER = "0";

	/**
	 * The build time. This is automatically changed every build: so do not touch
	 * this variable!
	 */
	public static final String BUILD_TIME = "2014-08-16 22:02:18";

	/**
	 * The array that stores all the language based strings. 
	 */
	private static final HashMap<String, String> language = new HashMap<>();
	
	/**
	 * Loads the selected language to the {@link #language} hashMap.
	 * @param langName the language to load. This name should be available 
	 * in the 'com/simpletasker/res/lang' folder. This function takes the
	 * {@link Executor #getInstance()} class to get the resourceAsStream.
	 * @throws IOException when loading stuff goes very wrong.
	 */
	public static void initLanguage(String langName) throws IOException{
		String path = "res/lang/" + langName + ".lang";
		InputStream stream = Lib.class.getResourceAsStream(path);
		InputStreamReader is = new InputStreamReader(stream);
		BufferedReader reader = new BufferedReader(is);
		
		String line;
		while((line = reader.readLine()) != null){
			if(line.equals("") || line.startsWith("#"))
				continue;
			String[] sp = line.split(":");
			if(sp.length != 2)
				throw new IllegalArgumentException("In the file every line should be key:value");
			language.put(sp[0], sp[1]);
		}
		
		reader.close();
	}
	
	/**
	 * @param key the key to the string.
	 * @return a locilazed string. Returns null if no value is found.
	 */
	public static String getLang(String key){
		if(language.containsKey(key))
			return language.get(key);
		return "~~";
	}
	
}
