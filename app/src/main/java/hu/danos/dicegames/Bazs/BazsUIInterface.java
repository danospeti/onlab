package hu.danos.dicegames.Bazs;

public interface BazsUIInterface {
	public boolean Believe(BazsRollable saidRoll);
	public void Roll(BazsRollable roll);		
	public BazsRollable Say();
	public void SaidIsSmaller();
	public void SetGuggolas();
	public void UpDateUI(UIDataType type, String value);
}
