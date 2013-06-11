package de.tudresden.deexcelerator.rules;

import java.util.HashMap;
import java.util.Map;

import de.tudresden.deexcelerator.data.AnalyzeInformation;
import de.tudresden.deexcelerator.data.DocumentInformation;
import de.tudresden.deexcelerator.util.HeaderVisualInformation;
import de.tudresden.deexcelerator.util.RangeMatrix;

/**
 * <span class="de">Header durch Numbersequence.</span>
 * <span class="en">Headerline by number sequence.</span>
 * @author Christopher Werner
 *
 */
public class HeaderRecognizeSequence {

	/**
	 * <span class="de">Headerlinie durch erste vorkommende Zahlzelle erkennen.
	 * Zahlenzellen noch nach Anzahl in Zeile bestimmen um Zahl im Header zu erkennen.
	 * Wenn keine Zahl vorhanden dann Zeilenende zur&uumlck geben.</span>
	 * <span class="en">Get the headerline by searching the sequence of numbers in the
	 * value area. If nothing is find return -1.</span>
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
		Map<Integer, HeaderVisualInformation> map = new HashMap<Integer, HeaderVisualInformation>();
		//----------------------------------------------------------
		//ConfigurationsWert zur Verschnellerung des Algortihmus
		int anzahlZeilen = gd.getCp().numberOfRows;
		//----------------------------------------------------------
		if (tabellenAnfang + anzahlZeilen < tabellenEnde)
			tabellenEnde = tabellenAnfang + anzahlZeilen;
		
		int vorherZelle = 0, jetztZelle = 0;
		
		//Loesung nimm den ersten Zeilenwert wo der Haupttype vorkommt
		int wert = 0, wert2 = 0;
		int sequenceStart = 0;
		int ergebnis = tabellenEnde;
		for (int x=sd.colStart; x<sd.colEnd; x++)
		{
			int sequenceTrueCounter = 0;
			if (!sd.emptyCols.contains(x))
				for (int y=tabellenAnfang; y<tabellenEnde; y++)
				{
					if (!sd.emptyRows.contains(y))
					{
						if (gd.getFc().numberCell(x, y))
							jetztZelle = 1;
						else
							jetztZelle = 2;
						if (jetztZelle == 1 && vorherZelle == 2) {							
							//merke die Gefundene Zeile
							Integer merke = gd.getFc().getIntegerValue(x, y);
							if (merke == null)
								jetztZelle = 2;
							else
								wert = merke.intValue();
							sequenceStart = y;
						} else if (jetztZelle == 1 && vorherZelle == 1){
							//merke die Gefundene Zeile
							Integer merke = gd.getFc().getIntegerValue(x, y);
							if (merke == null)
								jetztZelle = 2;
							else
								wert2 = merke.intValue();
							if (wert+1 == wert2) {
								sequenceTrueCounter++;
							} else {
								if (sequenceTrueCounter > 5) {
									if (sequenceStart < ergebnis)
										ergebnis = sequenceStart;
									//anzahlzeilen mit einbeziehen
									//vi liste erweitern wenn schon drin dann erhöhe prozentwert
									//grundwert 50%; 50/colnumber + wenn schon drin
									if (map.containsKey(sequenceStart)) {
										HeaderVisualInformation h = map.get(sequenceStart);
										h.percent = h.percent + 10/anzahlZeilen*sequenceTrueCounter;
										h.ranges.add(new RangeMatrix(x, sequenceStart, x, sequenceStart+sequenceTrueCounter));
										map.put(sequenceStart, h);
									} else {
										HeaderVisualInformation h = new HeaderVisualInformation();
										h.percent = 90;
										h.ranges.add(new RangeMatrix(x, sequenceStart, x, sequenceStart+sequenceTrueCounter));
										map.put(sequenceStart, h);
									}
								}
								if (sequenceTrueCounter > 0)
									break;
							}
							wert = wert2;
						}							
						vorherZelle = jetztZelle;						
					}
				}
			wert = 0;
			wert2 = 0;
			sequenceStart = 0;
			vorherZelle = 0; 
			jetztZelle = 0;
		}
		if (ergebnis == tabellenEnde)
			return null;
		
		return map;
	}
}
