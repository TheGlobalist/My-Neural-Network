package it.uniroma1.lcl.mynn;

/**
 * Interfaccia che espone due metodi che ritornano dei double. Andrà implementata 
 * su tutte le classi che rappresenteranno le varie funzioni di attivazione.
 * @author Gimmi
 *
 */

public interface ActivationFunction {
	double execute(double x);
	double executeDerivate(double x);
}
