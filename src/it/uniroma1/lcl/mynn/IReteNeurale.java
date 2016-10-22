package it.uniroma1.lcl.mynn;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.BiPredicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Interfaccia che definisce i metodi basilari che ogni Rete Neurale dovrebbe avere.
 * Viene fornita solo l'implementazione del metodo statico carica.
 * @author Gimmi
 *
 */

public interface IReteNeurale {

	public double[] process(double[] values);

	public double trainIstanza(double[] values, double output[]);

	public void train(double[][] inputs, double[][] outputs);

	public String getNome();
	
	/**
	 * Preso in input il nome di un file che conterrà le informazioni sulla rete neurale,
	 * il metodo carica restituisce una Rete Neurale costruita basandosi sulle informazioni 
	 * del file che ha letto
	 * @param filename il nome che avrà il file da cui caricare la rete
	 * @return un'istanza di una nuova rete neurale caricata dal file 
	 */

	public static IReteNeurale carica(String filename) {
		
		   /*
		    * Variabili che conterranno le informazioni che verranno estrapolate da file
		    */

		    String[] activationFunction;
			int[] inputUnits;
			int[] outputUnits;
			List<String> weights;
			StratoDiNeuroni[] strati;
			

			/*
			 * Leggo il file della rete che ricevo da input
			 */
			String extracted = null;
			try {
				extracted = Files
						.lines(Paths
								.get(filename))
						.map(Object::toString)
						.collect(Collectors.joining("\n"));

			} catch(IOException ex) {
				ex.printStackTrace();
			}



			/*
			 * Questo sarà il frammento di codice che determinerà quanti layer si ha nella rete.
			 * Di conseguenza, il frammento che mi permetterà di istanziare con la giusta larghezza
			 * gli array
			 */

			/*
			 * Come criterio di matching, fornisco alla regex uno di questi tre valori, a scelta casuale: inputunits, outputunits,
			 * activationfunction.
			 * layer no perché può comparire più di una volta, weights può non esserci e nome si 
			 * ripeterebbe più volte tra un layer ed un altro, portando ad un risultato sbagliato.
			 * Usare uno di questi tre valori riesce a fornire invece una stima giusta ed approssimativa 
			 * di quanti strati si hanno.
			 *
			 */

			//Scegliamo come pattern matching quindi inputUnits...
			Pattern p = Pattern.compile("inputUnits");
			//... e vediamo quante volte questa stringa compare in extracted
			Matcher m = p.matcher(extracted);
			int numberOfLayers = 0;
			while (m.find()){
				numberOfLayers ++;
			}

			/*
			 A questo punto, si inizializzano i vari array con la corretta grandezza
			 */

			activationFunction = new String[numberOfLayers];
			inputUnits = new int[numberOfLayers];
			outputUnits = new int[numberOfLayers];
			weights = new ArrayList<>();
			strati = new StratoDiNeuroni[numberOfLayers];

	
            //Definizione dell'array che conterrà le informazioni di split
			String[] support = extracted.split("\n");

			/*
			 * Ora si fa un for per estrapolare le informazioni necessarie.
			 * L'idea è quella di far partire il ciclo da 1 perché l'elemento 0 è, senz'ombra di dubbio, 
			 * il nome della rete che non interessa al fine dell'istanziazione dei neuroni.
			 * Prima del for si crea un'altra variabile di tipo int che parte da 0 che verrà usata per riempire gli array
			 */

			int x = 0;
			for (int i = 1; i < support.length; i++) {
				//Estrapolo l'i-simo elemento dell'array
				String tmp = support[i];
				//Se contiene activation...
				if (tmp.contains("activation")) {
					//estrapolo la funzione di attivazione e la salvo nel relativo array
					activationFunction[x] = tmp.substring(tmp.indexOf("activation")+19, tmp.indexOf("inputUnits")-1);
				}
				//Se contiene input
				if (tmp.contains("input")) {
					//Estrapolo le inputUnits, le converto in interi e le inserisco nel relativo array
					inputUnits[x] = Integer.parseInt(tmp.substring(tmp
							.indexOf("inputUnits")+11,
							tmp.indexOf("outputUnits")-1));
				}
				//Lo stesso procedimento viene quindi ripetuto per output e weights
				if (tmp.contains("output")) {
					String temp = tmp.substring(tmp
							.indexOf("outputUnits")+11,
							tmp.length());
					outputUnits[x] = Integer.parseInt(temp.substring(temp
									.indexOf("=")+1,
									temp.indexOf(" ")));
				}
				if (tmp.contains("weights")) {
					weights.add(tmp.substring(tmp.indexOf("weights"), tmp.lastIndexOf("}")));
				}
				x++;
			}
			
			/*
			 * Adesso bisogna istanziare gli strati.
			 * Vengono fatti due cicli for: nel più esterno si crea il nuovo strato, mentre nel più interno 
			 * si riempie lo strato appena creato con il nuovo neurone <=> dalla rete non si sono estrapolati 
			 * i pesi, in quanto assenti.
			 * 
			 * Questa parte di codice viene raggiunta soltanto se la condizione dello stream nell'if è soddisfatta,
			 * ovvero che tutti gli elementi dell'ArrayList dei pesi sono a null
			 */

			if(weights.stream().allMatch(s -> s == null)) {
				for(int i = 0; i < numberOfLayers ; i++) {
					strati[i] = new StratoDiNeuroni(activationFunction[i]);

					for (int j = 0; j < outputUnits[i]; j++) {
						strati[i].add(new Neurone(activationFunction[i], inputUnits[i], outputUnits[i]));
					}
				}
			/*
			 * Alternativamente, se ci sono i pesi, si hanno due cicli for in cui nel più esterno 
			 * si inizializza un nuovo strato e nel più interno si prepara una stringa che conterrà i pesi 
			 * del relativo neurone.	
			 */
			} else {



			for(int i = 0; i < numberOfLayers; i++ ) {
				strati[i] = new StratoDiNeuroni(activationFunction[i]);
				int y = 0;

				for (int j = 0; j < outputUnits[i]; j++) {
					String[] supportingWeights = weights.get(i).substring(weights.get(i).indexOf("=")+1)
							.replace("[", " ")
							.replace("]", " ")
							.trim()
							.split(",");
					String weightsToSend = "";
					do {
						weightsToSend += supportingWeights[y] + ",";
						y++;
						if (y == supportingWeights.length-1) {
							break;
						}
					} while (y != inputUnits[i]);
					strati[i].add(new Neurone(activationFunction[i], inputUnits[i],
					outputUnits[i], weightsToSend, Double.parseDouble(supportingWeights[y])));
					y++;
				}
		}
	}
			
			/*
			 * Adesso va fatto il check della teoria delle reti neurali, ovvero outputUnits 
			 * di uno strato == inputUnits altro
			 * strato e activationFunction uguali
			 */
			
			if (numberOfLayers > 1) {
				List<Boolean> checkBools = new ArrayList<>();
		        BiPredicate<StratoDiNeuroni, StratoDiNeuroni> condition = (a,b) -> a.getOuUnLay() == b.getInUnLay() && a.getActivationFunction().equals(b.getActivationFunction());
		        
		        
				for(int i = 0; i < strati.length; i++) {
					checkBools.add(condition.test(strati[i], strati[++i]));
				}

				/*
				 * Se esiste anche un solo false nell'arraylist dei booleani, la rete non è valida e bisogna quindi
				 * lanciare una runtime exception
				 */
		          if (checkBools.stream().anyMatch(s-> s == false)) {
		                  throw new NetworkNotGoodException("La rete caricata da input non può essere "
		                  		+ "considerata valida in quanto non sono state rispettate le proprietà basilari di una rete.");
		          }
			}

			
	//se si è giunti fino a qui, vuol dire che l'operazione è andata a buon fine ed è possibile tornare una nuova istanza della rete 
    return new ReteNeurale(extracted, numberOfLayers, strati);


	}
  }
