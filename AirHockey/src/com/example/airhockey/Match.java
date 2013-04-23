package com.example.airhockey;

/**
 * @author G25
 * @version 1.0
 */
public class Match {

	String player1name, player2name, date;
	int player1score, player2score;

	public Match(final String player1name, final String player2name,
			final int player1score, final int player2score, final String date) {
		this.player1name = player1name;
		this.player2name = player2name;
		this.date = date;
		this.player1score = player1score;
		this.player2score = player2score;
	}

	public String getPlayer1name() {
		return this.player1name;
	}

	public String getPlayer2name() {
		return this.player2name;
	}

	public String getDate() {
		return this.date;
	}

	public int getPlayer1score() {
		return this.player1score;
	}

	public int getPlayer2score() {
		return this.player2score;
	}

}
