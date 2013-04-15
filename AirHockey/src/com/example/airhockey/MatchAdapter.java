package com.example.airhockey;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class MatchAdapter extends ArrayAdapter<Match> {
	
	private Context context;
	private List<Match> matches;
	private TextView player1Name, player1Score, player2Name, player2Score;

	public MatchAdapter(Context context, List<Match> matches) {
		super(context, R.layout.highscore_list_item, matches);
		this.context = context;
		this.matches = matches;
	}
	
	  @Override
	  public View getView(int position, View convertView, ViewGroup parent) {
	    LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    View rowView = inflater.inflate(R.layout.highscore_list_item, parent, false);
	    Match match = matches.get(position);
	    
	    player1Name  = (TextView) rowView.findViewById(R.id.highscore_list_name1);
	    player1Score = (TextView) rowView.findViewById(R.id.highscore_list_score1);
	    player2Name  = (TextView) rowView.findViewById(R.id.highscore_list_name2);
	    player2Score = (TextView) rowView.findViewById(R.id.highscore_list_score2);

	    player1Name.setText(match.getPlayer1name());
	    player1Score.setText("" + match.getPlayer1score());
	    
	    player2Name.setText(match.getPlayer2name());
	    player2Score.setText("" + match.getPlayer2score());
	    
	    return rowView;
	  }

}
