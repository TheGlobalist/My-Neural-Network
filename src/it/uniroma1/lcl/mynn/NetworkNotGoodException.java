package it.uniroma1.lcl.mynn;

  /**
   * Eccezione che estende Runtime Exception che verrà lanciata solo nel caso 
   * in cui la rete che si sta caricando da file non rispetti le proprietà di rete neurale,
   * ovvero outputsUnits uguali alle inputUnits dello strato successivo e stessa funzione di 
   * attivazione.
   * @author Gimmi
   *
   */

public class NetworkNotGoodException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * Chiamata al costruttore di questa exception, che restituirà un'istanza a questa
	 * @param s ovvero la stringa del messaggio di errore
	 */
	public NetworkNotGoodException(String s) {
	    super(s);
	  }

	}
