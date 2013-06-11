package de.tudresden.deexcelerator.stringarray;


import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import de.tudresden.deexcelerator.util.ExtractionResult;
import de.tudresden.deexcelerator.util.FileIterator;

/**
 * <span class="de">F&uumlhrt alle Umwandlungen an einem JSON Code durch.
 * Einleiten der JSON Codes in die Extraktionspipeline.</span>
 * <span class="en">Make all changes in JSON code an add the
 * solution in the extraction pipeline.</span>
 * @author Christopher Werner
 *
 */
public class JsonStringarray {
	
	/**
	 * <span class="de">Erstellt eine String Matrix aus dem JSON Code.</span>
	 * <span class="en">Create an array of strings from the json code.</span>
	 * @param dateiname (<span class="de">Dateiname eigene Analyse</span>
	 * <span class="en">directory of the document</span>)
	 * @return <span class="de">Liste von Stringarrays</span>
	 * <span class="en">List of Stringarrays</span>
	 */
	public List<OutputStringarray> valuesToMatrix (String dateiname) {
		try {
			FileIterator<String> fIter = new FileIterator<String>(dateiname);
			List<OutputStringarray> outs = new ArrayList<OutputStringarray>();
			boolean laenge = true;
			int zaehler = 0;
			while (fIter.hasNext() && laenge) {
				String line = fIter.next();
				char[] c = line.toCharArray();
				String sheetName = "";
				boolean inBigEckig = false;
				boolean inSmallEckig = false;
				boolean inAnfZeichen = false;
				String inhalt = "";
				int x = 0, y = 0; 
				int breiteEnde = 0;
				for (int i = 0; i<c.length; i++)
			    {
				   	if (c[i]=='[' && !inAnfZeichen) {
				   		if (!inBigEckig)
				   			inBigEckig = true;
				   		else
				   			inSmallEckig = true;
				   	} else if (c[i]==']' && !inAnfZeichen) {
				   		if (inSmallEckig) {
				   			inSmallEckig = false;
				   			if (x>breiteEnde)
				   				breiteEnde = x;
				   			x=0;
				   			y++;
				   		} else {
				   			inBigEckig = false;
				   			inAnfZeichen = false;
				   		}
				   	} else if (inSmallEckig) {
				   		if (c[i]=='"' && inAnfZeichen) {
				   			inAnfZeichen = false;
							x++;
				   		} else if (c[i]=='"' && !inAnfZeichen) {
				   			inAnfZeichen = true;
				   		}			    			
				   	}
		    	}
				
				String[][] matrix = new String[breiteEnde][y];
				String matrixName = "";
				inBigEckig = false;
				inSmallEckig = false;
				inAnfZeichen = false;
				x = 0; y = 0;
				for (int i = 0; i<c.length; i++)
			    {
					if (c[i]=='{' && !inAnfZeichen) {
				    	matrixName = sheetName.trim();
				    } else if (c[i]=='[' && !inAnfZeichen) {
				    	if (!inBigEckig)
				    		inBigEckig = true;
				    	else
				    		inSmallEckig = true;
				    } else if (c[i]==']' && !inAnfZeichen) {
				    	if (inSmallEckig) {
				    		inSmallEckig = false;
				    		x=0;
				    		y++;
				    	} else {
				    		break;
				    	}
				    } else if (inSmallEckig) {
				    	if (c[i]=='"' && inAnfZeichen) {
				    		inAnfZeichen = false;
				    		matrix[x][y] = inhalt;
				    		x++;
							inhalt = "";
				    	} else if (c[i]=='"' && !inAnfZeichen) {
				    		inAnfZeichen = true;
				    	} else if (inAnfZeichen)
				    		inhalt = inhalt + c[i];			    			
				    } else {
				    	sheetName = sheetName + c[i];
			    	}
		    	}
				if (zaehler >10)
					laenge = false;
				zaehler++;
				//Aufruf der Extraktionspipeline
				OutputStringarray os = new OutputStringarray();
				os.name = matrixName;
				os.matrix = matrix;
				outs.add(os);
				//------------------------------------------
			}
			return outs;
		} catch (Exception e) {
			//System.out.println("Matrix erstellung aus JSON1 nicht moeglich!");
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * <span class="de">Erstellt eine String Matrix aus dem JSON Code.</span>
	 * <span class="en">Create an array of strings from the json code.</span>
	 * @param code (<span class="de">JSON Code als String</span>
	 * <span class="en">json code as string</span>)
	 * @return <span class="de">Liste von Stringarrays</span>
	 * <span class="en">List of Stringarrays</span>
	 */
	public List<OutputStringarray> lineToMatrix (String code) {
		String line = code;
		char[] c = line.toCharArray();
		String sheetName = "";
		boolean inBigEckig = false;
		boolean inSmallEckig = false;
		boolean inAnfZeichen = false;
		String inhalt = "";
		int x = 0, y = 0; 
		int breiteEnde = 0;
		for (int i = 0; i<c.length; i++) {
		   	if (c[i]=='[' && !inAnfZeichen) {
		   		if (!inBigEckig)
		   			inBigEckig = true;
		   		else
		   			inSmallEckig = true;
		   	} else if (c[i]==']' && !inAnfZeichen) {
		   		if (inSmallEckig) {
		   			inSmallEckig = false;
		   			if (x>breiteEnde)
		   				breiteEnde = x;
		   			x=0;
		   			y++;
		   		} else {
		   			inBigEckig = false;
		   			inAnfZeichen = false;
		   		}
		   	} else if (inSmallEckig) {
		   		if (c[i]=='"' && inAnfZeichen) {
		   			inAnfZeichen = false;
					x++;
		   		} else if (c[i]=='"' && !inAnfZeichen) {
		   			inAnfZeichen = true;
		   		}			    			
		   	}
		}
		String[][] matrix = new String[breiteEnde][y];
		String matrixName = "";
		inBigEckig = false;
		inSmallEckig = false;
		inAnfZeichen = false;
		x = 0; y = 0;
		for (int i = 0; i<c.length; i++) {
			if (c[i]=='{' && !inAnfZeichen) {
		    	matrixName = sheetName.trim();
		    } else if (c[i]=='[' && !inAnfZeichen) {
		    	if (!inBigEckig)
		    		inBigEckig = true;
		    	else
		    		inSmallEckig = true;
		    } else if (c[i]==']' && !inAnfZeichen) {
		    	if (inSmallEckig) {
		    		inSmallEckig = false;
		    		x=0;
		    		y++;
		    	} else
		    		break;
		    } else if (inSmallEckig) {
		    	if (c[i]=='"' && inAnfZeichen) {
		    		inAnfZeichen = false;
		    		matrix[x][y] = inhalt;
		    		x++;
					inhalt = "";
		    	} else if (c[i]=='"' && !inAnfZeichen) {
		    		inAnfZeichen = true;
		    	} else if (inAnfZeichen)
		    		inhalt = inhalt + c[i];			    			
		    } else {
		    	sheetName = sheetName + c[i];
		   	}
		}
		//Rückgabe des Stringarrays
		OutputStringarray os = new OutputStringarray();
		os.matrix = matrix;
		os.name = matrixName;		
		List<OutputStringarray> outs = new ArrayList<OutputStringarray>();
		outs.add(os);
		return outs;
	}
	
	/**
	 * <span class="de">Erstellt eine String Matrix aus dem JSON Code. (Variante 2)</span>
	 * <span class="en">Create an array of strings from the json code. (Option 2)</span>
	 * @param dateiname (<span class="de">Dateiname eigene Analyse</span>
	 * <span class="en">directory of the document</span>)
	 * @return <span class="de">Liste von Stringarrays</span>
	 * <span class="en">List of Stringarrays</span>
	 */
	public List<OutputStringarray> valuesToMatrixSecond (String dateiname) {
		try {
			List<OutputStringarray> outs = new ArrayList<OutputStringarray>();
			FileIterator<String> fIter = new FileIterator<String>(dateiname);
			boolean laenge = true;
			int zaehler = 0;
			while (fIter.hasNext() && laenge) {
				String line = fIter.next();
				char[] c = line.toCharArray();
				String identifikation = "";
				String inhalt = "";
				boolean inContent = false;
				for (int i = 0; i<c.length; i++) {
					if (inContent)
						inhalt = inhalt + c[i];
					else if (!inContent && c[i]=='{') {
						inhalt = inhalt + c[i];
						inContent = true;
					} else
						identifikation = identifikation + c[i];
				}
				identifikation = identifikation.trim();
				ExtractionResult er = new ExtractionResult();
				er = er.fromJson(inhalt);
				if (zaehler >10)
					laenge = false;
				zaehler++;
				//Rueckgabe des Stringarrays
				OutputStringarray os = new OutputStringarray();
				os.matrix = er.getRelation();
				os.name = identifikation;
				outs.add(os);
			}
			return outs;
		} catch (Exception e) {
			//System.out.println("Matrix erstellung aus JSON2 nicht moeglich!");
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * <span class="de">Erstellt eine String Matrix aus dem JSON Code. (Variante 2)</span>
	 * <span class="en">Create an array of strings from the json code. (Option 2)</span>
	 * @param code (<span class="de">JSON Code als String</span>
	 * <span class="en">json code as string</span>)
	 * @return <span class="de">Liste von Stringarrays</span>
	 * <span class="en">List of Stringarrays</span>
	 */
	public List<OutputStringarray> lineToMatrixSecond (String code) {
		String line = code;
		char[] c = line.toCharArray();
		String identifikation = "";
		String inhalt = "";
		boolean inContent = false;
		for (int i = 0; i<c.length; i++) {
			if (inContent)
				inhalt = inhalt + c[i];
			else if (!inContent && c[i]=='{') {
				inhalt = inhalt + c[i];
				inContent = true;
			} else
				identifikation = identifikation + c[i];
		}
		identifikation = identifikation.trim();
		ExtractionResult er = new ExtractionResult();
		er = er.fromJson(inhalt);
		//Rückgabe des Stringarrays
		OutputStringarray os = new OutputStringarray();
		os.matrix = er.getRelation();
		os.name = identifikation;		
		List<OutputStringarray> outs = new ArrayList<OutputStringarray>();
		outs.add(os);
		return outs;
	}
	
	/**
	 * <span class="de">Erstellt eine String Matrix aus dem JSON Code. (Variante 2)</span>
	 * <span class="en">Create an array of strings from the json code. (Option 2)</span>
	 * @param stream (<span class="de">Inputstream der eingehenden Datei</span>
	 * <span class="en">InputStream of the incoming document</span>)
	 * @return <span class="de">Liste von Stringarrays</span>
	 * <span class="en">List of Stringarrays</span>
	 */
	public List<OutputStringarray> valuesToMatrixSecond (InputStream stream) {
		File f = null;
		try {
			//Erstellt File um es dann der Extraktionspipelin hinzuzufügen
			f=new File("outFile");
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
		return this.valuesToMatrixSecond(dateiname);
	}

	/**
	 * <span class="de">Erstellt eine String Matrix aus dem JSON Code.</span>
	 * <span class="en">Create an array of strings from the json code.</span>
	 * @param stream (<span class="de">Inputstream der eingehenden Datei</span>
	 * <span class="en">InputStream of the incoming document</span>)
	 * @return <span class="de">Liste von Stringarrays</span>
	 * <span class="en">List of Stringarrays</span>
	 */
	public List<OutputStringarray> valuesToMatrix (InputStream stream) {
		File f = null;
		try {
			//Erstellt File um es dann der Extraktionspipelin hinzuzufügen
			f=new File("outFile");
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
		return this.valuesToMatrix(dateiname);
	}
}
