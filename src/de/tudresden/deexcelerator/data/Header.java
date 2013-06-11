package de.tudresden.deexcelerator.data;

/**
 * <span class="de">Header f&uuml OutputObjekt.</span>
 * <span class="en">Header object for output.</span>
 * @author Christopher Werner
 *
 */
public class Header {

	private String name;
	/**
	 * <span class="de">Length nur bei String spalten existent.</span>
	 * <span class="en">Length only for string columns.</span>
	 */
	private int length;
	private DataType type;
	private DataType convertTo;
	
	public Header (String name, int length, DataType type, DataType convertTo)
	{
		this.name = name;
		this.length = length;
		this.type = type;
		this.convertTo = convertTo;		
	}
	
	public String getName() {
		return name;
	}
	
	public DataType getConvertTo() {
		return convertTo;
	}
	
	public DataType getType() {
		return type;
	}
	
	public int getLength() {
		return length;
	}
	
	
}
