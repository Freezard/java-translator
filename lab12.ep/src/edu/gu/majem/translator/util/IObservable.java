package edu.gu.majem.translator.util;

import java.beans.PropertyChangeListener;

/**
 * 
 * Any class that can be observed
 * This is based on the Java beans event model
 * @author hajo
 *
 */
public interface IObservable {
	/**
	 * Adds an observer
	 * @param observer: the observer to add
	 */
	public void addObserver(PropertyChangeListener observer);
}
