package edu.gu.majem.translator.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.border.LineBorder;

import edu.gu.majem.dict.LanguageName;
import edu.gu.majem.dict.exception.DictionaryException;
import edu.gu.majem.translator.constants.StateChanges;
import edu.gu.majem.translator.core.ITranslator;
import edu.gu.majem.translator.core.impl.Translator;

/**
 * Panel for the keyboard.
 * Changes depending on from-language (different key sets)
 * @author hajo
 *
 */
@SuppressWarnings("serial")
public class KeyboardPanel extends JPanel implements PropertyChangeListener {
	private Insets insets = new Insets(2, 2, 3, 3);
	private Font font = new Font("Dialog", 1, 12);

	// Should of course be in some file
	private final static String en_US_LABLES = "q w e r t y u i o p a s d f g h j k l z x c v b n m";
	private final static String sv_SV_LABLES = "q w e r t y u i o p å a s d f g h j k l ö ä z x c v b n m";
	
	private int rows = 3;
	private int cols = 14;
	private JButton[] buttons;

	public KeyboardPanel() {
		initGUI();
		initKeys(en_US_LABLES);
	}
	
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		String name = evt.getPropertyName();
		
		if (name == StateChanges.FROM_LANG_CHANGED.toString()){
			LanguageName l = (LanguageName) evt.getNewValue();
			this.removeAll(); // Clears the keyboard
			
			if (l == LanguageName.en_US)
				initKeys(en_US_LABLES);
			else if (l == LanguageName.sv_SV){
				initKeys(sv_SV_LABLES);
			}
		}
		validate();
	}

	public void initGUI() {
		GridLayout gridLayout = new GridLayout(rows, cols);
		gridLayout.setHgap(2);
		gridLayout.setVgap(2);
		setLayout(gridLayout);
		setBorder(new LineBorder(Color.BLACK, 1));
	}

	private void initKeys(String labels) {
		String[] ls = labels.split(" ");
		buttons = new JButton[ls.length];
		int i;
		for (i = 0; i < ls.length; i++) {
			JButton btn = new JButton();
			btn.setName("jButtonKey");
			btn.setFont(font);
			btn.setMargin(insets);
			btn.addActionListener(listener);
			btn.setActionCommand(ls[i]);
			btn.setText(ls[i]);
			buttons[i] = btn;
			this.add(btn, null);
		}
	}

	// Same listener for all buttons
	private ActionListener listener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			ITranslator t = Translator.getInstance();
			t.addInput((((JButton) e.getSource()).getText()));
		}
	};
}
