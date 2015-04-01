package edu.gu.majem.dict.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import edu.gu.majem.dict.IDictionary;
import edu.gu.majem.dict.LanguageName;
import edu.gu.majem.dict.exception.DictionaryException;
import edu.gu.majem.dict.loader.IDictionaryLoader;

/**
 * Implementation of the dictionary
 * 
 * Dictionary uses two tries
 * - One for the source language and one for the
 *   target language. Nodes in the source language
 *   are connected to nodes in the target language 
 *   
 * NOTE: You should use the Facade pattern for this (this class should
 *       have a factory method)   
 *             
 */
public class Dictionary implements IDictionary{
	private IDictionaryLoader loader;
	private Trie source;
	private Trie target;
	private LanguageName from;
	private LanguageName to;
	
	public static IDictionary newInstance(IDictionaryLoader loader){
		return new Dictionary(loader);
	}
	
	private Dictionary(IDictionaryLoader loader){
		this.loader = loader;
		source = new Trie();
		target = new Trie();
	}
	
	@Override
	public void buildDictionary(LanguageName from, LanguageName to) throws DictionaryException{
		if (from == LanguageName.xx_XX || to == LanguageName.xx_XX)
			throw new DictionaryException("Can't load dictionary with" +
					" undefined languages " + from + " " + to);
		
		this.from = from;
		this.to = to;
		
		source.clear();
		target.clear();
		
		Map<String, String[]> map = loader.loadDictionary(from, to);
		TrieNode node;
			
		for (Entry<String, String[]> e : map.entrySet()){
			node = source.insert(e.getKey());
			for (String t : e.getValue())
				node.addTranslation(target.insert(t));
		}
	}
	
	@Override
	public Map<String, String[]> getTranslation(String s){
		Map<String, String[]> map = new HashMap<String, String[]>();

		for (TrieNode node : source.getWords(s)){
			String[] trans = new String[node.getTranslations().size()];
			for (int i = 0;i < node.getTranslations().size();i++)
				trans[i] = node.getTranslations().get(i).toString();
			map.put(node.toString(), trans);
		}
		return map;
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
	public void clearTranslation(){
		source.clear();
		target.clear();
	}
}
