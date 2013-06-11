package de.tudresden.deexcelerator.core;


import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;




import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import de.tudresden.deexcelerator.Configuration;
import de.tudresden.deexcelerator.data.AnalyzeInformation;
import de.tudresden.deexcelerator.data.DocumentInformation;
import de.tudresden.deexcelerator.output.OutputObject;
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
public class JsonExtraction {

	/**
	 * <span class="de">Ausgabe des JSON Codes als XLS Dokument.</span>
	 * <span class="en">Output the JSON code as a xls document.</span>
	 * @param dateiname (<span class="de">Dateiname eigene Analyse</span>
	 * <span class="en">directory of the document</span>)
	 * @return <span class="de">Wahrheitswert &uumlber Erfolg</span>
	 * <span class="en">boolean value for solution</span>
	 */
	public boolean valuesToExcel (String dateiname) {
		try {
			WritableWorkbook ueberfuehrung = Workbook.createWorkbook(new File(dateiname + ".xls"));
			WritableSheet sheet = null;
			FileIterator<String> fIter = new FileIterator<String>(dateiname);
			boolean laenge = true;
			int zaehler = 0;
			while (fIter.hasNext() && laenge) {
				String line = fIter.next();
				char[] c = line.toCharArray();
				String sheetName = "";
				boolean inBigEckig = false;
				boolean inSmallEckig = false;
				boolean inAnfZeichen = false;
				boolean anfang = true;
				String inhalt = "";
				int x = 0, y = 0; 
				List<String> metaDatenListe = new ArrayList<String>();
				String meta = "";
				for (int i = 0; i<c.length; i++)
			    {
					if (anfang) {
				    	if (c[i]=='{') {
				    		sheet = ueberfuehrung.createSheet(sheetName.trim(), zaehler);
				    	} else if (c[i]=='[' && !inAnfZeichen) {
				    		if (!inBigEckig)
				    			inBigEckig = true;
				    		else
				    			inSmallEckig = true;
				    	} else if (c[i]==']' && !inAnfZeichen) {
				    		if (inSmallEckig) {
				    			inSmallEckig = false;
				    			Label content = new Label(x, y, inhalt);
								sheet.addCell(content);
								inhalt = "";
				    			x=0;
				    			y++;
				    		} else {
				    			anfang = false;
				    			inBigEckig = false;
				    			inAnfZeichen = false;
				    		}
				    	} else if (inSmallEckig) {
				    		if (c[i]=='"' && inAnfZeichen) {
				    			inAnfZeichen = false;
				    			Label content = new Label(x, y, inhalt);
								sheet.addCell(content);
								x++;
								inhalt = "";
				    		} else if (c[i]=='"' && !inAnfZeichen) {
				    			inAnfZeichen = true;
				    		} else if (inAnfZeichen)
				    			inhalt = inhalt + c[i];			    			
				    	} else {
				    		sheetName = sheetName + c[i];
			    		}
					} else {
						//metaDaten rausloesen
						if (c[i]=='"' && inAnfZeichen) {
							if (!meta.trim().equals(""))
								metaDatenListe.add(meta);
							inAnfZeichen = false;
							meta = "";
						} else if (c[i]=='"' && !inAnfZeichen) {
							inAnfZeichen = true;
						} else if (inAnfZeichen) {
							meta = meta + c[i];
						}							
					}
		    	}
				if (zaehler >10)
					laenge = false;
				zaehler++;
			}
			ueberfuehrung.write();
			ueberfuehrung.close();
		} catch (Exception e) {
			//System.out.println("JsonAufloesung nicht moeglich!");
			e.printStackTrace();
		}
		return true;
	}
	
	/**
	 * <span class="de">Macht eine Matrix aus dem JSON Code und fuehrt diese in die Extraktionspipeline.</span>
	 * <span class="en">Create an array of strings from the json code and add it to extraction pipeline.</span>
	 * @param dateiname (<span class="de">Dateiname eigene Analyse</span>
	 * <span class="en">directory of the document</span>)
	 * @param cp (<span class="de">auch null moeglich dann setzen auf Standardwerte</span>
	 * <span class="en">configuration parameters from user or null</span>)
	 * @return <span class="de">Liste von OutputObjecten</span>
	 * <span class="en">List of OutputObjects</span>
	 */
	public List<OutputObject> valuesToMatrix (String dateiname, Configuration cp) {
		try {
			FileIterator<String> fIter = new FileIterator<String>(dateiname);
			List<OutputObject> outs = new ArrayList<OutputObject>();
			boolean laenge = true;
			int zaehler = 0;
			while (fIter.hasNext() && laenge) {
				String line = fIter.next();
				char[] c = line.toCharArray();
				String sheetName = "";
				boolean inBigEckig = false;
				boolean inSmallEckig = false;
				boolean inAnfZeichen = false;
				boolean anfang = true;
				String inhalt = "";
				int x = 0, y = 0; 
				List<String> metaDatenListe = new ArrayList<String>();
				String meta = "";
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
				anfang = true;
				x = 0; y = 0;
				for (int i = 0; i<c.length; i++)
			    {
					if (anfang) {
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
				    			anfang = false;
				    			inBigEckig = false;
				    			inAnfZeichen = false;
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
					} else {
						//metaDaten rausloesen
						if (c[i]=='"' && inAnfZeichen) {
							meta = meta.trim();
							if (!meta.equals("") && !checkString(meta)) {
								metaDatenListe.add(meta);
							}
							inAnfZeichen = false;
							meta = "";
						} else if (c[i]=='"' && !inAnfZeichen)
							inAnfZeichen = true;
						else if (inAnfZeichen)
							meta = meta + c[i];						
					}
		    	}
				//Aufruf der Extraktionspipeline
				DocumentInformation gd = new DocumentInformation(true, null, matrix, matrixName, null);
				gd.setCp(cp);
				//------------------------------------------
				AnalyzeInformation sd = new AnalyzeInformation(gd);
				sd.sheetNumber = zaehler;
				sd.metaDataTable.addAll(metaDatenListe);
				gd.getCp().onlyOneTable = true;
				TableSearch ts = new TableSearch(gd,sd);
				List<OutputObject> outs2 = ts.ruleSequence();
				if (zaehler >10)
					laenge = false;
				zaehler++;
				if (outs2 == null)
					continue;
				outs.addAll(outs2);
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
	 * <span class="de">Macht eine Matrix aus dem JSON Code und f&uumlhrt diese in die Extraktionspipeline.</span>
	 * <span class="en">Create an array of strings from the json code and add it to extraction pipeline.</span>
	 * @param code (<span class="de">JSON Code als String</span>
	 * <span class="en">json code as string</span>)
	 * @param cp (<span class="de">auch null m&oumlglich dann setzen auf Standardwerte</span>
	 * <span class="en">configuration parameters from user or null</span>)
	 * @return <span class="de">Liste von OutputObjecten</span>
	 * <span class="en">List of OutputObjects</span>
	 */
	public List<OutputObject> lineToMatrix (String code, Configuration cp) {
		String line = code;
		char[] c = line.toCharArray();
		String sheetName = "";
		boolean inBigEckig = false;
		boolean inSmallEckig = false;
		boolean inAnfZeichen = false;
		boolean anfang = true;
		String inhalt = "";
		int x = 0, y = 0; 
		List<String> metaDatenListe = new ArrayList<String>();
		String meta = "";
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
		anfang = true;
		x = 0; y = 0;
		for (int i = 0; i<c.length; i++) {
			if (anfang) {
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
		    			anfang = false;
		    			inBigEckig = false;
		    			inAnfZeichen = false;
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
			} else {
				//metaDaten rausloesen
				if (c[i]=='"' && inAnfZeichen) {
					meta = meta.trim();
					if (!meta.equals("") && !checkString(meta)) {
						metaDatenListe.add(meta);
					}
					inAnfZeichen = false;
					meta = "";
				} else if (c[i]=='"' && !inAnfZeichen)
					inAnfZeichen = true;
				else if (inAnfZeichen)
					meta = meta + c[i];						
			}
		}
		//Aufruf der Extraktionspipeline
		DocumentInformation gd = new DocumentInformation(true, null, matrix, matrixName, null);
		gd.setCp(cp);
		//------------------------------------------
		AnalyzeInformation sd = new AnalyzeInformation(gd);
		sd.metaDataTable.addAll(metaDatenListe);
		gd.getCp().onlyOneTable = true;
		TableSearch ts = new TableSearch(gd,sd);
		return ts.ruleSequence();
	}
	
	/**
	 * <span class="de">Macht eine Matrix aus dem JSON Code und f&uumlhrt diese in die Extraktionspipeline. (Variante 2)</span>
	 * <span class="en">Create an array of strings from the json code and add it to extraction pipeline. (option 2)</span>
	 * @param dateiname (<span class="de">Dateiname eigene Analyse</span>
	 * <span class="en">directory of the document</span>)
	 * @param cp (<span class="de">auch null m&oumlglich dann setzen auf Standardwerte</span>
	 * <span class="en">configuration parameters from user or null</span>)
	 * @return <span class="de">Liste von OutputObjecten</span>
	 * <span class="en">List of OutputObjects</span>
	 */
	public List<OutputObject> valuesToMatrixSecond (String dateiname, Configuration cp) {
		try {
			List<OutputObject> outs = new ArrayList<OutputObject>();
			FileIterator<String> fIter = new FileIterator<String>(dateiname);
			boolean laenge = true;
			int zaehler = 0;
			while (fIter.hasNext() && laenge) {
				String line = fIter.next();
				char[] c = line.toCharArray();
				String identifikation = "";
				String inhalt = "";
				List<String> metaDatenListe = new ArrayList<String>();
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
				String[][] matrix = er.getRelation();
				String meta = er.getTitle();
				if (meta != null && !meta.trim().equals(""))
					metaDatenListe.add(meta);
				meta = er.getContextAfter();
				if (meta != null && !meta.trim().equals(""))
					metaDatenListe.add(meta);
				meta = er.getContextBefore();
				if (meta != null && !meta.trim().equals(""))
					metaDatenListe.add(meta);
				meta = er.getMetadata();
				if (meta != null && !meta.trim().equals(""))
					metaDatenListe.add(meta);
				meta = er.getUrl();
				if (meta != null && !meta.trim().equals(""))
					metaDatenListe.add(meta);
				//Aufruf der Extraktionspipeline
				DocumentInformation gd = new DocumentInformation(true, null, matrix, identifikation, null);
				gd.setCp(cp);
				//------------------------------------------
				AnalyzeInformation sd = new AnalyzeInformation(gd);
				sd.sheetNumber = zaehler;
				sd.metaDataTable.addAll(metaDatenListe);
				if (zaehler >10)
					laenge = false;
				zaehler++;
				gd.getCp().onlyOneTable = true;
				TableSearch ts = new TableSearch(gd,sd);
				List<OutputObject> outs2 = ts.ruleSequence();
				if (outs2 == null)
					continue;
				outs.addAll(outs2);
				//------------------------------------------
			}
			return outs;
		} catch (Exception e) {
			//System.out.println("Matrix erstellung aus JSON2 nicht moeglich!");
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * <span class="de">Macht eine Matrix aus dem JSON Code und f&uumlhrt diese in die Extraktionspipeline. (Variante 2)</span>
	 * <span class="en">Create an array of strings from the json code and add it to extraction pipeline. (option 2)</span>
	 * @param code (<span class="de">JSON Code als String</span>
	 * <span class="en">json code as string</span>)
	 * @param cp (<span class="de">auch null m&oumlglich dann setzen auf Standardwerte</span>
	 * <span class="en">configuration parameters from user or null</span>)
	 * @return <span class="de">Liste von OutputObjecten</span>
	 * <span class="en">List of OutputObjects</span>
	 */
	public List<OutputObject> lineToMatrixSecond (String code, Configuration cp) {
		String line = code;
		char[] c = line.toCharArray();
		String identifikation = "";
		String inhalt = "";
		List<String> metaDatenListe = new ArrayList<String>();
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
		String[][] matrix = er.getRelation();
		String meta = er.getTitle();
		if (meta != null && !meta.trim().equals(""))
			metaDatenListe.add(meta);
		meta = er.getContextAfter();
		if (meta != null && !meta.trim().equals(""))
			metaDatenListe.add(meta);
		meta = er.getContextBefore();
		if (meta != null && !meta.trim().equals(""))
			metaDatenListe.add(meta);
		meta = er.getMetadata();
		if (meta != null && !meta.trim().equals(""))
			metaDatenListe.add(meta);
		meta = er.getUrl();
		if (meta != null && !meta.trim().equals(""))
			metaDatenListe.add(meta);
		//Aufruf der Extraktionspipeline
		DocumentInformation gd = new DocumentInformation(true, null, matrix, identifikation, null);
		gd.setCp(cp);
		//------------------------------------------
		AnalyzeInformation sd = new AnalyzeInformation(gd);
		sd.metaDataTable.addAll(metaDatenListe);
		gd.getCp().onlyOneTable = true;
		TableSearch ts = new TableSearch(gd,sd);
		return ts.ruleSequence();
	}
	
	/**
	 * <span class="de">&Uumlberpr&uumlft String auf Bestimmungsw&oumlrter.</span>
	 * <span class="en">Proof the string for information words.</span>
	 * @param toCheck (<span class="de">einzulesender String</span>
	 * <span class="en">readed string</span>)
	 * @return <span class="de">Wahrheitswert, ob es darunter ist oder nicht</span>
	 * <span class="en">boolean if it is on or not</span>
	 */
	private boolean checkString (String toCheck) {
		return (toCheck.equals("metadata") || toCheck.equals("url") || toCheck.equals("title") || toCheck.equals("contextBefore") || toCheck.equals("contextAfter"));
	}
	
	/**
	 * <span class="de">Macht eine Matrix aus dem JSON Code und f&uumlhrt diese in die Extraktionspipeline. (Variante 2)</span>
	 * <span class="en">Create an array of strings from the json code and add it to extraction pipeline. (option 2)</span>
	 * @param stream (<span class="de">Inputstream der eingehenden Datei</span>
	 * <span class="en">InputStream of the incoming document</span>)
	 * @param cp (<span class="de">auch null m&oumlglich dann setzen auf Standardwerte</span>
	 * <span class="en">configuration parameters from user or null</span>)
	 * @return <span class="de">Liste von OutputObjecten</span>
	 * <span class="en">List of OutputObjects</span>
	 */
	public List<OutputObject> valuesToMatrixSecond (InputStream stream, Configuration cp) {
		File f = null;
		try {
			//Erstellen einer Datei um diese Dann der Extraktionspipeline hinzuzufügen
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
		return this.valuesToMatrixSecond(dateiname, cp);
	}

	/**
	 * <span class="de">Macht eine Matrix aus dem JSON Code und f&uumlhrt diese in die Extraktionspipeline.</span>
	 * <span class="en">Create an array of strings from the json code and add it to extraction pipeline.</span>
	 * @param stream (<span class="de">Inputstream der eingehenden Datei</span>
	 * <span class="en">InputStream of the incoming document</span>)
	 * @param cp (<span class="de">auch null m&oumlglich dann setzen auf Standardwerte</span>
	 * <span class="en">configuration parameters from user or null</span>)
	 * @return <span class="de">Liste von OutputObjecten</span>
	 * <span class="en">List of OutputObjects</span>
	 */
	public List<OutputObject> valuesToMatrix (InputStream stream, Configuration cp) {
		File f = null;
		try {
			//Erstellen einer Datei um diese Dann der Extraktionspipeline hinzuzufügen
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
		return this.valuesToMatrix(dateiname, cp);
	}
}
