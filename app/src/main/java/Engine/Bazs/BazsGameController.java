package Engine.Bazs;
import java.util.ArrayList;

import hu.danos.dicegames.BazsActivity;

public class BazsGameController {
private ArrayList<BazsPlayer> players;
private int penaltyPoints;
private boolean penaltyReverse = false;
private BazsPlayer winner = null;
    private BazsActivity activity;
	private ArrayList<String> uIUpdates;

//Player, bot1, bot2, bot3

public BazsGameController(ArrayList<BazsPlayer> inPlayers, int pPoints, BazsActivity activity)
	{
	 	players = inPlayers;
		penaltyPoints = pPoints;
        this.activity = activity;
		for (BazsPlayer player:players)
		{
			player.setGC(this);
		}
		uIUpdates = new ArrayList<String>();
	}
public BazsActivity getActivity()
{
    return activity;
}

public void setPenaltyReverse(boolean penaltyReverse)
{
	this.penaltyReverse = penaltyReverse; 
}
public boolean getPenaltyReverse()
{
	return penaltyReverse;
}

public int getPenaltyPoints()
{
	return penaltyPoints;
}
public void AddPenaltyPoint()
{
	penaltyPoints++;
	//System.out.println("b�ntet�pontok sz�ma az asztalon: " + penaltyPoints);
	UpdateUI(null, DataType.TABLEPOINTS, String.valueOf(penaltyPoints));

}
public void RemovePenaltyPoint()
{
	penaltyPoints--;
	//System.out.println("b�ntet�pontok sz�ma az asztalon: " + penaltyPoints);
	UpdateUI(null, DataType.TABLEPOINTS, String.valueOf(penaltyPoints));
	if (penaltyPoints == 0)
		penaltyReverse = true;
}

	public void Play()
	{
		int i = 0;
		BazsTurnData data = new BazsTurnData();
		data.setGuggolas(true);
		data.setWinner(null);
		do
		{
			data = players.get(i).Turn(data, this);
			if (i == players.size()-1)
				i = 0;
			else
				i++;
			winner =data.getWinner();
		}
		while(winner == null);
		//End of game
		//System.out.println("A nyertes: "+ winner.getName());
		UpdateUI(null, DataType.MESSAGE, "A j�t�knak v�ge. " + winner.getName() + " nyert.");

	}
	public BazsTurnData PlayARound(BazsTurnData inData)
    {
        BazsTurnData data = inData;
        for(int i = 1; i < players.size(); i++)
        {
            data = players.get(i).Turn(data, this);
            if (data.getWinner()!=null)
                break;
        }
        return data;
    }
	public void UpdateUI(BazsPlayer p, DataType type, String msg)
	{

		// kimenet type:msg
		//types: msg, botXsay, botXpts, tpts
		//TODO playerpoints updateelése
		String toAdd = "";
		if (type == DataType.MESSAGE)
		{
			toAdd+="msg";
		}
		if (type == DataType.SAY)
		{
			toAdd +="bot";
			toAdd += String.valueOf(players.indexOf(p));
			toAdd +="say";

		}
		if (type == DataType.POINTS)
		{
			toAdd +="bot";
			toAdd += String.valueOf(players.indexOf(p));
			toAdd +="pts";
		}
		if (type == DataType.TABLEPOINTS)
		{
			toAdd+="tpts";
		}
		toAdd +=":";
		toAdd +=msg;
		uIUpdates.add(toAdd);
	}
	public ArrayList<String> getUIUpdates()
	{
		return uIUpdates;
	}
	public void clearUiUpdates()
	{
		uIUpdates.clear();
	}
	public BazsPlayer getPlayerAndroid()
    {
        return players.get(0);
    }
}