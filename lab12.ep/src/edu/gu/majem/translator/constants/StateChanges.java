package edu.gu.majem.translator.constants;

/**
 *  
 * These are the the names of the state change events that's 
 * fired by the translator implementation. 
 * To be used by observers,
 * 
 * @author hajo
 *
 */
public enum StateChanges { 
   FROM_LANG_CHANGED,
   TO_LANG_CHANGED, 
   INPUT_CHANGED, 
   TRANSLATIONS_CHANGED
}
