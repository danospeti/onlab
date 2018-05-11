package hu.danos.dicegames.Kamu;

import java.util.ArrayList;



public class KamuGameController {

	private ArrayList<KamuPlayer> players = new ArrayList<KamuPlayer>();
	public KamuGameController(ArrayList<KamuPlayer> players)
	{
	 	this.players = players;		
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
	
	private KamuRoll RollAll()
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
}
