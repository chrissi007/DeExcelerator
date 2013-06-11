package de.tudresden.deexcelerator;

/**
 * <span class="de">Gibt an, welche Regeln genutzt werden und welche nicht.</span>
 * <span class="en">Say what rules the program use and what rules not.</span>
 * @author Christopher Werner
 *
 */
public class FunctionSelection {

	//Tabellen Analyse Funktionen Auswahl
	/** <span class="de">Regel, welche leere Zeilen und Spalten eliminiert.</span>
	 * <span class="en">Rule, that eliminate empty rows and cols.</span> */
	public boolean rowsColsEliminationActivate = true;
	//Erste und letzte Zeile vergleichen
	/** <span class="de">Regel, welche die erste und die letzte Zeile vergleicht.
	 * Kann in Webtabellen von nutzen sein bei der Wiederholung des Headers.</span>
	 * <span class="en">Rule, that compare the first with the last row.
	 * The rule can be important in web tables.</span> */
	public boolean rowComparisonActivate = true;
	//Spalten mit keinem Wert im Wertebereich
	/** <span class="de">Regel, sucht leere Spalten im Wertebereich, die vieleicht einen Header enthalten aber keine Werte.</span>
	 * <span class="en">Rule, search for empty cols in the value area, that can have an Header but no values.</span> */
	public boolean colsEliminationInDataActivate= false;
	//Weiterschreiben von Zellinhalten
	/** <span class="de">Regel, welche Zellen sucht die in darunter liegenden Zeilen fortgeschrieben werden müssen.</span>
	 * <span class="en">Rule, search for cells, which values must go over more than one row.</span> */
	public boolean valuesReassessActivate = true;
	//Layoutlinien finden
	/** <span class="de">Regel, sucht Layoutzeilen mit nur einem Wert zum Zusammenfügen mit anderen Zeilen.</span>
	 * <span class="en">Rule, search layout rows to concatenate with other rows.</span> */
	public boolean searchLayoutLinesActivate = true;
	//Relations Name für PostgreSQL aktivieren 
	/** <span class="de">Regel, bearbeitet den Relationsnamen für die PostgreSQL Datenbank.</span>
	 * <span class="en">Rule, modifie the relation name for the postgreSQL database.</span> */
	public boolean stringChangeRelationNameActivate = false;
	//Tabelle in Datenbank schreiben
	/** <span class="de">Regel, fügt die gefundene Relation einer PostgreSQL Datenbank hinzu.</span>
	 * <span class="en">Rule, that add the relation in one postgreSQL database.</span> */
	public boolean newTableInDbActivate = false;
	//Summerzeilen im Wertebereich finden
	/** <span class="de">Regel, sucht Summenzeilen in der Relation und entfernt diese.</span>
	 * <span class="en">Rule, search sumrows in the relation and delete them.</span> */
	public boolean sumRowsActivate = true;
	//Summenspalten im Wertebereich finden
	/** <span class="de">Regel, sucht Summenspalten in der Relation und entfernt diese.</span>
	 * <span class="en">Rule, search sumcols in the relation and delete them.</span> */
	public boolean sumColsActivate = true;
	//Headererkennungs Funktionen
	//durch Zahlenwert erkennen
	//Pflicht ----------------------------------------
	/** <span class="de">Headerregel, sucht erste Zahlzellen im Wertebereich.</span>
	 * <span class="en">Headerrule, search first number cells in the value area.</span> */
	public boolean headerRecognizeNumberActivate = true;
	//durch backgroundcolor erkennen
	/** <span class="de">Headerregel, gibt an ob die Methode benutzt werden soll, welche die Headergrenze
	 * mit der Hintergrundfarbe bestimmt.</span>
	 * <span class="en">Headerrule, say if the backgroundcolor should be used for header recognize.</span> */
	public boolean headerRecognizeBackgroundcolorActivate = false;
	//durch Datumswert erkennen
	/** <span class="de">Headerregel, suchte erste Datumszellen im Wertebereich.</span>
	 * <span class="en">Headerrule, search first date cells in the value area.</span> */
	public boolean headerRecognizeDateActivate = true;
	//durch Zahlsequence erkennen
	/** <span class="de">Headerregel, sucht eine Zahlensequence im Wertebereich (Nummerierung jeder Zeile).</span>
	 * <span class="en">Headerrule, search a number sequence in the value area (numbering in every row).</span> */
	public boolean headerRecognizeSequenceActivate = true;
	//durch Unterschiede im Wertebereich
	/** <span class="de">Headerregel, sucht eine Spalte in der nur kleine Wertveränderungen auftreten.</span>
	 * <span class="en">Headerrule, search a col with only little values changes.</span> */
	public boolean headerRecognizeDifferentsActivate = true;
	//durch gleich String längen im Wertebereich
	/** <span class="de">Headerregel, sucht eine Spalte in der keine Längenunterschiede der Werte auftreten.</span>
	 * <span class="en">Headerrule, search a col where the length of the value don't change.</span> */
	public boolean headerRecognizeStringLengthActivate = true;
	//durch Jahres Zahlen im Header
	/** <span class="de">Headerregel, sucht eine Zeile mit Jahreszahlen und definiert diese als Headerende.</span>
	 * <span class="en">Headerrule, search a col with year values and define it as the end of the header.</span> */
	public boolean headerRecognizeYearActivate = true;
}
