package Engine.Bazs;
import java.util.ArrayList;

import hu.danos.dicegames.Bazs.BazsUpdate;
import hu.danos.dicegames.BazsActivity;

public class BazsGameController {
private ArrayList<BazsPlayer> players;
private int penaltyPoints;
private boolean penaltyReverse = false;
private BazsPlayer winner = null;
    private BazsActivity activity;
	private ArrayList<BazsUpdate> uIUpdates;

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
		uIUpdates = new ArrayList<BazsUpdate>();
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
		/*
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
		*/
		BazsUpdate update = new BazsUpdate();
		BazsUpdate update2 = null;
		if (type == DataType.MESSAGE)
		{
			update.setType(UIDataType.MESSAGE);
			update.setWait(false);
			update.setValue(msg);
			update2 = new BazsUpdate(true, UIDataType.MESSAGE, "delete");
		}
		if (type == DataType.SAY)
		{
			update.setWait(false);
			update.setValue(msg);
			if (players.indexOf(p) == 1)
				update.setType(UIDataType.BOT1SAYS);
			else if (players.indexOf(p) == 2)
				update.setType(UIDataType.BOT2SAYS);
			else if (players.indexOf(p) == 3)
				update.setType(UIDataType.BOT3SAYS);
			if (players.indexOf(p) != 3)
				update2 = new BazsUpdate(true, update.getType(), "delete");

		}
		if (type == DataType.POINTS)
		{
			update.setWait(false);
			update.setValue(msg);
			if (players.indexOf(p) == 1)
				update.setType(UIDataType.BOT1POINTS);
			else if (players.indexOf(p) == 2)
				update.setType(UIDataType.BOT2POINTS);
			else if (players.indexOf(p) == 3)
				update.setType(UIDataType.BOT3POINTS);
            else if (players.indexOf(p) == 0)
                update.setType(UIDataType.PLAYERPOINTS);
		}
		if (type == DataType.TABLEPOINTS)
		{
			update.setWait(false);
			update.setValue(msg);
			update.setType(UIDataType.TABLEPOINTS);

		}
		if (type == DataType.BELIEVE)
		{
			update.setWait(false);
			update.setValue(msg);
			if (players.indexOf(p) == 1)
				update.setType(UIDataType.BOT1BELIEVES);
			else if (players.indexOf(p) == 2)
				update.setType(UIDataType.BOT2BELIEVES);
			else if (players.indexOf(p) == 3)
				update.setType(UIDataType.BOT3BELIEVES);
			update2 = new BazsUpdate(true, update.getType(), "delete");
		}
		uIUpdates.add(update);
		if (update2 != null)
			uIUpdates.add(update2);
	}
	public ArrayList<BazsUpdate> getUIUpdates()
	{
		ArrayList<BazsUpdate> temp = (ArrayList<BazsUpdate>) uIUpdates.clone();
		uIUpdates.clear();
		return temp;
	}
	public BazsPlayer getPlayerAndroid()
    {
        return players.get(0);
    }
    public int getIndexOfPlayer(BazsPlayer p)
	{
		return players.indexOf(p);
	}
}