package edu.gu.majem.dict;

/**
 * The internal representation of language names
 * 
 * NOTE: 
 * To switch from string to Language use; 
 * Language l = Language.valueOf(string)
 * 
 * String must exactly match enum value, 
 * (xx_XX matches "xx_XX") 
 * 
 * To switch from Language to string
 * String s = l.toString()
 * 
 * @author hajo
 *
 */
public enum LanguageName {
   xx_XX,  // The undefined language
   sv_SV,
   en_US,
   de_DE
   // More to be added
}



