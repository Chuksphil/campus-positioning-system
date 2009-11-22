package message;

public class AccessPoint
{	
	
	private String macAdress;
	private int signalStrenght;
	
	
	
	public AccessPoint(String macAdress, int signalStrenght) 
	{
		super();
		this.macAdress = macAdress;
		this.signalStrenght = signalStrenght;
	}
	
	
	public String getMacAdress()
	{
		return macAdress;
	}
	public void setMacAdress(String value)
	{
		macAdress = value;
	}
	
	public int getSignalStrenght()
	{
		return signalStrenght;
	}
	public void setSignalStrenght(int value)
	{
		signalStrenght = value;
	}
}
