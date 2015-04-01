package edu.gu.majem.dict.impl;


import junit.framework.Assert;

import org.junit.Test;

import edu.gu.majem.dict.impl.Trie;
import edu.gu.majem.dict.impl.TrieNode;

import java.util.List;

/**
 * Testing the Trie classes 
 * 
 * For testing of private methods use the PrivateAccessor utility
 * 
 * Right click > Run as > Junit Test
 * 
 * NOTE: It's possible to run this in debugger
 * 
 * @author hajo
 * 
 * 
 */
public class TestTrie {

	@Test
	public void testTrie(){
		Trie t = new Trie();
		Trie t2 = new Trie();
		
		TrieNode root = t.find("");
		Assert.assertTrue(root != null);
		Assert.assertTrue(root.getParent() == null);
		Assert.assertTrue(root.getLabel() == '@');
		
		TrieNode node = t.insert("bil");
		TrieNode node2 = t.insert("billig");
		Assert.assertTrue(node != null);
		Assert.assertTrue(node.getLabel() == 'l');
		
		TrieNode emptyNode = t.insert("");
		Assert.assertTrue(root == emptyNode);
		
		node.addTranslation(t2.insert("car"));
		node2.addTranslation(t2.insert("cheap"));
		Assert.assertTrue(t2.find("car").toString().equals("car"));
		
		List<TrieNode> list = t.getWords("");
		Assert.assertTrue(list.size() == 0);
		
		list = t.getWords("apa");
		Assert.assertTrue(list.size() == 0);
		
		list = t.getWords("b");
		Assert.assertTrue(list.size() == 2);
	}
}
