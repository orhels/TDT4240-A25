package com.example.airhockey;
/**
 * Class for holding data on a match in the match history.
 *
 */
public class Match {
	
	String player1name, player2name, date;
	int player1score, player2score;

	public Match(String player1name, String player2name,
			int player1score, int player2score, String date) {
		this.player1name = player1name;
		this.player2name = player2name;
		this.date = date;
		this.player1score = player1score;
		this.player2score = player2score;
	}
	
	public String getPlayer1name() {
		return player1name;
	}
	
	public String getPlayer2name() {
		return player2name;
	}
	
	public String getDate() {
		return date;
	}
	
	public int getPlayer1score() {
		return player1score;
	}
	
	public int getPlayer2score() {
		return player2score;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}
}
