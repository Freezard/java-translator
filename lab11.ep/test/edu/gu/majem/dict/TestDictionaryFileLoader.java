package edu.gu.majem.dict;

import java.util.Map;

import junit.framework.Assert;

import org.junit.Test;

import edu.gu.majem.dict.LanguageName;
import edu.gu.majem.dict.exception.DictionaryException;
import edu.gu.majem.dict.loader.impl.DictionaryFileLoader;

/**
 * Test file handling
 * 
 * For testing of private methods use the PrivateAccessor utility
 * 
 * To run: Right click > Run as > Junit Test
 * 
 * NOTE: It's possible to run this in debugger
 * @author hajo
 *
 */
public class TestDictionaryFileLoader {

	private static final String PATH = "test/edu/gu/hajo/dict/";

	@Test
	public void testLoadDictionaryEN_SV() throws DictionaryException {
		/*
		 * Testing implementations, not interfaces!
		 * (rude castings ok)
		 */
		DictionaryFileLoader f = new DictionaryFileLoader(PATH, true);
		Map<String, String[]> map = f.loadDictionary(
				LanguageName.valueOf("en_US"), LanguageName.valueOf("sv_SV"));

		Assert.assertTrue(map != null);
		Assert.assertTrue(map.size() > 0);
	}
	
	// This test should throw an exception
	@Test(expected = DictionaryException.class)
	public void testLoadDictionaryEqualFromTo() throws DictionaryException {
		DictionaryFileLoader f = new DictionaryFileLoader(PATH, true);
		// Same from and to language not allowed
		Map<String, String[]> map = f.loadDictionary(
				LanguageName.valueOf("en_US"), LanguageName.valueOf("en_US"));

	}
	
}
