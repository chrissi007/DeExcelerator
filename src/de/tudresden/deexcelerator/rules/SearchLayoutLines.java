package de.tudresden.deexcelerator.rules;

import de.tudresden.deexcelerator.data.AnalyzeInformation;
import de.tudresden.deexcelerator.data.DocumentInformation;
import de.tudresden.deexcelerator.util.SplittedCells;

/**
 * <span class="de">Suchen von Layoutlinien.</span>
 * <span class="en">Search layout lines.</span>
 * @author Christopher Werner
 *
 */
public class SearchLayoutLines {
	
	/**
	 * <span class="de">Suchen der Zeilen, wo nur ein Wert am Anfang steht.
	 * Wert mit den Zeilen dar&uumlber und darunter vergleichen.</span>
	 * <span class="en">Search the rows, where is only one value at the start.
	 * Compare that value with the one in line before and the line after.</span>
	 * @param gd (<span class="de">Verwendete DocumentInformation</span>
     * <span class="en">the used document information</span>)
     * @param sd (<span class="de">AnalyseInformation</span>
     * <span class="en">analyze information</span>)
	 */
	public void action (DocumentInformation gd, AnalyzeInformation sd) {
		for (int y=sd.headerFinish; y<sd.rowEnd-1; y++) {
			if (!sd.emptyRows.contains(y)) { 
				int zelleNotEmpty = 0, mx = sd.colStart;
				for (int x=sd.colStart; x<sd.colEnd; x++) {
					if (!sd.emptyCols.contains(x))
						if (!gd.getFc().emptyCell(x, y)) {
							zelleNotEmpty++;
							mx = x;
						}
				}
				//kann Zeile in Frage kommen?
				if (zelleNotEmpty == 1){
					//zeilen kommt in Frage => vergleich Strings
					String unten;
					String zelleContent = gd.getFc().contentCell(mx, y);
					String zelleUntenContent = gd.getFc().contentCell(mx, y+1);
					unten = zelleContent + " " + zelleUntenContent;
					//falls NGRAM genutzt wird kann dieser Teil einkommentiert werden
					//pruefen der oberen und unteren Zellen auf zugehoerigkeit
					/*String oben; 
					String zelleObenContent = gd.getFc().contentCell(mx, y-1);
					oben = zelleObenContent + " " + zelleContent;
					BingLookup bl = new BingLookup(gd.getCp().ngramKey);
					double wert1 = bl.giveProbability(oben);
					double wert2 = bl.giveProbability(unten);
					if (wert1 > wert2 && wert1>-10) {
						SplittedCells sz = new SplittedCells(mx, y, oben);
						sz.setXrange(0);
						sz.setYrange(0);
						sd.vi.mergeCells.add(sz);
						sd.mergeCells.add(sz);
					} else if (wert1 < wert2 && wert2>-10) {
						SplittedCells sz = new SplittedCells(mx, y, unten);
						sz.setContent(unten);
						sz.setXrange(0);
						sz.setYrange(0);
						sd.vi.mergeCells.add(sz);
						sd.mergeCells.add(sz);
					}*/
					//auskommentieren falls oberer Teil genutzt werden soll
					SplittedCells sz = new SplittedCells(mx, y+1, unten);
					sz.setXrange(0);
					sz.setYrange(0);
					sd.vi.mergeCells.add(sz);
					//sd.mergeCells.add(sz);
					
					sd.metaDataTable.add(zelleContent);
					
					sd.emptyRows.add(y);
				}					
			}
		}
	}
}
