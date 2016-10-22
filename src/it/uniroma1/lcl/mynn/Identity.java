package it.uniroma1.lcl.mynn;

/**
 * Classe che incapsula il comportamento della funzione matematica Identity e della sua derivata.
 * Implementa l'interfaccia ActivationFunction.
 * @author Gimmi
 *
 */

public class Identity implements ActivationFunction {
	
	/**
	 * Esegue la formula normale di Identity
	 */
	@Override
	public double execute(double x) {
		return x;
	}
	
	/**
	 * Esegue la derivata di Identity
	 */

	@Override
	public double executeDerivate(double x) {
		return 1;
	}

}
