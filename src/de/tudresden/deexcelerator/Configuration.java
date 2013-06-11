package de.tudresden.deexcelerator;

import de.tudresden.deexcelerator.util.Database;

/**
 * <span class="de">Belegen der Konfigurationsparameter in der Pipeline.</span>
 * <span class="en">Settings for the configuration parameters in the pipeline.</span>
 * @author Christopher Werner
 *
 */
public class Configuration {
	
	/** <span class="de">Gibt das Datenbank objekt an in das gespeichert wird.</span>
	 * <span class="en">The name and information about the database the user want to safe the relation.</span> */
	public Database d;
	/** <span class="de">Gibt die Regeln an welche genutzt werden.</span>
	 * <span class="en">Say what rules the DeExcelerator uses.</span> */
	public FunctionSelection fs;
	
	/** <span class="de">Es gibt nur eine Relation in der Tabelle.</span>
	 * <span class="en">There is only on table in the document.</span> */
	public boolean onlyOneTable;
	//ConfigurationsWert zur Verschnellerung des Algortihmus
	/** <span class="de">Anzahl Zeilen bei denen auf Headerlinie gepr&uumlft wird.</span>
	 * <span class="en">Number of rows, which are need for header recognition.</span> */
	public int numberOfRows;
	/** <span class="de">Maximale nicht Inhaltsspalten die in einer Zeile vorhanden sein m&uumlssen.</span>
	 * <span class="en">The maximum value for empty cells in a row.</span> */
	public int limitForNoContent;
	/** <span class="de">Maximale Inhaltsspalten die in einer Zeile vorhanden sein m&uumlssen.</span>
	 * <span class="en">The maximum number of non empty cells in a row.</span> */
	public int limitForContentCells;
	//TotalFinderSpalte
	/** <span class="de">Abweichung zur berechneten Summe. (Spalte)</span>
	 * <span class="en">Maximum derivation of a sum in one col.</span> */
	public double deviationCol;
	/** <span class="de">Anzahl der Summanden die ben&oumltigt werden um eine Summe zu bilden.</span>
	 * <span class="en">Number of summands which will be need for making a sum.</span> */
	public int summandNumber;
	/** <span class="de">Summe muss eine bestimmte Gr&oumlsse haben. (Spalte)</span>
	 * <span class="en">The minimal value for a sum in one col.</span> */
	public int sizeSumCol;
	//TotalFinderZeile
	/** <span class="de">Summe muss eine bestimmte Gr&oumlsse haben. (Zeile)</span>
	 * <span class="en">The minimal value for a sum in one row.</span> */
	public int sizeSumRow;
	/** <span class="de">Abweichung zur berechneten Summe. (Zeile)</span>
	 * <span class="en">Maximum derivation of a sum in one row.</span> */
	public double deviationRow;
	//Abweichung bei ZeilenSpaltenEliminierung
	/** <span class="de">Abweichung bei RowColElimination. (Spalte)</span>
	 * <span class="en">The derivation for the RowColElimination in the cols.</span> */
	public int rceDeviationCol;
	/** <span class="de">Abweichung bei RowColElimination. (Zeile)</span>
	 * <span class="en">The derivation for the RowColElimination in the Rows.</span> */
	public int rceDeviationRow;
	//Output als Xls Funktioniert nicht wenn Tabelle zu groß
	/** <span class="de">Outputname bei XLS Ausgabe. Ausgabe wirft Fehler falls Relation zu gross.</span>
	 * <span class="en">The output name of the xls document. Throws Exception if document is to big.</span> */
	public int outputNameXls;
	
	/** <span class="de">Gibt an, wie der Excel Ausgabename aussieht.</span>
	 * <span class="en">Defines the name of the excel output.</span> */
	public int bevorOutputNameXls;
	
	/** <span class="de">Gibt an ob XLS Output erzeugt wird.</span>
	 * <span class="en">Show if xls output is created.</span> */
	public boolean useOutputNameXls;
	//Ausgabe als Frameset mit neuer und alter Tabelle
	/** <span class="de">Gibt an ob bei HTML Input auch HTML output erzeugt wird.</span> 
	 * <span class="en">Create html output for html input or not.</span> */
	public boolean useHtmlOutput;
	
	//Header Decide nur eins darf und muss gelten
	/** <span class="de">Die Prozente der Header Algorithmen werden zur Headererkennung genutzt.</span>
	 * <span class="en">The percent values of the functions would be used for header recogition.</span> */
	private boolean headerPercent;
	/** <span class="de">Die Priorit&aumlt der Header Algorithmen werden zur Headererkennung genutzt.</span>
	 * <span class="en">The priority of the functions would be used for header recogition.</span> */
	private boolean headerPriority;
	/** <span class="de">Die Mehrheit eines Ergebnisses wird als Headergrenze erkannt.</span>
	 * <span class="en">The majority of the results would take for header recogition.</span> */
	private boolean headerMayority;
	
	/** <span class="de">Gibt den Prozentwert an über dem die Ergebnisse der Summenzeilen und -spalten Algorithmen liegen müssen.</span>
	 * <span class="en">The percent of the results from the sumrows and sumcols, must be higher as that value.</span> */
	public int percentSum;
	
	//Key für NGRAM Nutzung
	/** <span class="de">Verwendeter Applikations Key. F&uumlr Layoutlines mit NGRAM.</span>
	 * <span class="en">The used application key for layout lines with NGRAM.</span> */
	public String ngramKey= "";
		
	/**
	 * <span class="de">Legt Configurations Paramter an und setzt diese auf Standardwerte.</span>
	 * <span class="en">Constructor which sets the start configuration.</span>
	 */
	public Configuration() {
		fs = new FunctionSelection();
		d = new Database();
		changeToStart();
	}
	
	/**
	 * <span class="de">Zur&uumlck setzen auf Startwerte.
	 * 
	 * useHtmlOutput = false
	 * onlyOneTable = false
	 * numberOfRows = 20
	 * limitForNoContent = 1
	 * limitForContentCells = 3
	 * deviationCol = 0.5
	 * summandNumber = 2
	 * sizeSumCol = 10
	 * sizeSumRow = 5
	 * deviationRow = 0
	 * rceDeviationCol = 0
	 * rceDeviationRow = 0
	 * outputNameXls = "outputData"
	 * useOutputNameXls = false</span>
	 * <span class="en">Change to start Configuration.
	 * 
	 * useHtmlOutput = false
	 * onlyOneTable = false
	 * numberOfRows = 20
	 * limitForNoContent = 1
	 * limitForContentCells = 3
	 * deviationCol = 0.5
	 * summandNumber = 2
	 * sizeSumCol = 10
	 * sizeSumRow = 5
	 * deviationRow = 0
	 * rceDeviationCol = 0
	 * rceDeviationRow = 0
	 * outputNameXls = "outputData"
	 * useOutputNameXls = false</span>
	 */
	public void changeToStart () {
		percentSum = 50;
		headerPercent = false;
		headerPriority = true;
		headerMayority = false;
		bevorOutputNameXls = 0;
		useHtmlOutput = false;
		onlyOneTable = false;
		numberOfRows = 20;
		limitForNoContent = 1;
		limitForContentCells = 3;
		deviationCol = 0.5;
		summandNumber = 2;
		sizeSumCol = 10;
		sizeSumRow = 5;
		deviationRow = 0.5;
		rceDeviationCol = 0;
		rceDeviationRow = 0;
		outputNameXls = 0;
		useOutputNameXls = false;
	}

	/**
	 * <span class="de">Setzt den headerPercent Wert und deaktiviert die anderen beiden.</span>
	 * <span class="en">Set the headerPercent value and reset the others.</span>	 
	 */
	public void setHeaderPercent() {
		headerPercent = true;
		headerPriority = false;
		headerMayority = false;
	}

	/**
	 * <span class="de">Gibt den Wert der headerPercent Variablen.</span>
	 * <span class="en">Gives the value of headerPercent.</span>
	 * @return <span class="de">headerPercent value</span>
	 * <span class="en">headerPercent Wert</span>
	 */
	public boolean isHeaderPercent() {
		return headerPercent;
	}

	/**
	 * <span class="de">Setzt den headerPriority Wert und deaktiviert die anderen beiden.</span>
	 * <span class="en">Set the headerPriority value and reset the others.</span>	
	 */
	public void setHeaderPriority() {
		headerPercent = false;
		headerPriority = true;
		headerMayority = false;
	}

	/**
	 * <span class="de">Gibt den Wert der headerPriority Variablen.</span>
	 * <span class="en">Gives the value of headerPriority.</span>
	 * @return <span class="de">headerPriority value</span>
	 * <span class="en">headerPriority Wert</span>
	 */
	public boolean isHeaderPriority() {
		return headerPriority;
	}

	/**
	 * <span class="de">Setzt den headerMajority Wert und deaktiviert die anderen beiden.</span>
	 * <span class="en">Set the headerMajority value and reset the others.</span>	 
	 */
	public void setHeaderMayority() {
		headerPercent = false;
		headerPriority = false;
		headerMayority = true;
	}

	/**
	 * <span class="de">Gibt den Wert der headerMajority Variablen.</span>
	 * <span class="en">Gives the value of headerMajority.</span>
	 * @return <span class="de">headerMajority value</span>
	 * <span class="en">headerMajority Wert</span>
	 */
	public boolean isHeaderMayority() {
		return headerMayority;
	}
}
