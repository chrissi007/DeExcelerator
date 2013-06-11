package de.tudresden.deexcelerator.rules;

import java.util.HashMap;
import java.util.Map;

import com.wcohen.ss.Jaro;

import de.tudresden.deexcelerator.data.AnalyzeInformation;
import de.tudresden.deexcelerator.data.DocumentInformation;
import de.tudresden.deexcelerator.util.HeaderVisualInformation;
import de.tudresden.deexcelerator.util.RangeMatrix;

/**
 * <span class="de">Headerlinie durch Score von JARO.</span>
 * <span class="en">Headerline by different of string values.</span>
 * @author Christopher Werner
 *
 */
public class HeaderRecognizeDifferents {
	
	/**
	 * <span class="de">Pr&uumlft den Score von untereinander liegenden Scores.
	 * Bei gleichem Score Wertebereich.
	 * Erkennen der Headerlinie durch Anfang des Wertebereichs.</span>
	 * <span class="en">Get the headerline by searching lines with the same score 
	 * in the value area. If nothing is find return -1.</span>
	 * @param tabellenAnfang (<span class="de">Tabellenanfang ab dem Zeile pro Zeile gepr&uumlft wird</span>
	 * <span class="en">the start for proofing of headerline</span>)
	 * @param gd (<span class="de">Verwendete DocumentInformation</span>
     * <span class="en">the used document information</span>)
     * @param sd (<span class="de">AnalyseInformation</span>
     * <span class="en">analyze information</span>)
	 * @return <span class="de">Intwert der gefundenen Zeile</span>
	 * <span class="en">int value of the headerline</span>
	 */
	public Map<Integer, HeaderVisualInformation> action (int tabellenAnfang, int tabellenEnde, DocumentInformation gd, AnalyzeInformation sd) {
		Jaro j = new Jaro();
		Map<Integer, HeaderVisualInformation> map = new HashMap<Integer, HeaderVisualInformation>();
		//----------------------------------------------------------
		//ConfigurationsWert zur Verschnellerung des Algortihmus
		int anzahlZeilen = gd.getCp().numberOfRows;
		double scoreVergleichsWert = 0.8;
		int fensterBreite = 4;
		//----------------------------------------------------------
		if (tabellenAnfang + anzahlZeilen < tabellenEnde)
			tabellenEnde = tabellenAnfang + anzahlZeilen;
				
		//Suche 4 er Block mit gleichem Score davor liegt der HeaderAnfang
		//x fuer Spalte und y fuer Wert
		int headerEnde = tabellenEnde;
		int anzahlScores = 0, headerAnfang = headerEnde;
		for (int x=sd.colStart; x<sd.colEnd; x++) {
			if (!sd.emptyCols.contains(x))
				for (int y=tabellenAnfang; y<tabellenEnde-1; y++)
					if (!sd.emptyRows.contains(y))
					{
						
						double score;
						if (gd.getFc().emptyCell(x, y) || gd.getFc().emptyCell(x, y+1))
							score = 0;
						else
							score = j.score(gd.getFc().contentCell(x, y), gd.getFc().contentCell(x, y+1));
						
						if (score>scoreVergleichsWert) {
							if (anzahlScores == 0)
								headerEnde = y;
							anzahlScores++;
						}
						if (anzahlScores == fensterBreite) {
							if (headerEnde != tabellenAnfang && headerEnde<tabellenEnde)
							{
								if (headerEnde<headerAnfang)
									headerAnfang = headerEnde;
								//vi liste erweitern wenn schon drin dann erhöhe prozentwert
								//grundwert 50%; 50/colnumber + wenn schon drin
								if (map.containsKey(headerEnde)) {
									HeaderVisualInformation h = map.get(headerEnde);
									h.percent = h.percent + 50/sd.colNumber;
									h.ranges.add(new RangeMatrix(x, headerEnde, x, headerEnde+fensterBreite));
									map.put(headerEnde, h);
								} else {
									HeaderVisualInformation h = new HeaderVisualInformation();
									h.ranges.add(new RangeMatrix(x, headerEnde, x, headerEnde+fensterBreite));
									h.percent = 50;
									map.put(headerEnde, h);
								}
							}
							anzahlScores = 0;
							break;
						}						
					}
			headerEnde = tabellenEnde;
			anzahlScores = 0;
		}
		if (headerAnfang == tabellenEnde)
			return null;
		
		return map;
	}
}
