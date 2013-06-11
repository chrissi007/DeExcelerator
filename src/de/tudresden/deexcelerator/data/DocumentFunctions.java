package de.tudresden.deexcelerator.data;

import java.util.Date;


import de.tudresden.deexcelerator.util.MatrixCell;
import de.tudresden.deexcelerator.util.RangeMatrix;

/**
 * <span class="de">Entscheidungsklasse für Auswahl der vorliegenden Grunddaten für Grundfunktionen.</span>
 * <span class="en">Gives information from the document and makes a decision who the document would be saved.</span>
 * @author Christopher Werner
 *
 */
public class DocumentFunctions {

	/** <span class="de">Gibt an ob als Matrix vorliegt.</span>
	 * <span class="en">Show if it is a string array or an cell array.</span> */
	private boolean isMatrix = false;
	
	/** <span class="de">Gibt das Objekt an aus dem Werte eines Exceldokuments bezogen werden.</span>
	 * <span class="en">Gives the information from the cell array, where are more information saved.</span> */
	private FunctionsOnMatrixOfCells fmc;
	/** <span class="de">Gibt das Objekt an aus dem Werte einer Matrix bezogen werden.</span>
	 * <span class="en">Gives the information from the string array.</span> */
	private FunctionsOnMatrixOfStrings fm;
	
	
	/**
	 * <span class="de">Konstruktor nimmt die gleichen Elemente wie der DcumentInformation Konstruktor auf.</span>
	 * <span class="en">Constructor to safe the document information.</span>
	 * @param isMatrix (<span class="de">Wahrheitswert, ob Matrix oder nicht</span>
	 * <span class="en">say if it is an string array or a cell array</span>)
	 * @param cellMatrix (<span class="de">Feld von Cellen</span>
	 * <span class="en">array of cells</span>)
	 * @param matrix (<span class="de">Stringarray der Matrix</span>
	 * <span class="en">string array of the document</span>)
	 * @param matrixName (<span class="de">Matrixname</span>
	 * <span class="en">name of the array, can be the document name</span>)
	 * @param ranges (<span class="de">Bereiche der Matrix</span>
	 * <span class="en">ranges in the document, which are find in the analyze</span>)
	 */
	public DocumentFunctions (boolean isMatrix, MatrixCell[][] cellMatrix, String[][] matrix, String matrixName, RangeMatrix[] ranges) {
		this.isMatrix = isMatrix;
		if (isMatrix)
			this.fm = new FunctionsOnMatrixOfStrings(matrix, matrixName, ranges);
		else
			this.fmc = new FunctionsOnMatrixOfCells(cellMatrix, matrixName, ranges);
	}
	
	/**
	 * <span class="de">Gibt zur&uumlck, ob es eine Matrix ist oder nicht.</span>
	 * <span class="en">Gives back if it is a string array or not.</span>
	 * @return <span class="de">isMatrix</span>
	 * <span class="en">isMatrix</span>
	 */
	public boolean getIsMatrix () {
		return isMatrix;
	}
	
	/**
	 * <span class="de">Gibt zur&uumlck, ob es eine Cell Matrix ist oder nicht.</span>
	 * <span class="en">Gives back if it is a cell array or not.</span>
	 * @return <span class="de">!isMatrix</span>
	 * <span class="en">!isMatrix</span>
	 */
	public boolean getIsExcel () {
		return !isMatrix;
	}
	
	/**
	 * <span class="de">Gibt den Inhalt der Zelle zur&uumlck.</span>
	 * <span class="en">Gives back the string content of the cell.</span>
	 * @param x (<span class="de">Spalte</span>
	 * <span class="en">col</span>)
	 * @param y (<span class="de">Zeile</span>
	 * <span class="en">row</span>)
	 * @return <span class="de">Zelleninhalt</span>
	 * <span class="en">cell content</span>
	 */
	public String contentCell (int x, int y) {
		if (isMatrix)
			return fm.contentCell(x, y);
		else
			return fmc.contentCell(x, y);
	}
	
	/**
	 * <span class="de">Pr&uumlft ob Zelle leer ist.</span>
	 * <span class="en">Proof if cell is empty.</span>
	 * @param x (<span class="de">Spalte</span>
	 * <span class="en">col</span>)
	 * @param y (<span class="de">Zeile</span>
	 * <span class="en">row</span>)
	 * @return <span class="de">boolean Wert</span>
	 * <span class="en">boolean value</span>
	 */
	public boolean emptyCell (int x, int y) {
		if (isMatrix)
			return fm.emptyCell(x, y);
		else
			return fmc.emptyCell(x, y);
	}
	
	/**
	 * <span class="de">Pr&uumlft ob Zelle ein Datum enth&aumllt.</span>
	 * <span class="en">Proof if the cell contains a date.</span>
	 * @param x (<span class="de">Spalte</span>
	 * <span class="en">col</span>)
	 * @param y (<span class="de">Zeile</span>
	 * <span class="en">row</span>)
	 * @return <span class="de">boolean Wert</span>
	 * <span class="en">boolean value</span>
	 */
	public boolean dateCell (int x, int y) {
		if (isMatrix)
			return fm.dateCell(x, y);
		else
			return fmc.dateCell(x, y);
	}
	
	/**
	 * <span class="de">Gibt die SpaltenAnzahl des Sheets oder des Dokuments zur&uumlck.</span>
	 * <span class="en">Gives back the number of cols.</span>
	 * @return <span class="de">int Wert Spaltenanzahl</span>
	 * <span class="en">number of cols</span>
	 */
	public int colNumber () {
		if (isMatrix)
			return fm.colNumber();
		else
			return fmc.colNumber();
	}
	
	/**
	 * <span class="de">Gibt die ZeilenAnzahl des Sheets oder des Dokuments zur&uumlck.</span>
	 * <span class="en">Gives back the number of rows in the document.</span>
	 * @return <span class="de">int Wert Zeilenanzahl</span>
	 * <span class="en">number of rows</span>
	 */
	public int rowNumber () {
		if (isMatrix)
			return fm.rowNumber();
		else
			return fmc.rowNumber();
	}
	
	/**
	 * <span class="de">Gibt Sheetnamen zur&uumlck.</span>
	 * <span class="en">Gives the document name.</span>
	 * @return <span class="de">String Wert</span>
	 * <span class="en">document name</span>
	 */
	public String sheetName () {
		if (isMatrix)
			return fm.sheetName();
		else
			return fmc.sheetName();
	}
	
	/**
	 * <span class="de">Gibt Range mit MergeZellen zur&uumlck.</span>
	 * <span class="en">Range cells from the document.</span>
	 * @return <span class="de">RangeMatrix feld</span>
	 * <span class="en">RangeMatrix array</span>
	 */
	public RangeMatrix[] mergeCells () {
		if (isMatrix)
			return fm.mergeCells();
		else
			return fmc.mergeCells();
	}
	
	/**
	 * <span class="de">Gibt Font der Zelle zur&uumlck (Excel).</span>
	 * <span class="en">Gives the font of the cell. (excel)</span>
	 * @param x (<span class="de">Spalte</span>
	 * <span class="en">col</span>)
	 * @param y (<span class="de">Zeile</span>
	 * <span class="en">row</span>)
	 * @return <span class="de">String Wert</span>
	 * <span class="en">font as string</span>
	 */
	public String fontName (int x, int y) {
		if (!isMatrix)
			return fmc.fontName(x, y);
		else
			return "Neu";
	}
	
	/**
	 * <span class="de">Gibt FontBold Wert zur&uumlck (Excel).</span>
	 * <span class="en">Gives the boldweight of the font from a cell. (excel)</span>
	 * @param x (<span class="de">Spalte</span>
	 * <span class="en">col</span>)
	 * @param y (<span class="de">Zeile</span>
	 * <span class="en">row</span>)
	 * @return <span class="de">int Wert</span>
	 * <span class="en">int value</span>
	 */
	public int fontBoldWeight (int x, int y) {
		if (!isMatrix)
			return fmc.fontBoldWeight(x, y);
		else
			return 0;
	}
	
	/**
	 * <span class="de">Gibt Hintergrundfarbe einer Zelle zur&uumlck (Excel).</span>
	 * <span class="en">Gives the background color of a cell. (ecxel)</span>
	 * @param x (<span class="de">Spalte</span>
	 * <span class="en">col</span>)
	 * @param y (<span class="de">Zeile</span>
	 * <span class="en">row</span>)
	 * @return <span class="de">int Wert</span>
	 * <span class="en">int value</span>
	 */
	public int backgroundColor (int x, int y) {
		if (!isMatrix)
			return fmc.backgroundColor(x, y);
		else
			return 0;
	}
	
	/**
	 * <span class="de">Gibt den Double Wert der Zelle zur&uumlck.
	 * Falls kein Double Wert drin return null.</span>
	 * <span class="en">Gives back the double value of the cell.
	 * If there is no double value the value would be null.</span>
	 * @param x (<span class="de">Spalte</span>
	 * <span class="en">col</span>)
	 * @param y (<span class="de">Zeile</span>
	 * <span class="en">row</span>)
	 * @return <span class="de">doubleWer der Zelle</span>
	 * <span class="en">double value</span>
	 */
	public Double getDoubleValue (int x, int y) {
		if (!isMatrix)
			return fmc.getDoubleValue(x, y);
		else
			return fm.getDoubleValue(x, y);
	}
	
	/**
	 * <span class="de">Gibt den Integer Wert einer Zelle zur&uumlck.
	 * Falls keiner vorhanden dann null.</span>
	 * <span class="en">Gives back the integer value of the cell.
	 * If there is no double value the value would be null.</span>
	 * @param x (<span class="de">Spalte</span>
	 * <span class="en">col</span>)
	 * @param y (<span class="de">Zeile</span>
	 * <span class="en">row</span>)
	 * @return <span class="de">integer Wert</span>
	 * <span class="en">integer value</span>
	 */
	public Integer getIntegerValue (int x, int y) {
		if (!isMatrix)
			return fmc.getIntegerValue(x, y);
		else
			return fm.getIntegerValue(x, y);
	}
	
	/**
	 * <span class="de">Pr&uumlft ob eine Zelle einen ZahlenWert enth&aumllt.</span>
	 * <span class="en">Proof if a cell is a number or not.</span>
	 * @param x (<span class="de">Spalte</span>
	 * <span class="en">col</span>)
	 * @param y (<span class="de">Zeile</span>
	 * <span class="en">row</span>)
	 * @return <span class="de">boolean Wert</span>
	 * <span class="en">boolean value</span>
	 */
	public boolean numberCell (int x, int y) {
		if (!isMatrix)
			return fmc.numberCell(x, y);
		else
			return fm.numberCell(x, y);
	}
	
	/**
	 * <span class="de">Gibt den Date Wert einer Zelle zur&uumlck sonst null.</span>
	 * <span class="en">Gives the data value of a cell else null.</span>
	 * @param x (<span class="de">Spalte</span>
	 * <span class="en">col</span>)
	 * @param y (<span class="de">Zeile</span>
	 * <span class="en">row</span>)
	 * @return <span class="de">Date Wert</span>
	 * <span class="en">date value</span>
	 */
	public Date getDateValue (int x, int y) {
		if (!isMatrix)
			return fmc.getDateValue(x, y);
		else
			return fm.getDateValue(x, y);
	}
	
	/**
	 * <span class="de">Pr&uumlft ob eine Zelle eine Währrung enth&aumllt.</span>
	 * <span class="en">Proof if a cell contains a currency value or not.</span>
	 * @param x (<span class="de">Spalte</span>
	 * <span class="en">col</span>)
	 * @param y (<span class="de">Zeile</span>
	 * <span class="en">row</span>)
	 * @return <span class="de">Wahrheitswert ob Währung</span>
	 * <span class="en">boolean value if cell contains currency</span>
	 */
	public boolean isCurrency (int x, int y) {
		if (!isMatrix)
			return fmc.isCurrency(x, y);
		else
			return fm.isCurrency(x, y);
	}
}
