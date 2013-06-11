package de.tudresden.deexcelerator;

import java.io.File;
import java.io.InputStream;
import java.util.List;

import de.tudresden.deexcelerator.core.CsvExtraction;
import de.tudresden.deexcelerator.core.ExcelInMatrixCellExtraction;
import de.tudresden.deexcelerator.core.HTMLExtraction;
import de.tudresden.deexcelerator.data.InputstreamToString;
import de.tudresden.deexcelerator.output.OutputObject;
import de.tudresden.deexcelerator.util.WebsiteReader;



/**
 * <span class="de">Einstiegspunkt in die Extraktionspipeline.
 * Funktion für passenden Input ausw&aumlhlen und Pipeline starten.
 * Ergebnis als Liste von OutputObjecten beziehen.</span>
 * <span class="en">Startpoint for the extraction pipeline.
 * Choose the right function for your input an get the 
 * solution OutputObjects.</span>
 * @author Christopher Werner
 *
 */
public class DeExcelerator {
	
	/**
	 * <span class="de">Nimmt Files auf und f&uumlhrt dieses der Matrix zu.</span>
	 * <span class="en">Takes a file with its directory an extract it in the piepeline.</span>
	 * @param f <span class="de">File name eigene Analyse</span>
	 * <span class="en">file for extraction</span>
	 * @param cp <span class="de">auch null m&oumlglich dann setzen auf Standardwerte</span>
	 * <span class="en">configuration parameters from user or null</span>
	 * @return <span class="de">Liste von OutputObjecten</span>
	 * <span class="en">List of OutputObjects</span>
	 */
	public List<OutputObject> extract (File f, Configuration cp) {
		String dateiname = f.getPath();
		if (dateiname.trim().equals(""))
			return null;
		String proofEnd = dateiname.substring(dateiname.length()-4, dateiname.length());
		if (proofEnd.toLowerCase().contains("csv")) {
			CsvExtraction ce = new CsvExtraction();
			return ce.valuesToMatrix(dateiname, cp, ',');
		} else if (proofEnd.toLowerCase().contains("xlsx")) {
			ExcelInMatrixCellExtraction ee = new ExcelInMatrixCellExtraction();
			return ee.excelInPipelinePoiXssf(dateiname, cp);
		}  else if (proofEnd.toLowerCase().contains("xls")) {
			ExcelInMatrixCellExtraction ee = new ExcelInMatrixCellExtraction();
			return ee.excelInPipelinePoiHssf(dateiname, cp);
		} else {
			System.out.println("Dateityp nicht csv, xls oder xlsx!");
			return null;
			//JsonExtraction je = new JsonExtraction();
			//return je.valuesToMatrix(dateiname, cp);
		}
	}

	/**
	 * <span class="de">Nimmt Files durch Dateinamen auf und f&uumlhrt diese der Matrix zu.</span>
	 * <span class="en">Takes a file with its directory an extract it in the piepeline.</span>
	 * @param dateiname <span class="de">Dateiname eigene Analyse</span>
	 * <span class="en">directory of the document</span>
	 * @param cp <span class="de">auch null m&oumlglich dann setzen auf Standardwerte</span>
	 * <span class="en">configuration parameters from user or null</span>
	 * @return <span class="de">Liste von OutputObjecten</span>
	 * <span class="en">List of OutputObjects</span>
	 */
	public List<OutputObject> extract (String dateiname, Configuration cp) {
		if (dateiname.trim().equals(""))
			return null;
		String proofEnd = dateiname.substring(dateiname.length()-4, dateiname.length());
		if (proofEnd.toLowerCase().contains("csv")) {
			CsvExtraction ce = new CsvExtraction();
			return ce.valuesToMatrix(dateiname, cp, ',');
		}  else if (proofEnd.toLowerCase().contains("xlsx")) {
			ExcelInMatrixCellExtraction ee = new ExcelInMatrixCellExtraction();
			return ee.excelInPipelinePoiXssf(dateiname, cp);
		}  else if (proofEnd.toLowerCase().contains("xls")) {
			ExcelInMatrixCellExtraction ee = new ExcelInMatrixCellExtraction();
			return ee.excelInPipelinePoiHssf(dateiname, cp);
		} else {
			System.out.println("Dateityp nicht csv, xls oder xlsx!");
			return null;
			//JsonExtraction je = new JsonExtraction();
			//return je.valuesToMatrix(dateiname, cp);
		}
	}
	
	/**
	 * <span class="de">Nimmt Files (xls) durch ihren Inputstream auf und f&uumlhrt diese der Matrix zu.</span>
	 * <span class="en">Takes the InputStream of an xls document and add it to the extraction pipeline.</span>
	 * @param stream <span class="de">Inputstream des Dokuments</span>
	 * <span class="en">InputStream of the incoming document</span>
	 * @param cp <span class="de">auch null m&oumlglich dann setzen auf Standardwerte</span>
	 * <span class="en">configuration parameters from user or null</span>
	 * @return <span class="de">Liste von OutputObjecten</span>
	 * <span class="en">List of OutputObjects</span>
	 */
	public List<OutputObject> extractXls (InputStream stream, Configuration cp) {
		if (stream == null)
			return null;
		ExcelInMatrixCellExtraction ee = new ExcelInMatrixCellExtraction();
		return ee.excelInputstreamHssf(stream, cp);
	}
	
	/**
	 * <span class="de">Nimmt Files (xlsx) durch ihren Inputstream auf und f&uumlhrt diese der Matrix zu.</span>
	 * <span class="en">Takes the InputStream of an xlsx document and add it to the extraction pipeline.</span>
	 * @param stream <span class="de">Inputstream des Dokuments</span>
	 * <span class="en">InputStream of the incoming document</span>
	 * @param cp <span class="de">auch null m&oumlglich dann setzen auf Standardwerte</span>
	 * <span class="en">configuration parameters from user or null</span>
	 * @return <span class="de">Liste von OutputObjecten</span>
	 * <span class="en">List of OutputObjects</span>
	 */
	public List<OutputObject> extractXlsx (InputStream stream, Configuration cp) {
		if (stream == null)
			return null;
		ExcelInMatrixCellExtraction ee = new ExcelInMatrixCellExtraction();
		return ee.excelInputstreamXssf(stream, cp);
	}
	
	/**
	 * <span class="de">Nimmt Files (xls) durch ihren Inputstream auf und f&uumlhrt diese der Matrix zu.</span>
	 * <span class="en">Takes the InputStream of an xls document and add it to the extraction pipeline.</span>
	 * @param stream <span class="de">Inputstream des Dokuments</span>
	 * <span class="en">InputStream of the incoming document</span>
	 * @return <span class="de">Liste von OutputObjecten</span>
	 * <span class="en">List of OutputObjects</span>
	 */
	public List<OutputObject> extractXls (InputStream stream) {
		return extractXls(stream, null);
	}
	
	/**
	 * <span class="de">Nimmt Files (xlsx) durch ihren Inputstream auf und f&uumlhrt diese der Matrix zu.</span>
	 * <span class="en">Takes the InputStream of an xslx document and add it to the extraction pipeline.</span>
	 * @param stream <span class="de">Inputstream des Dokuments</span>
	 * <span class="en">InputStream of the incoming document</span>
	 * @return <span class="de">Liste von OutputObjecten</span>
	 * <span class="en">List of OutputObjects</span>
	 */
	public List<OutputObject> extractXlsx (InputStream stream) {
		return extractXlsx(stream, null);
	}
	
	/**
	 * <span class="de">Nimmt Files (json) durch ihren Inputstream auf und f&uumlhrt diese der Matrix zu.</span>
	 * <span class="en">Takes the InputStream of an json document and add it to the extraction pipeline.</span>
	 * @param stream <span class="de">Inputstream des Dokuments</span>
	 * <span class="en">InputStream of the incoming document</span>
	 * @param cp <span class="de">auch null m&oumlglich dann setzen auf Standardwerte</span>
	 * <span class="en">configuration parameters from user or null</span>
	 * @return <span class="de">Liste von OutputObjecten</span>
	 * <span class="en">List of OutputObjects</span>
	 */
	/*public List<OutputObject> extractJson (InputStream stream, Configuration cp) {
		JsonExtraction je = new JsonExtraction();
		return je.valuesToMatrix(stream, cp);
	}*/
	
	/**
	 * <span class="de">Nimmt Files (csv) durch ihren Inputstream auf und f&uumlhrt diese der Matrix zu.</span>
	 * <span class="en">Takes the InputStream of an csv document and add it to the extraction pipeline.</span>
	 * @param stream <span class="de">Inputstream des Dokuments</span>
	 * <span class="en">InputStream of the incoming document</span>
	 * @param cp <span class="de">auch null m&oumlglich dann setzen auf Standardwerte</span>
	 * <span class="en">configuration parameters from user or null</span>
	 * @return <span class="de">Liste von OutputObjecten</span>
	 * <span class="en">List of OutputObjects</span>
	 */
	public List<OutputObject> extractCsv (InputStream stream, Configuration cp) {
		if (stream == null)
			return null;
		CsvExtraction ce = new CsvExtraction();
		return ce.valuesToMatrix(stream, cp, ',');
	}
	
	/**
	 * <span class="de">Nimmt Files (json) durch ihren Inputstream auf und f&uumlhrt diese der Matrix zu.</span>
	 * <span class="en">Takes the InputStream of an json document and add it to the extraction pipeline.</span>
	 * @param stream <span class="de">Inputstream des Dokuments</span>
	 * <span class="en">InputStream of the incoming document</span>
	 * @return <span class="de">Liste von OutputObjecten</span>
	 * <span class="en">List of OutputObjects</span>
	 */
	/*public List<OutputObject> extractJson (InputStream stream) {
		JsonExtraction je = new JsonExtraction();
		return je.valuesToMatrix(stream, null);
	}*/
	
	/**
	 * <span class="de">Nimmt Files (csv) durch ihren Inputstream auf und f&uumlhrt diese der Matrix zu.</span>
	 * <span class="en">Takes the InputStream of an csv document and add it to the extraction pipeline.</span>
	 * @param stream <span class="de">Inputstream des Dokuments</span>
	 * <span class="en">InputStream of the incoming document</span>
	 * @return <span class="de">Liste von OutputObjecten</span>
	 * <span class="en">List of OutputObjects</span>
	 */
	public List<OutputObject> extractCsv (InputStream stream) {
		return extractCsv(stream, null);
	}
	
	/**
	 * <span class="de">Nimmt eine URL und l&oumlst alle Relationen aus ihr.</span>
	 * <span class="en">Takes an URL an extract all relations from it.</span>
	 * @param url <span class="de">WWW Adresse einer Webseite</span>
	 * <span class="en">URL as string</span>
	 * @param cp <span class="de">auch null m&oumlglich dann setzen auf Standardwerte</span>
	 * <span class="en">configuration parameters from user or null</span>
	 * @return <span class="de">Liste von OutputObjecten</span>
	 * <span class="en">List of OutputObjects</span>
	 */
	public List<OutputObject> extractUrl (String url, Configuration cp) {
		if (url == null || url.trim().equals(""))
			return null;
		WebsiteReader wr = new WebsiteReader();
		return wr.doIt(url, cp);
	}
	
	/**
	 * <span class="de">Nimmt JSON Code und verarbeitet diesen (Variante1).</span>
	 * <span class="en">Takes json code and handle it with option 1.</span>
	 * @param code <span class="de">JSON Code</span>
	 * <span class="en">json code</span>
	 * @param cp <span class="de">auch null m&oumlglich dann setzen auf Standardwerte</span>
	 * <span class="en">configuration parameters from user or null</span>
	 * @return <span class="de">Liste von OutputObjecten</span>
	 * <span class="en">List of OutputObjects</span>
	 */
	/*public List<OutputObject> extractJson1 (String code, Configuration cp) {
		JsonExtraction je = new JsonExtraction();
		return je.lineToMatrix(code, cp);
	}*/
	
	/**
	 * <span class="de">Nimmt JSON Code und verarbeitet diesen (Variante2).</span>
	 * <span class="en">Takes json code and handle it with option 2.</span>
	 * @param code <span class="de">JSON Code</span>
	 * <span class="en">json code</span>
	 * @param cp <span class="de">auch null m&oumlglich dann setzen auf Standardwerte</span>
	 * <span class="en">configuration parameters from user or null</span>
	 * @return <span class="de">Liste von OutputObjecten</span>
	 * <span class="en">List of OutputObjects</span>
	 */
	/*public List<OutputObject> extractJson2 (String code, Configuration cp) {
		JsonExtraction je = new JsonExtraction();
		return je.lineToMatrixSecond(code, cp);
	}*/
	
	/**
	 * <span class="de">Nimmt HTML Code und verarbeitet diesen.</span>
	 * <span class="en">Take html code an work on it.</span>
	 * @param code <span class="de">HTML Code</span>
	 * <span class="en">html code</span>
	 * @param cp <span class="de">auch null m&oumlglich dann setzen auf Standardwerte</span>
	 * <span class="en">configuration parameters from user or null</span>
	 * @return <span class="de">Liste von OutputObjecten</span>
	 * <span class="en">List of OutputObjects</span>
	 */
	public List<OutputObject> extractHtml (String code, Configuration cp) {
		HTMLExtraction he = new HTMLExtraction ();
		return he.tablesFromHTML("", code, cp);
	}
	
	/**
	 * <span class="de">Nimmt File auf und f&uumlhrt dieses der Extraktionspipeline zu.</span>
	 * <span class="en">Takes a file with its directory an extract it in the piepeline.</span>
	 * @param f <span class="de">File name eigene Analyse</span>
	 * <span class="en">file for extraction</span>
	 * @return <span class="de">Liste von OutputObjecten</span>
	 * <span class="en">List of OutputObjects</span>
	 */
	public List<OutputObject> extract (File f) {
		return extract(f, null);
	}

	/**
	 * <span class="de">Nimmt Dateinamen eines Files auf und f&uumlhrt dieses der Extraktionspipeline hinzu.</span>
	 * <span class="en">Takes a file with its directory an extract it in the piepeline.</span>
	 * @param dateiname <span class="de">Dateiname der eingehenden Datei (Pfad)</span>
	 * <span class="en">directory of the document</span>
	 * @return <span class="de">Liste von OutputObjecten</span>
	 * <span class="en">List of OutputObjects</span>
	 */
	public List<OutputObject> extract (String dateiname) {
		return extract(dateiname, null);
	}
	
	/**
	 * <span class="de">Nimmt eine URL und l&oumlst alle Relationen aus ihr.</span>
	 * <span class="en">Takes an URL an extract all relations from it.</span>
	 * @param url <span class="de">WWW Adresse einer Webseite</span>
	 * <span class="en">URL as string</span>
	 * @return <span class="de">Liste von OutputObjecten</span>
	 * <span class="en">List of OutputObjects</span>
	 */
	public List<OutputObject> extractUrl (String url) {
		return extractUrl(url, null);
	}
	
	/**
	 * <span class="de">Nimmt JSON Code und verarbeitet diesen (Variante1).</span>
	 * <span class="en">Takes json code and handle it with option 1.</span>
	 * @param code <span class="de">JSON Code</span>
	 * <span class="en">json code</span>
	 * @return <span class="de">Liste von OutputObjecten</span>
	 * <span class="en">List of OutputObjects</span>
	 */
	/*public List<OutputObject> extractJson1 (String code) {
		JsonExtraction je = new JsonExtraction();
		return je.lineToMatrix(code, null);
	}*/
	
	/**
	 * <span class="de">Nimmt JSON Code und verarbeitet diesen (Variante2).</span>
	 * <span class="en">Takes json code and handle it with option 2.</span>
	 * @param code <span class="de">JSON Code</span>
	 * <span class="en">json code</span>
	 * @return <span class="de">Liste von OutputObjecten</span>
	 * <span class="en">List of OutputObjects</span>
	 */
	/*public List<OutputObject> extractJson2 (String code) {
		JsonExtraction je = new JsonExtraction();
		return je.lineToMatrixSecond(code, null);
	}*/
	
	/**
	 * <span class="de">Nimmt HTML Code und verarbeitet diesen.</span>
	 * <span class="en">Take html code an work with it.</span>
	 * @param code <span class="de">HTML Code</span>
	 * <span class="en">html code</span>
	 * @return <span class="de">Liste von OutputObjecten</span>
	 * <span class="en">List of OutputObjects</span>
	 */
	public List<OutputObject> extractHtml (String code) {
		return extractHtml(code, null);
	}
	
	/**
	 * <span class="de">Nimmt JSON Code und verarbeitet diesen (Variante1).</span>
	 * <span class="en">Takes json code and handle it with option 1.</span>
	 * @param stream <span class="de">JSON Code als InputStream</span>
	 * <span class="en">json code as InputStream</span>
	 * @return <span class="de">Liste von OutputObjecten</span>
	 * <span class="en">List of OutputObjects</span>
	 */
	/*public List<OutputObject> extractJson1 (InputStream stream) {
		return this.extractJson1(stream, null);
	}*/
	
	/**
	 * <span class="de">Nimmt JSON Code und verarbeitet diesen (Variante2).</span>
	 * <span class="en">Takes json code and handle it with option 2.</span>
	 * @param stream <span class="de">JSON Code als InputStream</span>
	 * <span class="en">json code as InputStream</span>
	 * @return <span class="de">Liste von OutputObjecten</span>
	 * <span class="en">List of OutputObjects</span>
	 */
	/*public List<OutputObject> extractJson2 (InputStream stream) {
		return this.extractJson2(stream, null);
	}*/
	
	/**
	 * <span class="de">Nimmt HTML Code und verarbeitet diesen.</span>
	 * <span class="en">Take html code an work with it.</span>
	 * @param stream <span class="de">HTML Code als InputStream</span>
	 * <span class="en">html code as InputStream</span>
	 * @return <span class="de">Liste von OutputObjecten</span>
	 * <span class="en">List of OutputObjects</span>
	 */
	public List<OutputObject> extractHtml (InputStream stream) {
		return extractHtml(stream, null);
	}

	/**
	 * <span class="de">Nimmt JSON Code und verarbeitet diesen (Variante1).</span>
	 * <span class="en">Takes json code and handle it with option 1.</span>
	 * @param stream <span class="de">JSON Code als InputStream</span>
	 * <span class="en">json code as InputStream</span>
	 * @param cp <span class="de">auch null m&oumlglich dann setzen auf Standardwerte</span>
	 * <span class="en">configuration parameters from user or null</span>
	 * @return <span class="de">Liste von OutputObjecten</span>
	 * <span class="en">List of OutputObjects</span>
	 */
	/*public List<OutputObject> extractJson1 (InputStream stream, Configuration cp) {
		InputstreamToString its = new InputstreamToString();
		String code = its.fromInputStreamOne(stream);
		JsonExtraction je = new JsonExtraction();
		return je.lineToMatrix(code, cp);
	}*/
	
	/**
	 * <span class="de">Nimmt JSON Code und verarbeitet diesen (Variante2).</span>
	 * <span class="en">Takes json code and handle it with option 2.</span>
	 * @param stream <span class="de">JSON Code als InputStream</span>
	 * <span class="en">json code as InputStream</span>
	 * @param cp <span class="de">auch null m&oumlglich dann setzen auf Standardwerte</span>
	 * <span class="en">configuration parameters from user or null</span>
	 * @return <span class="de">Liste von OutputObjecten</span>
	 * <span class="en">List of OutputObjects</span>
	 */
	/*public List<OutputObject> extractJson2 (InputStream stream, Configuration cp) {
		InputstreamToString its = new InputstreamToString();
		String code = its.fromInputStreamOne(stream);
		JsonExtraction je = new JsonExtraction();
		return je.lineToMatrixSecond(code, cp);
	}*/
	
	/**
	 * <span class="de">Nimmt HTML Code und verarbeitet diesen.</span>
	 * <span class="en">Take html code and handle it.</span>
	 * @param stream <span class="de">HTML Code als InputStream</span>
	 * <span class="en">html code as InputStream</span>
	 * @param cp <span class="de">auch null m&oumlglich dann setzen auf Standardwerte</span>
	 * <span class="en">configuration parameters from user or null</span>
	 * @return <span class="de">Liste von OutputObjecten</span>
	 * <span class="en">List of OutputObjects</span>
	 */
	public List<OutputObject> extractHtml (InputStream stream, Configuration cp) {
		if (stream == null)
			return null;
		InputstreamToString its = new InputstreamToString();
		String code = its.fromInputStreamOne(stream);
		HTMLExtraction he = new HTMLExtraction ();
		return he.tablesFromHTML("", code, cp);
	}

}
