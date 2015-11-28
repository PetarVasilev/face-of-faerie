package com.faceoffaerie.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.faceoffaerie.R;
import com.faceoffaerie.contants.Constants;
import com.faceoffaerie.contants.PlistInfo;
import com.faceoffaerie.db.Dao;
import com.faceoffaerie.parser.ParsePlistParser;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class FaeryChooseActivity extends BaseActivity implements OnClickListener{

    @InjectView(R.id.rootRelativeLayout) RelativeLayout rootRelativeLayout;
    @InjectView(R.id.titleTextView) TextView titleTextView;
    @InjectView(R.id.homeButton) Button homeButton;
    @InjectView(R.id.symbolImageView) ImageView symbolImageView;
    @InjectView(R.id.faerieRelativeLayout) RelativeLayout faerieRelativeLayout;
    @InjectView(R.id.faerieImageView) ImageView faerieImageView;
    @InjectView(R.id.faerieNameTextView) TextView faerieNameTextView;
    @InjectView(R.id.faerieReadingTextView) TextView faerieReadingTextView;
    @InjectView(R.id.menuLinearLayout) LinearLayout menuLinearLayout;
    @InjectView(R.id.facebookMenuImageView) ImageView facebookMenuImageView;
    @InjectView(R.id.twitterMenuImageView) ImageView twitterMenuImageView;
    @InjectView(R.id.emailMenuImageView) ImageView emailMenuImageView;
    @InjectView(R.id.messageMenuImageView) ImageView messageMenuImageView;
    @InjectView(R.id.cancelMenuImageView) ImageView cancelMenuImageView;
    @InjectView(R.id.reconnectMenuImageView) ImageView reconnectMenuImageView;
    @InjectView(R.id.saveMenuImageView) ImageView saveMenuImageView;
    @InjectView(R.id.homeMenuImageView) ImageView homeMenuImageView;
    @InjectView(R.id.infoMenuImageView) ImageView infoMenuImageView;

    private ArrayList<PlistInfo> faerieChooseList;
    private int selectedIndex = 0;

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
        menuLinearLayout.setVisibility(View.GONE);
    }

    public void setListener() {
        symbolImageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (faerieChooseList != null && faerieChooseList.size() > 0) {
                    Random random = new Random(System.currentTimeMillis());
                    selectedIndex = random.nextInt(faerieChooseList.size());
                    showFaerieChoose(selectedIndex);
                }
            }
        });
    }
    public void showFaerieChoose(int index) {
        faerieRelativeLayout.setVisibility(View.VISIBLE);
        PlistInfo selectedInfo = faerieChooseList.get(index);
        faerieNameTextView.setText(selectedInfo.name);
        faerieReadingTextView.setText(selectedInfo.reading);
        try {
            InputStream ims = getAssets().open(String.format("faerie%d.png", index));
            Drawable d = Drawable.createFromStream(ims, null);
            faerieImageView.setImageDrawable(d);
        } catch (Exception e) {}
    }
    public void initData() {
        String xml = Constants.readFaerieChooseFromAssetsPlist(this);
        ParsePlistParser pp = new ParsePlistParser();
        faerieChooseList = pp.parsePlist(xml);
    }
    public void onDestroy() {
        super.onDestroy();
        BitmapDrawable d = (BitmapDrawable) faerieImageView.getDrawable();
        if (d != null && d.getBitmap() != null)
            d.getBitmap().recycle();
    }
    public void onClick(View v) {
        int viewId = v.getId();
        switch (viewId) {
            case R.id.homeButton: {
                finish();
            }
            break;
            case R.id.faerieImageView: {
                if (menuLinearLayout.getVisibility() == View.VISIBLE) {
                    hideMenuLayout();
                } else {
                    showMenuLayout();
                }
            }
            break;
            case R.id.cancelMenuImageView: {
                hideMenuLayout();
            }
            break;
            case R.id.homeMenuImageView: {
                finish();
            }
            break;
            case R.id.infoMenuImageView: {
                Intent intent = new Intent(this, InfoActivity.class);
                intent.putExtra("fromFaeryChoose", true);
                startActivity(intent);
                hideMenuLayout();
            }
            break;
            case R.id.reconnectMenuImageView: {
                selectedIndex = 0;
                BitmapDrawable d = (BitmapDrawable) faerieImageView.getDrawable();
                if (d != null && d.getBitmap() != null)
                    d.getBitmap().recycle();
                faerieImageView.setImageBitmap(null);
                faerieRelativeLayout.setVisibility(View.GONE);
                hideMenuLayout();
            }
            break;
            case R.id.saveMenuImageView: {
                int result = saveFaeryToDB(faerieChooseList.get(selectedIndex), 0);
                if (result == 0) {
                    Constants.showMessage(FaeryChooseActivity.this, String.format("\"%s\"\nTo your saved faeries", faerieChooseList.get(selectedIndex).name));
                } else if (result == 1) {
                    new AlertDialog.Builder(new ContextThemeWrapper(FaeryChooseActivity.this, android.R.style.Theme_Holo_Light))
                            .setTitle("Faery Saved")
                            .setMessage(String.format("You have already saved the faery \"%s\" for this reading.\nAre you sure you want to save it again?", faerieChooseList.get(selectedIndex).name))
                            .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    saveFaeryToDB(faerieChooseList.get(selectedIndex), 1);
                                    Constants.showMessage(FaeryChooseActivity.this, String.format("\"%s\"\nTo your saved faeries", faerieChooseList.get(selectedIndex).name));
                                    hideMenuLayout();
                                }
                            })
                            .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // do nothing
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }

            }
            break;
        }
    }
    public int saveFaeryToDB(PlistInfo info, int force) {
        Dao dao = new Dao(FaeryChooseActivity.this);
        dao.open();
        int result = dao.addFavourFunc(info, force);
        dao.close();
        return result;
    }
    public void showMenuLayout() {
        menuLinearLayout.setAnimation(null);
        TranslateAnimation animation = new TranslateAnimation(0.0f, 0.0f, 0.0f, 0.0f - Constants.getDensity(FaeryChooseActivity.this) * 355);
        animation.setDuration(500);
        animation.setFillAfter(true);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                menuLinearLayout.setAnimation(null);
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) menuLinearLayout.getLayoutParams();
                params.setMargins(0, 0, 0, 0);
                menuLinearLayout.setLayoutParams(params);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        menuLinearLayout.startAnimation(animation);
        menuLinearLayout.setVisibility(View.VISIBLE);

    }
    public void hideMenuLayout() {
        menuLinearLayout.setAnimation(null);
        TranslateAnimation animation = new TranslateAnimation(0.0f, 0.0f, 0.0f, Constants.getDensity(FaeryChooseActivity.this) * 355);
        animation.setDuration(500);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) menuLinearLayout.getLayoutParams();
                params.setMargins(0, 0, 0, (int) (0 - Constants.getDensity(FaeryChooseActivity.this) * 355));
                menuLinearLayout.setLayoutParams(params);
                menuLinearLayout.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        menuLinearLayout.startAnimation(animation);
    }
}
