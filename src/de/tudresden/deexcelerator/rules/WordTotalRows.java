package de.tudresden.deexcelerator.rules;

import java.util.ArrayList;

import de.tudresden.deexcelerator.data.AnalyzeInformation;
import de.tudresden.deexcelerator.data.DocumentInformation;

/**
 * <span class="de">Potenzielle Summenzeilen finden.</span>
 * <span class="en">Find potential sum rows.</span>
 * @author Christopher Werner
 *
 */
public class WordTotalRows {
	
	/**
	 * <span class="de">Zeilen finden in denen eine Summe auftreten k&oumlnnte.
	 * Nach Identifikationsworten suchen.</span>
	 * <span class="en">Find a row, where a sum can be in.
	 * Proofs every row of some identification words.</span>
	 * @param gd (<span class="de">Verwendete DocumentInformation</span>
     * <span class="en">the used document information</span>)
     * @param sd (<span class="de">AnalyseInformation</span>
     * <span class="en">analyze information</span>)
	 */
	public void action (DocumentInformation gd, AnalyzeInformation sd) {
		sd.rememberTotalRows = new ArrayList<Integer>();
		//Zeilendurchlaufen
		for (int y = sd.headerFinish; y<sd.rowEnd; y++) {
			if (!sd.emptyRows.contains(y)) {
				//pruefen nach "Total"
				if (gd.getFc().contentCell(sd.colStart,y).toLowerCase().contains("total")) {
					sd.rememberTotalRows.add(y);
				}
			}
		}
	}
}
