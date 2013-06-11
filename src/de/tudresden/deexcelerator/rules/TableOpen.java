package de.tudresden.deexcelerator.rules;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;



import de.tudresden.deexcelerator.core.TableAnalysis;
import de.tudresden.deexcelerator.data.AnalyzeInformation;
import de.tudresden.deexcelerator.data.DocumentInformation;
import de.tudresden.deexcelerator.output.OutputObject;

/**
 * <span class="de">F&uumlhrt Tabellen in die Tabellenanalyse.</span>
 * <span class="en">Adds tables to the table analyzing.</span>
 * @author Christopher Werner
 *
 */
public class TableOpen {

	/**
	 * <span class="de">F&uumlhrt je nach erkannten Tabellenanfaengen die Tabellen in die Tabellenanalyse.</span>
	 * <span class="en">Add the tables, because of the find starts to the table analysis.</span>
	 * @param gd (<span class="de">Verwendete DocumentInformation</span>
     * <span class="en">the used document information</span>)
     * @param sd (<span class="de">AnalyseInformation</span>
     * <span class="en">analyze information</span>)
	 * @return <span class="de">Liste von OutputObjecten</span>
	 * <span class="en">List of OutputObjects.</span>
	 */
	public List<OutputObject> action (DocumentInformation gd, AnalyzeInformation sd) {
		List<OutputObject> outs = new ArrayList<OutputObject>();
		int counter = 0;
		sd.relationStarts.add(sd.rowEnd);
		
		Collections.sort(sd.relationStarts);
				
		for (int i = 0; i < sd.relationStarts.size(); i++) {
			int start = sd.relationStarts.get(i);
			if (i+1 == sd.relationStarts.size())
				break;
			int end = sd.relationStarts.get(i+1);
			//Aufruf der Table Analysis mit sd veraendern
			AnalyzeInformation sdnew = sd.copySpecificData(start, end);
			sdnew.tableNumber = counter;
			counter++;
			TableAnalysis ta = new TableAnalysis(gd,sdnew);
			OutputObject oo = ta.ruleSequence();
			if (oo != null)
				outs.add(oo);
			//End Aufruf
		}

		return outs;
	}
}
