package hu.danos.dicegames.Kamu;

import java.util.ArrayList;
import java.util.Random;

public class KamuPlayer {
	private String name;
	private KamuUIInterface ui;
	private ArrayList<Integer> dice = new ArrayList<Integer>();
	
	public KamuPlayer(String name, KamuUIInterface ui)
	{
		this.name = name;
		this.ui = ui;
		for (int i = 0; i <5 ; i++)
			dice.add(0);
	}
	public void Roll()
	{
		Random r = new Random();
		for (int i = 0; i < dice.size(); i++)
		{			
			dice.set(i, r.nextInt(6)+1);			
		}
		System.out.println(name);
		ui.getRoll(dice);
	}
	public ArrayList<Integer> getDice()
	{
		return dice;
	}
	public int getDiceSize()
	{
		return dice.size();
	}
	public boolean removeDie() //returns true if player has 0 dice
	{
		if (dice.size() == 0)
			return true;
		dice.remove(dice.size()-1);
		return dice.size() <=0;
	}

	public KamuTurnData Turn(KamuTurnData data)
	{
		System.out.println(name + " j�n");
		KamuTurnData outData = new KamuTurnData();
		outData.setPreviousPlayer(this);
		outData.setRoll(data.getRoll());
		
		if (data.isGuggolas())
		{
			ui.Guggolas();
			
			
			outData.setPreviousSaid(ui.Say());
			
		}
		else //ha nincs guggolas
		{
			boolean believes = ui.Believe(data.getPreviousSaid(), data.getRoll().getNumberOfDiceLeft());
			
			if (believes)
			{
				KamuRollable say = ui.Say();
				while(say.getQuantity()<=data.getPreviousSaid().getQuantity()) //am�g nem mondd egy nagyobbat mint az el�z�
				{
					ui.SaidIsSmaller();
					say = ui.Say();
				}
				outData.setPreviousSaid(say);
			}
			//ha nem hiszi el �s
			else if (data.getRoll().SayIsTrue(data.getPreviousSaid())) //az el�z� igazat mondott
			{
				if (removeDie())
				{
					outData.setWinner(this);
				}
				outData.setGuggolas(true);
			}
			else //az el�z� hazudott
			{
				
				if (data.getPreviousPlayer().removeDie())
				{
					outData.setWinner(data.getPreviousPlayer()); //a winner az eliminated //elbaszas
				}
				outData.setGuggolas(true);
			}
		}
		return outData;
	}
}
