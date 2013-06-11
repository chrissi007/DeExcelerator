package de.tudresden.deexcelerator.rules;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import de.tudresden.deexcelerator.data.AnalyzeInformation;
import de.tudresden.deexcelerator.data.DocumentInformation;
import de.tudresden.deexcelerator.util.HeaderVisualInformation;

/**
 * <span class="de">Headererkennung mit allen Regeln.</span>
 * <span class="en">Search the header with all rules.</span>
 * @author Christopher Werner
 *
 */
public class HeaderRecognizeNow {

	/**
	 * <span class="de">Headererkennung mit allen Regeln um genauen Header zu finden.
	 * F&uumlgt gefundenen Header bei den Headeranf&aumlngen ein.</span>
	 * <span class="en">Search the header with all rules and add it to header starts.</span>
	 * @param tabellenAnfang (<span class="de">gefundene Tabellenanf&aumlnge</span>
	 * <span class="en">the start for proofing of headerline</span>)
	 * @param gd (<span class="de">Verwendete DocumentInformation</span>
     * <span class="en">the used document information</span>)
     * @param sd (<span class="de">AnalyseInformation</span>
     * <span class="en">analyze information</span>)
	 * @return <span class="de">Wahrheitswert ob gefunden oder nicht</span>
	 * <span class="en">boolean value</span>
	 */
	public int action (int tabellenAnfang, int tabellenEnde , DocumentInformation gd, AnalyzeInformation sd) {
		int newValue = -1;
		
		Map<Integer, HeaderVisualInformation> all = new HashMap<Integer, HeaderVisualInformation>();
		
		Map<Integer, HeaderVisualInformation> headerDate = null;
		Map<Integer, HeaderVisualInformation> headerBack = null;
		Map<Integer, HeaderVisualInformation> sequenceValue = null;
		Map<Integer, HeaderVisualInformation> headerNumber = null;
		Map<Integer, HeaderVisualInformation> headerDifferents = null;
		Map<Integer, HeaderVisualInformation> headerLength = null;
		Map<Integer, HeaderVisualInformation> headerYear = null;
		
		if (gd.getCp().fs.headerRecognizeDateActivate) {
			//Header anhand von Datum		
			HeaderRecognizeDate hed = new HeaderRecognizeDate();
			headerDate = hed.action(tabellenAnfang, tabellenEnde, gd, sd);
		}
		
		if (gd.getCp().fs.headerRecognizeBackgroundcolorActivate) {
			//Header anhand von BackgroundColor
			HeaderRecognizeBackgroundcolor hrb = new HeaderRecognizeBackgroundcolor();
			headerBack = hrb.action(tabellenAnfang, tabellenEnde, gd, sd);
		}
		
		if (gd.getCp().fs.headerRecognizeNumberActivate) {
			//Header nach Zahlenwert
			HeaderRecognizeNumber hen = new HeaderRecognizeNumber();
			headerNumber = hen.action(tabellenAnfang, tabellenEnde, gd, sd);
		}
		
		if (gd.getCp().fs.headerRecognizeDifferentsActivate) {
			//Header nach Unterschied im Wertebereich
			HeaderRecognizeDifferents hediff = new HeaderRecognizeDifferents();
			headerDifferents = hediff.action(tabellenAnfang, tabellenEnde, gd, sd);
		}
		
		if (gd.getCp().fs.headerRecognizeSequenceActivate) {
			//Header nach Sequence von Zahlen
			HeaderRecognizeSequence hrs = new HeaderRecognizeSequence();
			sequenceValue = hrs.action(tabellenAnfang, tabellenEnde, gd, sd);
		}
		
		if (gd.getCp().fs.headerRecognizeStringLengthActivate) {
			//Header nach String Länge im Wertebereich
			HeaderRecognizeStringLength hesl = new HeaderRecognizeStringLength();
			headerLength = hesl.action(tabellenAnfang, tabellenEnde, gd, sd);
		}
		
		if (gd.getCp().fs.headerRecognizeYearActivate) {
			//Header nach Jahrezahlen im Header
			HeaderRecognizeYears hry = new HeaderRecognizeYears();
			headerYear = hry.action(tabellenAnfang, tabellenEnde, gd, sd);
		}
		
		if (headerDate != null) {
			all.putAll(headerDate);
			sd.vi.headerDate.putAll(headerDate);
		}
		if (headerBack != null) {
			all.putAll(headerBack);
			sd.vi.headerBackground.putAll(headerBack);
		}
		if (headerNumber != null) {
			all.putAll(headerNumber);
			sd.vi.headerNumber.putAll(headerNumber);
		}
		if (sequenceValue != null) {
			all.putAll(sequenceValue);
			sd.vi.headerSequence.putAll(sequenceValue);
		}
		if (headerYear != null) {
			all.putAll(headerYear);
			sd.vi.headerYear.putAll(headerYear);
		}
		if (headerDifferents != null) {
			all.putAll(headerDifferents);
			sd.vi.headerDifferents.putAll(headerDifferents);
		}
		if (headerLength != null) {
			all.putAll(headerLength);
			sd.vi.headerStringlength.putAll(headerLength);
		}
		
		if (gd.getCp().isHeaderMayority()) {
			//Mehrheitsbeschluss die Zeillen die am meisten gefunden wurden werden genommen
			Map<Integer, Integer> vorkommen = new HashMap<Integer, Integer>();
			for (Map.Entry<Integer, HeaderVisualInformation> entry : all.entrySet()) {
			    int key = entry.getKey();
			    if (vorkommen.containsKey(key))
			    	vorkommen.put(key, vorkommen.get(key)+1);
			    else
			    	vorkommen.put(key, 1);
			}
			int gefunden = 0;
			int counter = 0;
			for (Map.Entry<Integer, Integer> entry : vorkommen.entrySet()) {
			    int key = entry.getKey();
			    int value = entry.getValue();
			    if (value > counter) {
			    	gefunden = key;
			    	counter = value;
			    }
			}
			sd.headerStarts.add(gefunden);
			return gefunden;
		} else if (gd.getCp().isHeaderPercent()) {
			//Die Zeile mit dem höchsten Prozentwert wird genommen
			int gefunden = 0;
			int percent = 0;
			for (Map.Entry<Integer, HeaderVisualInformation> entry : all.entrySet()) {
			    int key = entry.getKey();
			    int value = entry.getValue().percent;
			    if (value > percent) {
			    	percent = value;
			    	gefunden = key;
			    }
			    
			}
			sd.headerStarts.add(gefunden);
			return gefunden;
		} else {
			//Nach Priorität der Algorithmen wird ein Ergebnis ausgewählt
			boolean found = false;
			if (headerDate != null) {
				found = true;
				newValue = Collections.min(headerDate.keySet());
			}
			if (!found && headerBack != null) {
				found = true;
				newValue = Collections.min(headerBack.keySet());
			}
			if (!found && headerNumber != null) {
				found = true;
				newValue = Collections.min(headerNumber.keySet());
				if (sequenceValue != null) {
					int seq = Collections.min(sequenceValue.keySet());
					if (seq < newValue)
						newValue = seq;
				} else if (headerYear != null) {
					int year = Collections.min(headerYear.keySet());
					if (year > newValue)
						newValue = year;
				}
			}
			if (!found && sequenceValue != null) {
				found = true;
				newValue = Collections.min(sequenceValue.keySet());
			}
			if (!found && headerYear != null) {
				found = true;
				newValue = Collections.min(headerYear.keySet());
			}
			if (!found && headerDifferents != null) {
				found = true;
				newValue = Collections.min(headerDifferents.keySet());
			}
			if (!found && headerLength != null) {
				found = true;
				newValue = Collections.min(headerLength.keySet());
			}
			//Profilaktisch Headerende auf eine Zeile 
			//nach Tabellen Anfang setzen falls nix gefunden
			if (newValue == -1) {
				newValue = tabellenAnfang+1;
			}
			sd.headerStarts.add(newValue);	
			return newValue;
		}
	}
}
