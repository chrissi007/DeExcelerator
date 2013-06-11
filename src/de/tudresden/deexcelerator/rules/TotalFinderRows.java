package de.tudresden.deexcelerator.rules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.tudresden.deexcelerator.data.AnalyzeInformation;
import de.tudresden.deexcelerator.data.DocumentInformation;
import de.tudresden.deexcelerator.util.ColInformation;
import de.tudresden.deexcelerator.util.Pairs;


/**
 * <span class="de">Sucht Summenzeilen.</span>
 * <span class="en">Search sum rows in the table.</span>
 * @author Christopher Werner
 *
 */
public class TotalFinderRows {

	/**
	 * <span class="de">Funktion findet Summenzeilen und gibt diese zur&uumlck.
	 * Sollte nur bei geringer Zeilenanzahl angewendet werden.
	 * Pr&uumlft alle Zeilen miteinander.</span>
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
	public Map<Integer,Integer> action (List<ColInformation> liste, DocumentInformation gd, AnalyzeInformation sd) {
		//-----------------------------------------
		//veraenderbare Werte:
		//Abweichung zur berechneten Summe:
		double abweichung = gd.getCp().deviationRow;
		//Anzahl der Summanden die benoetigt werden um eine Summe zu bilden
		int summandenAnzahl = gd.getCp().summandNumber;
		//Summe muss ne bestimmt Groesse haben
		int groesseSumme = gd.getCp().sizeSumRow;
		//-----------------------------------------
		List<Pairs> paarListe = new ArrayList<Pairs>();
		Map<Integer,Integer> totalZeilenMap = new HashMap<Integer,Integer>();		
		if (sd.rowEnd-sd.headerFinish > 10)
			return totalZeilenMap;
		//Zeilen Durchlaufen
		for (int j = 0; j<liste.size(); j++) {			
			//erzeugt alle moeglichen mussten mit denen Summen gebildet werden koennen
			for (int i = 3; i<Math.pow(2, sd.rowEnd-sd.headerFinish); i++) {
				double summe = 0;
				int counter = 0;
				String hilf = "0000000000" + Integer.toBinaryString(i);
				//es wird nur Summe auf den Int und Double Spalten gebildet
				for (int y = sd.headerFinish; y<sd.rowEnd; y++) {
					if (!sd.emptyRows.contains(y)) {
						char a = hilf.charAt(hilf.length()-y+sd.headerFinish-1);
						if (a == '1') {
							Double cellWert = gd.getFc().getDoubleValue(liste.get(j).getSpaltenNummer(),y);
							if (cellWert == null || cellWert == 0.0)
								continue;
							summe+=cellWert;
							counter++;
						}
					}
				}
				//wenn eine bestimmt summandenAnzahl erreicht ist pruefe ob die Summe auch einmal in der Spalte vorkommt
				if (counter >= summandenAnzahl && summe>groesseSumme) 
					for (int z = sd.headerFinish; z<sd.rowEnd; z++) {
						Double value = gd.getFc().getDoubleValue(liste.get(j).getSpaltenNummer(),z);
						if (!sd.emptyRows.contains(z) && value!=null && value - abweichung <= summe && value + abweichung >= summe) {
							boolean nichtDrin = true;
							//LeerSpalte gefunden jetzt erhoehe entweder bei ihr den Wert
							for (int h = 0; h<paarListe.size(); h++) {
								Pairs psw = paarListe.get(h);
								if (psw.x == z) {
									psw.addY();
									nichtDrin = false;
								}
							} 
							//oder lege sie neu an
							if (nichtDrin) {
								Pairs psw = new Pairs();
								psw.y = 1;
								psw.x = z;
								paarListe.add(psw);
							}
						}
					}
				}
		}
		//Paar Liste durschauen und ueberlegen ob Spalten zu LeerSpalten hinzugefuegt werden
		for (int h = 0; h<paarListe.size(); h++) { 
			int vergleichsWert = liste.size();
			Pairs psw = paarListe.get(h);
			if (psw.y > vergleichsWert/2)
				totalZeilenMap.put(psw.x, 100/vergleichsWert*psw.y);
		}
		//gib die Spalten zurück die wirklich TotalSpalten sind
		return totalZeilenMap;
	}
}
