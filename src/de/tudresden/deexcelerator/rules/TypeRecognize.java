package de.tudresden.deexcelerator.rules;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.validator.routines.DomainValidator;
import org.apache.commons.validator.routines.EmailValidator;
import org.apache.commons.validator.routines.UrlValidator;

import de.tudresden.deexcelerator.data.AnalyzeInformation;
import de.tudresden.deexcelerator.data.DataType;
import de.tudresden.deexcelerator.data.DocumentInformation;
import de.tudresden.deexcelerator.data.Header;
import de.tudresden.deexcelerator.util.ColInformation;

/**
 * <span class="de">Erkennen der Datentypen in den Spalten.</span>
 * <span class="en">Recognize the datatypes in ths cols.</span>
 * @author Christopher Werner
 *
 */
public class TypeRecognize {

	/**
	 * <span class="de">Aufgrundlage zuerst von CellType und danach auf Umrechnungen erkennen der Spaltentypen.</span>
	 * <span class="en">Find the data types, because of the cell types in the cols.</span>
	 * @param gd (<span class="de">Verwendete DocumentInformation</span>
     * <span class="en">the used document information</span>)
     * @param sd (<span class="de">AnalyseInformation</span>
     * <span class="en">analyze information</span>)
	 */
	public void action (DocumentInformation gd, AnalyzeInformation sd) {
		for (int x = sd.colStart; x<sd.colEnd ; x++)
			if (!sd.emptyCols.contains(x)) {
				int doubleAnzahl = 0;
				int intAnzahl = 0;
				int bigintAnzahl = 0;
				int dateAnzahl = 0;
				int stringAnzahl = 0;
				int stringLaenge = 0;
				int currencyAnzahl = 0;
				int urlAnzahl = 0;
				int emailAnzahl = 0;
				for (int y = sd.headerFinish; y<sd.rowEnd ; y++)
					if (!sd.emptyRows.contains(y)) { 
						if (gd.getFc().emptyCell(x, y))
							continue;
						else if (gd.getFc().numberCell(x, y)){
							Double doubleValue = gd.getFc().getDoubleValue(x, y);
							if (doubleValue == null)
								break;
							if (gd.getFc().isCurrency(x, y))
								currencyAnzahl++;
							else {
								double doubleWert = doubleValue.doubleValue();
								int intWert = doubleValue.intValue();
								if (doubleWert == intWert) {
									if (intWert > 32767 || intWert < -32768)
										bigintAnzahl++;
									else
										intAnzahl++;
								} else {
									doubleAnzahl++;
								}
							}
						} else if (gd.getFc().dateCell(x, y)){
							dateAnzahl++;
						} else {
							String content = gd.getFc().contentCell(x, y);
							int length = content.length();
							if (length>stringLaenge)
								stringLaenge = length;
							if (UrlValidator.getInstance().isValid(content) ||
									DomainValidator.getInstance().isValid(content))
								urlAnzahl++;
							else if (EmailValidator.getInstance().isValid(content))
								emailAnzahl++;
							else
								stringAnzahl++;
						}
					}
				//füllen der Vi Map
				//Map<Integer, Map <String, Integer> > ergebnisseTypen;
				Map<String, Integer> colInfos = new HashMap<String, Integer>();
				if (doubleAnzahl > 0)				
					colInfos.put("DOUBLE", doubleAnzahl);
				if (intAnzahl > 0)				
					colInfos.put("INT", intAnzahl);
				if (bigintAnzahl > 0)				
					colInfos.put("BIGINT", bigintAnzahl);
				if (dateAnzahl > 0)				
					colInfos.put("DATE", dateAnzahl);
				if (stringAnzahl > 0)				
					colInfos.put("STRING", stringAnzahl);
				if (currencyAnzahl > 0)				
					colInfos.put("CURRENCY", currencyAnzahl);
				if (urlAnzahl > 0)				
					colInfos.put("URL", urlAnzahl);
				if (emailAnzahl > 0)				
					colInfos.put("EMAIL", emailAnzahl);
				sd.vi.ergebnisseTypen.put(x, colInfos);
				
				//neue Bestimmung der Datentypen für Enum				
				int anzahlNumber = doubleAnzahl + intAnzahl + bigintAnzahl + currencyAnzahl;
				int anzahlStringtypes = urlAnzahl + emailAnzahl + stringAnzahl;
				int anzahlAll = anzahlNumber + anzahlStringtypes + dateAnzahl;
				
				boolean gefunden = false;
				if (anzahlNumber > anzahlAll * 0.6)	{
					int format = 0;
					DataType type = null;
					DataType convertTo = null;
					if (currencyAnzahl > anzahlAll * 0.5) {
						//setzte Datentype auf Currency
						format = 1;
						type = DataType.CURRENCY;
					} else if (doubleAnzahl > 0) {
						//setzte Datentype auf Double
						format = 1;
						type = DataType.DOUBLE;
					} else if (bigintAnzahl > 0) {
						//setzte Datentype auf bigint
						format = 2;
						type = DataType.BIGINT;
					} else if (intAnzahl > 0) {
						//setzte Datentype auf int
						format = 2;
						type = DataType.INTEGER;
					}
					if (anzahlStringtypes > dateAnzahl) {
						//setze convertTo auf String
						convertTo = DataType.STRING;
					} else if (dateAnzahl > anzahlStringtypes)
						convertTo = DataType.DATETIME;
					Header h = new Header("default", 0, type, convertTo);
					ColInformation z = new ColInformation(format, x, 0, h);
					sd.colInfos.add(z);
					gefunden = true;
				} else if (dateAnzahl > anzahlAll * 0.6) {
					DataType convertTo = null;
					if (anzahlNumber > anzahlStringtypes) {
						if (currencyAnzahl > 0)
							convertTo = DataType.CURRENCY;
						else if (doubleAnzahl > 0)
							convertTo = DataType.DOUBLE;
						else if (bigintAnzahl > 0)
							convertTo = DataType.BIGINT;
						else if (intAnzahl > 0)
							convertTo = DataType.INTEGER;
					} else if (anzahlNumber < anzahlStringtypes) {
						convertTo = DataType.STRING;
					}
					Header h = new Header("default", 0, DataType.DATETIME, convertTo);
					ColInformation z = new ColInformation(3, x, 0, h);
					sd.colInfos.add(z);
					gefunden = true;
				} else /*if (anzahlStringtypes > anzahlAll * 0.6)*/ {
					DataType type;
					DataType convertTo = null;
					if (emailAnzahl > anzahlAll * 0.5)
						type = DataType.EMAIL;
					else if (urlAnzahl > anzahlAll * 0.5)
						type = DataType.URL;
					else
						type = DataType.STRING;
					if (anzahlNumber > dateAnzahl) 
					{
						if (currencyAnzahl > 0)
							convertTo = DataType.CURRENCY;
						else if (doubleAnzahl > 0)
							convertTo = DataType.DOUBLE;
						else if (bigintAnzahl > 0)
							convertTo = DataType.BIGINT;
						else if (intAnzahl > 0)
							convertTo = DataType.INTEGER;
					} else if (dateAnzahl > anzahlNumber)
						convertTo = DataType.DATETIME;
					Header h = new Header("default", stringLaenge, type, convertTo);
					ColInformation z = new ColInformation(4, x, stringLaenge, h);
					sd.colInfos.add(z);
					gefunden = true;
				}
				if (!gefunden)
				{
					Header h = new Header("default", stringLaenge, DataType.STRING, null);
					ColInformation z = new ColInformation(4, x, stringLaenge, h);
					sd.colInfos.add(z);
				}		
			}	
	}
}
