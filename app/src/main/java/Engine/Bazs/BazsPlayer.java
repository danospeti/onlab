package Engine.Bazs;

public class BazsPlayer {
private String name;
private int penaltyPoints;
private BazsUIInterface ui;
private BazsDice dice = new BazsDice();
private BazsGameController gc;
public BazsPlayer(String name, BazsUIInterface ui, int penaltyPoints)
	{
		this.name = name;
		this.ui = ui;
		this.penaltyPoints = penaltyPoints;
	}
public void setGC(BazsGameController gc)
{
	this.gc = gc;
}

public int getPenaltyPoints()
{
	return penaltyPoints;
}
public void AddPenaltyPoint()
{
	penaltyPoints++;
	//System.out.println(name + " b�ntet�pontot kapott, �gy pontjainak sz�ma: " + penaltyPoints);
	gc.UpdateUI(this,DataType.MESSAGE, name + " büntetőpontot kapott.");
	gc.UpdateUI(this, DataType.POINTS, String.valueOf(penaltyPoints));
	
}
public String getName()
{
	return name;
}
public boolean RemovePenaltyPoint() //Returns true if won
{
	penaltyPoints--;
	//System.out.println(name + " b�ntet�pontot tett vissza, �gy pontjainak sz�ma: " + penaltyPoints);
	gc.UpdateUI(this,DataType.MESSAGE, name + " büntetőpontot tett vissza.");
	gc.UpdateUI(this, DataType.POINTS, String.valueOf(penaltyPoints));
	//Winner
	if (penaltyPoints == 0)
		return true;
	else
		return false;
}

	public BazsTurnData Turn(BazsTurnData data, BazsGameController gc) //TODO felesleges �tad�s
	{
		BazsTurnData outData = new BazsTurnData();
		outData.setGuggolas(false);
		outData.setPlayer(this);
		boolean guggolas = data.getGuggolas();
		
		if (gc.getPenaltyReverse() && penaltyPoints == 0) //ha �pp fordul a pontoz�s �s valakinek egy�ltal�n nincs pontja
		{
			outData.setWinner(this);
			return outData;
		}
		
		if (!guggolas) //ha nincs guggol�s
		{
			if (data.getRoll().getRollNumber() == 21 || data.getRoll().getRollNumber() == 66) //ha az el�z� 21-et dobott
			{
				
				BazsRollable roll = dice.Roll();
				//TODO ui interface 21
				ui.Roll(roll);
				outData.setRoll(roll);
				gc.UpdateUI(this, DataType.MESSAGE, name + " " + String.valueOf(roll.getRollNumber())+ "-t dobott!");
				if (roll.getRollNumber() !=21)
				{
					if (!gc.getPenaltyReverse())
					{
						AddPenaltyPoint();
						gc.RemovePenaltyPoint();
					}
					else
					{
						data.getPlayer().RemovePenaltyPoint();
						gc.AddPenaltyPoint();
					}
					outData.setGuggolas(true);
					return outData;
				}
				//TODO else (ha 21-re 21-et dob)
			}
			else // ha az el�z� nem 21
			{
				boolean believes = ui.Believe(data.getSaidRoll());
				
				if (ui instanceof BazsBotUI) 
				{
					if (believes)
						gc.UpdateUI(this, DataType.BELIEVE,"Elhiszem.");
					else
						gc.UpdateUI(this, DataType.BELIEVE,"Nem hiszem el.");
				}
				
				if (believes) // ha elhiszi amit az el�z� mondott
				{
					BazsRollable roll = dice.Roll();
					ui.Roll(roll);
					outData.setRoll(roll);
					if (roll.getRollNumber() != 21)
					{
						BazsRollable SaidRoll = ui.Say();
						while (SaidRoll.getValue() <= data.getSaidRoll().getValue())
						{
							ui.SaidIsSmaller();
							SaidRoll = ui.Say();
						}				
						outData.setSaidRoll(SaidRoll);
						gc.UpdateUI(this, DataType.SAY, String.valueOf(SaidRoll.getRollNumber()) + " -t dobtam");
					}
					else
					{
						gc.UpdateUI(this, DataType.MESSAGE, name + " 21-et dobott!");
					}
				
				}	
				else if (data.getSaidRoll().getRollNumber() == data.getRoll().getRollNumber()) //nem hiszi el, de igazat mondott
				{
					gc.UpdateUI(this, DataType.MESSAGE, data.getPlayer().getName() + " tényleg annyit dobott.");

					if (!gc.getPenaltyReverse()) //ha van m�g b�ntet�pont h�z�s van
					{
						AddPenaltyPoint();
						gc.RemovePenaltyPoint();					
					}
					else //ha b�ntet�pont visszatev�s van
					{
						
						if(data.getPlayer().RemovePenaltyPoint()) //nyertes be�ll�t�sa ha nyert
							outData.setWinner(data.getPlayer());
						gc.AddPenaltyPoint();
					}
					guggolas = true;
					ui.SetGuggolas();
				}
				else //nem hiszi el, �s t�nyleg kamu volt
				{
					gc.UpdateUI(this, DataType.MESSAGE, data.getPlayer().getName() + " hazudott.");

					if (!gc.getPenaltyReverse()) //ha van m�g b�ntet�pont h�z�s van
					{
						data.getPlayer().AddPenaltyPoint();
						gc.RemovePenaltyPoint();					
					}
					else //ha b�ntet�pont visszatev�s van
					{
						if (RemovePenaltyPoint()) // nyertes be�ll�t�sa ha nyert
							outData.setWinner(this);
						gc.AddPenaltyPoint();
					}
					guggolas = true;
					ui.SetGuggolas();
				}
			}
		}
		if (guggolas) // ha guggol�s van
		{
			BazsRollable roll = dice.Roll();
			ui.Roll(roll);
			outData.setRoll(roll);
			if (roll.getRollNumber() != 21)
			{
				BazsRollable SaidRoll = ui.Say();
				outData.setSaidRoll(SaidRoll);
				gc.UpdateUI(this, DataType.SAY, String.valueOf(SaidRoll.getRollNumber()) + " -t dobtam");
			}
		}
		return outData;
	}
}
