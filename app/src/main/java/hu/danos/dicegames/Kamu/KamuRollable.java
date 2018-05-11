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
	
}
