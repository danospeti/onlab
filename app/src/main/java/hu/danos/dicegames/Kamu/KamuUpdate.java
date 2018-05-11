package hu.danos.dicegames.Kamu;

/**
 * Created by danos on 2018. 05. 11..
 */

public class KamuUpdate {
    private boolean wait;
    private KamuUIDataType type;
    private String value;
    public KamuUpdate() {
    }

    public KamuUpdate(boolean wait, KamuUIDataType type, String value) {

        this.wait = wait;
        this.type = type;
        this.value = value;
    }

    public boolean isWait() {

        return wait;
    }

    public void setWait(boolean wait) {
        this.wait = wait;
    }

    public KamuUIDataType getType() {
        return type;
    }

    public void setType(KamuUIDataType type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }


}
