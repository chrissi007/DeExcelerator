package de.tudresden.deexcelerator.util;

import com.google.gson.Gson;

/**
 * <span class="de">Format aus dem das JSON exportiert wurde.</span>
 * <span class="en">Class for creating the json document.</span>
 * @author Christopher Werner
 *
 */
public class ExtractionResult {

	/** <span class="de">GSON Objekt zur Transformation.</span>
	 * <span class="en">GSON object for transformation.</span> */
    private static final Gson gson = new Gson();

    /** <span class="de">Relation als Stringarray.</span>
     * <span class="en">Relation as string array.</span> */
    private String[][] relation = null;
    /** <span class="de">Metadatenstring.</span>
     * <span class="en">Metadata string.</span> */
    private String metadata = "";
    /** <span class="de">URLstring.</span>
     * <span class="en">URL string</span> */
    private String url = "";
    /** <span class="de">Titel der Relation als String.</span>
     * <span class="en">Title of the relation.</span> */
    private String title = "";
    /** <span class="de">Beforecontext der Relation als String.</span>
     * <span class="en">Beforecontext of the relation.</span> */
    private String contextBefore = "";
    /** <span class="de">Aftercontext der Relation als String.</span>
     * <span class="en">Aftercontext of the relation.</span> */
    private String contextAfter = "";

    /**
     * <span class="de">Konstruktor.</span>
     * <span class="en">constructor</span>
     */
	public ExtractionResult() {}

	/**
	 * <span class="de">Konstruktor mit festlegen der Metadaten und der Relation.</span>
	 * <span class="en">Constructor with the metadata and the relation.</span>
	 * @param relation (<span class="de">Stringarray der Relation</span>
	 * <span class="en">string array as relation</span>)
	 * @param metadata (<span class="de">String von Metadaten</span>
	 * <span class="en">metadata as a string</span>)
	 */
	public ExtractionResult(String[][] relation, String metadata) {
		this.relation = relation;
		this.metadata = metadata;
	}

	public String[][] getRelation() {
		return relation;
	}

	public String getContextBefore() {
		return contextBefore;
	}

	public void setContextBefore(String contextBefore) {
		this.contextBefore = contextBefore;
	}

	public String getContextAfter() {
		return contextAfter;
	}

	public void setContextAfter(String contextAfter) {
		this.contextAfter = contextAfter;
	}

	/**
	 * <span class="de">Gibt Anzahl Zeilen der Relation.</span>
	 * <span class="en">Number of rows in the relation.</span>
	 * @return <span class="de">Anzahl Zeilen</span>
	 * <span class="en">numer of rows</span>
	 */
	public int getNumRows() {
		return this.relation.length;
	}
	
	/**
	 * <span class="de">Gibt Anzahl Spalten der Relation.</span>
	 * <span class="en">Number of cols in the relation.</span>
	 * @return <span class="de">Anzahl Spalten</span>
	 * <span class="en">number of cols</span>
	 */
	public int getNumCols() {
		return this.relation[0].length;
    }

	public String getMetadata() {
		return metadata;
	}

	public void setMetadata(String metadata) {
		this.metadata = metadata;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * <span class="de">Wandelt einen JSON String in eine Objekt dieser Klasse um.</span>
     * <span class="en">Change json string to ann object of this class.</span>
     * @param json (<span class="de">JSON String</span>
     * <span class="en">json string</span>)
     * @return <span class="de">Objekt dieser Klasse.</span>
     * <span class="en">ExtractionResult object</span>
     */
	public ExtractionResult fromJson(String json) {
        return gson.fromJson(json, ExtractionResult.class);
    }
	
	/**
	 * <span class="de">Erzeugt einen JSON String aus dieser Klasse.</span>
	 * <span class="en">Creates the JSON code from this class.</span>
	 * @return <span class="de">JSON String</span>
	 * <span class="en">json string</span>
	 */
    public String toJson() {
        return gson.toJson(this);
    }

}
