package de.tudresden.deexcelerator.output;

import com.google.gson.Gson;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


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
public class OutputObjectObj {
	
	/** <span class="de">GSON Objekt zur Transformation.</span>
	 * <span class="en">GSON object for transformation.</span> */    
	private static final Gson gson = new Gson();

	/** <span class="de">Relation als Stringarray.</span>
	 * <span class="en">Relation as string array.</span> */
    private Object[][] matrix;
    /** <span class="de">Map der extrahierten Headerinformationen.</span>
     * <span class="en">Map of the extracted header information.</span> */
    private Header[] headerInfo;
    /** <span class="de">Liste der extrahierten Metadaten.</span>
     * <span class="en">List of meta data out of the table.</span> */
    private List<String> metaDaten;
    /** <span class="de">Erkannter Relationsname.</span>
     * <span class="en">Extracted relation name.</span> */
    private String relationName;
	
    /**
     * <span class="de">Konstruktor zum ausf&uumlhren der Action Methode.</span>
     * <span class="en">Constructor which open the action method.</span>
     * @param gd (<span class="de">DocumentInformation</span>
     * <span class="en">document information</span>)
     * @param sd (<span class="de">AnalyseInformation</span>
     * <span class="en">analyze information</span>)
     */
	public OutputObjectObj (DocumentInformation gd, AnalyzeInformation sd) {
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
	@SuppressWarnings("unchecked")
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
		Map<String,String>[] headerInfoHelp = new Map[zaehlerSpalten];
		
		
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
				Map<String,String> map = new HashMap<String,String>();
				for (int h = 0; h<sd.colInfos.size(); h++)
	    			if (sd.colInfos.get(h).getSpaltenNummer() == x) {
	    				c = sd.colInfos.get(h);
	    				int type = sd.colInfos.get(h).getFormat();
	    				if (type == 1) {
	    					//double
	    					map.put("type", "double");
	    				} else if (type == 2) {
	    					//int
	    					map.put("type", "integer");	
	    				} else if (type == 3) {
	    					//date
	    					map.put("type", "date");
	    				} else if (type == 4) {
	    					//string
	    					map.put("type", "varchar");
	    					map.put("size", sd.colInfos.get(h).getLenght()+"");
	    				}
	    			}
				if (attribut.trim().equals("")) {
					attribut = "Attribut_" + zaehler;
					zaehler++;
				} else
					attribut = gd.getSf().stringChangerUnterstrich(attribut.trim());
				Header h = new Header(attribut, c.getH().getLength(), c.getH().getType(), c.getH().getConvertTo());
				headerInfo[spalte] = h;
				headerInfoHelp[spalte] = map;
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
						Map<String,String> map = headerInfoHelp[zaehlerSpalten];
						if (map.get("type").equals("varchar"))
						{
							String inhalt = gd.getFc().contentCell(x, y);
							if (gd.getFc().emptyCell(x, y) || x == sd.colStart)
								for (Iterator<SplittedCells> iter = sd.mergeCells.iterator(); iter.hasNext();) {
									SplittedCells sz = iter.next();
									if (x<=sz.getX()+sz.getXrange() && x>=sz.getX() && y<=sz.getY()+sz.getYrange() && y>=sz.getY())
										inhalt = sz.getContent();
								}
							matrix[zaehlerSpalten][zaehlerZeilen] = inhalt;
						}
						else if (map.get("type").equals("date"))
						{
							matrix[zaehlerSpalten][zaehlerZeilen] = gd.getFc().getDateValue(x, y);
						}
						else if (map.get("type").equals("integer"))
						{
							matrix[zaehlerSpalten][zaehlerZeilen] = gd.getFc().getIntegerValue(x, y);
						}
						else if (map.get("type").equals("double"))
						{
							matrix[zaehlerSpalten][zaehlerZeilen] = gd.getFc().getDoubleValue(x, y);
						}
						zaehlerZeilen++;
					}
				}
				zaehlerZeilen=0;
				zaehlerSpalten++;
			}
		}
		this.metaDaten = sd.metaDataTable;
	}
	
	/**
	 * <span class="de">Wandelt einen JSON String in ein Objekt dieser Klasse um.</span>
	 * <span class="en">Creat an object of this class out of a json representation.</span>
     * @param json (<span class="de">JSON String</span>
     * <span class="en">JSON string</span>)
     * @return <span class="de">Objekt dieser Klasse.</span>
     * <span class="en">object of this class</span>
	 */
	public static OutputObjectObj fromJson(String json) {
        return gson.fromJson(json, OutputObjectObj.class);
    }
		
	/**
	 * <span class="de">Erzeugt einen JSON String aus dieser Klasse.</span>
	 * <span class="en">Creates a json string from this class.</span>
	 * @return <span class="de">JSON String</span>
	 * <span class="en">json string</span>
	 */
    public String toJson() {
        return gson.toJson(this);
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
		}
    }
    
	public List<String> getMetaDaten() {
		return metaDaten;
	}	

	public Object[][] getMatrix() {
		return matrix;
	}

	public Header[] getHeaderInfo() {
		return headerInfo;
	}
	
	public String getTableName() {
		return relationName;
	}

}
