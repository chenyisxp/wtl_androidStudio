package com.wtl.html5;

import java.util.ArrayList;
import java.util.List;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.wtl.MainActivity;
import com.wtl.R;
import com.wtl.bean.WeldInfo;
import com.wtl.common.Data;
import com.wtl.common.DateUtil;
import com.wtl.common.SQLiteDBUtil;
import com.wtl.common.SaveUtil;

public class HtmlMainActivity extends Activity {
	public static final String TAG = "HtmlMainActivity";
	private static String nowUrl="";
	private WebView mWebView;
	private static Context context;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    context =getApplicationContext();
	    setContentView(R.layout.activity_html);
	    mWebView = (WebView) findViewById(R.id.webView);
//	    mWebView.loadDataWithBaseURL(null,"file:///android_asset/test.html", "text/html", "gbk",null);
//
//	    mWebView.loadUrl("file:///android_asset/JavaScript.html");
//	    mWebView.loadUrl("file:///android_asset/test2.html");
//	    mWebView.loadUrl("file:///android_asset/test.html");
//	    mWebView.loadUrl("file:///android_asset/testJrange.html");
//	    mWebView.loadUrl("file:///android_asset/index.html");
	    mWebView.loadUrl("http://192.168.1.5:8081/#/");
	    
	    WebSettings webSettings = mWebView.getSettings();
	    webSettings.setJavaScriptEnabled(true);
	    webSettings.setDefaultTextEncodingName("gbk");
	    mWebView.addJavascriptInterface(new JsInteration(), "android");
	    mWebView.setWebViewClient(new WebViewClient() {
	        @Override
	        public boolean shouldOverrideUrlLoading(WebView view, String url) {
	        	 Toast.makeText(HtmlMainActivity.this,Data.getA(),1).show();
	        	 nowUrl=url;
//	            if (url.equals("file:///android_asset/test2.html")) {
//	                Log.e(TAG, "shouldOverrideUrlLoading: " + url);
//	                startActivity(new Intent(SecondActivity.this,MainActivity.class));
//	                return true;
//	            } else {
	                mWebView.loadUrl(url);
	                return false;
//	            }
	        }
		    public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
		        return onJsAlert(view, url, message, result);
		    }
	    });
	    mWebView.setWebChromeClient(new WebChromeClient());

	    webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);

	}

	@TargetApi(Build.VERSION_CODES.KITKAT)
	public void onClick(View v) {
	 
	    mWebView.evaluateJavascript("sum(1,2)", new ValueCallback<String>() {
	        @Override
	        public void onReceiveValue(String value) {
	            Log.e(TAG, "onReceiveValue value=" + value);
	        }
	    });
	}
	public class JsInteration {
	 
	    @JavascriptInterface
	    public String back() {
//	        return "hello world";
	    	return getResources().getString(R.string.hello_world);
	    }
	    @JavascriptInterface
	    public String getDate() {
	        return "20:29:20";
	    }

	    @JavascriptInterface
	    public String setSettingInfo(String key,String value) {
	    	return SaveUtil.updateDataBykey(context, key, value);
	    }

	    @JavascriptInterface
	    public String getSettingInfo(String key) {
	        return  SaveUtil.getDataBykey(context,key);
	    }

	    @JavascriptInterface
	    public void testSqliteset() {

			SQLiteDBUtil db = new SQLiteDBUtil(getApplicationContext());
			SQLiteDatabase sd = db.getWritableDatabase();
				 //
				 ContentValues cValue = new ContentValues();

				 Log.v("aaaa", DateUtil.getCurrentDateyyyyMMddHHmmss());
				 cValue.put("cre_tm",DateUtil.getCurrentDateyyyyMMddHHmmss());
				 cValue.put("up_tm",DateUtil.getCurrentDateyyyyMMddHHmmss());
				 //insert()
				 sd.insert("weldInfo", null, cValue);
				 db.close();
	    }
	    //
	    @JavascriptInterface
	    public String testSqliteget() {
			String s = "";
			SQLiteDBUtil db = new SQLiteDBUtil(getApplicationContext());
			SQLiteDatabase sd = db.getReadableDatabase();
			String sql = "select id,machinedId,machinedName,weldType,dataType,setInfo,noteInfo,weldBeginTime"
								+ ",weldEndTime,weldConnectTime,memo,rec_stat,creator,modifier,cre_tm,up_tm"
								+ " from weldInfo  ORDER BY cre_tm DESC";
			Cursor cursor = sd.rawQuery(sql, null);
			WeldInfo weldInfo =new WeldInfo();
			List<WeldInfo> wldlist =new ArrayList<WeldInfo>();
			while (cursor.moveToNext()) {
				weldInfo.setId(cursor.getInt(0));
				weldInfo.setMachinedId(cursor.getString(1));
				weldInfo.setMachinedName(cursor.getString(2));
				weldInfo.setWeldType(cursor.getString(3));
				weldInfo.setDataType(cursor.getString(4));
				weldInfo.setSetInfo(cursor.getString(5));
				weldInfo.setNoteInfo(cursor.getString(6));
				weldInfo.setWeldBeginTime(cursor.getString(7));
				weldInfo.setWeldEndTime(cursor.getString(8));
				weldInfo.setWeldConnectTime(cursor.getString(9));
				weldInfo.setMemo(cursor.getString(10));
				weldInfo.setRec_stat(cursor.getString(11));
				weldInfo.setCreator(cursor.getString(12));
				weldInfo.setModifier(cursor.getString(13));
				weldInfo.setCre_tm(cursor.getString(14));
				weldInfo.setUp_tm(cursor.getString(15));
//				s += "id:" + cursor.getInt(0) + "\tmachinedId:" + cursor.getString(1)
//						+ "\t machinedName:" + cursor.getString(2) + "\t setInfo:" + cursor.getString(5) + "\n";
//				s+=weldInfo.toString();
				wldlist.add(weldInfo);
			}
			Log.w("aaaaa", s);
			db.close();
			cursor.close();
			return wldlist.toString();
	    }
	    //
	    @JavascriptInterface
	    public String testSqliteupdate() {
	    	//
			String s = "";
			SQLiteDBUtil db = new SQLiteDBUtil(getApplicationContext());
			SQLiteDatabase sd = db.getReadableDatabase();
			//
			ContentValues values = new ContentValues();  
			//
			values.put("setInfo","{'current':'10','valatge':'20'}");  
			//
			String whereClause = "id=?";  
			//
			String[] whereArgs={String.valueOf(1)};  
			//
//			sd.update("weldInfo",values,whereClause,whereArgs);
			sd.update("weldInfo",values,null,null);  
			db.close();
			return s;
	    }
	    @JavascriptInterface
	    public String testSqlitedelete() {
	    	//
			String s = "";
			SQLiteDBUtil db = new SQLiteDBUtil(getApplicationContext());
			SQLiteDatabase sd = db.getReadableDatabase();
			String sql = "delete from weldInfo";
			sd.execSQL(sql);
			db.close();
			return s;
	    }
	}
	
//	@Override
//	public boolean onKeyDown(int keyCode, KeyEvent event)  {
//	     if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
//	      
//	        return true;
//	    }
//
//	    return super.onKeyDown(keyCode, event);
//	}
	@Override
	public void onBackPressed() {
			//
		 	Toast.makeText(HtmlMainActivity.this,Data.getA(),1).show();
		 	if(nowUrl.equals("file:///android_asset/test2.html")){
		 		 nowUrl ="file:///android_asset/test.html";
		 		 mWebView.loadUrl("file:///android_asset/test.html");
		 	}else if(nowUrl.equals("file:///android_asset/test.html")){
		 		  startActivity(new Intent(HtmlMainActivity.this,MainActivity.class));
		 	}
			return;
	}
	
}
