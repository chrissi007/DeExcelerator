package de.tudresden.deexcelerator.util;

import java.util.Date;

/**
 * <span class="de">Zelleninformationen aus dem Dokument.</span>
 * <span class="en">Cell structure of the document representation.</span>
 * @author Christopher Werner
 *
 */
public class MatrixCell {
	
	public Date date;
	public String content;
	public Double number;
	
	public int backgroundColor;
	public String fontName;
	public int fontBoldWeight;

	public MatrixCell () {
		date = null;
		content = "";
		number = null;
		backgroundColor = 0;
		fontBoldWeight = 0;
		fontName = "";
	}
}
