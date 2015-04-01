package edu.gu.majem.dict.loader.impl;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Logger;

import edu.gu.majem.dict.LanguageName;
import edu.gu.majem.dict.exception.DictionaryException;
import edu.gu.majem.dict.loader.IDictionaryLoader;

/**
 * A file loader implementation of IDictionaryLoader. File format is;
 * 
 * Row (\n)
 * Row 
 * Row ...
 * 
 * Where Row is;
 * 
 * word=translation1, translation2,....translationN 
 * 
 * File names must be: from2to.dict where from and to are country and region
 * codes for example sv_SV2en_US.dict
 * 
 * @author hajo
 * 
 */
public final class DictionaryFileLoader implements IDictionaryLoader {

	private final Logger log = Logger.getLogger(this.toString());
	private static final String SEPARATOR_WORD = "=";
	private static final String SEPARATOR_TRANSLATIONS = ",";
	private static final String FILE_SUFFIX = ".dict";
	private static final char COMMENT = '#';
	private static final int EMPTY_LINE = 0;
	
	// Path to file based storage
	private final String pathTo;
	private boolean doLog;

	public DictionaryFileLoader(String pathTo) {
		this.pathTo = pathTo;
	}

	public DictionaryFileLoader(String pathTo, boolean doLog) {
		this(pathTo);
		this.doLog = doLog;
	}

	@Override
	public Map<String, String[]> loadDictionary(LanguageName from,
			LanguageName to) throws DictionaryException  {
		// Precondition
		if( from == null || to == null || from == to){
			throw new DictionaryException("Can't load dictionary " + from + " " + to);
		}
		
		String filename = pathTo + from.toString() + "2" + to.toString()
				+ FILE_SUFFIX;

		if (doLog) {
			log.info("Loading " + filename);
		}

		Map<String, String[]> map = new HashMap<String, String[]>();
		try {
			Scanner sc = new Scanner(new File(filename));
			try {
				String line = null;
				while (sc.hasNextLine()) {
					line = sc.nextLine();
					if (line.length() > EMPTY_LINE && line.charAt(0) != COMMENT) {
						String[] splitLine = line.split(SEPARATOR_WORD);
						String word = splitLine[0].trim();
						String[] trans = splitLine[1].trim().split(
								SEPARATOR_TRANSLATIONS);
						map.put(word, trans);
					}
				}
			} finally {
				sc.close();
			}
		
		} catch (Exception e) {
			throw new DictionaryException(e);
		}
		// Post condition ok map never null
		return map;
	}
}
