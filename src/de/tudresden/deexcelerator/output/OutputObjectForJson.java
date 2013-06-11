package de.tudresden.deexcelerator.output;

import com.google.gson.Gson;

import java.util.List;

import de.tudresden.deexcelerator.data.Header;

/**
 * <span class="de">OutputObject zur Nutzung und Bearbeitung der Ergebnisse der Extraktionspipeline.</span>
 * <span class="en">OutputObject for the solution of the extraction pipeline.</span>
 * @author Christopher Werner
 *
 */
public class OutputObjectForJson {
	
	/** <span class="de">GSON Objekt zur Transformation.</span>
	 * <span class="en">GSON object for transformation.</span> */    
	private static final Gson gson = new Gson();

	/** <span class="de">Relation als Stringarray.</span>
	 * <span class="en">Relation as string array.</span> */
    public String[][] matrix;
    /** <span class="de">Map der extrahierten Headerinformationen.</span>
     * <span class="en">Map of the extracted header information.</span> */
    public Header[] headerInfo;
    /** <span class="de">Liste der extrahierten Metadaten.</span>
     * <span class="en">List of meta data out of the table.</span> */
    public List<String> metaDaten;
    /** <span class="de">Erkannter Relationsname.</span>
     * <span class="en">Extracted relation name.</span> */
    public String relationName;
	
    /**
     * <span class="de">Konstruktor zum ausf&uumlhren der Action Methode.</span>
     * <span class="en">Constructor which open the action method.</span>
     * @param matrix (<span class="de">DocumentInformation</span>
     * <span class="en">document information</span>)
     * @param headerInfo (<span class="de">AnalyseInformation</span>
     * <span class="en">analyze information</span>)
     * @param metaDaten (<span class="de">DocumentInformation</span>
     * <span class="en">document information</span>)
     * @param relationName (<span class="de">DocumentInformation</span>
     * <span class="en">document information</span>)
     */
	public OutputObjectForJson (String[][] matrix, Header[] headerInfo, List<String> metaDaten, String relationName) {
		this.matrix = matrix;
		this.headerInfo = headerInfo;
		this.metaDaten = metaDaten;
		this.relationName = relationName;
	}
	
	/**
	 * <span class="de">Wandelt einen JSON String in ein Objekt dieser Klasse um.</span>
	 * <span class="en">Creat an object of this class out of a json representation.</span>
     * @param json (<span class="de">JSON String</span>
     * <span class="en">JSON string</span>)
     * @return <span class="de">Objekt dieser Klasse.</span>
     * <span class="en">object of this class</span>
	 */
	public static OutputObjectForJson fromJson(String json) {
        return gson.fromJson(json, OutputObjectForJson.class);
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
}
