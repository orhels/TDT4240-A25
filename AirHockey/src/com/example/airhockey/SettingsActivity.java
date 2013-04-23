package com.example.airhockey;

import android.app.ActionBar;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

/**
 * @author G25
 * @version 1.0
 */
public class SettingsActivity extends Activity implements OnCheckedChangeListener, OnSeekBarChangeListener {

	private RadioGroup mMalletSize, mBallSize;
	private SeekBar mBallSpeed;
	private TextView speedHeader;
	private SharedPreferences preferences;
	

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
		ab.setDisplayHomeAsUpEnabled(true);
		ab.setTitle("Settings");
	}

	public boolean onOptionsItemSelected (MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			this.finish();
		}
		return true;
	}

	private void checkMalletSize() {
		String checked = preferences.getString(Constants.MALLETSIZE, Constants.SMALL);
		if (checked.equals(Constants.SMALL)) {
			mMalletSize.check(R.id.settings_mallet_small);
		} else if (checked.equals(Constants.MEDIUM)) {
			mMalletSize.check(R.id.settings_mallet_medium);
		} else {
			mMalletSize.check(R.id.settings_mallet_large);
		}
	}

	private void checkBallSize() {
		String checked = preferences.getString(Constants.PUCKSIZE, Constants.SMALL);
		if (checked.equals(Constants.SMALL)) {
			mBallSize.check(R.id.settings_ball_small);
		} else if (checked.equals(Constants.MEDIUM)) {
			mBallSize.check(R.id.settings_ball_medium);
		} else {
			mBallSize.check(R.id.settings_ball_large);
		}
	}

	private void initializeSeekBar() {
		mBallSpeed = (SeekBar) findViewById(R.id.settings_ball_speed);
		mBallSpeed.setOnSeekBarChangeListener(this);
		mBallSpeed.setProgress(Integer.parseInt(preferences.getString(Constants.BALLSPEED, "0")));
		mBallSpeed.setMax(9);
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
			writePreference(Constants.MALLETSIZE, Constants.SMALL);
		} else if (checkedId == R.id.settings_mallet_medium) {
			writePreference(Constants.MALLETSIZE, Constants.MEDIUM);
		} else {
			writePreference(Constants.MALLETSIZE, Constants.LARGE);
		}
	}

	private void handleBallRadioGroup(int checkedId) {
		if (checkedId == R.id.settings_ball_small) {
			writePreference(Constants.PUCKSIZE, Constants.SMALL);
		} else if (checkedId == R.id.settings_ball_medium) {
			writePreference(Constants.PUCKSIZE, Constants.MEDIUM);
		} else {
			writePreference(Constants.PUCKSIZE, Constants.LARGE);
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

		speedHeader.setText("Speed: " + descriptor + " (" + (progress + 1) + ")");

	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
		if (fromUser) {
			writePreference(Constants.BALLSPEED, String.valueOf(progress + 1));
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
