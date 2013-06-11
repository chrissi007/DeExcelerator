package de.tudresden.deexcelerator.tests;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

import de.tudresden.deexcelerator.Configuration;
import de.tudresden.deexcelerator.DeExcelerator;
import de.tudresden.deexcelerator.data.InputstreamToString;
import de.tudresden.deexcelerator.output.OutputObject;
import junit.framework.TestCase;

public class Excel2Test extends TestCase {

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
	
	public void testExcel2Document1 () {
		boolean result = true;
		String dateiname = "XlsxBeispiel1.xlsx";
		Configuration c = new Configuration();
		c.onlyOneTable = true;
		List<OutputObject> outs = de.extract("Tests/Beispiel/XlsxBeispielDokumente/" + dateiname, c);
		for (int j = 0; j < outs.size(); j++)
		{	
			if (outs.get(j) == null)
				continue;
			String inhaltStr = outs.get(j).toJson();
			String resultStr = createResultString("Tests/Loesung/Xlsx/" + dateiname + j + ".txt");
			if (!resultStr.equals(inhaltStr))
				result = false;
		}
		assertTrue(result);
	}
	
	public void testExcel2Document2 () {
		boolean result = true;
		String dateiname = "XlsxBeispiel2.xlsx";
		List<OutputObject> outs = de.extract("Tests/Beispiel/XlsxBeispielDokumente/" + dateiname);
		for (int j = 0; j < outs.size(); j++)
		{	
			if (outs.get(j) == null)
				continue;
			String inhaltStr = outs.get(j).toJson();
			String resultStr = createResultString("Tests/Loesung/Xlsx/" + dateiname + j + ".txt");
			if (!resultStr.equals(inhaltStr))
				result = false;
		}
		assertTrue(result);
	}
	
	public void testExcel2Document3 () {
		boolean result = true;
		String dateiname = "XlsxBeispiel3.xlsx";
		List<OutputObject> outs = de.extract("Tests/Beispiel/XlsxBeispielDokumente/" + dateiname);
		for (int j = 0; j < outs.size(); j++)
		{	
			if (outs.get(j) == null)
				continue;
			String inhaltStr = outs.get(j).toJson();
			String resultStr = createResultString("Tests/Loesung/Xlsx/" + dateiname + j + ".txt");
			if (!resultStr.equals(inhaltStr))
				result = false;
		}
		assertTrue(result);
	}
	
	public void testExcel2Document4 () {
		boolean result = true;
		String dateiname = "XlsxBeispiel4.xlsx";
		Configuration c = new Configuration();
		c.onlyOneTable = true;
		List<OutputObject> outs = de.extract("Tests/Beispiel/XlsxBeispielDokumente/" + dateiname, c);
		for (int j = 0; j < outs.size(); j++)
		{	
			if (outs.get(j) == null)
				continue;
			String inhaltStr = outs.get(j).toJson();
			String resultStr = createResultString("Tests/Loesung/Xlsx/" + dateiname + j + ".txt");
			if (!resultStr.equals(inhaltStr))
				result = false;
		}
		assertTrue(result);
	}
	
	public void testExcel2Document5 () {
		boolean result = true;
		String dateiname = "XlsxBeispiel5.xlsx";
		Configuration c = new Configuration();
		c.onlyOneTable = true;
		List<OutputObject> outs = de.extract("Tests/Beispiel/XlsxBeispielDokumente/" + dateiname, c);
		for (int j = 0; j < outs.size(); j++)
		{	
			if (outs.get(j) == null)
				continue;
			String inhaltStr = outs.get(j).toJson();
			String resultStr = createResultString("Tests/Loesung/Xlsx/" + dateiname + j + ".txt");
			if (!resultStr.equals(inhaltStr))
				result = false;
		}
		assertTrue(result);
	}
	
	public void testExcel2Document6 () {
		boolean result = true;
		String dateiname = "XlsxBeispiel6.xlsx";
		List<OutputObject> outs = de.extract("Tests/Beispiel/XlsxBeispielDokumente/" + dateiname);
		for (int j = 0; j < outs.size(); j++)
		{	
			if (outs.get(j) == null)
				continue;
			String inhaltStr = outs.get(j).toJson();
			String resultStr = createResultString("Tests/Loesung/Xlsx/" + dateiname + j + ".txt");
			if (!resultStr.equals(inhaltStr))
				result = false;
		}
		assertTrue(result);
	}
	
	public void testExcel2Document7 () {
		boolean result = true;
		String dateiname = "XlsxBeispiel7.xlsx";
		Configuration c = new Configuration();
		c.onlyOneTable = true;
		List<OutputObject> outs = de.extract("Tests/Beispiel/XlsxBeispielDokumente/" + dateiname, c);
		for (int j = 0; j < outs.size(); j++)
		{	
			if (outs.get(j) == null)
				continue;
			String inhaltStr = outs.get(j).toJson();
			String resultStr = createResultString("Tests/Loesung/Xlsx/" + dateiname + j + ".txt");
			if (!resultStr.equals(inhaltStr))
				result = false;
		}
		assertTrue(result);
	}
	
	public void testExcel2Document8 () {
		boolean result = true;
		String dateiname = "XlsxBeispiel8.xlsx";
		List<OutputObject> outs = de.extract("Tests/Beispiel/XlsxBeispielDokumente/" + dateiname);
		for (int j = 0; j < outs.size(); j++)
		{	
			if (outs.get(j) == null)
				continue;
			String inhaltStr = outs.get(j).toJson();
			String resultStr = createResultString("Tests/Loesung/Xlsx/" + dateiname + j + ".txt");
			if (!resultStr.equals(inhaltStr))
				result = false;
		}
		assertTrue(result);
	}
	
	public void testExcel2Document9 () {
		boolean result = true;
		String dateiname = "XlsxBeispiel9.xlsx";
		Configuration c = new Configuration();
		c.onlyOneTable = true;
		List<OutputObject> outs = de.extract("Tests/Beispiel/XlsxBeispielDokumente/" + dateiname, c);
		for (int j = 0; j < outs.size(); j++)
		{	
			if (outs.get(j) == null)
				continue;
			String inhaltStr = outs.get(j).toJson();
			String resultStr = createResultString("Tests/Loesung/Xlsx/" + dateiname + j + ".txt");
			if (!resultStr.equals(inhaltStr))
				result = false;
		}
		assertTrue(result);
	}
	
	public void testExcel2Document10 () {
		boolean result = true;
		String dateiname = "XlsxBeispiel10.xlsx";
		Configuration c = new Configuration();
		c.limitForNoContent = 2;
		List<OutputObject> outs = de.extract("Tests/Beispiel/XlsxBeispielDokumente/" + dateiname, c);
		for (int j = 0; j < outs.size(); j++)
		{	
			if (outs.get(j) == null)
				continue;
			String inhaltStr = outs.get(j).toJson();
			String resultStr = createResultString("Tests/Loesung/Xlsx/" + dateiname + j + ".txt");
			if (!resultStr.equals(inhaltStr))
				result = false;
		}
		assertTrue(result);
	}
	
	public void testExcel2Document11 () {
		boolean result = true;
		String dateiname = "XlsxBeispiel11.xlsx";
		Configuration c = new Configuration();
		c.onlyOneTable = true;
		List<OutputObject> outs = de.extract("Tests/Beispiel/XlsxBeispielDokumente/" + dateiname, c);
		for (int j = 0; j < outs.size(); j++)
		{	
			if (outs.get(j) == null)
				continue;
			String inhaltStr = outs.get(j).toJson();
			String resultStr = createResultString("Tests/Loesung/Xlsx/" + dateiname + j + ".txt");
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
