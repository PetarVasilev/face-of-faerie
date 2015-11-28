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
import android.widget.ScrollView;

import com.faceoffaerie.R;
import com.faceoffaerie.activities.InfoActivity;

public class InfoMenuFragment extends Fragment implements OnClickListener {

	RelativeLayout rootRelativeLayout;
	Button homeButton;
	ImageView instructionsImageView;
	ImageView creditsImageView;
	ImageView rateImageView;
	ImageView shareImageView;
	ImageView moreImageView;
	ImageView pathWaysToFaeryImageView;
	ImageView faeriesTalesImageView;
	ImageView trollsBookImageView;
	ImageView lessonsLearnedImageView;
	ImageView wendyAFAImageView;
	ImageView brianAFAImageView;
	ImageView facebookOfImageView;
	ImageView twitterOfImageView;
	ImageView pinterestOfImageView;
	ImageView worldOfImageView;
	ImageView amazonStoreUKImageView;
	ImageView facebookBBPImageView;
	ImageView twitterBPPImageView;
	ImageView pinterestBBPImageView;
	ImageView bbpCreationsImageView;
	ImageView amazonStoreUSImageView;
	ScrollView contentScrollView;

	View rootView;
	public static InfoMenuFragment newInstance(int index) {
		InfoMenuFragment f = new InfoMenuFragment();
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
		rootView = inflater.inflate(R.layout.fragment_info_menu, container, false);
		initView(rootView);
		setListener();
		return rootView;
	}
	public void initView(View view) {
		rootRelativeLayout = (RelativeLayout) view.findViewById(R.id.rootRelativeLayout);
		homeButton = (Button) view.findViewById(R.id.homeButton);
		instructionsImageView = (ImageView) view.findViewById(R.id.instructionsImageView);
		creditsImageView = (ImageView) view.findViewById(R.id.creditsImageView);
		rateImageView = (ImageView) view.findViewById(R.id.rateImageView);
		shareImageView = (ImageView) view.findViewById(R.id.shareImageView);
		moreImageView = (ImageView) view.findViewById(R.id.moreImageView);
		pathWaysToFaeryImageView = (ImageView) view.findViewById(R.id.pathWaysToFaeryImageView);
		faeriesTalesImageView = (ImageView) view.findViewById(R.id.faeriesTalesImageView);
		trollsBookImageView = (ImageView) view.findViewById(R.id.trollsBookImageView);
		lessonsLearnedImageView = (ImageView) view.findViewById(R.id.lessonsLearnedImageView);
		wendyAFAImageView = (ImageView) view.findViewById(R.id.wendyAFAImageView);
		brianAFAImageView = (ImageView) view.findViewById(R.id.brianAFAImageView);
		facebookOfImageView = (ImageView) view.findViewById(R.id.facebookOfImageView);
		twitterOfImageView = (ImageView) view.findViewById(R.id.twitterOfImageView);
		pinterestOfImageView = (ImageView) view.findViewById(R.id.pinterestOfImageView);
		worldOfImageView = (ImageView) view.findViewById(R.id.worldOfImageView);
		amazonStoreUKImageView = (ImageView) view.findViewById(R.id.amazonStoreUKImageView);
		facebookBBPImageView = (ImageView) view.findViewById(R.id.facebookBBPImageView);
		twitterBPPImageView = (ImageView) view.findViewById(R.id.twitterBPPImageView);
		pinterestBBPImageView = (ImageView) view.findViewById(R.id.pinterestBBPImageView);
		bbpCreationsImageView = (ImageView) view.findViewById(R.id.bbpCreationsImageView);
		amazonStoreUSImageView = (ImageView) view.findViewById(R.id.amazonStoreUSImageView);
		contentScrollView = (ScrollView) view.findViewById(R.id.contentScrollView);

		if (InfoActivity.getInstance().fromFaeryChoose) {
			homeButton.setBackgroundResource(R.drawable.close);
		} else {
			homeButton.setBackgroundResource(R.drawable.home);
		}
	}
	@Override
	public void onPause() {
		super.onPause();
	}
	public void onResume() {
		super.onResume();
	}
	public void setListener() {
		homeButton.setOnClickListener(this);
		instructionsImageView.setOnClickListener(this);
		creditsImageView.setOnClickListener(this);
		rateImageView.setOnClickListener(this);
		shareImageView.setOnClickListener(this);
		pathWaysToFaeryImageView.setOnClickListener(this);
		faeriesTalesImageView.setOnClickListener(this);
		trollsBookImageView.setOnClickListener(this);
		lessonsLearnedImageView.setOnClickListener(this);
		wendyAFAImageView.setOnClickListener(this);
		brianAFAImageView.setOnClickListener(this);
		facebookOfImageView.setOnClickListener(this);
		twitterOfImageView.setOnClickListener(this);
		pinterestOfImageView.setOnClickListener(this);
		worldOfImageView.setOnClickListener(this);
		amazonStoreUKImageView.setOnClickListener(this);
		facebookBBPImageView.setOnClickListener(this);
		twitterBPPImageView.setOnClickListener(this);
		pinterestBBPImageView.setOnClickListener(this);
		bbpCreationsImageView.setOnClickListener(this);
		amazonStoreUSImageView.setOnClickListener(this);
	}
	public void onClick(View v) {
		switch(v.getId()) {
			case R.id.homeButton: {
				InfoActivity.getInstance().finish();
			}
			break;
			case R.id.instructionsImageView: {
				InfoActivity.getInstance().showInstructionsFragment();
			}
			break;
			case R.id.creditsImageView: {
				InfoActivity.getInstance().showCreditsFragment();
			}
			break;
			case R.id.rateImageView: {
				Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://itunes.apple.com/us/app/faces-of-faerie/id874271272?ls=1&mt=8"));
				InfoActivity.getInstance().startActivity(intent);
			}
			break;
			case R.id.shareImageView: {
				Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://itunes.apple.com/us/app/faces-of-faerie/id874271272?ls=1&mt=8"));
				InfoActivity.getInstance().startActivity(intent);
			}
			break;
			case R.id.pathWaysToFaeryImageView: {
				Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://itunes.apple.com/us/app/froud-meditations-pathways/id563141623?ls=1&mt=8"));
				InfoActivity.getInstance().startActivity(intent);
			}
			break;
			case R.id.faeriesTalesImageView: {
				Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.abramsbooks.com/Books/Brian_Froud_s_Faeries__Tales-9781419713866.html"));
				InfoActivity.getInstance().startActivity(intent);
			}
			break;
			case R.id.trollsBookImageView: {
				Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://itunes.apple.com/us/book/trolls/id568842229"));
				InfoActivity.getInstance().startActivity(intent);
			}
			break;
			case R.id.lessonsLearnedImageView: {
				Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://vimeo.com/97022699"));
				InfoActivity.getInstance().startActivity(intent);
			}
			break;
			case R.id.wendyAFAImageView: {
				Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://afanyc.com/frouds/wendy-froud/"));
				InfoActivity.getInstance().startActivity(intent);
			}
			break;
			case R.id.brianAFAImageView: {
				Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://afanyc.com/frouds/trolls-exhibition-art-brian-froud/#!prettyPhoto"));
				InfoActivity.getInstance().startActivity(intent);
			}
			break;
			case R.id.facebookOfImageView: {
				Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/RealmofFroud?fref=ts"));
				InfoActivity.getInstance().startActivity(intent);
			}
			break;
			case R.id.twitterOfImageView: {
				Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/RealmofFroud"));
				InfoActivity.getInstance().startActivity(intent);
			}
			break;
			case R.id.pinterestOfImageView: {
				Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.pinterest.com/worldoffroud/pins/"));
				InfoActivity.getInstance().startActivity(intent);
			}
			break;
			case R.id.worldOfImageView: {
				Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://worldoffroud.com/"));
				InfoActivity.getInstance().startActivity(intent);
			}
			break;
			case R.id.amazonStoreUKImageView: {
				Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://astore.amazon.co.uk/woroffro-21"));
				InfoActivity.getInstance().startActivity(intent);
			}
			break;
			case R.id.facebookBBPImageView: {
				Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/pages/Big-Ben-Parliament-Creations-Inc-BBP-Creations/250030905017668"));
				InfoActivity.getInstance().startActivity(intent);
			}
			break;
			case R.id.twitterBPPImageView: {
				Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/BBPCreations"));
				InfoActivity.getInstance().startActivity(intent);
			}
			break;
			case R.id.pinterestBBPImageView: {
				Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.pinterest.com/bbpcreations/"));
				InfoActivity.getInstance().startActivity(intent);
			}
			break;
			case R.id.bbpCreationsImageView: {
				Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.bbpcreations.com/"));
				InfoActivity.getInstance().startActivity(intent);
			}
			break;
			case R.id.amazonStoreUSImageView: {
				Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://astore.amazon.com/woroffro-20"));
				InfoActivity.getInstance().startActivity(intent);
			}
			break;
		}
	}

}