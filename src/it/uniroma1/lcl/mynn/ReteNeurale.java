package it.uniroma1.lcl.mynn;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * La classe principale dell'intero progetto.
 * Rete Neurale è una classe che contiene le informazioni sugli strati presenti,
 * la rete neurale caricata da input e il numero di strati. 
 * Implementa un'interfaccia, IReteNeurale.
 * Tutti i metodi presenti in questa classe sono in Override a quelli dell'interfaccia 
 * precedentemente citata.
 * La classe è responsabile del calcolo dell'output della rete e dell'eventuale allenamento
 * dell'intera rete. 
 * 
 * @author Gimmi
 *
 */


public class ReteNeurale implements IReteNeurale {

	private StratoDiNeuroni[] strati;
	private String extracted;
	private int numberOfLayers;
	
	/**
	 * Chiamata al costruttore ReteNeurale che creerà una nuova istanza della classe.
	 * @param extracted la Stringa che conterrà tutte le informazioni della rete caricata da file
	 * @param numberOfLayers il numero degli strati nella rete
	 * @param strati l'array che conterrà le informazioni sul numero di strati presenti nella rete.
	 */


  public ReteNeurale(String extracted, int numberOfLayers, StratoDiNeuroni...strati) {
		this.strati = strati;
		this.extracted = extracted;
		this.numberOfLayers = numberOfLayers;

  }
  
  
  
  /**
   * Metodo che riceve un insieme di addestramento e fa addestramento in 
   * accordo all'algoritmo d’addestramento fornito nelle specifiche del progetto
   */
  
  @Override
  public void train(double[][] inputs, double[][] outputs) {
	double sommaErrori;
	double errori;
	do {
		sommaErrori = 0;
		for(int i = 0; i < inputs.length; i++) {
			errori= trainIstanza(inputs[i], outputs[i]);
			sommaErrori += errori;
		}
	} while (sommaErrori > 0.01);
  }
  
  /**
   * * trainIstanza riceve una lista di valori in input e una lista dei valori attesi di output,
   * ovvero i valori che la rete idealmente dovrebbe restituire a fronte di quell’input. 
   * Il metodo, modifica i pesi in accordo con la formula di aggiornamento e restituisce la somma 
   * degli errori ottenuti dal confronto tra l’output effettivo della rete e quello ideale 
   * passato in input
   * 
   * @return il valore assoluto dell'errore calcolato
   */
    @Override
	public double trainIstanza(double[] inputs, double[] outputs) {
    	
    	//Generazione dell'array prodotto da process
	    double[] processedValues = process(inputs);
		double error;
		//Calcolo dell'errore
		error = calculateTheError(outputs, processedValues);
		//Inizio dell'aggiornamento dei pesi
        updateWeigths(inputs, outputs, processedValues);

        //il metodo fa return del valore assoluto dell'errore
		return Math.abs(error);
		}
    
    /**
     * Metodo privato di comodo usato per calcolare l'errore
     * @param outputs un array di double con gli output desiderati 
     * @param processedValues un array di double prodotto da process
     * @return l'errore presente
     */
	
  private double calculateTheError(double[] outputs, double[] processedValues) {
		double error = 0;
		//calcolo dell'errore
		for(int i = 0; i < outputs.length; i++) {
			error += outputs[i] - processedValues[i];
		}

		return error;
	}
  
  /**
   * Metodo che avvia l'aggiornamento dei pesi e del threshold
   * @param inputs un array di double passato da input
   * @param outputs un array di double dei valori attesi
   * @param processedValues un array di double creato da process
   */
	private void updateWeigths(double[] inputs, double[] outputs, double[] processedValues) {
		//Per ogni strato nella rete...
		for(StratoDiNeuroni t : strati) {
			//Aggiorno i valori dei pesi e del treshold di ogni neurone
			t.updateStart(inputs, outputs, processedValues, numberOfLayers);
		}

	}
	
	/**
	 * Riceve una lista di valori in input e restituisce l’output calcolato su di essi
	 * @return l'output calculato sulla lista passata da input
	 */

	@Override
	public double[] process(double[] values)  {
		
		//Inizializzazione dell'array da ritornare
		double[] toReturn = null;
		//Calcolo dell'array da generare
		for(int i = 0; i < strati.length; i++) {
			//Se è la prima iterazione...
			if(i == 0) {
				//Passo al metodo di comodo l'array values ricevuto da input
				toReturn = strati[i].generateTheLayersArray(strati[i].getOuUnLay(), values);
			} else {
				/*Altrimenti, se è uno strato diverso dal primo, passo i contenuti dell'array 
				 * da ritornare. Alla fine della procedura, quell'array verrà sovrascritto con
				 * dei nuovi valori.
				 */
				toReturn = strati[i].generateTheLayersArray(strati[i].getOuUnLay(), toReturn);
			}
		}

		return toReturn;
	}
	/**
     * Ritorna tutte le informazioni della rete fino a quel momento
     * 
     * @return la stringa contenente tutte le informazioni della rete
     *
     */
	@Override
	public String toString() { 
		//Si inizializza un array che contiene le informazioni sulla rete
		String[] support = extracted.split("\n");
		//Inizializzazione variabile per iterare sugli strati
		int j = 0;
		for(int i = 1; i < support.length; i++) {
			//Prendendo l'i-simo elemento di quell'array...
			String tmp = support[i];
			//Controllo se contiene la sottostringa "weights"
			if (tmp.contains("weights")) {
				//Se sì, estrapolo la sottostringa qui riportata
				support[i] = support[i].substring(0, support[i].indexOf("outputUnits")+12);
				//e a quella sottostringa si aggiungono le outputUnits (prese con relativo metodo dallo strato) e i pesi (con relativo metodo)
				support[i] += strati[j].getOuUnLay() + " weights=["+strati[j].getRepresentation() +" }";
			} else {
				//Se arrivati a questo punto, significa che non ci sono pesi nella rete. Si estrapola quindi la sottostringa qui riportata
				support[i] = support[i].substring(0, support[i].indexOf("outputUnits")+12);
				//E ripeto il procedimento di prima
				support[i] +=  strati[j].getOuUnLay() + " weights=["+strati[j].getRepresentation() +" }";
				//Se invece ci si trova di fronte ad una chiamata al toString con i pesi non ancora inizializzati...
				if (support[i].contains("weights=[]")) {
					//Si estrapola la sottostringa sottoriportata
					support[i] = support[i].substring(0, support[i].indexOf("weights=[]"));
					//E si aggiunge la rappresentazione
					support[i] += strati[j].getRepresentation() + "}";
				}
			}
			/*
			 * N.B.: Si è preferito riaggiungere "a mano" le outputUnits per una precisione maggiore della stringa
			 */
			
			//Incremento la variabile che mi permette di iterare sugli strati
			j++;
		}
		
		//L'array su cui ho lavorato lo "ricompongo" in una stringa attraverso il metodo Collectors.joining(), ritornando quindi la stringa desiderata
		return Arrays.asList(support).stream().collect(Collectors.joining("\n"));
	}

	
	/**
	 * Ritorna il nome della rete
	 * 
	 * @return il nome della rete
	 */

	@Override
	public String getNome() {
		return extracted.substring(extracted.indexOf("=")+1, extracted.indexOf("\n"));
	}


}
