package de.tudresden.deexcelerator.rules;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.tudresden.deexcelerator.data.AnalyzeInformation;
import de.tudresden.deexcelerator.data.DocumentInformation;

/**
 * <span class="de">Sucht LeereBereiche in der Tabelle.</span>
 * <span class="en">Search empty areas in the table.</span>
 * @author Christopher Werner
 *
 */
public class EmptyAreasFinder {
	
	/**
	 * <span class="de">Actions Methode zum finden leerer Bereiche.</span>
	 * <span class="en">Find empty areas.</span>
	 * @param gd (<span class="de">Verwendete DocumentInformation</span>
     * <span class="en">the used document information</span>)
     * @param sd (<span class="de">AnalyseInformation</span>
     * <span class="en">analyze information</span>)
	 */
	public void action (DocumentInformation gd, AnalyzeInformation sd) {
		//-----------------------------------------------------------------
		//Konfigurationsparameter
		//bei wie viel keine Inhalts Zeilen soll er LeerBereich erkennen
		int grenzWertFuerKeinInhalt = gd.getCp().limitForNoContent;
		int grenzWertFuerInhaltsZellen = gd.getCp().limitForContentCells;
		if (sd.colEnd - sd.colStart <=10)
			grenzWertFuerInhaltsZellen = 2;
		//-----------------------------------------------------------------
		int anzahlOhneInhaltsspalten = 0;
		int ohneInhaltTest = 0;		
		List<Integer> leereBereiche = new ArrayList<Integer>();
		int lastLeerbereich = 0;
		boolean last = true;
		for (int y=sd.rowEnd-1; y>=sd.rowStart; y--)
		{
			int emptyZellen = 0;
			int notemptyZellen = 0;
			for (int x=sd.colStart; x<sd.colEnd; x++)
			{
				if (gd.getFc().emptyCell(x, y))
					emptyZellen++;
				else
					notemptyZellen++;
			}
			if (grenzWertFuerInhaltsZellen > notemptyZellen)
				anzahlOhneInhaltsspalten++;
			else
			{
				ohneInhaltTest = anzahlOhneInhaltsspalten;
				anzahlOhneInhaltsspalten = 0;
			}
			if (ohneInhaltTest > grenzWertFuerKeinInhalt)
			{
				leereBereiche.add(y+1);
				if (last) {
					lastLeerbereich = ohneInhaltTest;
					last = false;
				}
				ohneInhaltTest = 0;
			}
		}
		HeaderRecognizeNow hrn = new HeaderRecognizeNow();
		//Zeilen anfang hinzufügen da sonst nicht drin
		leereBereiche.add(sd.rowStart);
		Collections.sort(leereBereiche);
		boolean stop = false;
		//LeereBereichs Auswertung
		for (int i = 0; i < leereBereiche.size(); i++) {
			int x = leereBereiche.get(i);
			int end = sd.rowEnd;
			if (i + 1 < leereBereiche.size())
				end = leereBereiche.get(i+1);
			if (sd.rowEnd - end < lastLeerbereich + 2) {
				end = sd.rowEnd;
				stop = true;
			}
			//bestimme Header für leere Bereiche
			hrn.action(x, end, gd, sd);
			sd.emptyAreasStarts.add(x);
			//bestimmt anfang der Tabellen anhand einer leeren Zeile
			if (x!=sd.rowStart)
				emptyTableStart(x, gd, sd);
			else
				sd.relationStarts.add(sd.rowStart);
			if (stop)
				break;
		}
	}	
	
	/**
	 * <span class="de">Findet Tabellenanf&aumlnge aufgrund von leeren Zeilen.</span>
	 * <span class="en">Find table starts because of empty rows.</span>
	 * @param zeilenTest (<span class="de">Zeile die &uumlberpr&uumlft wird</span>
	 * <span class="en">row to proof</span>)
	 * @param gd (<span class="de">Verwendete DocumentInformation</span>
     * <span class="en">the used document information</span>)
     * @param sd (<span class="de">AnalyseInformation</span>
     * <span class="en">analyze information</span>)
	 */
	private void emptyTableStart (int zeilenTest, DocumentInformation gd, AnalyzeInformation sd) {
		int zaehler = 1;
		boolean checker2 = true;
		while (zaehler<10 && zeilenTest+zaehler<sd.rowEnd) {
			for (int x = sd.colStart; x<sd.colEnd; x++) {
				if (!gd.getFc().emptyCell(x, zeilenTest+zaehler))
					checker2 = false;
			}
			if (checker2) {
				//ist in einer LeerZeile
				int i = 0;
				while (zeilenTest+zaehler+i < sd.rowEnd && checker2) {
					for (int x = sd.colStart; x<sd.colEnd; x++) {
						if (!gd.getFc().emptyCell(x, zeilenTest+zaehler+i)) {
							checker2 = false;
							break;
						}
					}
					if (checker2)
						i++;
				}
				sd.relationStarts.add(zeilenTest+zaehler+i);
				return;
			}
			checker2 = true;
			zaehler++;			
		}
	}
	
	
	/**
	 * <span class="de">Tabellenanfang aufgrund von Schriftart und Schriftbreite erkennen.</span>
	 * <span class="en">Find table start, because of font and boldweight.</span>
	 * @param zeilenTest (<span class="de">Zeile die &uumlberpr&uumlft wird</span>
	 * <span class="en">row to proof</span>)
	 * @param gd (<span class="de">Verwendete DocumentInformation</span>
     * <span class="en">the used document information</span>)
     * @param sd (<span class="de">AnalyseInformation</span>
     * <span class="en">analyze information</span>)
	 */
	/*private void tableStart (int zeilenTest, DocumentInformation gd, AnalyzeInformation sd) {
		int font1Breite = 0, font2Breite = 0;
		String font1Name = "", font2Name = "";
		if (gd.getFc().emptyCell(sd.colStart, zeilenTest)) {
			font1Name = "Arial";
			font1Breite = 200;
		} else {		
			font1Name = gd.getFc().fontName(sd.colStart, zeilenTest);
			font1Breite = gd.getFc().fontBoldWeight(sd.colStart, zeilenTest);
		}
		if (gd.getFc().emptyCell(sd.colStart,zeilenTest+1)) {
			font2Name = "Arial";
			font2Breite = 200;
		} else {
			font2Name = gd.getFc().fontName(sd.colStart,zeilenTest+1);
			font2Breite = gd.getFc().fontBoldWeight(sd.colStart,zeilenTest+1);
		}
		boolean checker = true;
		int x = 2;
		int y = 9;
		while (checker && x<y) {
			//if (!leerZeilen.contains(zeilenTest+x)) {
				//System.out.println("Zeile: "+zeilenTest+" X: "+x+ " Y: " + y +  " Spaltenanfang: "+sd.spaltenAnfang);
				if (font2Breite>font1Breite || !font1Name.equals(font2Name))
				{
					checker = false;
					sd.relationStarts.add(zeilenTest+x-1);
				}
				font1Name = font2Name;
				font1Breite = font2Breite;
				if (gd.getFc().emptyCell(sd.colStart,zeilenTest+x)) {
					font2Name = "Arial";
					font2Breite = 200;
				} else {
					font2Name = gd.getFc().fontName(sd.colStart,zeilenTest+x);
					font2Breite = gd.getFc().fontBoldWeight(sd.colStart,zeilenTest+x);
				}
				
				y--;
			//}
			x++;
			//y++;
		}
	}*/
}
