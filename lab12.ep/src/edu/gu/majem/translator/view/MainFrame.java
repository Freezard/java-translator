package edu.gu.majem.translator.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.BorderFactory;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.WindowConstants;
import javax.swing.border.TitledBorder;


import edu.gu.majem.dict.LanguageName;
import edu.gu.majem.dict.exception.DictionaryException;
import edu.gu.majem.translator.constants.StateChanges;
import edu.gu.majem.translator.core.ITranslator;
import edu.gu.majem.translator.util.Options;
import edu.gu.majem.translator.util.SelectItem;

@SuppressWarnings("serial")
public class MainFrame extends javax.swing.JFrame implements
		PropertyChangeListener {
	private JMenuBar jMBarMain;
	private JMenu jMnuFile;
	private JMenu jMnuLanguages;
	private JMenuItem jMnuISv;
	private JButton jBtnBackspace;
	private JPanel jPnlSouthRight;
	private JPanel jPnlSouth;
	private JMenuItem jMnuIAbout;
	private JMenuItem jMnuIEng;
	private JPanel jPnlNorth;
	private JComboBox jCBoxTo;
	private JComboBox jCBoxFrom;
	private JList jLstOut;
	private JTextField jTxtFldIn;
	private JPanel jPnlCenter;
	private JLabel jLblTo;
	private JLabel jLlbFrom;
	private JButton jBtnClear;
	private JMenuItem jMnuIExit;
	private JMenu jMnuAbout;
	private JMenu jMnuOptions;
	private KeyboardPanel kbdPanel;
	private AboutDialog aboutDia;
	
	// Used to clear the output word list
	private static final DefaultListModel EMPTY = new DefaultListModel();
	// Used to store the languages strings for the titles borders
	// used in propertyChange method
	private final Map<LanguageName, String> lang2str = new HashMap<LanguageName, String>();
	private final ITranslator translator;

	public MainFrame(ITranslator translator) {
		super();
		initGUI();
		initCtrl();
		// Order Matters
		initKeyboard();
		initAbout();
		this.translator = translator;
		translator.addObserver(this);
		translator.addObserver(kbdPanel);
	}

	private void initAbout(){
		aboutDia = new AboutDialog(this);
		aboutDia.setLocationRelativeTo(jPnlSouth);
	}
	
	private void initKeyboard() {
		kbdPanel = new KeyboardPanel();
		jPnlSouth.add(kbdPanel, BorderLayout.CENTER);
	}

	// Clear input and output
	private void clear() {
		jLstOut.setModel(EMPTY);
		translator.clearInput();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		String name = evt.getPropertyName();
		 
		if (name.equals(StateChanges.FROM_LANG_CHANGED.toString())){
			clear();
			translator.tryLoad();
			jTxtFldIn.setBorder(BorderFactory
				.createTitledBorder(evt.getNewValue().toString()));
		}
		else if (name.equals(StateChanges.TO_LANG_CHANGED.toString())){
			clear();
			translator.tryLoad();
			jLstOut.setBorder(BorderFactory
				.createTitledBorder(evt.getNewValue().toString()));
		}
		else if (name.equals(StateChanges.INPUT_CHANGED.toString())){
			String input = translator.getInput();
			jTxtFldIn.setText(input);
			translator.translate(input);
		}
		else if (name.equals(StateChanges.TRANSLATIONS_CHANGED.toString())){		
			// Snippet to fill the output word list
			if (translator.getInput().equals(""))
				jLstOut.setModel(EMPTY);
			else{
				Map<String, String[]> map = (Map<String, String[]>) evt
				.getNewValue();
				DefaultListModel m = new DefaultListModel();
				for (Entry<String, String[]> e : map.entrySet()) {
					StringBuffer transl = new StringBuffer();
					for (String s : e.getValue()) {
						transl.append(s).append(",");
					}
					m.addElement(e.getKey() + ": " + transl.toString());
				}
				jLstOut.setModel(m);
			}
		}
		repaint();
	}

	private void initCtrl() {		
		jBtnBackspace.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (translator.getInput().length() >= 1)
					translator.deleteInput(); 
				}
		});
		jBtnClear.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				clear();
			}
		});
		jMnuIExit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		jMnuIAbout.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				aboutDia.setVisible(true);
			}
		});
		jMnuIEng.addActionListener(guiLangListener);
		jMnuISv.addActionListener(guiLangListener);
		// Must have this as the internal representation
		jMnuISv.setActionCommand("sv_SV");
		jMnuIEng.setActionCommand("en_US");

		jCBoxFrom.addActionListener(langListener);
		jCBoxTo.addActionListener(langListener);
	}

	// Shared listener
	private ActionListener langListener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			LanguageName l;
			if (jCBoxFrom == e.getSource()){
				l = LanguageName.valueOf((String) jCBoxFrom.getSelectedItem());
				if (l == translator.getTo() && l != LanguageName.xx_XX){
					jCBoxTo.setSelectedIndex(jCBoxTo.getItemCount() - jCBoxFrom.getSelectedIndex());
					l = LanguageName.valueOf((String) jCBoxFrom.getSelectedItem());
				}
				translator.setFrom(l);
			}
			else{
				l = LanguageName.valueOf((String) jCBoxTo.getSelectedItem());
				if (l == translator.getFrom() && l != LanguageName.xx_XX){
					jCBoxFrom.setSelectedIndex(jCBoxFrom.getItemCount() - jCBoxTo.getSelectedIndex());
					l = LanguageName.valueOf((String) jCBoxTo.getSelectedItem());
				}
			    translator.setTo(l);
			}
		}

	};
	
	// Shared listener
	private ActionListener guiLangListener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			// Change GUI language
		}

	};
	
	// ------------ DON*T TOUCH BELOW ------------------------

	private void initGUI() {
		try {
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			this.setTitle("translator");
			{
				jPnlSouth = new JPanel();
				BorderLayout jPanelSouthLayout = new BorderLayout();
				jPnlSouth.setLayout(jPanelSouthLayout);
				getContentPane().add(jPnlSouth, BorderLayout.SOUTH);

				{
					jPnlSouthRight = new JPanel();
					GridLayout jPanelSouthRightLayout = new GridLayout(3, 1);
					jPanelSouthRightLayout.setHgap(5);
					jPanelSouthRightLayout.setVgap(5);
					jPanelSouthRightLayout.setColumns(1);
					jPanelSouthRightLayout.setRows(3);
					jPnlSouthRight.setLayout(jPanelSouthRightLayout);
					jPnlSouth.add(jPnlSouthRight, BorderLayout.EAST);
					{
						jBtnBackspace = new JButton();
						jPnlSouthRight.add(jBtnBackspace);
						jBtnBackspace.setText("backsp");
						jBtnBackspace.setName("jButtonBackspace");

					}
					{
						jBtnClear = new JButton();
						jPnlSouthRight.add(jBtnClear);
						jBtnClear.setText("clear");
						jBtnClear.setName("jButtonClear");

					}
					{
					}
				}
			}
			{
				jPnlNorth = new JPanel();
				FlowLayout jPanelNorthLayout = new FlowLayout();
				getContentPane().add(jPnlNorth, BorderLayout.NORTH);
				jPnlNorth.setLayout(jPanelNorthLayout);
				jPnlNorth.setBorder(BorderFactory.createTitledBorder("select"));
				{
					jLlbFrom = new JLabel();
					jPnlNorth.add(jLlbFrom);
					jLlbFrom.setText("from");
				}
				{
					ComboBoxModel jComboBoxFromModel = new DefaultComboBoxModel(
							new String[] { "xx_XX", "en_US", "sv_SV" });
					jCBoxFrom = new JComboBox();
					jPnlNorth.add(jCBoxFrom);
					jCBoxFrom.setModel(jComboBoxFromModel);
				}
				{
					jLblTo = new JLabel();
					jPnlNorth.add(jLblTo);
					jLblTo.setText("to");
				}
				{
					ComboBoxModel jComboBoxToModel = new DefaultComboBoxModel(
							new String[] { "xx_XX", "en_US", "sv_SV" });
					jCBoxTo = new JComboBox();
					jPnlNorth.add(jCBoxTo);
					jCBoxTo.setModel(jComboBoxToModel);
				}
			}
			{
				jPnlCenter = new JPanel();
				BorderLayout jPanelCenterLayout = new BorderLayout();
				jPnlCenter.setLayout(jPanelCenterLayout);
				getContentPane().add(jPnlCenter, BorderLayout.CENTER);
				{
					jTxtFldIn = new JTextField();
					jPnlCenter.add(jTxtFldIn, BorderLayout.NORTH);
					jTxtFldIn.setBorder(BorderFactory
							.createTitledBorder("xx_XX"));
					jTxtFldIn.setPreferredSize(new java.awt.Dimension(390, 40));
					jTxtFldIn.setName("jTextFieldIn");
				}
				{
					ListModel jListOutModel = new DefaultComboBoxModel();
					jLstOut = new JList();
					jPnlCenter.add(jLstOut, BorderLayout.CENTER);
					jLstOut.setModel(jListOutModel);
					jLstOut.setBorder(BorderFactory.createTitledBorder(null,
							"xx_XX", TitledBorder.LEADING, TitledBorder.TOP));
					jLstOut.setOpaque(false);
				}
			}
			{
				jMBarMain = new JMenuBar();
				setJMenuBar(jMBarMain);
				{
					jMnuFile = new JMenu();
					jMBarMain.add(jMnuFile);
					jMnuFile.setText("File");
					{
						jMnuIExit = new JMenuItem();
						jMnuFile.add(jMnuIExit);
						jMnuIExit.setText("Exit");

					}
				}
				{
					jMnuOptions = new JMenu();
					jMBarMain.add(jMnuOptions);
					jMnuOptions.setText("Options");
					{
						jMnuLanguages = new JMenu();
						jMnuOptions.add(jMnuLanguages);
						jMnuLanguages.setText("Language");
						{
							jMnuIEng = new JMenuItem();
							jMnuLanguages.add(jMnuIEng);
							jMnuIEng.setText("en_US");

						}
						{
							jMnuISv = new JMenuItem();
							jMnuLanguages.add(jMnuISv);
							jMnuISv.setText("sv_SV");
						}
					}
				}
				{
					jMnuAbout = new JMenu();
					jMBarMain.add(jMnuAbout);
					jMnuAbout.setText("About");
					{
						jMnuIAbout = new JMenuItem();
						jMnuAbout.add(jMnuIAbout);
						jMnuIAbout.setText("About");

					}
				}
			}
			// TODO pack();
			this.setSize(400, 400);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
// Snippet to stop the comboboxes from firing events
/*jCBoxFrom.removeActionListener(langListener);
jCBoxFrom.setSelectedIndex(0);
jCBoxFrom.addActionListener(langListener);	

jCBoxTo.removeActionListener(langListener);
jCBoxTo.setSelectedIndex(0);
jCBoxTo.addActionListener(langListener);*/	