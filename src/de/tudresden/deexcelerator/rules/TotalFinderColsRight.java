package de.tudresden.deexcelerator.rules;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.tudresden.deexcelerator.data.AnalyzeInformation;
import de.tudresden.deexcelerator.data.DocumentInformation;
import de.tudresden.deexcelerator.util.ColInformation;

/**
 * <span class="de">Sucht Summenspalten.</span>
 * <span class="en">Search sum cols in the table.</span>
 * @author Christopher Werner
 *
 */
public class TotalFinderColsRight {

	/**
	 * <span class="de">Funktion findet Summenspalten und gibt diese zur&uumlck.
	 * Pr&uumlft, ob Summe rechts liegt.</span>
	 * <span class="en">Find sum cols and gives a list of them back.
	 * Look if sum is right the col.</span>
	 * @param liste (<span class="de">die nur Int und Double Zeilen auflistet</span>
	 * <span class="en">list with int or double rows</span>)
	 * @param gd (<span class="de">Verwendete DocumentInformation</span>
     * <span class="en">the used document information</span>)
     * @param sd (<span class="de">AnalyseInformation</span>
     * <span class="en">analyze information</span>)
	 * @return <span class="de">Liste der gefundenen Summenspalten</span>
	 * <span class="en">List of found sum cols.</span>
	 */
	public Map<Integer, Integer> action (List<ColInformation> liste, DocumentInformation gd, AnalyzeInformation sd) {
		//-----------------------------------------
		//veraenderbare Werte:
		//Abweichung zur berechneten Summe:
		double abweichung = gd.getCp().deviationCol;
		//-----------------------------------------
		Map<Integer, Integer> totalZeilenMap = new HashMap<Integer, Integer>();
		//nur auf Zeilen anwenden in denen das Wort "Total" erkannt wurde
		for (int i = 0; i<sd.rememberTotalCols.size(); i++) {
			if (!sd.emptyCols.contains(sd.rememberTotalCols.get(i))) {
				int totalSpalte = sd.rememberTotalCols.get(i);
				//Zeilen durchlaufen
				//negatives ergebnis
				int anzahl = 0;
				//positives ergebnis
				int vergleichsAnzahl = 0;
				for (int y = sd.rowStart; y<sd.rowEnd; y++) {
					if (!sd.emptyRows.contains(y)) {
						Double findSum = gd.getFc().getDoubleValue(totalSpalte, y);
						if (findSum == null || findSum == 0)
							continue;
						double sum = 0;
						//abweichung bestimmen
						if (findSum > 50000)
							abweichung = 5;
						else
							abweichung = gd.getCp().deviationCol;
						for (int x = totalSpalte+1; x<sd.colEnd; x++) {
							if (!sd.emptyCols.contains(x) && gd.getFc().numberCell(x, y)) {
								Double totalWert = gd.getFc().getDoubleValue(x, y);
								if (totalWert == null || totalWert == 0)
									continue;
								sum = sum + totalWert;
								if (sum < findSum - abweichung)
									continue;
								if (sum > findSum + abweichung)
									anzahl++;
								else
									vergleichsAnzahl++;
								break;
							}
						}
					}
				}
				if (vergleichsAnzahl > anzahl)
					totalZeilenMap.put(totalSpalte, 100/(vergleichsAnzahl+anzahl)*vergleichsAnzahl);
			}
		}
		return totalZeilenMap;
	}
}
