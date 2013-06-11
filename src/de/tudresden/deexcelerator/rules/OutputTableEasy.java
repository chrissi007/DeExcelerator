package de.tudresden.deexcelerator.rules;

import java.io.File;
import java.io.IOException;

import de.tudresden.deexcelerator.data.AnalyzeInformation;
import de.tudresden.deexcelerator.data.DocumentInformation;
import jxl.Workbook;
import jxl.write.Colour;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

/**
 * <span class="de">Erzeugt XLS Output.</span>
 * <span class="en">Create a xls document.</span>
 * @author Christopher Werner
 *
 */
@SuppressWarnings("deprecation")
public class OutputTableEasy {
	
	/**
	 * <span class="de">Gibt Tabelle so aus wie sie ist.
	 * Zeigt an wo der erkannte Header ist.
	 * L&aumlsst die Leerzeilen und Leerspalten weg.</span>
	 * <span class="en">Creates a table, which is like the old table
	 * without empty rows and cols and with the headerline.</span>
	 * @param gd (<span class="de">Verwendete DocumentInformation</span>
     * <span class="en">the used document information</span>)
     * @param sd (<span class="de">AnalyseInformation</span>
     * <span class="en">analyze information</span>)
	 */
	public void action (DocumentInformation gd, AnalyzeInformation sd) {
		WritableWorkbook workbookOutcome;
		WritableSheet sheetNeu = null;
		try {
			workbookOutcome = Workbook.createWorkbook(new File("AusgabeBeispiel"));
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
		int spalte = 0;
		int zeile = 0;
		for (int x=sd.colStart; x<sd.colEnd; x++)
		{
			if (!sd.emptyCols.contains(x))
				for (int y=sd.rowStart; y<sd.rowEnd; y++)
				{
					try {
						if (!sd.emptyRows.contains(y))
						{
							Label contentLeer = new Label(x-spalte, y-zeile, gd.getFc().contentCell(x, y));
							if (y<sd.headerFinish)
								contentLeer.setCellFormat(headerBereichZellen);
							else
								contentLeer.setCellFormat(wertBereichZellen);
							sheetNeu.addCell(contentLeer);
						} else 
							zeile++;
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			else 
				spalte++;
			zeile = 0;
		}
	}
}
