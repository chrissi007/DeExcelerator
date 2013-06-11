package de.tudresden.deexcelerator.rules;

import java.util.HashMap;
import java.util.Map;

import de.tudresden.deexcelerator.data.AnalyzeInformation;
import de.tudresden.deexcelerator.data.DocumentInformation;
import de.tudresden.deexcelerator.util.HeaderVisualInformation;
import de.tudresden.deexcelerator.util.RangeMatrix;

/**
 * <span class="de">Headerlinie durch DATE.</span>
 * <span class="en">Headerline by date.</span>
 * @author Christopher Werner
 *
 */
public class HeaderRecognizeDate {
	
	/**
	 * <span class="de">Erkennt die Headerlinie durch Datezelle.
	 * Wenn kein Farbunterschied erkannt wurde, dann -1 zurück geben.
	 * Sucht die erste auftretende Datezelle.</span>
	 * <span class="en">Get the headerline by searching the first date value in the
	 * value area. If nothing is find return -1.</span>
	 * @param tabellenAnfang (<span class="de">Tabellenanfang, ab dem Zeile pro Zeile gepr&uumlft wird</span>
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
		int headerEnde = tabellenEnde;
		boolean find = false;
		for (int x=sd.colStart; x<sd.colEnd; x++)
		{
			if (!sd.emptyCols.contains(x))
				for (int y=tabellenAnfang; y<tabellenEnde; y++)
				{
					if (!sd.emptyRows.contains(y))
					{
						if (gd.getFc().dateCell(x, y))
							jetztZelle = 1;
						else
							jetztZelle = 2;
						if (jetztZelle != vorherZelle && jetztZelle == 1)
						{
							//merke die Gefundene Zeile
							if (headerEnde > y)
								headerEnde = y;
							//vi liste erweitern wenn schon drin dann erhöhe prozentwert
							if (find)
								if (map.containsKey(y)) {
									HeaderVisualInformation h = map.get(y);
									h.percent = h.percent - 10;
									h.ranges.add(new RangeMatrix(x, y, x, y));
									map.put(y, h);
								} else {
									HeaderVisualInformation h = new HeaderVisualInformation();
									h.percent = 70;
									h.ranges.add(new RangeMatrix(x, y, x, y));
									map.put(y, h);
								}
							else
								if (map.containsKey(y)) {
									HeaderVisualInformation h = map.get(y);
									h.percent = h.percent + 20/sd.colNumber;
									h.ranges.add(new RangeMatrix(x, y, x, y));
									map.put(y, h);
								} else {
									HeaderVisualInformation h = new HeaderVisualInformation();
									h.percent = 80;
									h.ranges.add(new RangeMatrix(x, y, x, y));
									map.put(y, h);
								}
							find = true;
						}
						vorherZelle = jetztZelle;						
					}
				}
			find = false;
		}
		if (headerEnde == tabellenEnde)
			return null;
		
		return map;
	}
}
