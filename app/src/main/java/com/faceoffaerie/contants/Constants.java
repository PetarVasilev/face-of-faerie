/**
 * manage static variables and shared preferences
 */
package com.faceoffaerie.contants;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.view.ContextThemeWrapper;

import com.faceoffaerie.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Constants {

	public final static int MSG_SUCCESS = 0;
	public final static int MSG_FAIL = 1;
	private final static String packageName = "com.faceoffaerie";

	public static void setWidth(Context context, int width) {
		SharedPreferences.Editor editor = context.getSharedPreferences(packageName, 0).edit();
		editor.putInt("width", width);
		editor.commit();
	}

	public static int getWidth(Context context) {
		SharedPreferences pref = context.getSharedPreferences(packageName, 0);
		return pref.getInt("width", 720);
	}

	public static void setHeight(Context context, int height) {
		SharedPreferences.Editor editor = context.getSharedPreferences(packageName, 0).edit();
		editor.putInt("height", height);
		editor.commit();
	}
	public static int getHeight(Context context) {
		SharedPreferences pref = context.getSharedPreferences(packageName, 0);
		return pref.getInt("height", 1280);

	}
	public static void setDensity(Context context, float density) {
		SharedPreferences.Editor editor = context.getSharedPreferences(packageName, 0).edit();
		editor.putFloat("density", density);
		editor.commit();
	}
	public static int[] animArray = { R.drawable.animated_symbol0, R.drawable.animated_symbol1,
			R.drawable.animated_symbol2, R.drawable.animated_symbol3, R.drawable.animated_symbol4,
			R.drawable.animated_symbol5, R.drawable.animated_symbol6, R.drawable.animated_symbol7,
			R.drawable.animated_symbol8, R.drawable.animated_symbol9, R.drawable.animated_symbol10,
			R.drawable.animated_symbol11, R.drawable.animated_symbol12, R.drawable.animated_symbol13,
			R.drawable.animated_symbol14, R.drawable.animated_symbol15, R.drawable.animated_symbol16,
			R.drawable.animated_symbol17, R.drawable.animated_symbol18, R.drawable.animated_symbol19,
			R.drawable.animated_symbol20, R.drawable.animated_symbol21, R.drawable.animated_symbol22,
			R.drawable.animated_symbol23, R.drawable.animated_symbol24, R.drawable.animated_symbol25,
			R.drawable.animated_symbol26, R.drawable.animated_symbol27, R.drawable.animated_symbol28,
			R.drawable.animated_symbol29, R.drawable.animated_symbol30, R.drawable.animated_symbol31,
			R.drawable.animated_symbol32, R.drawable.animated_symbol33, R.drawable.animated_symbol34,
			R.drawable.animated_symbol35, R.drawable.animated_symbol36, R.drawable.animated_symbol37,
			R.drawable.animated_symbol38, R.drawable.animated_symbol39, R.drawable.animated_symbol40,
			R.drawable.animated_symbol41, R.drawable.animated_symbol42, R.drawable.animated_symbol43,
			R.drawable.animated_symbol44, R.drawable.animated_symbol45, R.drawable.animated_symbol46,
			R.drawable.animated_symbol47, R.drawable.animated_symbol48, R.drawable.animated_symbol49,
			R.drawable.animated_symbol50, R.drawable.animated_symbol51, R.drawable.animated_symbol52,
			R.drawable.animated_symbol53, R.drawable.animated_symbol54, R.drawable.animated_symbol55,
			R.drawable.animated_symbol56, R.drawable.animated_symbol57, R.drawable.animated_symbol58,
			R.drawable.animated_symbol59, R.drawable.animated_symbol60, R.drawable.animated_symbol61,
			R.drawable.animated_symbol62 };
	public static float getDensity(Context context) {
		SharedPreferences pref = context.getSharedPreferences(packageName, 0);
		return pref.getFloat("density", 1.0f);

	}
	public static String readFaerieChooseFromAssetsPlist(Context context) {
		StringBuffer sb = new StringBuffer();
		BufferedReader br=null;
		try {
			br = new BufferedReader(new InputStreamReader(context.getAssets().open("faerie_choose.plist")));
			String temp;
			while ((temp = br.readLine()) != null)
				sb.append(temp);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				br.close(); // stop reading
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return sb.toString();
	}
	public static String readYouChooseFromAssetsPlist(Context context) {
		StringBuffer sb = new StringBuffer();
		BufferedReader br=null;
		try {
			br = new BufferedReader(new InputStreamReader(context.getAssets().open("you_choose.plist")));
			String temp;
			while ((temp = br.readLine()) != null)
				sb.append(temp);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				br.close(); // stop reading
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return sb.toString();
	}
	public static void showMessage(Context context, String msg) {
		new AlertDialog.Builder(new ContextThemeWrapper(context, android.R.style.Theme_Holo_Light))
				.setTitle("Saved!")
				.setMessage(msg)
				.setCancelable(false)
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
					}
				}).create().show();
	}
	public static void showMessage(Context context, String title, String msg) {
		new AlertDialog.Builder(new ContextThemeWrapper(context, android.R.style.Theme_Holo_Light))
				.setTitle(title)
				.setMessage(msg)
				.setCancelable(false)
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
					}
				}).create().show();
	}
}
