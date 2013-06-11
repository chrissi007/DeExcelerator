package de.tudresden.deexcelerator.data;

import de.tudresden.deexcelerator.Configuration;
import de.tudresden.deexcelerator.util.MatrixCell;
import de.tudresden.deexcelerator.util.RangeMatrix;

/**
 * <span class="de">Dokumentinformationen, welche f&uumlr die Extraktion ben&oumltigt werden.</span>
 * <span class="en">Documentinformation, for the extraction.</span>
 * @author Christopher Werner
 *
 */
public class DocumentInformation {

	/** <span class="de">Definiert das zu nutzende Functionchoose Objekt.</span>
	 * <span class="en">Defined the used document functions.</span> */
	private DocumentFunctions df;	
	/** <span class="de">Definiert das zu nutzende Stringfunction Objekt.</span>
	 * <span class="en">Defined the used string functions.</span> */
	private StringFunctions sf;
	/** <span class="de">Evaluations Objekt zum Sammeln verschiedenen Statistiken.</span>
	 * <span class="en">Evaluation object to collect different statistics.</span> */
	//private Evaluation ev;
	/** <span class="de">Gibt die verwendeten Konfigurationsparameter an.</span>
	 * <span class="en">Configuration object for the specific parameters.</span> */
	private Configuration cp;
	
	/**
	 * <span class="de">Konstruktor.</span>
	 * <span class="en">Constructor.</span>
	 */
	private DocumentInformation () {
		sf = new StringFunctions();
		//ev = new Evaluation(this);
		cp = new Configuration();
	}
	
	/**
	 * <span class="de">Konstruktor nimmt die eingehenden Dokument Informationen auf.</span>
	 * <span class="en">Constructor to safe the document information.</span>
	 * @param isMatrix (<span class="de">Wahrheitswert, ob Matrix oder nicht</span>
	 * <span class="en">say if it is an string array or a cell array</span>)
	 * @param matrixCell (<span class="de">Feld von Cellen</span>
	 * <span class="en">array of cells</span>)
	 * @param matrix (<span class="de">Stringarray der Matrix</span>
	 * <span class="en">string array of the document</span>)
	 * @param matrixName (<span class="de">Matrixname</span>
	 * <span class="en">name of the array, can be the document name</span>)
	 * @param ranges (<span class="de">Bereiche der Matrix</span>
	 * <span class="en">ranges in the document, which are find in the analyze</span>)
	 */
	public DocumentInformation(boolean isMatrix, MatrixCell[][] matrixCell, String[][] matrix, String matrixName, RangeMatrix[] ranges) {
		this();
		if (ranges == null)
			df = new DocumentFunctions(isMatrix, matrixCell, matrix, matrixName, new RangeMatrix[0]);
		else
			df = new DocumentFunctions(isMatrix, matrixCell, matrix, matrixName, ranges);
	}
	
	/**
	 * <span class="de">Gibt das FunctionChooseCellMatrix Objekt zur&uumlck.</span>
	 * <span class="en">Gives the document function object back.</span>
	 * @return <span class="de">df</span>
	 * <span class="en">df</span>
	 */
	public DocumentFunctions getFc() {
		return df;
	}

	/**
	 * <span class="de">Gibt das StringFunction Objekt zur&uumlck.</span>
	 * <span class="en">Gives an object back, where you can find string functions.</span>
	 * @return <span class="de">sf</span>
	 * <span class="en">sf</span>
	 */
	public StringFunctions getSf() {
		return sf;
	}

	/**
	 * <span class="de">Setzt das Evaluations Objekt neu.</span>
	 * <span class="en">Sets the evaluation object.</span>
	 * @param ev (<span class="de">neues Evaluations Objekt</span>
	 * <span class="en">the new evaluation object</span>)
	 */
	//public void setEv(Evaluation ev) {
	//	this.ev = ev;
	//}

	/**
	 * <span class="de">Gibt das Evaluations Objekt zur&uumlck.</span>
	 * <span class="en">Gets the evaluation object.</span>
	 * @return <span class="de">ev</span>
	 * <span class="en">ev</span>
	 */
	//public Evaluation getEv() {
	//	return ev;
	//}
	
	/**
	 * <span class="de">Setzt neues Configuration Objekt.</span>
	 * <span class="en">Sets the new configuration object.</span>
	 * @param cp (<span class="de">neues ConfigurationParameter Objekt oder null</span>
	 * <span class="en">new configuration object or null</span>)
	 */
	public void setCp(Configuration cp) {
		if (cp != null)
			this.cp = cp;
	}

	/**
	 * <span class="de">Gibt das verwendete Configuration Objekt zur&uumlck.</span>
	 * <span class="en">Gets the configuration object.</span>
	 * @return <span class="de">cp</span>
	 * <span class="en">cp</span>
	 */
	public Configuration getCp() {
		return cp;
	}
}
