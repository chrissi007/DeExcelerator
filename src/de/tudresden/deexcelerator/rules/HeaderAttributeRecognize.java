package de.tudresden.deexcelerator.rules;

import de.tudresden.deexcelerator.data.AnalyzeInformation;
import de.tudresden.deexcelerator.data.DocumentInformation;
import de.tudresden.deexcelerator.util.HeaderEntry;
import de.tudresden.deexcelerator.util.RangeMatrix;

/**
 * <span class="de">Headerattribute bestimmen.</span>
 * <span class="en">Define the header attributes.</span>
 * @author Christopher Werner
 *
 */
public class HeaderAttributeRecognize {
	
	/**
	 * <span class="de">Bestimmt die Headerattribute.
	 * Fasst untereinander liegenden Inhalte zusammen
	 * Wertet Mergezellen aus.</span>
	 * <span class="en">Define the header attributes.
	 * Merges contents in the header to get one attribute for every col.</span>
	 * @param gd (<span class="de">Verwendete DocumentInformation</span>
     * <span class="en">the used document information</span>)
     * @param sd (<span class="de">AnalyseInformation</span>
     * <span class="en">analyze information</span>)
	 */
	public void action (DocumentInformation gd, AnalyzeInformation sd) {
		for (int x=sd.colStart; x<sd.colEnd; x++)
		{
			String headerZeile = "";
			if (!sd.emptyCols.contains(x)) {
				for (int y=sd.rowStart; y<sd.headerFinish; y++)
				{
					if (!sd.emptyRows.contains(y))
					{
						RangeMatrix[] mergeZellen = gd.getFc().mergeCells();
						int lengeRange = mergeZellen.length;
						boolean test = true;
						for (int z = 0; z<lengeRange; z++)
						{
							RangeMatrix mergeZelle = mergeZellen[z];
							int rowBottomRight = mergeZelle.getBottomRightY();
							int colBottomRight = mergeZelle.getBottomRightX();
							int rowTopLeft = mergeZelle.getTopLeftY();
							int colTopLeft = mergeZelle.getTopLeftX();
							if (colTopLeft<= x && x <= colBottomRight && rowTopLeft <= y && y <= rowBottomRight)
							{
								String lineValue = gd.getFc().contentCell(colTopLeft, rowTopLeft);
								if (!headerZeile.contains(lineValue)) {
									headerZeile = headerZeile + " " + lineValue;
									test = false;
								}
							}
						}
						if (test && !gd.getFc().emptyCell(x, y))
						{
							if (gd.getFc().contentCell(x, y).equals(""))
								headerZeile = headerZeile + " " + gd.getFc().getIntegerValue(x, y);
							else
								headerZeile = headerZeile + " " + gd.getFc().contentCell(x, y);
						}
					}
				}
				HeaderEntry test = new HeaderEntry(x, gd.getSf().stringHeaderChanger(headerZeile));
				sd.attributeList.add(test);
			}
		}
	}
}
