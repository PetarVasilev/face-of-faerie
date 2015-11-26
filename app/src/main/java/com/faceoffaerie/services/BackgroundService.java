package com.faceoffaerie.services;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import com.faceoffaerie.R;
import com.faceoffaerie.interfaces.BackgroundMusicModel;
import com.faceoffaerie.interfaces.BackgroundMusicModel.OnMusicStateListener;

public class BackgroundService extends Service implements OnMusicStateListener {

	private static MediaPlayer mediaPlayer = null;

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	@Override
	public void onCreate() {
		BackgroundMusicModel.getInstance().setListener(this);
     	playAudio();
	}

	@Override
	public void onStart(Intent intent, int startId) {

	}
	@Override
	public void onDestroy() {

	}

	public void stateChanged() {
		boolean isBackgroundMode = BackgroundMusicModel.getInstance().getState();
		if (isBackgroundMode) {
			pauseAudio();
		} else {
			playAudio();
		}
	}
	public void playAudio() {
		if (mediaPlayer == null) {
			mediaPlayer = MediaPlayer.create(this, R.raw.faerie_loop_short);
			mediaPlayer.setLooping(true);
			mediaPlayer.start();
		} else {
			int currentPos = mediaPlayer.getCurrentPosition();
			mediaPlayer.start();
			mediaPlayer.seekTo(currentPos);
		}
	}

	public void pauseAudio() {
		if (mediaPlayer != null && mediaPlayer.isPlaying()) {
			mediaPlayer.pause();
		} else if (mediaPlayer != null) {
			mediaPlayer.reset();
			mediaPlayer = null;
		}
	}
}
