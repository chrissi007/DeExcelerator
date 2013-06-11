package de.tudresden.deexcelerator.rules;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.wcohen.ss.Jaro;

import de.tudresden.deexcelerator.data.AnalyzeInformation;
import de.tudresden.deexcelerator.data.DocumentInformation;

/**
 * <span class="de">Pr&uumlft auf Tabellengleichheit.</span>
 * <span class="en">Proof if tables are equal.</span>
 * @author Christopher Werner
 *
 */
public class TableProof {
	
	/**
	 * <span class="de">Wenn mehrere Tabellen in einem Dokument vorhanden.
	 * Dann Pr&uumlfe ob die Tabellen gleiche Anf&aumlnge besitzen.
	 * Wenn ja f&uumlhr nur eine Tabelle weiter.
	 * Wenn nicht dann 2 oder mehr.</span>
	 * <span class="en">Proof if there are more than one table in a document.
	 * If they have the same starts you only see it as on table.</span>
	 * @param gd (<span class="de">Verwendete DocumentInformation</span>
     * <span class="en">the used document information</span>)
     * @param sd (<span class="de">AnalyseInformation</span>
     * <span class="en">analyze information</span>)
	 */
	public void action (DocumentInformation gd, AnalyzeInformation sd) {
		
		List<Integer> testListe = new ArrayList<Integer>();
		List<Integer> zwischenListe = new ArrayList<Integer>();
		zwischenListe.addAll(sd.headerStarts);
		zwischenListe.addAll(sd.emptyAreasStarts);
		Collections.sort(zwischenListe);
				
		for (int i=0; i<zwischenListe.size()/2; i++) {
			if (zwischenListe.size() >= 2*i+1) {
				int one = zwischenListe.get(2*i);
				int two = zwischenListe.get(2*i+1);
				for (int j = one; j < two; j++)
					testListe.add(j);
			}			
		}
		
		Collections.sort(testListe);
		
		for (int i=0; i<testListe.size(); i++)
			for (int j=i+1; j<testListe.size(); j++)
			{
				boolean testWert = zeilenVergleich(testListe.get(i),testListe.get(j),gd,sd);
				if (testWert) {
					sd.emptyRows.add(testListe.get(j));
				}
			}
		
		sd.relationStarts.removeAll(sd.emptyRows);
	}
	
	/**
	 * <span class="de">Vergleiche 2 Zeilen miteinander.</span>
	 * <span class="en">Compare two rows.</span>
	 * @param zeilei (<span class="de">Zeile i</span>
	 * <span class="en">row i</span>)
	 * @param zeilej (<span class="de">Zeile j</span>
	 * <span class="en">row j</span>)
	 * @param gd (<span class="de">Verwendete DocumentInformation</span>
     * <span class="en">the used document information</span>)
     * @param sd (<span class="de">AnalyseInformation</span>
     * <span class="en">analyze information</span>)
	 * @return <span class="de">Wahrheitswert</span>
	 * <span class="en">boolean value</span>
	 */
	private boolean zeilenVergleich (int zeilei, int zeilej, DocumentInformation gd, AnalyzeInformation sd) {
		//Pruefe ob 2 Zeilen gleich Sind
		//muss Score von 0.9 oder höher aufweisen
		for (int i = sd.colStart; i<sd.colEnd; i++) {
			Jaro j = new Jaro();
			boolean testwertEmpty = true;
			if (gd.getFc().emptyCell(i,zeilei) && gd.getFc().emptyCell(i,zeilej))
				testwertEmpty = false;
			if (testwertEmpty && (gd.getFc().emptyCell(i,zeilei) || gd.getFc().emptyCell(i,zeilej)))
				return false;
			if (testwertEmpty && j.score(gd.getFc().contentCell(i,zeilei), gd.getFc().contentCell(i,zeilej)) < 0.9)
				return false;			
		} 
		return true;
	}
}
