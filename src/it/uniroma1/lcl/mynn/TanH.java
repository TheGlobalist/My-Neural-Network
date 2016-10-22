package it.uniroma1.lcl.mynn;


/**
 * Classe che incapsula il comportamento della funzione matematica TanH e della sua derivata.
 * Implementa l'interfaccia ActivationFunction.
 * @author Gimmi
 *
 */

public class TanH implements ActivationFunction {
	
	/**
	 * Esegue la formula normale di tanH
	 */

	@Override
	public double execute(double x) {
		return ((2)/(1+Math.exp(-2*x)))-1;
	}
	
	/**
	 * Esegue la derivata di tanH
	 */

	@Override
	public double executeDerivate(double x) {
		return 1- (((2)/(1+Math.exp(-2*x)))-1) * (((2)/(1+Math.exp(-2*x)))-1);
	}

}
