package de.tudresden.deexcelerator.util;

import java.util.ArrayList;
import java.util.List;

/**
 * <span class="de">HeaderInformationen zur Visualisierung der Oberfl&aumlche.</span>
 * <span class="en">Header visual information for the gui.</span>
 * @author Christopher Werner
 *
 */
public class HeaderVisualInformation {

	public int percent;
	public List<RangeMatrix> ranges;
	
	public HeaderVisualInformation () {
		ranges = new ArrayList<RangeMatrix>();
		percent = 0;
	} 
}
