package hu.danos.dicegames.Bazs;

public class BazsTurnData {
	private boolean guggolas;
	private BazsPlayer winner = null;
	private BazsRollable SaidRoll;
	private BazsRollable Roll;
	private BazsPlayer player;
	
	
	public void setGuggolas(boolean in)
	{
		guggolas = in;
	}
	public boolean getGuggolas()
	{
		return guggolas;
	}
	public void setWinner(BazsPlayer in)
	{
		winner = in;
	}
	public BazsPlayer getWinner()
	{
		return winner;
	}
	
	public void setRoll(BazsRollable in)
	{
		Roll = in;
	}
	public BazsRollable getRoll()
	{
		return Roll;
	}
	public void setSaidRoll(BazsRollable in)
	{
		SaidRoll = in;
	}
	public BazsRollable getSaidRoll()
	{
		return SaidRoll;
	}
	
	public void setPlayer(BazsPlayer player)
	{
		this.player = player;
	}
	public BazsPlayer getPlayer()
	{
		return player;
	}
}
