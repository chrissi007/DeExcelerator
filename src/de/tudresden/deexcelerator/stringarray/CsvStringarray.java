package de.tudresden.deexcelerator.stringarray;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import de.tudresden.deexcelerator.util.FileIterator;

/**
 * <span class="de">F&uumlhrt alle Umwandlungen eines CSV Dokuments durch.</span>
 * <span class="en">Takes an csv document and change it.</span>
 * @author Christopher Werner
 *
 */
public class CsvStringarray {

	/**
	 * <span class="de">Macht eine Stringmatrix aus der CSV Datei.</span>
	 * <span class="en">Create an array of strings from the csv document.</span>
	 * @param dateiname <span class="de">Dateiname eigene Analyse</span>
	 * <span class="en">directory of the document</span>
	 * @param splitter <span class="de">Char der dem Trennzeichen entspricht</span>
	 * <span class="en">char, which represents the splitter in the lines</span>
	 * @return <span class="de">Liste von Stringarrays</span>
	 * <span class="en">List of Stringarrays</span>
	 */
	public List<OutputStringarray> valuesToMatrix (String dateiname, char splitter) {
		List<OutputStringarray> output = new ArrayList<OutputStringarray>();
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
			OutputStringarray os = new OutputStringarray();
			os.matrix = matrix;
			os.name = name;
			output.add(os);
			return output;
			//------------------------------------------
		} catch (Exception e) {
			//System.out.println("Fehler bei der Bestimmung der Matrix hoehe und breite!");
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * <span class="de">Macht eine Stringmatrix aus der CSV Datei.</span>
	 * <span class="en">Create an array of strings from the csv document.</span>
	 * @param f <span class="de">File name eigene Analyse</span>
	 * <span class="en">file for extraction</span>
	 * @param splitter <span class="de">Char der dem Trennzeichen entspricht</span>
	 * <span class="en">char, which represents the splitter in the lines</span>
	 * @return <span class="de">Liste von Stringarrays</span>
	 * <span class="en">List of Stringarrays</span>
	 */
	public List<OutputStringarray> valuesToMatrix (File f, char splitter) {
		String dateiname = f.getPath();
		return this.valuesToMatrix(dateiname, splitter);
	}
	
	/**
	 * <span class="de">Macht eine Stringmatrix aus der CSV Datei.</span>
	 * <span class="en">Create an array of strings from the csv document.</span>
	 * @param stream <span class="de">Inputstream der eingehenden Analysedatei</span>
	 * <span class="en">InputStream of the incoming document</span>
	 * @param splitter <span class="de">Char der dem Trennzeichen entspricht</span>
	 * <span class="en">char, which represents the splitter in the lines</span>
	 * @return <span class="de">Liste von Stringarrays</span>
	 * <span class="en">List of Stringarrays</span>
	 */
	public List<OutputStringarray> valuesToMatrix (InputStream stream, char splitter) {
		File f = null;
		try {
			//Erstellt eine CSV Datei aus dem Inputstream und wertet diese aus
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
		return this.valuesToMatrix(dateiname, splitter);
	}
}
