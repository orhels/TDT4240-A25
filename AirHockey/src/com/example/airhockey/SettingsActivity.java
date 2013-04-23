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
public class SettingsActivity extends Activity implements
		OnCheckedChangeListener, OnSeekBarChangeListener {

	private RadioGroup mMalletSize, mBallSize;
	private SeekBar mBallSpeed;
	private TextView speedHeader;
	private SharedPreferences preferences;

	@Override
	public void onCreate(final Bundle bundle) {
		super.onCreate(bundle);
		this.setContentView(R.layout.settings_view);
		this.fetchPreferences();
		this.initializeUI();
		this.configureActionBar();
	}

	private void initializeUI() {
		this.speedHeader = (TextView) this
				.findViewById(R.id.settings_ball_speed_header);
		this.initializeRadioGroups();
		this.initializeSeekBar();
	}

	private void initializeRadioGroups() {
		this.mMalletSize = (RadioGroup) this
				.findViewById(R.id.settings_mallet_radio_group);
		this.checkMalletSize();
		this.mMalletSize.setOnCheckedChangeListener(this);
		this.mBallSize = (RadioGroup) this
				.findViewById(R.id.settings_ball_radio_group);
		this.checkBallSize();
		this.mBallSize.setOnCheckedChangeListener(this);
	}

	private void configureActionBar() {
		final ActionBar ab = this.getActionBar();
		ab.setDisplayHomeAsUpEnabled(true);
		ab.setTitle("Settings");
	}

	@Override
	public boolean onOptionsItemSelected(final MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			this.finish();
		}
		return true;
	}

	private void checkMalletSize() {
		final String checked = this.preferences.getString(Constants.MALLETSIZE,
				Constants.SMALL);
		if (checked.equals(Constants.SMALL)) {
			this.mMalletSize.check(R.id.settings_mallet_small);
		} else if (checked.equals(Constants.MEDIUM)) {
			this.mMalletSize.check(R.id.settings_mallet_medium);
		} else {
			this.mMalletSize.check(R.id.settings_mallet_large);
		}
	}

	private void checkBallSize() {
		final String checked = this.preferences.getString(Constants.PUCKSIZE,
				Constants.SMALL);
		if (checked.equals(Constants.SMALL)) {
			this.mBallSize.check(R.id.settings_ball_small);
		} else if (checked.equals(Constants.MEDIUM)) {
			this.mBallSize.check(R.id.settings_ball_medium);
		} else {
			this.mBallSize.check(R.id.settings_ball_large);
		}
	}

	private void initializeSeekBar() {
		this.mBallSpeed = (SeekBar) this.findViewById(R.id.settings_ball_speed);
		this.mBallSpeed.setOnSeekBarChangeListener(this);
		this.mBallSpeed.setProgress(Integer.parseInt(this.preferences
				.getString(Constants.BALLSPEED, "0")));
		this.mBallSpeed.setMax(9);
		this.updateHeader();
	}

	private void fetchPreferences() {
		this.preferences = PreferenceManager.getDefaultSharedPreferences(this);
	}

	@Override
	public void onCheckedChanged(final RadioGroup group, final int checkedId) {
		if (group.getId() == R.id.settings_mallet_radio_group) {
			this.handleMalletRadioGroup(checkedId);
		} else {
			this.handleBallRadioGroup(checkedId);
		}
	}

	private void writePreference(final String key, final String value) {
		final SharedPreferences.Editor editor = this.preferences.edit();
		editor.putString(key, value);
		editor.commit();
	}

	private void handleMalletRadioGroup(final int checkedId) {
		if (checkedId == R.id.settings_mallet_small) {
			this.writePreference(Constants.MALLETSIZE, Constants.SMALL);
		} else if (checkedId == R.id.settings_mallet_medium) {
			this.writePreference(Constants.MALLETSIZE, Constants.MEDIUM);
		} else {
			this.writePreference(Constants.MALLETSIZE, Constants.LARGE);
		}
	}

	private void handleBallRadioGroup(final int checkedId) {
		if (checkedId == R.id.settings_ball_small) {
			this.writePreference(Constants.PUCKSIZE, Constants.SMALL);
		} else if (checkedId == R.id.settings_ball_medium) {
			this.writePreference(Constants.PUCKSIZE, Constants.MEDIUM);
		} else {
			this.writePreference(Constants.PUCKSIZE, Constants.LARGE);
		}
	}

	private void updateHeader() {
		final int progress = this.mBallSpeed.getProgress();
		String descriptor = "Normal";

		if (progress < 3) {
			descriptor = "Slow";
		} else if (progress > 7) {
			descriptor = "Fast";
		}

		this.speedHeader.setText("Speed: " + descriptor + " (" + (progress + 1)
				+ ")");

	}

	@Override
	public void onProgressChanged(final SeekBar seekBar, final int progress,
			final boolean fromUser) {
		if (fromUser) {
			this.writePreference(Constants.BALLSPEED,
					String.valueOf(progress + 1));
			this.updateHeader();
		}
	}

	@Override
	public void onStartTrackingTouch(final SeekBar seekBar) {
	}

	@Override
	public void onStopTrackingTouch(final SeekBar seekBar) {
	}

}
