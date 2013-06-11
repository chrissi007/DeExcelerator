package de.tudresden.deexcelerator.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * <span class="de">Erfasst das Dokument zum durchlaufen.</span>
 * <span class="en">Gets the file to work.</span>
 * @author Christopher Werner
 *
 */
public class FileWorker 
{
	
	private File file;
	
	public FileWorker()
	{
		
	}
	
	public FileWorker(String path)
	{
		file=new File(path);
	}
	
	public FileWorker(File file)
	{
		this.file=file;
	}
	
	public void SetNewFile(String path)
	{
		this.file=new File(path);
	}
	
	public void SetNewFileAlsFile(File file)
	{
		this.file=file;
	}
	
	public boolean addLineToFile(String s)
	{
		try {
            FileWriter writer;
			writer = new FileWriter(file,true);
            BufferedWriter bw = new BufferedWriter(writer);
    		bw.write(s);
    		bw.newLine();
    		bw.close();
    		return true;
        }
		catch (IOException e) 
		{
            return false;
        }
	}
	
	public boolean removeFirstLine()
	{
		try 
		{
			List<String> speicher=new LinkedList<String>();
    		FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
    		String zeile;
    		while ((zeile=br.readLine())!=null)
    			speicher.add(zeile);
    		br.close();
    		File zwischen=file;
	    	File probe=new File(zwischen.getAbsolutePath()+"t");
	    	zwischen.renameTo(probe);
	    	FileWriter fw=new FileWriter(file);
	    	BufferedWriter bw=new BufferedWriter(fw);
	    	boolean ausgabe=true;
	    	if (speicher.size()>0)
	    	{
	    		speicher.remove(0);
	    		ausgabe=true;
	    	}
	    	else
	    		ausgabe=false;
	    	for (int i=0;i<speicher.size();i++)
	    	{
	    		bw.write(speicher.get(i));
	    		bw.newLine();
	    	}
	    	bw.close();
	    	probe.delete();		
    		return ausgabe;
		} 
		catch (Exception e) 
		{
            return false;
        }
	}
	
	public boolean removeLineStartsWith(String start)
	{
		try 
		{
			List<String> speicher=new LinkedList<String>();
    		FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
    		String zeile;
    		while ((zeile=br.readLine())!=null)
    			speicher.add(zeile);
    		br.close();
    		File zwischen=file;
	    	File probe=new File(zwischen.getAbsolutePath()+"t");
	    	zwischen.renameTo(probe);
	    	FileWriter fw=new FileWriter(file);
	    	BufferedWriter bw=new BufferedWriter(fw);
	    	boolean ausgabe=true;
	    	if (speicher.size()>0)
	    	{
	    		Iterator<String> iter=speicher.iterator();
	    		while (iter.hasNext())
	    		{
	    			String s=iter.next();
	    			if (s.startsWith(start))
	    			{
	    				speicher.remove(s);
	    				iter=speicher.iterator();
	    				break;
	    			}
	    		}
	    		ausgabe=true;
	    	}
	    	else
	    		ausgabe=false;
	    	for (int i=0;i<speicher.size();i++)
	    	{
	    		bw.write(speicher.get(i));
	    		bw.newLine();
	    	}
	    	bw.close();
	    	probe.delete();		
    		return ausgabe;
		} 
		catch (Exception e) 
		{
            return false;
        }
	}
	
	public String removeLineStartsWithReturnString(String start)
	{
		try 
		{
			String rueckgabe=null;
			List<String> speicher=new LinkedList<String>();
    		FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
    		String zeile;
    		while ((zeile=br.readLine())!=null)
    			speicher.add(zeile);
    		br.close();
    		File zwischen=file;
	    	File probe=new File(zwischen.getAbsolutePath()+"t");
	    	zwischen.renameTo(probe);
	    	FileWriter fw=new FileWriter(file);
	    	BufferedWriter bw=new BufferedWriter(fw);
	    	//boolean ausgabe=true;
	    	if (speicher.size()>0)
	    	{
	    		Iterator<String> iter=speicher.iterator();
	    		while (iter.hasNext())
	    		{
	    			String s=iter.next();
	    			if (s.startsWith(start))
	    			{
	    				rueckgabe=s;
	    				speicher.remove(s);
	    				iter=speicher.iterator();
	    				break;
	    			}
	    		}
	    		//ausgabe=true;
	    	}
	    	//else
	    		//ausgabe=false;
	    	for (int i=0;i<speicher.size();i++)
	    	{
	    		bw.write(speicher.get(i));
	    		bw.newLine();
	    	}
	    	bw.close();
	    	probe.delete();		
    		return rueckgabe;
		} 
		catch (Exception e) 
		{
            return null;
        }
	}
	
	private FileReader fr = null;
	private BufferedReader br = null;
	public String readNextLine()
	{
		try
		{
			if (this.fr == null && this.br == null)
			{
				this.fr = new FileReader(file);
				this.br = new BufferedReader(fr);
			}
    		String zeile;
    		while ((zeile=br.readLine())!=null)
    			return zeile;
    		br.close();
    		this.fr = null;
    		this.br = null;
		}
		catch (Exception e)
		{
			
		}
		return null;
	}

	public List<String> readFile()
	{
		List<String> ausgabe=new LinkedList<String>();
		try
		{
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
    		String zeile;
    		while ((zeile=br.readLine())!=null)
    			ausgabe.add(zeile);
    		br.close();
		}
		catch (Exception e)
		{
			
		}
		return ausgabe;
	}
	
	public List<String> getAllFilesListInDirectoryAndDown(File file)
	{
		List<String> fileListe=new LinkedList<String>();
		File maindir = file;
		File[] files = maindir.listFiles();
		for (int i = 0; i < files.length; i++)
			if (!(files[i].isDirectory()))
			{
				fileListe.add(file.getAbsolutePath()+"/"+files[i].getName());
			}
			else
				fileListe.addAll(this.getAllFilesListInDirectoryAndDown(files[i]));
		return fileListe;
	}
	
	public List<String> getAllDirectoryListInDirectoryAndDown(File file)
	{
		List<String> fileListe=new LinkedList<String>();
		File maindir = file;
		File[] files = maindir.listFiles();
		for (int i = 0; i < files.length; i++)
			if (files[i].isDirectory())
			{
				fileListe.add(file.getAbsolutePath()+"/"+files[i].getName());
				fileListe.addAll(this.getAllDirectoryListInDirectoryAndDown(files[i]));
			}			
		return fileListe;
	}
	
	public long lengthBerechner(File file)
	{
		long x=0;
		if (file.isDirectory())
		{
			File[] files=file.listFiles();
			for (int i=0;i<files.length;i++)
				x=x+lengthBerechner(files[i]);
			return x;
		}
		else
		{
			return x+file.length();
		}
	}
	
	public boolean sameFileTest(File f1,File f2)
	{
		if (f1.length()==f2.length() && f1.lastModified()==f2.lastModified())
			return true;
		else
			return false;
	}

}
