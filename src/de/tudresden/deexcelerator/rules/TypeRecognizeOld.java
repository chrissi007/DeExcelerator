package de.tudresden.deexcelerator.rules;

import de.tudresden.deexcelerator.data.AnalyzeInformation;
import de.tudresden.deexcelerator.data.DocumentInformation;
import de.tudresden.deexcelerator.util.ColInformation;


/**
 * <span class="de">Erkennen der Datentypen in den Spalten.</span>
 * <span class="en">Recognize the datatypes in ths cols.</span>
 * @author Christopher Werner
 *
 */
public class TypeRecognizeOld {
	
	/**
	 * <span class="de">Aufgrundlage zuerst von CellType und danach auf Umrechnungen erkennen der Spaltentypen.</span>
	 * <span class="en">Find the data types, because of the cell types in the cols.</span>
	 * @param gd (<span class="de">Verwendete DocumentInformation</span>
     * <span class="en">the used document information</span>)
     * @param sd (<span class="de">AnalyseInformation</span>
     * <span class="en">analyze information</span>)
	 */
	public void action (DocumentInformation gd, AnalyzeInformation sd) {
		for (int x = sd.colStart; x<sd.colEnd ; x++)
			if (!sd.emptyCols.contains(x)) {
				int doubleAnzahl = 0;
				int doubleLaenge = 0;
				int intBigintAnzahl = 0;
				int intBigintLaenge = 0;
				int dateAnzahl = 0;
				int stringAnzahl = 0;
				int stringLaenge = 0;
				boolean mussStringSein = false;
				for (int y = sd.headerFinish; y<sd.rowEnd ; y++)
					if (!sd.emptyRows.contains(y)) { 
						if (gd.getFc().emptyCell(x, y))
							continue;
						else if (gd.getFc().numberCell(x, y)){
							String stringWert = gd.getFc().contentCell(x, y);
							int intWert = gd.getFc().getIntegerValue(x, y);
							double doubleWert = gd.getFc().getDoubleValue(x, y);
							if (doubleWert > intWert) {
								doubleAnzahl++;
								String umgewandelt = String.valueOf(doubleWert);
								if (umgewandelt.length() > doubleLaenge)
									doubleLaenge = umgewandelt.length();
							} else {
								String umgewandelt = String.valueOf(intWert);
								if (umgewandelt.length()>=stringWert.length()) {
									intBigintAnzahl++;
									if (umgewandelt.length() > intBigintLaenge)
										intBigintLaenge = umgewandelt.length();
								} else {
									mussStringSein = true;
									if (gd.getFc().contentCell(x, y).length()>stringLaenge)
										stringLaenge = gd.getFc().contentCell(x, y).length();
								}
							}
						} else if (gd.getFc().dateCell(x, y)){
							dateAnzahl++;
						} else {
							stringAnzahl++;
							if (gd.getFc().contentCell(x, y).length()>stringLaenge)
								stringLaenge = gd.getFc().contentCell(x, y).length();
						}
					}
				if (doubleAnzahl > 0 && !mussStringSein) {
					ColInformation z = new ColInformation(1, x, doubleLaenge, null);
					sd.colInfos.add(z);
				} else if (intBigintAnzahl > 0 && !mussStringSein) {
					ColInformation z = new ColInformation(2, x, intBigintLaenge, null);
					sd.colInfos.add(z);
				} else if (dateAnzahl > 0) {
					ColInformation z = new ColInformation(3, x, 0, null);
					sd.colInfos.add(z);
				} else if (stringAnzahl > 0 ||  mussStringSein) {
					ColInformation z = new ColInformation(4, x, stringLaenge, null);
					sd.colInfos.add(z);
				}
			}	
	}
}
