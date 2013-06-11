package de.tudresden.deexcelerator.data;

import java.util.ArrayList;
import java.util.List;

import de.tudresden.deexcelerator.util.ColInformation;
import de.tudresden.deexcelerator.util.HeaderEntry;
import de.tudresden.deexcelerator.util.SplittedCells;



/**
 * <span class="de">Spezifische Daten die in der Extraktion bearbeitet und genutzt werden.</span>
 * <span class="en">The spezific information for the extraktion, which will be used and change.</span>
 * @author Christopher Werner
 *
 */
public class AnalyzeInformation {

	/** <span class="de">Erste mit Werten bef&uumlllte Spalte.</span>
	 * <span class="en">The first not empty col.</span> */ 
	public int colStart;
	/** <span class="de">Letzte mit Werten bef&uumlllte Spalte.</span>
	 * <span class="en">The last not empty col.</span> */ 
	public int colEnd;
	/** <span class="de">Erste mit Werten bef&uumlllte Zeile.</span>
	 * <span class="en">The first not empty row.</span> */ 
	public int rowStart;
	/** <span class="de">Letzte mit Werten bef&uumlllte Zeile.</span>
	 * <span class="en">The last not empty row.</span> */ 
	public int rowEnd;
	/** <span class="de">Anzahl der vorhandenen Spalten.</span>
	 * <span class="en">Number of existing cols.</span> */ 
	public int colNumber;
	/** <span class="de">Anzahl der vorhandenen Zeilen.</span>
	 * <span class="en">Number of existing rows.</span> */ 
	public int rowNumber;
	/** <span class="de">Zeile in welcher der Headerbereich aufh&oumlrt und der Wertebereich beginnt.</span>
	 * <span class="en">The row, where the value area starts and the header area ends.</span> */ 
	public int headerFinish;
	
	/** <span class="de">Liste von Leerspalten in der Tabelle.</span>
	 * <span class="en">List of empty cols in the table.</span> */ 
	public List <Integer> emptyCols;
	/** <span class="de">Liste von Leerzeilen in der Tabelle.</span>
	 * <span class="en">List of empty rows in the table.</span> */ 
	public List <Integer> emptyRows;
	
	/** <span class="de">Infos über Spalten mit ihrem Datentyp.</span>
	 * <span class="en">Informstion about cols and the datatype.</span> */ 
	public List <ColInformation> colInfos;   
	/** <span class="de">Liste der Spalten in denen ein Identifikationswort vorkommt.</span>
	 * <span class="en">List of cols, where an identifikation word was find.</span> */ 
	public List <Integer> rememberTotalCols;
	/** <span class="de">Liste der Zeilen in denen eine Identifikationswort vorkommt.</span>
	 * <span class="en">List of rows, where an identifikation word was find.</span> */ 
	public List <Integer> rememberTotalRows;
	/** <span class="de">Liste der vorhandenen Totalspalten.</span>
	 * <span class="en">List of the identified totalcols.</span> */ 
	public List <Integer> algoTotalCols;
	/** <span class="de">Liste der vorhandenen Totalzeilen.</span>
	 * <span class="en">List of the identified totalrows.</span> */ 
	public List <Integer> algoTotalRows;
	/** <span class="de">Liste von zusammengeh&oumlrigen Zellen.</span>
	 * <span class="en">List of connected cells.</span> */ 
	public List <SplittedCells> mergeCells;
		
	/** <span class="de">Liste der Metadaten.</span>
	 * <span class="en">Liste of metadata as strings.</span> */ 
	public List <String> metaDataTable;
	/** <span class="de">Liste der Attributnamen.</span>
	 * <span class="en">List of attributnames.</span> */ 
	public List <HeaderEntry> attributeList;
	
	/** <span class="de">Name der gefundenen Relation.</span>
	 * <span class="en">Name of the identified relation.</span> */ 
	public String relationName;
	
	//fuer Tabellen Finder relevant
	/** <span class="de">Liste der Relationsanf&aumlnge einer Tabelle.</span>
	 * <span class="en">List of all relation starts in the table.</span> */ 
	public List<Integer> relationStarts;
	/** <span class="de">Liste der leeren Bereichsanf&aumlnge einer Tabelle.</span>
	 * <span class="en">List of all starts from empty areas in the table.</span> */ 
	public List<Integer> emptyAreasStarts;
	/** <span class="de">Liste der Headeranf&aumlnge einer Tabelle.</span>
	 * <span class="en">List of all headerstarts in the table.</span> */ 
	public List <Integer> headerStarts;
	
	/** <span class="de">VisualInformation f&uumlr die Oberfl&aumlche.</span>
	 * <span class="en">VisualInformation for the gui.</span> */ 
	public VisualInformation vi;
	/** <span class="de">Nummer des Sheets, wo die Tabelle gefunden wurde.</span>
	 * <span class="en">Number of the sheet in which the table is.</span> */ 
	public int sheetNumber;
	/** <span class="de">Nummer der Tabelle im Sheet.</span>
	 * <span class="en">Number of the table in the sheet.</span> */ 
	public int tableNumber;
	
	/** <span class="de">Konstruktur</span>
	 * <span class="en">Constructor</span> */ 
	private AnalyzeInformation () {
		vi = new VisualInformation();
		sheetNumber = 0;
		tableNumber = 0;
		
		colStart = 0;
		colEnd = 0;
		rowStart = 0;
		rowEnd = 0;
		colNumber = 0;
		rowNumber = 0;
		headerFinish = 0;
		
		emptyCols = new ArrayList<Integer>();
		emptyRows = new ArrayList<Integer>();
		
		colInfos = new ArrayList<ColInformation>();   
		rememberTotalCols = new ArrayList<Integer>();
		rememberTotalRows = new ArrayList<Integer>();
		algoTotalCols = new ArrayList<Integer>();
		algoTotalRows = new ArrayList<Integer>();
		mergeCells = new ArrayList<SplittedCells>();		
		
		metaDataTable = new ArrayList<String>();
		attributeList = new ArrayList<HeaderEntry>();
		
		headerStarts = new ArrayList<Integer>();
		relationName = "";
		
		relationStarts = new ArrayList<Integer>();
		emptyAreasStarts = new ArrayList<Integer>();
	}
	
	/**
	 * <span class="de">Konstruktor mit Anfangskonfigurations setzen.</span>
	 * <span class="en">Constructor, which sets the start configuration.</span>
	 * @param gd <span class="de">Die Grunddaten der Tabelle.</span>
	 * <span class="en">The ground data of the table.</span>
	 */
	public AnalyzeInformation (DocumentInformation gd) {
		this();
		this.setStartKonfiguration(gd);
	}
	
	/**
	 * <span class="de">Anlegen einer Copy von diesem Objekt mit ver&aumlndertem Zeilenanfang und Zeilenende.</span>
	 * <span class="en">Creats a copy of this element with neue row start and row end.</span>
	 * @param newZeilenAnfang (<span class="de">neuer Zeilenanfang</span>
	 * <span class="en">new row start</span>)
	 * @param newZeilenEnde (<span class="de">neues Zeilenende</span>
	 * <span class="en">new row end</span>)
	 * @return <span class="de">Copy</span>
	 * <span class="en">copy of this object</span>
	 */
	public AnalyzeInformation copySpecificData (int newZeilenAnfang, int newZeilenEnde) {
		AnalyzeInformation sdnew = copySpecificData();
		sdnew.rowStart = newZeilenAnfang;
		sdnew.rowEnd = newZeilenEnde;
		return sdnew;
	}
	
	/**
	 * <span class="de">Anlegen einer Copy von disem Objekt.</span>
	 * <span class="en">Create a copy of this object.</span>
	 * @return <span class="de">Copy</span>
	 * <span class="en">copy of this object</span>
	 */
	public AnalyzeInformation copySpecificData () {
		AnalyzeInformation sdnew = new AnalyzeInformation();
		sdnew.relationStarts = relationStarts;
		sdnew.emptyAreasStarts = emptyAreasStarts;
		sdnew.headerStarts = headerStarts;
		sdnew.colStart = colStart;
		sdnew.colEnd = colEnd;
		sdnew.rowStart = rowStart;
		sdnew.rowEnd = rowEnd;
		sdnew.sheetNumber = sheetNumber;
		sdnew.vi.inputNew(vi);
		return sdnew;
	}
	
	/**
	 * <span class="de">Anfangskonfigurations setzen.</span>
	 * <span class="en">Set start configuration.</span>
	 * @param gd (<span class="de">Grunddaten</span>
	 * <span class="en">ground data of this table</span>)
	 */
	public void setStartKonfiguration (DocumentInformation gd) {
		this.colEnd = gd.getFc().colNumber();
		this.rowEnd = gd.getFc().rowNumber();
		this.colNumber = this.colEnd;
		this.rowNumber = this.rowEnd;
	}
}
