package de.tudresden.deexcelerator.output;

import java.io.File;
import java.io.FileOutputStream;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

/**
 * <span class="de">Ausgabe Methoden zum Pr&uumlfen von Ergebnissen.</span>
 * <span class="en">Output methods to proof some solutions.</span>
 * @author Christopher Werner
 *
 */
public class OutputMethods {

	/**
	 * <span class="de">Erzeugt eine Excel Datei aus einem String Array.</span>
	 * <span class="en">Creates a xls document from one string array.</span>
	 * @param matrix (<span class="de">Stringarray</span>
	 * <span class="en">string array</span>)
	 * @param name (<span class="de">String der den Namen der Ausgabe beschreibt</span>
	 * <span class="en">string with the name of the output</span>)
	 */
	public void giveOutExcel (String[][] matrix, String name) {
		if (name == null)
			return;
    	try {
			WritableWorkbook ueberfuehrung = Workbook.createWorkbook(new File(name + ".xls"));
			WritableSheet sheet = ueberfuehrung.createSheet("Tabellen", 0);
			for (int x = 0; x<matrix.length;x++)
				for (int y=0; y<matrix[0].length;y++) {
					Label content = new Label(x, y, matrix[x][y]);
					sheet.addCell(content);
				}
			ueberfuehrung.write();
			ueberfuehrung.close();
			
		} catch (Exception e) {
			//System.out.println("Matrix ausgabe nicht moeglich!");
			e.printStackTrace();
		}
    }
	
	/**
	 * <span class="de">Erzeugt eine html Seite mit dem gegebenen Inhalt.</span>
	 * <span class="en">Creates a html page with the string content.</span>
	 * @param table (<span class="de">Inhalt f&uumlr die HTML Seite.</span>
	 * <span class="en">content of the html page</span>)
	 * @param documentname (<span class="de">Name der html Seite</span>
	 * <span class="en">name of the html page</span>)
	 */
    public void frameOne (String table, String documentname) {
    	String inhalt = "<html><head>" +
    	"<meta http-equiv='Content-Type' content='text/html; charset=utf-8' />" +
    	"   	<title>Unbenanntes Dokument</title>" +
    	"    	</head><body>";
    	//Aussehen modifizieren
    	inhalt = inhalt + table.substring(0, 6);
    	inhalt = inhalt + " border='4' cellpadding='2' cellspacing='1'";
    	inhalt = inhalt + table.substring(6, table.length());    	
    	//
    	//inhalt = inhalt + table;
    	inhalt = inhalt + "</body></html>";
    	String dateiName = documentname + ".html";
        FileOutputStream schreibeStrom;
		try {
			schreibeStrom = new FileOutputStream(dateiName);
	        for (int i=0; i < inhalt.length(); i++){
	          schreibeStrom.write((byte)inhalt.charAt(i));
	        }
	        schreibeStrom.close();
		} catch (Exception e) {
			//System.out.println("Ausgabe nicht geklappt!");
			e.printStackTrace();
		}
    }
}
