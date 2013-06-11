package de.tudresden.deexcelerator.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
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
import jxl.NumberCell;
import jxl.Range;
import jxl.Sheet;
import jxl.Workbook;
import de.tudresden.deexcelerator.Configuration;
import de.tudresden.deexcelerator.data.AnalyzeInformation;
import de.tudresden.deexcelerator.data.DocumentInformation;
import de.tudresden.deexcelerator.output.OutputObject;
import de.tudresden.deexcelerator.util.RangeMatrix;

/**
 * <span class="de">F&uumlhrt alle Umwandlungen eines Excel Dokuments durch.
 * Dabei wird jede Excel Datei erst in eine Matrix form &uumlberf&uumlhrt
 * und dann in die Extraktionspipeline geleitet.
 * Einleiten der Excel Dokumente in die Extraktionspipeline.</span>
 * <span class="en">Take Excel documents for extraction pipeline
 * and pick out the important information.</span>
 * @author Christopher Werner
 *
 */
public class ExcelInMatrixExtraction {

	/**
	 * <span class="de">F&uumlhrt ein Excel Dokument in die Extraktionspipeline.</span>
	 * <span class="en">Create an array of strings from the xls document and add it to extraction pipeline with JXL.</span>
	 * @param f (<span class="de">File name eigene Analyse</span>
	 * <span class="en">file for extraction</span>)
	 * @param cp (<span class="de">auch null m&oumlglich dann setzen auf Standardwerte</span>
	 * <span class="en">configuration parameters from user or null</span>)
	 * @return <span class="de">Liste von OutputObjecten</span>
	 * <span class="en">List of OutputObjects</span>
	 */
	public List<OutputObject> excelInPipelineJxl (File f, Configuration cp) {
		String dateiname = f.getPath();
		return this.excelInPipelineJxl(dateiname, cp);
	}

	/**
	 * <span class="de">F&uumlhrt ein Excel Dokument in die Extraktionspipeline.</span>
	 * <span class="en">Create an array of strings from the xls document and add it to extraction pipeline with HSSF.</span>
	 * @param f (<span class="de">File name eigene Analyse</span>
	 * <span class="en">file for extraction</span>)
	 * @param cp (<span class="de">auch null m&oumlglich dann setzen auf Standardwerte</span>
	 * <span class="en">configuration parameters from user or null</span>)
	 * @return <span class="de">Liste von OutputObjecten</span>
	 * <span class="en">List of OutputObjects</span>
	 */
	public List<OutputObject> excelInPipelinePoiHssf (File f, Configuration cp) {
		String dateiname = f.getPath();
		return this.excelInPipelinePoiHssf(dateiname, cp);
	}
	
	/**
	 * <span class="de">F&uumlhrt ein Excel Dokument in die Extraktionspipeline.</span>
	 * <span class="en">Create an array of strings from the xlsx document and add it to extraction pipeline with XSSF.</span>
	 * @param f (<span class="de">File name eigene Analyse</span>
	 * <span class="en">file for extraction</span>)
	 * @param cp (<span class="de">auch null m&oumlglich dann setzen auf Standardwerte</span>
	 * <span class="en">configuration parameters from user or null</span>)
	 * @return <span class="de">Liste von OutputObjecten</span>
	 * <span class="en">List of OutputObjects</span>
	 */
	public List<OutputObject> excelInPipelinePoiXssf (File f, Configuration cp) {
		String dateiname = f.getPath();
		return this.excelInPipelinePoiXssf(dateiname, cp);
	}
		
	/**
	 * <span class="de">Wandelt ein Exceldokument in eine Matrix um und f&uumlgt diese der Extraktionspipeline zu.</span>
	 * <span class="en">Create an array of strings from the xls document and add it to extraction pipeline with JXL.</span>
	 * @param dateiname (<span class="de">Dateiname eigene Analyse</span>
	 * <span class="en">directory of the document</span>)
	 * @param cp (<span class="de">auch null m&oumlglich dann setzen auf Standardwerte</span>
	 * <span class="en">configuration parameters from user or null</span>)
	 * @return <span class="de">Liste von OutputObjecten</span>
	 * <span class="en">List of OutputObjects</span>
	 */
	public List<OutputObject> excelInPipelineJxl (String dateiname, Configuration cp) {
		try {
			List<OutputObject> outs = new ArrayList<OutputObject>();
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
						String value;
						if (cell.getType() == CellType.NUMBER || cell.getType() == CellType.NUMBER_FORMULA) {
							NumberCell nc = (NumberCell) cell;
							value = nc.getValue() + "";			
						}  else {
							value = cell.getContents();
						}
						matrix[k][j] = value;
					}
				}
				//-------------Ranges-------------
				//Range aus Excel Tabelle auslesen und in RangeMatrix speichern
		        RangeMatrix[] rmListe = new RangeMatrix[sheet.getMergedCells().length];
		        for (int z = 0; z<sheet.getMergedCells().length;z++) {
					Range r = sheet.getMergedCells()[z];
					RangeMatrix rm = new RangeMatrix(r.getTopLeft().getColumn(), r.getTopLeft().getRow(), r.getBottomRight().getColumn(), r.getBottomRight().getRow());
					rmListe[z] = rm;
				}
		        //Extraktions Pipeline aufrufen und mit Matrix und Sheetnamen füllen
				String name = sheet.getName();
				DocumentInformation gd = new DocumentInformation(true, null, matrix, name, rmListe);
				gd.setCp(cp);
				AnalyzeInformation sd = new AnalyzeInformation(gd);
				sd.sheetNumber = i;
				TableSearch ts = new TableSearch(gd,sd);
				List<OutputObject> outs2 = ts.ruleSequence();
				if (outs2 == null)
					continue;
				outs.addAll(outs2);
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
	 * <span class="de">F&uumlhrt ein Excel Dokument (XLS) in die Extraktionspipeline mit POI Library.</span>
	 * <span class="en">Create an array of strings from the xls document and add it to extraction pipeline with HSSF.</span>
	 * @param dateiname (<span class="de">Dateiname eigene Analyse</span>
	 * <span class="en">directory of the document</span>)
	 * @param cp (<span class="de">auch null m&oumlglich dann setzen auf Standardwerte</span>
	 * <span class="en">configuration parameters from user or null</span>)
	 * @return <span class="de">Liste von OutputObjecten</span>
	 * <span class="en">List of OutputObjects</span>
	 */
	public List<OutputObject> excelInPipelinePoiHssf (String dateiname, Configuration cp) {
		try {
			InputStream input = new FileInputStream(dateiname);
			return this.excelInputstreamHssf(input, cp);
		} catch (Exception e) {
			e.printStackTrace();		
		}
		return null;
	}
	
	/**
	 * <span class="de">F&uumlhrt ein Excel Dokument (XLSX) in die Extraktionspipeline mit POI Library.</span>
	 * <span class="en">Create an array of strings from the xlsx document and add it to extraction pipeline with XSSF.</span>
	 * @param dateiname (<span class="de">Dateiname eigene Analyse</span>
	 * <span class="en">directory of the document</span>)
	 * @param cp (<span class="de">auch null m&oumlglich dann setzen auf Standardwerte</span>
	 * <span class="en">configuration parameters from user or null</span>)
	 * @return <span class="de">Liste von OutputObjecten</span>
	 * <span class="en">List of OutputObjects</span>
	 */
	public List<OutputObject> excelInPipelinePoiXssf (String dateiname, Configuration cp) {
		try {
			InputStream fis = new FileInputStream(dateiname);
			return this.excelInputstreamXssf(fis, cp);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * <span class="de">F&uumlhrt ein Excel Dokument (xls) in die Extraktionspipeline.</span>
	 * <span class="en">Create an array of strings from the xls document and add it to extraction pipeline with JXL.</span>
	 * @param stream (<span class="de">Inputstream der den Dateiinhalt beschreibt</span>
	 * <span class="en">InputStream of the incoming document</span>)
	 * @param cp (<span class="de">auch null m&oumlglich dann setzen auf Standardwerte</span>
	 * <span class="en">configuration parameters from user or null</span>)
	 * @return <span class="de">Liste von OutputObjecten</span>
	 * <span class="en">List of OutputObjects</span>
	 */
	public List<OutputObject> excelInputstreamJxl (InputStream stream, Configuration cp) {
		try {
			List<OutputObject> outs = new ArrayList<OutputObject>();
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
						String value;
						if (cell.getType() == CellType.NUMBER || cell.getType() == CellType.NUMBER_FORMULA) {
							NumberCell nc = (NumberCell) cell;
							value = nc.getValue() + "";			
						}  else {
							value = cell.getContents();
						}
						matrix[k][j] = value;
					}
				}
				//-------------Ranges-------------
				//Range aus Excel Tabelle auslesen und in RangeMatrix speichern
		        RangeMatrix[] rmListe = new RangeMatrix[sheet.getMergedCells().length];
		        for (int z = 0; z<sheet.getMergedCells().length;z++) {
					Range r = sheet.getMergedCells()[z];
					RangeMatrix rm = new RangeMatrix(r.getTopLeft().getColumn(), r.getTopLeft().getRow(), r.getBottomRight().getColumn(), r.getBottomRight().getRow());
					rmListe[z] = rm;
				}
		        //Extraktions Pipeline aufrufen und mit Matrix und Sheetnamen füllen
				String name = sheet.getName();
				DocumentInformation gd = new DocumentInformation(true, null, matrix, name, rmListe);
				gd.setCp(cp);
				AnalyzeInformation sd = new AnalyzeInformation(gd);
				sd.sheetNumber = i;
				TableSearch ts = new TableSearch(gd,sd);
				List<OutputObject> outs2 = ts.ruleSequence();
				if (outs2 == null)
					continue;
				outs.addAll(outs2);
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
	 * <span class="de">F&uumlhrt ein Excel Dokument (xls) in die Extraktionspipeline. 
	 * Testmethode muss noch &uumlberarbeitet werden.</span>
	 * <span class="en">Create an array of strings from the xls document and add it to extraction pipeline with HSSF.</span>
	 * @param stream (<span class="de">Inputstream der eingehenden Datei</span>
	 * <span class="en">InputStream of the incoming document</span>)
	 * @param cp (<span class="de">auch null m&oumlglich dann setzen auf Standardwerte</span>
	 * <span class="en">configuration parameters from user or null</span>)
	 * @return <span class="de">Liste von OutputObjecten</span>
	 * <span class="en">List of OutputObjects</span>
	 */
	public List<OutputObject> excelInputstreamHssf (InputStream stream, Configuration cp) {
		List<OutputObject> outs = new ArrayList<OutputObject>();
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
							continue;
						}
						int colCount = row.getPhysicalNumberOfCells();
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
					int colCount = row.getPhysicalNumberOfCells();
					for (int k = 0; k < colCount; k++) {
						HSSFCell cell = row.getCell(k);
						if (cell == null)
							continue;
						boolean zahl = false;
						int cellType = cell.getCellType(); //Getting a null pointer exception when the cell is empty
						String value = "";
						if (cellType == HSSFCell.CELL_TYPE_NUMERIC) {
							value = cell.getNumericCellValue() + "";
							zahl = true;
						} else if (cellType == HSSFCell.CELL_TYPE_STRING) {
							value = cell.getStringCellValue();
						}
						else if (cellType == HSSFCell.CELL_TYPE_BOOLEAN) {
							if (cell.getBooleanCellValue())
								value = "1";
							else
								value = "0";
						} else if (cellType == HSSFCell.CELL_TYPE_FORMULA) {
							try {
								CellValue cellV = evaluator.evaluate(cell);
								if (cellV.getCellType() == Cell.CELL_TYPE_NUMERIC) {
									value = cell.getNumericCellValue() + "";
									zahl = true;
								}
								if (cellV.getCellType() == Cell.CELL_TYPE_STRING)
									value = cell.getStringCellValue();
							} catch (Exception e) {
								//Mach nix (Ignorieren)
							}								
						}
						if (zahl) {
							char[] c = value.toCharArray();
		        			String ergebnis = "";
		        		    for (int z = 0; z<c.length; z++)
		        		    {	    	
		        		    	if (c[z] == '.')
		        		    		ergebnis = ergebnis + ',';
		        		    	else
		        		    		ergebnis = ergebnis + c[z];
		        		    }
		        		    value = ergebnis;
						}
						matrix[k][j] = value;
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
		        DocumentInformation gd = new DocumentInformation(true, null, matrix, sheet.getSheetName(), rmListe);
				gd.setCp(cp);
				AnalyzeInformation sd = new AnalyzeInformation(gd);
				sd.sheetNumber = i;
				TableSearch ts = new TableSearch(gd,sd);
				List<OutputObject> outs2 = ts.ruleSequence();
				if (outs2 == null)
					continue;
				outs.addAll(outs2);
	        }
	        stream.close();
			return outs;
		} catch (Exception e) {
			//e.printStackTrace();
			//System.out.println("Einfuegen in Excel Pipeline mit HSSFPOI nicht moeglich!");
			return this.excelInputstreamJxl(stream, cp);			
		}
	}

	/**
	 * <span class="de">F&uumlhrt ein Excel Dokument (xlsx) in die Extraktionspipeline.</span> 
	 * <span class="en">Create an array of strings from the xlsx document and add it to extraction pipeline with XSSF.</span>
	 * @param stream (<span class="de">Inputstream der eingehenden Datei</span>
	 * <span class="en">InputStream of the incoming document</span>)
	 * @param cp (<span class="de">auch null m&oumlglich dann setzen auf Standardwerte</span>
	 * <span class="en">configuration parameters from user or null</span>)
	 * @return <span class="de">Liste von OutputObjecten</span>
	 * <span class="en">List of OutputObjects</span>
	 */
	public List<OutputObject> excelInputstreamXssf (InputStream stream, Configuration cp) {
		List<OutputObject> outs = new ArrayList<OutputObject>();
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
							continue;
						}
						int colCount = row.getPhysicalNumberOfCells();
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
						int colCount = row.getPhysicalNumberOfCells();
						for (int k = 0; k < colCount; k++) {
							XSSFCell cell = row.getCell(k);
							if (cell == null)
								continue;
							boolean zahl = false;
							int cellType = cell.getCellType(); //Getting a null pointer exception when the cell is empty
							String value = "";
							if (cellType == Cell.CELL_TYPE_NUMERIC) {
								value = cell.getNumericCellValue() + "";
								zahl = true;
							} else if (cellType == Cell.CELL_TYPE_STRING) {
								value = cell.getStringCellValue();
							}
							else if (cellType == Cell.CELL_TYPE_BOOLEAN) {
								if (cell.getBooleanCellValue())
									value = "1";
								else
									value = "0";
							} else if (cellType == Cell.CELL_TYPE_FORMULA) {
								try {
									CellValue cellV = evaluator.evaluate(cell);
									if (cellV.getCellType() == Cell.CELL_TYPE_NUMERIC) {
										value = cell.getNumericCellValue() + "";
										zahl = true;
									}
									if (cellV.getCellType() == Cell.CELL_TYPE_STRING)
										value = cell.getStringCellValue();
								} catch (Exception e) {
									//Mach nix (Ignorieren)
								}								
							}
							if (zahl) {
								char[] c = value.toCharArray();
		        				String ergebnis = "";
		        			    for (int z = 0; z<c.length; z++)
		        			    {	    	
		        			    	if (c[z] == '.')
		        			    		ergebnis = ergebnis + ',';
		        			    	else
		        			    		ergebnis = ergebnis + c[z];
		        			    }
		        			    value = ergebnis;
							}
							matrix[k][j] = value;
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
				DocumentInformation gd = new DocumentInformation(true, null, matrix, name, rmListe);
				gd.setCp(cp);
				AnalyzeInformation sd = new AnalyzeInformation(gd);
				sd.sheetNumber = i;
				TableSearch ts = new TableSearch(gd,sd);
				List<OutputObject> outs2 = ts.ruleSequence();
				if (outs2 == null)
					continue;
				outs.addAll(outs2);
			}
		} catch (IOException e) {
			//System.out.println("XSSF funktioniert nicht!");
			e.printStackTrace();
		}
		return outs;
	}
}
