package com.faceoffaerie.activities;

import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.faceoffaerie.R;
import com.faceoffaerie.adapter.SavedFaerieListAdapter;
import com.faceoffaerie.contants.PlistInfo;
import com.faceoffaerie.db.Dao;
import com.faceoffaerie.swipemenulistview.SwipeMenu;
import com.faceoffaerie.swipemenulistview.SwipeMenuCreator;
import com.faceoffaerie.swipemenulistview.SwipeMenuItem;
import com.faceoffaerie.swipemenulistview.SwipeMenuListView;

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
    SwipeMenuListView faeryListView;

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
        setSwipeListSetting();
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
    public void setSwipeListSetting() {
        SwipeMenuCreator creator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                // create "open" item ff3b30
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                deleteItem.setBackground(new ColorDrawable(0xffff3b30));
                // set item width
                deleteItem.setWidth(dp2px(90));
                // set item title
                deleteItem.setTitle("Delete");
                // set item title fontsize
                deleteItem.setTitleSize(18);
                // set item title font color
                deleteItem.setTitleColor(Color.WHITE);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };
        faeryListView.setMenuCreator(creator);

        // step 2. listener item click event
        faeryListView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                Dao dao = new Dao(SavedFaeriesActivity.this);
                dao.open();
                dao.removeFavourFunc(savedFaeries.get(position).PID);
                dao.close();
                initData();
                return false;
            }
        });

        // set SwipeListener
        faeryListView.setOnSwipeListener(new SwipeMenuListView.OnSwipeListener() {

            @Override
            public void onSwipeStart(int position) {
                // swipe start
            }

            @Override
            public void onSwipeEnd(int position) {
                // swipe end
            }
        });

        // set MenuStateChangeListener
        faeryListView.setOnMenuStateChangeListener(new SwipeMenuListView.OnMenuStateChangeListener() {
            @Override
            public void onMenuOpen(int position) {
            }

            @Override
            public void onMenuClose(int position) {
            }
        });

        // other setting
//		listView.setCloseInterpolator(new BounceInterpolator());
        faeryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });
        // test item long click
        faeryListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           int position, long id) {
                //Toast.makeText(getApplicationContext(), position + " long click", Toast.LENGTH_SHORT).show();

                return false;
            }
        });
    }
    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }
}
