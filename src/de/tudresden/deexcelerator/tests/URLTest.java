package de.tudresden.deexcelerator.tests;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import junit.framework.TestCase;

import de.tudresden.deexcelerator.Configuration;
import de.tudresden.deexcelerator.DeExcelerator;
import de.tudresden.deexcelerator.data.InputstreamToString;


public class URLTest extends TestCase {

	private String frameOne;
	private String frameTwo;
	private String frameThree;
	private String frameFour;
	private String result;
	
	private DeExcelerator de;
	private Configuration c;
	
	public void setUp() {
		de = new DeExcelerator();
		c = new Configuration();
		c.useHtmlOutput = true;
		try {
			InputstreamToString is = new InputstreamToString();
			FileInputStream fis = new FileInputStream("Tests/Loesung/URL/frame1.html");
			frameOne = is.fromInputStreamOne(fis);
			fis = new FileInputStream("Tests/Loesung/URL/frame2.html");
			frameTwo = is.fromInputStreamOne(fis);
			fis = new FileInputStream("Tests/Loesung/URL/frame3.html");
			frameThree = is.fromInputStreamOne(fis);
			fis = new FileInputStream("Tests/Loesung/URL/frame4.html");
			frameFour = is.fromInputStreamOne(fis);
			is.fromInputStreamOne(fis);
		} catch (FileNotFoundException e) {
			System.out.println("File nicht erzeugt!");
			e.printStackTrace();
		}
    }	
	
	private void createResultString () {
		try {
			FileInputStream fis = new FileInputStream("frame2.html");
			InputstreamToString is = new InputstreamToString();
			result = is.fromInputStreamOne(fis);
		} catch (FileNotFoundException e) {
			System.out.println("File nicht erzeugt!");
			e.printStackTrace();
		}
	}
	
	public void testURLOne () {
		de.extractUrl("http://en.wikipedia.org/wiki/List_of_cities_in_Germany_by_population", c);
		createResultString();
		assertEquals(frameOne, result);
	} 
	
	public void testURLTwo () {
		de.extractUrl("http://de.wikipedia.org/wiki/Deutschland", c);
		createResultString();
		assertEquals(frameTwo, result);
	}
	
	public void testURLThree () {
		de.extractUrl("http://vm206.leopoldina.rsm-development.de/de/ueber-uns/akademiegeschichte/tabelle-praesidenten-leopoldina/", c);
		createResultString();
		assertEquals(frameThree, result);
	}
	
	public void testURLFour () {
		de.extractUrl("http://www.beko-bbl.de/dates-und-results/tabellen.html", c);
		createResultString();
		assertEquals(frameFour, result);
	}
}
