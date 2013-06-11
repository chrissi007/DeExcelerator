package de.tudresden.deexcelerator.stringarray;

import de.tudresden.deexcelerator.util.RangeMatrix;

/**
 * <span class="de">OutputStringarray Objekte für die Visualisierung.</span>
 * <span class="en">OutputStringarray objects for the visualization.</span>
 * @author Christopher Werner
 *
 */
public class OutputStringarray {

	public String[][] matrix;
	public String name;
	public RangeMatrix[] rmListe;
	
	public OutputStringarray () {
		rmListe = null;
	}
}
