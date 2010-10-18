package com.blogspot.wardroster;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.blogspot.wardroster.R;
import com.flurry.android.FlurryAgent;

import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.ClipboardManager;
import android.text.util.Linkify;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.View.OnCreateContextMenuListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TabHost;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class FamilyDetails extends TabActivity {

	private String imageUri;
	private String familyImageName;
	private String familyName;
	private Person ht1;
	private Person ht2;
	private boolean instantdial = false;
	private boolean familyphoto = true;
	private long fID;
	static final private int PHOTO_GROUP = 1;
	static final private int ADDRESS_GROUP = 2;
	static final private int PHONENUMBER1_GROUP = 3;
	static final private int PHONENUMBER2_GROUP = 4;
	static final private int EMAIL_GROUP = 5;
	static final private int ALL = 6;
	static final private int CAPTURE_FROM_CAMERA = Menu.FIRST;
	static final private int LOCATE_IMAGE = Menu.FIRST + 1;
	static final private int COPY = Menu.FIRST;
	static final private int SHARE = Menu.FIRST + 1;
	static final private int SHARE_ALL = Menu.FIRST + 2;
	static final private String IMAGE_BASE_URI = "/sdcard/WardRoster/family/";
	static final private int PICK_FROM_CAMERA = 1;
	static final private int PICK_FROM_FILE = 2;
    private static final String PREFS_NAME = "WardRoster";
    private static final boolean ONLINE_MODE = false;
    private static final boolean MLS_MODE = true;
    private static final int WARD_LIST = 1;
    private static final int ORGANIZATION = 2;
    private static final int SETTINGS = 3;
    private static final int WARD_LIST_SORT = 1;
    private static final int ORGANIZATION_SORT = 2;
    private static final int SETTINGS_SORT = 3;

    private boolean membership_mode;

	// Create an anonymous implementation of OnClickListener
	private OnClickListener mHomeTeacher1Click = new OnClickListener() {
		public void onClick(View v) {
			// do something when the button is clicked
	        Context context = getBaseContext();
        	Intent i = new Intent(context, PersonDetails.class);
        	i.putExtra("family", ht1.getFamily());
        	i.putExtra("sort", ht1.getSort());
        	startActivity(i);
        }
    };
	private OnClickListener mHomeTeacher2Click = new OnClickListener() {
		public void onClick(View v) {
			// do something when the button is clicked
	        Context context = getBaseContext();
        	Intent i = new Intent(context, PersonDetails.class);
        	i.putExtra("family", ht2.getFamily());
        	i.putExtra("sort", ht2.getSort());
        	startActivity(i);
        }
    };
	private OnClickListener mImageButtonClick = new OnClickListener() {
		public void onClick(View v) {
        	if (!checkForFile(imageUri)){
        	}
        	else {
    			// do something when the button is clicked
    	        Context context = getBaseContext();
        		Intent i = new Intent(context, ImageViewer.class);
        		i.putExtra("imageUri", imageUri);
        		startActivity(i);
        	}
        }
    };
	private OnClickListener mPerson1Click = new OnClickListener() {
		public void onClick(View v) {
			// do something when the button is clicked
	        Context context = getBaseContext();
        	Intent i = new Intent(context, PersonDetails.class);
        	i.putExtra("family", fID);
        	i.putExtra("sort", 1);
        	startActivity(i);
        }
    };
	private OnClickListener mPerson2Click = new OnClickListener() {
		public void onClick(View v) {
			// do something when the button is clicked
	        Context context = getBaseContext();
        	Intent i = new Intent(context, PersonDetails.class);
        	i.putExtra("family", fID);
        	i.putExtra("sort", 2);
        	startActivity(i);
        }
    };
	private OnClickListener mPerson3Click = new OnClickListener() {
		public void onClick(View v) {
			// do something when the button is clicked
	        Context context = getBaseContext();
        	Intent i = new Intent(context, PersonDetails.class);
        	i.putExtra("family", fID);
        	i.putExtra("sort", 3);
        	startActivity(i);
        }
    };
	private OnClickListener mPerson4Click = new OnClickListener() {
		public void onClick(View v) {
			// do something when the button is clicked
	        Context context = getBaseContext();
        	Intent i = new Intent(context, PersonDetails.class);
        	i.putExtra("family", fID);
        	i.putExtra("sort", 4);
        	startActivity(i);
        }
    };
	private OnClickListener mPerson5Click = new OnClickListener() {
		public void onClick(View v) {
			// do something when the button is clicked
	        Context context = getBaseContext();
        	Intent i = new Intent(context, PersonDetails.class);
        	i.putExtra("family", fID);
        	i.putExtra("sort", 5);
        	startActivity(i);
        }
    };
	private OnClickListener mPerson6Click = new OnClickListener() {
		public void onClick(View v) {
			// do something when the button is clicked
	        Context context = getBaseContext();
        	Intent i = new Intent(context, PersonDetails.class);
        	i.putExtra("family", fID);
        	i.putExtra("sort", 6);
        	startActivity(i);
        }
    };
	private OnClickListener mPerson7Click = new OnClickListener() {
		public void onClick(View v) {
			// do something when the button is clicked
	        Context context = getBaseContext();
        	Intent i = new Intent(context, PersonDetails.class);
        	i.putExtra("family", fID);
        	i.putExtra("sort", 7);
        	startActivity(i);
        }
    };
	private OnClickListener mPerson8Click = new OnClickListener() {
		public void onClick(View v) {
			// do something when the button is clicked
	        Context context = getBaseContext();
        	Intent i = new Intent(context, PersonDetails.class);
        	i.putExtra("family", fID);
        	i.putExtra("sort", 8);
        	startActivity(i);
        }
    };
	private OnClickListener mPerson9Click = new OnClickListener() {
		public void onClick(View v) {
			// do something when the button is clicked
	        Context context = getBaseContext();
        	Intent i = new Intent(context, PersonDetails.class);
        	i.putExtra("family", fID);
        	i.putExtra("sort", 9);
        	startActivity(i);
        }
    };
	private OnClickListener mPerson10Click = new OnClickListener() {
		public void onClick(View v) {
			// do something when the button is clicked
	        Context context = getBaseContext();
        	Intent i = new Intent(context, PersonDetails.class);
        	i.putExtra("family", fID);
        	i.putExtra("sort", 10);
        	startActivity(i);
        }
    };
	private OnClickListener mPerson11Click = new OnClickListener() {
		public void onClick(View v) {
			// do something when the button is clicked
	        Context context = getBaseContext();
        	Intent i = new Intent(context, PersonDetails.class);
        	i.putExtra("family", fID);
        	i.putExtra("sort", 11);
        	startActivity(i);
        }
    };
	private OnClickListener mPerson12Click = new OnClickListener() {
		public void onClick(View v) {
			// do something when the button is clicked
	        Context context = getBaseContext();
        	Intent i = new Intent(context, PersonDetails.class);
        	i.putExtra("family", fID);
        	i.putExtra("sort", 12);
        	startActivity(i);
        }
    };
	private OnClickListener mPerson13Click = new OnClickListener() {
		public void onClick(View v) {
			// do something when the button is clicked
	        Context context = getBaseContext();
        	Intent i = new Intent(context, PersonDetails.class);
        	i.putExtra("family", fID);
        	i.putExtra("sort", 13);
        	startActivity(i);
        }
    };
	private OnClickListener mPhone1Click = new OnClickListener() {
		public void onClick(View v) {
			TextView phone = (TextView)findViewById(R.id.phone1);
        	Intent intent = new Intent(Intent.ACTION_CALL);
        	intent.setData(Uri.parse("tel:" + phone.getText()));
        	startActivity(intent);
        }
    };
	private OnClickListener mPhone2Click = new OnClickListener() {
		public void onClick(View v) {
			TextView phone = (TextView)findViewById(R.id.phone2);
        	Intent intent = new Intent(Intent.ACTION_CALL);
        	intent.setData(Uri.parse("tel:" + phone.getText()));
        	startActivity(intent);
        }
    };
	private OnClickListener mPhone3Click = new OnClickListener() {
		public void onClick(View v) {
			TextView phone = (TextView)findViewById(R.id.phone3);
        	Intent intent = new Intent(Intent.ACTION_CALL);
        	intent.setData(Uri.parse("tel:" + phone.getText()));
        	startActivity(intent);
        }
    };
	private OnClickListener mPhone4Click = new OnClickListener() {
		public void onClick(View v) {
			TextView phone = (TextView)findViewById(R.id.phone4);
        	Intent intent = new Intent(Intent.ACTION_CALL);
        	intent.setData(Uri.parse("tel:" + phone.getText()));
        	startActivity(intent);
        }
    };
	private OnClickListener mPhone5Click = new OnClickListener() {
		public void onClick(View v) {
			TextView phone = (TextView)findViewById(R.id.phone5);
        	Intent intent = new Intent(Intent.ACTION_CALL);
        	intent.setData(Uri.parse("tel:" + phone.getText()));
        	startActivity(intent);
        }
    };
	private OnClickListener mPhone6Click = new OnClickListener() {
		public void onClick(View v) {
			TextView phone = (TextView)findViewById(R.id.phone6);
        	Intent intent = new Intent(Intent.ACTION_CALL);
        	intent.setData(Uri.parse("tel:" + phone.getText()));
        	startActivity(intent);
        }
    };
	private OnClickListener mPhone7Click = new OnClickListener() {
		public void onClick(View v) {
			TextView phone = (TextView)findViewById(R.id.phone7);
        	Intent intent = new Intent(Intent.ACTION_CALL);
        	intent.setData(Uri.parse("tel:" + phone.getText()));
        	startActivity(intent);
        }
    };
	private OnClickListener mPhone8Click = new OnClickListener() {
		public void onClick(View v) {
			TextView phone = (TextView)findViewById(R.id.phone8);
        	Intent intent = new Intent(Intent.ACTION_CALL);
        	intent.setData(Uri.parse("tel:" + phone.getText()));
        	startActivity(intent);
        }
    };
	private OnClickListener mPhone9Click = new OnClickListener() {
		public void onClick(View v) {
			TextView phone = (TextView)findViewById(R.id.phone9);
        	Intent intent = new Intent(Intent.ACTION_CALL);
        	intent.setData(Uri.parse("tel:" + phone.getText()));
        	startActivity(intent);
        }
    };
	private OnClickListener mPhone10Click = new OnClickListener() {
		public void onClick(View v) {
			TextView phone = (TextView)findViewById(R.id.phone10);
        	Intent intent = new Intent(Intent.ACTION_CALL);
        	intent.setData(Uri.parse("tel:" + phone.getText()));
        	startActivity(intent);
        }
    };
	private OnClickListener mPhone11Click = new OnClickListener() {
		public void onClick(View v) {
			TextView phone = (TextView)findViewById(R.id.phone11);
        	Intent intent = new Intent(Intent.ACTION_CALL);
        	intent.setData(Uri.parse("tel:" + phone.getText()));
        	startActivity(intent);
        }
    };
	private OnClickListener mPhone12Click = new OnClickListener() {
		public void onClick(View v) {
			TextView phone = (TextView)findViewById(R.id.phone12);
        	Intent intent = new Intent(Intent.ACTION_CALL);
        	intent.setData(Uri.parse("tel:" + phone.getText()));
        	startActivity(intent);
        }
    };
	private OnClickListener mPhone13Click = new OnClickListener() {
		public void onClick(View v) {
			TextView phone = (TextView)findViewById(R.id.phone13);
        	Intent intent = new Intent(Intent.ACTION_CALL);
        	intent.setData(Uri.parse("tel:" + phone.getText()));
        	startActivity(intent);
        }
    };
	private OnClickListener mPhoneNumber1Click = new OnClickListener() {
		public void onClick(View v) {
			TextView phone = (TextView)findViewById(R.id.phonenumber1);
        	Intent intent = new Intent(Intent.ACTION_CALL);
        	intent.setData(Uri.parse("tel:" + phone.getText()));
        	startActivity(intent);
        }
    };

    @Override
    public boolean onContextItemSelected(MenuItem item) {
      super.onContextItemSelected(item);
      Intent i;
      if (item.getOrder() == PHOTO_GROUP){
	      switch (item.getItemId()){
	      	case CAPTURE_FROM_CAMERA:
	      		i = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE, null);
	      		i.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File (imageUri)));
	      		startActivityForResult(i, PICK_FROM_CAMERA);
	      		break;
	      	case LOCATE_IMAGE:
	      		i = new Intent(Intent.ACTION_GET_CONTENT, null);
	      		i.setType("image/jpg");
	            startActivityForResult(Intent.createChooser(i, "Select photo"), PICK_FROM_FILE);
	
	      		break;
	      }
     } else if (item.getOrder() == ADDRESS_GROUP || item.getOrder() == PHONENUMBER1_GROUP || item.getOrder() == EMAIL_GROUP){
    	switch (item.getItemId()){
    		case COPY:
    			copyData(item.getOrder());
    			break;
    		case SHARE:
    			shareData(item.getOrder());
    			break;
    		case SHARE_ALL:
    			shareData(ALL);
    	}
     }
      return false;
    }
    private void copyData(int group){
    	  Context context = getBaseContext();
    	  ClipboardManager cm = (ClipboardManager) context.getSystemService(CLIPBOARD_SERVICE);
    	  TextView address = (TextView)findViewById(R.id.address);
    	  TextView phoneNumber1 = (TextView)findViewById(R.id.phonenumber1);
	  	  TextView email = (TextView)findViewById(R.id.email);
	  	  switch (group){
    	  	case ADDRESS_GROUP:
    	    	cm.setText(address.getText());
    	  		break;
    	  	case PHONENUMBER1_GROUP:
    	    	cm.setText(phoneNumber1.getText());
    	  		break;
    	  	case EMAIL_GROUP:
    	  		cm.setText(email.getText());
    	  	case ALL:
    	  		cm.setText("Address: " + address.getText() + "\nPhone 1: " + phoneNumber1.getText() + "\nEmail: " + email.getText());
    	  		break;
    	  }
    }
    private void shareData(int group){
        Intent i;
  		i = new Intent(Intent.ACTION_SEND, null);
    	TextView address = (TextView)findViewById(R.id.address);
    	TextView phoneNumber1 = (TextView)findViewById(R.id.phonenumber1);
    	TextView email = (TextView)findViewById(R.id.email);
  		switch (group){
	  	case ADDRESS_GROUP:
	  		i.putExtra(Intent.EXTRA_TEXT, address.getText() + "\n");
	  		i.putExtra(Intent.EXTRA_SUBJECT, "Address for " + familyName);
	  		//i.putExtra("sms_body", address.getText());
	  		copyData(ADDRESS_GROUP);
	  		break;
	  	case PHONENUMBER1_GROUP:
	  		i.putExtra(Intent.EXTRA_TEXT, phoneNumber1.getText() + "\n");
	  		i.putExtra(Intent.EXTRA_SUBJECT, "Phone Number for " + familyName);
	  		//i.putExtra("sms_body", phoneNumber1.getText());
	  		copyData(PHONENUMBER1_GROUP);
	  		break;
	  	case EMAIL_GROUP:
	  		i.putExtra(Intent.EXTRA_TEXT, email.getText() + "\n");
	  		i.putExtra(Intent.EXTRA_SUBJECT, "Email for " + familyName);
	  		//i.putExtra("sms_body", email.getText());
	  		copyData(PHONENUMBER2_GROUP);
	  		break;
	  	case ALL:
	  		i.putExtra(Intent.EXTRA_TEXT, "Address: " + address.getText() + "\nPhone 1: " + phoneNumber1.getText() + "\nEmail: " + email.getText());
	  		i.putExtra(Intent.EXTRA_SUBJECT, "Contact info for " + familyName);
	  		break;
	  }
		i.setType("text/plain");
  		startActivity(Intent.createChooser(i, "Send Via"));
    }
    public void restorePreferences(){
        // Restore preferences
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        membership_mode = settings.getBoolean("mode", MLS_MODE);
    }
    public void modifyPreferences(boolean b){
    	// Save user preferences. We need an Editor object to
        // make changes. All objects are from android.context.Context
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean("mode", b);

        // Don't forget to commit your edits!!!
        editor.commit();
    }
    protected void onActivityResult(int requestCode ,int resultCode ,Intent outputIntent)
    {
    	super.onActivityResult(requestCode, resultCode, outputIntent);
    	switch (requestCode) {
    		case PICK_FROM_CAMERA:
    			
    			break;
    		case PICK_FROM_FILE:
    			try {
        			AssetFileDescriptor thePhoto = getContentResolver().openAssetFileDescriptor(outputIntent.getData(), "r");
        			FileInputStream fis = thePhoto.createInputStream();
        			FileOutputStream fos = new FileOutputStream(new File(imageUri));
        			byte[] buf = new byte[1024];
        			int i = 0;
        			while ((i = fis.read(buf)) != -1) {
        				fos.write(buf, 0, i);
        			}
    			}
    			catch (Exception ex){
    				System.out.println(ex.getMessage());
    			}
    			break;
    	}
    	loadImage(familyImageName);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
    	setContentView(R.layout.family_details);
    	restorePreferences();
    	loadSettings();
  	  	long famId;
	  	Bundle extras  = getIntent().getExtras();
	    if(extras != null){
	    	famId = extras.getLong("family");
	    	System.out.println("----------- Family  is-->"+ famId);
		    loadFamily(famId);

		    loadFamilyMembers((int)famId);
	    }
	    setTitle();
	    	   
	  	TabHost mTabHost = getTabHost();
	  	
	  	TabHost.TabSpec overview = mTabHost.newTabSpec("tab_test1");
	  	overview.setIndicator("Overview");
	  	overview.setContent(R.id.tablay1);
	    mTabHost.addTab(overview);
	    mTabHost.addTab(mTabHost.newTabSpec("tab_test2").setIndicator("Contact").setContent(R.id.tablay2));
	    if(membership_mode){
	    	mTabHost.addTab(mTabHost.newTabSpec("tab_test3").setIndicator("Details").setContent(R.id.tablay3));
	    } else {
	    	ScrollView sv3 = (ScrollView)findViewById(R.id.scrollview3);
	    	sv3.setVisibility(View.INVISIBLE);	
	    }
	    
	    mTabHost.setCurrentTab(0);    

	    ImageButton button = (ImageButton)findViewById(R.id.family_image);
	    if (familyphoto){
		    // Capture our button from layout
		    // Register the onClick listener with the implementation above
		    button.setOnClickListener(mImageButtonClick);
		    registerForContextMenu(button);
	    } else {
	    	LinearLayout ll = (LinearLayout)findViewById(R.id.tablay1);
	    	ll.removeView(button);
	    }


	    // This will make the listView create a ContextMenu when you long press it.
	    button.setOnCreateContextMenuListener(new OnCreateContextMenuListener() {
	    	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
	    		menu.add(0, Menu.FIRST, Menu.NONE, R.string.photo_option_1).setIcon(android.R.drawable.ic_menu_camera);
	    		menu.add(0, Menu.FIRST+1, Menu.NONE, R.string.photo_option_2).setIcon(android.R.drawable.ic_menu_gallery);
	    	}
	    }); 
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	super.onCreateOptionsMenu(menu);
    	// Group ID
    	int groupId = 0;
    	// Create the menu item and keep a reference to it.
    	MenuItem wardList = menu.add(groupId, WARD_LIST, WARD_LIST_SORT, R.string.menu_item_ward_list);
    	if(membership_mode){
    		MenuItem organization = menu.add(groupId, ORGANIZATION, ORGANIZATION_SORT, R.string.menu_item_organization);
    	}
    	MenuItem settings = menu.add(groupId, SETTINGS, SETTINGS_SORT, R.string.menu_item_settings);
    	return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
    	super.onOptionsItemSelected(item);
    	
    	// Find which menu item has been selected
    	switch (item.getItemId()) {
    	// Check for each known menu item
    		case (WARD_LIST):
    			launchWardList();
    			return true;
    		case (ORGANIZATION):
    			// TODO open organization list window
    			launchOrganizationList();
    			return true;
    		case (SETTINGS):
    			launchSettings();
    			return true;
    	}
    	// Return false if you have not handled the menu item.
    	return false;
    }
    public void onStart()
    {
       super.onStart();
       FlurryAgent.onStartSession(this, "YEGD9BWGMZ4SQWY2QTKI");
       FlurryAgent.onEvent("FamilyDetails");
    }
    public void onStop()
    {
       super.onStop();
       FlurryAgent.onEndSession(this);
    }
    private void launchOrganizationList(){
		Intent i = new Intent(this, OrganizationList.class);
		startActivity(i);
    }
    private void launchWardList(){
    	Intent i = new Intent(this, WardList.class);
    	startActivity(i);
    }
    private void launchSettings(){
		Intent i = new Intent(this, Settings.class);
		startActivity(i);
    }
    private void loadFamily(long familyId){
        Context context = getBaseContext();
      	DBAdapter dba = new DBAdapter(context);
    	dba.open();
    	Family f = dba.getFamily(familyId);
    	fID = f.getId();
    	familyImageName = f.getName().replaceAll(", ", "_").replaceAll(" & ","_").toLowerCase() + ".jpg";
    	familyName = f.getName();
    	
    	// Address
    	TextView address = (TextView)findViewById(R.id.address);
    	address.setText(f.getAddress1() + "\n" + f.getCity() + ", " + f.getState() + "  " + f.getPostal());
    	Linkify.addLinks(address, Linkify.MAP_ADDRESSES);

	    // This will make the Address create a ContextMenu when you long press it.
	    address.setOnCreateContextMenuListener(new OnCreateContextMenuListener() {
	    	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
	    		menu.add(0, COPY, ADDRESS_GROUP, R.string.copy).setIcon(android.R.drawable.ic_menu_camera);
	    		menu.add(0, SHARE, ADDRESS_GROUP, R.string.send).setIcon(android.R.drawable.ic_menu_camera);
	    		menu.add(0, SHARE_ALL, ADDRESS_GROUP, R.string.sendall).setIcon(android.R.drawable.ic_menu_camera);
	    	}
	    }); 

    	
    	//PhoneNumber 1
    	TextView phoneNumber1 = (TextView)findViewById(R.id.phonenumber1);
    	phoneNumber1.setText(f.getPhoneNumber1());

	    // This will make the Address create a ContextMenu when you long press it.
	    phoneNumber1.setOnCreateContextMenuListener(new OnCreateContextMenuListener() {
	    	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
	    		menu.add(0, COPY, PHONENUMBER1_GROUP, R.string.copy).setIcon(android.R.drawable.ic_menu_camera);
	    		menu.add(0, SHARE, PHONENUMBER1_GROUP, R.string.send).setIcon(android.R.drawable.ic_menu_camera);
	    		menu.add(0, SHARE_ALL, PHONENUMBER1_GROUP, R.string.sendall).setIcon(android.R.drawable.ic_menu_camera);
	    	}
	    }); 
		if(instantdial){
			phoneNumber1.setOnClickListener(mPhoneNumber1Click);
		}else{
			Linkify.addLinks(phoneNumber1, Linkify.PHONE_NUMBERS);
		}


    	//Email
    	TextView email = (TextView)findViewById(R.id.email);
    	email.setText(f.getEmail());
    	Linkify.addLinks(email, Linkify.EMAIL_ADDRESSES);

	    // This will make the Email create a ContextMenu when you long press it.
	    email.setOnCreateContextMenuListener(new OnCreateContextMenuListener() {
	    	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
	    		menu.add(0, COPY, EMAIL_GROUP, R.string.copy).setIcon(android.R.drawable.ic_menu_camera);
	    		menu.add(0, SHARE, EMAIL_GROUP, R.string.send).setIcon(android.R.drawable.ic_menu_camera);
	    		menu.add(0, SHARE_ALL, EMAIL_GROUP, R.string.sendall).setIcon(android.R.drawable.ic_menu_camera);
	    	}
	    }); 

    	
    	//HomeTeacher 1
    	TextView homeTeacher1 = (TextView)findViewById(R.id.hometeacher1);
    	if (f.getHomeTeacher1() > 0){
    		ht1 = dba.getPerson(f.getHomeTeacher1());
    		homeTeacher1.setText(ht1.getPreferredName());
    		homeTeacher1.setOnClickListener(mHomeTeacher1Click);
    	}
   	
    	//HomeTeacher 2
    	TextView homeTeacher2 = (TextView)findViewById(R.id.hometeacher2);
    	if (f.getHomeTeacher2() > 0){
    		ht2 = dba.getPerson(f.getHomeTeacher2());
    		homeTeacher2.setText(String.valueOf(ht2.getPreferredName()));
    		homeTeacher2.setOnClickListener(mHomeTeacher2Click);
    	}
    	loadImage(familyImageName);
    	
    	dba.close();
    }
        
    public void loadFamilyMembers(int familyId){
        Context context = getBaseContext();
      	DBAdapter dba = new DBAdapter(context);
    	dba.open();
    	Cursor c = dba.getFamilyMembers(familyId);

		int counter = 0;
		int maxSort = 0;
    	if (c.moveToNext()){
			TextView personX = new TextView(context);
			TextView ageX = new TextView(context);
			TextView genderX = new TextView(context);
			TextView phoneX = new TextView(context);
    		do {
    			switch (c.getInt(c.getColumnIndex("sort")) - 1){
    				case 0:
    	    			personX = (TextView)findViewById(R.id.person1);
    	    			ageX = (TextView)findViewById(R.id.age1);
    	    			genderX = (TextView)findViewById(R.id.gender1);
    	    			phoneX = (TextView)findViewById(R.id.phone1);
    	    		    if(membership_mode){
    	    		    	personX.setOnClickListener(mPerson1Click);
    	    		    }
    	    		    if(instantdial){
    	    		    	phoneX.setOnClickListener(mPhone1Click);
    	    		    	phoneX.setLinksClickable(true);
    	    		    }
    					break;
    				case 1:
    	    			personX = (TextView)findViewById(R.id.person2);
    	    			ageX = (TextView)findViewById(R.id.age2);
    	    			genderX = (TextView)findViewById(R.id.gender2);
    	    			phoneX = (TextView)findViewById(R.id.phone2);
    	    		    if(membership_mode){
    	    		    	personX.setOnClickListener(mPerson2Click);
    	    		    }
    	    		    if(instantdial){
    	    		    	phoneX.setOnClickListener(mPhone2Click);
    	    		    }
    					break;
    				case 2:
    	    			personX = (TextView)findViewById(R.id.person3);
    	    			ageX = (TextView)findViewById(R.id.age3);
    	    			genderX = (TextView)findViewById(R.id.gender3);
    	    			phoneX = (TextView)findViewById(R.id.phone3);
    	    		    if(membership_mode){
	    		    		personX.setOnClickListener(mPerson3Click);
    	    		    }
    	    		    if(instantdial){
    	    		    	phoneX.setOnClickListener(mPhone3Click);
    	    		    }
    					break;
    				case 3:
    	    			personX = (TextView)findViewById(R.id.person4);
    	    			ageX = (TextView)findViewById(R.id.age4);
    	    			genderX = (TextView)findViewById(R.id.gender4);
    	    			phoneX = (TextView)findViewById(R.id.phone4);
    	    		    if(membership_mode){
    	    		    	personX.setOnClickListener(mPerson4Click);
    	    		    }
    	    		    if(instantdial){
    	    		    	phoneX.setOnClickListener(mPhone4Click);
    	    		    }
    					break;
    				case 4:
    	    			personX = (TextView)findViewById(R.id.person5);
    	    			ageX = (TextView)findViewById(R.id.age5);
    	    			genderX = (TextView)findViewById(R.id.gender5);
    	    			phoneX = (TextView)findViewById(R.id.phone5);
    	    		    if(membership_mode){
    	    		    	personX.setOnClickListener(mPerson5Click);
    	    		    }
    	    		    if(instantdial){
    	    		    	phoneX.setOnClickListener(mPhone5Click);
    	    		    }
    					break;
    				case 5:
    	    			personX = (TextView)findViewById(R.id.person6);
    	    			ageX = (TextView)findViewById(R.id.age6);
    	    			genderX = (TextView)findViewById(R.id.gender6);
    	    			phoneX = (TextView)findViewById(R.id.phone6);
    	    		    if(membership_mode){
    	    		    	personX.setOnClickListener(mPerson6Click);
    	    		    }
    	    		    if(instantdial){
    	    		    	phoneX.setOnClickListener(mPhone6Click);
    	    		    }
    					break;
    				case 6:
    	    			personX = (TextView)findViewById(R.id.person7);
    	    			ageX = (TextView)findViewById(R.id.age7);
    	    			genderX = (TextView)findViewById(R.id.gender7);
    	    			phoneX = (TextView)findViewById(R.id.phone7);
    	    		    if(membership_mode){
    	    		    	personX.setOnClickListener(mPerson7Click);
    	    		    }
    	    		    if(instantdial){
    	    		    	phoneX.setOnClickListener(mPhone7Click);
    	    		    }
    					break;
    				case 7:
    	    			personX = (TextView)findViewById(R.id.person8);
    	    			ageX = (TextView)findViewById(R.id.age8);
    	    			genderX = (TextView)findViewById(R.id.gender8);
    	    			phoneX = (TextView)findViewById(R.id.phone8);
    	    		    if(membership_mode){
    	    		    	personX.setOnClickListener(mPerson8Click);
    	    		    }
    	    		    if(instantdial){
    	    		    	phoneX.setOnClickListener(mPhone8Click);
    	    		    }
    					break;
    				case 8:
    	    			personX = (TextView)findViewById(R.id.person9);
    	    			ageX = (TextView)findViewById(R.id.age9);
    	    			genderX = (TextView)findViewById(R.id.gender9);
    	    			phoneX = (TextView)findViewById(R.id.phone9);
    	    		    if(membership_mode){
    	    		    	personX.setOnClickListener(mPerson9Click);
    	    		    }
    	    		    if(instantdial){
    	    		    	phoneX.setOnClickListener(mPhone9Click);
    	    		    }
    					break;
    				case 9:
    	    			personX = (TextView)findViewById(R.id.person10);
    	    			ageX = (TextView)findViewById(R.id.age10);
    	    			genderX = (TextView)findViewById(R.id.gender10);
    	    			phoneX = (TextView)findViewById(R.id.phone10);
    	    		    if(membership_mode){
    	    		    	personX.setOnClickListener(mPerson10Click);
    	    		    }
    	    		    if(instantdial){
    	    		    	phoneX.setOnClickListener(mPhone10Click);
    	    		    }
    					break;
    				case 10:
    	    			personX = (TextView)findViewById(R.id.person11);
    	    			ageX = (TextView)findViewById(R.id.age11);
    	    			genderX = (TextView)findViewById(R.id.gender11);
    	    			phoneX = (TextView)findViewById(R.id.phone11);
    	    		    if(membership_mode){
    	    		    	personX.setOnClickListener(mPerson11Click);
    	    		    }
    	    		    if(instantdial){
    	    		    	phoneX.setOnClickListener(mPhone11Click);
    	    		    }
    					break;
    				case 11:
    	    			personX = (TextView)findViewById(R.id.person12);
    	    			ageX = (TextView)findViewById(R.id.age12);
    	    			genderX = (TextView)findViewById(R.id.gender12);
    	    			phoneX = (TextView)findViewById(R.id.phone12);
    	    		    if(membership_mode){
    	    		    	personX.setOnClickListener(mPerson12Click);
    	    		    }
    	    		    if(instantdial){
    	    		    	phoneX.setOnClickListener(mPhone12Click);
    	    		    }
    					break;    				
    				case 12:
    	    			personX = (TextView)findViewById(R.id.person12);
    	    			ageX = (TextView)findViewById(R.id.age13);
    	    			genderX = (TextView)findViewById(R.id.gender13);
    	    			phoneX = (TextView)findViewById(R.id.phone13);
    	    		    if(membership_mode){
    	    		    	personX.setOnClickListener(mPerson13Click);
    	    		    }
    	    		    if(instantdial){
    	    		    	phoneX.setOnClickListener(mPhone13Click);
    	    		    }
    					break;    				
    			
    			}
    			int age = 0;
    			try {
    				DateFormat df = new SimpleDateFormat("dd MMM yyyy");
    				Date birth = df.parse(c.getString(c.getColumnIndex("birth")));
    				Date today = new Date();
    				age = today.getYear() - birth.getYear();
    				if (today.getMonth() < birth.getMonth()){
    					age = age - 1;
    				}
    			}
    			catch (Exception ex){
    				System.out.print("");
    			}
    			personX.setText(c.getString(c.getColumnIndex("firstname")) + "  ");;
    			if(membership_mode){
	    			if(age > 0){
	    				ageX.setText(String.valueOf(age) + " ");
	    			}
	    			if(c.getString(c.getColumnIndex("gender")).compareTo("M") == 0 || c.getString(c.getColumnIndex("gender")).compareTo("F") == 0){
	    				genderX.setText(c.getString(c.getColumnIndex("gender")) + "  ");
	    			}
	    			if(c.getString(c.getColumnIndex("phonenumber")).trim().compareTo("") != 0){
	    				phoneX.setText(c.getString(c.getColumnIndex("phonenumber")) + "  ");
	    				if(instantdial){

	    				}else{
	    					Linkify.addLinks(phoneX, Linkify.PHONE_NUMBERS);
	    				}
	    			}
    			}
    			
    			counter++;
    			if (c.getInt(c.getColumnIndex("sort")) > maxSort){
    				maxSort = c.getInt(c.getColumnIndex("sort"));
    			}
    		} while (c.moveToNext());
    	}
    	c.close();
    	dba.close();
    	blankOutNames(maxSort);
    		
    }
    public void blankOutNames(int counter){
        Context context = getBaseContext();
		TableRow rowX = new TableRow(context);
		TableLayout table = (TableLayout)findViewById(R.id.table);
		for (int i=counter;i<=12;i++){
			switch (counter){
			case 0:
    			rowX = (TableRow)findViewById(R.id.row1);
				break;
			case 1:
    			rowX = (TableRow)findViewById(R.id.row2);
				break;
			case 2:
    			rowX = (TableRow)findViewById(R.id.row3);
				break;
			case 3:
    			rowX = (TableRow)findViewById(R.id.row4);
				break;
			case 4:
    			rowX = (TableRow)findViewById(R.id.row5);
				break;
			case 5:
    			rowX = (TableRow)findViewById(R.id.row6);
				break;
			case 6:
    			rowX = (TableRow)findViewById(R.id.row7);
				break;
			case 7:
    			rowX = (TableRow)findViewById(R.id.row8);
				break;
			case 8:
    			rowX = (TableRow)findViewById(R.id.row9);
				break;
			case 9:
    			rowX = (TableRow)findViewById(R.id.row10);
				break;
			case 10:
    			rowX = (TableRow)findViewById(R.id.row11);
				break;
			case 11:
    			rowX = (TableRow)findViewById(R.id.row12);
				break;    				
			case 12:
    			rowX = (TableRow)findViewById(R.id.row13);
				break;    				
			}
			table.removeView(rowX);
			counter++;
		}
        
    	
    }
    public void loadImage(String name){
    	try {
    		ImageButton ib = (ImageButton)findViewById(R.id.family_image);
    		imageUri = IMAGE_BASE_URI + name;
    		File f = new File(imageUri);
    		if (f.exists()){
    	        ib.setImageURI(Uri.parse(imageUri));
    			ib.setBackgroundColor(Color.TRANSPARENT);    			
    	        ib.invalidate();
    	        ib.refreshDrawableState();
    		} 
    	}
    	catch (Exception ex) {
    		System.out.println(ex.getMessage());
    	}
    }
    public boolean checkForFile(String location){
    	File file = new File(location);
    	return file.exists();    	
    }
    public void setTitle(){
    	TextView title = (TextView)findViewById(R.id.title);
    	title.setText(familyName);
    }
    private void loadSettings(){
		SharedPreferences settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
		instantdial = settings.getBoolean("instantdial", instantdial);
		familyphoto = settings.getBoolean("familyphoto", familyphoto);
    }
    
}
