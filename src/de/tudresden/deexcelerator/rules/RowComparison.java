package de.tudresden.deexcelerator.rules;

import com.wcohen.ss.Jaro;

import de.tudresden.deexcelerator.data.AnalyzeInformation;
import de.tudresden.deexcelerator.data.DocumentInformation;

/**
 * <span class="de">Vergleich von Zeilen.</span>
 * <span class="en">Compare rows.</span>
 * @author Christopher Werner
 *
 */
public class RowComparison {

	/**
	 * <span class="de">Vergleicht die letzte und die erste Zeile einer Tabelle miteinander.
	 * Falls diese gleich sind wird die letzte Zeile den Leeren Zeilen hinzugef&uumlgt.</span>
	 * <span class="en">If the first and the last row in one table are equal, the last row
	 * would be delete.</span>
	 * @param gd (<span class="de">Verwendete DocumentInformation</span>
     * <span class="en">the used document information</span>)
     * @param sd (<span class="de">AnalyseInformation</span>
     * <span class="en">analyze information</span>)
	 */
	public void action (DocumentInformation gd, AnalyzeInformation sd) {
		Jaro j = new Jaro();
		int count = 0;
		int counter = 0;
		//Mache für jede Spalte einen Zeilenvergleich
		for (int i = sd.colStart; i<sd.colEnd; i++) {
			boolean testwertEmpty = true;
			if (gd.getFc().emptyCell(i,sd.rowStart) && gd.getFc().emptyCell(i,sd.rowEnd-1)) {
				testwertEmpty = false;
				counter++;
			} if (testwertEmpty && (gd.getFc().emptyCell(i,sd.rowStart) || gd.getFc().emptyCell(i,sd.rowEnd-1)))
				count++;
			else if (testwertEmpty && j.score(gd.getFc().contentCell(i,sd.rowStart), gd.getFc().contentCell(i,sd.rowEnd-1)) < 0.9)
				count++;		
		} 
		//Falls genug Uebereinstimmung entferne letzte Zeile
		if (count<=1 && counter*2 < sd.colEnd - sd.colStart)
			sd.emptyRows.add(sd.rowEnd-1);
	}
}
