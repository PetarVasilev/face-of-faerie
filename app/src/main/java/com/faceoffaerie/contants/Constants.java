/**
 * manage static variables and shared preferences
 */
package com.faceoffaerie.contants;

import android.content.Context;
import android.content.SharedPreferences;

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

	public static float getDensity(Context context) {
		SharedPreferences pref = context.getSharedPreferences(packageName, 0);
		return pref.getFloat("density", 1.0f);

	}

}
