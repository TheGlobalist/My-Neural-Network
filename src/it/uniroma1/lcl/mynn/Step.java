package it.uniroma1.lcl.mynn;


/**
 * Classe che incapsula il comportamento della funzione matematica Step e della sua derivata.
 * Implementa l'interfaccia ActivationFunction.
 * @author Gimmi
 *
 */

public class Step implements ActivationFunction {
	
	/** 
	 * Esegue la formula normale di Step
	 */

	@Override
	public double execute(double x) {
		if (x < 0.0) {
			return 0.0;
		} else {
			return 1.0;
		}
	}

	/**
	 * Esegue la derivata di Step che altro non è che un semplice richiamo al 
	 * metodo execute(), non avendo Step una derivata
	 */

	@Override
	public double executeDerivate(double x) {
		return execute(x);
	}

}
