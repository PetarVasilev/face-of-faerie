package com.faceoffaerie.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.faceoffaerie.R;
import com.faceoffaerie.activities.InfoActivity;

public class InfoCreditsFragment extends Fragment implements OnClickListener {

	RelativeLayout rootRelativeLayout;
	Button closeButton;
	ImageView worldOfImageView;
	ImageView bbpCreationsImageView;
	ImageView lillToddImageView;

	View rootView;

	public static InfoCreditsFragment newInstance(int index) {
		InfoCreditsFragment f = new InfoCreditsFragment();
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
		rootView = inflater.inflate(R.layout.fragment_info_credits, container, false);
		initView(rootView);
		setListener();
		return rootView;
	}
	public void initView(View view) {
		rootRelativeLayout = (RelativeLayout) view.findViewById(R.id.rootRelativeLayout);
		closeButton = (Button) view.findViewById(R.id.closeButton);
		worldOfImageView = (ImageView) view.findViewById(R.id.worldOfImageView);
		bbpCreationsImageView = (ImageView) view.findViewById(R.id.bbpCreationsImageView);
		lillToddImageView = (ImageView) view.findViewById(R.id.lillToddImageView);
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
		worldOfImageView.setOnClickListener(this);
		bbpCreationsImageView.setOnClickListener(this);
		lillToddImageView.setOnClickListener(this);
	}
	public void onClick(View v) {
		switch(v.getId()) {
			case R.id.closeButton: {
				InfoActivity.getInstance().showInfoMenuFragment();
			}
			break;
			case R.id.worldOfImageView: {
				Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.worldoffroud.com/about/index.php"));
				InfoActivity.getInstance().startActivity(intent);
			}
			break;
			case R.id.bbpCreationsImageView: {
				Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.bbpcreations.com/BBP-Creations-Company-iPhone-iPad-Application-Development.php"));
				InfoActivity.getInstance().startActivity(intent);
			}
			break;
			case R.id.lillToddImageView: {
				Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://lilliantoddjones.com/Lillian_Rhiannon/News.html"));
				InfoActivity.getInstance().startActivity(intent);
			}
			break;
		}
	}

}