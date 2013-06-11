package de.tudresden.deexcelerator.rules;

import de.tudresden.deexcelerator.data.AnalyzeInformation;
import de.tudresden.deexcelerator.data.DocumentInformation;

/**
 * <span class="de">Entfernt leere Spalten.</span>
 * <span class="en">Delete empty cols.</span>
 * @author Christopher Werner
 *
 */
public class ColsEliminationInData {

	/**
	 * <span class="de">Pr&uumlft im Wertebereich, ob da Spalten keine Inhalte besitzen.</span>
	 * <span class="en">Proofs under the headerline if there are cols with no content.</span>
	 * @param gd (<span class="de">Verwendete DocumentInformation</span>
     * <span class="en">the used document information</span>)
     * @param sd (<span class="de">AnalyseInformation</span>
     * <span class="en">analyze information</span>)
	 */
	public void action (DocumentInformation gd, AnalyzeInformation sd) {
		//Konfirgurationsparameter
		int abweichungZeile = gd.getCp().rceDeviationRow;
		//-----------------------------------------------
		//Spaltenweise Durchgehen
		for (int x=sd.colStart; x<sd.colEnd; x++)
		{
			if (!sd.emptyCols.contains(x)) {
				int test = 0;
				for (int y=sd.headerFinish; y<sd.rowEnd; y++)
					if (!gd.getFc().emptyCell(x, y))
						test++;
				if (test <= abweichungZeile) {	
					sd.emptyCols.add(x);
				}
			}
		}
	}
}
