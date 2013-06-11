package de.tudresden.deexcelerator.data;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.regex.Pattern;

import de.tudresden.deexcelerator.util.MatrixCell;
import de.tudresden.deexcelerator.util.RangeMatrix;

/**
 * <span class="de">Funktionen die vorgefertigt in Excel abgerufen werden k&oumlnnen.</span>
 * <span class="en">Gives information from the cell array.</span>
 * @author Christopher Werner
 *
 */
public class FunctionsOnMatrixOfCells {
	
	/** <span class="de">Gibt String Matrix der Tabelle an.</span>
	 * <span class="en">String representation of the document.</span> */
	private MatrixCell[][] matrix;
	/** <span class="de">Gibt Matrixnamen der Tabelle an.</span>
	 * <span class="en">The document name of the table.</span> */
	private String matrixName;
	/** <span class="de">Gibt die gefundenen Bereiche der Tabelle an.</span>
	 * <span class="en">Ranges in the document, which are find in the analyze.</span> */
	private RangeMatrix[] ranges;
	
	/** <span class="de">Pattern um die Währung zu pr&uumlfen.</span>
	 * <span class="en">Pattern to proof for currency.</span> */
	private Pattern isCurrency = Pattern.compile("-?\\s*([$£₤]\\s*[\\d,]+(\\.\\d+)?)|([\\d,]+(\\.\\d+)?\\s*[€])|");
	
	/**
	 * <span class="de">Konstruktor mit Dokument Informationen als Cell array.</span>
	 * <span class="en">Constructor to safe the document information.</span>
	 * @param matrix (<span class="de">Cellarray der Matrix</span>
	 * <span class="en">cell array of the document</span>)
	 * @param matrixName (<span class="de">Matrixname</span>
	 * <span class="en">name of the array, can be the document name</span>)
	 * @param ranges (<span class="de">Bereiche der Matrix</span>
	 * <span class="en">ranges in the document, which are find in the analyze</span>)
	 */
	public FunctionsOnMatrixOfCells (MatrixCell[][] matrix, String matrixName, RangeMatrix[] ranges) {
		this.matrix = matrix;
		this.matrixName = matrixName;
		this.ranges = ranges;
		//fillEmptyCells();
	}
	
	/**
	 * <span class="de">Füllt null Werte im Array mit neuen leeren Instanzen.</span>
	 * <span class="en">Fill null values in the array with new empty objects.</span>
	 */
	/*private void fillEmptyCells ()
	{
		for (int x = 0; x<this.colNumber(); x++)
			for (int y = 0; y<this.rowNumber();y++)
				if (matrix[x][y] == null)
					matrix[x][y] = new MatrixCell();
	}*/

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
		if (matrix[x][y] == null || matrix[x][y].content == null)
			return "";
		return matrix[x][y].content;
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
		if (matrix[x][y] == null)
			return true;
		if (matrix[x][y].date == null && (matrix[x][y].content.trim().equals("")  || contentCell(x,y).trim().equals("--") || contentCell(x,y).trim().equals("-")) && matrix[x][y].number == null)
			return true;
		return false;
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
		if (matrix[x][y] == null)
			return false;
		if (matrix[x][y].date == null)
			return false;
		return true;
	}
	
	/**
	 * <span class="de">Gibt die SpaltenAnzahl des Sheets oder des Dokuments zur&uumlck.</span>
	 * <span class="en">Gives back the number of cols.</span>
	 * @return <span class="de">int Wert Spaltenanzahl</span>
	 * <span class="en">number of cols</span>
	 */
	public int colNumber () {
		return matrix.length;
	}
	
	/**
	 * <span class="de">Gibt die ZeilenAnzahl des Sheets oder des Dokuments zur&uumlck.</span>
	 * <span class="en">Gives back the number of rows in the document.</span>
	 * @return <span class="de">int Wert Zeilenanzahl</span>
	 * <span class="en">number of rows</span>
	 */
	public int rowNumber () {
		return matrix[0].length;
	}
	
	/**
	 * <span class="de">Gibt Sheetnamen zur&uumlck.</span>
	 * <span class="en">Gives the document name.</span>
	 * @return <span class="de">String Wert</span>
	 * <span class="en">document name</span>
	 */
	public String sheetName () {
		return matrixName;
	}
	
	/**
	 * <span class="de">Gibt Range mit MergeZellen zur&uumlck.</span>
	 * <span class="en">Range cells from the document.</span>
	 * @return <span class="de">RangeMatrix feld</span>
	 * <span class="en">RangeMatrix array</span>
	 */
	public RangeMatrix[] mergeCells () {
		return ranges;
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
		if (matrix[x][y] == null)
			return null;
		return matrix[x][y].fontName;
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
		if (matrix[x][y] == null)
			return 0;
		return matrix[x][y].backgroundColor;
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
		if (matrix[x][y] == null)
			return 0;
		return matrix[x][y].fontBoldWeight;
	}
	
	/**
	 * <span class="de">Gibt Double Wert eines Strings zur&uumlck falls nicht dann null.</span>
	 * <span class="en"></span>
	 * @param content (<span class="de">Stringwert</span>
	 * <span class="en"></span>)
	 * @return <span class="de">double Wert</span>
	 * <span class="en"></span>
	 */
	private Double getDouble (String content) {
		double wert = 0;
		DecimalFormat format = new DecimalFormat();
		try {
			wert = format.parse(content).doubleValue();
		} catch (ParseException e) {
			return null;
		}			
		return wert;
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
		if (matrix[x][y] == null)
			return null;
		if (matrix[x][y].number != null)
			return matrix[x][y].number;
		if (contentCell(x, y).trim().equals(""))
			return null;
		char[] c = contentCell(x, y).trim().toCharArray();
		String ergebnis = "";
	    for (int i = 0; i<c.length; i++)
	    {	    	
	    	if (c[i] != '+' && c[i] != '%' && c[i] != '$' &&  c[i] != '$')
	    		ergebnis = ergebnis + c[i];
	    }
	    return getDouble(ergebnis);
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
		Double d = getDoubleValue(x,y);
		if (d == null)
			return null;
		else
			return (int)d.doubleValue();
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
		if (matrix[x][y] == null)
			return false;
		if (matrix[x][y].number != null)
			return true;
		if (contentCell(x,y).trim().equals(""))
			return false;
		String content = contentCell(x, y).trim();
		char[] c = content.toCharArray();
		boolean checker = false;
	    boolean checkNumber = false;
	    boolean exponentCheck = false;
	    boolean bindeCheck = false;
	    for (int i = 0; i<c.length; i++)
	    {	    	
	    	if (c[i] == '.' || c[i] == ',' || c[i] == '+' || c[i] == '%'|| c[i] == '$' || c[i] == '$')
	    		checker = true;
	    	if (c[i]<='9' && c[i]>='0') {
	    		checker = true;
	    		checkNumber = true;
	    	}
	    	if (c[i]=='-') {
	    		checker = true;
	    		if (bindeCheck)
	    			return false;
	    		bindeCheck = true;
	    	}
	    	if (c[i]=='E') {
	    		checker = true;
	    		if (exponentCheck)
	    			return false;
	    		exponentCheck = true;
	    	}
	    	if (!checker)
	    		return false;
	    	checker = false;
	    }
	    return checkNumber;
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
	public Date getDateValue (int x,int y) {
		if (matrix[x][y] == null)
			return null;
		return matrix[x][y].date;
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
		if (matrix[x][y] == null)
			return false;
		boolean test = false;
		char[] c = matrix[x][y].content.toCharArray();
		for (int i = 0; i < c.length; i++)
			if (c[i] == '$' || c[i] == '$')
				test = true;
		return test && isCurrency.matcher(matrix[x][y].content).matches();
	}
}
