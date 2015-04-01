package edu.gu.majem.dict.loader;

import java.util.Map;

import edu.gu.majem.dict.LanguageName;
import edu.gu.majem.dict.exception.DictionaryException;

/**
 * Interface for a loader, a class the dictionary can use to load
 * content. A default file loader implementation is provided
 * 
 * @author hajo
 *
 */
public interface IDictionaryLoader {
	/**
	 * Load the dictionary data from somewhere
	 * @param from, The from language
	 * @param to, The to language
	 * @return the dictionary as a map. Key is a word in from language
	 * and value is a list of translations in the to language.
	 * @throws DictionaryException
	 * @pre  from != null && to != null && from != to
	 * @post \result != null
	 */
	public Map<String, String[]> loadDictionary(LanguageName from,
			LanguageName to) throws DictionaryException;
}
