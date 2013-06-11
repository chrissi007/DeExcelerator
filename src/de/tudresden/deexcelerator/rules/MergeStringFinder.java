package de.tudresden.deexcelerator.rules;

import de.tudresden.deexcelerator.data.AnalyzeInformation;
import de.tudresden.deexcelerator.data.DocumentInformation;
import de.tudresden.deexcelerator.util.RangeMatrix;

/**
 * <span class="de">Sucht Metadaten anhand von Mergezellen.
 * Mergezellen m&uumlssen &uumlber komplette Zeile oder Spalte gehen.</span>
 * <span class="en">Search meta data in ranges, which goes over 
 * the complete table cols.</span>
 * @author Christopher Werner
 *
 */
public class MergeStringFinder {
	
	/**
	 * <span class="de">Pr&uumlft ob Mergezelle &uumlber komplette Zelle oder Spalte geht.
	 * Wenn ja dann als Metadaten speichern.
	 * Zeile oder Spalten den Leeren Zeilen oder Spalten hinzuf&uumlgen.</span>
	 * <span class="en">Proof if reanges go over all cols. This ranges contain meta
	 * data.</span>
	 * @param gd (<span class="de">Verwendete DocumentInformation</span>
     * <span class="en">the used document information</span>)
     * @param sd (<span class="de">AnalyseInformation</span>
     * <span class="en">analyze information</span>)
	 */
	public void action (DocumentInformation gd, AnalyzeInformation sd) {
		RangeMatrix[] mergeZellen = gd.getFc().mergeCells();
		int lengeRange = mergeZellen.length;
		for (int z = 0; z<lengeRange; z++)
		{
			RangeMatrix mergeZelle = mergeZellen[z];
			int rowBottomRight = mergeZelle.getBottomRightY();
			int colBottomRight = mergeZelle.getBottomRightX();
			int rowTopLeft = mergeZelle.getTopLeftY();
			int colTopLeft = mergeZelle.getTopLeftX();
			if (rowBottomRight - rowTopLeft == sd.rowNumber - 1)
			{
				for (int x = colTopLeft;x<=colBottomRight; x++ )
				{
					sd.emptyCols.add(x);
				}
				sd.metaDataTable.add(gd.getFc().contentCell(colTopLeft, rowTopLeft));
			}
			if (colBottomRight - colTopLeft == sd.colNumber - 1)
			{
				for (int x = rowTopLeft; x<=rowBottomRight; x++ )
				{
					sd.emptyRows.add(x);
				}
				sd.metaDataTable.add(gd.getFc().contentCell(colTopLeft, rowTopLeft));
			}
		}
	}
}
