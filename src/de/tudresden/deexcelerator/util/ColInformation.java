package de.tudresden.deexcelerator.util;

import de.tudresden.deexcelerator.data.Header;

/**
 * <span class="de">Spalteninformationen.</span>
 * <span class="en">Col informations.</span>
 * @author Christopher Werner
 *
 */
public class ColInformation {
	/**
	 * <span class="de">Formatinformationen als int Wert.
	 * 1 steht f&uumlr double.
	 * 2 steht f&uumlr int.
	 * 3 steht f&uumlr date.
	 * 4 steht f&uumlr string.</span>
	 * <span class="en">Type information as integer value.
	 * 1 stand for double.
	 * 2 stand for integer.
	 * 3 stand for date.
	 * 4 stand for string.</span>
	 */
	private int format; 
	/** <span class="de">Spaltennummer.</span>
	 * <span class="en">Col number.</span> */
	private int spaltenNummer;
	/** <span class="de">Maximale L&aumlnge des Typs in der Spalte.</span>
	 * <span class="en">Maximal string length in the col.</span> */
	private int lenght;
	/** <span class="de">Header f&uumlr OutputObjekt.</span>
	 * <span class="en">Header for OutputObject.</span> */
	private Header h;
	
	/**
	 * <span class="de">Konstruktor zur Belegung der Variablen.</span>
	 * <span class="en">Constructor to set the attributes.</span>
	 * @param format (<span class="de">Formattyp als Intwert</span>
	 * <span class="en">data type as integer value</span>)
	 * @param spaltenNummer (<span class="de">Spaltennummer</span>
	 * <span class="en">col number</span>)
	 * @param lenght (<span class="de">Maximale L&aumlnge des Typs</span>
	 * <span class="en">maximal string length in the col</span>)
	 */
	public ColInformation (int format, int spaltenNummer, int lenght, Header h) {
		this.spaltenNummer = spaltenNummer;
		this.format = format;
		this.lenght = lenght;
		this.h = h;
	}
	
	/**
	 * <span class="de">Getter.</span>
	 * <span class="en">Getter</span>
	 * @return <span class="de">Spaltennummer</span>
	 * <span class="en">col number</span>
	 */
	public int getSpaltenNummer() {
		return spaltenNummer;
	}
	
	/**
	 * <span class="de">Getter.</span>
	 * <span class="en">Getter.</span>
	 * @return <span class="de">Formatwert</span>
	 * <span class="en">data type value</span>
	 */
	public int getFormat() {
		return format;
	}

	/**
	 * <span class="de">Getter.</span>
	 * <span class="en">Getter.</span>
	 * @return <span class="de">Maximale L&aumlnge</span>
	 * <span class="en">maximal length</span>
	 */
	public int getLenght() {
		return lenght;
	}

	/**
	 * <span class="de">Getter.</span>
	 * <span class="en">Getter.</span>
	 * @return <span class="de">Header Objekt</span>
	 * <span class="en">header object</span>
	 */
	public Header getH() {
		return h;
	}
}
