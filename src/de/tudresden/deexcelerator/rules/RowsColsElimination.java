package de.tudresden.deexcelerator.rules;

import de.tudresden.deexcelerator.data.AnalyzeInformation;
import de.tudresden.deexcelerator.data.DocumentInformation;

/**
 * <span class="de">Entfernt leere Zeilen und Spalten.</span>
 * <span class="en">Delete empty rows and cols.</span>
 * @author Christopher Werner
 *
 */
public class RowsColsElimination {

	/**
	 * <span class="de">Entfernt Zeilen und Spalten in denen nach einer Abweichung fast kein Inhalt steht.</span>
	 * <span class="en">Delete rows and cols, which do not have any content.</span>
	 * @param gd (<span class="de">Verwendete DocumentInformation</span>
     * <span class="en">the used document information</span>)
     * @param sd (<span class="de">AnalyseInformation</span>
     * <span class="en">analyze information</span>)
	 */
	public void action (DocumentInformation gd, AnalyzeInformation sd) {
		//Konfirgurationsparameter
		int abweichungSpalte = gd.getCp().rceDeviationCol;
		int abweichungZeile = gd.getCp().rceDeviationRow;
		//-----------------------------------------------

		//Spaltenweise Durchgehen
		//Falls noetig Spaltenende nach vorne verschieben
		boolean testVariableSpaltenZeilen = true;
		for (int x=sd.colEnd-1; x>=sd.colStart; x--)
		{
			int test = 0;
			for (int y=sd.rowStart; y<sd.rowEnd; y++)	{
				if (!gd.getFc().emptyCell(x, y))
					test++;
			}
			if (test <= abweichungSpalte) {	
				if (testVariableSpaltenZeilen)
					sd.colEnd--;
				else
					sd.emptyCols.add(x);
			} 
			else 
				testVariableSpaltenZeilen = false;
		}
		testVariableSpaltenZeilen = true;
		//Zeilenweise Durchgehen
		//Falls noetig Zeilenende nach oben verschieben
		for (int y=sd.rowEnd-1; y>=sd.rowStart; y--) {
			int test = 0; 
			for (int x=sd.colStart; x<sd.colEnd; x++) {
				if (!gd.getFc().emptyCell(x, y))
					test++;
			}
			if (test <= abweichungZeile) {
				if (testVariableSpaltenZeilen)
					sd.rowEnd--;
				else
					sd.emptyRows.add(y);
			}
			else 
				testVariableSpaltenZeilen = false;				
		}
		//Schauen ob Zeilenanfang nach unten verschoeben werden muss
		boolean checker = true;
		while (checker) {
			if (sd.emptyRows.contains(sd.rowStart))
				sd.rowStart++;
			else
				checker = false;
		}
		//Schauen ob Spalten Anfang nach Rechts verschoben werden muss
		checker = true;
		while (checker) {
			if (sd.emptyCols.contains(sd.colStart))
				sd.colStart++;
			else
				checker = false;
		}
		sd.colNumber = sd.colEnd - sd.colStart;
		sd.rowNumber = sd.rowEnd - sd.rowStart;
	}
}
