package com.wtl.common;

import android.annotation.TargetApi;
import android.os.Build;
import android.util.Log;
import android.webkit.ValueCallback;
import android.webkit.WebView;


public class SendDataToHtmlFucUtil {


	@TargetApi(Build.VERSION_CODES.KITKAT)
	public static void updateHtmlBleConnectStatus(String connect_status,WebView mWebView) {

		mWebView.evaluateJavascript("upConnectStatus("+connect_status+")", new ValueCallback<String>() {
			@Override
			public void onReceiveValue(String value) {
			}
		});


	}

	@TargetApi(Build.VERSION_CODES.KITKAT)
	public static void updateHtmlBleList(String bleList,WebView mWebView) {
		mWebView.evaluateJavascript("updateHtmlBleList("+bleList+")", new ValueCallback<String>() {
			@Override
			public void onReceiveValue(String value) {
			}
		});


	}

	@TargetApi(Build.VERSION_CODES.KITKAT)
	public static void updateHtmlBleScanBtnText(String text,WebView mWebView) {

		mWebView.evaluateJavascript("updateHtmlBleScanBtnText("+text+")", new ValueCallback<String>() {

			@Override
			public void onReceiveValue(String value) {
			}
		});


	}
}
