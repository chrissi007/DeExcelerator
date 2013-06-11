package de.tudresden.deexcelerator.util;

import com.microsoft.research.webngram.service.LookupService;
import com.microsoft.research.webngram.service.NgramServiceFactory;

/**
 * <span class="de">Ngram zum pr&uumlfen von Zusammengeh&oumlrigkeiten.</span>
 * <span class="en">Ngram to proof the unity.</span>
 * @author Christopher Werner
 *
 */
public class BingLookup {

	//muss von Benutzer hinzugefügt werden falls er es verwenden will
	//standardmäßig nicht in Verwendung
	/** <span class="de">Verwendeter Applikations Key.</span>
	 * <span class="en">The used application key.</span> */
	private String APPLICATION_KEY= "";
	/**	<span class="de">Festlegen der Ngram Factory.</span>
	 * <span class="en">Ngram Factory, which is used.</span> */
	private NgramServiceFactory factory;
	/** <span class="de">Lookupservice zum aufrufen der Algorithmen.</span>
	 * <span class="en">Lookupservice for getting the answers.</span> */
	private LookupService service;
	
	/**
	 * <span class="de">Konstruktor zum initialisieren der Variablen.</span>
	 * <span class="en">Constructor for initialize the variables.</span>
	 */
	public BingLookup(String key) {
		APPLICATION_KEY = key;
		factory = NgramServiceFactory.newInstance(APPLICATION_KEY);
		service = factory.newLookupService();		
	}
	
	/**
	 * <span class="de">Erfasst den Ergebniswert der Probability</span>
	 * <span class="en">Gets the solution value from the probability.</span>
	 * @param wert (<span class="de">String wert f&uumlr Probability</span>
	 * <span class="en">String value for probability.</span>)
	 * @return <span class="de">double Wert der Probability</span>
	 * <span class="en">The solution double value of the probability.</span>
	 */
	public double giveProbability (String wert) {
		return service.getProbability(APPLICATION_KEY, "bing-body/jun09/3", wert);
	} 
	
	/**
	 * <span class="de">Gibt zur&uumlck welcher Wert eher passt.</span>
	 * <span class="en">Gets back what string has a bigger probability.</span>
	 * @param wert1 (<span class="de">String wert 1 zum Vergleichen</span>
	 * <span class="en">First value for comparison.</span>)
	 * @param wert2 (<span class="de">String wert 2 zum Vergleichen</span>
	 * <span class="en">Secand value for comparison.</span>)
	 * @return <span class="de">True wenn 1 dazu passt False wenn 2</span>
	 * <span class="en">If the first value is the right value its true, else it is false.</span>
	 */
	public boolean wertChecker (String wert1, String wert2) {
		double probability = service.getProbability(APPLICATION_KEY, "bing-body/jun09/3", wert1);
		double probability2 = service.getProbability(APPLICATION_KEY, "bing-body/jun09/3", wert2);
		//je kleiner der Wert desto mehr passen diese zusammen
		System.out.println(wert1 + " probability: " + probability + " Logarithmus: " + Math.log(probability));
		System.out.println(wert2 + " probability: " + probability2 + " Logarithmus: " + Math.log(probability2));
		if (probability>probability2)
			return true; //der wert 1 passt eher 
		else
			return false; //der wert 2 passt eher
	}
	
	/**
	 * <span class="de">Gibt zur&uumlck welcher Wert eher passt.</span>
	 * <span class="en">Gets back what string has a bigger probability.</span>
	 * @param wert1 (<span class="de">String wert 1 zum Vergleichen</span>
	 * <span class="en">First value for comparison.</span>)
	 * @param wert2 (<span class="de">String wert 2 zum Vergleichen</span>
	 * <span class="en">Second value for comparison.</span>)
	 * @return <span class="de">1 wenn Wert1 dazu passt 2 wenn Wert2</span>
	 * <span class="en">If the first value is the right value its '1', else it is '0'.</span>
	 */
	public int bingChecker (String wert1, String wert2) {
		double probability = service.getProbability(APPLICATION_KEY, "bing-body/jun09/3", wert1);
		double probability2 = service.getProbability(APPLICATION_KEY, "bing-body/jun09/3", wert2);
		//je kleiner der Wert desto mehr passen diese zusammen
		System.out.println(wert1 + " probability: " + probability + " Logarithmus: " + Math.log(probability));
		System.out.println(wert2 + " probability: " + probability2 + " Logarithmus: " + Math.log(probability2));
		if (probability>probability2)
			return 1; //der wert 1 passt eher 
		else
			return 2; //der wert 2 passt eher
	}
}
