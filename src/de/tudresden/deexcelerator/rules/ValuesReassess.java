package de.tudresden.deexcelerator.rules;

import de.tudresden.deexcelerator.data.AnalyzeInformation;
import de.tudresden.deexcelerator.data.DocumentInformation;
import de.tudresden.deexcelerator.util.SplittedCells;

/**
 * <span class="de">Erkennen von Zusammegef&uumlgten Zellen.</span>
 * <span class="en">Find merge cells.</span>
 * @author Christopher Werner
 *
 */
public class ValuesReassess {

	/**
	 * <span class="de">Weiterschreiben von Werten in der ersten Spalte.
	 * In darunter liegende freie Bereiche.</span>
	 * <span class="en">Further write of values in the first col to empty areas.</span>
	 * @param gd (<span class="de">Verwendete DocumentInformation</span>
     * <span class="en">the used document information</span>)
     * @param sd (<span class="de">AnalyseInformation</span>
     * <span class="en">analyze information</span>)
	 */
	@SuppressWarnings("unused")
	public void action (DocumentInformation gd, AnalyzeInformation sd) {
		for (int i = 0; i < sd.colInfos.size(); i++) {
			if (sd.colInfos.get(i).getSpaltenNummer() == sd.colStart && sd.colInfos.get(i).getFormat() != 4)
				return;
			else
				break;
		}
		boolean gefunden = true;
		String content = "";
		int yrange=0, zeile=0;
		for (int y = sd.headerFinish; y<sd.rowEnd; y++)
			if (!sd.emptyRows.contains(y)) {
				if (gd.getFc().emptyCell(sd.colStart, y)) {
					//setze die Luecke mit rein
					yrange++;					
				} else if (gefunden) {
					yrange = 0;
					zeile = y;
					content = gd.getFc().contentCell(sd.colStart, y);
					gefunden = false;
				} else {
					if (yrange > 0) {
						//Suche in der Breite weiter nach bereichen
						bereichFortschreibenWerte(zeile, zeile+yrange, sd.colStart+1, gd, sd);
						SplittedCells sz = new SplittedCells(sd.colStart, zeile, content);
						sz.setXrange(0);
						sz.setYrange(yrange);
						sd.mergeCells.add(sz);
					}
					yrange = 0;
					zeile = y;
					content = gd.getFc().contentCell(sd.colStart, y);
					gefunden = false;
				}
			}
		if (yrange>0) {
			//Suche in der Breite weiter nach bereichen
			bereichFortschreibenWerte(zeile, zeile+yrange, sd.colStart+1, gd, sd);
			SplittedCells sz = new SplittedCells(sd.colStart, zeile, content);
			sz.setXrange(0);
			sz.setYrange(yrange);
			sd.mergeCells.add(sz);
		}
	}
	
	/**
	 * <span class="de">Macht das Rekursive Bereichs erkennen von Freibereichen.</span>
	 * <span class="en">Find recursive empty areas in other cols.</span>
	 * @param anfang (<span class="de">Bereichsanfang</span>
	 * <span class="en">area start</span>)
	 * @param ende (<span class="de">Bereichsende</span>
	 * <span class="en">area end</span>)
	 * @param spalte (<span class="de">Spalte in der gepr&uumlft wird</span>
	 * <span class="en">col in which you look</span>)
	 * @param gd (<span class="de">Verwendete DocumentInformation</span>
     * <span class="en">the used document information</span>)
     * @param sd (<span class="de">AnalyseInformation</span>
     * <span class="en">analyze information</span>)
	 */
	private void bereichFortschreibenWerte(int anfang, int ende, int spalte, DocumentInformation gd, AnalyzeInformation sd) {
		if (spalte >= sd.colEnd)
			return;
		if (sd.emptyCols.contains(spalte))
			bereichFortschreibenWerte(anfang, ende, spalte+1,gd ,sd);
		else {
			boolean gefunden = true;
			String content = "";
			int yrange=0, zeile=0;
			for (int y = anfang; y<ende+1; y++)
				if (!sd.emptyRows.contains(y)) {
					if (gd.getFc().emptyCell(spalte, y)) {
						//setze die Luecke mit rein
						yrange++;					
					} else if (gefunden) {
						yrange = 0;
						zeile = y;
						content = gd.getFc().contentCell(spalte, y);
						gefunden = false;
					} else {
						if (yrange>0) {
							bereichFortschreibenWerte(zeile, zeile+yrange, spalte+1,gd ,sd);
							SplittedCells sz = new SplittedCells(spalte, zeile, content);
							sz.setXrange(0);
							sz.setYrange(yrange);
							sd.mergeCells.add(sz);
						}
						yrange = 0;
						zeile = y;
						content = gd.getFc().contentCell(spalte, y);
						gefunden = false;
					}
				}
			if (yrange>0) {
				bereichFortschreibenWerte(zeile, zeile+yrange, spalte+1,gd ,sd);
				SplittedCells sz = new SplittedCells(spalte, zeile, content);
				sz.setXrange(0);
				sz.setYrange(yrange);
				sd.mergeCells.add(sz);
			}
		}				
	}
}
