package edu.gu.majem.translator.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Holding user options (GUI language)
 * Will read/write (serialize) a map from/to disk
 * 
 * @author hajo
 * 
 */
public class Options {

	private static final String OPTIONS_FILE = "options.ser";
	private static Map<String, String> options;

	static {
		load();
	}

	@SuppressWarnings("unchecked")
	public static void load() {
		System.out.println("options");
		File filename = new File(OPTIONS_FILE);
		if (filename.exists()){
			try {
				ObjectInputStream in = new ObjectInputStream(
						new FileInputStream(filename));
				// Cast ok
				options = (HashMap<String, String>) in.readObject();
				in.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			options = new HashMap<String, String>();
		}
	}

	public static void save() {
		File filename = new File(OPTIONS_FILE);
		ObjectOutputStream out;
		try {
			out = new ObjectOutputStream(new FileOutputStream(filename));
			out.writeObject(options);
			out.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void setGUILanguage(String guiLanguage) {
		options.put("GUI_LANG", guiLanguage);
	}

	// Default GUI language is en_US
	public static String getGUILanguage() {
		String s = options.get("GUI_LANG");
		return (s != null) ? s : "en_US";
	}
}
