package com.faceoffaerie.activities;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.faceoffaerie.R;
import com.faceoffaerie.contants.Constants;
import com.faceoffaerie.contants.PlistInfo;
import com.faceoffaerie.parser.ParsePlistParser;

import java.util.ArrayList;
import java.util.Random;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class FaeryChooseActivity extends BaseActivity implements OnClickListener{

    @InjectView(R.id.rootRelativeLayout)
    RelativeLayout rootRelativeLayout;

    @InjectView(R.id.titleTextView)
    TextView titleTextView;

    @InjectView(R.id.homeButton)
    Button homeButton;

    @InjectView(R.id.symbolImageView)
    ImageView symbolImageView;

    @InjectView(R.id.faerieRelativeLayout)
    RelativeLayout faerieRelativeLayout;

    @InjectView(R.id.faerieImageView)
    ImageView faerieImageView;

    @InjectView(R.id.faerieNameTextView)
    TextView faerieNameTextView;

    @InjectView(R.id.faerieReadingTextView)
    TextView faerieReadingTextView;

    private ArrayList<PlistInfo> faerieChooseList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setLayoutId(this, R.layout.activity_faery_choose);
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        initView();
        setListener();
        initData();
    }

    public void initView() {
        ButterKnife.inject(this);

        Typeface typeface = Typeface.createFromAsset(getAssets(), "BarbedorSCTMed.ttf");
        titleTextView.setTypeface(typeface);
        faerieNameTextView.setTypeface(typeface);
        faerieReadingTextView.setTypeface(typeface);

        faerieRelativeLayout.setVisibility(View.GONE);
    }

    public void setListener() {
        homeButton.setOnClickListener(this);
        symbolImageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (faerieChooseList != null && faerieChooseList.size() > 0) {
                    Random random = new Random(System.currentTimeMillis());
                    int index = random.nextInt(faerieChooseList.size());
                    showFaerieChoose(index);
                }
            }
        });
    }
    public void showFaerieChoose(int index) {
        faerieRelativeLayout.setVisibility(View.VISIBLE);
        PlistInfo selectedInfo = faerieChooseList.get(index);
        faerieNameTextView.setText(selectedInfo.name);
        faerieReadingTextView.setText(selectedInfo.reading);

    }
    public void initData() {
        String xml = Constants.readFaerieChooseFromAssetsPlist(this);
        ParsePlistParser pp = new ParsePlistParser();
        faerieChooseList = pp.parsePlist(xml);
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
