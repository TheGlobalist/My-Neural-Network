package it.uniroma1.lcl.mynn;

/**
 * Classe che incapsula il comportamento della funzione matematica Logistic e della sua derivata.
 * Implementa l'interfaccia ActivationFunction.
 * @author Gimmi
 *
 */

public class Logistic implements ActivationFunction {
	/**
	 * Esegue la formula normale di Logistic
	 */
	@Override
	public double execute (double x) {
		return (1)/(1+Math.exp(-x));
	}
	
	/**
	 * Esegue la derivata di Logistic
	 */

	@Override
	public double executeDerivate(double x) {
		return ((1)/(1+Math.exp(-x)))*(1- (1)/(1+Math.exp(-x)));
	}

}
