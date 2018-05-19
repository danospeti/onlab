package hu.danos.dicegames.Kamu;

public class KamuRollable {
	private int rollNumber;
	private int quantity;
	
	public KamuRollable(int rollNumber, int quantity)
	{
		this.rollNumber = rollNumber;	
		this.quantity = quantity;
		
	}
	public int getRollNumber()
	{
		return rollNumber;
	}
	public int getQuantity()
	{
		return quantity;
	}
	public void setRollNumber(int rollNumber)
	{
		this.rollNumber = rollNumber;
	}
	public void setQuantity(int quantity)
	{
		this.quantity = quantity;
	}
	public void AddOne()
	{
		quantity++;
	}

	public String getString()
	{
		String toReturn = "Szerintem van\n" + String.valueOf(this.quantity) + "db ";
		if (rollNumber == 1) toReturn += "egyes";
		else if (rollNumber == 2) toReturn += "kettes";
		else if (rollNumber == 3) toReturn += "hármas";
		else if (rollNumber == 4) toReturn += "négyes";
		else if (rollNumber == 5) toReturn += "ötös";
		else if (rollNumber == 6) toReturn += "hatos";
		toReturn += ".";
		return toReturn;
	}
}
