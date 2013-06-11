package de.tudresden.deexcelerator.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

/**
 * <span class="de">Iterator über die Zeilen eines Doukuments.</span>
 * <span class="en">Iterator over the rows in a doukument.</span>
 * @author Christopher Werner
 *
 * @param <T>
 */
public class FileIterator<T> implements Iterator<T>{

	private String dateiname;
	private FileReader fr = null;
	private BufferedReader br = null;
	private T nextValue = null;
	private T beforeValue = null;
	private int lineNumber;
	public FileIterator(String datei)
	{
		this.dateiname = datei;
		this.init();
	}
	
	public void init()
	{
		try
		{
			this.fr = new FileReader(this.dateiname);
			this.br = new BufferedReader(this.fr);    		
		}
		catch (Exception e)
		{
			this.fr = null;
			this.br = null;
		}
		this.lineNumber = 0;
		this.nextValue = null;
		this.beforeValue = null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean hasNext() {
		try
		{
			this.beforeValue = this.nextValue;
    		this.nextValue = (T)this.br.readLine();
		}
		catch (Exception e)
		{
			this.nextValue = null;
		}
		if (this.nextValue == null)
		{
			if (this.br != null)
			{
				try {
					this.br.close();
				} catch (IOException e) {
					//tue nix
				}
			}
			return false;
		}
		else
		{
			this.lineNumber++;
			return true;
		}
	}
	
	public T before() {
		return this.beforeValue;
	}

	@Override
	public T next() {
		return this.nextValue;
	}

	@Override
	public void remove() {
		try 
		{
			this.br.close();
    		FileReader fr = new FileReader(this.dateiname);
			BufferedReader br = new BufferedReader(fr);
			FileWriter fw=new FileWriter(this.dateiname + ".tmp");
	    	BufferedWriter bw=new BufferedWriter(fw);
    		String zeile;
    		while ((zeile=br.readLine())!=null)
    		{
    			if (!zeile.equals(this.nextValue.toString()))
    			{
    				bw.write(zeile);
    				bw.newLine();
    			}
    		}
    		br.close();    		
	    	bw.close();
	    	File f1 = new File(this.dateiname);
	    	File f2 = new File(this.dateiname + ".tmp");
	    	f1.renameTo(new File(this.dateiname + "alt"));
	    	f2.renameTo(new File(this.dateiname));
		}
		catch (Exception e) 
		{
        }
		this.init();
	}
	
	public void changeTo(String newLine)
	{
		try 
		{
			this.br.close();
    		FileReader fr = new FileReader(this.dateiname);
			BufferedReader br = new BufferedReader(fr);
			FileWriter fw=new FileWriter(this.dateiname + ".tmp");
	    	BufferedWriter bw=new BufferedWriter(fw);
    		String zeile;
    		while ((zeile=br.readLine())!=null)
    		{
    			if (zeile.equals(this.nextValue.toString()))
    			{
    				zeile = newLine;
    			}
    			bw.write(zeile);
				bw.newLine();
    		}
    		br.close();    		
	    	bw.close();
	    	File f1 = new File(this.dateiname);
	    	File f3 = new File(this.dateiname + "alt");
	    	f1.renameTo(f3);
	    	f3.delete();
	    	File f2 = new File(this.dateiname + ".tmp");	    	
	    	f2.renameTo(new File(this.dateiname));	    	
		}
		catch (Exception e) 
		{
			//System.out.println("Catch fehler change");
        }
		this.init();
	}
	
	public void removeListLines(List<String> liste)
	{
		try 
		{
			this.br.close();
    		FileReader fr = new FileReader(this.dateiname);
			BufferedReader br = new BufferedReader(fr);
			FileWriter fw=new FileWriter(this.dateiname + ".tmp");
	    	BufferedWriter bw=new BufferedWriter(fw);
    		String zeile;
    		while ((zeile=br.readLine())!=null)
    		{
    			if (!liste.contains(zeile))
    			{
    				bw.write(zeile);
    				bw.newLine();
    			}
    		}
    		br.close();    		
	    	bw.close();
	    	File f1 = new File(this.dateiname);
	    	File f2 = new File(this.dateiname + ".tmp");
	    	f1.renameTo(new File(this.dateiname + "alt"));
	    	f2.renameTo(new File(this.dateiname));
		}
		catch (Exception e) 
		{
			//tue nix
        }
		this.init();
	}
	
	/**
	 * Merge the current line with the line before.
	 */
	public void merge() {
		
	}
	
}
