package de.tudresden.deexcelerator.data;

import java.util.ArrayList;
import java.util.List;

/**
 * <span class="de">String Funktionen f&uumlr spezielle Modifikationen.</span>
 * <span class="en">String function for specific modifications.</span>
 * @author Christopher Werner
 *
 */
public class StringFunctions {
	
	/**
	 * <span class="de">Pr&uumlft ob Stringwert eine Zahl ist.</span>
	 * <span class="en">Proof if a string value is a numeric value.</span>
	 * @param wert (<span class="de">zu &uumlberpr&uumlfender String</span>
	 * <span class="en">string to proof</span>)
	 * @return <span class="de">Wahrheitswert true wenn ja, false wenn nein</span>
	 * <span class="en">boolean answer</span>
	 */
	public boolean numberTest (String wert) {
	    if (wert == null || wert.trim().equals(""))
	    	return false;
	    char[] c = wert.trim().toCharArray();
	    for (int x = 0; x<c.length; x++)
	    {	    	
	    	if (c[x] == '.' || c[x] == ',' || c[x] == '+' || c[x] == '-' || c[x] == '%')
	    		continue;
	    	if (c[x]<='9' && c[x]>='0')
	    		continue;
	    	return false;
	    }
	    return true;
	}
	
	/**
	 * <span class="de">Pr&uumlft ob es sich um Englische Zahlen handelt.</span>
	 * <span class="en">Proof if there are English numbers.</span>
	 * @param wert (<span class="de">zu &uumlberpr&uumlfender String</span>
	 * <span class="en">string for proofing</span>)
	 * @return <span class="de">Wahrheitswert true wenn ja, false wenn nein</span>
	 * <span class="en">boolean answer</span>
	 */
	public boolean englischTest (String wert) {
		if (wert == null || wert.trim().equals(""))
	    	return false;
		int counter = 0;
	    char[] c = wert.trim().toCharArray();
	    for (int x = 0; x<c.length; x++)
	    {	    
	    	if (c[x] == ',') {
	    		counter++;
	    		continue;
	    	}
	    	if (c[x] == '.' || c[x] == '+' || c[x] == '-' || c[x] == '%')
	    		continue;
	    	if (c[x]<='9' && c[x]>='0')
	    		continue;
	    	return false;
	    }
	    if (counter > 1)	    	
	    	return true;
	    return false;
	}
	
	/**
	 * <span class="de">Macht alle '.' zu ',' und andersherum.</span>
	 * <span class="en">Change all '.' to an ',' and reverse.</span>
	 * @param wert (<span class="de">zu bearbeitender String</span>
	 * <span class="en">string for proofing</span>)
	 * @return <span class="de">Ergebnis String</span>
	 * <span class="en">solution string</span>
	 */
	public String stringChangerEnglish (String wert) {
		String ergebnis="";
	    char[] c = wert.trim().toCharArray();
	    for (int x = 0; x<c.length; x++)
	    {
	    	if (c[x]==',')
	    		ergebnis = ergebnis + ".";
	    	else if (c[x]=='.')
	    		ergebnis = ergebnis + ",";
	    	else
	    		ergebnis = ergebnis + c[x];
	    }	    	
		return ergebnis;
	}
	
	/**
	 * <span class="de">Ersetzt mehrere Leerzeichen durch eins.
	 * Ersetzt alle Leerzeichen und Bindestriche durch einen Unterstrich.</span>
	 * <span class="en">Change two or more empty signs to one and every 
	 * empty sign and hyphen to an underline.</span>
	 * @param wert (<span class="de">zu bearbeitender Stringwert</span>
	 * <span class="en">string value for modification</span>)
	 * @return <span class="de">modifizierter Stringwert</span>
	 * <span class="en">modified string value</span>
	 */
	public String stringChangerUnterstrich (String wert) {
		String ergebnis="";
	    char[] c = wert.trim().toCharArray();
	    for (int x = 0; x<c.length; x++)
	    {
	    	if (c[x] == '.')
	    		continue;
	    	if (c[x]<='Z' && c[x]>='A' || c[x]<='z' && c[x]>='a' || c[x]<='9' && c[x]>='0')
	    		ergebnis = ergebnis + c[x];
	    	else
	    		ergebnis = ergebnis + ' ';
	    }    
	    c = ergebnis.toCharArray();
	    ergebnis = "";
	    boolean checker = false;
	    for (int x = 0; x<c.length; x++)
	    {	    	
	    	if (c[x] == ' ')
	    		checker = true;
	    	else if (checker) {
	    		checker = false;
	    		ergebnis = ergebnis + ' ' + c[x];
	    	} else
	    		ergebnis = ergebnis + c[x];
	    }	    
	    c = ergebnis.toCharArray();
	    ergebnis = "";	    
	    for (int x = 0; x<c.length; x++)
	    {
	    	if (c[x]==' ')
	    		ergebnis = ergebnis + "_";
	    	else
	    		ergebnis = ergebnis + c[x];
	    }	
		return ergebnis;
	}
	
	/**
	 * <span class="de">Schneidet einen Stringwert auf eine L&aumlnge von 54 Zeichen zurecht.</span>
	 * <span class="en">Cuts a string value to a length of 54 signs.</span>
	 * @param wert (<span class="de">zu bearbeitender Stringwert</span>
	 * <span class="en">string for modification</span>)
	 * @return <span class="de">modifizierter Stringwert</span>
	 * <span class="en">modified string</span>
	 */
	public String stringSmaller (String wert) {
		String ergebnis="";
	    char[] c = wert.trim().toCharArray();
	    if (c.length>55) {
		    for (int x = 0; x<55; x++)
		    {	    	
		    	ergebnis = ergebnis + c[x];
		    }
		    return ergebnis;
	    }		
		return wert;
	}
	
	/**
	 * <span class="de">Trennt eine Zeile in mehrere Strings immer wenn der splitter vorkommt.</span>
	 * <span class="en">Divide a row in more string if there comes the split sign.</span>
	 * @param wert (<span class="de">zu bearbeitende Zeile</span>
	 * <span class="en">string for modification</span>)
	 * @param splitter (<span class="de">Charwert</span>
	 * <span class="en">char value</span>)
	 * @return <span class="de">Liste der geteilten Strings</span>
	 * <span class="en">list of the separated strings.</span>
	 */
	public List<String> splitterCsvRow (String wert, char splitter) {
		List<String> ergebnisMenge = new ArrayList<String>();
		String ergebnis="";
	    char[] c = wert.toCharArray();
	    boolean inAnfZei = false;
	    for (int x = 0; x<c.length; x++)
	    {
	    	if (c[x]=='"') {
	    		if (inAnfZei) {
	    			ergebnisMenge.add(ergebnis);
	    			ergebnis = "";
	    			inAnfZei = false;
	    		} else
	    			inAnfZei = true;
	    	} else if (inAnfZei) {
	    		ergebnis = ergebnis + c[x];
	    	} else if (c[x]==splitter) {
	    		ergebnisMenge.add(ergebnis);
	    		ergebnis = "";
	    	} else
	    		ergebnis = ergebnis + c[x];
	    }	
		return ergebnisMenge;
	}
	
	/**
	 * <span class="de">Bearbeite f&uumlr einen Wert diesen damit er in PostgreSQL eingef&uumlgt werden kann.</span>
	 * <span class="en">Modifie a value for inserting in postgreSQL.</span>
	 * @param wert (<span class="de">zu bearbeitender Wert</span>
	 * <span class="en">sting for modifikation</span>)
	 * @return <span class="de">modifizierter Wert</span>
	 * <span class="en">modified string</span>
	 */
	public String stringChangerHochkomma (String wert) {
		String test="";
	    char[] c = wert.toCharArray();
	    boolean vergleicher  = false;
	    for (int x = 0; x<c.length; x++)
	    {
	    	if (c[x]=='\'') {
	    		test = test + '\\' + '\'';
	    		vergleicher = true;
	    	} else
	    		test = test + c[x];
	    }
	    if (vergleicher)
	    	test = "E'" + test + "'";
	    else
	    	test = "'" + test + "'";
		return test;
	}
	
	/**
	 * <span class="de">Bearbeitet den Headerstring, so das nur noch Buchstaben und Leerzeichen vorkommen.</span>
	 * <span class="en">Change the headerstring, that he only consits lettern and empty signs.</span>
	 * @param wert (<span class="de">zu bearbeitender Wert</span>
	 * <span class="en">sting for modifikation</span>)
	 * @return <span class="de">modifizierter Wert</span>
	 * <span class="en">modified string</span>
	 */
	public String stringHeaderChanger (String wert) {
		String test="";
	    char[] c = wert.toCharArray();
	    for (int x = 0; x<c.length; x++)
	    {
	    	if (c[x]<='z' && c[x]>='a' || c[x]<='Z' && c[x]>='A' || c[x]==' ' || c[x]<='9' && c[x]>='0')
	    		test = test + c[x];
	    	else if (c[x]=='#')
	    		test = test + "Anzahl";
	    	else
	    		test = test + ' ';
	    }
	    test = test.trim();	
		return test;
	}
}
