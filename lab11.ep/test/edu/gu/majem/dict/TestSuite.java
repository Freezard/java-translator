package edu.gu.majem.dict;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import edu.gu.majem.dict.impl.TestDictionary;
import edu.gu.majem.dict.impl.TestTrie;

/**
 * A test suite to run all tests
 * 
 * @author hajo
 * 
 */

@RunWith(Suite.class)
@Suite.SuiteClasses({ TestTrie.class, TestDictionaryFileLoader.class,
		TestDictionary.class })
public class TestSuite {
	// Not much use
}
