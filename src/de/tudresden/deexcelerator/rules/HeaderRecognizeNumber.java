package de.tudresden.deexcelerator.rules;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import de.tudresden.deexcelerator.data.AnalyzeInformation;
import de.tudresden.deexcelerator.data.DocumentInformation;
import de.tudresden.deexcelerator.util.HeaderVisualInformation;
import de.tudresden.deexcelerator.util.RangeMatrix;

/**
 * <span class="de">Headerlinie durch NUMBER.</span>
 * <span class="en">Headerline by number values.</span>
 * @author Christopher Werner
 *
 */
public class HeaderRecognizeNumber {
	
	/**
	 * <span class="de">Headerlinie durch erste vorkommende Zahlzelle erkennen.
	 * Zahlenzellen noch nach Anzahl in Zeile bestimmen um Zahl im Header zu erkennen.
	 * Wenn keine Zahl vorhanden dann Zeilenende zur&uumlck geben.</span>
	 * <span class="en">Get the headerline by searching the first number value in the
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
		int anzahlGefundene = 0;
		//----------------------------------------------------------
		if (tabellenAnfang + anzahlZeilen < tabellenEnde)
			tabellenEnde = tabellenAnfang + anzahlZeilen;
		
		int vorherZelle = 0, jetztZelle = 0;
		
		//Loesung nimm den ersten Zeilenwert wo der Haupttype vorkommt
		Map<Integer,Integer> werte = new HashMap<Integer,Integer>();
		int gefunden = tabellenEnde;
		ModifieHeader mh = new ModifieHeader();
		for (int x=sd.colStart; x<sd.colEnd; x++)
		{
			boolean emptyTest = false;
			boolean find = false;
			if (!sd.emptyCols.contains(x))
				for (int y=tabellenAnfang; y<tabellenEnde; y++)
				{
					if (!sd.emptyRows.contains(y))
					{
						if (gd.getFc().numberCell(x, y))
							jetztZelle = 1;
						else if (gd.getFc().emptyCell(x, y))
							jetztZelle = 0;
						else
							jetztZelle = 2;
						if (jetztZelle == 1 && vorherZelle == 2) {							
							//merke die Gefundene Zeile
							if (gefunden > y)
								gefunden = y;
							find = true;
						} else if (jetztZelle == 1 && vorherZelle == 0){
							//merke die Gefundene Zeile
							if (gefunden > y) {
								gefunden = y;
							}
							emptyTest = true;
							find = true;
						}		
						if (find) {
							gefunden = mh.action(gefunden, gd, sd);
							if (gefunden <= tabellenAnfang)
								break;
							if (map.containsKey(gefunden)) {
								HeaderVisualInformation h = map.get(gefunden);
								h.ranges.add(new RangeMatrix(x, gefunden, x, gefunden));
								map.put(gefunden, h);
							} else {
								HeaderVisualInformation h = new HeaderVisualInformation();
								h.ranges.add(new RangeMatrix(x, gefunden, x, gefunden));
								map.put(gefunden, h);
							}
							break;
						}
						vorherZelle = jetztZelle;						
					}
				}
			if (gefunden != tabellenEnde && gefunden > tabellenAnfang) {
				if (werte.containsKey(gefunden)) {
					if (!emptyTest)
						werte.put(gefunden, werte.get(gefunden)+1);
				} else {
					werte.put(gefunden, 1);
				}
			}
			gefunden = tabellenEnde;
			vorherZelle = 0; 
			jetztZelle = 0;
		}
		int big = tabellenEnde;
		Iterator<Integer> iter = werte.keySet().iterator();
		while (iter.hasNext()) {
			Integer key = iter.next();
			if (werte.get(key) >= anzahlGefundene && key < big) {
				gefunden = key;
				big = key;
				anzahlGefundene = werte.get(key);
			}
			map.get(key).percent = 50 + 50/sd.colNumber*anzahlGefundene;
		}

		if (gefunden == tabellenEnde)
			return null;
		
		return map;
	}
}
