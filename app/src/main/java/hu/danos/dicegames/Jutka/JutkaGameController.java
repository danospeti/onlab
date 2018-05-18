package hu.danos.dicegames.Jutka;

import java.util.ArrayList;

/**
 * Created by danos on 2017. 12. 09..
 */

public class JutkaGameController {

    ArrayList<JutkaPlayer> players;
    JutkaPlayer currentPlayer;
    int roundNumber;

    public static int negative_NULLA = -1;
    static JutkaGameController instance = null;

    private void GameController(){

    }


    public static JutkaGameController getInstance(){
        if(instance == null){
            instance = new JutkaGameController();
            instance.players = new ArrayList<>();
            instance.roundNumber = 0;
            return instance;
        }
        else{
            return instance;
        }
    }

    public JutkaPlayer getCurrentPlayer() {
        return currentPlayer;
    }

    public void negativeNulla(){
        if(currentPlayer.getPoint() >= 0){

            currentPlayer.setPoint(0);
            currentPlayer.setnegativeSzeria(currentPlayer.getnegativeSzeria() + 1);

            if(currentPlayer.getnegativeSzeria() >= 3){
                currentPlayer.setPoint(currentPlayer.getPoint() + currentPlayer.getnegativePoint());
                currentPlayer.setnegativePoint(0);
            }

        }

    }


    public void setResultForCurrentPlayer(int point) {
        if(point <= 0){

            currentPlayer.setnegativeSzeria(currentPlayer.getnegativeSzeria() + 1);
            currentPlayer.setnegativePoint(currentPlayer.getnegativePoint() + point);

            if(currentPlayer.getnegativeSzeria() >= 3){
                currentPlayer.setPoint(currentPlayer.getPoint() + currentPlayer.getnegativePoint());
                currentPlayer.setnegativePoint(0);
            }

        }

        else{
            currentPlayer.setnegativePoint(0);
            currentPlayer.setnegativeSzeria(0);
            currentPlayer.setPoint(currentPlayer.getPoint() + point);
        }
    }

    public void switchToNextPlayer(){
        int pos = players.indexOf(currentPlayer);
        if(pos == players.size()-1){
            pos = -1;
            roundNumber++;
        }

        currentPlayer = players.get(pos+1);

    }

    public void initGame(){
        currentPlayer = players.get(0);
    }

    public void addPlayer(JutkaPlayer player){
        players.add(player);
    }

    public ArrayList<JutkaPlayer> getPlayers() {
        return players;
    }

    public void resetGame(){
        players = new ArrayList<>();
        currentPlayer = null;
        roundNumber = 0;
    }

    public void setPlayers(ArrayList<JutkaPlayer> players) {
        this.players = players;
    }

    public int getRoundNumber() {
        return roundNumber;
    }

    public void setRoundNumber(int roundNumber) {
        this.roundNumber = roundNumber;
    }

}
