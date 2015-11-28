package com.faceoffaerie.activities;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.faceoffaerie.R;
import com.faceoffaerie.adapter.SavedFaerieListAdapter;
import com.faceoffaerie.contants.PlistInfo;
import com.faceoffaerie.db.Dao;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class SavedFaeriesActivity extends BaseActivity implements OnClickListener{

    @InjectView(R.id.rootRelativeLayout)
    RelativeLayout rootRelativeLayout;

    @InjectView(R.id.titleImageView)
    ImageView titleImageView;

    @InjectView(R.id.swipeTextView)
    TextView swipeTextView;

    @InjectView(R.id.homeButton)
    Button homeButton;

    @InjectView(R.id.faeryListView)
    ListView faeryListView;

    private ArrayList<PlistInfo> savedFaeries = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setLayoutId(this, R.layout.activity_saved_faeries);
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        initView();
        setListener();
        initData();
    }

    public void initView() {
        ButterKnife.inject(this);

        Typeface typeface = Typeface.createFromAsset(getAssets(), "BarbedorSCTMed.ttf");
        swipeTextView.setTypeface(typeface);
    }

    public void setListener() {
        homeButton.setOnClickListener(this);
    }

    public void initData() {
        Dao dao = new Dao(this);
        dao.open();
        savedFaeries = dao.getFavourFunc();
        dao.close();
        SavedFaerieListAdapter savedFaerieListAdapter = new SavedFaerieListAdapter(this, savedFaeries, faeryListView);
        faeryListView.setAdapter(savedFaerieListAdapter);
        savedFaerieListAdapter.notifyDataSetChanged();
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
