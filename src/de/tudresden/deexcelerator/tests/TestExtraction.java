package de.tudresden.deexcelerator.tests;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.util.Date;
import java.util.List;
import de.tudresden.deexcelerator.Configuration;
import de.tudresden.deexcelerator.DeExcelerator;
import de.tudresden.deexcelerator.core.CsvExtraction;
import de.tudresden.deexcelerator.core.JsonExtraction;
import de.tudresden.deexcelerator.data.InputstreamToString;
import de.tudresden.deexcelerator.output.OutputObject;


/**
 * <span class="de">Testmethode f&uumlr Performance Tests zum einf&uumlgen von Inhalten in die Pipeline.</span>
 * <span class="en"></span>
 * @author Christopher Werner
 *
 */
public class TestExtraction {

	/**
	 * <span class="de">Mainmethode zum pr&uumlfen.</span>
	 * <span class="en"></span>
	 * @param args
	 */
	public static void main(String[] args) {		
		TestExtraction te = new TestExtraction();
		//te.csvTest();
		//te.json1Test();
		//te.htmlTest();
		te.excelTest();
		//te.urlTest();
		//te.timeTest();
		//te.fehlerTest();
		//te.massenTest();
		//te.htmlCodeTest();
		//te.ordnerTest();
		//te.createXlsDokument();
		//te.createCodeDokument();
		//te.createUrlDokument();
		//te.createCsvDokument();
		//te.createXlsxDokument();
		//te.csvTest1();
		//te.excel2Test();
	}
	
	public void csvTest1() {
		String name = "CsvBeispiel5.csv"; 
		Configuration c = new Configuration();
		c.useOutputNameXls = true;
		c.onlyOneTable = true;
		try {
			DeExcelerator de = new DeExcelerator();
			List<OutputObject> outs = de.extract("Tests/Beispiel/CsvBeispielDokumente/" + name, c);
			for (int j = 0; j < outs.size(); j++)
			{	
				if (outs.get(j) == null)
					continue;
				String inhalt = outs.get(j).toJson();
		    	String dateiName = name + j + ".txt";
		        FileOutputStream schreibeStrom;
				schreibeStrom = new FileOutputStream(dateiName);
			    for (int k=0; k < inhalt.length(); k++)
			    	schreibeStrom.write((byte)inhalt.charAt(k));
			    schreibeStrom.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Fertig!");
	}
	
	/**
	 * <span class="de">Beispiel JSON Aufruf.
	 * Variante 1.</span>
	 * <span class="en"></span>
	 */
	public void json1Test () {
		JsonExtraction jtm = new JsonExtraction();
		jtm.valuesToMatrix("part-r-00000", null);
	}
	
	/**
	 * <span class="de">Beispiel JSON Aufruf.
	 * Variante 2.</span>
	 * <span class="en"></span>
	 */
	public void json2Test () {
		JsonExtraction jtm = new JsonExtraction();
		jtm.valuesToMatrixSecond("part-r-00000", null);
	}
	
	public void ordnerTest () {
		File f = new File("xlsVorher/Durchlauf3/");
		//XLS Dateien einlesen
		String[] liste = f.list(new FilenameFilter() {
            public boolean accept(File d, String name) {
               return name.toLowerCase().endsWith(".xls");
            }
        });
		if (liste == null)
			System.out.println("Liste null!");
		else
		{
			Date d = new Date();
			for (int i = 0; i<liste.length; i++)
			{
				System.out.println("Eingehende Datei:");
				System.out.println(liste[i]);
				try {
					DeExcelerator de = new DeExcelerator();
					de.extract("xlsVorher/Durchlauf3/" + liste[i], null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			Date d2 = new Date();
			System.out.println("Date 2: " + d2);
			System.out.println(d2.getTime()-d.getTime());
			System.out.println("Date 1: " + d);
		}
	}
	
	/**
	 * <span class="de">Beispiel Excel Aufruf.</span>
	 * <span class="en"></span>
	 */
	public void excelTest () {
		DeExcelerator de = new DeExcelerator();
		String name = "Beispiel.xlsx";
		String dateiname = name;
		Configuration c = erzeugeKonfiguration();
		//c.onlyOneTable = true;
		//c.limitForNoContent = 2;
		try {
			List<OutputObject> outs = de.extract(dateiname, c);
			for (int j = 0; j < outs.size(); j++)
			{	
				if (outs.get(j) == null)
					continue;
				String inhalt = outs.get(j).toJson();
		    	String dateiName = name + j + ".txt";
		        FileOutputStream schreibeStrom;
				schreibeStrom = new FileOutputStream(dateiName);
			    for (int k=0; k < inhalt.length(); k++)
			    	schreibeStrom.write((byte)inhalt.charAt(k));
			    schreibeStrom.close();			    
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Fertig");		
	}
	
	public void excel2Test () {
		DeExcelerator de = new DeExcelerator();
		String name = "XlsxBeispiel5.xlsx";
		String dateiname = "Tests/Beispiel/XlsxBeispielDokumente/" + name;
		Configuration c = erzeugeKonfiguration();
		c.onlyOneTable = true;
		//c.limitForNoContent = 2;
		try {
			List<OutputObject> outs = de.extract(dateiname, c);
			for (int j = 0; j < outs.size(); j++)
			{	
				if (outs.get(j) == null)
					continue;
				String inhalt = outs.get(j).toJson();
		    	String dateiName = name + j + ".txt";
		        FileOutputStream schreibeStrom;
				schreibeStrom = new FileOutputStream(dateiName);
			    for (int k=0; k < inhalt.length(); k++)
			    	schreibeStrom.write((byte)inhalt.charAt(k));
			    schreibeStrom.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Fertig");		
	}
	
	public void createUrlDokument () {
		try {
			DeExcelerator de = new DeExcelerator();
			Configuration c = new Configuration();
			c.useHtmlOutput = true;	
			de.extractUrl("http://en.wikipedia.org/wiki/List_of_cities_in_Germany_by_population", c);
			File f = new File("frame2.html");
			f.renameTo(new File("Frames/frame1.html"));
			de.extractUrl("http://de.wikipedia.org/wiki/Deutschland", c);
			f = new File("frame2.html");
			f.renameTo(new File("Frames/frame2.html"));
			de.extractUrl("http://vm206.leopoldina.rsm-development.de/de/ueber-uns/akademiegeschichte/tabelle-praesidenten-leopoldina/", c);
			f = new File("frame2.html");
			f.renameTo(new File("Frames/frame3.html"));
			de.extractUrl("http://www.beko-bbl.de/dates-und-results/tabellen.html", c);
			f = new File("frame2.html");
			f.renameTo(new File("Frames/frame4.html"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Fertig!");
	}
	
	public void createCodeDokument () {
		try {
			DeExcelerator de = new DeExcelerator();
			Configuration c = new Configuration();
			c.useHtmlOutput = true;	
			for (int i = 1; i < 11; i++)
			{
				InputstreamToString is = new InputstreamToString();
				FileInputStream fis = new FileInputStream("Tests/Beispiel/CodeBeispiele/HtmlCode" + i + ".txt");
				String code = is.fromInputStreamOne(fis);
				try {
					List<OutputObject> outs = de.extractHtml(code, c);
					for (int j = 0; j < outs.size(); j++)
					{	
						if (outs.get(j) == null)
							continue;
						String inhalt = outs.get(j).toJson();
				    	String dateiName = "HTML" + i + "Code" + j + ".txt";
				        FileOutputStream schreibeStrom;
						schreibeStrom = new FileOutputStream(dateiName);
					    for (int k=0; k < inhalt.length(); k++)
					    	schreibeStrom.write((byte)inhalt.charAt(k));
					    schreibeStrom.close();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				File f = new File("frame2.html");
				f.renameTo(new File("Frames/frame" + i + ".html"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Fertig!");
	}
	
	public void createXlsxDokument () {
		File f = new File("Tests/Beispiel/XlsxBeispielDokumente/");
		//XLS Dateien einlesen
		String[] liste = f.list(new FilenameFilter() {
            public boolean accept(File d, String name) {
               return name.toLowerCase().endsWith(".xlsx");
            }
        });
		if (liste != null)
		{
			for (int i = 0; i<liste.length; i++)
			{
				System.out.println("Eingehende Datei:");
				System.out.println(liste[i]);
				try {
					DeExcelerator de = new DeExcelerator();
					List<OutputObject> outs = de.extract("Tests/Beispiel/XlsxBeispielDokumente/" + liste[i], null);
					for (int j = 0; j < outs.size(); j++)
					{	
						if (outs.get(j) == null)
							continue;
						String inhalt = outs.get(j).toJson();
				    	String dateiName = liste[i] + j + ".txt";
				        FileOutputStream schreibeStrom;
						schreibeStrom = new FileOutputStream(dateiName);
					    for (int k=0; k < inhalt.length(); k++)
					    	schreibeStrom.write((byte)inhalt.charAt(k));
					    schreibeStrom.close();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		System.out.println("Fertig!");
	}
	
	public void createXlsDokument () {
		File f = new File("Tests/Beispiel/XlsBeispielDokumente/");
		//XLS Dateien einlesen
		String[] liste = f.list(new FilenameFilter() {
            public boolean accept(File d, String name) {
               return name.toLowerCase().endsWith(".xls");
            }
        });
		if (liste != null)
		{
			for (int i = 0; i<liste.length; i++)
			{
				System.out.println("Eingehende Datei:");
				System.out.println(liste[i]);
				try {
					DeExcelerator de = new DeExcelerator();
					List<OutputObject> outs = de.extract("Tests/Beispiel/XlsBeispielDokumente/" + liste[i], null);
					for (int j = 0; j < outs.size(); j++)
					{	
						if (outs.get(j) == null)
							continue;
						String inhalt = outs.get(j).toJson();
				    	String dateiName = liste[i] + j + ".txt";
				        FileOutputStream schreibeStrom;
						schreibeStrom = new FileOutputStream(dateiName);
					    for (int k=0; k < inhalt.length(); k++)
					    	schreibeStrom.write((byte)inhalt.charAt(k));
					    schreibeStrom.close();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		System.out.println("Fertig!");
	}
	
	public void createCsvDokument () {
		File f = new File("Tests/Beispiel/CsvBeispielDokumente/");
		//XLS Dateien einlesen
		String[] liste = f.list(new FilenameFilter() {
            public boolean accept(File d, String name) {
               return name.toLowerCase().endsWith(".csv");
            }
        });
		Configuration c = new Configuration();
		c.onlyOneTable = true;
		if (liste != null)
		{
			for (int i = 0; i<liste.length; i++)
			{
				System.out.println("Eingehende Datei:");
				System.out.println(liste[i]);
				try {
					DeExcelerator de = new DeExcelerator();
					List<OutputObject> outs = de.extract("Tests/Beispiel/CsvBeispielDokumente/" + liste[i], c);
					for (int j = 0; j < outs.size(); j++)
					{	
						if (outs.get(j) == null)
							continue;
						String inhalt = outs.get(j).toJson();
				    	String dateiName = liste[i] + j + ".txt";
				        FileOutputStream schreibeStrom;
						schreibeStrom = new FileOutputStream(dateiName);
					    for (int k=0; k < inhalt.length(); k++)
					    	schreibeStrom.write((byte)inhalt.charAt(k));
					    schreibeStrom.close();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		System.out.println("Fertig!");
	}
	
	
	/**
	 * <span class="de">Beispiel HTML Code Test.</span>
	 * <span class="en"></span>
	 */
	public void htmlCodeTest () {
		String table = "<table border='0' cellspacing='0' cellpadding='0' width='650' align='center'> <tbody><td><img src='http://www.geocities.com/clipart/pbi/c.gif' height='1' width='17' /></td> <td><img src='http://www.geocities.com/clipart/pbi/c.gif' height='1' width='3' /></td> <td><img src='http://www.geocities.com/clipart/pbi/c.gif' height='1' width='23' /></td> <td><img src='http://www.geocities.com/clipart/pbi/c.gif' height='1' width='1' /></td> <td><img src='http://www.geocities.com/clipart/pbi/c.gif' height='1' width='1' /></td> <td><img src='http://www.geocities.com/clipart/pbi/c.gif' height='1' width='14' /></td> <td><img src='http://www.geocities.com/clipart/pbi/c.gif' height='1' width='23' /></td> <td><img src='http://www.geocities.com/clipart/pbi/c.gif' height='1' width='27' /></td> <td><img src='http://www.geocities.com/clipart/pbi/c.gif' height='1' width='5' /></td> <td><img src='http://www.geocities.com/clipart/pbi/c.gif' height='1' width='10' /></td> <td><img src='http://www.geocities.com/clipart/pbi/c.gif' height='1' width='26' /></td> <td><img src='http://www.geocities.com/clipart/pbi/c.gif' height='1' width='30' /></td> <td><img src='http://www.geocities.com/clipart/pbi/c.gif' height='1' width='2' /></td> <td><img src='http://www.geocities.com/clipart/pbi/c.gif' height='1' width='36' /></td> <td><img src='http://www.geocities.com/clipart/pbi/c.gif' height='1' width='5' /></td> <td><img src='http://www.geocities.com/clipart/pbi/c.gif' height='1' width='7' /></td> <td><img src='http://www.geocities.com/clipart/pbi/c.gif' height='1' width='25' /></td> <td><img src='http://www.geocities.com/clipart/pbi/c.gif' height='1' width='12' /></td> <td><img src='http://www.geocities.com/clipart/pbi/c.gif' height='1' width='33' /></td> <td><img src='http://www.geocities.com/clipart/pbi/c.gif' height='1' width='3' /></td> </tr> <td colspan='10' rowspan='4' width='144' align='center' valign='middle'><img height='125' width='144' src='http://www.cat-house.org/catskull.jpg' border='0' /></td> <td colspan='5' rowspan='1' width='152' align='center' valign='middle'><img height='121' width='152' src='http://www.cat-house.org/folklife_flyer.jpg' border='0' /></td> <td colspan='8' rowspan='3' width='146' align='center' valign='middle'><img height='123' width='146' src='http://www.cat-house.org/catskull.jpg' border='0' /></td> <td colspan='11' rowspan='2' width='150' align='center' valign='middle'><img height='122' width='150' src='http://www.cat-house.org/catskull.jpg' border='0' /></td> </tr> <td colspan='9' height='1'></td> <td colspan='1' height='1'></td> <tr valign='top'> <td colspan='13' height='1'></td> <tr valign='top'> </tr> <td colspan='40' height='5'></td> <tr valign='top'> <td colspan='37' rowspan='1' width='630'><font color='#FFFFCC' size='+2'><span style='font-size:24'> </span></font><a href='http://www.cat-house.org/index.html'><font color='#FFFFCC' size='+2'><span style='font-size:24'>home</span></font></a><font color='#FFFFCC' size='+2'><span style='font-size:24'> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span></font><font color='#FFFFCC' size='+1'><span style='font-size:18'>cathouse&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; cathouse&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; cathouse</span></font><font color='#FFFFCC' size='+2'><span style='font-size:24'> &nbsp; <br />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span></font><a href='http://www.cat-house.org/CATHOUSELYRICS.html'><font color='#FFFFCC' size='+2'><span style='font-size:24'>lyrics </span></font></a><font color='#FFFFCC' size='+2'><span style='font-size:24'> &nbsp;&nbsp;&nbsp;&nbsp; </span></font><a href='http://www.cat-house.org/CATHOUSEROSTER.html'><font color='#FFFFCC' size='+2'><span style='font-size:24'>roster</span></font></a><font color='#FFFFCC' size='+2'><span style='font-size:24'> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span></font><a href='http://www.cat-house.org/CATHOUSEPHOTOS.html'><font color='#FFFFCC' size='+2'><span style='font-size:24'>photos </span></font></a><font color='#FFFFCC' size='+2'><span style='font-size:24'> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span></font><a href='http://www.cat-house.org/LINKS.html'><font color='#FFFFCC' size='+2'><span style='font-size:24'>links</span></font></a><font color='#FFFFCC' size='+2'><span style='font-size:24'> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span></font><a href='http://www.cat-house.org/CONTACT.html'><font color='#FFFFCC' size='+2'><span style='font-size:24'>contact</span></font></a></td> </tr> <td colspan='40' height='39'></td> <tr valign='top'> <td colspan='2' rowspan='31' width='66'><u><font color='#CCCC00' size='+2'><span style='font-size:24'>c</span></font></u><u><font color='#FFFFFF' size='+2'><span style='font-size:24'><br /></span></font></u><u><font color='#CCCC00' size='+2'><span style='font-size:24'>a</span></font></u><u><font color='#FFFFFF' size='+2'><span style='font-size:24'> &nbsp;&nbsp; M<br /></span></font></u><u><font color='#CCCC00' size='+2'><span style='font-size:24'>t</span></font></u><u><font color='#FFFFFF' size='+2'><span style='font-size:24'><br /></span></font></u><u><font color='#CCCC00' size='+2'><span style='font-size:24'>h</span></font></u><u><font color='#FFFFFF' size='+2'><span style='font-size:24'> &nbsp;&nbsp; U<br /></span></font></u><u><font color='#CCCC00' size='+2'><span style='font-size:24'>o</span></font></u><u><font color='#FFFFFF' size='+2'><span style='font-size:24'><br /></span></font></u><u><font color='#CCCC00' size='+2'><span style='font-size:24'>u</span></font></u><u><font color='#FFFFFF' size='+2'><span style='font-size:24'> &nbsp;&nbsp;&nbsp; S<br /></span></font></u><u><font color='#CCCC00' size='+2'><span style='font-size:24'>s</span></font></u><u><font color='#FFFFFF' size='+2'><span style='font-size:24'><br /></span></font></u><u><font color='#CCCC00' size='+2'><span style='font-size:24'>e</span></font></u><u><font color='#FFFFFF' size='+2'><span style='font-size:24'> &nbsp;&nbsp;&nbsp; I<br /><br />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; C</span></font></u></td> <td colspan='8' rowspan='3' width='91'><a href='http://www.cat-house.org/MUDSLIDE01011997.mp3'><u><font color='#FFFFCC' size='+2'><span style='font-size:24'>mudslide</span></font></u></a></td> </tr> <td colspan='2' height='31'></td> <td colspan='7' rowspan='1' width='129'><a href='http://www.cat-house.org/grandstreet.mp3'><u><font color='#FFFFCC' size='+2'><span style='font-size:24'>Grand Street</span></font></u></a></td> <td colspan='9' height='31'></td> <tr valign='top'> <td colspan='19' height='3'></td> </tr> <td colspan='2' height='4'></td> </tr> <td colspan='2' height='2'></td> <td colspan='15' rowspan='2' width='284'><a href='http://www.cat-house.org/revelationmp3.mp3'><font color='#FFFFCC' size='+2'><span style='font-size:24'>Revelation for a Revolution </span></font></a></td> </tr> <td colspan='2' height='30'></td> <td colspan='1' height='30'></td> <td colspan='8' height='30'></td> <tr valign='top'> <td colspan='19' height='1'></td> </tr> <td colspan='2' height='7'></td> </tr> <td colspan='2' height='31'></td> <td colspan='6' rowspan='1' width='150'><a href='http://www.cat-house.org/pneumo.mp3'><u><font color='#FFFFCC' size='+2'><span style='font-size:24'>Pneumothorax </span></font></u></a></td> <td colspan='11' rowspan='1' width='134'><a href='http://www.cat-house.org/Sandarry.mp3'><u><font color='#FFFFCC' size='+2'><span style='font-size:24'>SANDARRY</span></font></u></a></td> </tr> <td colspan='2' height='5'></td> </tr> <td colspan='2' height='3'></td> <td colspan='9' rowspan='2' width='128'><a href='http://www.cat-house.org/UNSANE1997.mp3'><u><font color='#FFFFCC' size='+2'><span style='font-size:24'>unsane 1997</span></font></u></a></td> </tr> <td colspan='2' height='29'></td> <td colspan='14' rowspan='2' width='240'><a href='http://www.cat-house.org/SomeVisions.mp3'><u><font color='#FFFFCC' size='+2'><span style='font-size:24'>some kinds of visions</span></font></u></a></td> <td colspan='7' height='29'></td> <tr valign='top'> <td colspan='2' height='3'></td> </tr> <td colspan='2' height='2'></td> </tr> <td colspan='2' height='5'></td> <td colspan='6' rowspan='2' width='83'><a href='http://www.cat-house.org/MONA.mp3'><b><font color='#FFFFCC' size='+2'><span style='font-size:24'>MONA</span></font></b></a></td> </tr> <td colspan='2' height='27'></td> <td colspan='14' rowspan='2' width='240'><a href='http://www.cat-house.org/BeautifulBeast.mp3'><u><font color='#FFFFCC' size='+2'><span style='font-size:24'>BEAUTIFUL BEAST</span></font></u></a></td> <td colspan='10' height='27'></td> <tr valign='top'> <td colspan='2' height='5'></td> </tr> <td colspan='2' height='7'></td> <td colspan='4' rowspan='2' width='65'><a href='http://www.cat-house.org/IPECAC3302000.mp3'><u><font color='#FFFFCC' size='+2'><span style='font-size:24'>ipecac</span></font></u></a></td> </tr> <td colspan='2' height='24'></td> <td colspan='8' rowspan='3' width='183'><a href='http://www.cat-house.org/President.mp3'><u><font color='#FFFFCC' size='+2'><span style='font-size:24'>PRESIDENT instr</span></font></u></a></td> <td colspan='11' height='24'></td> <tr valign='top'> <td colspan='3' height='6'></td> </tr> <td colspan='2' height='1'></td> <td colspan='10' height='1'></td> <td colspan='4' height='1'></td> <tr valign='top'> <td colspan='21' height='9'></td> </tr> <td colspan='2' height='24'></td> <td colspan='9' rowspan='3' width='191'><a href='http://www.cat-house.org/POUREURIK1299.mp3'><u><font color='#FFFFCC' size='+2'><span style='font-size:24'>pull off your DICK</span></font></u></a></td> <td colspan='4' height='24'></td> <tr valign='top'> <td colspan='4' height='5'></td> </tr> <td colspan='2' height='2'></td> <td colspan='9' height='2'></td> <td colspan='3' height='2'></td> <tr valign='top'> <td colspan='22' height='6'></td> </tr> <td colspan='2' height='23'></td> <td colspan='9' rowspan='2' width='196'><a href='http://www.cat-house.org/GameShowSlut.mp3'><u><font color='#FFFFCC' size='+2'><span style='font-size:24'>game SHOW SLUT</span></font></u></a></td> <td colspan='3' height='23'></td> <tr valign='top'> <td colspan='5' height='8'></td> </tr> <td colspan='2' height='1'></td> </tr> <td colspan='2' height='8'></td> <td colspan='9' rowspan='4' width='142'><a href='http://www.cat-house.org/WINELIGHT.mp3'><b><font color='#FFFFCC' size='+2'><span style='font-size:24'>WINELIGHT</span></font></b></a></td> </tr> <td colspan='2' height='15'></td> <td colspan='12' rowspan='2' width='263'><b><font color='#FFFFCC'><span style='font-size:14'>SHITHOUSE MOUTH </span></font></b><a href='http://www.cat-house.org/SHlive.mp3'><b><font color='#FFFFCC'><span style='font-size:14'>LIVE</span></font></b></a><b><font color='#FFFFCC'><span style='font-size:14'> 1993 w/ NICK</span></font></b></td> <td colspan='5' height='15'></td> <tr valign='top'> <td colspan='5' height='6'></td> </tr> <td colspan='26' height='6'></td> </tr> <td colspan='40' height='33'></td> <tr valign='top'> <td colspan='11' rowspan='1' width='244'><font color='#9999FF' size='-2'><span style='font-size:10'>** warning** recordings are not CATHOUSE's high-point</span></font></td> </tr> </table><tbody></tr><td>Year </td><td>! Chassis </td> <td>! 1 </td> <td>! 3 </td> <td>! 5 </td> <td>! 7 </td> <td>! 9 </td> <td>! 11 </td> <td>! 13 </td> <td>! 15 </td> <td>! WDC </td> <!--!ignored html tags--> </td> <tr> <td>!rowspan=2</td> <td !='' <a='' href='http://wn.com/Brian_Hart_Ltd_Hart' title='Brian Hart Ltd.&gt;Hart'>Brian Hart Ltd.&gt;Hart <a href='http://wn.com/Straight_4_td_td_style_background_efcfff_td_td_style_background_dfffdf_td_td_style_background_dfffdf_td_td_style_background_ffcfcf_td_td_td_td_td_td_td_td_td_td_td_td_td_td_td_td_td_td_td_td_td_td_td_td_td_td_rowspan_2_9th_td_td_rowspan_2_13_td_tr_tr_td_Toleman' title='Straight-4&lt;/td&gt;<td> style=&quot;background:#dfffdf;&quot;</td> <td> style=&quot;background:#ffcfcf;&quot;</td> <td> </td> <td> </td> <td> </td> <td> </td> <td> </td> <td> </td> <td !rowspan='2'> 13 </td> <tr> <td> style=&quot;background:#efcfff;&quot;</td> <td> style=&quot;background:#dfffdf;&quot;</td> <td> </td> <td> </td> <td> </td> <td> </td> <td> </td> <td> </td> <td !rowspan='2'> 9th </td> </tr><td !='' [[toleman<=''> <a href='http://wn.com/Toleman_TG184_TG184' title='Toleman TG184&gt;TG184'>Toleman TG184&gt;TG184</a> </td> &lt;td &gt;&lt;td &gt;&lt;td &gt;&lt;td &gt;<='' d=''> </a></td><td> style=&quot;background:#cfcfff;&quot;</td> <td> style=&quot;background:#efcfff;&quot;</td> <td> style=&quot;background:#efcfff;&quot;</td> <td> style=&quot;background:#efcfff;&quot;</td> <td [[1984='' italian='' grand='' prix=''>ITA&quot;&gt;Straight-4</td> <td> </td> <td> </td> <td> style=&quot;background:#dfdfdf;&quot;</td> <td> style=&quot;background:#efcfff;&quot;</td> <td> style=&quot;background:#ffdf9f;&quot;</td> <td> style=&quot;background:#efcfff;&quot;</td> <td></td>  <!--!ignored open tags--> </td> <td> style=&quot;background:#ffdf9f;&quot;</td> <tr> <td>! <a href='http://wn.com/John_Player_Special' title='John Player Special'>John Player Special</a> <a href='http://wn.com/Team_Lotus' title='Team Lotus'>Team Lotus</a> </td> </a></td> <='' d=''> </a></td><td> style=&quot;background:#cfcfff;&quot;</td> <td> style=&quot;background:#cfcfff;&quot;</td> <td> style=&quot;background:#efcfff;&quot;</td> <td> style=&quot;background:#efcfff;&quot;</td> <td> style=&quot;background:#ffdf9f;&quot;</td> <td> style=&quot;background:#ffffbf;&quot;</td> <td> style=&quot;background:#efcfff;&quot;</td> <td>! 4th </td> </tr><td></td> <td> style=&quot;background:#efcfff;&quot;</td> <td> style=&quot;background:#cfcfff;&quot;</td> <td> style=&quot;background:#cfcfff;&quot;</td> <td> style=&quot;background:#efcfff;&quot;</td> <td> style=&quot;background:#efcfff;&quot;</td> <td> style=&quot;background:#ffdf9f;&quot;</td> <td> style=&quot;background:#ffffbf;&quot;</td> <td> style=&quot;background:#efcfff;&quot;</td> <td>! 4th </td> </tr><td></td> <td>! <a href='http://wn.com/Team_Lotus_td_td_Renault_F1_Renault' title='Team Lotus&lt;/td&gt;<td !='' [[renault='' f1=''>Renault <a href='http://wn.com/V6_engine_td_td_style_background_dfdfdf_td_td_style_background_ffffbf_td_td_style_background_efcfff_td_td_style_background_ffdf9f_td_td_style_background_dfdfdf_td_td_style_background_dfffdf_td_td_style_background_ffffbf_td_td_style_background_efcfff_td_td_style_background_efcfff_td_td_style_background_dfdfdf_td_td_style_background_dfdfdf_td_td_style_background_efcfff_td_td_style_background_efcfff_td_td_style_background_dfffdf_td_td_style_background_ffdf9f_td_td_style_background_efcfff_td_td_4th_td_td_55_td_tr_tr_td_td_td_Camel_cigarette_td_td_Team_Lotus_td_td_Honda_Racing_F1_Honda' title='V6 engine&lt;/td&gt;<td> style=&quot;background:#ffffbf;&quot;</td> <td> style=&quot;background:#ffdf9f;&quot;</td> <td> style=&quot;background:#dfffdf;&quot;</td> <td> style=&quot;background:#efcfff;&quot;</td> <td> style=&quot;background:#dfdfdf;&quot;</td> <td> style=&quot;background:#efcfff;&quot;</td> <td> style=&quot;background:#dfffdf;&quot;</td> <td> style=&quot;background:#efcfff;&quot;</td> <td>! 55 </td> <tr> <td>! [[Camel (cigarette)</td> <td !='' [[honda='' racing='' f1=''>Honda&quot;&gt;V6 engine</td> <td> style=&quot;background:#ffffbf;&quot;</td> <td> style=&quot;background:#ffdf9f;&quot;</td> <td> style=&quot;background:#dfffdf;&quot;</td> <td> style=&quot;background:#efcfff;&quot;</td> <td> style=&quot;background:#dfdfdf;&quot;</td> <td> style=&quot;background:#efcfff;&quot;</td> <td> style=&quot;background:#dfffdf;&quot;</td> <td> style=&quot;background:#efcfff;&quot;</td> <td>! 55 </td> <tr> <td>! [[Camel (cigarette)</td> <td !='' [[honda='' racing='' f1=''>Honda <a href='http://wn.com/V6_engine_td_td_style_background_efcfff_td_td_style_background_dfdfdf_td_td_style_background_efcfff_td_td_style_background_ffffbf_td_td_style_background_ffffbf_td_td_style_background_dfffdf_td_td_style_background_ffdf9f_td_td_style_background_ffdf9f_td_td_style_background_dfdfdf_td_td_style_background_dfffdf_td_td_style_background_dfdfdf_td_td_style_background_cfcfff_td_td_style_background_dfffdf_td_td_style_background_efcfff_td_td_style_background_dfdfdf_td_td_style_background_000_color_white_td_td_td_td_td_tr_tr_td_td_td_Honda_Racing_F1_Honda' title='V6 engine&lt;/td&gt;<td> style=&quot;background:#dfdfdf;&quot;</td> <td> style=&quot;background:#ffffbf;&quot;</td> <td> style=&quot;background:#dfffdf;&quot;</td> <td> style=&quot;background:#ffdf9f;&quot;</td> <td> style=&quot;background:#dfffdf;&quot;</td> <td> style=&quot;background:#cfcfff;&quot;</td> <td> style=&quot;background:#efcfff;&quot;</td> <td> style=&quot;background:#000; color:white;&quot;</td> <td></td> <tr> <td !='' [[honda='' racing='' f1=''>Honda&quot;&gt;V6 engine</td> <td> style=&quot;background:#dfdfdf;&quot;</td> <td> style=&quot;background:#ffffbf;&quot;</td> <td> style=&quot;background:#dfffdf;&quot;</td> <td> style=&quot;background:#ffdf9f;&quot;</td> <td> style=&quot;background:#dfffdf;&quot;</td> <td> style=&quot;background:#cfcfff;&quot;</td> <td> style=&quot;background:#efcfff;&quot;</td> <td> style=&quot;background:#000; color:white;&quot;</td> <td></td> <tr> <td !='' [[honda='' racing='' f1=''>Honda <a href='http://wn.com/Marlboro_cigarette_td_td_McLaren_racing_td_td_Honda_Racing_F1_Honda' title='Marlboro (cigarette)&lt;/td&gt;</a></td> <td !='' [[honda='' racing='' f1=''>Honda <a href='http://wn.com/V6_engine_td_td_style_background_000_color_white_td_td_style_background_ffffbf_td_td_style_background_efcfff_td_td_style_background_dfdfdf_td_td_style_background_ffffbf_td_td_style_background_ffffbf_td_td_style_background_dfdfdf_td_td_style_background_ffffbf_td_td_style_background_ffffbf_td_td_style_background_ffffbf_td_td_style_background_ffffbf_td_td_style_background_cfcfff_td_td_style_background_dfffdf_td_td_style_background_dfffdf_td_td_style_background_ffffbf_td_td_style_background_dfdfdf_td_td_td_td_td_tr_tr_td_td_td_Honda_Racing_F1_Honda' title='V6 engine&lt;/td&gt;<td> style=&quot;background:#ffffbf;&quot;</td> <td> style=&quot;background:#dfdfdf;&quot;</td> <td> style=&quot;background:#ffffbf;&quot;</td> <td> style=&quot;background:#ffffbf;&quot;</td> <td> style=&quot;background:#ffffbf;&quot;</td> <td> style=&quot;background:#cfcfff;&quot;</td> <td> style=&quot;background:#dfffdf;&quot;</td> <td> style=&quot;background:#dfdfdf;&quot;</td> <td></td> <tr> <td !='' [[honda='' racing='' f1=''>Honda&quot;&gt;V6 engine</td> <td> style=&quot;background:#ffffbf;&quot;</td> <td> style=&quot;background:#dfdfdf;&quot;</td> <td> style=&quot;background:#ffffbf;&quot;</td> <td> style=&quot;background:#ffffbf;&quot;</td> <td> style=&quot;background:#ffffbf;&quot;</td> <td> style=&quot;background:#cfcfff;&quot;</td> <td> style=&quot;background:#dfffdf;&quot;</td> <td> style=&quot;background:#dfdfdf;&quot;</td> <td></td> <tr> <td !='' [[honda='' racing='' f1=''>Honda <a href='http://wn.com/Marlboro_cigarette_td_td_McLaren_racing_td_td_Honda_Racing_F1_td_td_style_background_cfcfff_td_td_style_background_ffffbf_td_td_style_background_ffffbf_td_td_style_background_ffffbf_td_td_style_background_efcfff_td_td_style_background_cfcfff_td_td_style_background_efcfff_td_td_style_background_efcfff_td_td_style_background_ffffbf_td_td_style_background_dfdfdf_td_td_style_background_ffffbf_td_td_style_background_efcfff_td_td_style_background_efcfff_td_td_style_background_ffffbf_td_td_style_background_000_color_fff_td_td_style_background_efcfff_td_td_td_td_td_tr_tr_td_td_td_Honda_Racing_F1_Honda' title='Marlboro (cigarette)&lt;/td&gt;&lt;td&gt;! [[Honda Racing F1&lt;/td&gt;<td> style=&quot;background:#ffffbf;&quot;</td> <td> style=&quot;background:#ffffbf;&quot;</td> <td> style=&quot;background:#cfcfff;&quot;</td> <td> style=&quot;background:#efcfff;&quot;</td> <td> style=&quot;background:#dfdfdf;&quot;</td> <td> style=&quot;background:#efcfff;&quot;</td> <td> style=&quot;background:#ffffbf;&quot;</td> <td> style=&quot;background:#efcfff;&quot;</td> <td></td> <tr> <td !='' [[honda='' racing='' f1=''>Honda&quot;&gt;Marlboro (cigarette)</td> <td>! [[Honda Racing F1</td> <td> style=&quot;background:#ffffbf;&quot;</td> <td> style=&quot;background:#ffffbf;&quot;</td> <td> style=&quot;background:#cfcfff;&quot;</td> <td> style=&quot;background:#efcfff;&quot;</td> <td> style=&quot;background:#dfdfdf;&quot;</td> <td> style=&quot;background:#efcfff;&quot;</td> <td> style=&quot;background:#ffffbf;&quot;</td> <td> style=&quot;background:#efcfff;&quot;</td> <td></td> <tr> <td !='' [[honda='' racing='' f1=''>Honda <a href='http://wn.com/Marlboro_cigarette_td_td_McLaren_racing_td_td_Honda_Racing_F1_td_td_style_background_ffffbf_td_td_style_background_ffdf9f_td_td_style_background_efcfff_td_td_style_background_ffffbf_td_td_style_background_ffffbf_td_td_style_background_cfcfff_td_td_style_background_ffdf9f_td_td_style_background_ffdf9f_td_td_style_background_ffffbf_td_td_style_background_dfdfdf_td_td_style_background_ffffbf_td_td_style_background_ffffbf_td_td_style_background_dfdfdf_td_td_style_background_efcfff_td_td_style_background_efcfff_td_td_style_background_efcfff_td_td_td_td_td_tr_tr_td_td_td_Honda_Racing_F1_Honda' title='Marlboro (cigarette)&lt;/td&gt;&lt;td&gt;! [[Honda Racing F1&lt;/td&gt;<td> style=&quot;background:#ffdf9f;&quot;</td> <td> style=&quot;background:#ffffbf;&quot;</td> <td> style=&quot;background:#cfcfff;&quot;</td> <td> style=&quot;background:#ffdf9f;&quot;</td> <td> style=&quot;background:#dfdfdf;&quot;</td> <td> style=&quot;background:#ffffbf;&quot;</td> <td> style=&quot;background:#efcfff;&quot;</td> <td> style=&quot;background:#efcfff;&quot;</td> <td></td> <tr> <td !='' [[honda='' racing='' f1=''>Honda&quot;&gt;Marlboro (cigarette)</td> <td>! [[Honda Racing F1</td> <td> style=&quot;background:#ffdf9f;&quot;</td> <td> style=&quot;background:#ffffbf;&quot;</td> <td> style=&quot;background:#cfcfff;&quot;</td> <td> style=&quot;background:#ffdf9f;&quot;</td> <td> style=&quot;background:#dfdfdf;&quot;</td> <td> style=&quot;background:#ffffbf;&quot;</td> <td> style=&quot;background:#efcfff;&quot;</td> <td> style=&quot;background:#efcfff;&quot;</td> <td></td> <tr> <td !='' [[honda='' racing='' f1=''>Honda [[Marlboro (cigarette)</td> <td>! [[Honda Racing F1</td> <td> style=&quot;background:#ffffbf;&quot;</td> <td> style=&quot;background:#ffffbf;&quot;</td> <td> style=&quot;background:#ffdf9f;&quot;</td> <td> style=&quot;background:#dfffdf;&quot;</td> <td> style=&quot;background:#ffffbf;&quot;</td> <td> style=&quot;background:#dfdfdf;&quot;</td> <td> style=&quot;background:#dfffdf;&quot;</td> <td> style=&quot;background:#ffffbf;&quot;</td> <td></td> <tr> <td> <a href='http://wn.com/Honda_Racing_F1' title='Honda Racing F1'>Honda</a> <a href='http://wn.com/Marlboro_cigarette_' title='Marlboro (cigarette)'>Marlboro</a> <a href='http://wn.com/McLaren_racing_' title='McLaren (racing)'>McLaren</a></td> &lt;td&gt;! [[Honda Racing F1&lt;/td&gt;<td> style=&quot;background:#efcfff;&quot;</td> <td> </td> <td> </td> <td> </td> <td> </td> <td> </td> <td> </td> <td> </td> <td !rowspan='2'> 50 </td> <tr> <td>! [[Honda Racing F1</td> <td> </td> <td> style=&quot;background:#cfcfff;&quot;</td> <td> style=&quot;background:#ffffbf;&quot;</td> <td> style=&quot;background:#efcfff;&quot;</td> <td> style=&quot;background:#dfdfdf;&quot;</td> <td> style=&quot;background:#dfffdf;&quot;</td> <td> style=&quot;background:#ffdf9f;&quot;</td> <td> style=&quot;background:#efcfff;&quot;</td> <tr> <td>! [[Marlboro (cigarette)</td> <td>! [[Ford Motor Company</td> <td> style=&quot;background:#ffffbf;&quot;</td> <td> style=&quot;background:#efcfff;&quot;</td> <td> style=&quot;background:#ffffbf;&quot;</td> <td> style=&quot;background:#dfffdf;&quot;</td> <td> style=&quot;background:#dfffdf;&quot;</td> <td> style=&quot;background:#dfffdf;&quot;</td> <td> style=&quot;background:#efcfff;&quot;</td> <td> style=&quot;background:#ffffbf;&quot;</td> <td></td> <tr> <td !='' [[rothmans='' international='' plc=''>Rothmans&quot;&gt;McLaren (racing)</td> <td> style=&quot;background:#ffdf9f;&quot;</td> <td> </td> <td> </td> <td> </td> <td> </td> <td> </td> <td> </td> <td> </td> <td !rowspan='2'> 4th </td> </tr><td>! [[McLaren (racing)</td> <td> </td> <td> style=&quot;background:#efcfff;&quot;</td> <td> style=&quot;background:#ffdf9f;&quot;</td> <td> style=&quot;background:#efcfff;&quot;</td> <td> style=&quot;background:#efcfff;&quot;</td> <td> style=&quot;background:#ffffbf;&quot;</td> <td> style=&quot;background:#ffffbf;&quot;</td> <td> style=&quot;background:#efcfff;&quot;</td> </tr><td></td> <td>! [[McLaren (racing)</td> <td> style=&quot;background:#dfdfdf;&quot;</td> <td> style=&quot;background:#ffffbf;&quot;</td> <td> style=&quot;background:#dfdfdf;&quot;</td> <td> style=&quot;background:#cfcfff;&quot;</td> <td> style=&quot;background:#dfffdf;&quot;</td> <td> style=&quot;background:#efcfff;&quot;</td> <td> style=&quot;background:#efcfff;&quot;</td> <td> style=&quot;background:#ffffbf;&quot;</td> <td></td> </tr><td></td> <td>! [[WilliamsF1</td> <td> style=&quot;background:#efcfff;&quot;</td> <td> style=&quot;background:#efcfff;&quot;</td> <td></td> <td></td> <td></td> <td></td> <td></td> <td></td> <td>! NC </td> </tr></table><thead> <th class='filmographyTbl_yearCol'> <a id='filmography_sort_year' href='javascript:void(0)' class='selected'>Year</a> <span class='arrow down'></span> </th> <th> <a id='filmography_sort_title' href='javascript:void(0)'>Title</a> </th> <th class='filmographyTbl_boxofficeCol'> <a id='filmography_sort_box_office' href='javascript:void(0)'>Box Office</a> </th> </thead> <tr> <td> <span class='tMeterIcon tiny'> </span> </td> <td><li> Police Officer #5 </li><td>--</td> <tr class='alt_row'> <td> <span class='tMeterIcon tiny'> <span title='Fresh' class='icon 		<td> <a href='/m/doc_hollywood/' class=''>Doc Hollywood</a></td> <ul></ul></td> </tr> </table><thead> <th class='filmographyTbl_yearCol'> <a id='filmography_sort_year' href='javascript:void(0)' class='selected'>Year</a> <span class='arrow down'></span> </th> <th> <a id='filmography_sort_title' href='javascript:void(0)'>Title</a> </th> <th class='filmographyTbl_boxofficeCol'> <a id='filmography_sort_box_office' href='javascript:void(0)'>Box Office</a> </th> </thead> <tr> <td> <span class='tMeterIcon tiny'> </span> </td> <td><li> <em class='subtle'>Screenwriter</em> </li><td>--</td> <tr class='alt_row'> <td> <span class='tMeterIcon tiny'> </span> </td> <td><li> <em class='subtle'>Screenwriter</em> </li><td>--</td> </tbody> ";
		DeExcelerator ef = new DeExcelerator();
		Configuration cp = new Configuration();
		cp.useOutputNameXls = true;
		cp.useHtmlOutput = true;
		ef.extractHtml(table, cp);
		System.out.println("Fertig");
	}
	
	/**
	 * <span class="de">Erzeugung eine Configurations Objekts für die Probeausgaben.</span>
	 * <span class="en"></span>
	 * @return <span class="de">Configurations Objekt</span>
	 * <span class="en"></span>
	 */
	private Configuration erzeugeKonfiguration () {
		Configuration cp = new Configuration();
		cp.useOutputNameXls = true;
		cp.useHtmlOutput = true;
		return cp;
	}
	
	/**
	 * <span class="de">Beispiel CSV Aufruf.</span>
	 * <span class="en"></span>
	 */
	public void csvTest () {
		try {
			CsvExtraction cte = new CsvExtraction();
			File f = new File("csvVorher/");
			File f2 = new File("all_alpha_10.csv");
			System.out.println(f2.getPath());
			cte.valuesToMatrix(f2.getPath(), null, ',');
			String[] liste = f.list(new FilenameFilter() {
	            public boolean accept(File d, String name) {
	               return name.toLowerCase().endsWith(".csv");
	            }
	        });
			if (liste == null)
				System.out.println("Liste null!");
			else
				for (int i = 0; i<liste.length; i++)
				{
					cte.valuesToMatrix(liste[i], null, ',');
				}
		} catch (Exception e) {
			//System.out.println("Einlese Fehler");
			e.printStackTrace();
		}
		
	}
	
	/**
	 * <span class="de">Beispiel URL Aufruf.</span>
	 * <span class="en"></span>
	 */
	public void urlTest () {
		DeExcelerator de = new DeExcelerator();
		Configuration c = new Configuration();
		c.useHtmlOutput = true;
		c.useOutputNameXls = true;
		//de.extractUrl("http://www.wdr2.de/aktionen/wdr2_fuer_eine_stadt/2012/finalrunde/tabelle102.html", c);
		de.extractUrl("http://en.wikipedia.org/wiki/Peter_and_Gordon", c);
		System.out.println("Fertig");
	}

}
