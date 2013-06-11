package de.tudresden.deexcelerator.rules;

import java.util.ArrayList;
import java.util.List;

import de.tudresden.deexcelerator.data.AnalyzeInformation;
import de.tudresden.deexcelerator.data.DocumentInformation;

/**
 * <span class="de">Bestimmen des Relationsnamens.</span>
 * <span class="en">Find the relation name.</span>
 * @author Christopher Werner
 *
 */
public class RelationName {
	
	/**
	 * <span class="de">Sucht in &Uumlberschriften nach dem Relationsnamen.</span>
	 * <span class="en">Search in the header of the table the relation name.</span>
	 * @param gd (<span class="de">Verwendete DocumentInformation</span>
     * <span class="en">the used document information</span>)
     * @param sd (<span class="de">AnalyseInformation</span>
     * <span class="en">analyze information</span>)
	 * @return <span class="de">Relationsname sonst leerer String</span>
	 * <span class="en">relation name or empty string</span>
	 */
	public String action (DocumentInformation gd, AnalyzeInformation sd) {
		List<String> ueber = new ArrayList<String>();
		int y = sd.rowStart;
		int boldOld = -1;
		int boldNew = -1;
		String entWert = "";
		//Lauf von Oben nach unten durch
		while (y<sd.rowEnd) {
			String wert = "";
			int test = 0;
			for (int x=sd.colStart; x<sd.colEnd; x++)
				if (!sd.emptyCols.contains(x)) {
					if (!gd.getFc().emptyCell(x, y)) {
						test++;
						wert = gd.getFc().contentCell(x, y);
						boldNew = gd.getFc().fontBoldWeight(x, y);
					}
				}
			if (test == 1) {
				//String Liste Wert adden
				boolean help = true;
				if (wert.toLowerCase().contains("table") && wert.length() < 10)
					help = false;
				if (boldNew > boldOld && help) {
					boldOld = boldNew;
					sd.relationName = wert;
					entWert = wert;
				}
				ueber.add(wert);
			} else if (test > 1)
				break;
			y++;
		}
		//Nix gefunden setze Default Wert
		if (ueber.size() == 0) {
			sd.relationName = "Tabelle1";
			return "";
		}
		return entWert;
	}
}
