package edu.gu.majem.translator.core.impl;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.HashMap;
import java.util.Map;

import edu.gu.majem.dict.IDictionary;
import edu.gu.majem.dict.LanguageName;
import edu.gu.majem.dict.exception.DictionaryException;
import edu.gu.majem.translator.constants.StateChanges;
import edu.gu.majem.translator.core.ITranslator;
import edu.gu.majem.translator.util.IObservable;

/**
 * Core translator
 * @author hajo
 *
 */
public class Translator implements ITranslator, IObservable{
	private IDictionary dictionary;
	private static ITranslator instance;
	private LanguageName from;
	private LanguageName to;
	private PropertyChangeSupport observers;
	private String input;
	private Map<String, String[]> translations;
	
	// Singleton
	public static ITranslator getInstance(){
		if (instance == null) {
			instance = new Translator();
		}
		return instance;
	}

	public void init(IDictionary dictionary){
		this.dictionary = dictionary;
		observers = new PropertyChangeSupport(this);
		translations = new HashMap<String, String[]>();
		from = LanguageName.xx_XX;
		to = LanguageName.xx_XX;
		input = "";
	}
	
	@Override
	public void tryLoad(){
		// This is a pre-condition to dictionary
		boolean possibleToLoad = (from != LanguageName.xx_XX && 
				to != LanguageName.xx_XX && from != to);
		if (possibleToLoad) {
			try {
				dictionary.buildDictionary(from, to);
			} catch (DictionaryException e) {
				e.printStackTrace();
			}
		}
		else dictionary.clearTranslation();
	}
	
	@Override
	public void translate(String s){
		observers.firePropertyChange(StateChanges.TRANSLATIONS_CHANGED.
				toString(), translations, translations =
				dictionary.getTranslation(input));
	}
	
	@Override
	public void setFrom(LanguageName lang){
		observers.firePropertyChange(StateChanges.FROM_LANG_CHANGED.
				toString(), from, from = lang);
	}
	
	@Override
	public void setTo(LanguageName lang){
		observers.firePropertyChange(StateChanges.TO_LANG_CHANGED.
				toString(), to, to = lang);
	}
	
	@Override
	public void addInput(String s){
		observers.firePropertyChange(StateChanges.INPUT_CHANGED.
				toString(), input, input += s);
	}
	
	@Override
	public void deleteInput(){
		observers.firePropertyChange(StateChanges.INPUT_CHANGED.
				toString(), input, input =
					input.substring(0, input.length() - 1));
	}
	
	@Override
	public void clearInput(){
		observers.firePropertyChange(StateChanges.INPUT_CHANGED.
				toString(), input, input = "");
	}
	
	@Override
	public String getInput(){
		return input;
	}
	
	@Override
	public LanguageName getFrom(){
		return from;
	}
	
	@Override
	public LanguageName getTo(){
		return to;
	}
	
	@Override
	public void addObserver(PropertyChangeListener observer) {
		this.observers.addPropertyChangeListener(observer);

	}
}
