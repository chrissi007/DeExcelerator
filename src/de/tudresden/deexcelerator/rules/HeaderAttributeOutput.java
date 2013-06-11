package de.tudresden.deexcelerator.rules;

import java.util.Iterator;

import de.tudresden.deexcelerator.data.AnalyzeInformation;
import de.tudresden.deexcelerator.data.DocumentInformation;
import de.tudresden.deexcelerator.util.HeaderEntry;

/**
 * <span class="de">Gibt Headerattribute in der Console aus.</span>
 * <span class="en">Gives out the header attributes in the console.</span>
 * @author Christopher Werner
 *
 */
public class HeaderAttributeOutput {
	
	/**
	 * <span class="de">Gibt Headerattribute in der Console aus.</span>
	 * <span class="en">Gives out the header attributes in the console.</span>
	 * @param gd (<span class="de">Verwendete DocumentInformation</span>
     * <span class="en">the used document information</span>)
     * @param sd (<span class="de">AnalyseInformation</span>
     * <span class="en">analyze information</span>)
	 */
	public void action (DocumentInformation gd, AnalyzeInformation sd) {
		for (Iterator<HeaderEntry> iter = sd.attributeList.iterator(); iter.hasNext();) {
			HeaderEntry test = iter.next();
			System.out.println("Header Attribut Liste: " + test.getAttribut() + " " + test.getSpalte() + ". Attribut");
		}
	}

}
