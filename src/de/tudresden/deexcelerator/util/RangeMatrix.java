package de.tudresden.deexcelerator.util;

/**
 * <span class="de">RangeZellen f&uumlr gemergte Bereiche aus allen Dateiformaten.</span>
 * <span class="en">RangeCells for merged areas of all datatypes.</span>
 * @author Christopher Werner
 *
 */
public class RangeMatrix {

	/** <span class="de">Spaltenwert der oberen Linken Zelle.</span>
	 * <span class="en">Colvalue of the top left cell.</span> */
	private int topLeftX; 
	/** <span class="de">Zeilenwert der oberen Linken Zelle.</span>
	 * <span class="en">Rowvalue of the top left cell.</span> */
	private int topLeftY;
	/** <span class="de">Spaltenwert der unteren Rechten Zelle.</span>
	 * <span class="en">Colsvalue of the bottom right cell.</span> */
	private int bottomRightX;
	/** <span class="de">Zeilenwert der unteren Rechten Zelle.</span>
	 * <span class="en">Rowvalue of the bottom right cell.</span> */
	private int bottomRightY;
	
	/**
	 * <span class="de">Konstruktur mit Spalten und Zeileninformation der Range.</span>
	 * <span class="en">Constructor with row and col information of the range.</span>
	 * @param topLeftX (<span class="de">Spaltenwert oben links</span>
	 * <span class="en">colvalue top left</span>)
	 * @param topLeftY (<span class="de">Zeilenwert oben links</span>
	 * <span class="en">rowvalue top left</span>)
	 * @param bottomRightX (<span class="de">Spaltenwert unten rechts</span>
	 * <span class="en">colvalue bottom right</span>)
	 * @param bottomRightY (<span class="de">Zeilenwert unten rechts</span>
	 * <span class="en">rowvalue bottom right</span>)
	 */
	public RangeMatrix (int topLeftX, int topLeftY, int bottomRightX, int bottomRightY) {
		this.bottomRightX = bottomRightX;
		this.bottomRightY = bottomRightY;
		this.topLeftX = topLeftX;
		this.topLeftY = topLeftY;
	}

	/**
	 * <span class="de">Getter bottomRightY.</span>
	 * <span class="en">Getter of bottomRightY.</span>
	 * @return <span class="de">bottomRightY</span>
	 * <span class="en">bottomRightY</span>
	 */
	public int getBottomRightY() {
		return bottomRightY;
	}

	/**
	 * <span class="de">Getter bottomRightX.</span>
	 * <span class="en">Getter of bottomRightX.</span>
	 * @return <span class="de">bottomRightX</span>
	 * <span class="en">bottomRightX</span>
	 */
	public int getBottomRightX() {
		return bottomRightX;
	}

	/**
	 * <span class="de">Getter topLeftY.</span>
	 * <span class="en">Getter of topLeftY.</span>
	 * @return <span class="de">topLeftY</span>
	 * <span class="en">topLeftY</span>
	 */
	public int getTopLeftY() {
		return topLeftY;
	}

	/**
	 * <span class="de">Getter topLeftX.</span>
	 * <span class="en">Getter of topLeftX.</span>
	 * @return <span class="de">topLeftX</span>
	 * <span class="en">topLeftX</span>
	 */
	public int getTopLeftX() {
		return topLeftX;
	}
	
	/**
	 * <span class="de">Stringwert zur Kontrollausgabe des Bereichs.</span>
	 * <span class="en">String value for control testing output.</span>
	 */
	public String toString() {
		//Als Beispiel Output
		return "TopX: " + topLeftX + " TopY: " + topLeftY + " BottomX: " + bottomRightX + " BottomY: " + bottomRightY;
	}

}
