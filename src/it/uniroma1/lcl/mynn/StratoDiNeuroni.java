package it.uniroma1.lcl.mynn;

import java.util.*;

/**
 * La classe StratoDiNeuroni rappresenterà lo strato.
 * Conterrà un ArrayList che avrà tutti i neuroni di quello strato, le informazioni sulle input ed outputunits dello strato
 * e la funzione di attivazione.
 * Si tratta di una classe di supporto per la vera classe "principale", ReteNeurale.
 * 
 * 
 * @author Gimmi
 *
 */

public class StratoDiNeuroni {

	List<Neurone> strato;
	private int inputUnitsOfThisLayer;
	private int outputUnitsOfThisLayer;
	private String activationFunction;
	//Variabile che si salva l'informazione sul threshold
	private double newThreshold;
	//Variabile necessaria per la rappresentazione dei pesi nel toString() della classe ReteNeurale
	
	/**
	 * Chiamata al costruttore che creerà una nuova istanza della classe.
	 * 
	 * @param activationFunction Stringa che conterrà l'informazione sulla funzione di attivazione
	 */

	public StratoDiNeuroni(String activationFunction) {
		//Inizializzazione dell'ArrayList che conterrà i neuroni
		strato = new ArrayList<>();
		this.activationFunction = activationFunction;
	}
	
	/**
	 * Aggiunge uno o più neuroni nello strato.
	 * @param neu uno o più neuroni da passare in input al metodo da inserire nello strato
	 */

	public void add(Neurone...neu) {
		/*Non sapendo quanti neuroni arriveranno da input, il metodo è stato impostato per la ricezione
		di un numero indefinito di questi*/
		for(Neurone x : neu) {
			//Aggiungi il neurone x-simo allo strato.
			strato.add(x);
		}
		/*
		 * Essendo inputUnits ed outputUnits uguali per tutti i neuroni di quello strato, si impostano
		 * qui le unità prendendo l'informazione dal primo neurone
		 */
		inputUnitsOfThisLayer = strato.get(0).getInputUnits();
		outputUnitsOfThisLayer = strato.get(0).getOutputUnits();
	}
	
	/**
	 * Ritorna il numero di inputUnits dello Strato
	 * @return il numero delle inputUnits dello strato
	 */
	
	public int getInUnLay() { return inputUnitsOfThisLayer; }
	
	/**
	 * Ritorna il numero di outputUnits dello Strato
	 * @return il numero delle outputUnits dello strato
	 */

	public int getOuUnLay() { return outputUnitsOfThisLayer; }
	
	/**
	 * Ritorna il nome della funzione di attivazione
	 * @return il nome della funzione di attivazione di questo strato
	 */
	public String getActivationFunction() { return activationFunction; }
	
	/**
	 * Ritorna un array che contiene i valori della funzione di attivazione di ogni neurone dello strato
	 * @param size la grandezza dell'array da generare
	 * @param values array di double passato da input
	 * @return un array di double di dimensioni size che conterrà "size valori" di attivazione dei neuroni
	 */

	public double[] generateTheLayersArray(int size, double[] values) {
		//Definizione ed istanziazione dell'array da ritornare
		double[] toReturn = new double[size];
		//Inizializzazione della variabile per iterare sui neuroni
		int i = 0;
		//Per ogni neurone nello strato, andrò a calcolare il valore della funzione d'attivazione
		for(Neurone t: strato) {
			//l'i-simo elemento dell'array da tornare conterrà il valore generato da questo metodo.
			toReturn[i] = t.sumAndActivate(values);
			//Incremento della variabile per l'iterazione sui neuroni
			i++;
		}
		return toReturn;
	}
	
	/**
	 * Aggiorna i pesi e il threshold in base al tipo della rete: se la rete in questione è il Percettrone e la sua funzione
	 * di attivazione è la Step, allora si avrà un tipo di aggiornamento. 
	 * Altrimenti, il metodo seguirà un'altra procedura di aggiornamento.
	 * @param inputs un array di double di valori passato da input
	 * @param outputs un array di double dei valori attesi 
	 * @param processedValues un array di double dei valori prodotti da process
	 * @param numberOfLayers un intero che definisce il numero di layer nella Rete
	 */

	public void updateStart(double[] inputs, double[] outputs, double[] processedValues, int numberOfLayers) {
		
		//Se il numero dei Layer è uno e la funzione di attivazione è Step...
		if (numberOfLayers == 1 && activationFunction.equals("Step")) {
			
			/*
			 * Si segue la formula di aggiornamento dedicata al Percettrone.
			 */
			//Inizializzazione della variabile che itererà sui neuroni
			int x = 0;
			for(Neurone t : strato) {
				//Chiamata al metodo di aggiornamento dei pesi
				t.updatePerceptronNetwork(inputs, outputs, processedValues, x);
				//aggiornamento del threshold
				newThreshold = t.getThreshold() + t.getLearningConstant() * (outputs[x] - processedValues[x]);
				//settaggio del nuovo threshold
				t.setThreshold(newThreshold);
				//incremento della variabile di iterazione
				x++;
			}
		} else {
			
			/*
			 * Si segue la formula di aggiornamento generalizzata
			 */
			//Inizializzazione della variabile che itererà sui neuroni
			int x = 0;
			for(Neurone t: strato) {
				//Chiamata al metodo di aggiornamento dei pesi
				t.update(inputs, outputs, processedValues, x);
				//aggiornamento del threshold
				newThreshold = t.getThreshold() + t.getLearningConstant() *(outputs[x]-processedValues[x])
						* t.activateDerivateAndSum(inputs);
				//settaggio del nuovo threshold
				t.setThreshold(newThreshold);
				//incremento della variabile di iterazione
				x++;
			}
		}
	}

	
	/**
	 * Restituisce la stringa della rappresentazione dei pesi
	 * @return la stringa che contiene l'informazione sui pesi attuali della rete.
	 */
	
	public String getRepresentation() { 
		
		//Inizializzazione della variabile da ritornare
		String toReturn = "";
		//Per ogni neurone nello strato...
		for (Neurone t: strato) {
			//Se i neuroni nello strato sono più di 1...
			if (outputUnitsOfThisLayer > 1) {
				//Chiamo il metodo che genera la rappresentazione dei pesi del neurone e faccio aggiungere un "],", per indicare che 
				//dopo c'è ancora qualcosa
				toReturn += t.createTheRepresentation() + "],";
			} else {
				//Altrimenti chiamo il metodo che genera la rappresentazione dei pesi del neurone e chiudo la rappresentazione con "]"
				toReturn += t.createTheRepresentation() + "]";
			}
		}
		
		//Se però dovessero esserci più neuroni in uno strato e questa condizione dovesse rivelarsi vera ( > 0 )...
		if(toReturn.lastIndexOf("],") > 0) {
			//Ritorno la sottostringa che eliminerebbe l'ultima ,
			return toReturn.substring(0, toReturn.lastIndexOf(",")) + "]";
		} else {
			//Altrimenti ritorno semplicemente la stringa con l'aggiunta di una "]"
			return toReturn+"]";
		}
	}
	
}
