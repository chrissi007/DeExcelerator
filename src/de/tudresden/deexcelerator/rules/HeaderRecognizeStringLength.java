package de.tudresden.deexcelerator.rules;

import java.util.HashMap;
import java.util.Map;

import de.tudresden.deexcelerator.data.AnalyzeInformation;
import de.tudresden.deexcelerator.data.DocumentInformation;
import de.tudresden.deexcelerator.util.HeaderVisualInformation;
import de.tudresden.deexcelerator.util.RangeMatrix;

/**
 * <span class="de">Headerlinie durch Stringl&aumlnge.</span>
 * <span class="en">Headerline by string length.</span>
 * @author Christopher Werner
 *
 */
public class HeaderRecognizeStringLength {
	
	/**
	 * <span class="de">Erkennen der Headerlinie durch Stringl&aumlnge.
	 * Suche gleicher Stringl&aumlngen und das als Wertebereich festlegen.
	 * Ansonsten Zeilenende zur&uumlck geben.</span>
	 * <span class="en">Get the headerline by searching rows with the same string length in the
	 * value area. If nothing is find return -1.</span>
	 * @param tabellenAnfang (Tabellenanfang ab dem Zeile pro Zeile gepr&uumlft wird</span>
	 * <span class="en">the start for proofing of headerline</span>)
	 * @param gd (<span class="de">Verwendete DocumentInformation</span>
     * <span class="en">the used document information</span>)
     * @param sd (<span class="de">AnalyseInformation</span>
     * <span class="en">analyze information</span>)
	 * @return <span class="de">Intwert der gefundenen Zeile</span>
	 * <span class="en">int value of the headerline</span>
	 */
	public Map<Integer, HeaderVisualInformation> action (int tabellenAnfang, int tabellenEnde, DocumentInformation gd, AnalyzeInformation sd) {		
		Map<Integer, HeaderVisualInformation> map = new HashMap<Integer, HeaderVisualInformation>();
		//----------------------------------------------------------
		//ConfigurationsWert zur Verschnellerung des Algortihmus
		int anzahlZeilen = gd.getCp().numberOfRows;
		int differenceVergleichsWert = 0;
		int fensterBreite = 4;
		//----------------------------------------------------------
		if (tabellenAnfang + anzahlZeilen < tabellenEnde)
			tabellenEnde = tabellenAnfang + anzahlZeilen;
				
		//Suche 4 er Block mit gleichem Laengenunterschied davor liegt der HeaderAnfang
		//x fuer Spalte und y fuer Wert
		int headerEnde = tabellenEnde;
		int anzahlDifference = 0, headerAnfang = headerEnde;
		for (int x=sd.colStart; x<sd.colEnd; x++) {
			if (!sd.emptyCols.contains(x))
				for (int y=tabellenAnfang; y<tabellenEnde-1; y++)
					if (!sd.emptyRows.contains(y))
					{
						if (gd.getFc().contentCell(x, y).trim().equals("") || gd.getFc().contentCell(x, y+1).trim().equals(""))
							continue;
						int difference = gd.getFc().contentCell(x, y).length()-gd.getFc().contentCell(x, y+1).length();
						if (difference == differenceVergleichsWert) {
							if (anzahlDifference == 0)
								headerEnde = y;
							anzahlDifference++;
						} else
							anzahlDifference = 0;
						if (anzahlDifference >= fensterBreite) {
							if (headerEnde != tabellenAnfang && headerEnde<tabellenEnde) {
								if (headerEnde<headerAnfang)
									headerAnfang = headerEnde;
								//vi liste erweitern wenn schon drin dann erhöhe prozentwert
								//grundwert 40%; 40/colnumber + wenn schon drin
								if (map.containsKey(headerEnde)) {
									HeaderVisualInformation h = map.get(headerEnde);
									h.percent = h.percent + 60/sd.colNumber;
									h.ranges.add(new RangeMatrix(x, headerEnde, x, headerEnde+fensterBreite));
									map.put(headerEnde, h);
								} else {
									HeaderVisualInformation h = new HeaderVisualInformation();
									h.ranges.add(new RangeMatrix(x, headerEnde, x, headerEnde+fensterBreite));
									h.percent = 40;
									map.put(headerEnde, h);
								}
							}
							anzahlDifference = 0;
							break;
						}						
					}
			headerEnde = tabellenEnde;
			anzahlDifference = 0;
		}
		if (headerAnfang == tabellenEnde)
			return null;
		
		return map;
	}
}
