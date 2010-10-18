package com.blogspot.wardroster;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class CallReceiver extends BroadcastReceiver {
	@Override
	public void onReceive(Context context, Intent intent) {
    	Log.d("callreceiver", "so far, so good");
	    String mAction = intent.getAction();

    	Toast t = Toast.makeText(context, "hello world!", Toast.LENGTH_LONG);
    	t.show();
	    
	    if(mAction.equals("android.provider.Telephony.SMS_RECIEVED")) {

	    	Bundle bundle = intent.getExtras();
	    	String phone = bundle.getString("incoming_number");
	    	Log.d("callreceiver", "phone: "+phone);

	    	int duration = Toast.LENGTH_LONG;
	    	
	      	DBAdapter dba = new DBAdapter(context);
	    	dba.open();
	    	Person p = dba.getPersonByPhone(phone);
	    	String text = "not in ward";
	    	
	    	if (p != null){
	    		text = p.getLastName() + ", " + p.getFirstName();
	    	}
	    	
	    	Family f = dba.getFamilyByPhone(phone);
	    	if (f != null){
	    		text = f.getName();
	    	}    	
	    	
	    	Toast toast = Toast.makeText(context, text, duration);
	    	toast.show();
	    }
		
	}

}
