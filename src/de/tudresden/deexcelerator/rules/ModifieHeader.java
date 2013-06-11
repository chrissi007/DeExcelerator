package de.tudresden.deexcelerator.rules;

import de.tudresden.deexcelerator.data.AnalyzeInformation;
import de.tudresden.deexcelerator.data.DocumentInformation;

/**
 * <span class="de">Modifiziert die Headerlinie nach oben.</span>
 * <span class="en">Modified the headline to top.</span>
 * @author Christopher Werner
 *
 */
public class ModifieHeader {
	
	/**
	 * <span class="de">Vermindert die Headerlinie falls Zeilen dar&uumlber die nur am Anfang einen Inhalt enthalten.</span> 
	 * <span class="en">Decrement the headerline if rows over the headerline only contain one content cell.</span>
	 * @param header1 (<span class="de">jetzige Headerlinie</span>
	 * <span class="en">old headerline</span>)
	 * @param gd (<span class="de">Verwendete DocumentInformation</span>
     * <span class="en">the used document information</span>)
     * @param sd (<span class="de">AnalyseInformation</span>
     * <span class="en">analyze information</span>)
	 * @return <span class="de">Intwert der neuen Headerlinie</span>
	 * <span class="en">new headerline</span>
	 */
	public int action (int header1, DocumentInformation gd, AnalyzeInformation sd) {
		boolean schleife = true;
		boolean start = true;
		//laufe solange durch bis keine leere Zeile mit einer befüllten Zelle mehr darüber existiert
		while (schleife && header1>0) {
			for (int x = sd.colStart; x<sd.colEnd; x++)
				if (!sd.emptyCols.contains(x)) {
					if (start)
						start = false;
					else
						if (!gd.getFc().emptyCell(x, header1-1))
							schleife = false;
				}
			if (schleife) {
				header1--;
			}
			start = true;
		}
		return header1;
	}
}
