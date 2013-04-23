package com.example.airhockey;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * @author G25
 * @version 1.0
 */
public class MatchAdapter extends ArrayAdapter<Match> {

	private final Context context;
	private final List<Match> matches;
	private TextView player1Name, player1Score, player2Name, player2Score;

	public MatchAdapter(final Context context, final List<Match> matches) {
		super(context, R.layout.highscore_list_item, matches);
		this.context = context;
		this.matches = matches;
	}

	@Override
	public View getView(final int position, final View convertView,
			final ViewGroup parent) {
		final LayoutInflater inflater = (LayoutInflater) this.context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		final View rowView = inflater.inflate(R.layout.highscore_list_item,
				parent, false);
		final Match match = this.matches.get(position);

		this.player1Name = (TextView) rowView
				.findViewById(R.id.highscore_list_name1);
		this.player1Score = (TextView) rowView
				.findViewById(R.id.highscore_list_score1);
		this.player2Name = (TextView) rowView
				.findViewById(R.id.highscore_list_name2);
		this.player2Score = (TextView) rowView
				.findViewById(R.id.highscore_list_score2);

		this.player1Name.setText(match.getPlayer1name());
		this.player1Score.setText("" + match.getPlayer1score());

		this.player2Name.setText(match.getPlayer2name());
		this.player2Score.setText("" + match.getPlayer2score());

		return rowView;
	}

	public Match remove(final int pos) {
		return this.matches.remove(pos);
	}

}
