package de.tudresden.deexcelerator.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;

import de.tudresden.deexcelerator.util.HeaderVisualInformation;
import de.tudresden.deexcelerator.util.SplittedCells;

/**
 * <span class="de">Nummer des Sheets, wo die Tabelle gefunden wurde.</span>
 * <span class="en">Number of the sheet in which the table is.</span>
 * @author Christopher Werner
 *
 */
public class VisualInformation {
	
	private static final Gson gson = new Gson();

	//Informationen am Anfang
	public List <Integer> emptyRowsBefor;
	public List <Integer> emptyColsBefor;
	
	//Metadaten
	public List <String> metaDaten;
	public List <Integer> emptyRowsMeta;
	
	//Header Schritt
	//Header Attribute siehe analyse informatione
	//***Zeilennummer und Prozentwert***
	public Map <Integer, HeaderVisualInformation> headerNumber;
	public Map <Integer, HeaderVisualInformation> headerYear;
	public Map <Integer, HeaderVisualInformation> headerBackground;
	public Map <Integer, HeaderVisualInformation> headerDate;
	public Map <Integer, HeaderVisualInformation> headerDifferents;
	public Map <Integer, HeaderVisualInformation> headerSequence;
	public Map <Integer, HeaderVisualInformation> headerStringlength;
	
	//Typerkennung
	//Gesamtanzahl an Zeilen im Wertebereich
	public Map<Integer, Map <String, Integer> > ergebnisseTypen;
	//Ergebnis anzeigen
	
	//Summenzeilen und Spalten
	//***Zeilen oder Spaltennummer und Prozentwert***
	public Map <Integer, Integer> sumCols;
	public Map <Integer, Integer> sumRows;
	
	//Layoutlines
	public List <SplittedCells> mergeCells;
	
	public VisualInformation () {
		emptyRowsBefor = new ArrayList<Integer>();
		emptyColsBefor = new ArrayList<Integer>();
		metaDaten = new ArrayList<String>();
		emptyRowsMeta  = new ArrayList<Integer>();
		headerNumber = new HashMap<Integer, HeaderVisualInformation>();
		headerYear = new HashMap<Integer, HeaderVisualInformation>();
		headerBackground = new HashMap<Integer, HeaderVisualInformation>();
		headerDate = new HashMap<Integer, HeaderVisualInformation>();
		headerDifferents = new HashMap<Integer, HeaderVisualInformation>();
		headerSequence = new HashMap<Integer, HeaderVisualInformation>();
		headerStringlength = new HashMap<Integer, HeaderVisualInformation>();
		ergebnisseTypen = new HashMap<Integer, Map<String, Integer>>();
		sumCols = new HashMap<Integer, Integer>();
		sumRows = new HashMap<Integer, Integer>();
		mergeCells = new ArrayList<SplittedCells>();
	}
	
	public void inputNew (VisualInformation v) {
		this.headerBackground = v.headerBackground;
		this.headerDate = v.headerDate;
		this.headerDifferents = v.headerDifferents;
		this.headerNumber = v.headerNumber;
		this.headerSequence = v.headerSequence;
		this.headerStringlength = v.headerStringlength;
		this.headerYear = v.headerYear;
	}
	
	public String toJson() {
        return gson.toJson(this);
    }
}
