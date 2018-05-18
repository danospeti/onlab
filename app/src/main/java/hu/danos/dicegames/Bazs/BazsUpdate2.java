package hu.danos.dicegames.Bazs;

/**
 * Created by danos on 2018. 05. 03..
 */

public class BazsUpdate2 {

    public int getPlayerIndex() {
        return playerIndex;
    }

    public void setPlayerIndex(int playerIndex) {
        this.playerIndex = playerIndex;
    }

    private DataType type;
    private int playerIndex;
    private String msg;
    private boolean disappearing;
    private static BazsGameController gc;
    public BazsUpdate2(BazsPlayer player, DataType type, String msg)
    {
        playerIndex = gc.getIndexOfPlayer(player);
        this.type = type;
        if (type == DataType.MESSAGE)
        {
            disappearing = true;
        }
        if (type == DataType.SAY)
        {
            if (playerIndex==3)
                disappearing = false;
            else
                disappearing = true;
        }
        if (type == DataType.TABLEPOINTS)
        {
            disappearing = false;
        }
        if (type == DataType.POINTS)
        {
            disappearing = false;
        }
        if (type == DataType.BELIEVE)
        {
            disappearing = true;
        }
    }

    public DataType getType() {
        return type;
    }

    public void setType(DataType type) {
        this.type = type;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isDisappearing() {
        return disappearing;
    }

    public void setDisappearing(boolean disappearing) {
        this.disappearing = disappearing;
    }

    public static BazsGameController getGc() {
        return gc;
    }

    public static void setGc(BazsGameController gc) {
        BazsUpdate2.gc = gc;
    }
}
