package de.tudresden.deexcelerator.rules;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.tudresden.deexcelerator.data.AnalyzeInformation;
import de.tudresden.deexcelerator.data.DocumentInformation;
import de.tudresden.deexcelerator.util.ColInformation;


/**
 * <span class="de">Sucht Summenzeilen.</span>
 * <span class="en">Search sum rows in the table.</span>
 * @author Christopher Werner
 *
 */
public class TotalFinderRowsDown {
	
	/**
	 * <span class="de">Funktion findet Summenzeilen und gibt diese zur&uumlck.
	 * Pr&uumlft ob Summe darunter liegt.</span>
	 * <span class="en">Find sum rows and gives a list of them back.
	 * Look if sum is under the row.</span>
	 * @param liste (<span class="de">die nur Int und Double Zeilen auflistet</span>
	 * <span class="en">list with int or double rows</span>)
	 * @param gd (<span class="de">Verwendete DocumentInformation</span>
     * <span class="en">the used document information</span>)
     * @param sd (<span class="de">AnalyseInformation</span>
     * <span class="en">analyze information</span>)
	 * @return <span class="de">Liste der gefundenen Summenzeilen</span>
	 * <span class="en">List of found sum rows.</span>
	 */
	public Map<Integer,Integer> action (List<ColInformation> liste, DocumentInformation gd, AnalyzeInformation sd) {
		//-----------------------------------------
		//veraenderbare Werte:
		//Abweichung zur berechneten Summe:
		double abweichung = gd.getCp().deviationRow;
		//-----------------------------------------
		Map<Integer,Integer> totalZeilenMap = new HashMap<Integer,Integer>();
		//nur auf Zeilen anwenden die Kandidat sind
		for (int i = 0; i<sd.rememberTotalRows.size(); i++) {
			if (!sd.emptyRows.contains(sd.rememberTotalRows.get(i))) {
				int totalZeile = sd.rememberTotalRows.get(i);
				//Spalten durchlaufen
				int anzahl = 0;
				int vergleichsAnzahl = 0;
				for (int j = 0; j<liste.size(); j++) {
					Double totalWert = gd.getFc().getDoubleValue(liste.get(j).getSpaltenNummer(),totalZeile);
					if (totalWert == null || totalWert == 0)
						continue;
					double summe = 0;
					//Zeilen unterhalb der TotalZeile durchlaufen bis Summe gefunden
					for (int y = totalZeile+1; y<sd.rowEnd; y++)
						if (!sd.emptyRows.contains(y)) {
							Double cellWert = gd.getFc().getDoubleValue(liste.get(j).getSpaltenNummer(),y);
							if (cellWert == null || cellWert == 0)
								continue;
							summe+=cellWert;
							if (totalWert - abweichung <= summe && totalWert + abweichung >= summe) {
								anzahl++;
								vergleichsAnzahl++;
								break;
							} else if (totalWert < summe) {
								vergleichsAnzahl++;
								break;
							}
						}
				}
				if (anzahl > liste.size()/2)
					totalZeilenMap.put(totalZeile, 100/vergleichsAnzahl*anzahl);
			}
		}
		return totalZeilenMap;
	}
}
