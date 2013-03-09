package tdt4240.A25;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class AirHockeyActivity extends Activity
{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		TextView tv = new TextView(this);
		tv.setText("Hoang er homo!");
		setContentView(tv);
    }
}
