package de.tudresden.deexcelerator.core;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;




import de.tudresden.deexcelerator.Configuration;
import de.tudresden.deexcelerator.data.AnalyzeInformation;
import de.tudresden.deexcelerator.data.DocumentInformation;
import de.tudresden.deexcelerator.output.OutputObject;
import de.tudresden.deexcelerator.util.FileIterator;

/**
 * <span class="de">F&uumlhrt alle Umwandlungen eines CSV Dokuments durch.
 * Einleiten der CSV Dokumente in die Extraktionspipeline.</span>
 * <span class="en">Takes an csv document and change it for the extraction pipeline.</span>
 * @author Christopher Werner
 *
 */
public class CsvExtraction {

	/**
	 * <span class="de">Macht eine Matrix aus der CSV Datei und f&uumlhrt diese in die Extraktionspipeline.</span>
	 * <span class="en">Create an array of strings from the csv document and add it to extraction pipeline.</span>
	 * @param dateiname <span class="de">Dateiname eigene Analyse</span>
	 * <span class="en">directory of the document</span>
	 * @param cp <span class="de">auch null m&oumlglich dann setzen auf Standardwerte</span>
	 * <span class="en">configuration parameters from user or null</span>
	 * @param splitter <span class="de">Char der dem Trennzeichen entspricht</span>
	 * <span class="en">char, which represents the splitter in the lines</span>
	 * @return <span class="de">Liste von OutputObjecten</span>
	 * <span class="en">List of OutputObjects</span>
	 */
	public List<OutputObject> valuesToMatrix (String dateiname, Configuration cp, char splitter) {
		String name = dateiname.substring(0, dateiname.length()-4);
		int endY = 0;
		int endX = 0;
		try {
			FileIterator<String> fIter = new FileIterator<String>(dateiname);
			while (fIter.hasNext()) {
				int x = 1;
				String line = fIter.next();
				//for Schleife um Werte in Excel Tabelle einzutragen
				char[] c = line.toCharArray();
			    boolean inAnfZei = false;
			    for (int i = 0; i<c.length; i++)
			    {
			    	if (c[i]=='"')
			    		if (inAnfZei)
			    			inAnfZei = false;
			    		else
			    			inAnfZei = true;
			    	else if (!inAnfZei && c[i]==splitter)
			    		x++;
		    	}	
			    if (x>endX)
			    	endX = x;
			    endY++;			
			}
			String[][] matrix = new String[endX][endY];
			
			if (endX == 0 || endY == 0)
				return null;
			fIter = new FileIterator<String>(dateiname);
			int y = 0;
			while (fIter.hasNext()) {
				int x = 0;
				String line = fIter.next();
				String ergebnis="";
			    char[] c = line.toCharArray();
			    boolean inAnfZei = false;
			    for (int i = 0; i<c.length; i++)
			    {
			    	boolean test = true;
			    	if (c[i]=='"') {
			    		if (inAnfZei) {
			    			inAnfZei = false;
			    		} else
			    			inAnfZei = true;
			    	} else if (inAnfZei) {
			    		ergebnis = ergebnis + c[i];
			    	} else if (c[i]==splitter) {
			    		//Celle zum Sheet adden
			    		matrix[x][y] = ergebnis.trim();
	    				x++;							
		    			ergebnis = "";
		    			test = false;
		    		} else
		    			ergebnis = ergebnis + c[i];
		    		if (test && i == c.length-1) {
		    			//Celle zum Sheet adden
		    			matrix[x][y] = ergebnis.trim();
		    		}
		    	}	
		    	y++;
		    		
			}
			DocumentInformation gd = new DocumentInformation(true, null, matrix, name, null);
			gd.setCp(cp);
			//Einführen in Extraktionspipeline
			//------------------------------------------
			AnalyzeInformation sd = new AnalyzeInformation(gd);
			TableSearch ts = new TableSearch(gd,sd);
			return ts.ruleSequence();
			//------------------------------------------
		} catch (Exception e) {
			System.out.println("Fehler bei der Bestimmung der Matrix hoehe und breite!");
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * <span class="de">Macht eine Matrix aus der CSV Datei und f&uumlhrt diese in die Extraktionspipeline.</span>
	 * <span class="en">Create an array of strings from the csv document and add it to extraction pipeline.</span>
	 * @param f <span class="de">File name eigene Analyse</span>
	 * <span class="en">file for extraction</span>
	 * @param cp <span class="de">auch null m&oumlglich dann setzen auf Standardwerte</span>
	 * <span class="en">configuration parameters from user or null</span>
	 * @param splitter <span class="de">Char der dem Trennzeichen entspricht</span>
	 * <span class="en">char, which represents the splitter in the lines</span>
	 * @return <span class="de">Liste von OutputObjecten</span>
	 * <span class="en">List of OutputObjects</span>
	 */
	public List<OutputObject> valuesToMatrix (File f, Configuration cp, char splitter) {
		String dateiname = f.getPath();
		return this.valuesToMatrix(dateiname, cp, splitter);
	}
	
	/**
	 * <span class="de">Macht eine Matrix aus der CSV Datei und f&uumlhrt diese in die Extraktionspipeline.</span>
	 * <span class="en">Create an array of strings from the csv document and add it to extraction pipeline.</span>
	 * @param stream <span class="de">Inputstream der eingehenden Analysedatei</span>
	 * <span class="en">InputStream of the incoming document</span>
	 * @param cp <span class="de">auch null m&oumlglich dann setzen auf Standardwerte</span>
	 * <span class="en">configuration parameters from user or null</span>
	 * @param splitter <span class="de">Char der dem Trennzeichen entspricht</span>
	 * <span class="en">char, which represents the splitter in the lines</span>
	 * @return <span class="de">Liste von OutputObjecten</span>
	 * <span class="en">List of OutputObjects</span>
	 */
	public List<OutputObject> valuesToMatrix (InputStream stream, Configuration cp, char splitter) {
		File f = null;
		try {
			//erstellen eines CSV Dokuments aus dem Inputstream und einführen in
			//Extraktionspipeline
			f=new File("outFile.csv");
			OutputStream out=new FileOutputStream(f);
			byte buf[]=new byte[1024];
			int len;
			while((len=stream.read(buf))>0)
			out.write(buf,0,len);
			out.close();
			stream.close();
		} catch (Exception e){
			e.printStackTrace();
		}
		String dateiname = f.getPath();
		return this.valuesToMatrix(dateiname, cp, splitter);
	}
}
