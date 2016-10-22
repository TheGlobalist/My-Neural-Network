package it.uniroma1.lcl.mynn;


/**
 * Classe che incapsula il comportamento della funzione matematica ReLu e della sua derivata.
 * Implementa l'interfaccia ActivationFunction.
 * @author Gimmi
 *
 */

public class ReLu implements ActivationFunction {
	
	/**
	 * Esegue la formula normale di ReLu
	 */

	@Override
	public double execute(double x) {
		if (x < 0.0) {
			return 0.0;
		} else {
			return x;
		}
	}
	
	/**
	 * Esegue la derivata di ReLu
	 */
	
	@Override
	public double executeDerivate(double x) {
		if (x < 0.0) {
			return 0.0;
		} else {
			return 1;
		}
	}

}
