package edu.gu.majem.dict.impl;

import java.util.ArrayList;
import java.util.List;

/**
 * A trie, see http://en.wikipedia.org/wiki/Trie
 * Helper class for the dictionary
 * 
 * @inv this.root != null
 * 		this.root.parent == null
 * 		this.root.label == '@'
 * 
 * @author hajo
 * 
 */
// Private class
class Trie {
	private TrieNode root;
	
	public Trie(){
		root = new TrieNode(TrieNode.ROOT_LABEL);
	}
	
	/**
	 * Inserts a word into the Trie
	 * @param word: the word to insert
	 * @return the node containing the final
	 *         letter of the word. Doesn't
	 *         replace already existing words
	 */
	public TrieNode insert(String word){
		TrieNode node = root;
		for (int i = 0;i < word.length();i++){
			char c = word.charAt(i);
			TrieNode child = node.getChild(c);
			if (child == null){
				node.addChild(c, new TrieNode(c));
				node.getChild(c).addParent(node);
				node = node.getChild(c);
			}
			else node = child;
		}
		return node;
	}
	
	/**
	 * Returns a list of every found word starting
	 * from the specified string
	 * @param s: get words starting from this string
	 * @return a list of nodes containing the words,
	 */
	public List<TrieNode> getWords(String s){
		List<TrieNode> list = new ArrayList<TrieNode>();
		if (s == "")
			return list;
		TrieNode node = find(s);
		if (node == null)
			return list;

		findAllWords(node, list);
		return list;
	}
	
	/**
	 * Finds every word from this node and inserts
	 * them into the specified list
	 * @param node: the node to start searching from
	 * @param list: the list to insert nodes into
	 * @pre node != null && list != null
	 * @post list contains the nodes of all found words
	 */
	private void findAllWords(TrieNode node, List<TrieNode> list){
		if(node.getTranslations().size() != 0)
			list.add(node);
		for(TrieNode child : node.getChildren())
			findAllWords(child, list);
	}
	
	/**
	 * Returns the final node of a word
	 * @param word: the word to find
	 * @return the node containing the final
	 *         letter of the word, null
	 *         if no such word found, or
	 *         the root if word is empty
	 */
	public TrieNode find(String word){
		TrieNode node = root;
		for (int i = 0;i < word.length();i++){
			char c = word.charAt(i);
			TrieNode child = node.getChild(c);
			if (child == null)
				return null;
			else node = child;
		}
		return node;
	}
	
	/**
	 * Clears the Trie
	 * @post root = empty node
	 */
	public void clear(){
		root = new TrieNode(TrieNode.ROOT_LABEL);
	}
}
