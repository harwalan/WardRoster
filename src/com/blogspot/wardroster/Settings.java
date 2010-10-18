package com.blogspot.wardroster;

import com.flurry.android.FlurryAgent;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.LinearLayout;

public class Settings extends Activity {
    public static final String PREFS_NAME = "WardRoster";
    private boolean membership_mode = false;
    public static final boolean MLS_MODE = true;

	@Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
    	setContentView(R.layout.settings); 
    	restorePreferences();
    	
		SharedPreferences settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
		boolean familyphoto = settings.getBoolean("familyphoto", false);
		boolean individualphoto = settings.getBoolean("individualphoto", false);
		boolean instantdial = settings.getBoolean("instantdial", false);
    	
		CheckBox fp = (CheckBox)findViewById(R.id.familyphoto);
		fp.setChecked(familyphoto);
    	fp.setOnClickListener(mFamilyPhotoClick);
		CheckBox ip = (CheckBox)findViewById(R.id.individualphoto);
		ip.setChecked(individualphoto);
    	ip.setOnClickListener(mIndividualPhotoClick);
		CheckBox id = (CheckBox)findViewById(R.id.instantdial);
		id.setChecked(instantdial);
    	id.setOnClickListener(mInstantDialClick);
    	if(!membership_mode){
    		LinearLayout ll = (LinearLayout)findViewById(R.id.LinearLayout01);
    		ll.removeView(ip);
    	}
	}
	private void setFamilyPhoto(){
		CheckBox cb = (CheckBox)findViewById(R.id.familyphoto);
		
		SharedPreferences settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean("familyphoto", cb.isChecked());

        editor.commit();

	}
	private void setIndividualPhoto(){
		CheckBox cb = (CheckBox)findViewById(R.id.individualphoto);
		
		SharedPreferences settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean("individualphoto", cb.isChecked());

        editor.commit();

	}
	private void setInstantDial(){
		CheckBox cb = (CheckBox)findViewById(R.id.instantdial);
		
		SharedPreferences settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean("instantdial", cb.isChecked());

        editor.commit();

	}
	private OnClickListener mInstantDialClick = new OnClickListener() {
		public void onClick(View v) {
			setInstantDial();
        }
    };
	private OnClickListener mFamilyPhotoClick = new OnClickListener() {
		public void onClick(View v) {
			setFamilyPhoto();
        }
    };
	private OnClickListener mIndividualPhotoClick = new OnClickListener() {
		public void onClick(View v) {
			setIndividualPhoto();
        }
    };
    public void onStart()
    {
       super.onStart();
       FlurryAgent.onStartSession(this, "YEGD9BWGMZ4SQWY2QTKI");
       FlurryAgent.onEvent("Settings");
    }
    public void onStop()
    {
       super.onStop();
       FlurryAgent.onEndSession(this);
    }
    public void restorePreferences(){
        // Restore preferences
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        membership_mode = settings.getBoolean("mode", MLS_MODE);
    }
}
