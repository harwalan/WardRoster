package com.blogspot.wardroster;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import com.blogspot.wardroster.R;
import com.flurry.android.FlurryAgent;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class PersonDetails extends Activity {
    
	private String imageUri;
	private Person p;
	private String personImageName;
	private boolean individualphoto = true;
	static final private int CAPTURE_FROM_CAMERA = Menu.FIRST;
	static final private int LOCATE_IMAGE = Menu.FIRST + 1;
	static final private String IMAGE_BASE_URI = "/sdcard/WardRoster/individual/";
	static final private int PICK_FROM_CAMERA = 1;
	static final private int PICK_FROM_FILE = 2;
    public static final String PREFS_NAME = "WardRoster";
    private static final boolean MLS_MODE = true;
    private static final int WARD_LIST = 1;
    private static final int ORGANIZATION = 2;
    private static final int SETTINGS = 3;
    private static final int WARD_LIST_SORT = 1;
    private static final int ORGANIZATION_SORT = 2;
    private static final int SETTINGS_SORT = 3;

    private boolean membership_mode;


	@Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
    	setContentView(R.layout.person_details); 
    	loadSettings();
        
  	  	long familyId; 
  	  	long sort;
	  	Bundle extras  = getIntent().getExtras();
	    if(extras != null){
	    	familyId = extras.getLong("family");
	    	sort = extras.getLong("sort");
	    	if (sort == 0){
	    		sort = extras.getInt("sort");
	    	}
	    	System.out.println("----------- Family  is-->"+ familyId);
		    loadPerson(familyId, sort);
		    loadImage();
	    }
	    setTitle();
	    	   
	    
	    // Capture our button from layout
	    ImageButton button = (ImageButton)findViewById(R.id.person_image);
	    if(individualphoto){
		    // Register the onClick listener with the implementation above
		    button.setOnClickListener(mImageButtonClick);
		    registerForContextMenu(button);
	    } else
	    {
	    	LinearLayout ll = (LinearLayout)findViewById(R.id.verticallinear);
	    	ll.removeView(button);
	    }


	    // This will make the listView create a ContextMenu when you long press it.
	    // It also create the awesome menu option "Click Me"
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
       FlurryAgent.onEvent("PersonDetails");
    }
    public void onStop()
    {
       super.onStop();
       FlurryAgent.onEndSession(this);
    }
    private void launchWardList(){
    	Intent i = new Intent(this, WardList.class);
    	startActivity(i);
    }
    private void launchOrganizationList(){
		Intent i = new Intent(this, OrganizationList.class);
		startActivity(i);
    }
    private void launchSettings(){
		Intent i = new Intent(this, Settings.class);
		startActivity(i);
    }
    public void loadPerson(long family, long sort){
        Context context = getBaseContext();
      	DBAdapter dba = new DBAdapter(context);
    	dba.open();
    	p = dba.getPerson(family, sort);
    	personImageName = p.getLastName().toLowerCase() + "_" + p.getFirstName().toLowerCase() + "_" + p.getMiddleName().toLowerCase() + ".jpg";
    	
    	// Callings
    	TableLayout tl = (TableLayout)findViewById(R.id.details_table);
    	TableRow tr;
    	Cursor c = dba.getPositionsForPerson(p.getId());
    	TextView calling;
    	TextView firstColumn;
    	int counter = 0;
    	while(c.moveToNext()){
    		tr = new TableRow(context);
    		firstColumn = new TextView(context);
    		calling = new TextView(context);
    		if(counter == 0){
    			firstColumn.setText(R.string.callings_label);
    		} else {
    			firstColumn.setText("");
    		}
    		calling.setText(c.getString(c.getColumnIndex("calling")) + " - " + c.getString(c.getColumnIndex("sustained")) + "  ");
    		tr.addView(firstColumn);
    		tr.addView(calling);
    		tl.addView(tr, 2 + counter);
    		counter++;
    	}
    	
    	// Phone
    	TextView phoneLabel = (TextView)findViewById(R.id.phone_label);
    	phoneLabel.setText(R.string.phone_label);
    	TextView phone = (TextView)findViewById(R.id.phone);
    	phone.setText(p.getPhoneNumber());

    	// Gender
    	TextView genderLabel = (TextView)findViewById(R.id.gender_label);
    	genderLabel.setText(R.string.gender_label);
    	TextView gender = (TextView)findViewById(R.id.gender);
    	gender.setText(p.getGender());

    	// Age
    	TextView ageLabel = (TextView)findViewById(R.id.age_label);
    	ageLabel.setText(R.string.age_label);
    	TextView age = (TextView)findViewById(R.id.age);
    	age.setText(String.valueOf(p.getAge()));

    	// Birth
    	TextView birthLabel = (TextView)findViewById(R.id.birth_label);
    	birthLabel.setText(R.string.birth_label);
    	TextView birth = (TextView)findViewById(R.id.birth);
    	birth.setText(p.getBirth());

    	// Baptized
    	TextView baptizedLabel = (TextView)findViewById(R.id.baptized_label);
    	baptizedLabel.setText(R.string.baptized_label);
    	TextView baptized = (TextView)findViewById(R.id.baptized);
    	baptized.setText(p.getBaptized());
    	
    	// Confirmed
    	TextView confirmedLabel = (TextView)findViewById(R.id.confirmed_label);
    	confirmedLabel.setText(R.string.confirmed_label);
    	TextView confirmed = (TextView)findViewById(R.id.confirmed);
    	confirmed.setText(p.getConfirmed());
    	
    	// Endowed
    	TextView endowedLabel = (TextView)findViewById(R.id.endowed_label);
    	endowedLabel.setText(R.string.endowed_label);
    	TextView endowed = (TextView)findViewById(R.id.endowed);
    	endowed.setText(p.getEndowed());

    	// Priesthood
    	TextView priesthoodLabel = (TextView)findViewById(R.id.priesthood_label);
    	priesthoodLabel.setText(R.string.priesthood_label);
    	TextView priesthood = (TextView)findViewById(R.id.priesthood);
    	priesthood.setText(p.getPriesthood());

    	// Mission
    	TextView missionLabel = (TextView)findViewById(R.id.mission_label);
    	missionLabel.setText(R.string.mission_label);
    	TextView mission = (TextView)findViewById(R.id.mission);
    	mission.setText(p.getMission());
    
    	// MembershipNumber
    	TextView membershipNumberLabel = (TextView)findViewById(R.id.membership_number_label);
    	membershipNumberLabel.setText(R.string.membership_number_label);
    	TextView membershipNumber = (TextView)findViewById(R.id.membership_number);
    	membershipNumber.setText(p.getMembershipNumber());

    	// Email
    	TextView emailLabel = (TextView)findViewById(R.id.email_label);
    	emailLabel.setText(R.string.email_label);
    	TextView email = (TextView)findViewById(R.id.email);
    	email.setText(p.getEmail());
    	Linkify.addLinks(email, Linkify.EMAIL_ADDRESSES);

    	// RecommendExpiration
    	TextView recommendExpirationLabel = (TextView)findViewById(R.id.recommend_expiration_label);
    	recommendExpirationLabel.setText(R.string.recommend_expiration_label);
    	TextView recommendExpiration = (TextView)findViewById(R.id.recommend_expiration);
    	recommendExpiration.setText(p.getRecommendExpiration());

    	// MarriageStatus
    	TextView marriageStatusLabel = (TextView)findViewById(R.id.marriage_status_label);
    	marriageStatusLabel.setText(R.string.marriage_status_label);
    	TextView marriageStatus = (TextView)findViewById(R.id.marriage_status);
    	marriageStatus.setText(p.getMarriageStatus());

    	// SpouseMember
    	TextView spouseMemberLabel = (TextView)findViewById(R.id.spouse_member_label);
    	spouseMemberLabel.setText(R.string.spouse_member_label);
    	TextView spouseMember = (TextView)findViewById(R.id.spouse_member);
    	spouseMember.setText(p.getSpouseMember());

    	// SealedToSpouse
    	TextView sealedToSpouseLabel = (TextView)findViewById(R.id.sealed_to_spouse_label);
    	sealedToSpouseLabel.setText(R.string.sealed_to_spouse_label);
    	TextView sealedToSpouse = (TextView)findViewById(R.id.sealed_to_spouse);
    	sealedToSpouse.setText(p.getSealedToSpouse());

    	// Visiting Teacher 1
    	TextView visitingTeacherLabel1 = (TextView)findViewById(R.id.visiting_teacher_1_label);
    	TextView visitingTeacher1 = (TextView)findViewById(R.id.visiting_teacher_1);
    	if(p.getVisitingTeacher1() > 0){
	    	Person vt1 = dba.getPerson(p.getVisitingTeacher1());
	    	visitingTeacherLabel1.setText(R.string.visiting_teacher_1_label);
	    	visitingTeacher1.setText(vt1.getPreferredName());
    	} else {
    		visitingTeacherLabel1.setVisibility(View.GONE);
    		visitingTeacher1.setVisibility(View.GONE);
    	}

    	// Visiting Teacher 2
    	TextView visitingTeacherLabel2 = (TextView)findViewById(R.id.visiting_teacher_2_label);
    	TextView visitingTeacher2 = (TextView)findViewById(R.id.visiting_teacher_2);
    	if(p.getVisitingTeacher2() > 0){
	    	Person vt2 = dba.getPerson(p.getVisitingTeacher2());
	    	visitingTeacherLabel2.setText(R.string.visiting_teacher_2_label);
	    	visitingTeacher2.setText(vt2.getPreferredName());
    	} else {
    		visitingTeacherLabel2.setVisibility(View.GONE);
    		visitingTeacher2.setVisibility(View.GONE);
    	}
    	
    	
}
	// Create an anonymous implementation of OnClickListener
	private OnClickListener mImageButtonClick = new OnClickListener() {
		public void onClick(View v) {
        	// check to see if file exits
        	if (!checkForFile(imageUri)){
        	}
        	else {
    			// open ImageViewer
    	        Context context = getBaseContext();
            	Intent i = new Intent(context, ImageViewer.class);
        		i.putExtra("imageUri", imageUri);
        		startActivity(i);
        	}
        }
    };
    public void loadImage(){
    	try {
    		ImageButton ib = (ImageButton)findViewById(R.id.person_image);
    		imageUri = IMAGE_BASE_URI + personImageName;
    		File f = new File(imageUri);
    		if (f.exists()){
    	        ib.setImageURI(Uri.parse(imageUri));
    			ib.setBackgroundColor(Color.BLACK);
    	        ib.invalidate();
    	        ib.refreshDrawableState();
    		}
    	}
    	catch (Exception ex) {
    		System.out.println(ex.getMessage());
    	}
    }
    public void restorePreferences(){
        // Restore preferences
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        membership_mode = settings.getBoolean("mode", MLS_MODE);
    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {
      super.onContextItemSelected(item);
      Intent i;
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
      return false;
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
    	loadImage();
    }
    public boolean checkForFile(String location){
    	File file = new File(location);
    	return file.exists();    	
    }
    public void setTitle(){
    	TextView titleLast = (TextView)findViewById(R.id.title_last);
    	titleLast.setText(p.getLastName() + ", ");
    	titleLast.setOnClickListener(mtitleLastClick);
    	TextView titleFirst = (TextView)findViewById(R.id.title_first);
    	titleFirst.setText(p.getFirstName());
    }
	private OnClickListener mtitleLastClick = new OnClickListener() {
		public void onClick(View v) {
			// do something when the button is clicked
	        Context context = getBaseContext();
        	Intent i = new Intent(context, FamilyDetails.class);
        	i.putExtra("family", p.getFamily());
        	startActivity(i);
        }
    };
    private void loadSettings(){
		SharedPreferences settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
		individualphoto = settings.getBoolean("individualphoto", individualphoto);
    }


}
