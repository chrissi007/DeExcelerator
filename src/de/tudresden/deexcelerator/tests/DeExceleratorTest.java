package de.tudresden.deexcelerator.tests;

import java.io.File;
import java.io.FileInputStream;
import junit.framework.TestCase;

import de.tudresden.deexcelerator.Configuration;
import de.tudresden.deexcelerator.DeExcelerator;
import de.tudresden.deexcelerator.data.InputstreamToString;

public class DeExceleratorTest extends TestCase {

	private DeExcelerator de;
	private Configuration cp;
	
	public void setUp() {
		de = new DeExcelerator();
		cp = new Configuration();
	}
	
	public void testHtmlFunctions() {
		boolean result = true;
		try {
			InputstreamToString is = new InputstreamToString();
			FileInputStream fis = new FileInputStream("Tests/Beispiel/CodeBeispiele/HtmlCode1.txt");
			String code = is.fromInputStreamOne(fis);
			fis = new FileInputStream("Tests/Beispiel/CodeBeispiele/HtmlCode1.txt");
			de.extractHtml(fis);
			de.extractHtml(code);
			fis = new FileInputStream("Tests/Beispiel/CodeBeispiele/HtmlCode1.txt");
			de.extractHtml(fis, cp);
			de.extractHtml(code, cp);
			fis = new FileInputStream("Tests/Beispiel/CodeBeispiele/HtmlCode1.txt");
			de.extractHtml(fis, null);
			de.extractHtml(code, null);
		} catch (Exception e) {
			e.printStackTrace();
			result = false;
		}
		assertTrue(result);
	}
	
	public void testUrlFunctions() {
		boolean result = true;
		try {
			de.extractUrl("http://en.wikipedia.org/wiki/List_of_cities_in_Germany_by_population");
			de.extractUrl("http://en.wikipedia.org/wiki/List_of_cities_in_Germany_by_population", cp);
			de.extractUrl("http://en.wikipedia.org/wiki/List_of_cities_in_Germany_by_population", null);
		} catch (Exception e) {
			result = false;
		}
		assertTrue(result);
	}
	
	public void testXlsFunctions() {
		boolean result = true;
		try {
			File f = new File("Tests/Beispiel/XlsBeispielDokumente/XlsBeispiel2.xls");
			FileInputStream stream = new FileInputStream(f);
			String dateiname = f.getPath();
			de.extract(f);
			de.extract(dateiname);
			de.extractXls(stream);
			de.extract(f, cp);
			de.extract(dateiname, cp);
			stream = new FileInputStream(f);
			de.extractXls(stream, cp);
			de.extract(f, null);
			de.extract(dateiname, null);
			stream = new FileInputStream(f);
			de.extractXls(stream, null);
		} catch (Exception e) {
			result = false;
		}
		assertTrue(result);
	}
	
	public void testXlsxFunctions() {
		boolean result = true;
		try {
			File f = new File("Tests/Beispiel/XlsxBeispielDokumente/XlsxBeispiel1.xlsx");
			FileInputStream stream = new FileInputStream(f);
			String dateiname = f.getPath();
			de.extract(f);
			de.extract(dateiname);
			de.extractXlsx(stream);
			de.extract(f, cp);
			de.extract(dateiname, cp);
			stream = new FileInputStream(f);
			de.extractXlsx(stream, cp);
			de.extract(f, null);
			de.extract(dateiname, null);
			stream = new FileInputStream(f);
			de.extractXlsx(stream, null);
		} catch (Exception e) {
			result = false;
		}
		assertTrue(result);
	}
	
	public void testCsvFunctions() {
		boolean result = true;
		try {
			File f = new File("Tests/Beispiel/CsvBeispielDokumente/CsvBeispiel8.csv");
			FileInputStream stream = new FileInputStream(f);
			String dateiname = f.getPath();
			de.extract(f);
			de.extract(dateiname);
			de.extractCsv(stream);
			de.extract(f, cp);
			de.extract(dateiname, cp);
			stream = new FileInputStream(f);
			de.extractCsv(stream, cp);
			de.extract(f, null);
			de.extract(dateiname, null);
			stream = new FileInputStream(f);
			de.extractCsv(stream, null);
		} catch (Exception e) {
			result = false;
		}
		assertTrue(result);
	}
	
	public void testAntiFunctions() {
		boolean result = true;
		FileInputStream fis = null;
		FileInputStream fis1 = null;
		FileInputStream fis2 = null;
		try {
			fis1 = new FileInputStream("Tests/Beispiel/.csv.xls.xlsx.txt");
			fis2 = new FileInputStream("Tests/Beispiel/csv_xls_xlsx.txt");
		} catch (Exception e) {
			result = false;
		}
		File f = null;
		File f1 = null;
		File f2 = null;
		try {
			f = new File("");
			f1 = new File("Tests/Beispiel/.csv.xls.xlsx.txt");
			f2 = new File("Tests/Beispiel/csv_xls_xlsx.txt");
			de.extractHtml("");
			de.extractHtml("Test falscher Code!");
			de.extractHtml(fis);
			de.extractHtml(fis1);
			de.extractHtml(fis2);
			de.extractUrl(null);
			de.extractUrl("");
			de.extract(f);
			de.extract(f1);
			de.extract(f2);
			de.extract(f.getPath());
			de.extract(f1.getPath());
			de.extract(f2.getPath());
			de.extractXls(null);
			de.extractCsv(null);
			de.extractXlsx(null);
		} catch (Exception e) {
			result = false;
		}
		assertTrue(result);
	}
}
