package de.tudresden.deexcelerator.rules;

import java.util.Date;
import java.util.Iterator;

import de.tudresden.deexcelerator.data.AnalyzeInformation;
import de.tudresden.deexcelerator.data.DocumentInformation;
import de.tudresden.deexcelerator.util.Database;
import de.tudresden.deexcelerator.util.SplittedCells;

/**
 * <span class="de">Einf&uumlgen in PostgreSQL.</span>
 * <span class="en">Insert the relation in postgreSQL.</span>
 * @author Christopher Werner
 *
 */
public class NewTableInDb {
	
	/**
	 * <span class="de">Anlegen einer neuen Relation in PostgreSQL mit Relationsnamen.
	 * Einf&uumlgen der Tupel in die Relation.</span>
	 * <span class="en">Create a new relation in postgreSQL with the relation name
	 * and insert the new tuple.</span>
	 * @param gd (<span class="de">Verwendete DocumentInformation</span>
     * <span class="en">the used document information</span>)
     * @param sd (<span class="de">AnalyseInformation</span>
     * <span class="en">analyze information</span>)
	 */
	public void action (DocumentInformation gd, AnalyzeInformation sd) {
		Database database = gd.getCp().d;
        database.loadJdbcDriver ();
        //dbConnection oeffnen
        database.openConnection ();
        //query senden
        sd.relationName = gd.getSf().stringChangerUnterstrich(sd.relationName.trim());
        String query = "CREATE TABLE " + sd.relationName;
        query = query + "( TID SERIAL PRIMARY KEY";
        int i = 1;
        for (int z = 0; z<sd.attributeList.size(); z++) {
        	sd.attributeList.get(z).setAttribut(gd.getSf().stringChangerUnterstrich(sd.attributeList.get(z).getAttribut()));
        	sd.attributeList.get(z).setAttribut(gd.getSf().stringSmaller(sd.attributeList.get(z).getAttribut()));
        }
        for (int j = 0; j<sd.attributeList.size(); j++) {
        	for (int h = j; h<sd.attributeList.size(); h++) {
        		if (sd.attributeList.get(j).getSpalte() != sd.attributeList.get(h).getSpalte()) {
        			if (sd.attributeList.get(j).getAttribut().equals(sd.attributeList.get(h).getAttribut())) {
        				sd.attributeList.get(j).setAttribut(sd.attributeList.get(j).getAttribut()+i);
        				i++;
        			}
        		}
        	}
        }
        i = 1;
        for (int x = sd.colStart; x<sd.colEnd; x++) {
        	if (!sd.emptyCols.contains(x)) {
        		String attribut = "";
        		for (int j = 0; j<sd.attributeList.size(); j++)
        			if (sd.attributeList.get(j).getSpalte() == x)
        				attribut = gd.getSf().stringChangerUnterstrich(sd.attributeList.get(j).getAttribut());
        		if (attribut == "") {
        			attribut = "Attribut_" + i;
        		}
        		String typeQuery = "";
        		for (int h = 0; h<sd.colInfos.size(); h++)
        			if (sd.colInfos.get(h).getSpaltenNummer() == x) {
        				int type = sd.colInfos.get(h).getFormat();
        				if (type == 1) {
        					//double
        					typeQuery = "double precision";
        				} else if (type == 2) {
        					//int
        					typeQuery = "integer";
        				} else if (type == 3) {
        					//date
        					typeQuery = "date";
        				} else if (type == 4) {
        					//string
        					typeQuery = "varchar(" + sd.colInfos.get(h).getLenght() + ")";
        				}
        			}
        		query = query + ", " + attribut + " " + typeQuery;
        		i++;
        	}
        }
        query = query + ")";
        database.sendStatement(query);
        int zeilenAnzahl = 1;
        for (int y = sd.headerFinish; y<sd.rowEnd; y++)
        	if (!sd.emptyRows.contains(y)) {
        		String queryTableInsert = "INSERT INTO " + sd.relationName + " VALUES (" + zeilenAnzahl;
            	for (int x = sd.colStart; x<sd.colEnd; x++)
        			if (!sd.emptyCols.contains(x)) {
        				if (!gd.getFc().emptyCell(x, y)) {
	        				for (int h = 0; h<sd.colInfos.size(); h++)
	        					if (sd.colInfos.get(h).getSpaltenNummer() == x) {
	        						if (sd.colInfos.get(h).getFormat() == 2) {
	        							int wert = gd.getFc().getIntegerValue(x, y);	        							
										queryTableInsert = queryTableInsert + ", " + wert;
	        						}
		        					else if (sd.colInfos.get(h).getFormat() == 3) {
		    							Date dateWert = gd.getFc().getDateValue(x, y);
		        						queryTableInsert = queryTableInsert + ", '" + dateWert + "'";
		        					} 
		        					else if (sd.colInfos.get(h).getFormat() == 1) {
		    							double numberWert = gd.getFc().getDoubleValue(x, y);
		        						queryTableInsert = queryTableInsert + ", " + numberWert;
		        					}
		        					else
		        						queryTableInsert = queryTableInsert + ", " + gd.getSf().stringChangerHochkomma(gd.getFc().contentCell(x, y));
	        					}
        				} else {
        					String inhalt = "";
							for (Iterator<SplittedCells> iter = sd.mergeCells.iterator(); iter.hasNext();) {
								SplittedCells sz = iter.next();
								if (x<=sz.getX()+sz.getXrange() && x>=sz.getX() && y<=sz.getY()+sz.getYrange() && y>=sz.getY())
									inhalt = sz.getContent();
							}
							if (inhalt.equals(""))
								queryTableInsert = queryTableInsert	+ ", null";
							else
								queryTableInsert = queryTableInsert + ", '" + inhalt + "'";
        				}
        			}
        		queryTableInsert = queryTableInsert + ")";
        		database.sendStatement(queryTableInsert);
        		zeilenAnzahl++;
        	}
        				
        /*database.sendStatement("CREATE TABLE films (" +
        		"    code        char(5) CONSTRAINT firstkey PRIMARY KEY," +
        		"    title       varchar(40) NOT NULL," +
        		"    did         integer NOT NULL," +
        		"    date_prod   date," +
        		"    kind        varchar(10)," +
        		"    len         interval hour to minute" +
        		")"
        		);*/
        //dbConnection schliessen
        database.closeConnection ();
	}
}
