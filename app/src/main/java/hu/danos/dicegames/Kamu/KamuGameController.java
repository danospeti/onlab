package hu.danos.dicegames.Kamu;

import java.util.ArrayList;


public class KamuGameController {

	private ArrayList<KamuPlayer> players = new ArrayList<KamuPlayer>();

	public ArrayList<KamuUpdate> getUIUpdates() {
		ArrayList<KamuUpdate> temp = (ArrayList<KamuUpdate>) uIUpdates.clone();
		uIUpdates.clear();
		return temp;
	}

	private ArrayList<KamuUpdate> uIUpdates = new ArrayList<KamuUpdate>();
	public KamuGameController(ArrayList<KamuPlayer> players)
	{
	 	this.players = players;
		for (KamuPlayer player : players)
		{
			player.setGc(this);
		}
	}
	
	public void Play()
	{
		int i = 0;
		KamuTurnData data = new KamuTurnData();
		data.setGuggolas(true);
		data.setRoll(RollAll());
		do
		{
			data = players.get(i).Turn(data);
			if (data.getWinner() != null)
			{
				int eliminatedIndex = players.indexOf(data.getWinner());
				if (eliminatedIndex<=i)
				{
					--i;
				}
				//i helyre�ll�t�s
				players.remove(data.getWinner());
			}
			if (data.isGuggolas()) //ha kiesett valaki lehet hogy rossz j�t�kost h�v meg
			{
				data.setRoll(RollAll());
				data = players.get(i).Turn(data);
			}
						
			if (i == players.size()-1)
				i = 0;
			else
				i++;
		}
		while(players.size()>1);
	}

	public KamuTurnData PlayARound(KamuTurnData inData)
	{
		KamuTurnData data = inData;
		for (int i = 1; i <= 3; i++)
		{
			if (!players.get(i).isEliminated()) {
				if (data.isGuggolas()) {
					data.setRoll(RollAll());
				}
				data = players.get(i).Turn(data);
				if (data.getWinner() != null) {
					//TODO kiesés
					data.getWinner().setEliminated(true);
				}
			}
		}
		return data;
	}
	public KamuPlayerAndroid getPlayerAndroid()
	{
		return (KamuPlayerAndroid) players.get(0);
	}
	
	public KamuRoll RollAll()
	{
		KamuRoll roll = new KamuRoll();
		for (int i = 0; i < players.size(); i++) //minden j�t�kos dob
		{
			players.get(i).Roll();
			ArrayList<Integer> dice = players.get(i).getDice(); //megkapjuk a dobott kock�k �rt�keit
			for (int e = 0; e < dice.size(); e++) //minden visszakapott kock�ra
			{
				roll.AddOne(dice.get(e));
			}		
		}
		return roll;
	}
	public void UpdateUI(KamuPlayer p, KamuDataType type, String msg)
	{
		KamuUpdate update = new KamuUpdate();
		KamuUpdate update2 = null;
		if (type == KamuDataType.MESSAGE)
		{
			update.setType(KamuUIDataType.MESSAGE);
			update.setWait(false);
			update.setValue(msg);
			update2 = new KamuUpdate(true, KamuUIDataType.MESSAGE, "delete");
		}
		if (type == KamuDataType.SAY)
		{
			update.setWait(false);
			update.setValue(msg);
			if (players.indexOf(p) == 1)
				update.setType(KamuUIDataType.BOT1SAYS);
			else if (players.indexOf(p) == 2)
				update.setType(KamuUIDataType.BOT2SAYS);
			else if (players.indexOf(p) == 3)
				update.setType(KamuUIDataType.BOT3SAYS);
			if (players.indexOf(p) != 3)
				update2 = new KamuUpdate(true, update.getType(), "delete");

		}
		if (type == KamuDataType.DICE)
		{
			update.setWait(false);
			update.setValue(msg);
			if (players.indexOf(p) == 1)
				update.setType(KamuUIDataType.BOT1DICE);
			else if (players.indexOf(p) == 2)
				update.setType(KamuUIDataType.BOT2DICE);
			else if (players.indexOf(p) == 3)
				update.setType(KamuUIDataType.BOT3DICE);
			else if (players.indexOf(p) == 0)
				update.setType(KamuUIDataType.PLAYERDICE);
		}
		if (type == KamuDataType.BELIEVE)
		{
			update.setWait(false);
			update.setValue(msg);
			if (players.indexOf(p) == 1)
				update.setType(KamuUIDataType.BOT1BELIEVES);
			else if (players.indexOf(p) == 2)
				update.setType(KamuUIDataType.BOT2BELIEVES);
			else if (players.indexOf(p) == 3)
				update.setType(KamuUIDataType.BOT3BELIEVES);
			update2 = new KamuUpdate(true, update.getType(), "delete");
		}
		uIUpdates.add(update);
		if (update2 != null)
			uIUpdates.add(update2);
	}
}
