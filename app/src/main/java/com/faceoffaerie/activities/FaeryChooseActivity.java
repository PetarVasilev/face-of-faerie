package com.faceoffaerie.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.ContextThemeWrapper;
import android.view.MotionEvent;
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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
    private Handler mHandler = null;
    private boolean flag = true;
    private int index = 0;
    private int intervalTime = 80;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setLayoutId(this, R.layout.activity_faery_choose);
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        initView();
        setListener();
        initData();

        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {

                super.handleMessage(msg);

                switch (msg.what) {
                    case Constants.MSG_SUCCESS: {
                        int i = msg.arg1;
                        symbolImageView.setBackgroundResource(Constants.animArray[i]);
                    }
                    break;
                }
            }

        };
    }

    public void initView() {
        ButterKnife.inject(this);

        Typeface typeface = Typeface.createFromAsset(getAssets(), "BarbedorSCTMed.ttf");
        titleTextView.setTypeface(typeface);
        faerieNameTextView.setTypeface(typeface);
        faerieReadingTextView.setTypeface(typeface);

        faerieRelativeLayout.setVisibility(View.GONE);
        menuLinearLayout.setVisibility(View.GONE);

        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) symbolImageView.getLayoutParams();
        params.width = Constants.getWidth(this);
        params.height = params.width;
        symbolImageView.setLayoutParams(params);
    }
    public void showAnimation() {
        flag = true;
        intervalTime = 80;
        AnimationTask animationTask = new AnimationTask();
        animationTask.execute();
    }
    public void setListener() {
        symbolImageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    intervalTime = 30;
                    return true;
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    flag = false;
                    return false;
                }
                return false;
            }
        });
    }
    public void showFaerieChoose(int index) {
        faerieRelativeLayout.setVisibility(View.VISIBLE);
        PlistInfo selectedInfo = faerieChooseList.get(index);
        faerieNameTextView.setText(selectedInfo.name);
        faerieReadingTextView.setText(selectedInfo.reading);
        InputStream ims = null;
        try {
            ims = getAssets().open(String.format("faerie%d.png", index));
            Drawable d = Drawable.createFromStream(ims, null);
            faerieImageView.setImageDrawable(d);
        } catch (Exception e) {}
        finally {
            if (ims != null) {
                try {
                    ims.close();
                } catch (IOException e) {
                    // NOOP
                }
            }
        }
    }
    public void initData() {
        String xml = Constants.readFaerieChooseFromAssetsPlist(this);
        ParsePlistParser pp = new ParsePlistParser();
        faerieChooseList = pp.parsePlist(xml);
        showAnimation();
    }
    public void onDestroy() {
        super.onDestroy();
        flag = false;
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
                showAnimation();
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
                hideMenuLayout();
            }
            break;
            case R.id.facebookMenuImageView: {
                String text = String.format("This is the message the faery \"%s\" has for me.\n %s\n\n You can get the new World of Froud app at:\n https://itunes.apple.com/us/app/faces-of-faerie/id874271272?ls=1&mt=8", faerieChooseList.get(selectedIndex).name, faerieChooseList.get(selectedIndex).reading);
                shareToFacebook(FaeryChooseActivity.this, text, Uri.parse(prepareSdcardImage()));
                hideMenuLayout();
            }
            break;
            case R.id.twitterMenuImageView: {
                String text = "Faces of Faerie!\n You can get the new World of Froud app at:\n https://itunes.apple.com/us/app/faces-of-faerie/id874271272?ls=1&mt=8\"";
                shareToTwitter(FaeryChooseActivity.this, text, Uri.parse(prepareSdcardImage()));
                hideMenuLayout();
            }
            break;
            case R.id.emailMenuImageView: {
                String subject = String.format("Check out my Faery for Today: %s", faerieChooseList.get(selectedIndex).name);
                String text = String.format("This is the message the faery \"%s\" has for me.\n %s\n\n You can get the new World of Froud app at:\n https://itunes.apple.com/us/app/faces-of-faerie/id874271272?ls=1&mt=8", faerieChooseList.get(selectedIndex).name, faerieChooseList.get(selectedIndex).reading);
                shareToEmail(FaeryChooseActivity.this, subject, text, Uri.parse(prepareSdcardImage()));
                hideMenuLayout();
            }
            break;
            case R.id.messageMenuImageView: {
                String text = String.format("This is the message the faery \"%s\" has for me.\n %s\n\n You can get the new World of Froud app at:\n https://itunes.apple.com/us/app/faces-of-faerie/id874271272?ls=1&mt=8", faerieChooseList.get(selectedIndex).name, faerieChooseList.get(selectedIndex).reading);
                shareToSMS(FaeryChooseActivity.this, text, Uri.parse(prepareSdcardImage()));
                hideMenuLayout();
            }
            break;

        }
    }
    private String prepareSdcardImage() {
        String faeriePath = String.format("faerie%d.png", selectedIndex);
        return "file://" + copyAssets(faeriePath);
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
    private String copyAssets(String assetFileName) {
        AssetManager assetManager = getAssets();
        File outFile = null;
        if (assetFileName != null) {
            InputStream in = null;
            OutputStream out = null;
            try {
                in = assetManager.open(assetFileName);
                File directory = new File(Environment.getExternalStorageDirectory() + File.separator + getResources().getString(R.string.app_name));
                if (!directory.exists())
                    directory.mkdir();
                outFile = new File(directory, assetFileName);
                out = new FileOutputStream(outFile);
                copyFile(in, out);
            } catch(IOException e) {
            }
            finally {
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException e) {
                        // NOOP
                    }
                }
                if (out != null) {
                    try {
                        out.close();
                    } catch (IOException e) {
                        // NOOP
                    }
                }
            }
        }
        if (outFile != null) {
            return outFile.getAbsolutePath();
        } else {
            return null;
        }
    }
    private void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while((read = in.read(buffer)) != -1){
            out.write(buffer, 0, read);
        }
    }
    private class AnimationTask extends AsyncTask<String, Void, String> {
        private int count;
        @Override
        protected String doInBackground(String... params) {
            count = 0;
            while (flag) {
                if (intervalTime == 30)
                    count++;
                else
                    count = 0;
                if (count > 180)
                    flag = false;
                index++;
                if (index > 61)
                    index = 0;
                try {
                    Message msg = new Message();
                    msg.what = Constants.MSG_SUCCESS;
                    msg.arg1 = index;
                    mHandler.sendMessage(msg);
                    Thread.sleep(intervalTime);
                } catch (Exception e) {
                }
            }
            return null;
        }
        @Override
        protected void onPostExecute(String result) {
            if (faerieChooseList != null && faerieChooseList.size() > 0) {
                Random random = new Random(System.currentTimeMillis());
                selectedIndex = random.nextInt(faerieChooseList.size());
                if (selectedIndex == 0)
                    selectedIndex = faerieChooseList.size();
                showFaerieChoose(selectedIndex);
            }
        }
        @Override
        protected void onPreExecute() {
        }
        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }
}
