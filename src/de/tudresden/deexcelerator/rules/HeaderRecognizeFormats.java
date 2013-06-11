package de.tudresden.deexcelerator.rules;

import de.tudresden.deexcelerator.data.AnalyzeInformation;
import de.tudresden.deexcelerator.data.DocumentInformation;

/**
 * <span class="de">Headerlinie durch DATE und NUMBER. (Nicht Aktuell)</span>
 * <span class="en">Headerline by date and number.</span>
 * @author Christopher Werner
 *
 */
public class HeaderRecognizeFormats {
	
	/**
	 * <span class="de">Macht eine Zusammenfassung von Header durch Number und Date.
	 * Nicht mehr Aktuell.
	 * Bestimmt Wertebreich durch erstes auftreten einer von beiden.</span>
	 * <span class="en">Get the headerline by searching the first date or number value in the
	 * value area. If nothing is find return 0. Not actual.</span>
	 * @param gd (<span class="de">Verwendete DocumentInformation</span>
     * <span class="en">the used document information</span>)
     * @param sd (<span class="de">AnalyseInformation</span>
     * <span class="en">analyze information</span>)
	 * @return <span class="de">Intwert der gefundenen Zeile</span>
	 * <span class="en">int value of the headerline</span>
	 */
	public int action (DocumentInformation gd, AnalyzeInformation sd) {
		int numberAnzahl = 0;
		int dateAnzahl = 0;
		int zeilenWert = sd.rowEnd;
		//----------------------------------------------------------
		//ConfigurationsWert zur Verschnellerung des Algortihmus
		int anzahlZeilen = gd.getCp().numberOfRows;
		//----------------------------------------------------------
		if (sd.rowStart + anzahlZeilen < sd.rowEnd)
			zeilenWert = sd.rowStart + anzahlZeilen;
		for (int x=sd.colStart; x<sd.colEnd; x++)
			if (!sd.emptyCols.contains(x))
				for (int y=sd.rowStart; y<zeilenWert; y++)
				{
					if (!sd.emptyRows.contains(y))
					{
						if (gd.getFc().numberCell(x, y)) 
							numberAnzahl++;
						if (gd.getFc().dateCell(x, y))
							dateAnzahl++;
					}
				}
		int suchZelle = 0;
		//1 steht fuer Number
		//2 steht fuer Date
		//0 steht fuer Anfang
		//3 steht fuer Label
		if (numberAnzahl > 0 || dateAnzahl > 0)
			if (numberAnzahl >= dateAnzahl) {
				suchZelle = 1;
			} else if (numberAnzahl < dateAnzahl) {
				suchZelle = 2;
			}
		
		int vorherZelle = 0, jetztZelle = 0;
		
		//Loesung nimm den ersten Zeilenwert wo der Haupttype vorkommt
		int headerEnde = zeilenWert;
		for (int x=sd.colStart; x<sd.colEnd; x++)
		{
			if (!sd.emptyCols.contains(x))
				for (int y=sd.rowStart; y<headerEnde; y++)
				{
					if (!sd.emptyRows.contains(y))
					{
						if (gd.getFc().numberCell(x, y))
							jetztZelle = 1;
						else if (gd.getFc().dateCell(x, y))
							jetztZelle = 2;
						else
							jetztZelle = 3;
						if (jetztZelle != vorherZelle && jetztZelle == suchZelle)
						{
							//merke die Gefundene Zeile
							if (headerEnde > y)
								headerEnde = y;
						}
						vorherZelle = jetztZelle;						
					}
				}
		}
		return headerEnde;
	}
}
	
