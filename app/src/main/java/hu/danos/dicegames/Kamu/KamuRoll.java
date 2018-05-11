package hu.danos.dicegames.Kamu;

import java.util.ArrayList;

public class KamuRoll {
	private ArrayList<KamuRollable> roll =new ArrayList<KamuRollable>();
	
	public KamuRoll()
	{
		for (int i = 1; i <=6; i++)
		{
			roll.add(new KamuRollable(i,0));
		}
	}
	
	public void AddOne(int rollNumber)
	{
		roll.get(rollNumber-1).AddOne();
	}
	public boolean SayIsTrue(KamuRollable say)
	{
		return roll.get(say.getRollNumber()-1).getQuantity() >= say.getQuantity();
	}
	public int getNumberOfDiceLeft()
	{
		int diceQuantity = 0;
		for (int i = 0; i < roll.size(); i++)
		{
			diceQuantity += roll.get(i).getQuantity();
		}
		return diceQuantity;
	}
	
}
