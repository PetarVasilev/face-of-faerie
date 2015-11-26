package com.faceoffaerie.activities;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.faceoffaerie.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class AutoGraphsActivity extends BaseActivity implements OnClickListener{

    @InjectView(R.id.rootRelativeLayout)
    RelativeLayout rootRelativeLayout;

    @InjectView(R.id.titleImageView)
    ImageView titleImageView;

    @InjectView(R.id.autoGraphImageView)
    ImageView autoGraphImageView;

    @InjectView(R.id.homeButton)
    Button homeButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setLayoutId(this, R.layout.activity_autographs);
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        initView();
        setListener();
        initData();
    }

    public void initView() {
        ButterKnife.inject(this);
    }

    public void setListener() {
        homeButton.setOnClickListener(this);
    }

    public void initData() {

    }
    public void onClick(View v) {
        int viewId = v.getId();
        switch (viewId) {
            case R.id.homeButton: {
                finish();
            }
            break;
        }
    }
}
