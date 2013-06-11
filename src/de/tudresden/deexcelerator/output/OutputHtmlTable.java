package de.tudresden.deexcelerator.output;

import java.io.FileOutputStream;
import java.util.List;

/**
 * <span class="de">Erzeugt eine HTML Ausgabe mit 2 Frames.
 * Dabei wird eine Frameset erzeugt.
 * Es wird der alte Tabelleninhalt in Frame1 eingef&uumlgt.
 * Es wird der neue Tabelleninhalt in Frame2 eingef&uumlgt.</span>
 * <span class="en">Creates a HTML output document with a frameset of the old html
 * tables and one with the new relations.</span>
 * @author Christopher Werner
 *
 */
public class OutputHtmlTable {

	/** <span class="de">String mit altem Tabelleninhalt.</span>
	 * <span class="en">String with old tables.</span> */
	private String tablesOld = "";
	/** <span class="de">String mit neuem Tabelleninhalt.</span>
	 * <span class="en">String with new relations as tables.</span> */
	private String tablesNew = "";
	
	/**
	 * <span class="de">F&uumlgt eine gefundene Tabelle den alten Tabellen hinzu.</span>
	 * <span class="en">Adds an old table to the tablesOld string.</span> 
	 * @param oldTable (<span class="de">String mit dem Inhalt der alten Tabelle</span>
	 * <span class="en">string with new old table</span>)
	 */
	public void addOldTable (String oldTable) {
		tablesOld = tablesOld + oldTable.substring(0, 6);
		tablesOld = tablesOld + " border='4' cellpadding='2' cellspacing='1'";
		tablesOld = tablesOld + oldTable.substring(6, oldTable.length()); 
	}
	
	/**
	 * <span class="de">Erstellt Frame eins mit den alten Tabellen.</span>
	 * <span class="en">Creates frame one with the old tables.</span>
	 */
	private void createFrameOne () {
		String inhalt = "<html><head>" +
    	"<meta http-equiv='Content-Type' content='text/html; charset=utf-8' />" +
    	"   	<title>Unbenanntes Dokument</title>" +
    	"    	</head><body>";
    	inhalt = inhalt + tablesOld;   	
    	inhalt = inhalt + "</body></html>";
        createSite("frame1.html", inhalt);
	}
	
	/**
	 * <span class="de">Erstellt das Frameset, welches immer gleich aussieht.</span>
	 * <span class="en">Creates the frameset, which is always the same.</span>
	 */
	private void createFrameSet () {
		String inhalt = "<html xmlns='http://www.w3.org/1999/xhtml'><head>" +
				"		<meta http-equiv='Content-Type' content='text/html; charset=utf-8'>" +
				"		<title>Frameset</title>" +
				"		</head><frameset cols='*,*' frameborder='no' border='0' framespacing='0'>" +
				"		  <frame src='frame1.html' name='leftFrame' id='leftFrame' title='leftFrame'>" +
				"		  <frame src='frame2.html' name='mainFrame' id='mainFrame' title='mainFrame'>" +
				"		</frameset><noframes></noframes></html>";
		createSite("frameset.html", inhalt);
	}
	
	/**
	 * <span class="de">Erstellt Frame2 mit den neuen Tabellen.</span>
	 * <span class="en">Creates frame two with the new tables.</span>
	 */
	private void createFrameTwo () {
		String inhalt = "<html><head>" +
    	"<meta http-equiv='Content-Type' content='text/html; charset=utf-8' />" +
    	"   	<title>Unbenanntes Dokument</title>" +
    	"    	</head><body>";
    	inhalt = inhalt + tablesNew;   	
    	inhalt = inhalt + "</body></html>";
        createSite("frame2.html", inhalt);
	}
	
	/**
	 * <span class="de">F&uumlgt alle gefundenen Tabellen dem neuene Tabellenstring hinzu.</span>
	 * <span class="en">Adds all extracted relations to the newTable string and add the table tags.</span>
	 * @param outs (<span class="de">Liste an erkannten Tabellen als OutputObjecte</span>
	 * <span class="en">List of OutputObjects</span>)
	 */
	public void addNewTables (List<OutputObject> outs) {
		for (int j = 0; j<outs.size(); j++) {
			OutputObject oo = outs.get(j);
			String inhalt = "<table border='4' cellpadding='2' cellspacing='1'><tbody><tr>";
	    	for (int i = 0; i<oo.getHeaderInfo().length; i++)
	    		inhalt = inhalt + "<th>" + oo.getHeaderInfo()[i].getName() + "</th>";
	    	inhalt = inhalt + "</tr>";
			if (oo.getMatrix().length > 0)
		    	for (int y = 0; y<oo.getMatrix()[0].length;y++) {
					inhalt = inhalt + "<tr>";
					for (int x = 0; x<oo.getMatrix().length;x++) {
						inhalt = inhalt + "<td>" + oo.getMatrix()[x][y] + "</td>";
					}
					inhalt = inhalt + "</tr>";
				}
	    	inhalt = inhalt + "</tbody></table>";
	    	tablesNew = tablesNew + inhalt;
		}
	}
	
	/**
	 * <span class="de">Erstellt die HTML Seiten.</span>
	 * <span class="en">Creates a html page.</span>
	 * @param name (<span class="de">Name der zu erzeugenden HTML Datei</span>
	 * <span class="en">name of the new html page</span>)
	 * @param content (<span class="de">HTML Code der zu erzeugenden Datei</span>
	 * <span class="en">code for the new html page</span>)
	 */
	private void createSite (String name, String content) {
		FileOutputStream schreibeStrom;
		try {
			schreibeStrom = new FileOutputStream(name);
	        for (int i=0; i < content.length(); i++){
	          schreibeStrom.write((byte)content.charAt(i));
	        }
	        schreibeStrom.close();
		} catch (Exception e) {
			//System.out.println("Ausgabe nicht geklappt!");
			e.printStackTrace();
		}
	}
	
	/**
	 * <span class="de">Erstellen aller Dateien f&uumlr die HTML Pr&uumlfung.</span>
	 * <span class="en">Creates all document for the html output and representation of the modifications.</span>
	 */
	public void createHtmlPages() {
		this.createFrameSet();
		this.createFrameOne();
		this.createFrameTwo();
	}
}
