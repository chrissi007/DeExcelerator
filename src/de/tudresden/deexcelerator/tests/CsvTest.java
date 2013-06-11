package de.tudresden.deexcelerator.tests;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

import de.tudresden.deexcelerator.Configuration;
import de.tudresden.deexcelerator.DeExcelerator;
import de.tudresden.deexcelerator.data.InputstreamToString;
import de.tudresden.deexcelerator.output.OutputObject;
import junit.framework.TestCase;

public class CsvTest extends TestCase {

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
	
	public void testCsvDocument1 () {
		boolean result = true;
		String dateiname = "CsvBeispiel1.csv";
		List<OutputObject> outs = de.extract("Tests/Beispiel/CsvBeispielDokumente/" + dateiname);
		for (int j = 0; j < outs.size(); j++)
		{	
			if (outs.get(j) == null)
				continue;
			String inhaltStr = outs.get(j).toJson();
			String resultStr = createResultString("Tests/Loesung/Csv/" + dateiname + j + ".txt");
			if (!resultStr.equals(inhaltStr))
				result = false;
		}
		assertTrue(result);
	}
	
	public void testCsvDocument2 () {
		boolean result = true;
		String dateiname = "CsvBeispiel2.csv";
		List<OutputObject> outs = de.extract("Tests/Beispiel/CsvBeispielDokumente/" + dateiname);
		for (int j = 0; j < outs.size(); j++)
		{	
			if (outs.get(j) == null)
				continue;
			String inhaltStr = outs.get(j).toJson();
			String resultStr = createResultString("Tests/Loesung/Csv/" + dateiname + j + ".txt");
			if (!resultStr.equals(inhaltStr))
				result = false;
		}
		assertTrue(result);
	}
	
	public void testCsvDocument3 () {
		boolean result = true;
		String dateiname = "CsvBeispiel3.csv";
		List<OutputObject> outs = de.extract("Tests/Beispiel/CsvBeispielDokumente/" + dateiname);
		for (int j = 0; j < outs.size(); j++)
		{	
			if (outs.get(j) == null)
				continue;
			String inhaltStr = outs.get(j).toJson();
			String resultStr = createResultString("Tests/Loesung/Csv/" + dateiname + j + ".txt");
			if (!resultStr.equals(inhaltStr))
				result = false;
		}
		assertTrue(result);
	}
	
	public void testCsvDocument4 () {
		boolean result = true;
		String dateiname = "CsvBeispiel4.csv";
		Configuration c = new Configuration();
		c.onlyOneTable = true;
		List<OutputObject> outs = de.extract("Tests/Beispiel/CsvBeispielDokumente/" + dateiname, c);
		for (int j = 0; j < outs.size(); j++)
		{	
			if (outs.get(j) == null)
				continue;
			String inhaltStr = outs.get(j).toJson();
			String resultStr = createResultString("Tests/Loesung/Csv/" + dateiname + j + ".txt");
			if (!resultStr.equals(inhaltStr))
				result = false;
		}
		assertTrue(result);
	}
	
	public void testCsvDocument5 () {
		boolean result = true;
		String dateiname = "CsvBeispiel5.csv";
		Configuration c = new Configuration();
		c.onlyOneTable = true;
		List<OutputObject> outs = de.extract("Tests/Beispiel/CsvBeispielDokumente/" + dateiname, c);
		for (int j = 0; j < outs.size(); j++)
		{	
			if (outs.get(j) == null)
				continue;
			String inhaltStr = outs.get(j).toJson();
			String resultStr = createResultString("Tests/Loesung/Csv/" + dateiname + j + ".txt");
			if (!resultStr.equals(inhaltStr))
				result = false;
		}
		assertTrue(result);
	}
	
	public void testCsvDocument6 () {
		boolean result = true;
		String dateiname = "CsvBeispiel6.csv";
		List<OutputObject> outs = de.extract("Tests/Beispiel/CsvBeispielDokumente/" + dateiname);
		for (int j = 0; j < outs.size(); j++)
		{	
			if (outs.get(j) == null)
				continue;
			String inhaltStr = outs.get(j).toJson();
			String resultStr = createResultString("Tests/Loesung/Csv/" + dateiname + j + ".txt");
			if (!resultStr.equals(inhaltStr))
				result = false;
		}
		assertTrue(result);
	}
	
	public void testCsvDocument7 () {
		boolean result = true;
		String dateiname = "CsvBeispiel7.csv";
		List<OutputObject> outs = de.extract("Tests/Beispiel/CsvBeispielDokumente/" + dateiname);
		for (int j = 0; j < outs.size(); j++)
		{	
			if (outs.get(j) == null)
				continue;
			String inhaltStr = outs.get(j).toJson();
			String resultStr = createResultString("Tests/Loesung/Csv/" + dateiname + j + ".txt");
			if (!resultStr.equals(inhaltStr))
				result = false;
		}
		assertTrue(result);
	}
	
	public void testCsvDocument8 () {
		boolean result = true;
		String dateiname = "CsvBeispiel8.csv";
		List<OutputObject> outs = de.extract("Tests/Beispiel/CsvBeispielDokumente/" + dateiname);
		for (int j = 0; j < outs.size(); j++)
		{	
			if (outs.get(j) == null)
				continue;
			String inhaltStr = outs.get(j).toJson();
			String resultStr = createResultString("Tests/Loesung/Csv/" + dateiname + j + ".txt");
			if (!resultStr.equals(inhaltStr))
				result = false;
		}
		assertTrue(result);
	}
	
	public void testCsvDocument9 () {
		boolean result = true;
		String dateiname = "CsvBeispiel9.csv";
		List<OutputObject> outs = de.extract("Tests/Beispiel/CsvBeispielDokumente/" + dateiname);
		for (int j = 0; j < outs.size(); j++)
		{	
			if (outs.get(j) == null)
				continue;
			String inhaltStr = outs.get(j).toJson();
			String resultStr = createResultString("Tests/Loesung/Csv/" + dateiname + j + ".txt");
			if (!resultStr.equals(inhaltStr))
				result = false;
		}
		assertTrue(result);
	}
	
	public void testCsvDocument10 () {
		boolean result = true;
		String dateiname = "CsvBeispiel10.csv";
		List<OutputObject> outs = de.extract("Tests/Beispiel/CsvBeispielDokumente/" + dateiname);
		for (int j = 0; j < outs.size(); j++)
		{	
			if (outs.get(j) == null)
				continue;
			String inhaltStr = outs.get(j).toJson();
			String resultStr = createResultString("Tests/Loesung/Csv/" + dateiname + j + ".txt");
			if (!resultStr.equals(inhaltStr))
				result = false;
		}
		assertTrue(result);
	}
	
	public void testCsvDocument11 () {
		boolean result = true;
		String dateiname = "CsvBeispiel11.csv";
		List<OutputObject> outs = de.extract("Tests/Beispiel/CsvBeispielDokumente/" + dateiname);
		for (int j = 0; j < outs.size(); j++)
		{	
			if (outs.get(j) == null)
				continue;
			String inhaltStr = outs.get(j).toJson();
			String resultStr = createResultString("Tests/Loesung/Csv/" + dateiname + j + ".txt");
			if (!resultStr.equals(inhaltStr))
				result = false;
		}
		assertTrue(result);
	}
	
	public void testCsvDocument12 () {
		boolean result = true;
		String dateiname = "CsvBeispiel12.csv";
		List<OutputObject> outs = de.extract("Tests/Beispiel/CsvBeispielDokumente/" + dateiname);
		for (int j = 0; j < outs.size(); j++)
		{	
			if (outs.get(j) == null)
				continue;
			String inhaltStr = outs.get(j).toJson();
			String resultStr = createResultString("Tests/Loesung/Csv/" + dateiname + j + ".txt");
			if (!resultStr.equals(inhaltStr))
				result = false;
		}
		assertTrue(result);
	}
	
	public void testCsvDocument13 () {
		boolean result = true;
		String dateiname = "CsvBeispiel13.csv";
		List<OutputObject> outs = de.extract("Tests/Beispiel/CsvBeispielDokumente/" + dateiname);
		for (int j = 0; j < outs.size(); j++)
		{	
			if (outs.get(j) == null)
				continue;
			String inhaltStr = outs.get(j).toJson();
			String resultStr = createResultString("Tests/Loesung/Csv/" + dateiname + j + ".txt");
			if (!resultStr.equals(inhaltStr))
				result = false;
		}
		assertTrue(result);
	}
	
	public void testCsvDocument14 () {
		boolean result = true;
		String dateiname = "CsvBeispiel14.csv";
		List<OutputObject> outs = de.extract("Tests/Beispiel/CsvBeispielDokumente/" + dateiname);
		for (int j = 0; j < outs.size(); j++)
		{	
			if (outs.get(j) == null)
				continue;
			String inhaltStr = outs.get(j).toJson();
			String resultStr = createResultString("Tests/Loesung/Csv/" + dateiname + j + ".txt");
			if (!resultStr.equals(inhaltStr))
				result = false;
		}
		assertTrue(result);
	}
	
	public void testCsvDocument15 () {
		boolean result = true;
		String dateiname = "CsvBeispiel15.csv";
		List<OutputObject> outs = de.extract("Tests/Beispiel/CsvBeispielDokumente/" + dateiname);
		for (int j = 0; j < outs.size(); j++)
		{	
			if (outs.get(j) == null)
				continue;
			String inhaltStr = outs.get(j).toJson();
			String resultStr = createResultString("Tests/Loesung/Csv/" + dateiname + j + ".txt");
			if (!resultStr.equals(inhaltStr))
				result = false;
		}
		assertTrue(result);
	}
	
	@SuppressWarnings("unused")
	private void differenceStrings (String a, String b) {
		char[] c = a.toCharArray();
		char[] d = b.toCharArray();
		int laenge = 0;
		if (c.length >= d.length)
			laenge = d.length;
		else
			laenge = c.length;
		if (c.length == d.length || true)
		{
			int counter = 0;
			for (int i = 0; i< laenge; i++)
			{
				if (c[i] != d[i])
				{
					counter = 30;
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
}
