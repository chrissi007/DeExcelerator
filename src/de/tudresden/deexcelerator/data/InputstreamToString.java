package de.tudresden.deexcelerator.data;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;

/**
 * <span class="de">Mehrere Funktionen die einen Inputstream in einen String umwandeln sollen.</span>
 * <span class="en">Some functions, which create a string from an inputstream.</span>
 * @author Christopher Werner
 *
 */
public class InputstreamToString {

	/**
	 * <span class="de">Extrahiert einen String aus einem InputStream.</span>
	 * <span class="en">Extract a string from an InputStream.</span>
	 * @param stream (<span class="de">Inputstream welcher einen String enth&aumllt</span>
	 * <span class="en">InputStram with string</span>)
	 * @return <span class="de">eingebetteter String</span>
	 * <span class="en">string value</span>
	 */
	public String fromInputStreamOne (InputStream stream)
	{
		 String inputStreamString = new Scanner(stream,"UTF-8").useDelimiter("\\A").next();
	     return inputStreamString;
	}
	
	/**
	 * <span class="de">Extrahiert einen String aus einem InputStream.</span>
	 * <span class="en">Extract a string from an InputStream.</span>
	 * @param stream (<span class="de">Inputstream welcher einen String enth&aumllt</span>
	 * <span class="en">InputStream with string</span>)
	 * @return <span class="de">eingebetteter String</span>
	 * <span class="en">string value</span>
	 */
	public String fromInputStreamTwo (InputStream stream)
	{
		try
		{
			StringBuilder inputStringBuilder = new StringBuilder();
	        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
	        String line = bufferedReader.readLine();
	        while(line != null){
	            inputStringBuilder.append(line);inputStringBuilder.append('\n');
	            line = bufferedReader.readLine();
	        }
	        return inputStringBuilder.toString();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
