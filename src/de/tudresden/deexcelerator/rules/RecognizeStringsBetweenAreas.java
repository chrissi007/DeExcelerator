package de.tudresden.deexcelerator.rules;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.tudresden.deexcelerator.data.AnalyzeInformation;
import de.tudresden.deexcelerator.data.DocumentInformation;

/**
 * <span class="de">Sucht Metadaten.</span>
 * <span class="en">Search Meta data in the table.</span>
 * @author Christopher Werner
 *
 */
public class RecognizeStringsBetweenAreas {
	
	/**
	 * <span class="de">Sucht Metadaten &uumlber der Tabelle und unterhalb.
	 * Ausserdem in Leerenbereichen.
	 * Dabei werden nur Zeilen gesucht, wo maximal ein Inhalt drin steht.</span>
	 * <span class="en">Search meta data at the start and the end of tables and in
	 * empty areas if some where found.</span>
	 * @param gd (<span class="de">Verwendete DocumentInformation</span>
     * <span class="en">the used document information</span>)
     * @param sd (<span class="de">AnalyseInformation</span>
     * <span class="en">analyze information</span>)
	 */
	public void action (DocumentInformation gd, AnalyzeInformation sd) {
		List <Integer> arbeiten = new ArrayList<Integer>();	
		
		for (int i = 0; i<sd.headerStarts.size(); i++)
			arbeiten.add(sd.headerStarts.get(i)-1);
		
		arbeiten.addAll(sd.emptyAreasStarts);
		
		if (!arbeiten.contains(sd.rowEnd-1))
			arbeiten.add(sd.rowEnd-1);
				
		int y= sd.rowStart;
		//von oben solange durchlaufen bis einmalnicht false wird
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
		//von unten solange durchlaufen bis einmalnicht false wird
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
		
		List <Integer> arbeitenNow = new ArrayList<Integer>();	
		for (int i = 0; i<arbeiten.size(); i++) {
			if (arbeiten.get(i) > sd.headerFinish && arbeiten.get(i) < y)
				arbeitenNow.add(arbeiten.get(i));
		}

		Collections.sort(arbeitenNow);
		
		int wert1=0, wert2=0;
		//in allen zwischen bereichen Werte auslesen
		for (int i = 0; i<arbeitenNow.size(); i++) {
			if (wert1 == 0)
				wert1 = arbeiten.get(i);
			else {
				wert2 = arbeiten.get(i);
				stringsauslesen(wert1, wert2, gd, sd);
				wert1 = 0;
			}
		}		
	}
	
	private void stringsauslesen (int wert1, int wert2, DocumentInformation gd, AnalyzeInformation sd) {
		for (int y=wert1; y<=wert2; y++) {
			if (!sd.emptyRows.contains(y)) {
				String wert = "";
				int test = 0;
				for (int x=sd.colStart; x<sd.colEnd; x++)
					if (!sd.emptyCols.contains(x))
						if (!gd.getFc().emptyCell(sd.colStart, y)) {
							wert = gd.getFc().contentCell(x, y);
							test++;
						}
				if (test == 1)
					sd.metaDataTable.add(wert);
				sd.emptyRows.add(y);
			}	
		}
	}
}
