package com.faceoffaerie.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;

import com.faceoffaerie.R;
import com.faceoffaerie.fragments.InfoCreditsFragment;
import com.faceoffaerie.fragments.InfoInstructionsFragment;
import com.faceoffaerie.fragments.InfoMenuFragment;

import butterknife.ButterKnife;

public class InfoActivity extends BaseActivity implements OnClickListener{

    private final int MENUFRAGMENT = 0;
    private final int INSTRUCTIONSFRAGMENT = 1;
    private final int CREDITSFRAGMENT = 2;

    private static InfoActivity infoActivity;
    private FragmentManager fragmentManager = null;
    private FragmentTransaction fragmentTransaction = null;
    public InfoMenuFragment infoMenuFragment = null;
    public InfoInstructionsFragment infoInstructionsFragment = null;
    public InfoCreditsFragment infoCreditsFragment = null;

    public static InfoActivity getInstance() {
        return infoActivity;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        setLayoutId(this, R.layout.activity_info_menu);
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        infoActivity = this;

        initView();
        setListener();
        initData();
    }

    public void initView() {
        ButterKnife.inject(this);
    }

    public void setListener() {

    }

    public void initData() {
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        infoMenuFragment = InfoMenuFragment.newInstance(MENUFRAGMENT);
        infoMenuFragment.setRetainInstance(true);
        infoInstructionsFragment = InfoInstructionsFragment.newInstance(INSTRUCTIONSFRAGMENT);
        infoInstructionsFragment.setRetainInstance(true);
        infoCreditsFragment = InfoCreditsFragment.newInstance(CREDITSFRAGMENT);
        infoCreditsFragment.setRetainInstance(true);

        fragmentTransaction.add(R.id.rootFrameLayout, infoMenuFragment);
        fragmentTransaction.add(R.id.rootFrameLayout, infoInstructionsFragment);
        fragmentTransaction.add(R.id.rootFrameLayout, infoCreditsFragment);
        fragmentTransaction.commit();

    }
    public void onResume() {
        super.onResume();
        showInfoMenuFragment();
    }
    public void onClick(View v) {
        int viewId = v.getId();
        switch (viewId) {

        }
    }
    public void showInfoMenuFragment() {
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.show(infoMenuFragment);
        fragmentTransaction.hide(infoInstructionsFragment);
        fragmentTransaction.hide(infoCreditsFragment);
        fragmentTransaction.commit();
    }
    public void showInstructionsFragment() {
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.show(infoInstructionsFragment);
        fragmentTransaction.hide(infoMenuFragment);
        fragmentTransaction.hide(infoCreditsFragment);
        fragmentTransaction.commit();
    }
    public void showCreditsFragment() {
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.show(infoCreditsFragment);
        fragmentTransaction.hide(infoInstructionsFragment);
        fragmentTransaction.hide(infoMenuFragment);
        fragmentTransaction.commit();
    }
}
