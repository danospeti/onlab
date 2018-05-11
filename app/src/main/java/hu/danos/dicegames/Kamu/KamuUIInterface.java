package hu.danos.dicegames.Kamu;

import java.util.ArrayList;

public interface KamuUIInterface {
public void getRoll(ArrayList<Integer> roll);
public boolean Believe(KamuRollable said, int numberOfDiceLeft);
public KamuRollable Say();
public void SaidIsSmaller();
public void Guggolas();
}
