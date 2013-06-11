package de.tudresden.deexcelerator.stringarray;


import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;

import de.tudresden.deexcelerator.util.BadWords;
import de.tudresden.deexcelerator.util.RangeMatrix;

/**
 * <span class="de">F&uumlhrt alle Umwandlungen eines HTML Dokuments durch.</span>
 * <span class="en">Makes all changes in an HTML document.</span>
 * @author Christopher Werner
 *
 */
public class HTMLStringarray {

	/** <span class="de">Maximal vorkommende schlechte W&oumlrter.</span>
	 * <span class="en">Maximum number of bad words in on table.</span> */
	private static final int BADWORD_LIMIT = 3;
    /** <span class="de">Erstes ersetzen der Leeren nicht vorhandenen Zellen.</span>
     * <span class="en">First removing of the empty cells in the table.</span> */
    private static String emptyRepresentation = "@";

    /**
     * <span class="de">Macht eine Matrix aus dem HTML Code.</span>
     * <span class="en">Create an array of strings from the html code.</span>
     * @param pageURL (<span class="de">URL String für Metadaten</span>
     * <span class="en">url string for metadata</span>)
     * @param html (<span class="de">HTML Tags mit Inhalt</span>
     * <span class="en">html code als string</span>)
     * @return <span class="de">Liste von Stringarrays</span>
	 * <span class="en">List of Stringarrays</span>
     */
    public List<OutputStringarray> tablesFromHTML(String pageURL, String html) {
    	//änderte alle Strings die später nicht verarbeitet werden können
    	html = changeSigns(html);
        html = this.deleteEmptyRepresentation(html);
    	Document doc = Jsoup.parse(html);
    	//------------------------------------------------------------------------
    	//spezielle Sachen die aus HTML tabellen entfernt werden
    	doc.select("[style=display:none]").remove();
    	doc.select("[style=display:none;]").remove();
    	doc.select(".reference").remove();
    	//------------------------------------------------------------------------
        // iterate tables tags; find relations
        Elements tables = doc.getElementsByTag("table");
        List<OutputStringarray> outs = new ArrayList<OutputStringarray>();
        //System.out.println("Anzahl Tabellen: " + tables.size());
        for (Element table : tables) {
            Elements eles = table.parents();
            //remove tables where form bevor
            boolean formTest = false;
            for (Iterator<Element> iter = eles.iterator(); iter.hasNext();) {
            	Element e = iter.next();
            	if (e.tagName().equals("form"))
            		formTest = true;
            }
            if (formTest) {
            	//System.out.println("Fehler wegen FORM Tag vor der Tabelle!");
            	continue;
            }            	
            //remove tables where form is in
            if (table.select("form").size() > 0) {
            	//System.out.println("Fehler wegen FORM Tag in der Tabelle!");
                continue;
            }
            // remove table with sub-tables
            Elements subTables = table.getElementsByTag("table");
            subTables.remove(table);
            if (subTables.size() > 0) {
            	//System.out.println("Fehler wegen Sub/Unter Tabellen!");
                continue;
            }
            //frameOne(tables.toString());
            Elements trs = table.getElementsByTag("tr");
            // remove tables with less than 3 columns
            int maxtdCount = 0;
            int[] tdCounts = new int[trs.size()];
            Multiset<Integer> colCounts = HashMultiset.create();
            boolean headerSenkrecht = true;
            for (int tr_idx = 0; tr_idx < trs.size(); tr_idx++) {
                Elements tds = trs.get(tr_idx).select("td, th");
                Elements tdh = trs.get(tr_idx).select("th");
                if (tdh.size() == 0)
                	headerSenkrecht = false;
                int td_size = tds.size();
                tdCounts[tr_idx] = td_size;
                colCounts.add(td_size);
                if (td_size > maxtdCount)
                    maxtdCount = td_size;
            }
            String[][] matrix;
            if (headerSenkrecht)
            	matrix = new String[trs.size()][maxtdCount];
            else
            	matrix = new String[maxtdCount][trs.size()];
            int x = 0;
            int badWordCounter = 0;
            //---------Range einbauen----------------
            Elements colSpans = table.select("td[colspan]");
            Elements rowSpans = table.select("td[rowspan]");
            Elements thColSpans = table.select("th[colspan]");
            Elements thRowSpans = table.select("th[rowspan]");
            int dimension = rowSpans.size()+colSpans.size()+thColSpans.size()+thRowSpans.size();
            RangeMatrix[] mergeZellen = new RangeMatrix[dimension];
            int nummerRange=0;
            //---------Range einbauen----------------
            //Headeranfang durch Th Elemente pruefen
            int headerAnfang = 0;
            //Header liegt Senkrecht sprich er muss die Werte tauschen
            if (headerSenkrecht)
	            for (int y=0; y<trs.size();y++) {
	            	Elements cells = trs.get(y).select("td, th");
	            	for (int c = 0; c < cells.size(); c++) {
	            		while (x < matrix[0].length && matrix[y][x]!=null && !matrix[y][x].equals("")) {
	            			x++;
	            		}
	            		if (x >= matrix[0].length)
	            			continue;
	                    String cell = this.getContentFromElement(cells.get(c));
	                    badWordCounter += BadWords.count(cell);
	                    String rowspan = cells.get(c).attr("rowspan");
	                    String colspan = cells.get(c).attr("colspan");
	                    DecimalFormat format = new DecimalFormat();
	                    DecimalFormat format2 = new DecimalFormat();
	                    int rowspanInt = 1;
	                    int colspanInt = 1;
	        			try {
	        				rowspanInt = (int)format.parse(rowspan).doubleValue();
	        			} catch (ParseException e) {
	        				rowspanInt = 1;
	        			}
	        			try {
	        				colspanInt = (int)format2.parse(colspan).doubleValue();
	        			} catch (ParseException e) {
	        				colspanInt = 1;
	        			}
	        			if (rowspanInt != 1 || colspanInt != 1) {
	        				for (int k = 0; k<colspanInt; k++)
	        					for (int l = 0; l<rowspanInt; l++) {
	        						if (matrix.length > y+l && matrix[0].length > x+k)
	        							matrix[y+l][x+k] = emptyRepresentation; 
	        					}
	        				RangeMatrix r = new RangeMatrix(y,x,y+rowspanInt-1,x+colspanInt-1);
	        				mergeZellen[nummerRange] = r;
	        				nummerRange++;
	        			}
	        			if (cell == null)
	        				cell = "";
	        			matrix[y][x]= changeSigns(cell);
	        			x++;
	                }
	            	x=0;
	            }
            else
            	//Header liegt Waagerecht normales einfügen der Werte
            	for (int y=0; y<trs.size();y++) {
                	Elements cells = trs.get(y).select("td, th");
                	Elements cellhs = trs.get(y).select("th");
                	if (headerAnfang == y && cellhs.size()>0)
                		headerAnfang = y+1;
                	for (int c = 0; c < cells.size(); c++) {
                		while (x < matrix.length && matrix[x][y]!=null && !matrix[x][y].equals("")) {
                			x++;
                		}
                		if (x >= matrix.length)
	            			continue;
                        String cell = this.getContentFromElement(cells.get(c));
	                    String rowspan = cells.get(c).attr("rowspan");
                        String colspan = cells.get(c).attr("colspan");
                        DecimalFormat format = new DecimalFormat();
                        DecimalFormat format2 = new DecimalFormat();
                        int rowspanInt = 1;
                        int colspanInt = 1;
            			try {
            				rowspanInt = (int)format.parse(rowspan).doubleValue();
            			} catch (ParseException e) {
            				rowspanInt = 1;
            			}
            			try {
            				colspanInt = (int)format2.parse(colspan).doubleValue();
            			} catch (ParseException e) {
            				colspanInt = 1;
            			}
            			if (rowspanInt != 1 || colspanInt != 1) {
            				for (int k = 0; k<colspanInt; k++)
            					for (int l = 0; l<rowspanInt; l++) {
            						if (matrix.length > x+k && matrix[0].length > y+l) {
            							//int aus1 = x+k, aus2 = y+l;
            							matrix[x+k][y+l] = emptyRepresentation; 
            						}
            					}
            				RangeMatrix r = new RangeMatrix(x,y,x+colspanInt-1,y+rowspanInt-1);
            				mergeZellen[nummerRange] = r;
            				nummerRange++;
            			}
            			if (cell == null)
	        				cell = "";
            			matrix[x][y]= changeSigns(cell);
            			x++;
                    }
                	x=0;
                }
            if (nummerRange<mergeZellen.length) {
        		RangeMatrix[] mergeZellenModified = new RangeMatrix[nummerRange];
        		for (int zaehler = 0; zaehler<nummerRange;zaehler++)
        			mergeZellenModified[zaehler] = mergeZellen[zaehler];
        		mergeZellen = mergeZellenModified;
            }            
            if (badWordCounter > BADWORD_LIMIT) {
                continue;
            }
            matrix = this.changeEmptyStrings(matrix);    
            
            //Rueckgabe der Stringsarrays
            OutputStringarray os = new OutputStringarray();
            os.matrix = matrix;
            os.name = pageURL;
            os.rmListe = mergeZellen;    
            outs.add(os);
        }
        return outs;
    }
    
    /**
     * <span class="de">Zur&uumlck ersetzen der Leeren Zellen.
     * Erzeugt wahre Representation.</span>
     * <span class="en">Back reset of the empty cells from the emptyRepresentation.</span>
     * @param matrix (<span class="de">Tabelle als Matrix</span>
     * <span class="en">table es matrix</span>)
     * @return <span class="de">modifzierte Matrix der Tabelle</span>
     * <span class="en">modified matrix of the table</span>
     */
    private String[][] changeEmptyStrings (String[][] matrix) {
    	for (int x = 0; x<matrix.length;x++)
			for (int y=0; y<matrix[0].length;y++)
				if (matrix[x][y] != null && matrix[x][y].equals(emptyRepresentation))
					matrix[x][y] = "";
    	return matrix;
    }
    
    /**
     * <span class="de">Entfernt leere Zeichen im HTML Code.</span>
     * <span class="en">Delete empty signs in the html code.</span>
     * @param code (<span class="de">html code</span>
     * <span class="en">html code</span>)
     * @return <span class="de">modified html code</span>
     * <span class="en">modified html code</span>
     */
    private String deleteEmptyRepresentation (String code) {
    	code = code.replaceAll("&nbsp;", "");
    	return code;
    }
    
    private String changeSigns (String content) {
    	content = content.replaceAll("&sup2;", "2");
    	content = content.replaceAll("&sup3;", "3");
    	content = content.replaceAll("&uuml;", "ue");
    	content = content.replaceAll("&auml;", "ae");
    	content = content.replaceAll("&ouml;", "oe");
    	content = content.replaceAll("&Uuml;", "Ue");
    	content = content.replaceAll("&Ouml;", "Oe");
    	content = content.replaceAll("&Auml;", "Ae");
    	content = content.replaceAll("ü", "ue");
    	content = content.replaceAll("ä", "ae");
    	content = content.replaceAll("ö", "oe");
    	content = content.replaceAll("Ü", "Ue");
    	content = content.replaceAll("Ö", "Oe");
    	content = content.replaceAll("Ä", "Ae");
    	return content;
    }
    
    /**
     * <span class="de">Modifiziert den Zelleninhalt und entfernt unn&oumltige Zeichen und Tags.</span>
     * <span class="en">Modifie the cellvalue an delete unimportant tags and signs.</span>
     * @param e (<span class="de">td Zelle der Tabelle als Element</span>
     * <span class="en">td element in the table</span>)
     * @return <span class="de">modifizierter Inhalt als String</span>
     * <span class="en">modified cellvalue</span>
     */
    private String getContentFromElement (Element e) {
    	String content = e.text();
    	return content;
    }
}
