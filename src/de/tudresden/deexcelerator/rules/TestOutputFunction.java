package de.tudresden.deexcelerator.rules;

import java.io.File;
import java.io.IOException;

import de.tudresden.deexcelerator.data.AnalyzeInformation;
import de.tudresden.deexcelerator.data.DocumentInformation;
import de.tudresden.deexcelerator.util.RangeMatrix;
import jxl.Workbook;
import jxl.write.Colour;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

/**
 * <span class="de">Erzeugt XLS Datei.</span>
 * <span class="en">Creates a xls document.</span>
 * @author Christopher Werner
 *
 */
@SuppressWarnings("deprecation")
public class TestOutputFunction {

	/**
	 * <span class="de">Ausgabe einer XLS Datei mit einer Farbe für jeden Zelltyp.</span>
	 * <span class="en">Creates a xls document with one color for every cell type.</span>
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
		WritableCellFormat textZellen = new WritableCellFormat(test);
		WritableCellFormat zahlZellen = new WritableCellFormat(test);
		WritableCellFormat leerZellen = new WritableCellFormat(test);
		WritableCellFormat hilfZellen = new WritableCellFormat(test);
		WritableCellFormat germergteZellen = new WritableCellFormat(test);
		try {
			textZellen.setBackground(Colour.BLUE);
			zahlZellen.setBackground(Colour.GOLD);
			leerZellen.setBackground(Colour.RED);
			hilfZellen.setBackground(Colour.GREEN);
			germergteZellen.setBackground(Colour.BROWN);
		} catch (WriteException e1) {
			e1.printStackTrace();
		}
		
		for (int x=0; x<sd.colNumber; x++)
			for (int y=0; y<sd.rowNumber; y++)
			{
				try {					
					if (gd.getFc().numberCell(x, y))
					{
						Number numberCell = new Number(x, y, gd.getFc().getDoubleValue(x, y));
						numberCell.setCellFormat(zahlZellen);
						sheetNeu.addCell(numberCell);
					} else {
						String inhalt = gd.getFc().contentCell(x, y);
						Label content = new Label(x, y, gd.getFc().contentCell(x, y));
						if (inhalt.equals("")|| inhalt == null || inhalt.equals("--"))
							content.setCellFormat(leerZellen);
						else
							content.setCellFormat(textZellen);
						sheetNeu.addCell(content);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		//-----------------------------------------------------------------
		RangeMatrix[] mergeZellen = gd.getFc().mergeCells();
		int lengeRange = mergeZellen.length;
		for (int z = 0; z<lengeRange; z++)
		{
			RangeMatrix mergeZelle = mergeZellen[z];
			int rowBottomRight = mergeZelle.getBottomRightY();
			int colBottomRight = mergeZelle.getBottomRightX();
			int rowTopLeft = mergeZelle.getTopLeftY();
			int colTopLeft = mergeZelle.getTopLeftX();
			for (int row = rowTopLeft; row<=rowBottomRight; row++)
				for (int col = colTopLeft; col<=colBottomRight; col++)
				{
					Label contentLeer = new Label(col, row, "Empty");
					contentLeer.setCellFormat(germergteZellen);
					try {
						sheetNeu.addCell(contentLeer);
					} catch (Exception e) {
						e.printStackTrace();
					} 
				}
		}
	}
}
