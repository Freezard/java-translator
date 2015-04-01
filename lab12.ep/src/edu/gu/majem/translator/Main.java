package edu.gu.majem.translator;

import javax.swing.SwingUtilities;

import edu.gu.majem.dict.impl.Dictionary;
import edu.gu.majem.dict.loader.impl.DictionaryFileLoader;
import edu.gu.majem.translator.core.ITranslator;
import edu.gu.majem.translator.core.impl.Translator;
import edu.gu.majem.translator.view.MainFrame;

/**
 * Application entry point
 * 
 * @author hajo
 * 
 */
public class Main {

	// This is the only location where DictionaryFileLoader
	// and Translator (concrete classes) are used.
	// other parts should use IDictionary and IDictionaryLoader
	public static void main(String[] args) {
		DictionaryFileLoader df = new DictionaryFileLoader(args[0]);
		final ITranslator t = Translator.getInstance();
		((Translator) t).init(Dictionary.newInstance(df));
		/*
		 * Don't need to understand the below for now
		 */
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				MainFrame mf = new MainFrame(t);
				mf.setLocationRelativeTo(null);
				mf.setVisible(true);
				// TODO Set last selected GUI language
			}
		});
	}
}
