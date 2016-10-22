package it.uniroma1.lcl.mynn;

import java.util.*;

/**
 * La classe Neurone conterrà informazioni sul Neurone, in particolare:
 * 1) quale funzione di attivazione ha;
 * 2) le input ed output units;
 * 3) i pesi ed il threshold;
 * 4) la costante d'apprendimento.
 * 
 * Oltre a questo, la classe espone i metodi per impostare i pesi 
 * (e ne ha due privati per lo stesso scopo), aggiornare i pesi, getters.
 * Si tratta di una classe di supporto per la vera classe "principale", ReteNeurale.
 * 
 * @author Gimmi
 *
 */

public class Neurone {
	
	  private String activationFunction;
	  private int inputUnits;
	  private int outputUnits;
	  private double[] weigths;
	  private double threshold;
	  private static final double learningConstant = 0.2;
	  private ActivationFunction function;
	  
      /**
       * Chiamata alla prima variante del costruttore che creerà una nuova istanza della classe ed 
       * imposterà i pesi ed il threshold.
       * @param activationFunction la stringa della funzione di attivazione di questo neurone
       * @param inputUnits di questo neurone
       * @param outputUnits di questo neurone
       * @param weightsToSend la stringa che contiene l'informazione sui pesi
       * @param theThreshold il valore del threshold
       */

	  public Neurone(String activationFunction, int inputUnits, int outputUnits, String weightsToSend, double theThreshold) {
	    this.activationFunction = activationFunction;
	    this.inputUnits = inputUnits;
	    this.outputUnits = outputUnits;
	    setWeights(weightsToSend);
	    setThreshold(theThreshold);
	  }
	  
	  /**
	   * Chiamata alla seconda variante del costruttore che riceve in input i seguenti elementi
	   * @param activationFunction la stringa della funzione di attivazione di questo neurone
	   * @param inputUnits di questo neurone
	   * @param outputUnits di questo neurone
	   */

	  public Neurone(String activationFunction, int inputUnits, int outputUnits) {
	    this.activationFunction = activationFunction;
	    this.inputUnits = inputUnits;
	    this.outputUnits = outputUnits;
	    setWeights(null);
	  }
	  
	  /**
	   * Imposta il valore del nuovo threshold
	   * @param thresholdToSet il valore del nuovo threshold da impostare
	   */
	  public void setThreshold(double thresholdToSet) {
	      threshold = thresholdToSet;
	  }
	  
	  /**
	   * Ritorna il valore del threshold del neurone
	   * @return il double che conterrà il valore del threshold
	   */
	  
	  public double getThreshold() { return threshold; }
	  
	  /**
	   * Questo metodo viene invocato generalmente dall'interno del costruttore per permettere
	   * la creazione dell'array che conterrà i pesi del neurone.
	   * @param toSet Stringa che conterrà l'informazione dei pesi da impostare
	   */
	  
	  public void setWeights(String toSet) {
		//L'array viene inizializzato con una grandezza pari a quella delle inputUnits di quel neurone
	    weigths = new double[inputUnits];
	    //se toSet è diversa da null...
	    if (toSet != null) {
	      //... viene chiamato un metodo dedicato che estrapolerà l'informazione dalla stringa
	      noRandomGenerate(toSet);
	    } else {
	      /*...altrimenti, vuol dire che nel file della rete non era presente nulla sui pesi.
	      * Sarà quindi necessario chiamare randomGenerate() per generare automaticamente n pesi 
	      * con valore compreso tra 0 e 1.
	      */
	      randomGenerate();
	    }
	  }
	  
	  /**
	   * Metodo che viene invocato qualora il file della rete neurale contenga informazioni sui pesi.
	   * Presa in input la stringa dell'informazione sui pesi, quest'ultima viene elaborata in modo da poter
	   * rendere possibile l'estrapolazione dei pesi dalla stringa e convertirli in double.
	   * @param toSet Stringa che contiene l'informazione dei pesi da impostare
	   */
	  
	  private void noRandomGenerate(String toSet) {
		/*
		 * Prima di tutto è necessario ripulire la stringa. 
		 * Per fare ciò sostituisco con replace le virgole con spazi bianchi, poi invoco trim()
		 * per rimuovere eventuali spazi bianchi che si sono generali ed infine inserisco i dati 
		 * estrapolati da toSet in un array con split().
		 */
	    String[] support = toSet
	        .replace(",", " ")
	        .trim()
			.split(" ");
	    int x = 0;
	    
	    //Riempimento dell'array dei pesi 	    
	    for (int i = 0; i < weigths.length; i++) {
	      weigths[i] = Double.parseDouble(support[x]);
	      x++;
	    }
	  }
	  
	  /**
	   * Se non viene fornita una stringa sui valori dei pesi, è necessario impostarli 
	   * in maniera randomica, threshold incluso.
	   * Questo metodo imposta quindi in maniera random il threshold e i pesi grazie alla classe
	   * Random() ed al metodo nextDouble(), che ritorna un double compreso tra 0 e 1.
	   */

	  private void randomGenerate() {
		
		  /*
		   * Con DecimalFormat, mi assicuro che il numero abbia due cifre dopo la virgola ma questo
		   * non fa altro che generarmi una stringa: per ovviare questo, si invoca il metodo 
		   * Double.parseDouble(). 
		   */
		  
		Random random = new Random();
		  
	    double thresholdToSet = random.nextDouble();
	        //Settaggio del nuovo threshold
			setThreshold(thresholdToSet);
			/*Riempimento dell'array dei pesi con valori randomici. La logica seguita è 
			 * la stessa del threshold di qualche riga sopra
			 */
			for(int i = 0; i < weigths.length; i++) {
					weigths[i] =
							random.nextDouble();
				}
	  }
	  
	  /**
	   * Ritorna il valore delle inputUnits del neurone
	   * @return l'intero che rappresenta il numero delle inputUnits di quel Neurone
	   */
	  
	  public int getInputUnits() { return inputUnits; }
	  
	  /**
	   * Ritorna il valore delle outputUnits del neurone
	   * @return l'intero che rappresenta il numero delle outputUnits di quel neurone
	   */

	  public int getOutputUnits() { return outputUnits; }
	  
	  /**
	   * Ritorna il valore della stringa di attivazione del neurone
	   * @return la stringa che conterrà l'informazione circa la funzione di attivazione del neurone
	   */

	  public String getActivationFunction() { return activationFunction; }
	  
	  /**
	   * Ritorna il valore della costante d'apprendimento del neurone.
	   * @return il double che rappresenta il valore della costante d'apprendimento del neurone.
	   */
	  
	  public double getLearningConstant() { return learningConstant; }
	  
	  /**
	   * sumAndActivate è un metodo che applica la seguente formula matematica
	   * 
	   * y = f((sommatoria da i di w_i * x_i) + threshold)
	   * 
	   * Dove f è la funzione di attivazione, w_i è l'i-simo peso, x_i è l'i-simo input.
	   * 
	   * Il valore che ritorna questo metodo è quindi y, ovvero il valore numerico 
	   * risultante dopo aver la funzione di attivazione.
	   * 
	   * @param values l'array di valori su cui lavorare
	   * @return il valore y
	   */

	  public double sumAndActivate(double[] values) {
		  
		  //Inizializzazione della variabile che ospiterà l'oggetto da istanziare con reflection
			
			
			/*
			 * Essendo la reflection una tecnica che può lanciare exception, si è scelto di mettere
			 * la chiamata ai metodi della reflection in un blocco try-catch per gestire poi l'eccezione.
			 *  
			 */
			
			try {
				//Cerca nel package it.uniroma1.lcl.mynn la classe dell'activationFunction specificata dal file della rete
				function = (ActivationFunction) Class.forName("it.uniroma1.lcl.mynn." + activationFunction.trim()).newInstance();
				//e ne si crea una nuova istanza
			} catch(IllegalAccessException ex) {
				System.out.println("Non è stato trovato o fornito nulla in input.");
			} catch (InstantiationException ex) {
				System.out.println("Non è stato trovato o fornito nulla in input.");
			} catch(ClassNotFoundException ex) {
				System.out.println("Non è stato trovato o fornito nulla in input.");
			} catch(NullPointerException ex) {
				System.out.println("Non è stato trovato o fornito nulla in input.");
			}
			//inizializzazione della variabile da ritornare
			double toReturn = 0;
			//Eseguo la sommatoria di w_i * x_i
			for(int i = 0; i < values.length; i++) {
					toReturn += weigths[i] * values[i];
				}
			/* Il risultato della sommatoria viene sommato al threshold e mandato alla funzione di 
			 * attivazione, per poi essere ritornato dal metodo
			*/
			return function.execute(toReturn + threshold);
		}

	 
	  /**
	   * Aggiorna i pesi in accordo con la formula di aggiornamento dedicata alla Rete Neurale chiamata "Percettrone". 
	   * @param inputs un array di double degli input 
	   * @param outputs un array di double degli output desiderati
	   * @param processedValues un array di double degli output prodotti dal metodo process
	   * @param x l'indice del neurone che si sta vedendo
	   */

	public void updatePerceptronNetwork(double[] inputs, double[] outputs, 
			double[] processedValues, int x) {
		
		    //Ciclo per aggiornare i pesi
			for(int i = 0; i < inputs.length; i++) {
				//w_i = w_i + learningConstant * (output_x - processedValues_x)*inputs_i
				weigths[i] += learningConstant*(outputs[x] - processedValues[x])*inputs[i];
			}
			
			
		}
	
	/**
	 * Aggiorna i pesi in accordo alla formula di aggiornamento generalizzata
	 * @param inputs un array di double degli input 
	 * @param outputs un array di double degli output desiderati
	 * @param processedValues un array di double degli output prodotti dal metodo process
	 * @param x l'indice del neurone che si sta vedendo
	 */

	public void update(double[] inputs, double[] outputs, double[] processedValues, int x) {
	    //Ciclo per aggiornare i pesi
		for(int i = 0; i < weigths.length; i++) {
			//w_i = w_i + learningConstant 
			//* (output_x - processedValues_x)*f'((sommatoria da j di w_j * x_j) + threshold)*inputs_i

			weigths[i] += learningConstant*(outputs[x] - processedValues[x]) *
					activateDerivateAndSum(inputs) * inputs[i];
		}
		
		
	}
	
	  /**
	   * activateDerivateAndSum è un metodo che applica la seguente formula matematica
	   * 
	   * y = f'((sommatoria da i di w_i * x_i) + threshold)
	   * 
	   * Dove f' è la derivata della funzione di attivazione, w_i è l'i-simo peso, 
	   * x_i è l'i-simo input.
	   * Il valore che ritorna questo metodo è quindi y, ovvero il valore numerico 
	   * risultante dopo aver la funzione di attivazione.
	   * 
	   * @param values l'array di valori su cui lavorare
	   * @return il valore y
	   */
	

	public double activateDerivateAndSum(double[] values) {
		//Inizializzazione della variabile che ospiterà l'oggetto da istanziare con reflection
		
		//Inizializzazione della variabile da ritornare
		double toReturn = 0;
		//Eseguo la sommatoria di w_i * x_i
		for(int i = 0; i < values.length; i++) {
				toReturn += weigths[i] * values[i];
			}
		
		/* Il risultato della sommatoria viene sommato al threshold e mandato alla funzione di 
		 * attivazione, per poi essere ritornato dal metodo
		*/
		
		return function.executeDerivate(toReturn + threshold);

	}
	
	/**
	 * Ritorna la stringa che contiene l'informazione sui pesi
	 * @return la stringa che contiene l'informazione sui pesi correttamente formattata.
	 */
	
	public String createTheRepresentation() {
		//Inizializzazione della stringa da ritornare
		String toReturn = "[";
		for(int i = 0; i < weigths.length; i++) {
			//Se il double i-simo che si sta esaminando è un numero intero...
			if(weigths[i]%1 == 0) {
				//Lo si converte prima ad Integer con un cast e poi a String, eliminando inopportuni spazi ed aggiungendo una virgola
				toReturn += Integer.toString((int)weigths[i]).replace(" ", "")+",";
			} else {
				//Altrimenti si fa la stessa operazione per tutti gli altri numeri, evitando il passaggio del casting ad Integer
				toReturn += Double.toString(weigths[i]).replace(" ", "")+",";
			}
		}
		
		//Lo stesso procedimento viene ripetuto per il threshold, ritornando quindi la stringa
		if(threshold%1 != 0) {
			return toReturn += Double.toString(threshold).replace(" ", "");
		} else {
			return toReturn += Integer.toString((int)threshold).replace(" ", "");
		}
	}
	

}
