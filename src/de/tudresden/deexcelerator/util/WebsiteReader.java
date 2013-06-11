package de.tudresden.deexcelerator.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;

import de.tudresden.deexcelerator.Configuration;
import de.tudresden.deexcelerator.core.HTMLExtraction;
import de.tudresden.deexcelerator.output.OutputObject;
import de.tudresden.deexcelerator.stringarray.HTMLStringarray;
import de.tudresden.deexcelerator.stringarray.OutputStringarray;



/**
 * <span class="de">Einlesen des HTML Codes von Webseiten.</span>
 * <span class="en">Read the html code of a website.</span>
 * @author Christopher Werner
 *
 */
public class WebsiteReader {
    
	/**
	 * <span class="de">Nimmt eine URL und liest ihren HTML Code ein.
	 * F&uumlgt diesen HTML Code der Pipeline hinzu.</span>
	 * <span class="en">Read the html code from an url.</span>
	 * @param s (<span class="de">URL Adresse der Webseite</span>
	 * <span class="en">url of the website</span>)
	 * @param cp (<span class="de">auch null m&oumlglich dann setzen auf Standardwerte</span>
	 * <span class="en">configuration parameters from user or null</span>)
	 * @return <span class="de">Liste von OutputObjecten</span>
	 * <span class="en">List of OutputObjects</span>
	 */
    public List<OutputObject> doIt(String s, Configuration cp) {    	 
    	String html = "";
        BufferedReader br = null;
        InputStreamReader isr = null;
        URL url = null;
        try {
            url = new URL(s);
            if (url != null) {
            	isr = new InputStreamReader(url.openStream());
                br = new BufferedReader(isr);
                String line = null;
                     
                while ((line = br.readLine()) != null) {
                  	html = html + line;
                }
                HTMLExtraction he = new HTMLExtraction();
                return he.tablesFromHTML(s, html, cp);
            } 
        } catch (Exception e) {
            e.printStackTrace();
        } 
		return null;
    }
    
    /**
	 * <span class="de">Nimmt eine URL und liest ihren HTML Code ein.
	 * F&uumlgt diesen HTML Code der Pipeline hinzu.</span>
	 * <span class="en">Read the html code from an url.</span>
	 * @param s (<span class="de">URL Adresse der Webseite</span>
	 * <span class="en">url of the website</span>)
	 * @return <span class="de">Liste von OutputStringarray</span>
	 * <span class="en">List of OutputStringarray</span>
	 */
    public List<OutputStringarray> doIt(String s) {
    	if (s == null || s.trim().equals(""))
    		return null;
    	String html = "";
        BufferedReader br = null;
        InputStreamReader isr = null;
        URL url = null;
        try {
            url = new URL(s);
            if (url != null) {
            	isr = new InputStreamReader(url.openStream());
                br = new BufferedReader(isr);
                String line = null;
                     
                while ((line = br.readLine()) != null) {
                  	html = html + line;
                }
                HTMLStringarray hs = new HTMLStringarray();
                return hs.tablesFromHTML(s, html);
            } 
        } catch (Exception e) {
            e.printStackTrace();
        } 
		return null;
    }
}
