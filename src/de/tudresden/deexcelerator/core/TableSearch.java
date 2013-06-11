package de.tudresden.deexcelerator.core;

import java.util.ArrayList;
import java.util.List;


import de.tudresden.deexcelerator.data.AnalyzeInformation;
import de.tudresden.deexcelerator.data.DocumentInformation;
import de.tudresden.deexcelerator.output.OutputObject;
import de.tudresden.deexcelerator.rules.EmptyAreasFinder;
import de.tudresden.deexcelerator.rules.RowsColsElimination;
import de.tudresden.deexcelerator.rules.TableOpen;
import de.tudresden.deexcelerator.rules.TableProof;

/**
 * <span class="de">Tabellensucher Managementklasse zum Aufrufen der Regeln und einf&uumlhren in die Analyse.</span>
 * <span class="en">Search all relations in the table and open the extraction for all.</span>
 * @author Christopher Werner
 *
 */
public class TableSearch {

	/** <span class="de">Zu nutzende Dokumentinformationen.</span>
	 * <span class="en">Using document information.</span> */
	private DocumentInformation gd;
	/** <span class="de">Zu nutzende analyse Informatione.</span>
	 * <span class="en">Using analyse information.</span> */
	private AnalyzeInformation sd;
	
	/**
	 * <span class="de">Legt die Spezifischen- und Grunddaten fest.</span>
	 * <span class="en">Constructor, which set the attributes.</span>
	 * @param gd (<span class="de">DocumentInformation Objekt</span>
	 * <span class="en">DocumentInformation object</span>)
	 * @param sd (<span class="de">AnalyseInformation Objekt</span>
	 * <span class="en">AnalyseInformation object</span>)
	 */
	public TableSearch (DocumentInformation gd, AnalyzeInformation sd) {
		this.gd = gd;
		this.sd = sd;
	}
	
	/**
	 * <span class="de">F&uumlhrt die sequentielle Ausf&uumlhrung der Regeln durch.</span>
	 * <span class="en">Makes the sequential execution of the rules.</span>
	 * @return <span class="de">Liste von OutputObjecten</span>
	 * <span class="en">List of OutputObjects</span>
	 */
	public List<OutputObject> ruleSequence () {
		List<OutputObject> outs = new ArrayList<OutputObject>();
		//Eliminieren von leeren Spalten am Anfang und Ende und von Leeren Zeilen am Anfang und Ende
		RowsColsElimination zse = new RowsColsElimination();
		zse.action(gd, sd);
		
		//prüfe ob nur ein table drin ist wenn ja umgehe das Tabellen suchen
		if (gd.getCp().onlyOneTable) {
			if (sd.colNumber > 1 && sd.rowNumber > 1) {
				//nur eine Tabelle also kann sie einfach weitergereicht werden
				TableAnalysis ta = new TableAnalysis(gd,sd);
				OutputObject oo = ta.ruleSequence();
				outs.add(oo);
				return outs;
			} else {
				return null;
			}
		} else {
			//System.out.println("Cols: " + sd.colNumber + " Rows: " + sd.rowNumber);
			if (sd.colNumber > 1 && sd.rowNumber > 1) {
				
				//findet Tabellen anfaenge header Anfaenge und leerBereichsanfaenge
				EmptyAreasFinder lbf = new EmptyAreasFinder();
				lbf.action(gd, sd);
				
				//finden von identischen Zeilen
				TableProof tp = new TableProof();
				tp.action(gd, sd);
				
				//aufrufen der neuen Tabelle
				TableOpen to = new TableOpen();
				outs = to.action(gd, sd);
				
				return outs;
			} else {
				return null;
			}
		}
	}
}
