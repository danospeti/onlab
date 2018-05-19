package hu.danos.dicegames.Kamu;

import java.util.ArrayList;
import java.util.Random;

public class KamuPlayer {
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private String name;
	private KamuUIInterface ui;

	public void setGc(KamuGameController gc) {
		this.gc = gc;
	}

	private KamuGameController gc;


	public boolean isEliminated() {
		return eliminated;
	}

	public void setEliminated(boolean eliminated) {
		this.eliminated = eliminated;
	}

	private boolean eliminated = false;
	protected ArrayList<Integer> dice = new ArrayList<Integer>();
	
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
		if (dice.size() == 1) {
			eliminated = true;
			gc.UpdateUI(this,KamuDataType.ELIMINATED, "");
			gc.UpdateUI(this,KamuDataType.MESSAGE, name + " kiesett.");
			gc.UpdateUI(this,KamuDataType.DICE, String.valueOf(getDiceSize()));
			return true;
		}
		dice.remove(dice.size()-1);
		gc.UpdateUI(this,KamuDataType.DICE, String.valueOf(getDiceSize()));
		return dice.size() <=0;
	}

	public KamuTurnData Turn(KamuTurnData data)
	{
		System.out.println(name + " jön");
		KamuTurnData outData = new KamuTurnData();
		outData.setPreviousPlayer(this);
		outData.setRoll(data.getRoll());
		
		if (data.isGuggolas())
		{
			ui.Guggolas();
			outData.setPreviousSaid(ui.Say());
			outData.setGuggolas(false);
			gc.UpdateUI(this,KamuDataType.SAY, outData.getPreviousSaid().getString());
			
		}
		else //ha nincs guggolas
		{
			boolean believes = ui.Believe(data.getPreviousSaid(), data.getRoll().getNumberOfDiceLeft());

			if (believes)
			{
				gc.UpdateUI(this,KamuDataType.BELIEVE, "Elhiszem.");

				KamuRollable say = ui.Say();
				while(say.getQuantity()<=data.getPreviousSaid().getQuantity()) //am�g nem mondd egy nagyobbat mint az el�z�
				{
					ui.SaidIsSmaller();
					say = ui.Say();
				}
				outData.setPreviousSaid(say);
				gc.UpdateUI(this,KamuDataType.SAY, outData.getPreviousSaid().getString());
			}
			//ha nem hiszi el �s
			else if (data.getRoll().SayIsTrue(data.getPreviousSaid())) //az el�z� igazat mondott
			{
				gc.UpdateUI(this,KamuDataType.BELIEVE, "Nem hiszem el.");
				gc.UpdateUI(this,KamuDataType.MESSAGE, "Van annyi kocka.");
				if (removeDie())
				{
					outData.setWinner(this);
				}
				outData.setGuggolas(true);
                outData.setRoll(gc.RollAll());
            }
			else //az el�z� hazudott
			{
				gc.UpdateUI(this,KamuDataType.BELIEVE, "Nem hiszem el.");
				gc.UpdateUI(this,KamuDataType.MESSAGE, "Nincs annyi kocka.");
				if (data.getPreviousPlayer().removeDie())
				{
					outData.setWinner(data.getPreviousPlayer()); //a winner az eliminated //elbaszas
				}
				outData.setGuggolas(true);
                outData.setRoll(gc.RollAll());
			}
		}
		if (outData.isGuggolas() /*TODO és nem esett ki*/)
		{
			outData = Turn(outData);
		}
		return outData;
	}
}
