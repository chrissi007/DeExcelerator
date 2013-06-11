package de.tudresden.deexcelerator.rules;

import de.tudresden.deexcelerator.data.AnalyzeInformation;
import de.tudresden.deexcelerator.data.DocumentInformation;

/**
 * <span class="de">Sucht Metadaten.</span>
 * <span class="en">Search meta data.</span>
 * @author Christopher Werner
 *
 */
public class RecognizeStrings {

	/**
	 * <span class="de">Sucht nach Zeilen in denen nur eine Contentwert vorkommt.
	 * Sucht von oben und unten.
	 * H&oumlrt auf falls keine mehr gefunden.
	 * F&uumlgt die Inhalte den Metadaten hinzu.</span>
	 * <span class="en">Search lines where is only one content cell and
	 * at the content to the meta data information.</span>
	 * @param gd (<span class="de">Verwendete DocumentInformation</span>
     * <span class="en">the used document information</span>)
     * @param sd (<span class="de">AnalyseInformation</span>
     * <span class="en">analyze information</span>)
	 */
	public void action (DocumentInformation gd, AnalyzeInformation sd) {
		
		int y= sd.rowStart;
		//von oben solange durchlaufen bis Zeile mit mehr als einem vollen Feld
		while (y<sd.rowEnd) {
			if (!sd.emptyRows.contains(y)) {
				String wert = "";
				int test = 0;
				for (int x=sd.colStart; x<sd.colEnd; x++) {
					if (!sd.emptyCols.contains(x) && !gd.getFc().emptyCell(x, y)) {
						test++;
						wert = gd.getFc().contentCell(x, y);
					} 
				}
				if (test <= 1) {
					if (test > 0)
						sd.metaDataTable.add(wert);					
					sd.emptyRows.add(y);
				} else 
					break;
			}
			y++;
		}
		
		int merke = y;
		if (sd.headerFinish < y)
			sd.headerFinish = y;
		
		y= sd.rowEnd - 1;
		//von unten solange durchlaufen bis Zeile mit mehr als einem vollen Feld
		while (y>=sd.rowStart) {
			if (!sd.emptyRows.contains(y)) {
				String wert = "";
				int test = 0;
				for (int x=sd.colStart; x<sd.colEnd; x++) {
					if (!sd.emptyCols.contains(x) && !gd.getFc().emptyCell(x, y)) {
						test++;
						wert = gd.getFc().contentCell(x, y);
					} 
				}
				if (test <= 1) {
					if (test > 0)
						sd.metaDataTable.add(wert);					
					sd.emptyRows.add(y);
				} else 
					break;
			}
			y--;
		}
		
		if (y-1 <= sd.headerFinish) {
			sd.headerFinish = merke;
		}
	}
}
