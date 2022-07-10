package com.wtl.common;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.content.SharedPreferences;

public class SaveUtil {
    private final static String PREFS_NAME = "MyPrefsFile";
	public static Map<String, String> getSettingData(){
		Map<String, String> rstmap=new HashMap<String, String>();
		
		return rstmap;
	}

	public static String getDataBykey(Context context,String key){
		SharedPreferences setInfo = context.getSharedPreferences(PREFS_NAME, context.MODE_PRIVATE);
        String info = setInfo.getString(key, null);
		return info;
	}

	public static String updateDataBykey(Context context,String key,String value){
		SharedPreferences setInfo = context.getSharedPreferences(PREFS_NAME, context.MODE_PRIVATE );
        SharedPreferences.Editor editor = setInfo.edit();

        editor.putString(key, value);
        editor.commit();

        String info = setInfo.getString(key, null);
        return info;
	}
}
