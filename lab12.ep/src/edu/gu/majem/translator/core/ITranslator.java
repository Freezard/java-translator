package edu.gu.majem.translator.core;

import edu.gu.majem.dict.LanguageName;
import edu.gu.majem.translator.util.IObservable;

/**
 * What the GUI will use
 * @author hajo
 *
 */
public interface ITranslator extends IObservable {
	/**
	 * Tries to load the translator with a dictionary
	 * Does nothing if unable to load
	 */
	public void tryLoad();
	/**
	 * Translates all words starting from the specified
	 * string, updates the dictionary
	 * Alerts observers of changes
	 */
	public void translate(String s);
	/**
	 * Changes the source language
	 * Alerts observers of changes
	 */
	public void setFrom(LanguageName lang);
	/**
	 * Changes the target language
	 * Alerts observers of changes
	 */
	public void setTo(LanguageName lang);
	/**
	 * Adds a string of text to the current input
	 * @param s: the string to add
	 * Alerts observers of changes
	 */
	public void addInput(String s);
	/**
	 * Removes one character of the input
	 * Alerts observers of changes
	 */
	public void deleteInput();
	/**
	 * Clears the input
	 * Alerts observers of changes
	 * @pre input >= 1
	 */
	public void clearInput();
	public String getInput();
	public LanguageName getFrom();
	public LanguageName getTo();
}
