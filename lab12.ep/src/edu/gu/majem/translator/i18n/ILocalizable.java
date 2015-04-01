package edu.gu.majem.translator.i18n;

import java.util.ResourceBundle;

/**
 * All localizable part of GUI must implement this
 * @author hajo
 */
public interface ILocalizable {
	public void localize(ResourceBundle rb);
}
