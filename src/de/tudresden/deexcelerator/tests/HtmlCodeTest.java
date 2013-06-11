package de.tudresden.deexcelerator.tests;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import junit.framework.TestCase;

import de.tudresden.deexcelerator.Configuration;
import de.tudresden.deexcelerator.DeExcelerator;
import de.tudresden.deexcelerator.data.InputstreamToString;

public class HtmlCodeTest extends TestCase {
	
	private String code1;
	private String code2;
	private String code3;
	private String code4;
	private String code5;
	private String code6;
	private String code7;
	private String code8;
	private String code9;
	private String code10;
	
	private String result1;
	private String result2;
	private String result3;
	private String result4;
	private String result5;
	private String result6;
	private String result7;
	private String result8;
	private String result9;
	private String result10;
	
	private String result;
	
	private DeExcelerator de;
	private Configuration c;
	
	public void setUp() {
		de = new DeExcelerator();
		c = new Configuration();
		c.useHtmlOutput = true;		
		try {
			InputstreamToString is = new InputstreamToString();
			//anfangs codes setzen
			FileInputStream fis = new FileInputStream("Tests/Beispiel/CodeBeispiele/HtmlCode1.txt");
			code1 = is.fromInputStreamOne(fis);
			fis = new FileInputStream("Tests/Beispiel/CodeBeispiele/HtmlCode2.txt");
			code2 = is.fromInputStreamOne(fis);
			fis = new FileInputStream("Tests/Beispiel/CodeBeispiele/HtmlCode3.txt");
			code3 = is.fromInputStreamOne(fis);
			fis = new FileInputStream("Tests/Beispiel/CodeBeispiele/HtmlCode4.txt");
			code4 = is.fromInputStreamOne(fis);
			fis = new FileInputStream("Tests/Beispiel/CodeBeispiele/HtmlCode5.txt");
			code5 = is.fromInputStreamOne(fis);
			fis = new FileInputStream("Tests/Beispiel/CodeBeispiele/HtmlCode6.txt");
			code6 = is.fromInputStreamOne(fis);
			fis = new FileInputStream("Tests/Beispiel/CodeBeispiele/HtmlCode7.txt");
			code7 = is.fromInputStreamOne(fis);
			fis = new FileInputStream("Tests/Beispiel/CodeBeispiele/HtmlCode8.txt");
			code8 = is.fromInputStreamOne(fis);
			fis = new FileInputStream("Tests/Beispiel/CodeBeispiele/HtmlCode9.txt");
			code9 = is.fromInputStreamOne(fis);
			fis = new FileInputStream("Tests/Beispiel/CodeBeispiele/HtmlCode10.txt");
			code10 = is.fromInputStreamOne(fis);
			//resultat String setzen
			fis = new FileInputStream("Tests/Loesung/HtmlCode/frame1.html");
			result1 = is.fromInputStreamOne(fis);
			fis = new FileInputStream("Tests/Loesung/HtmlCode/frame2.html");
			result2 = is.fromInputStreamOne(fis);
			fis = new FileInputStream("Tests/Loesung/HtmlCode/frame3.html");
			result3 = is.fromInputStreamOne(fis);
			fis = new FileInputStream("Tests/Loesung/HtmlCode/frame4.html");
			result4 = is.fromInputStreamOne(fis);
			fis = new FileInputStream("Tests/Loesung/HtmlCode/frame5.html");
			result5 = is.fromInputStreamOne(fis);
			fis = new FileInputStream("Tests/Loesung/HtmlCode/frame6.html");
			result6 = is.fromInputStreamOne(fis);
			fis = new FileInputStream("Tests/Loesung/HtmlCode/frame7.html");
			result7 = is.fromInputStreamOne(fis);
			fis = new FileInputStream("Tests/Loesung/HtmlCode/frame8.html");
			result8 = is.fromInputStreamOne(fis);
			fis = new FileInputStream("Tests/Loesung/HtmlCode/frame9.html");
			result9 = is.fromInputStreamOne(fis);
			fis = new FileInputStream("Tests/Loesung/HtmlCode/frame10.html");
			result10 = is.fromInputStreamOne(fis);
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
	
	public void testCode1 () {
		de.extractHtml(code1, c);
		createResultString();
		assertEquals(result1, result);
	} 
	
	public void testCode2 () {
		de.extractHtml(code2, c);
		createResultString();
		assertEquals(result2, result);
	}
	
	public void testCode3 () {
		de.extractHtml(code3, c);
		createResultString();
		assertEquals(result3, result);
	}
	
	public void testCode4 () {
		de.extractHtml(code4, c);
		createResultString();
		assertEquals(result4, result);
	}
	
	public void testCode5 () {
		de.extractHtml(code5, c);
		createResultString();
		assertEquals(result5, result);
	}
	
	public void testCode6 () {
		de.extractHtml(code6, c);
		createResultString();
		assertEquals(result6, result);
	}
	
	public void testCode7 () {
		de.extractHtml(code7, c);
		createResultString();
		assertEquals(result7, result);
	}
	
	public void testCode8 () {
		de.extractHtml(code8, c);
		createResultString();
		assertEquals(result8, result);
	}
	
	public void testCode9 () {
		de.extractHtml(code9, c);
		createResultString();
		assertEquals(result9, result);
	}
	
	public void testCode10 () {
		de.extractHtml(code10, c);
		createResultString();
		assertEquals(result10, result);
	}

}
