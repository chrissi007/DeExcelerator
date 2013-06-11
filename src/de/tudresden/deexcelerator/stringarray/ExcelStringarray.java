package de.tudresden.deexcelerator.stringarray;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import jxl.CellType;
import jxl.DateCell;
import jxl.NumberCell;
import jxl.Range;
import jxl.Sheet;
import jxl.Workbook;
import de.tudresden.deexcelerator.util.RangeMatrix;

/**
 * <span class="de">F&uumlhrt alle Umwandlungen eines Excel Dokuments durch.
 * &Uumlberf&uumlhrt die Excel Datei in eine String Matrix.</span>
 * <span class="en">Take Excel documents and create a string array.</span>
 * @author Christopher Werner
 *
 */
public class ExcelStringarray {

	/**
	 * <span class="de">Erzeugt eine String Matrix aus einem Excel Dokument.</span>
	 * <span class="en">Create an array of strings from the xls document.</span>
	 * @param f (<span class="de">File name eigene Analyse</span>
	 * <span class="en">file for extraction</span>)
	 * @return <span class="de">Liste von Stringarrays</span>
	 * <span class="en">List of Stringarrays</span>
	 */
	public List<OutputStringarray> excelInPipelineJxl (File f) {
		String dateiname = f.getPath();
		return this.excelInPipelineJxl(dateiname);
	}

	/**
	 * <span class="de">Erzeugt eine String Matrix aus einem Excel Dokument.</span>
	 * <span class="en">Create an array of strings from the xls document.</span>
	 * @param f (<span class="de">File name eigene Analyse</span>
	 * <span class="en">file for extraction</span>)
	 * @return <span class="de">Liste von Stringarrays</span>
	 * <span class="en">List of Stringarrays</span>
	 */
	public List<OutputStringarray> excelInPipelinePoiHssf (File f) {
		String dateiname = f.getPath();
		return this.excelInPipelinePoiHssf(dateiname);
	}
	
	/**
	 * <span class="de">Erzeugt eine String Matrix aus einem Excel Dokument.</span>
	 * <span class="en">Create an array of strings from the xls document.</span>
	 * @param f (<span class="de">File name eigene Analyse</span>
	 * <span class="en">file for extraction</span>)
	 * @return <span class="de">Liste von Stringarrays</span>
	 * <span class="en">List of Stringarrays</span>
	 */
	public List<OutputStringarray> excelInPipelinePoiXssf (File f) {
		String dateiname = f.getPath();
		return this.excelInPipelinePoiXssf(dateiname);
	}
		
	/**
	 * <span class="de">Erzeugt eine String Matrix aus einem Excel Dokument.</span>
	 * <span class="en">Create an array of strings from the xls document.</span>
	 * @param dateiname (<span class="de">Dateiname eigene Analyse</span>
	 * <span class="en">directory of the document</span>)
	 * @return <span class="de">Liste von Stringarrays</span>
	 * <span class="en">List of Stringarrays</span>
	 */
	public List<OutputStringarray> excelInPipelineJxl (String dateiname) {
		try {
			List<OutputStringarray> outs = new ArrayList<OutputStringarray>();
			Workbook workbook = Workbook.getWorkbook(new File(dateiname));
			Sheet[] alleSheetsEingabe = workbook.getSheets();
			int anzahlSheets = alleSheetsEingabe.length;
			for (int i = 0; i<anzahlSheets; i++ )
			{	
				Sheet sheet = workbook.getSheet(i);
				int rowCount = sheet.getRows();
				int colCount = sheet.getColumns();
				if (colCount == 0 || rowCount == 0)
			 		continue;
				// erstellen der Matrix mit Spalten und Zeilenanzahl der XLS Datei
			 	String[][] matrix = new String[colCount][rowCount];
			 	//Füllen der Matrix
				for (int j = 0; j < rowCount; j++) {
					for (int k = 0; k < colCount; k++) {
						jxl.Cell cell = sheet.getCell(k,j);
						if (cell == null)
							continue;
						if (cell.getType() == CellType.NUMBER || cell.getType() == CellType.NUMBER_FORMULA) {
							NumberCell nc = (NumberCell) cell;
							matrix[k][j] = nc.getValue() + "";
						}  else if (cell.getType() == CellType.DATE || cell.getType() == CellType.DATE_FORMULA) {
							DateCell dc = (DateCell) cell;
							matrix[k][j] = dc.getDate().toString();
						} else {
							matrix[k][j] = cell.getContents();
						}
					}
				}
				//-------------Ranges-------------
				//Range aus Excel Tabelle auslesen und in RangeMatrix speichern
		        RangeMatrix[] rmListe = new RangeMatrix[sheet.getMergedCells().length];
		        for (int z = 0; z<sheet.getMergedCells().length;z++) {
					Range r = sheet.getMergedCells()[z];
					RangeMatrix rm = new RangeMatrix(r.getTopLeft().getColumn(), r.getTopLeft().getRow(), r.getBottomRight().getColumn(), r.getBottomRight().getRow());
					rmListe[i] = rm;
				}
		        //Extraktions Pipeline aufrufen und mit Matrix und Sheetnamen füllen
				String name = sheet.getName();
				OutputStringarray os = new OutputStringarray();
				os.matrix = matrix;
				os.name = name;
				os.rmListe = rmListe;
				outs.add(os);
			}
			return outs;
		} catch (Exception e) {
			//System.out.println("Einfuegen in Excel Pipeline nicht moeglich!");
			e.printStackTrace();
			//return excelInPipelineWithPoi(dateiname,cp);
		}		
		return null;
	}
		
	/**
	 * <span class="de">Erzeugt eine String Matrix aus einem Excel Dokument.</span>
	 * <span class="en">Create an array of strings from the xls document.</span>
	 * @param dateiname (<span class="de">Dateiname eigene Analyse</span>
	 * <span class="en">directory of the document</span>)
	 * @return <span class="de">Liste von Stringarrays</span>
	 * <span class="en">List of Stringarrays</span>
	 */
	public List<OutputStringarray> excelInPipelinePoiHssf (String dateiname) {
		try {
			InputStream input = new FileInputStream(dateiname);
			return this.excelInputstreamHssf(input);
		} catch (Exception e) {
			e.printStackTrace();		
		}
		return null;
	}
	
	/**
	 * <span class="de">Erzeugt eine String Matrix aus einem Excel Dokument.</span>
	 * <span class="en">Create an array of strings from the xls document.</span>
	 * @param dateiname (<span class="de">Dateiname eigene Analyse</span>
	 * <span class="en">directory of the document</span>)
	 * @return <span class="de">Liste von Stringarrays</span>
	 * <span class="en">List of Stringarrays</span>
	 */
	public List<OutputStringarray> excelInPipelinePoiXssf (String dateiname) {
		try {
			InputStream fis = new FileInputStream(dateiname);
			return this.excelInputstreamXssf(fis);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * <span class="de">Erzeugt eine String Matrix aus einem Excel Dokument.</span>
	 * <span class="en">Create an array of strings from the xls document.</span>
	 * @param stream (<span class="de">Inputstream der den Dateiinhalt beschreibt</span>
	 * <span class="en">InputStream of the incoming document</span>)
	 * @return <span class="de">Liste von Stringarrays</span>
	 * <span class="en">List of Stringarrays</span>
	 */
	public List<OutputStringarray> excelInputstreamJxl (InputStream stream) {
		try {
			List<OutputStringarray> outs = new ArrayList<OutputStringarray>();
			Workbook workbook = Workbook.getWorkbook(stream);
			Sheet[] alleSheetsEingabe = workbook.getSheets();
			int anzahlSheets = alleSheetsEingabe.length;
			for (int i = 0; i<anzahlSheets; i++ )
			{	
				Sheet sheet = workbook.getSheet(i);
				int rowCount = sheet.getRows();
				int colCount = sheet.getColumns();
				if (colCount == 0 || rowCount == 0)
			 		continue;
				// erstellen der Matrix mit Spalten und Zeilenanzahl der XLS Datei
			 	String[][] matrix = new String[colCount][rowCount];
			 	//Füllen der Matrix
				for (int j = 0; j < rowCount; j++) {
					for (int k = 0; k < colCount; k++) {
						jxl.Cell cell = sheet.getCell(k,j);
						if (cell == null)
							continue;
						if (cell.getType() == CellType.NUMBER || cell.getType() == CellType.NUMBER_FORMULA) {
							NumberCell nc = (NumberCell) cell;
							matrix[k][j] = nc.getValue() + "";
						}  else if (cell.getType() == CellType.DATE || cell.getType() == CellType.DATE_FORMULA) {
							DateCell dc = (DateCell) cell;
							matrix[k][j] = dc.getDate().toString();
						} else {
							matrix[k][j] = cell.getContents();
						}
					}
				}
				//-------------Ranges-------------
				//Range aus Excel Tabelle auslesen und in RangeMatrix speichern
		        RangeMatrix[] rmListe = new RangeMatrix[sheet.getMergedCells().length];
		        for (int z = 0; z<sheet.getMergedCells().length;z++) {
					Range r = sheet.getMergedCells()[z];
					RangeMatrix rm = new RangeMatrix(r.getTopLeft().getColumn(), r.getTopLeft().getRow(), r.getBottomRight().getColumn(), r.getBottomRight().getRow());
					rmListe[i] = rm;
				}
		        //Extraktions Pipeline aufrufen und mit Matrix und Sheetnamen füllen
				String name = sheet.getName();
				OutputStringarray os = new OutputStringarray();
				os.name = name;
				os.matrix = matrix;
				os.rmListe = rmListe;				
				outs.add(os);
			}
			return outs;
		} catch (Exception e) {
			//System.out.println("Einfuegen in Excel Pipeline nicht moeglich!");
			e.printStackTrace();
			//return excelInPipelineWithPoi(dateiname,cp);
		}		
		return null;
	}
	
	/**
	 * <span class="de">Erzeugt eine String Matrix aus einem Excel Dokument.</span>
	 * <span class="en">Create an array of strings from the xls document.</span>
	 * @param stream (<span class="de">Inputstream der eingehenden Datei</span>
	 * <span class="en">InputStream of the incoming document</span>)
	 * @return <span class="de">Liste von Stringarrays</span>
	 * <span class="en">List of Stringarrays</span>
	 */
	public List<OutputStringarray> excelInputstreamHssf (InputStream stream) {
		List<OutputStringarray> outs = new ArrayList<OutputStringarray>();
		try {
			HSSFWorkbook wb = new HSSFWorkbook(stream);
			FormulaEvaluator evaluator = wb.getCreationHelper().createFormulaEvaluator();
	        int sheetAnzahl = wb.getNumberOfSheets();
	        for (int i = 0; i<sheetAnzahl; i++) {
	        	HSSFSheet sheet = wb.getSheetAt(i);
		        int rowCount = sheet.getPhysicalNumberOfRows();
				int cols = 0;
				for (int j = 0; j < rowCount; j++) {
					HSSFRow row = sheet.getRow(j);
					if (row == null) {
						rowCount++;
						continue;
					}
					int colCount = row.getPhysicalNumberOfCells();
					for (int x = 0; x < colCount; x++)
						if (sheet.getRow(j).getCell(x) == null)
							colCount++;
					if (cols < colCount)
						cols = colCount;
				}
				if (cols == 0 || rowCount == 0)
			 		continue;				
			 	String[][] matrix = new String[cols][rowCount];
			 	for (int j = 0; j < rowCount; j++) {
					HSSFRow row = sheet.getRow(j);
					if (row == null)
						continue;
					for (int k = 0; k < cols; k++) {
						HSSFCell cell = row.getCell(k);
						if (cell == null)
							continue;
						int cellType = cell.getCellType(); //Getting a null pointer exception when the cell is empty
						if (cellType == HSSFCell.CELL_TYPE_NUMERIC) {
							if (HSSFDateUtil.isCellDateFormatted(cell))
								matrix[k][j] = cell.getDateCellValue().toString();
				            else
				            	matrix[k][j] = cell.getNumericCellValue() + "";
						} else if (cellType == HSSFCell.CELL_TYPE_STRING) {
							matrix[k][j] = cell.getStringCellValue().trim();
						} else if (cellType == HSSFCell.CELL_TYPE_BOOLEAN) {
							if (cell.getBooleanCellValue())
								matrix[k][j] = "1";
							else
								matrix[k][j] = "0";
						} else if (cellType == HSSFCell.CELL_TYPE_FORMULA) {
							try {
								CellValue cellV = evaluator.evaluate(cell);
								if (cellV.getCellType() == Cell.CELL_TYPE_NUMERIC) {
									if (HSSFDateUtil.isCellDateFormatted(cell)) {
										matrix[k][j] = cell.getDateCellValue().toString();
						            } else
						            	matrix[k][j] = cell.getNumericCellValue() + "";
								} else if (cellV.getCellType() == Cell.CELL_TYPE_STRING)
									matrix[k][j] = cell.getStringCellValue().trim();
							} catch (Exception e) {
								//Mach nix (Ignorieren)
							}								
						}
					}
				}
	        	//Range aus Excel Tabelle auslesen und in RangeMatrix speichern
		        RangeMatrix[] rmListe = new RangeMatrix[sheet.getNumMergedRegions()];
		        for (int j = 0; j<sheet.getNumMergedRegions(); j++) {
		        	CellRangeAddress cra = sheet.getMergedRegion(j);
		        	RangeMatrix rm = new RangeMatrix(cra.getFirstColumn(), cra.getFirstRow(), cra.getLastColumn(), cra.getLastRow());
			        rmListe[j] = rm;			        
		        }
		        //einführen der Daten in die Extraktionspipeline
		        OutputStringarray os = new OutputStringarray();
		        os.name = sheet.getSheetName();
		        os.matrix = matrix;
		        os.rmListe = rmListe;
		        outs.add(os);
	        }
	        stream.close();
		} catch (Exception e) {
			e.printStackTrace();
			//System.out.println("Einfuegen in Excel Pipeline mit HSSFPOI nicht moeglich!");
			//return this.excelInputstreamJxl(stream, cp);			
		}
		return outs;
	}

	/**
	 * <span class="de">Erzeugt eine String Matrix aus einem Excel Dokument.</span>
	 * <span class="en">Create an array of strings from the xls document.</span>
	 * @param stream (<span class="de">Inputstream der eingehenden Datei</span>
	 * <span class="en">InputStream of the incoming document</span>)
	 * @return <span class="de">Liste von Stringarrays</span>
	 * <span class="en">List of Stringarrays</span>
	 */
	public List<OutputStringarray> excelInputstreamXssf (InputStream stream) {
		List<OutputStringarray> outs = new ArrayList<OutputStringarray>();
		try {
			XSSFWorkbook workbook = new XSSFWorkbook(stream);
			FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
			int sheetCount = workbook.getNumberOfSheets();
			for (int i = 0; i < sheetCount; i++) {
				XSSFSheet sheet = workbook.getSheetAt(i);

				int rowCount = sheet.getPhysicalNumberOfRows();
				int cols = 0;
				for (int j = 0; j < rowCount; j++) {
					XSSFRow row = sheet.getRow(j);
					if (row == null) {
						rowCount++;
						continue;
					}
					int colCount = row.getPhysicalNumberOfCells();
					for (int x = 0; x < colCount; x++)
						if (sheet.getRow(j).getCell(x) == null)
							colCount++;
					if (cols < colCount)
						cols = colCount;
				}
				if (cols == 0 || rowCount == 0)
			 		continue;				
				String[][] matrix = new String[cols][rowCount];
			 	for (int j = 0; j < rowCount; j++) {
						XSSFRow row = sheet.getRow(j);
						if (row == null)
							continue;
						for (int k = 0; k < cols; k++) {
							XSSFCell cell = row.getCell(k);
							if (cell == null)
								continue;
							int cellType = cell.getCellType(); //Getting a null pointer exception when the cell is empty
							if (cellType == Cell.CELL_TYPE_NUMERIC) {
								if (HSSFDateUtil.isCellDateFormatted(cell))
									matrix[k][j] = cell.getDateCellValue().toString();
					            else
					            	matrix[k][j] = cell.getNumericCellValue() + "";
							} else if (cellType == Cell.CELL_TYPE_STRING) {
								matrix[k][j] = cell.getStringCellValue().trim();
							} else if (cellType == Cell.CELL_TYPE_BOOLEAN) {
								if (cell.getBooleanCellValue())
									matrix[k][j] = "1";
								else
									matrix[k][j] = "0";
							} else if (cellType == Cell.CELL_TYPE_FORMULA) {
								try {
									CellValue cellV = evaluator.evaluate(cell);
									if (cellV.getCellType() == Cell.CELL_TYPE_NUMERIC) {
										if (HSSFDateUtil.isCellDateFormatted(cell))
											matrix[k][j] = cell.getDateCellValue().toString();
							            else
							            	matrix[k][j] = cell.getNumericCellValue() + "";
									}
									if (cellV.getCellType() == Cell.CELL_TYPE_STRING)
										matrix[k][j] = cell.getStringCellValue().trim();
								} catch (Exception e) {
									//Mach nix (Ignorieren)
								}								
							}
						}
				}
				//-------------Ranges-------------
				//Range aus Excel Tabelle auslesen und in RangeMatrix speichern
		        RangeMatrix[] rmListe = new RangeMatrix[sheet.getNumMergedRegions()];
		        for (int j = 0; j<sheet.getNumMergedRegions(); j++) {
		        	CellRangeAddress cra = sheet.getMergedRegion(j);
		        	RangeMatrix rm = new RangeMatrix(cra.getFirstColumn(), cra.getFirstRow(), cra.getLastColumn(), cra.getLastRow());
			        rmListe[j] = rm;			        
		        }
		        //einfügen der Daten in die Extraktionspipeline
		        String name = sheet.getSheetName();
				OutputStringarray outputObject = new OutputStringarray();
				outputObject.matrix = matrix;
				outputObject.name = name;
				outputObject.rmListe = rmListe;
		        outs.add(outputObject);
			}
		} catch (IOException e) {
			//System.out.println("XSSF funktioniert nicht!");
			e.printStackTrace();
		}
		return outs;
	}
}
