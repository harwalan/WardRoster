package com.blogspot.wardroster;

import com.blogspot.wardroster.R;
import com.flurry.android.FlurryAgent;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.view.Window;
import android.widget.ImageView;

public class ImageViewer extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.image_viewer); 
        requestWindowFeature(Window.FEATURE_NO_TITLE);

  	  	String imageUri;
	  	Bundle extras  = getIntent().getExtras();
	  	
	  	//this.requestWindowFeature(Window.FEATURE_NO_TITLE);
	  	//this.requestWindowFeature(Window.FEATURE_NO_TITLE);
	  	//this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NO_STATUS_BAR, WindowManager.LayoutParams.FLAG_NO_STATUS_BAR);
	    if(extras != null){
	    	imageUri = extras.getString("imageUri");
		    ImageView iv = (ImageView)findViewById(R.id.image);
		    try {
		    iv.setImageURI(Uri.parse(imageUri));
		    }
		    catch (Exception ex){
		    	System.out.println(ex.getMessage());
		    }
	    }
    }
    public void onStart()
    {
       super.onStart();
       FlurryAgent.onStartSession(this, "YEGD9BWGMZ4SQWY2QTKI");
       FlurryAgent.onEvent("ImageViewer");
    }
    public void onStop()
    {
       super.onStop();
       FlurryAgent.onEndSession(this);
    }
}
