package com.faceoffaerie.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.faceoffaerie.R;
import com.faceoffaerie.activities.InfoActivity;
import com.faceoffaerie.contants.Constants;

public class InfoInstructionsFragment extends Fragment implements OnClickListener {

	RelativeLayout rootRelativeLayout;
	Button closeButton;
	ImageView instructionsImageView;
	ImageView whatdoesitmeanImageView;
	ImageView howtoImageView;
	ScrollView contentScrollView;

	View rootView;

	public static InfoInstructionsFragment newInstance(int index) {
		InfoInstructionsFragment f = new InfoInstructionsFragment();
		Bundle args = new Bundle();
		args.putInt("index", index);
		f.setArguments(args);
		return f;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
    }
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_info_instructions, container, false);
		initView(rootView);
		setListener();
		return rootView;
	}
	public void initView(View view) {
		rootRelativeLayout = (RelativeLayout) view.findViewById(R.id.rootRelativeLayout);
		closeButton = (Button) view.findViewById(R.id.closeButton);
		instructionsImageView = (ImageView) view.findViewById(R.id.instructionsImageView);
		whatdoesitmeanImageView = (ImageView) view.findViewById(R.id.whatdoesitmeanImageView);
		howtoImageView = (ImageView) view.findViewById(R.id.howtoImageView);
		contentScrollView = (ScrollView) view.findViewById(R.id.contentScrollView);

		LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) whatdoesitmeanImageView.getLayoutParams();
		params.height = (int) (833 * (Constants.getWidth(InfoActivity.getInstance()) - 100 * Constants.getDensity(InfoActivity.getInstance())) / 443);
		whatdoesitmeanImageView.setLayoutParams(params);

		params = (LinearLayout.LayoutParams) howtoImageView.getLayoutParams();
		params.height = (int) (1038 * (Constants.getWidth(InfoActivity.getInstance()) - 100 * Constants.getDensity(InfoActivity.getInstance())) / 462);
		howtoImageView.setLayoutParams(params);
	}
	@Override
	public void onPause() {
		super.onPause();
	}
	public void onResume() {
		super.onResume();
	}
	public void setListener() {
		closeButton.setOnClickListener(this);
	}
	public void onClick(View v) {
		switch(v.getId()) {
			case R.id.closeButton: {
				InfoActivity.getInstance().showInfoMenuFragment();
			}
			break;
		}
	}

}