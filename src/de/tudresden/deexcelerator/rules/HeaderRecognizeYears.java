package de.tudresden.deexcelerator.rules;

import java.util.HashMap;
import java.util.Map;

import de.tudresden.deexcelerator.data.AnalyzeInformation;
import de.tudresden.deexcelerator.data.DocumentInformation;
import de.tudresden.deexcelerator.util.HeaderVisualInformation;
import de.tudresden.deexcelerator.util.RangeMatrix;

/**
 * <span class="de">Headerlinie durch Jahreszahlen im Header.</span>
 * <span class="en">Headerline by year values in the header.</span>
 * @author Christopher Werner
 *
 */
public class HeaderRecognizeYears {
	
	/**
	 * <span class="de">Headerlinie mit Jahreszahlen erkennen, wobei diese zum Header gehören.
	 * Jahreszellen müssen zwischen 1500 und 2050 liegen.
	 * Wenn keine Headerlinie erkannt wurde -1 zurück geben.</span>
	 * <span class="en">Search year values between 1500 and 2050 in the header.
	 * If nothing is find return -1.</span>
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
		int anzahlGefundene = 5;
		int anzahlNotYearsValue = 1;
		//----------------------------------------------------------
		if (tabellenAnfang + anzahlZeilen < tabellenEnde)
			tabellenEnde = tabellenAnfang + anzahlZeilen;
		
		int gefunden = tabellenEnde;
		int anzahlYears = 0;
		int anzahlNotYears = 0;
		for (int y=tabellenAnfang; y<tabellenEnde; y++)
			if (!sd.emptyRows.contains(y))
			{

				for (int x=sd.colStart; x<sd.colEnd; x++)
					if (!sd.emptyCols.contains(x) && gd.getFc().numberCell(x, y))
					{
						Integer year =  gd.getFc().getIntegerValue(x, y);
						if (year == null)
							continue;
						int yearValue = year.intValue();
						if (intTest(gd, sd, x, y) && yearValue < 2050 && yearValue > 1500)
							anzahlYears++;
						else
							anzahlNotYears++;
					}
				if (anzahlYears > anzahlGefundene && anzahlNotYears <= anzahlNotYearsValue && gefunden > y) {
					gefunden = y + 1;	
					//vi liste erweitern wenn schon drin dann erhöhe prozentwert
					//grundwert 50%; 50/colnumber + wenn schon drin
					HeaderVisualInformation h = new HeaderVisualInformation();
					h.percent = 70 + 30/sd.colNumber * anzahlYears - anzahlNotYears*5;
					h.ranges.add(new RangeMatrix(sd.colStart, y, sd.colEnd, y));
					map.put(gefunden, h);
					break;
				}			
				anzahlYears = 0;
				anzahlNotYears = 0;
			}
		
		if (gefunden == tabellenEnde)
			return null;
		
		return map;
	}
	
	/**
	 * <span class="de">Gibt an ob in einer Zelle ein int Wert ist oder nicht.</span>
	 * <span class="en">Gives back a bool value, if in one celle is an int value or not.</span>
	 * @param gd (<span class="de">Verwendete DocumentInformation</span>
     * <span class="en">the used document information</span>)
     * @param sd (<span class="de">AnalyseInformation</span>
     * <span class="en">analyze information</span>)
	 * @param x (<span class="de">Spaltenwert</span>
	 * <span class="en">Colvalue</span>)
	 * @param y (<span class="de">Zeilenwert</span>
	 * <span class="en">Rowvalue</span>)
	 * @return <span class="de">Wahrheitswert, ob es ein int Wert ist.</span>
	 * <span class="en">boolean value, if it is an int value or not.</span>
	 */
	private boolean intTest (DocumentInformation gd, AnalyzeInformation sd, int x, int y) {
		Double yearDouble = gd.getFc().getDoubleValue(x, y);
		Integer yearInteger =  gd.getFc().getIntegerValue(x, y);
		if (yearDouble == null || yearInteger == null)
			return false;
		if (yearInteger.intValue() == yearDouble.doubleValue())
			return true;
		return false;
	}
}
