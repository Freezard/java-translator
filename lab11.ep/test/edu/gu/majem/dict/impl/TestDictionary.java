package edu.gu.majem.dict.impl;

import java.util.Map;
import junit.framework.Assert;
import org.junit.Test;

import edu.gu.majem.dict.IDictionary;
import edu.gu.majem.dict.LanguageName;
import edu.gu.majem.dict.exception.DictionaryException;
import edu.gu.majem.dict.impl.Dictionary;
import edu.gu.majem.dict.loader.IDictionaryLoader;
import edu.gu.majem.dict.loader.impl.DictionaryFileLoader;


public class TestDictionary {
	
	@Test
	public void testDictionaryWithUndefinedLanguage(){
		IDictionaryLoader df = new DictionaryFileLoader("test/edu/gu/hajo/dict/");
		IDictionary dic = Dictionary.newInstance(df);
		
		try{
			dic.buildDictionary(LanguageName.xx_XX, LanguageName.en_US);
		} catch (DictionaryException e){}
	}
	
	@Test
	public void testDictionaryWithValidLanguages(){
		IDictionaryLoader df = new DictionaryFileLoader("test/edu/gu/hajo/dict/");
		IDictionary dic = Dictionary.newInstance(df);
		Assert.assertTrue(dic != null);
		
		try{		
			dic.buildDictionary(LanguageName.en_US, LanguageName.sv_SV);
			Map<String, String[]> map = dic.getTranslation("b");
			Assert.assertTrue(map.size() == 0);
		
			map = dic.getTranslation("aa");
			Assert.assertTrue(map.size() == 1);
		
			map = dic.getTranslation("a");
			Assert.assertTrue(map.size() == 3);
		} catch (DictionaryException e){}
	}
}
