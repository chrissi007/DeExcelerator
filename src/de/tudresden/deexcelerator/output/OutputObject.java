package de.tudresden.deexcelerator.output;

import com.google.gson.Gson;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import de.tudresden.deexcelerator.data.AnalyzeInformation;
import de.tudresden.deexcelerator.data.DocumentInformation;
import de.tudresden.deexcelerator.data.Header;
import de.tudresden.deexcelerator.util.ColInformation;
import de.tudresden.deexcelerator.util.HeaderEntry;
import de.tudresden.deexcelerator.util.SplittedCells;

/**
 * <span class="de">OutputObject zur Nutzung und Bearbeitung der Ergebnisse der Extraktionspipeline.</span>
 * <span class="en">OutputObject for the solution of the extraction pipeline.</span>
 * @author Christopher Werner
 *
 */
public class OutputObject {
	
	/** <span class="de">GSON Objekt zur Transformation.</span>
	 * <span class="en">GSON object for transformation.</span> */    
	private static final Gson gson = new Gson();

	/** <span class="de">Relation als Stringarray.</span>
	 * <span class="en">Relation as string array.</span> */
    private String[][] matrix;
    /** <span class="de">Map der extrahierten Headerinformationen.</span>
     * <span class="en">Map of the extracted header information.</span> */
    private Header[] headerInfo;
    /** <span class="de">Liste der extrahierten Metadaten.</span>
     * <span class="en">List of meta data out of the table.</span> */
    private List<String> metaDaten;
    /** <span class="de">Erkannter Relationsname.</span>
     * <span class="en">Extracted relation name.</span> */
    private String relationName;
    
    public DocumentInformation gd;
    public AnalyzeInformation sd;
	
    /**
     * <span class="de">Konstruktor zum ausf&uumlhren der Action Methode.</span>
     * <span class="en">Constructor which open the action method.</span>
     * @param gd (<span class="de">DocumentInformation</span>
     * <span class="en">document information</span>)
     * @param sd (<span class="de">AnalyseInformation</span>
     * <span class="en">analyze information</span>)
     */
	public OutputObject (DocumentInformation gd, AnalyzeInformation sd) {
		this.gd = gd;
		this.sd = sd;
		this.action(gd, sd);
	}
	
	/**
	 * <span class="de">Festlegen der Extraktionsergebnisse.
	 * Analyse Informationen auswerten und Variablen belegen.</span>
	 * <span class="en">Set the extraction results and fill
	 * the attributes with the new relation informations.</span>
	 * @param gd (<span class="de">DocumentInformation</span>
     * <span class="en">document information</span>)
     * @param sd (<span class="de">AnalyseInformation</span>
     * <span class="en">analyze information</span>)
	 */
	private void action(DocumentInformation gd, AnalyzeInformation sd){
		
		relationName = sd.relationName;
		int zaehlerZeilen = 0;
		int zaehlerSpalten = 0;
		for (int x=sd.colStart; x<sd.colEnd; x++) {
			if (!sd.emptyCols.contains(x)) {
				zaehlerZeilen=0;
				for (int y=sd.headerFinish; y<sd.rowEnd; y++)
					if (!sd.emptyRows.contains(y))
						zaehlerZeilen++;
				zaehlerSpalten++;
			}
		}
		matrix = new String[zaehlerSpalten][zaehlerZeilen];
		headerInfo = new Header[zaehlerSpalten];
		
		List<Integer> leerBeachten = new ArrayList<Integer>();
		//----------------------------------------
		for (Iterator<HeaderEntry> iter = sd.attributeList.iterator(); iter.hasNext();) {
			HeaderEntry test1 = iter.next();
			if (sd.emptyCols.contains(test1.getSpalte()))
				leerBeachten.add(test1.getSpalte());
		}
		//----------------------------------------
		
		int zaehler = 0;
		int spalte = 0;
		for (int x=sd.colStart; x<sd.colEnd; x++)
		{
			if (!sd.emptyCols.contains(x)) {
				String attribut = "";
				for (Iterator<HeaderEntry> iter = sd.attributeList.iterator(); iter.hasNext();) {
					HeaderEntry test1 = iter.next();
					if (test1.getSpalte() == x)
						attribut = test1.getAttribut();
				}
				ColInformation c = null;
				for (int h = 0; h<sd.colInfos.size(); h++)
	    			if (sd.colInfos.get(h).getSpaltenNummer() == x) {
	    				c = sd.colInfos.get(h);
	    			}
				if (attribut.trim().equals("")) {
					attribut = "Attribut_" + zaehler;
					zaehler++;
				} else
					attribut = gd.getSf().stringChangerUnterstrich(attribut.trim());
				Header h = new Header(attribut, c.getH().getLength(), c.getH().getType(), c.getH().getConvertTo());
				headerInfo[spalte] = h;
				spalte++;
			}
		}
		zaehlerZeilen = 0;
		zaehlerSpalten = 0;
		for (int x=sd.colStart; x<sd.colEnd; x++)
		{
			if (!sd.emptyCols.contains(x)) {
				for (int y=sd.headerFinish; y<sd.rowEnd; y++)
				{
					if (!sd.emptyRows.contains(y))
					{
						int type = headerInfo[zaehlerSpalten].getType().Specificity;
						String inhalt = null;
						if (type == 4 && gd.getFc().numberCell(x, y)) {
							Double d = gd.getFc().getDoubleValue(x, y);
							if (d != null)
								inhalt = d.toString();
						} else if ((type == 6 || type == 7) && gd.getFc().numberCell(x, y)) {
							Integer intValue = gd.getFc().getIntegerValue(x, y);
							if (intValue != null)
								inhalt = intValue.toString();
						} else if (type == 3) {
							Date datum = gd.getFc().getDateValue(x, y);
							if (datum != null)
								inhalt = datum.toString();
						} else {							
							inhalt = gd.getFc().contentCell(x, y);
							if (gd.getFc().emptyCell(x, y) || x == sd.colStart)
								for (Iterator<SplittedCells> iter = sd.mergeCells.iterator(); iter.hasNext();) {
									SplittedCells sz = iter.next();
									if (x<=sz.getX()+sz.getXrange() && x>=sz.getX() && y<=sz.getY()+sz.getYrange() && y>=sz.getY())
										inhalt = sz.getContent();
								}
						}
						if (headerInfo[zaehlerSpalten].getConvertTo() != null && (inhalt == null || inhalt.trim().equals(""))) {
							int contype = headerInfo[zaehlerSpalten].getConvertTo().Specificity;
							if (contype == 4 && gd.getFc().numberCell(x, y)) {
								Double d = gd.getFc().getDoubleValue(x, y);
								if (d != null)
									inhalt = d.toString();
							} else if ((contype == 6 || contype == 7) && gd.getFc().numberCell(x, y)) {
								Integer intValue = gd.getFc().getIntegerValue(x, y);
								if (intValue != null)
									inhalt = intValue.toString();
							} else if (contype == 3) {
								Date datum = gd.getFc().getDateValue(x, y);
								if (datum != null)
									inhalt = datum.toString();
							} else {							
								inhalt = gd.getFc().contentCell(x, y);
								if (gd.getFc().emptyCell(x, y) || x == sd.colStart)
									for (Iterator<SplittedCells> iter = sd.mergeCells.iterator(); iter.hasNext();) {
										SplittedCells sz = iter.next();
										if (x<=sz.getX()+sz.getXrange() && x>=sz.getX() && y<=sz.getY()+sz.getYrange() && y>=sz.getY())
											inhalt = sz.getContent();
									}
							}
						}
						if (inhalt == null)
							inhalt = "";
						matrix[zaehlerSpalten][zaehlerZeilen] = inhalt;
						zaehlerZeilen++;
					}
				}
				zaehlerZeilen=0;
				zaehlerSpalten++;
			}
		}
		this.metaDaten = new ArrayList<String>();
		for (int i = 0; i < sd.metaDataTable.size(); i++)
			if (sd.metaDataTable.get(i) != null && !sd.metaDataTable.get(i).trim().equals(""))
				this.metaDaten.add(sd.metaDataTable.get(i).trim());
	}
	
	/**
	 * <span class="de">Wandelt einen JSON String in ein Objekt dieser Klasse um.</span>
	 * <span class="en">Creat an object of this class out of a json representation.</span>
     * @param json (<span class="de">JSON String</span>
     * <span class="en">JSON string</span>)
     * @return <span class="de">Objekt dieser Klasse.</span>
     * <span class="en">object of this class</span>
	 */
	public static OutputObject fromJson(String json) {
        return gson.fromJson(json, OutputObject.class);
    }
		
	/**
	 * <span class="de">Erzeugt einen JSON String aus dieser Klasse.</span>
	 * <span class="en">Creates a json string from this class.</span>
	 * @return <span class="de">JSON String</span>
	 * <span class="en">json string</span>
	 */
    public String toJson() {
    	OutputObjectForJson oj = new OutputObjectForJson(matrix, headerInfo, metaDaten, relationName);
        return oj.toJson();
    }

    /**
     * <span class="de">Schreiben eines neuen Textfiles.</span>
     * <span class="en">Writes a new txt file.</span>
     * @param inhalt (<span class="de">Inhalt des Textfiles</span>
     * <span class="en">content of the text file</span>)
     */
    public void neuesFile (String inhalt) {
        String dateiName = "Test.txt";
        FileOutputStream schreibeStrom;
		try {
			schreibeStrom = new FileOutputStream(dateiName);
	        for (int i=0; i < inhalt.length(); i++){
	          schreibeStrom.write((byte)inhalt.charAt(i));
	        }
	        schreibeStrom.close();
		} catch (Exception e) {
			e.printStackTrace();
			//System.out.println("Ausgabe nicht geklappt!");
		}
    }
    
	public List<String> getMetaDaten() {
		return metaDaten;
	}	

	public String[][] getMatrix() {
		return matrix;
	}

	public Header[] getHeaderInfo() {
		return headerInfo;
	}
	
	public String getTableName() {
		return relationName;
	}
	
	public OutputObject getOutputObject() {
		if (matrix.length == 0)
			return null;
		else if (matrix[0].length == 0)
			return null;
		return this;
	}

}
