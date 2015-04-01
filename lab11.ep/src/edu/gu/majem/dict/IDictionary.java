package edu.gu.majem.dict;

import java.util.Map;

import edu.gu.majem.dict.exception.DictionaryException;

/**
 * Interface to be used by the translator
 * 
 * @author hajo
 * 
 */
public interface IDictionary {
	/**
	 * Builds the dictionary by first loading data from file
	 * and then inserting it into two Tries
	 * @param from: The from language
	 * @param to: The to language
	 * @throws DictionaryException
	 * @pre from != null && to != null && from != to
	 * 		from != xx_XX && to != xx_XX
	 * @post from and to languages changed
	 * 		 Tries source and target filled with data
	 */
	public void buildDictionary(LanguageName from, LanguageName to) throws DictionaryException;
	
	/**
	 * Gets a map filled with words and associated
	 * translations found starting from the specified string
	 * @param s: the string to start search from
	 * @return a map filled with words and translations
	 */
	public Map<String, String[]> getTranslation(String s);
	public LanguageName getFrom();
	public LanguageName getTo();
	public void clearTranslation();
}