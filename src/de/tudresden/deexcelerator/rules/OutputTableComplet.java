package de.tudresden.deexcelerator.rules;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import de.tudresden.deexcelerator.data.AnalyzeInformation;
import de.tudresden.deexcelerator.data.DocumentInformation;
import de.tudresden.deexcelerator.util.HeaderEntry;
import de.tudresden.deexcelerator.util.SplittedCells;

import jxl.Workbook;
import jxl.write.Colour;
import jxl.write.DateFormats;
import jxl.write.DateTime;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

/**
 * <span class="de">Erzeugen einer XLS Datei.</span>
 * <span class="en">Create a xls document.</span>
 * @author Christopher Werner
 *
 */
@SuppressWarnings("deprecation")
public class OutputTableComplet {
	
	/**
	 * <span class="de">Erzeugt eine XLS Datei im Projektordner.
	 * Die Bl&aumltter sehen darin genauso aus wie die extrahierte Relation.</span>
	 * <span class="en">Creates a xls document in the project directory.
	 * The first folder looks like the complete relation.</span>
	 * @param gd (<span class="de">Verwendete DocumentInformation</span>
     * <span class="en">the used document information</span>)
     * @param sd (<span class="de">AnalyseInformation</span>
     * <span class="en">analyze information</span>)
	 */
	public void action (DocumentInformation gd, AnalyzeInformation sd) {
		
		if (gd.getFc().colNumber() > 400 || gd.getFc().rowNumber() >= 65000)
			return;
		WritableWorkbook workbookOutcome = null;
		WritableSheet sheetNeu = null;
		try {
			workbookOutcome = Workbook.createWorkbook(new File(gd.getCp().bevorOutputNameXls + "o" + gd.getCp().outputNameXls + ".xls"));
			gd.getCp().outputNameXls++;
			sheetNeu = workbookOutcome.createSheet(gd.getFc().sheetName()+"Tabelle", 0);	
		} catch (IOException e2) {
			e2.printStackTrace();
		}	
		WritableFont test = new WritableFont(WritableFont.TIMES);
		WritableCellFormat headerBereichZellen = new WritableCellFormat(test);
		WritableCellFormat wertBereichZellen = new WritableCellFormat(test);
		try {
			headerBereichZellen.setBackground(Colour.GREEN);
			wertBereichZellen.setBackground(Colour.GRAY_25);
		} catch (WriteException e1) {
			e1.printStackTrace();
		}
		
		List<Integer> leerBeachten = new ArrayList<Integer>();
		//----------------------------------------
		for (Iterator<HeaderEntry> iter = sd.attributeList.iterator(); iter.hasNext();) {
			HeaderEntry test1 = iter.next();
			if (sd.emptyCols.contains(test1.getSpalte()))
				leerBeachten.add(test1.getSpalte());
		}
		//----------------------------------------
		try {
			Label tabellenZelle = new Label(0, 0, "Tabellenname:");
			sheetNeu.addCell(tabellenZelle);
			Label tabellenNameZelle = new Label(1, 0, sd.relationName);
			sheetNeu.addCell(tabellenNameZelle);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		int zaehler = 1;		
		//-----------------------------------------------------------------
		int spalte = 0;
		for (int x=sd.colStart; x<sd.colEnd; x++)
		{
			if (!sd.emptyCols.contains(x)) {
				String attribut = "";
				for (Iterator<HeaderEntry> iter = sd.attributeList.iterator(); iter.hasNext();) {
					HeaderEntry test1 = iter.next();
					if (test1.getSpalte() == x)
						attribut = test1.getAttribut();
				}
				String typeQuery = "";
				for (int h = 0; h<sd.colInfos.size(); h++)
	    			if (sd.colInfos.get(h).getSpaltenNummer() == x) {
	    				int type = sd.colInfos.get(h).getFormat();
	    				if (type == 1) {
	    					//double
	    					typeQuery = "Double";
	    				} else if (type == 2) {
	    					//int
	    					typeQuery = "Integer";
	    				} else if (type == 3) {
	    					//date
	    					typeQuery = "Date";
	    				} else if (type == 4) {
	    					//string
	    					typeQuery = "Varchar(" + sd.colInfos.get(h).getLenght() + ")";
	    				}
	    			}
				Label typeZelle = new Label(spalte, 2, typeQuery);
				if (attribut.trim().equals("")) {
					attribut = "Attribut_" + zaehler;
					zaehler++;
				} else
					attribut = gd.getSf().stringChangerUnterstrich(attribut.trim());
				Label contentLeer = new Label(spalte, 3, attribut);
				contentLeer.setCellFormat(headerBereichZellen);
				try {
					sheetNeu.addCell(contentLeer);
					sheetNeu.addCell(typeZelle);
				} catch (Exception e) {
					e.printStackTrace();
				}
				spalte++;
			}
		}
		//-----------------------------------------------------------------
		
		spalte = 0;
		int zeile = 4;
		for (int x=sd.colStart; x<sd.colEnd; x++)
		{
			if (!sd.emptyCols.contains(x)) {
				for (int y=sd.headerFinish; y<sd.rowEnd; y++)
				{
					try {
						if (!sd.emptyRows.contains(y))
						{
							String inhalt = gd.getFc().contentCell(x, y);
							if (gd.getFc().emptyCell(x, y) || x == sd.colStart)
								for (Iterator<SplittedCells> iter = sd.mergeCells.iterator(); iter.hasNext();) {
									SplittedCells sz = iter.next();
									if (x<=sz.getX()+sz.getXrange() && x>=sz.getX() && y<=sz.getY()+sz.getYrange() && y>=sz.getY())
										inhalt = sz.getContent();
								}
							for (int h = 0; h<sd.colInfos.size(); h++)
			        			if (sd.colInfos.get(h).getSpaltenNummer() == x) {
			        				int type = sd.colInfos.get(h).getFormat();
			        				if (type == 1 || type == 2) {
			        					//double
			        					if (gd.getFc().numberCell(x, y)) {
			        						Number numberCell = new Number(spalte, zeile, gd.getFc().getDoubleValue(x, y));
			    							numberCell.setCellFormat(wertBereichZellen);
			    							sheetNeu.addCell(numberCell);
			        					}
			        					else
			        					{
			        						Label contentLeer = new Label(spalte, zeile, "");
			        						contentLeer.setCellFormat(wertBereichZellen);
			        						sheetNeu.addCell(contentLeer);
			        					}
			        				} else if (type == 3) {
			        					//date
			        					if (gd.getFc().dateCell(x, y)) {
			        						WritableCellFormat dateFormat = new WritableCellFormat(DateFormats.DEFAULT);
			        						DateTime datum = new DateTime(spalte, zeile, gd.getFc().getDateValue(x, y), dateFormat);
			        						datum.setCellFormat(wertBereichZellen);
			        						sheetNeu.addCell(datum);
			        					} else {
			        						Label contentLeer = new Label(spalte, zeile, "");
			        						contentLeer.setCellFormat(wertBereichZellen);
			        						sheetNeu.addCell(contentLeer);
			        					}
			        				} else if (type == 4) {
			        					//string
			        					Label contentLeer;
			        					if (inhalt == null)
			        						contentLeer = new Label(spalte, zeile, "");
			        					else
			        						contentLeer = new Label(spalte, zeile, inhalt.trim());
			        					contentLeer.setCellFormat(wertBereichZellen);
										sheetNeu.addCell(contentLeer);
			        				}
			        			}
							zeile++;
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				spalte++;
			}
			zeile = 4;
		}
		spalte++;
		Label meta = new Label(spalte, 0, "MetaDaten:");
		try {
			sheetNeu.addCell(meta);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (sd.metaDataTable.size() < 65000)
			for (int i=0;i<sd.metaDataTable.size(); i++) {
				Label metaDaten = new Label(spalte, i+1, sd.metaDataTable.get(i));
				try {
					sheetNeu.addCell(metaDaten);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		//Schreiben des Excel Dokuments
		try {
			workbookOutcome.write();
			workbookOutcome.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
