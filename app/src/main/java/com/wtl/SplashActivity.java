package com.wtl;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.video.CustomVideoView;

public class SplashActivity extends Activity{
	 private static final int sleepTime = 4000;
	 private CustomVideoView mVideoView;
	    @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_splash);
//	        ViewUtils.inject(this);
	        mVideoView = (CustomVideoView) findViewById(R.id.welcome_videoview);
	        //
	        mVideoView.setVideoURI(Uri.parse("android.resource://" + this.getPackageName() + "/" + R.raw.app_open));
	        //
	        mVideoView.start();
	        Treed();
	    }
	public void Treed(){
	    
		  new Thread(new Runnable() { 
		      public void run() {    
		        long start = System.currentTimeMillis(); 
		        long costTime = System.currentTimeMillis() - start; 
		          if (sleepTime - costTime > 0) { 
		            try { 
		              Thread.sleep(sleepTime - costTime); 
		            } catch (InterruptedException e) { 
		              e.printStackTrace(); 
		            } 
		          } 
		          startActivity(new Intent(SplashActivity.this, MainActivity.class)); 
		          finish(); 
		      } 
		    }).start(); 
	}
	  
}
