package hu.danos.dicegames.Kamu;

import java.util.Random;

/**
 * Created by danos on 2018. 05. 11..
 */

public class KamuPlayerAndroid extends KamuPlayer {

    public KamuTurnData getData() {
        return data;
    }

    public void setData(KamuTurnData data) {
        this.data = data;
    }

    private KamuTurnData data;

    public KamuTurnData getOutData() {
        return outData;
    }

    public void setOutData(KamuTurnData outData) {
        this.outData = outData;
    }

    private KamuTurnData outData;
    public KamuPlayerAndroid(String name, KamuUIInterface ui) {
        super(name, ui);
    }

    @Override
    public void Roll() {
        Random r = new Random();
        for (int i = 0; i < dice.size(); i++)
        {
            dice.set(i, r.nextInt(6)+1);
        }
    }

    @Override
    public KamuTurnData Turn(KamuTurnData data) {
        return super.Turn(data);
    }

}
