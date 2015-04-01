package edu.gu.majem.dict.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Nodes for the Trie 
 * 
 * @author hajo
 */
// private class
class TrieNode { 
	public static final char ROOT_LABEL = '@';
	
	// The node above this node
	private TrieNode parent;
	// The nodes below this node
	private Map<Character, TrieNode> children;
	// If empty, this node is no word. If not empty,
	// this node is the last letter of a word and this
	// list contains the translated words
	private List<TrieNode> translations;
	// This is the char for the edge leading to this node
	private char label;
	
	public TrieNode(char c){
		parent = null;
		children = new HashMap<Character, TrieNode>();
		translations = new ArrayList<TrieNode>();
		label = c;
	}
	
	public void addParent(TrieNode p){
		parent = p;
	}
	
	public TrieNode getParent(){
		return parent;
	}
	
	public void addChild(char c, TrieNode child){
		children.put(c, child);
	}
	
	public TrieNode getChild(char c){
		if (children.containsKey(c))
			return children.get(c);
		return null;
	}
	
	public Collection<TrieNode> getChildren(){
		return children.values();
	}
	
	public char getLabel(){
		return label;
	}
	
	public void addTranslation(TrieNode n){
		translations.add(n);
	}
	
	public List<TrieNode> getTranslations(){
		return translations;
	}
	
	/** Concatenates all labels from the root
	up to this node and returns the string*/
	public String toString(){
		if (label == '@')
			return "";
		else return parent.toString() + Character.toString(label);
	}
}