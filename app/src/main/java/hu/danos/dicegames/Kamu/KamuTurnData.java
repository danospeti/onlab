package hu.danos.dicegames.Kamu;

public class KamuTurnData {
	private KamuRoll roll;
	private KamuPlayer previousPlayer;
	private boolean guggolas;
	private KamuRollable previousSaid;
	private KamuPlayer winner = null;

	public KamuRoll getRoll() {
		return roll;
	}
	public void setRoll(KamuRoll roll) {
		this.roll = roll;
	}
	public KamuPlayer getPreviousPlayer() {
		return previousPlayer;
	}
	public void setPreviousPlayer(KamuPlayer previousPlayer) {
		this.previousPlayer = previousPlayer;
	}
	public boolean isGuggolas() {
		return guggolas;
	}
	public void setGuggolas(boolean guggolas) {
		this.guggolas = guggolas;
	}
	public KamuRollable getPreviousSaid() {
		return previousSaid;
	}
	public void setPreviousSaid(KamuRollable previousSaid) {
		this.previousSaid = previousSaid;
	}
	public KamuPlayer getWinner() {
		return winner;
	}
	public void setWinner(KamuPlayer winner) {
		this.winner = winner;
	}
}
