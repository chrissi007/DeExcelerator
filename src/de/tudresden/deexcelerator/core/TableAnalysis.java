package de.tudresden.deexcelerator.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;



import de.tudresden.deexcelerator.data.AnalyzeInformation;
import de.tudresden.deexcelerator.data.DocumentInformation;
import de.tudresden.deexcelerator.output.OutputObject;
import de.tudresden.deexcelerator.rules.*;
import de.tudresden.deexcelerator.util.ColInformation;

/**
 * <span class="de">Tabellenanalyse Managementklasse zum Aufrufen der Regeln.</span>
 * <span class="en">Analyze one relation in the table and creates an outputobject of this.</span>
 * @author Christopher Werner
 *
 */
public class TableAnalysis {
	
	/** <span class="de">Zu nutzende Dokumentinformationen.</span>
	 * <span class="en">Using document information.</span> */
	private DocumentInformation gd;
	/** <span class="de">Zu nutzende analyse Informatione.</span>
	 * <span class="en">Using analyse information.</span> */
	private AnalyzeInformation sd;
	
	/**
	 * <span class="de">Legt die Spezifischen- und Grunddaten fest.</span>
	 * <span class="en">Constructor, which set the attributes.</span>
	 * @param gd (<span class="de">DocumentInformation Objekt</span>
	 * <span class="en">DocumentInformation object</span>)
	 * @param sd (<span class="de">AnalyseInformation Objekt</span>
	 * <span class="en">AnalyseInformation object</span>)
	 */
	public TableAnalysis (DocumentInformation gd, AnalyzeInformation sd) {
		this.gd = gd;
		this.sd = sd;
	}
	
	/**
	 * <span class="de">F&uumlhrt die sequentielle Ausf&uumlhrung der Regeln durch.</span>
	 * <span class="en">Makes the sequential execution of the rules.</span>
	 * @return <span class="de">Liste von OutputObjecten</span>
	 * <span class="en">OutputObject of the relation</span>
	 */
	public OutputObject ruleSequence () {
				
		if (!gd.getCp().onlyOneTable && gd.getCp().fs.rowsColsEliminationActivate) {
			//Eliminiert noch mal leer Spalten und Zeilen falls die Tabelle kleiner geworden ist
			RowsColsElimination zse = new RowsColsElimination();
			zse.action(gd, sd);
		}
		
		if (gd.getCp().onlyOneTable) {
			//Suche Headerlinie falls es nur eine Tabelle ist
			HeaderRecognizeNow hrn = new HeaderRecognizeNow();
			hrn.action(sd.rowStart, sd.rowEnd, gd, sd);
		}
		
		if (sd.colNumber < 2 || sd.rowNumber < 2)
			return null;
		
		//finden des Tabellen Namens
		//TabellenNamen extrahieren
		RelationName tn = new RelationName();
		String name1 = tn.action(gd, sd);
		//SheetNamen ausgeben
		String name2 = gd.getFc().sheetName();
		sd.metaDataTable.add(name2);
		if (name1.equals("")) {
			sd.relationName = name2;
		} else {
			sd.relationName = name1;
		}
				
		if (gd.getCp().fs.rowComparisonActivate) {
			//vergleicht 1. und letzte Zeile (löscht im gleichhals fall letzte)
			RowComparison rc = new RowComparison();
			rc.action(gd, sd);
		}
		
		//Löse den Headeranfang aus allen gefundenen Headerstarts
		Collections.sort(sd.headerStarts);
		for (int i = 0; i<sd.headerStarts.size(); i++) {
			if (sd.headerStarts.get(i)>sd.rowStart) {
				sd.headerFinish = sd.headerStarts.get(i);
				break;
			}
		}
		
		/*LeerZeilen und LeerSpalten speichern zur Visualisierung */
		sd.vi.emptyColsBefor.addAll(sd.emptyCols);
		sd.vi.emptyRowsBefor.addAll(sd.emptyRows);
				
		/*List die Zeilen aus die zwischen doppelt gefundenen Zeilen liegen um somit
		sinnlose Header Entfernen
		Ausserdem oben und unten Zeilen mit nur vorne einem Content entfernen*/
		if (!gd.getCp().onlyOneTable) {
			RecognizeStringsBetweenAreas stize = new RecognizeStringsBetweenAreas();
			stize.action(gd, sd);
		} else {
			RecognizeStrings rs = new RecognizeStrings();
			rs.action(gd, sd);
		}		

		if (gd.getCp().fs.colsEliminationInDataActivate) {
			//lösche Spalte im Wertebereich mit keinem Wert
			ColsEliminationInData rceid = new ColsEliminationInData();
			rceid.action(gd, sd);
		}	
		
		/* Metadaten Abspeichern und neue Leerzeilen */
		sd.vi.emptyRowsMeta.addAll(sd.emptyRows); //kan sein das nur die von meta brauchen dann noch mal in der Funktion
		sd.vi.metaDaten.addAll(sd.metaDataTable);
		
		//Erkennt die headerAttribute und fuegt diese der Liste hinzu
		HeaderAttributeRecognize hae = new HeaderAttributeRecognize();
		hae.action(gd, sd);
		
		//erkennen welche Typen die verschiedenen Spalten haben
		TypeRecognize tet = new TypeRecognize();
		tet.action(gd, sd);
		
		//Liste erstellen die nur die int und double Spalten beinhaltet
		List<ColInformation> totalErkennerListe = new ArrayList<ColInformation>();
		for (int i = 0; i< sd.colInfos.size(); i++) {
			if (sd.colInfos.get(i).getFormat() < 3) {
				totalErkennerListe.add(sd.colInfos.get(i));
				//System.out.println("Spaltennummer: " + sd.colInfos.get(i).getSpaltenNummer());
				//System.out.println("Laenge: " + sd.zeilenInfos.get(i).getLenght());
				//System.out.println("Format: " + sd.zeilenInfos.get(i).getFormat());
			}
		}
		
		//Summen Analyse für ZEILEN
		if (gd.getCp().fs.sumRowsActivate) {		
			//Finden der Zeilen in das Wort Total in der 1. Spalte vorkommt
			WordTotalRows wtz = new WordTotalRows();
			wtz.action(gd, sd);
			//fügt diesen Zeilen noch die hinzu wo werte weitergeschrieben wurden
			ReassessTotal rt = new ReassessTotal();
			sd.rememberTotalRows.addAll(rt.action(gd, sd));
			Map<Integer, Integer> sumRow;
			//mit der Int/Double liste die total Zeilen finden
			for (int i = 0; i < sd.rememberTotalRows.size(); i++)
				sd.vi.sumRows.put(sd.rememberTotalRows.get(i), 0);
			if (sd.rememberTotalRows.size() > 0) {
				TotalFinderRowsTop tfzt = new TotalFinderRowsTop();
				TotalFinderRowsDown tfzd = new TotalFinderRowsDown();
				sumRow = tfzt.action(totalErkennerListe, gd, sd);
				sumRow.putAll(tfzd.action(totalErkennerListe, gd, sd));
			} else {
				TotalFinderRows tfz = new TotalFinderRows();
				sumRow = tfz.action(totalErkennerListe, gd, sd);
			}
			for (Entry<Integer, Integer> entry : sumRow.entrySet()) {
			    int key = entry.getKey();
			    int value = entry.getValue();
			    if (value >= gd.getCp().percentSum)
			    	sd.emptyRows.add(key);
			}
			sd.vi.sumRows.putAll(sumRow);
			
		}
		
		//Summen Analyse für SPALTEN
		if (gd.getCp().fs.sumColsActivate) {
			//gibt die Spalten an in denen das Wort "Total" vorkommt
			WordTotalCols wts = new WordTotalCols();
			wts.action(gd, sd);
			Map<Integer, Integer> sumCol;
			for (int i = 0; i < sd.rememberTotalCols.size(); i++)
				sd.vi.sumCols.put(sd.rememberTotalCols.get(i), 0);
			//mit der Int/Double liste die total Spalten finden
			if (sd.rememberTotalCols.size()>0) {
				boolean change = true;
				while (change && sd.rememberTotalCols.size() > 0) {
					TotalFinderColsRight tfcr = new TotalFinderColsRight();
					sumCol = tfcr.action(totalErkennerListe, gd, sd);
					TotalFinderColsLeft tfcl = new TotalFinderColsLeft();
					sumCol.putAll(tfcl.action(totalErkennerListe, gd, sd));
					if (sumCol.size() == 0)
						change = false;
					for (Entry<Integer, Integer> entry : sumCol.entrySet()) {
					    int key = entry.getKey();
					    int value = entry.getValue();
					    if (value >= gd.getCp().percentSum)
					    	sd.emptyCols.add(key);
					}
					sd.vi.sumCols.putAll(sumCol);
					sd.rememberTotalCols.removeAll(sumCol.keySet());
					sumCol.clear();
				}
			} else {
				TotalFinderCols tfs = new TotalFinderCols();
				sumCol = tfs.action(totalErkennerListe, gd, sd);
				for (Entry<Integer, Integer> entry : sumCol.entrySet()) {
				    int key = entry.getKey();
				    int value = entry.getValue();
				    if (value >= gd.getCp().percentSum)
				    	sd.emptyCols.add(key);
				}
				sd.vi.sumCols.putAll(sumCol);
			}
			//gd.getEv().addTotalSpalteAnzahl(sd.algoTotalCols.size());
		}
		
		if (gd.getCp().fs.valuesReassessActivate) {
			//schreibt Werte in darunter liegenden Zeilen fort
			ValuesReassess wf = new ValuesReassess();
			wf.action(gd, sd);	
		}		
		
		if (gd.getCp().fs.searchLayoutLinesActivate) {
			//sucht layout linien
			SearchLayoutLines sll = new SearchLayoutLines();
			sll.action(gd, sd);
		}
		
		if (gd.getCp().fs.stringChangeRelationNameActivate) {
			//wandelt den relations name für eine Datenbank einfüge operation um
			sd.relationName = gd.getSf().stringChangerUnterstrich(sd.relationName.trim());
		}
		
		if (gd.getCp().fs.newTableInDbActivate) {
			//Nimmt alle gefunden Informationen und packt diese in eine Datenbank
			NewTableInDb ntid = new NewTableInDb ();
			ntid.action(gd, sd);
		}
		
		if (gd.getCp().useOutputNameXls) {
			//gibt eine Ausgabe xls Datei aus um das Ergebnis der Extraktion zu begutachten
			OutputTableComplet atmf = new OutputTableComplet();
			atmf.action(gd, sd);
		}
		
		OutputObject af = new OutputObject(gd, sd);
		return af.getOutputObject();	
	}

}
