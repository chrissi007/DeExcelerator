package de.tudresden.deexcelerator.util;

/**
 * <span class="de">Headereintrag für Headerinformation</span>
 * <span class="en">Header entry for header information.</span>
 * @author Christopher Werner
 *
 */
public class HeaderEntry {

	/** <span class="de">Spaltennummer des Headers.</span>
	 * <span class="en">Col number of the header entry.</span> */
	private int spalte;
	/** <span class="de">Headername.</span>
	 * <span class="en">Name of the header entry</span> */
	private String attribut;
	
	/**
	 * <span class="de">Konstruktor zur Belegung der Variablen.</span>
	 * <span class="en">Constructor to set the attributes.</span>
	 * @param spalte (<span class="de">Spaltenwert</span>
	 * <span class="en">col value</span>)
	 * @param attribut (<span class="de">Headername</span>
	 * <span class="en">header name</span>)
	 */
	public HeaderEntry (int spalte, String attribut) {
		this.setAttribut(attribut);
		this.setSpalte(spalte);
	}

	public void setAttribut(String attribut) {
		this.attribut = attribut;
	}

	public String getAttribut() {
		return attribut;
	}

	public void setSpalte(int spalte) {
		this.spalte = spalte;
	}

	public int getSpalte() {
		return spalte;
	}
}
