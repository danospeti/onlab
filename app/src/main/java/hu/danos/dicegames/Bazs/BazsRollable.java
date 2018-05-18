package hu.danos.dicegames.Bazs;

public class BazsRollable {
private int rollNumber;
private int value;

public BazsRollable(int rN, int v)
{
	rollNumber = rN;
	value = v;

}
public int getRollNumber()
{
	return rollNumber;
}

public void setRollNumber(int rN)
{
	rollNumber = rN;
}

public int getValue()
{
	return value;
}

public void setValue(int v)
{
	value = v;
}

}
