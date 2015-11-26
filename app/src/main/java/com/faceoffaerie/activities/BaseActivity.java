package com.faceoffaerie.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Window;

import com.faceoffaerie.interfaces.BackgroundMusicModel;

public class BaseActivity extends FragmentActivity {
	private int layoutID;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(layoutID);

	}

	@Override
	public void onResume() {
		super.onResume();
		BackgroundMusicModel.getInstance().changeState(false);
	}
	@Override
	public void onPause() {
		super.onPause();
		BackgroundMusicModel.getInstance().changeState(true);
	}
	protected void setLayoutId(Context context, final int layoutID) {
		this.layoutID = layoutID;
	}

}