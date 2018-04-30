package Engine.Bazs;

/**
 * Created by danos on 2018. 04. 30..
 */

public class BazsPlayerAndroid extends BazsPlayer {
    private BazsTurnData data;

    public BazsTurnData getOutData() {
        return outData;
    }

    public void setOutData(BazsTurnData outData) {
        this.outData = outData;
    }

    private BazsTurnData outData;
    public BazsPlayerAndroid(String name, int penaltyPoints) {
        super(name, null, penaltyPoints);
    }
    public void setData(BazsTurnData data)
    {
        this.data = data;
    }
    public BazsTurnData getData()
    {
        return data;
    }
    @Override
    public BazsTurnData Turn(BazsTurnData data, BazsGameController gc) {
        return super.Turn(data, gc);
    }
}
