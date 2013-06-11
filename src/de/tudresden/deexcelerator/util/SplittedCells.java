package de.tudresden.deexcelerator.util;

/**
 * <span class="de">Hilfsklasse zum Merken der gemergten Zellen.</span> 
 * <span class="en">Information about the merge cells.</span>
 * @author Christopher Werner
 *
 */
public class SplittedCells {

	/** <span class="de">X Wert der Startzelle.</span>
	 * <span class="en">X value of the start cell.</span> */
	private int x;
	/** <span class="de">Y Wert der Startzelle</span>
	 * <span class="en">Y value of the start cell.</span> */
	private int y;
	/** <span class="de">XSpanne zum erstrecken.</span>
	 * <span class="en">X margin.</span> */
	private int xrange;
	/** <span class="de">YSpanne zum erstrecken.</span>
	 * <span class="en">Y margin.</span> */
	private int yrange;
	/** <span class="de">Inhalt der Spenne.</span>
	 * <span class="en">Content of the merge cells.</span> */
	private String content;
	
	/**
	 * <span class="de">Konstruktor zum festlegen der Werte.</span>
	 * <span class="en">Constructor to define the attributes.</span>
	 * @param x (<span class="de">X Wert der Startzelle</span>
	 * <span class="en">x value of the start cell</span>)
	 * @param y (<span class="de">Y Wert der Startzelle</span>
	 * <span class="en">y value of the start cell</span>)
	 * @param content (<span class="de">Inhalt der Startzelle</span>
	 * <span class="en">content of the merge cells</span>)
	 */
	public SplittedCells (int x, int y, String content) {
		this.setX(x);
		this.setY(y);
		this.setContent(content);
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getContent() {
		return content;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getY() {
		return y;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getX() {
		return x;
	}

	public void setYrange(int yrange) {
		this.yrange = yrange;
	}

	public int getYrange() {
		return yrange;
	}

	public void setXrange(int xrange) {
		this.xrange = xrange;
	}

	public int getXrange() {
		return xrange;
	}
	
	public String toString() {
		//Als Beispiel output
		return content + " x: " + x + " y: " + y + " xr: " + xrange + " yr: " + yrange;
	}
}
