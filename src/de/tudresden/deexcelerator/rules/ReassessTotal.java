package de.tudresden.deexcelerator.rules;

import java.util.ArrayList;
import java.util.List;

import de.tudresden.deexcelerator.data.AnalyzeInformation;
import de.tudresden.deexcelerator.data.DocumentInformation;

/**
 * <span class="de">Sucht Zusammengef&uumlgte Zellen.</span>
 * <span class="en">Search merged cells.</span>
 * @author Christopher Werner
 *
 */
public class ReassessTotal {

	/**
	 * <span class="de">Sucht zusammengef&uumlgte Zellen und gibt diese aus.
	 * Sucht in der ersten Spalte nach solchen Zellen.
	 * Fügt diese dann den Totalzeilen hinzu um sie danach zu pr&uumlfen.</span>
	 * <span class="en">Search merged cells and gives them back.
	 * Afterward proof them for total rows.</span>
	 * @param gd (<span class="de">Verwendete DocumentInformation</span>
     * <span class="en">the used document information</span>)
     * @param sd (<span class="de">AnalyseInformation</span>
     * <span class="en">analyze information</span>)
	 * @return <span class="de">Liste der gefundenen Zeilen</span>
	 * <span class="en">List of identified rows.</span> 
	 */
	public List<Integer> action (DocumentInformation gd, AnalyzeInformation sd) {
		List<Integer> werte = new ArrayList<Integer>();
		int zaehler = 0;
		//Zaehlt leere Zellen in der ersten Spalte
		for (int y = sd.headerFinish; y<sd.rowEnd; y++)
			if (!sd.emptyRows.contains(y))
				if (gd.getFc().emptyCell(sd.colStart, y))
					zaehler++;
				else
					zaehler--;
		//Falls mehr nicht leere als leere Zellen dann returne leere Liste
		if (zaehler <= 0)
			return werte;
		//gehe noch einmal erste Spalte durch und fügen alle Zeilen wo ein Wert vorkommt der liste hinzu
		for (int y = sd.headerFinish; y<sd.rowEnd; y++)
			if (!sd.emptyRows.contains(y))
				if (!gd.getFc().emptyCell(sd.colStart, y))
					werte.add(y);
		return werte;
	}
}
