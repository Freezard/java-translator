package edu.gu.majem.dict.exception;

/**
 * Use class to get higher level exceptions.
 * Usage: Catch exceptions end re-throw like this
 * 
 * try{
 *   :
 * } catch( IOException e){  // low level exception 
 *    throw new DictionaryException(e);
 * }
 * 
 * 
 * @author hajo
 *
 */
public class DictionaryException extends Exception {

   private static final long serialVersionUID = 1L;

   public DictionaryException() {
   }

   public DictionaryException(String message) {
      super(message);
   }

   public DictionaryException(Throwable cause) {
      super(cause);
      
   }

   public DictionaryException(String message, Throwable cause) {
      super(message, cause);
   }

}
