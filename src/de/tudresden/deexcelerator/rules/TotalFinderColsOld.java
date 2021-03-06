package de.tudresden.deexcelerator.rules;

import java.util.ArrayList;
import java.util.List;

import de.tudresden.deexcelerator.data.AnalyzeInformation;
import de.tudresden.deexcelerator.data.DocumentInformation;
import de.tudresden.deexcelerator.util.ColInformation;
import de.tudresden.deexcelerator.util.Pairs;


/**
 * <span class="de">Summenzeilen erkennen.
 * Veraltet, zu hoher Aufwand.</span>
 * <span class="en">Search sum cols in the table.
 * With a big outlay. (OLD)</span>
 * @author Christopher Werner
 *
 */
public class TotalFinderColsOld {
	
	/**
	 * <span class="de">Funktion findet Summenspalten und gibt diese zur&uumlck.
	 * Vergleicht alle Spalten miteinander und bildet die Summen.</span>
	 * <span class="en">Find sum cols and gives a list of them back.
	 * Proof all possibilities of col connections.
	 * Should only used if there are only some cols.</span>
	 * @param liste (<span class="de">die nur Int und Double Zeilen auflistet</span>
	 * <span class="en">list with int or double rows</span>)
	 * @param gd (<span class="de">Verwendete DocumentInformation</span>
     * <span class="en">the used document information</span>)
     * @param sd (<span class="de">AnalyseInformation</span>
     * <span class="en">analyze information</span>)
	 * @return <span class="de">Liste der gefundenen Summenspalten</span>
	 * <span class="en">List of found sum cols.</span>
	 */
	public List<Integer> action (List<ColInformation> liste, DocumentInformation gd, AnalyzeInformation sd) {
		//-----------------------------------------
		//veraenderbare Werte:
		//Abweichung zur berechneten Summe:
		double abweichung = gd.getCp().deviationCol;
		//Anzahl der Summanden die benoetigt werden um eine Summe zu bilden
		int summandenAnzahl = gd.getCp().summandNumber;
		//Summe muss ne bestimmt Groesse haben
		int groesseSumme = gd.getCp().sizeSumCol;
		//-----------------------------------------
		List<Pairs> paarListe = new ArrayList<Pairs>();
		List<Integer> totalSpaltenListe = new ArrayList<Integer>();
		if (liste.size()>6)
			return totalSpaltenListe;
		//Zeilen Durchlaufen			
		for (int y = sd.headerFinish; y<sd.rowEnd; y++) {
			if (!sd.emptyRows.contains(y))
				//erzeugt alle moeglichen mussten mit denen Summen gebildet werden koennen
				for (int i = 3; i<Math.pow(2, liste.size()); i++) {
					double summe = 0;
					int counter = 0;
					String hilf = Integer.toBinaryString(i);
					hilf = "00000000000" + hilf;
					//es wird nur Summe auf den Int und Double Spalten gebildet
					for (int j = 0; j<liste.size(); j++) {
						char a = hilf.charAt(hilf.length()-j-1);
						if (a == '1') {
							Double cellWert = gd.getFc().getDoubleValue(liste.get(j).getSpaltenNummer(),y);
							if (cellWert == null)
								continue;
							summe+=cellWert.doubleValue();
							if (cellWert != 0)
								counter++;
						}
					}
					//wenn eine bestimmt summandenAnzahl erreicht ist pruefe ob die Summe auch einmal in der Spalte vorkommt
					if (counter >= summandenAnzahl && summe>groesseSumme) 
						for (int j = 0; j<liste.size(); j++) {
							Double wert = gd.getFc().getDoubleValue(liste.get(j).getSpaltenNummer(),y);
							if (wert == null)
								continue;
							if (wert.doubleValue() - abweichung <= summe && wert.doubleValue() + abweichung >= summe) {
								boolean nichtDrin = true;
								//LeerSpalte gefunden jetzt erhoehe entweder bei ihr den Wert
								for (int h = 0; h<paarListe.size(); h++) {
									Pairs psw = paarListe.get(h);
									if (psw.x == liste.get(j).getSpaltenNummer()) {
										psw.addY();
										nichtDrin = false;
									}
								} 
								//oder lege sie neu an
								if (nichtDrin) {
									Pairs psw = new Pairs();
									psw.x = liste.get(j).getSpaltenNummer();
									psw.y = 1;
									paarListe.add(psw);
								}
							}
						}					
				}
		}
		//Paar Liste durschauen und ueberlegen ob Spalten zu LeerSpalten hinzugefuegt werden
		for (int h = 0; h<paarListe.size(); h++) { 
			int vergleichsWert = sd.rowEnd - sd.headerFinish;
			Pairs psw = paarListe.get(h);
			if (psw.y > vergleichsWert/2)
				totalSpaltenListe.add(psw.x);
		}
		//gib die Spalten zurueck die wirklich TotalSpalten sind
		return totalSpaltenListe;
	}
}
