package hu.danos.dicegames.Jutka;


import java.util.Random;

public class JutkaDie {
    private int value;
    private boolean playable =false;
    private boolean enabled =true;
    private boolean keep =false;
    public int Roll()
    {
        Random r = new Random();
        value = r.nextInt(5)+1;
        return value;
    }
    public int getValue()
    {
        return value;
    }
    public void setValue(int in)
    {
        value = in;
    }
    public boolean getPlayable()
    {
        return playable;
    }
    public void setPlayable(boolean setValue)
    {
        playable = setValue;
    }

    public boolean getEnabled()
    {
        return enabled;
    }
    public void setEnabled(boolean setValue)
    {
        enabled = setValue;
    }
    public boolean getKeep()
    {
        return keep;
    }
    public void setKeep(boolean setValue)
    {
        keep = setValue;
    }
}
