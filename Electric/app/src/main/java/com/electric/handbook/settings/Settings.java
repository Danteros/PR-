/**
 * 
 */
package com.electric.handbook.settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * @author Dmitry Tankovich date: 04.06.2014 time: 19:50:32
 */
public class Settings {

	public static final String PREF_NAME = "electric_pref";

	private static SharedPreferences preferences;
	private static Editor editor;
	
	private static final String IS_BUYED = "is_buyed";

    public static void setIsBuyed(boolean value) {
		editor.putBoolean(IS_BUYED, value);
		editor.commit();
	}

	public static boolean isBuyed() {
		try {
			return preferences.getBoolean(IS_BUYED, false);
		} catch (NullPointerException e) {
			return false;
		}
	}

	public static void init(Context ctx) {
		preferences = ctx.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
		editor = preferences.edit();
	}

}
