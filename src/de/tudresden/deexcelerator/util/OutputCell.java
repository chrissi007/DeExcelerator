package de.tudresden.deexcelerator.util;

import java.util.Date;

/**
 * <span class="de">Outputzelle zum befüllen.</span>
 * <span class="en">Output cell to use.</span>
 * @author Christopher Werner
 *
 */
public class OutputCell {

	public Date date;
	public String content;
	public Double doubleNumber;
	public Integer integerNumber;
	
	public OutputCell () {
		date = null;
		content = null;
		doubleNumber = null;
		integerNumber = null;
	}
}
