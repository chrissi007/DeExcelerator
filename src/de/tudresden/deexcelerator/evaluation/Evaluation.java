package de.tudresden.deexcelerator.evaluation;

import de.tudresden.deexcelerator.data.DocumentInformation;

/**
 * <span class="de">Evaluations Informationen die beim Ausf&uumlhren der Pipeline produziert werden.</span>
 * <span class="en"></span>
 * @author Christopher Werner
 *
 */
public class Evaluation {

	//Excel Variable
	private int headerNumber;
	private int keinHeaderNumber;
	private int headerDate;
	private int keinHeaderDate;
	private int headerLineOne;
	private int keinHeader;
	private int numberInHeader;
	private int tabellenNameSheet;
	private int tabellenNameUeber;
	private int anzahlTabellen;
	private int sheetAnzahl;
	private int leereSheetAnzahl;
	private int totalZeilenAlle;
	private int totalZeilenObenUnten;
	private int totalSpalteAnzahl;
	private int totalZeilenAnzahl;
	private int anzahlSheetsMitFehler;
	private int attributGenerierung;
	private int anzahlIndexSpalten;
	
	private int anzahlSheetsMehrTabellen;
	private int anzahlLayoutzeilen;
	private int anzahlTabellenMehr;
	
	//CSV Variable
	private int headerNumberCsv;
	private int keinHeaderNumberCsv;
	private int headerDateCsv;
	private int keinHeaderDateCsv;
	private int headerLineOneCsv;
	private int keinHeaderCsv;
	private int numberInHeaderCsv;
	private int tabellenNameSheetCsv;
	private int tabellenNameUeberCsv;
	private int anzahlTabellenCsv;
	private int sheetAnzahlCsv;
	private int leereSheetAnzahlCsv;
	private int totalZeilenAlleCsv;
	private int totalZeilenObenUntenCsv;
	private int totalSpalteAnzahlCsv;
	private int totalZeilenAnzahlCsv;
	private int anzahlSheetsMitFehlerCsv;
	private int attributGenerierungCsv;
	private int anzahlIndexSpaltenCsv;
	
	private int anzahlSheetsMehrTabellenCsv;
	private int anzahlLayoutzeilenCsv;
	private int anzahlTabellenMehrCsv;
	private DocumentInformation gd; 
	
	public Evaluation (DocumentInformation gd) {
		this.gd = gd;		
		anzahlSheetsMehrTabellenCsv = 0;
		anzahlLayoutzeilenCsv = 0;
		anzahlTabellenMehrCsv = 0;
		anzahlSheetsMehrTabellen = 0;
		anzahlLayoutzeilen = 0;
		anzahlTabellenMehr = 0;
		//Excel
		anzahlIndexSpalten = 0;
		anzahlIndexSpaltenCsv = 0;
		leereSheetAnzahlCsv = 0;
		leereSheetAnzahl = 0;
		attributGenerierung = 0;
		anzahlSheetsMitFehler = 0;
		totalSpalteAnzahl = 0;
		totalZeilenAnzahl = 0;
		keinHeader = 0;
		headerNumber = 0;
		headerDate = 0;
		keinHeaderNumber = 0;
		keinHeaderDate = 0;
		headerLineOne = 0;
		numberInHeader = 0;
		tabellenNameSheet = 0;
		tabellenNameUeber = 0;
		anzahlTabellen = 0;
		sheetAnzahl = 0;
		totalZeilenAlle = 0;
		totalZeilenObenUnten = 0;
		
		//CSV
		attributGenerierungCsv = 0;
		anzahlSheetsMitFehlerCsv = 0;
		totalSpalteAnzahlCsv = 0;
		totalZeilenAnzahlCsv = 0;
		keinHeaderCsv = 0;
		headerNumberCsv = 0;
		headerDateCsv = 0;
		keinHeaderNumberCsv = 0;
		keinHeaderDateCsv = 0;
		headerLineOneCsv = 0;
		numberInHeaderCsv = 0;
		tabellenNameSheetCsv = 0;
		tabellenNameUeberCsv = 0;
		anzahlTabellenCsv = 0;
		sheetAnzahlCsv = 0;
		totalZeilenAlleCsv = 0;
		totalZeilenObenUntenCsv = 0;
	}
	
	public void addHeaderNumber() {
		if (gd.getFc().getIsExcel())
			this.headerNumber++;
		else
			this.headerNumberCsv++;
	}
	public int getHeaderNumber() {
		return headerNumber;
	}
	public void addHeaderDate() {
		if (gd.getFc().getIsExcel())
			this.headerDate++;
		else
			this.headerDateCsv++;
	}
	public int getHeaderDate() {
		return headerDate;
	}
	public void addHeaderLineOne() {
		if (gd.getFc().getIsExcel())
			this.headerLineOne++;
		else
			this.headerLineOneCsv++;
	}
	public int getHeaderLineOne() {
		return headerLineOne;
	}
	public void addNumberInHeader() {
		if (gd.getFc().getIsExcel())
			this.numberInHeader++;
		else
			this.numberInHeaderCsv++;
	}
	public int getNumberInHeader() {
		return numberInHeader;
	}
	public void addTabellenNameSheet() {
		if (gd.getFc().getIsExcel())
			this.tabellenNameSheet++;
		else
			this.tabellenNameSheetCsv++;
	}
	public int getTabellenNameSheet() {
		return tabellenNameSheet;
	}
	public void addTabellenNameUeber() {
		if (gd.getFc().getIsExcel())
			this.tabellenNameUeber++;
		else
			this.tabellenNameUeberCsv++;
	}
	public int getTabellenNameUeber() {
		return tabellenNameUeber;
	}
	public void addAnzahlTabellen() {
		if (gd.getFc().getIsExcel())
			this.anzahlTabellen++;
		else
			this.anzahlTabellenCsv++;
	}
	public int getAnzahlTabellen() {
		return anzahlTabellen;
	}
	public void addSheetAnzahl() {
		if (gd.getFc().getIsExcel())
			this.sheetAnzahl++;
		else
			this.sheetAnzahlCsv++;
	}
	public int getSheetAnzahl() {
		return sheetAnzahl;
	}
	public void addTotalZeilenAlle() {
		if (gd.getFc().getIsExcel())
			this.totalZeilenAlle++;
		else
			this.totalZeilenAlleCsv++;
	}
	public int getTotalZeilenAlle() {
		return totalZeilenAlle;
	}
	public void addTotalZeilenObenUnten() {
		if (gd.getFc().getIsExcel())
			this.totalZeilenObenUnten++;
		else
			this.totalZeilenObenUntenCsv++;
	}
	public int getTotalZeilenObenUnten() {
		return totalZeilenObenUnten;
	}

	public void addKeinHeaderDate() {
		if (gd.getFc().getIsExcel())
			this.keinHeaderDate++;
		else
			this.keinHeaderDateCsv++;
	}

	public int getKeinHeaderDate() {
		return keinHeaderDate;
	}

	public void addKeinHeaderNumber() {
		if (gd.getFc().getIsExcel())
			this.keinHeaderNumber++;
		else
			this.keinHeaderNumberCsv++;
	}

	public int getKeinHeaderNumber() {
		return keinHeaderNumber;
	}

	public void addKeinHeader() {
		if (gd.getFc().getIsExcel())
			this.keinHeader++;
		else
			this.keinHeaderCsv++;
	}

	public int getKeinHeader() {
		return keinHeader;
	}

	public void addTotalZeilenAnzahl(int anzahl) {
		if (gd.getFc().getIsExcel())
			this.totalZeilenAnzahl += anzahl;
		else
			this.totalZeilenAnzahlCsv += anzahl;
	}

	public int getTotalZeilenAnzahl() {
		return totalZeilenAnzahl;
	}

	public void addTotalSpalteAnzahl(int anzahl) {
		if (gd.getFc().getIsExcel())
			this.totalSpalteAnzahl += anzahl;
		else
			this.totalSpalteAnzahlCsv += anzahl;
	}

	public int getTotalSpalteAnzahl() {
		return totalSpalteAnzahl;
	}

	public void addAnzahlSheetsMitFehler() {
		if (gd.getFc().getIsExcel())
			this.anzahlSheetsMitFehler++;
		else
			this.anzahlSheetsMitFehlerCsv++;
	}

	public int getAnzahlSheetsMitFehler() {
		return anzahlSheetsMitFehler;
	}

	public void addAttributGenerierung() {
		if (gd.getFc().getIsExcel())
			this.attributGenerierung++;
		else
			this.attributGenerierungCsv++;
	}

	public int getAttributGenerierung() {
		return attributGenerierung;
	}

	//CSV get Funktionen

	public int getHeaderNumberCsv() {
		return headerNumberCsv;
	}

	public int getKeinHeaderNumberCsv() {
		return keinHeaderNumberCsv;
	}

	public int getHeaderDateCsv() {
		return headerDateCsv;
	}

	public int getKeinHeaderDateCsv() {
		return keinHeaderDateCsv;
	}

	public int getHeaderLineOneCsv() {
		return headerLineOneCsv;
	}

	public int getKeinHeaderCsv() {
		return keinHeaderCsv;
	}

	public int getNumberInHeaderCsv() {
		return numberInHeaderCsv;
	}

	public int getTabellenNameSheetCsv() {
		return tabellenNameSheetCsv;
	}

	public int getTabellenNameUeberCsv() {
		return tabellenNameUeberCsv;
	}

	public int getAnzahlTabellenCsv() {
		return anzahlTabellenCsv;
	}

	public int getSheetAnzahlCsv() {
		return sheetAnzahlCsv;
	}

	public int getTotalZeilenAlleCsv() {
		return totalZeilenAlleCsv;
	}

	public int getTotalZeilenObenUntenCsv() {
		return totalZeilenObenUntenCsv;
	}

	public int getAttributGenerierungCsv() {
		return attributGenerierungCsv;
	}

	public int getAnzahlSheetsMitFehlerCsv() {
		return anzahlSheetsMitFehlerCsv;
	}

	public int getTotalZeilenAnzahlCsv() {
		return totalZeilenAnzahlCsv;
	}

	public int getTotalSpalteAnzahlCsv() {
		return totalSpalteAnzahlCsv;
	}

	public int getLeereSheetAnzahlCsv() {
		return leereSheetAnzahlCsv;
	}

	public void addLeereSheetAnzahl() {
		if (gd.getFc().getIsExcel())
			this.leereSheetAnzahl++;
		else
			this.leereSheetAnzahlCsv++;
	}

	public int getLeereSheetAnzahl() {
		return leereSheetAnzahl;
	}

	public int getAnzahlIndexSpaltenCsv() {
		return anzahlIndexSpaltenCsv;
	}

	public void addAnzahlIndexSpalten() {
		if (gd.getFc().getIsExcel())
			this.anzahlIndexSpalten++;
		else
			this.anzahlIndexSpaltenCsv++;
	}

	public int getAnzahlIndexSpalten() {
		return anzahlIndexSpalten;
	}

	public void addAnzahlSheetsMehrTabellen() {
		if (gd.getFc().getIsExcel())
			this.anzahlSheetsMehrTabellen++;
		else
			this.anzahlSheetsMehrTabellenCsv++;
	}

	public int getAnzahlSheetsMehrTabellen() {
		return anzahlSheetsMehrTabellen;
	}

	public void addAnzahlLayoutzeilen() {
		if (gd.getFc().getIsExcel())
			this.anzahlLayoutzeilen++;
		else
			this.anzahlLayoutzeilenCsv++;
	}

	public int getAnzahlLayoutzeilen() {
		return anzahlLayoutzeilen;
	}

	public void addAnzahlTabellenMehr() {
		if (gd.getFc().getIsExcel())
			this.anzahlTabellenMehr++;
		else
			this.anzahlTabellenMehrCsv++;
	}

	public int getAnzahlTabellenMehr() {
		return anzahlTabellenMehr;
	}

	public int getAnzahlSheetsMehrTabellenCsv() {
		return anzahlSheetsMehrTabellenCsv;
	}
	
	public int getAnzahlLayoutzeilenCsv() {
		return anzahlLayoutzeilenCsv;
	}

	public int getAnzahlTabellenMehrCsv() {
		return anzahlTabellenMehrCsv;
	}
	
	public void addToAnotherEvaluation (Evaluation evn) {
		anzahlIndexSpalten += evn.getAnzahlIndexSpalten();
		headerNumber += evn.getHeaderNumber();
		keinHeaderNumber += evn.getKeinHeaderNumber();
		headerDate += evn.getHeaderDate();
		keinHeaderDate += evn.getKeinHeaderDate();
		headerLineOne += evn.getHeaderLineOne();
		keinHeader += evn.getKeinHeader();
		numberInHeader += evn.getNumberInHeader();
		tabellenNameSheet += evn.getTabellenNameSheet();
		tabellenNameUeber += evn.getTabellenNameUeber();
		anzahlTabellen += evn.getAnzahlTabellen();
		sheetAnzahl += evn.getSheetAnzahl();
		leereSheetAnzahl += evn.getLeereSheetAnzahl();
		totalZeilenAlle += evn.getTotalZeilenAlle();
		totalZeilenObenUnten += evn.getTotalZeilenObenUnten();
		totalSpalteAnzahl += evn.getTotalSpalteAnzahl();
		totalZeilenAnzahl += evn.getTotalZeilenAnzahl();
		anzahlSheetsMitFehler += evn.getAnzahlSheetsMitFehler();
		attributGenerierung += evn.getAttributGenerierung();
		anzahlIndexSpalten += evn.getAnzahlIndexSpalten();
		anzahlSheetsMehrTabellen += evn.getAnzahlSheetsMehrTabellen();
		anzahlLayoutzeilen += evn.getAnzahlLayoutzeilen();
		anzahlTabellenMehr += evn.getAnzahlTabellenMehr();
		headerNumberCsv += evn.getHeaderNumberCsv();
		keinHeaderNumberCsv += evn.getKeinHeaderNumberCsv();
		headerDateCsv += evn.getHeaderDateCsv();
		keinHeaderDateCsv += evn.getKeinHeaderDateCsv();
		headerLineOneCsv += evn.getHeaderLineOneCsv();
		keinHeaderCsv += evn.getKeinHeaderCsv();
		numberInHeaderCsv += evn.getNumberInHeaderCsv();
		tabellenNameSheetCsv += evn.getTabellenNameSheetCsv();
		tabellenNameUeberCsv += evn.getTabellenNameUeberCsv();
		anzahlTabellenCsv += evn.getAnzahlTabellenCsv();
		sheetAnzahlCsv += evn.getSheetAnzahlCsv();
		leereSheetAnzahlCsv += evn.getLeereSheetAnzahlCsv();
		totalZeilenAlleCsv += evn.getTotalZeilenAlleCsv();
		totalZeilenObenUntenCsv += evn.getTotalZeilenObenUntenCsv();
		totalSpalteAnzahlCsv += evn.getTotalSpalteAnzahlCsv();
		totalZeilenAnzahlCsv += evn.getTotalZeilenAnzahlCsv();
		anzahlSheetsMitFehlerCsv += evn.getAnzahlSheetsMitFehlerCsv();
		attributGenerierungCsv += evn.getAttributGenerierungCsv();
		anzahlIndexSpaltenCsv += evn.getAnzahlIndexSpaltenCsv();
		anzahlSheetsMehrTabellenCsv += evn.getAnzahlSheetsMehrTabellenCsv();
		anzahlLayoutzeilenCsv += evn.getAnzahlLayoutzeilenCsv();
		anzahlTabellenMehrCsv += evn.getAnzahlTabellenMehrCsv();
	}
	
}
