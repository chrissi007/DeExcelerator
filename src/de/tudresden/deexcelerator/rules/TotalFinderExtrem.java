package de.tudresden.deexcelerator.rules;

import java.util.ArrayList;
import java.util.List;

import de.tudresden.deexcelerator.data.AnalyzeInformation;
import de.tudresden.deexcelerator.data.DocumentInformation;
import de.tudresden.deexcelerator.util.ColInformation;

/**
 * <span class="de">Sucht Summenzeilen.</span>
 * <span class="en">Search sum rows in the table.</span>
 * @author Christopher Werner
 *
 */
public class TotalFinderExtrem {

	/**
	 * <span class="de">Pr&uumlft jede Zeilen mit der Summe der darunter liegenden.
	 * Muss bei Spalten stimmen damit eine Zeile als Summenzeile durchgeht.</span>
	 * <span class="en">Find sum rows and gives a list of them back.
	 * Proof all possibilities of row connections.
	 * Should only used if there are only some rows.</span>
	 * @param liste (<span class="de">die nur Int und Double Zeilen auflistet</span>
	 * <span class="en">list with int or double rows</span>)
	 * @param gd (<span class="de">Verwendete DocumentInformation</span>
     * <span class="en">the used document information</span>)
     * @param sd (<span class="de">AnalyseInformation</span>
     * <span class="en">analyze information</span>)
	 * @return <span class="de">Liste der gefundenen Summenzeilen</span>
	 * <span class="en">List of found sum rows.</span>
	 */
	public List<Integer> action (List<ColInformation> liste, DocumentInformation gd, AnalyzeInformation sd) {
		//-----------------------------------------
		//veraenderbare Werte:
		//Abweichung zur berechneten Summe:
		//-----------------------------------------
		List<Integer> totalZeilenListe = new ArrayList<Integer>();
		//nur auf Zeilen anwenden in denen das Wort "Total" erkannt wurde
		for (int i = sd.headerFinish; i<sd.rowEnd; i++) {
			if (!sd.emptyRows.contains(i)) {
				//Spalten durchlaufen
				int anzahl = 0;
				for (int j = 0; j<liste.size(); j++) {
					Double totalWert = gd.getFc().getDoubleValue(liste.get(j).getSpaltenNummer(),i);
					if (totalWert == null || totalWert == 0)
						continue;
					double summe = 0;
					//Zeilen ueberhalb der TotalZeile durchlaufen bis Summe gefunden
					for (int y = i+1; y<sd.rowEnd; y++)
						if (!sd.emptyRows.contains(y)) {
							Double cellWert = gd.getFc().getDoubleValue(liste.get(j).getSpaltenNummer(),y);
							if (cellWert != null)
								summe+=cellWert;
							if (totalWert == summe) {
								anzahl++;
								break;
							} else if (totalWert < summe)
								break;
						}
				}
				if (anzahl >= 4) {
					totalZeilenListe.add(i);
				}
			}
		}
		return totalZeilenListe;
	}
}
