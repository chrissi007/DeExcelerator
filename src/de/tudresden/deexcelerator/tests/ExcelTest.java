package de.tudresden.deexcelerator.tests;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

import de.tudresden.deexcelerator.Configuration;
import de.tudresden.deexcelerator.DeExcelerator;
import de.tudresden.deexcelerator.data.InputstreamToString;
import de.tudresden.deexcelerator.output.OutputObject;
import junit.framework.TestCase;

public class ExcelTest extends TestCase {

	private DeExcelerator de;
	
	public void setUp() {
		de = new DeExcelerator();
	}
	
	private String createResultString (String dateiname) {
		String result = "";
		try {
			FileInputStream fis = new FileInputStream(dateiname);
			InputstreamToString is = new InputstreamToString();
			result = is.fromInputStreamOne(fis);
		} catch (FileNotFoundException e) {
			System.out.println("File nicht erzeugt!");
			e.printStackTrace();
		}
		return result;
	}
	
	public void testExcelDocument1 () {
		boolean result = true;
		String dateiname = "XlsBeispiel1.xls";
		List<OutputObject> outs = de.extract("Tests/Beispiel/XlsBeispielDokumente/" + dateiname);
		for (int j = 0; j < outs.size(); j++)
		{	
			if (outs.get(j) == null)
				continue;
			String inhaltStr = outs.get(j).toJson();
			String resultStr = createResultString("Tests/Loesung/Xls/" + dateiname + j + ".txt");
			if (!resultStr.equals(inhaltStr))
				result = false;
		}
		assertTrue(result);
	}
	
	public void testExcelDocument2 () {
		boolean result = true;
		String dateiname = "XlsBeispiel2.xls";
		List<OutputObject> outs = de.extract("Tests/Beispiel/XlsBeispielDokumente/" + dateiname);
		for (int j = 0; j < outs.size(); j++)
		{	
			if (outs.get(j) == null)
				continue;
			String inhaltStr = outs.get(j).toJson();
			String resultStr = createResultString("Tests/Loesung/Xls/" + dateiname + j + ".txt");
			if (!resultStr.equals(inhaltStr))
				result = false;
		}
		assertTrue(result);
	}
	
	public void testExcelDocument3 () {
		boolean result = true;
		String dateiname = "XlsBeispiel3.xls";
		List<OutputObject> outs = de.extract("Tests/Beispiel/XlsBeispielDokumente/" + dateiname);
		for (int j = 0; j < outs.size(); j++)
		{	
			if (outs.get(j) == null)
				continue;
			String inhaltStr = outs.get(j).toJson();
			String resultStr = createResultString("Tests/Loesung/Xls/" + dateiname + j + ".txt");
			if (!resultStr.equals(inhaltStr))
				result = false;
		}
		assertTrue(result);
	}
	
	public void testExcelDocument4 () {
		boolean result = true;
		String dateiname = "XlsBeispiel4.xls";
		List<OutputObject> outs = de.extract("Tests/Beispiel/XlsBeispielDokumente/" + dateiname);
		for (int j = 0; j < outs.size(); j++)
		{	
			if (outs.get(j) == null)
				continue;
			String inhaltStr = outs.get(j).toJson();
			String resultStr = createResultString("Tests/Loesung/Xls/" + dateiname + j + ".txt");
			if (!resultStr.equals(inhaltStr))
				result = false;
		}
		assertTrue(result);
	}
	
	public void testExcelDocument5 () {
		boolean result = true;
		String dateiname = "XlsBeispiel5.xls";
		List<OutputObject> outs = de.extract("Tests/Beispiel/XlsBeispielDokumente/" + dateiname);
		for (int j = 0; j < outs.size(); j++)
		{	
			if (outs.get(j) == null)
				continue;
			String inhaltStr = outs.get(j).toJson();
			String resultStr = createResultString("Tests/Loesung/Xls/" + dateiname + j + ".txt");
			if (!resultStr.equals(inhaltStr))
				result = false;
		}
		assertTrue(result);
	}
	
	public void testExcelDocument6 () {
		boolean result = true;
		String dateiname = "XlsBeispiel6.xls";
		List<OutputObject> outs = de.extract("Tests/Beispiel/XlsBeispielDokumente/" + dateiname);
		for (int j = 0; j < outs.size(); j++)
		{	
			if (outs.get(j) == null)
				continue;
			String inhaltStr = outs.get(j).toJson();
			String resultStr = createResultString("Tests/Loesung/Xls/" + dateiname + j + ".txt");
			if (!resultStr.equals(inhaltStr))
				result = false;
		}
		assertTrue(result);
	}
	
	public void testExcelDocument7 () {
		boolean result = true;
		String dateiname = "XlsBeispiel7.xls";
		List<OutputObject> outs = de.extract("Tests/Beispiel/XlsBeispielDokumente/" + dateiname);
		for (int j = 0; j < outs.size(); j++)
		{	
			if (outs.get(j) == null)
				continue;
			String inhaltStr = outs.get(j).toJson();
			String resultStr = createResultString("Tests/Loesung/Xls/" + dateiname + j + ".txt");
			if (!resultStr.equals(inhaltStr))
				result = false;
		}
		assertTrue(result);
	}
	
	public void testExcelDocument8 () {
		boolean result = true;
		String dateiname = "XlsBeispiel8.xls";
		Configuration c = new Configuration();
		c.onlyOneTable = true;
		List<OutputObject> outs = de.extract("Tests/Beispiel/XlsBeispielDokumente/" + dateiname, c);
		for (int j = 0; j < outs.size(); j++)
		{	
			if (outs.get(j) == null)
				continue;
			String inhaltStr = outs.get(j).toJson();
			String resultStr = createResultString("Tests/Loesung/Xls/" + dateiname + j + ".txt");
			if (!resultStr.equals(inhaltStr))
				result = false;
		}
		assertTrue(result);
	}
	
	public void testExcelDocument9 () {
		boolean result = true;
		String dateiname = "XlsBeispiel9.xls";
		List<OutputObject> outs = de.extract("Tests/Beispiel/XlsBeispielDokumente/" + dateiname);
		for (int j = 0; j < outs.size(); j++)
		{	
			if (outs.get(j) == null)
				continue;
			String inhaltStr = outs.get(j).toJson();
			String resultStr = createResultString("Tests/Loesung/Xls/" + dateiname + j + ".txt");
			if (!resultStr.equals(inhaltStr))
				result = false;
		}
		assertTrue(result);
	}
	
	@SuppressWarnings("unused")
	private void differenceStrings (String a, String b) {
		char[] c = a.toCharArray();
		char[] d = b.toCharArray();
		if (c.length == d.length)
		{
			int counter = 0;
			for (int i = 0; i< c.length; i++)
			{
				if (c[i] != d[i])
				{
					counter = 10;
				}
				if (counter > 0)
				{
					counter--;
					System.out.println("Stelle: " + i + " C: " + c[i] + " D: " + d[i]);
				}
			}
		}
		else
			System.out.println("unterschiedliche Länge!");
	}
	
	public void testExcelDocument10 () {
		boolean result = true;
		String dateiname = "XlsBeispiel10.xls";
		List<OutputObject> outs = de.extract("Tests/Beispiel/XlsBeispielDokumente/" + dateiname);
		for (int j = 0; j < outs.size(); j++)
		{	
			if (outs.get(j) == null)
				continue;
			String inhaltStr = outs.get(j).toJson();
			String resultStr = createResultString("Tests/Loesung/Xls/" + dateiname + j + ".txt");
			if (!resultStr.equals(inhaltStr))
				result = false;
		}
		assertTrue(result);
	}
	
	public void testExcelDocument11 () {
		boolean result = true;
		String dateiname = "XlsBeispiel11.xls";
		List<OutputObject> outs = de.extract("Tests/Beispiel/XlsBeispielDokumente/" + dateiname);
		for (int j = 0; j < outs.size(); j++)
		{	
			if (outs.get(j) == null)
				continue;
			String inhaltStr = outs.get(j).toJson();
			String resultStr = createResultString("Tests/Loesung/Xls/" + dateiname + j + ".txt");
			if (!resultStr.equals(inhaltStr))
				result = false;
		}
		assertTrue(result);
	}
	
	public void testExcelDocument12 () {
		boolean result = true;
		String dateiname = "XlsBeispiel12.xls";
		List<OutputObject> outs = de.extract("Tests/Beispiel/XlsBeispielDokumente/" + dateiname);
		for (int j = 0; j < outs.size(); j++)
		{	
			if (outs.get(j) == null)
				continue;
			String inhaltStr = outs.get(j).toJson();
			String resultStr = createResultString("Tests/Loesung/Xls/" + dateiname + j + ".txt");
			if (!resultStr.equals(inhaltStr))
				result = false;
		}
		assertTrue(result);
	}
	
	public void testExcelDocument13 () {
		boolean result = true;
		String dateiname = "XlsBeispiel13.xls";
		List<OutputObject> outs = de.extract("Tests/Beispiel/XlsBeispielDokumente/" + dateiname);
		for (int j = 0; j < outs.size(); j++)
		{	
			if (outs.get(j) == null)
				continue;
			String inhaltStr = outs.get(j).toJson();
			String resultStr = createResultString("Tests/Loesung/Xls/" + dateiname + j + ".txt");
			if (!resultStr.equals(inhaltStr))
				result = false;
		}
		assertTrue(result);
	}
	
	public void testExcelDocument14 () {
		boolean result = true;
		String dateiname = "XlsBeispiel14.xls";
		List<OutputObject> outs = de.extract("Tests/Beispiel/XlsBeispielDokumente/" + dateiname);
		for (int j = 0; j < outs.size(); j++)
		{	
			if (outs.get(j) == null)
				continue;
			String inhaltStr = outs.get(j).toJson();
			String resultStr = createResultString("Tests/Loesung/Xls/" + dateiname + j + ".txt");
			if (!resultStr.equals(inhaltStr))
				result = false;
		}
		assertTrue(result);
	}
	
	public void testExcelDocument15 () {
		boolean result = true;
		String dateiname = "XlsBeispiel15.xls";
		Configuration c = new Configuration();
		c.onlyOneTable = true;
		List<OutputObject> outs = de.extract("Tests/Beispiel/XlsBeispielDokumente/" + dateiname, c);
		for (int j = 0; j < outs.size(); j++)
		{	
			if (outs.get(j) == null)
				continue;
			String inhaltStr = outs.get(j).toJson();
			String resultStr = createResultString("Tests/Loesung/Xls/" + dateiname + j + ".txt");
			if (!resultStr.equals(inhaltStr))
				result = false;
		}
		assertTrue(result);
	}
	
	public void testExcelDocument16 () {
		boolean result = true;
		String dateiname = "XlsBeispiel16.xls";
		Configuration c = new Configuration();
		c.limitForNoContent = 2;		
		List<OutputObject> outs = de.extract("Tests/Beispiel/XlsBeispielDokumente/" + dateiname, c);
		for (int j = 0; j < outs.size(); j++)
		{	
			if (outs.get(j) == null)
				continue;
			String inhaltStr = outs.get(j).toJson();
			String resultStr = createResultString("Tests/Loesung/Xls/" + dateiname + j + ".txt");
			if (!resultStr.equals(inhaltStr))
				result = false;
		}
		assertTrue(result);
	}
	
	public void testExcelDocument17 () {
		boolean result = true;
		String dateiname = "XlsBeispiel17.xls";
		List<OutputObject> outs = de.extract("Tests/Beispiel/XlsBeispielDokumente/" + dateiname);
		for (int j = 0; j < outs.size(); j++)
		{	
			if (outs.get(j) == null)
				continue;
			String inhaltStr = outs.get(j).toJson();
			String resultStr = createResultString("Tests/Loesung/Xls/" + dateiname + j + ".txt");
			if (!resultStr.equals(inhaltStr))
				result = false;
		}
		assertTrue(result);
	}
	
	public void testExcelDocument18 () {
		boolean result = true;
		String dateiname = "XlsBeispiel18.xls";
		List<OutputObject> outs = de.extract("Tests/Beispiel/XlsBeispielDokumente/" + dateiname);
		for (int j = 0; j < outs.size(); j++)
		{	
			if (outs.get(j) == null)
				continue;
			String inhaltStr = outs.get(j).toJson();
			String resultStr = createResultString("Tests/Loesung/Xls/" + dateiname + j + ".txt");
			if (!resultStr.equals(inhaltStr))
				result = false;
		}
		assertTrue(result);
	}
	
	public void testExcelDocument19 () {
		boolean result = true;
		String dateiname = "XlsBeispiel19.xls";
		List<OutputObject> outs = de.extract("Tests/Beispiel/XlsBeispielDokumente/" + dateiname);
		for (int j = 0; j < outs.size(); j++)
		{	
			if (outs.get(j) == null)
				continue;
			String inhaltStr = outs.get(j).toJson();
			String resultStr = createResultString("Tests/Loesung/Xls/" + dateiname + j + ".txt");
			if (!resultStr.equals(inhaltStr))
				result = false;
		}
		assertTrue(result);
	}
	
	public void testExcelDocument20 () {
		boolean result = true;
		String dateiname = "XlsBeispiel20.xls";
		List<OutputObject> outs = de.extract("Tests/Beispiel/XlsBeispielDokumente/" + dateiname);
		for (int j = 0; j < outs.size(); j++)
		{	
			if (outs.get(j) == null)
				continue;
			String inhaltStr = outs.get(j).toJson();
			String resultStr = createResultString("Tests/Loesung/Xls/" + dateiname + j + ".txt");
			if (!resultStr.equals(inhaltStr))
				result = false;
		}
		assertTrue(result);
	}
}

