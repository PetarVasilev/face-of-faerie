package com.faceoffaerie.activities;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import com.faceoffaerie.R;
import com.faceoffaerie.contants.Constants;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class SplashActivity extends Activity {

    @InjectView(R.id.rootRelativeLayout)
    RelativeLayout rootRelativeLayout;

    @InjectView(R.id.logoImageView)
    ImageView logoImageView;

    @InjectView(R.id.chooseTextView)
    TextView chooseTextView;

    @InjectView(R.id.youChooseImageView)
    ImageView youChooseImageView;

    @InjectView(R.id.faeryChooseImageView)
    ImageView faeryChooseImageView;

    @InjectView(R.id.splashImageView)
    ImageView splashImageView;

    @InjectView(R.id.savedFaeriesImageView)
    ImageView savedFaeriesImageView;

    @InjectView(R.id.autoGraphImageView)
    ImageView autoGraphImageView;

    @InjectView(R.id.infoImageView)
    ImageView infoImageView;

    private VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        initView();
        setListener();
        initData();
    }

    public void initView() {
        ButterKnife.inject(this);

        Display dispDefault = ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        Constants.setWidth(this, dispDefault.getWidth());
        Constants.setHeight(this, dispDefault.getHeight());
        DisplayMetrics dispMetrics = new DisplayMetrics();
        dispDefault.getMetrics(dispMetrics);
        Constants.setDensity(this, dispMetrics.density);

        float rateX = Constants.getWidth(this) / 500.0f;
        float rateY = Constants.getHeight(this) / 888.0f;

        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) logoImageView.getLayoutParams();
        params.width = (int) (185 * Constants.getDensity(this) * rateX);
        params.height = (int) (100 * Constants.getDensity(this) * rateY);
        logoImageView.setLayoutParams(params);

        params = (RelativeLayout.LayoutParams) chooseTextView.getLayoutParams();
        params.setMargins(0, (int) (18 * Constants.getDensity(this) * rateY), 0, 0);
        chooseTextView.setLayoutParams(params);

        params = (RelativeLayout.LayoutParams) youChooseImageView.getLayoutParams();
        params.width = (int) (160 * Constants.getDensity(this) * rateX);
        params.height = (int) (30 * Constants.getDensity(this) * rateY);
        params.setMargins(0, (int) (32 * Constants.getDensity(this) * rateY), 0, 0);
        youChooseImageView.setLayoutParams(params);

        params = (RelativeLayout.LayoutParams) faeryChooseImageView.getLayoutParams();
        params.width = (int) (180 * Constants.getDensity(this) * rateX);
        params.height = (int) (90 * Constants.getDensity(this) * rateY);
        faeryChooseImageView.setLayoutParams(params);
    }

    public void setListener() {

    }

    public void initData() {
        Uri videoUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.splash_animation_640x1136);
        videoView = new VideoView(this);
        rootRelativeLayout.addView(videoView);
        videoView.setVideoURI(videoUri);
        videoView.requestFocus();
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                splashImageView.setVisibility(View.GONE);
                videoView.start();
            }
        });
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                videoView.suspend();
                rootRelativeLayout.removeView(videoView);
            }
        });
    }
}
