package com.faceoffaerie.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import com.faceoffaerie.R;
import com.faceoffaerie.contants.Constants;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class SplashActivity extends BaseActivity implements OnClickListener{

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

    @InjectView(R.id.savedFaeriesButton)
    Button savedFaeriesButton;

    @InjectView(R.id.autoGraphButton)
    Button autoGraphButton;

    @InjectView(R.id.infoButton)
    Button infoButton;

    private VideoView videoView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setLayoutId(this, R.layout.activity_splash);
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

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

        Typeface typeface = Typeface.createFromAsset(getAssets(), "BarbedorSCTMed.ttf");
        chooseTextView.setTypeface(typeface);
    }

    public void setListener() {
        savedFaeriesButton.setOnClickListener(this);
        autoGraphButton.setOnClickListener(this);
        infoButton.setOnClickListener(this);
        faeryChooseImageView.setOnClickListener(this);
        youChooseImageView.setOnClickListener(this);
    }

    public void initData() {
        Uri videoUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.splash_animation_640x1136);
        videoView = new VideoView(this);
        rootRelativeLayout.addView(videoView);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) videoView.getLayoutParams();
        params.width = RelativeLayout.LayoutParams.MATCH_PARENT;
        params.height = RelativeLayout.LayoutParams.MATCH_PARENT;
        videoView.setLayoutParams(params);
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
    public void onClick(View v) {
        int viewId = v.getId();
        switch (viewId) {
            case R.id.savedFaeriesButton: {
                startActivity(new Intent(this, SavedFaeriesActivity.class));
            }
            break;
            case R.id.autoGraphButton: {
                startActivity(new Intent(this, AutoGraphsActivity.class));
            }
            break;
            case R.id.infoButton: {
                startActivity(new Intent(this, InfoActivity.class));
            }
            break;
            case R.id.faeryChooseImageView: {
                startActivity(new Intent(this, FaeryChooseActivity.class));
            }
            break;
            case R.id.youChooseImageView: {
                startActivity(new Intent(this, FaeryCameraActivity.class));
            }
            break;

        }
    }
}
