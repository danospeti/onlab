package hu.danos.dicegames.Bazs;

/**
 * Created by danos on 2018. 05. 11..
 */

public class BazsUpdate {
    public boolean isWait() {
        return wait;
    }

    public void setWait(boolean wait) {
        this.wait = wait;
    }

    public UIDataType getType() {
        return type;
    }

    public void setType(UIDataType type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public BazsUpdate(boolean wait, UIDataType type, String value) {

        this.wait = wait;
        this.type = type;
        this.value = value;
    }
    public BazsUpdate() {

        this.wait = false;
        this.type = null;
        this.value = null;
    }
    private boolean wait;
    private UIDataType type;
    private String value;

}
