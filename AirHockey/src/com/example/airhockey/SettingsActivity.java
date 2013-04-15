package com.example.airhockey;

import android.app.ActionBar;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class SettingsActivity extends Activity implements OnCheckedChangeListener, OnSeekBarChangeListener {

	private RadioGroup mMalletSize, mBallSize;
	private SeekBar mBallSpeed;
	private TextView speedHeader;
	private SharedPreferences preferences;
	public static final String ballSpeed = "Speed", malletSize = "Mallet", ballSize = "Ball"; 
	public static final String small = "Small", medium = "Medium", large = "Large";

	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.settings_view);
		fetchPreferences();
		initializeUI();
		configureActionBar();
	}

	private void initializeUI() {
		speedHeader = (TextView) findViewById(R.id.settings_ball_speed_header);
		initializeRadioGroups();
		initializeSeekBar();
	}

	private void initializeRadioGroups() {
		mMalletSize = (RadioGroup) findViewById(R.id.settings_mallet_radio_group);
		checkMalletSize();
		mMalletSize.setOnCheckedChangeListener(this);
		mBallSize   = (RadioGroup) findViewById(R.id.settings_ball_radio_group);
		checkBallSize();
		mBallSize.setOnCheckedChangeListener(this);
	}
	
	private void configureActionBar() {
		ActionBar ab = getActionBar();
		ab.setTitle("Settings");
	}
	
	private void checkMalletSize() {
		String checked = preferences.getString(malletSize, small);
		if (checked.equals(small)) {
			mMalletSize.check(R.id.settings_mallet_small);
		} else if (checked.equals(medium)) {
			mMalletSize.check(R.id.settings_mallet_medium);
		} else {
			mMalletSize.check(R.id.settings_mallet_large);
		}
	}
	
	private void checkBallSize() {
		String checked = preferences.getString(ballSize, small);
		if (checked.equals(small)) {
			mBallSize.check(R.id.settings_ball_small);
		} else if (checked.equals(medium)) {
			mBallSize.check(R.id.settings_ball_medium);
		} else {
			mBallSize.check(R.id.settings_ball_large);
		}
	}

	private void initializeSeekBar() {
		mBallSpeed = (SeekBar) findViewById(R.id.settings_ball_speed);
		mBallSpeed.setOnSeekBarChangeListener(this);
		mBallSpeed.setProgress(Integer.parseInt(preferences.getString(ballSpeed, "0")));
		mBallSpeed.setMax(10);
		updateHeader();
	}

	private void fetchPreferences() {
		preferences = PreferenceManager.getDefaultSharedPreferences(this);
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		if (group.getId() == R.id.settings_mallet_radio_group) {
			handleMalletRadioGroup(checkedId);
		} else {
			handleBallRadioGroup(checkedId);
		}
	}

	private void writePreference(String key, String value) {
		SharedPreferences.Editor editor = preferences.edit();
		editor.putString(key, value);
		editor.commit();
	}

	private void handleMalletRadioGroup(int checkedId) {
		if (checkedId == R.id.settings_mallet_small) {
			writePreference(malletSize, small);
		} else if (checkedId == R.id.settings_mallet_medium) {
			writePreference(malletSize, medium);
		} else {
			writePreference(malletSize, large);
		}
	}

	private void handleBallRadioGroup(int checkedId) {
		if (checkedId == R.id.settings_ball_small) {
			writePreference(ballSize, small);
		} else if (checkedId == R.id.settings_ball_medium) {
			writePreference(ballSize, medium);
		} else {
			writePreference(ballSize, large);
		}
	}
	
	private void updateHeader() {
		int progress = mBallSpeed.getProgress();
		String descriptor = "Normal";
		
		if (progress < 3) {
			descriptor = "Slow";
		} else if (progress > 7) {
			descriptor = "Fast";
		}
		
		speedHeader.setText("Speed: " + descriptor + " (" + progress + ")");

	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
		if (fromUser) {
			writePreference(ballSpeed, String.valueOf(progress));
			updateHeader();
		}
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
	}

}
