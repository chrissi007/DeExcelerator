package de.tudresden.deexcelerator.rules;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import de.tudresden.deexcelerator.data.AnalyzeInformation;
import de.tudresden.deexcelerator.data.DocumentInformation;
import de.tudresden.deexcelerator.util.HeaderVisualInformation;
import de.tudresden.deexcelerator.util.RangeMatrix;

/**
 * <span class="de">Header Erkennung durch Hintergrundfarbe im Header.</span>
 * <span class="en">Headerline by background color.</span>
 * @author Christopher Werner
 *
 */
public class HeaderRecognizeBackgroundcolor {

	/**
	 * <span class="de">Headerlinie durch hervorstechen der Hintergrundfarbe des Headers.
	 * Wenn kein Farbunterschied erkannt wurde, dann -1 zurück geben.</span>
	 * <span class="en">Get the headerline by searching for different background color in the
	 * header area. If nothing is find return -1.</span>
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
		if (gd.getFc().getIsMatrix())
			return null;
		//----------------------------------------------------------
		//ConfigurationsWert zur Verschnellerung des Algortihmus
		int anzahlZeilen = gd.getCp().numberOfRows;
		//----------------------------------------------------------
		if (tabellenAnfang + anzahlZeilen < tabellenEnde)
			tabellenEnde = tabellenAnfang + anzahlZeilen;
		
		//Anzahl der Hintergrundfarben und ihres Vorkommens herausfinden
		Map<Integer, Integer> anzahlHintergrund = new HashMap<Integer,Integer>();
		for (int x=sd.colStart; x<sd.colEnd; x++)
		{
			if (!sd.emptyCols.contains(x))
				for (int y=tabellenAnfang; y<tabellenEnde; y++) {
					if (!sd.emptyRows.contains(y)) {
						int background = gd.getFc().backgroundColor(x, y);
						if (background == 0)
							continue;
						if (anzahlHintergrund.containsKey(background)) {
							anzahlHintergrund.put(background, anzahlHintergrund.get(background)+1);
						} else {
							anzahlHintergrund.put(background, 1);
						}
					}
				}
		}
		
		if (anzahlHintergrund.size() == 1)
			return null;
		int maximumBackground = 0;
		int color = 0;
		//was passiert wenn der maximale Integerbereich beim iterieren überschritten wird?		
		Iterator<Integer> iter = anzahlHintergrund.keySet().iterator();
		while (iter.hasNext()) {
			Integer key = iter.next();
			if (anzahlHintergrund.get(key) > maximumBackground) {
				maximumBackground = anzahlHintergrund.get(key);
				color = key;
			}
		}
		anzahlHintergrund.remove(color);
		iter = anzahlHintergrund.keySet().iterator();
		maximumBackground = 0;
		while (iter.hasNext()) {
			Integer key = iter.next();
			if (anzahlHintergrund.get(key) > maximumBackground) {
				maximumBackground = anzahlHintergrund.get(key);
				color = key;
			}
		}
		//Zeilenweise duchlaufen und suche 1. Zeile in der das vorkommt
		//Die Zeile muss die ganze Zeit headerfarbe halten wenn es einmal 
		//aufhört darf es nicht wieder anfangen
		int startHeader = -1;
		int endHeader = -1;
		for (int y=tabellenAnfang; y<tabellenEnde; y++) {
			if (!sd.emptyRows.contains(y)) {
				//prüft ob wenn Farbe aufhört noch mal die Farbe anfängt
				boolean onOut = false;
				//prüft ob einmal die farbe aufgehört hat
				boolean out = false;
				int counter = 0;
				for (int x=sd.colStart; x<sd.colEnd; x++) {
					if (!sd.emptyCols.contains(x)) {
						int backColor = gd.getFc().backgroundColor(x, y);
						if (backColor == 0)
							continue;
						if (backColor == color) {
							counter++;
							if (out)
								onOut = true;
						} else {
							if (counter > 0)
								out = true;
						}
					}
				}	
				if (counter > 0 && !onOut) {
					//Zeilegefunden die als Header in Frage kommt
					if (startHeader == -1)
						startHeader = y;
					if (y > endHeader)
						endHeader = y;
				}
			}
		}
		if (endHeader == -1)
			return null;
		HeaderVisualInformation h = new HeaderVisualInformation();
		RangeMatrix r = new RangeMatrix(sd.colStart, startHeader, sd.colEnd, endHeader);
		h.ranges.add(r);
		h.percent = 85;
		endHeader++;
		map.put(endHeader, h);
		return map;
	}
}
