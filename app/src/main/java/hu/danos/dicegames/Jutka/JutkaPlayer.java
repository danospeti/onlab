package hu.danos.dicegames.Jutka;

public class JutkaPlayer {
    String name;
    int point;
    int negativePoint;
    int negativeSzeria;

    public JutkaPlayer() {}
    public JutkaPlayer(String name) {
        this.name = name;
    }

    public JutkaPlayer(String name, int point, int negativePoint, int negativeSzeria) {
        this.name = name;
        this.point = point;
        this.negativePoint = negativePoint;
        this.negativeSzeria = negativeSzeria;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }
    
    public int getnegativePoint() {
        return negativePoint;
    }

    public void setnegativePoint(int negativePoint) {
        this.negativePoint = negativePoint;
    }

    public int getnegativeSzeria() {
        return negativeSzeria;
    }

    public void setnegativeSzeria(int negativeSzeria) {
        this.negativeSzeria = negativeSzeria;
    }
}
