package de.tudresden.deexcelerator.rules;

import java.util.ArrayList;

import de.tudresden.deexcelerator.data.AnalyzeInformation;
import de.tudresden.deexcelerator.data.DocumentInformation;
import de.tudresden.deexcelerator.util.HeaderEntry;

/**
 * <span class="de">Potenzielle Summenspalten finden.</span>
 * <span class="en">Find potential sum cols.</span>
 * @author Christopher Werner
 *
 */
public class WordTotalCols {
	
	/**
	 * <span class="de">Spalten finden in denen eine Summe auftreten k&oumlnnte.
	 * Nach Identifikationsworten suchen.</span>
	 * <span class="en">Find a col, where a sum can be in.
	 * Proofs every row of some identification words.</span>
	 * @param gd (<span class="de">Verwendete DocumentInformation</span>
     * <span class="en">the used document information</span>)
     * @param sd (<span class="de">AnalyseInformation</span>
     * <span class="en">analyze information</span>)
	 */
	public void action (DocumentInformation gd, AnalyzeInformation sd) {
		sd.rememberTotalCols = new ArrayList<Integer>();
		for (int i = 0; i<sd.attributeList.size(); i++) {
			HeaderEntry test = sd.attributeList.get(i);
			if (test.getAttribut().toLowerCase().contains("total")) {
				sd.rememberTotalCols.add(test.getSpalte());
			}
		}
		if (sd.headerFinish - 1 < 0)
			return;
		int allCounter = 0;
		int bigBold = 0;
		int bigCounter = 0;
		for (int x = sd.colStart; x < sd.colEnd; x++)
			if (!sd.emptyCols.contains(x)) {
				int bold = gd.getFc().fontBoldWeight(x, sd.headerFinish - 1);
				if (bold > bigBold) {
					bigBold = bold;
					bigCounter = 1;
				} else if (bold == bigBold)
					bigCounter++;
				allCounter++;
			}
		if (bigCounter > allCounter/2)
			return;
		for (int x = sd.colStart; x < sd.colEnd; x++)
			if (!sd.emptyCols.contains(x)) {
				int bold = gd.getFc().fontBoldWeight(x, sd.headerFinish - 1);
				if (bold == bigBold)
					sd.rememberTotalCols.add(x);
			}
	}
}
