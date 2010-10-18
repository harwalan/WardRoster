package com.blogspot.wardroster;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.StringTokenizer;

import com.blogspot.wardroster.R;
import com.flurry.android.FlurryAgent;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class WardList extends ListActivity {
    private static final int ACTIVITY_DETAIL=1;
    public static final String APP_DIRECTORY = "/sdcard/WardRoster/";
    public static final String INDIVIDUAL_PHOTO_DIRECTORY = APP_DIRECTORY + "individual/";
    public static final String FAMILY_PHOTO_DIRECTORY = APP_DIRECTORY + "family/";
    public static final String PREFS_NAME = "WardRoster";
    public static final boolean ONLINE_MODE = false;
    public static final boolean MLS_MODE = true;
    public static final int IMPORT_FROM_CSV = 1;
    public static final int ORGANIZATION_LIST = 2;
    public static final int HELP = 3;
    public static final int SETTINGS = 4;
    public static final int IMPORT_SORT = 1;
    public static final int ORGANIZATION_LIST_SORT = 2;
    public static final int SETTINGS_SORT = 3;
    public static final int HELP_SORT = 4;
    
    private boolean membership_mode;
    @Override
    public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      requestWindowFeature(Window.FEATURE_NO_TITLE);
      //getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.main);
      
      checkFileStructure();
      restorePreferences();
      
      refreshList();    	

    }
    public void checkFileStructure(){
    	if (!checkForFile(APP_DIRECTORY)){
    		boolean success = new File(APP_DIRECTORY).mkdir();
    	}
    	if (!checkForFile(INDIVIDUAL_PHOTO_DIRECTORY)){
    		boolean success = new File(INDIVIDUAL_PHOTO_DIRECTORY).mkdir();
    	}
    	if (!checkForFile(FAMILY_PHOTO_DIRECTORY)){
    		boolean success = new File(FAMILY_PHOTO_DIRECTORY).mkdir();
    	}
    }
    public boolean checkForFile(String location){
    	File file = new File(location);
    	return file.exists();    	
    }
    public void modifyPreferences(boolean b){
    	// Save user preferences. We need an Editor object to
        // make changes. All objects are from android.context.Context
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean("mode", b);

        editor.commit();
    }
    public void onListItemClick(ListView l, View v, int position, long id){
    	// TODO fix click when searching
    	super.onListItemClick(l, v, position, id);
    	Intent i = new Intent(this, FamilyDetails.class);
    	i.putExtra("family", id);
    	startActivity(i);
    }
    public void restorePreferences(){
        // Restore preferences
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        membership_mode = settings.getBoolean("mode", MLS_MODE);
    }
	    

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	super.onCreateOptionsMenu(menu);
    	// Group ID
    	int groupId = 0;
    	// Create the menu item and keep a reference to it.
    	MenuItem importFromCSV = menu.add(groupId, IMPORT_FROM_CSV, IMPORT_SORT, R.string.menu_item_import);
    	MenuItem help = menu.add(groupId, HELP, HELP_SORT, R.string.menu_item_help);
    	if(membership_mode){
    		MenuItem organization = menu.add(groupId, ORGANIZATION_LIST, ORGANIZATION_LIST_SORT, R.string.menu_item_organization);
    	}
    	MenuItem settings = menu.add(groupId, SETTINGS, SETTINGS_SORT, R.string.menu_item_settings);
    	return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
    	super.onOptionsItemSelected(item);
    	
    	// Find which menu item has been selected
    	switch (item.getItemId()) {
    	// Check for each known menu item
    		case (IMPORT_FROM_CSV):
    			importCSV();
    			return true;
    		case (ORGANIZATION_LIST):
    			// TODO open organization list window
    			launchOrganizationList();
    			return true;
    		case (HELP):
    			showHelpMessage();
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
       FlurryAgent.onEvent("WardList");
    }
    public void onStop()
    {
       super.onStop();
       FlurryAgent.onEndSession(this);
    }
    public void showHelpMessage(){
		AlertDialog ad = new AlertDialog.Builder(this).create();
		ad.setTitle("Help");
		Context c = getBaseContext();
		ad.setMessage(Html.fromHtml(c.getString(R.string.help_text)));
		ad.setButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				return;
			} });
		ad.show();
    }
    public void importCSV(){
    	if (checkForFile(APP_DIRECTORY + "Membership.csv")){
//    		modifyPreferences(ONLINE_MODE);
//    		importFromOnlineCSV();
    		modifyPreferences(MLS_MODE);
    		FlurryAgent.onEvent("ImportMLS");
    		//importFromMLSCSV();
    		if(checkForFile(APP_DIRECTORY + "Organization.csv")){
    			FlurryAgent.onEvent("ImportOrganization");
    			//importOrganization();
    		}
    		if(checkForFile(APP_DIRECTORY + "HomeTeaching.csv")){
    			FlurryAgent.onEvent("ImportHomeTeaching");
    			//importHomeTeaching();
    		}
    		if(checkForFile(APP_DIRECTORY + "VisitingTeaching.csv")){
    			FlurryAgent.onEvent("ImportVisitingTeaching");
    			importVisitingTeaching();
    		}
    	} else if(checkForFile(APP_DIRECTORY + "Directory.csv")){    		
    		modifyPreferences(ONLINE_MODE);
    		FlurryAgent.onEvent("ImportOnline");
    		importFromOnlineCSV();
    	} else {	
    		AlertDialog ad = new AlertDialog.Builder(this).create();
    		ad.setTitle("File Not Found");
    		ad.setMessage("No CSV files found");
    		ad.setButton("OK", new DialogInterface.OnClickListener() {
    			public void onClick(DialogInterface dialog, int which) {
    				return;
    			} });  
    		ad.show();
    	}

    }
    private void importFromMLSCSV(){
    	Context context = getBaseContext();
    	if (!checkForFile(APP_DIRECTORY + "Membership.csv")){
    		throwDialog("File Not Found", "No Membership.csv file found");
    	}
    	else 
    	{
	    	// Open Database
	    	DBAdapter dba = new DBAdapter(context);
	    	dba.open();
	    	// Delete data
	    	dba.deleteTable("Family");
	    	dba.deleteTable("Person");
		    		    	
	    	// Import data
	    	
	    	String MEMBERSHIP_FILE = APP_DIRECTORY + "/Membership.csv";
	    	try{
	    		BufferedReader bufRdr = new BufferedReader(new FileReader(MEMBERSHIP_FILE));
	    		String line = null;
		    	int row = 0;
		    	int col = 0;
		    	//read each line of text file
		    	
		    	Family f;
		    	Person p;
		    	String tempString = "";
		    	String tempPreferredName = "";
		    	int tempInt;
		    	long tempLong;
		    	long lastFamily = 0;
		    	while((line = bufRdr.readLine()) != null)
		    	{
		    		line = line.replaceAll("\",\"", "| ");
		    		StringTokenizer st = new StringTokenizer(line,"|");
		    		f = new Family();
		    		p = new Person();
		    		if (row > 0){
		    			while (st.hasMoreTokens())
		    			{
		    				// collect data
		    				switch(col){
	
		    				// Individual ID
		    				case 0:
		    					tempInt = Integer.valueOf(st.nextToken().replaceAll("\"", "").trim());
		    					p.setId(tempInt);
		    					break;
	
			    			// Full Name
			    			case 1:
			    				tempString = st.nextToken().trim();
			    				p.setMiddleName(getMiddleName(tempString));
			    				break;
	
			    			// Preferred Name
			    			case 2:
			    				tempPreferredName = st.nextToken().trim();
			    				p.setPreferredName(tempPreferredName);
			    				String[] tempArray1 = tempPreferredName.split(", ");
			    				String last = tempArray1[0];
			    				String[] tempArray2 = tempPreferredName.replace(last, "").replace(",", "").trim().split("\\s");
			    				f.setName(last.toUpperCase() + ", " + tempArray2[0]);
			    				p.setFirstName(tempArray2[0]);
			    				p.setLastName(last);
			    				break;
		    				
				    			// Membership Record Number
			    			case 3:
			    				p.setMembershipNumber(st.nextToken().trim());
			    				break;

			    				// Head of Household ID
			    			case 4:
			    				tempInt = Integer.valueOf(st.nextToken().trim());
			    				p.setFamily(tempInt);
			    				f.setId(tempInt);
			    				break;
		
			    			// Head of Household Position
			    			case 5:
			    				tempString = st.nextToken().trim().toLowerCase(); 
			    				if(tempString.compareTo("spouse") == 0){
			    					setFamilyName(p.getFamily(), p.getFirstName());
			    					setHousehold(p);
			    				} else if (tempString.compareTo("head of household") == 0){
			    					f.setHousehold(tempPreferredName.trim());
			    				}
			    				break;
		
			    			// Head of Household Order
			    			case 6:
			    				tempLong = Long.valueOf(st.nextToken().trim());
			    				p.setSort(tempLong);
			    				break;
			    			
			    			// Household Phone
			    			case 7:
			    				f.setPhoneNumber1(st.nextToken().trim());
			    				break;
		
			    			// Individual Phone
			    			case 8:
			    				p.setPhoneNumber(st.nextToken().trim());
			    				break;
		
			    			// HouseHold email
			    			case 9:
			    				f.setEmail(st.nextToken().trim());
			    				break;
	
			    			// Individual email
			    			case 10:
			    				p.setEmail(st.nextToken().trim());
			    				break;
			    			// Street 1
			    			case 11:
			    				f.setAddress1(st.nextToken().trim());
			    				break;
		
			    			// Street 2
			    			case 12:
			    				f.setAddress2(st.nextToken().trim());
			    				break;
		
			    			// City
			    			case 14:
			    				f.setCity(st.nextToken().trim());
			    				break;
		
			    			// Zip
			    			case 15:
			    				f.setPostal(st.nextToken().trim());
			    				break;
		
			    			// State
			    			case 16:
			    				f.setState(st.nextToken().trim());
			    				break;
		
			    			// Country
			    			case 17:
			    				f.setCountry(st.nextToken().trim());
			    				break;
		
			    			// Gender
			    			case 27:
			    				p.setGender(st.nextToken().trim().substring(0,1));
			    				break;
		
			    			// Birth
			    			case 28:
			    				p.setBirth(st.nextToken().trim());
			    				break;
	
			    			// Baptized
			    			case 29:
			    				p.setBaptized(st.nextToken().trim());
			    				break;
		
			    			// Confirmed
			    			case 30:
			    				p.setConfirmed(st.nextToken().trim());
			    				break;
		
			    			// Endowed
			    			case 31:
			    				p.setEndowed(st.nextToken().trim());
			    				break;

			    			// Recommend expiration
			    			case 32:
			    				p.setRecommendExpiration(st.nextToken().trim());
			    				break;
			    				
			    			// Priesthood
			    			case 33:
			    				p.setPriesthood(st.nextToken().trim());
			    				break;
		
			    			// Mission
			    			case 34:
			    				p.setMission(st.nextToken().trim());
			    				break;
		
			    			// Married
			    			case 35:
			    				p.setMarriageStatus(st.nextToken().trim());
			    				break;
			    				
			    			// Spouse Member
			    			case 36:
			    				p.setSpouseMember(st.nextToken().trim());
			    				break;
			    				
			    			// Sealed to spouse
			    			case 37:
			    				p.setSealedToSpouse(st.nextToken().trim());
			    				break;
			    				
			    			default:
			    				st.nextToken();
			    				break;			
		    		
		    				}
		    				col++;
		    			}
		    			try {
		    				// Check if family exists
		    				if (lastFamily != f.getId()){
		    				
		    					long i = dba.insertEntry(f);
		    					lastFamily = f.getId();
		    				} 
		    			    		
		    				// insert person
		    				p.setAge(0);
		    				p.setTeachingFamily1(0);
		    				p.setTeachingFamily2(0);
		    				p.setTeachingFamily3(0);
		    				p.setTeachingFamily4(0);
		    				long j = dba.insertEntry(p);	    			
		    				System.out.println("");
		    			}
		    			catch (Exception ex){
		    				// TODO put a popup here
		    				String yo = ex.getMessage();
		    				throwDialog("Membership insert error", "");
		    			}
		    		}
		    		row++;
		    		col = 0;
		    	}
	    	}
	    	catch (Exception ex){
	    		String t = ex.getMessage();
	    		String s = ex.getStackTrace().toString();
	    		throwDialog("Membership Import Error", ex.getMessage());
	    	}
	
	    	dba.close();
	    	refreshList();
    	}
    }
    private void importFromOnlineCSV(){
    	Context context = getBaseContext();
    	if (!checkForFile(APP_DIRECTORY + "Directory.csv")){
    		AlertDialog ad = new AlertDialog.Builder(this).create();
    		ad.setTitle("File Not Found");
    		ad.setMessage("No Directory.csv file found");
    		ad.setButton("OK", new DialogInterface.OnClickListener() {
    			public void onClick(DialogInterface dialog, int which) {
    				return;
    			} }); 
    	}
    	else 
    	{
	    	// Open Database
	    	DBAdapter dba = new DBAdapter(context);
	    	dba.open();
	    	// Delete data
	    	dba.deleteTable("Family");
	    	dba.deleteTable("Person");
		    		    	
	    	// Import data
	    	
	    	String MEMBERSHIP_FILE = APP_DIRECTORY + "/Directory.csv";
	    	try{
	    		BufferedReader bufRdr = new BufferedReader(new FileReader(MEMBERSHIP_FILE));
	    		String line = null;
		    	int row = 0;
		    	int col = 0;
		    	long personcount = 1;
		    	//read each line of text file
		    	
		    	Family f;
		    	Person p;
		    	while((line = bufRdr.readLine()) != null)
		    	{
		    		line = line.replaceAll("\",\"", "| ");
		    		line = line.replaceAll("\"", "");
		    		line = line.replaceAll(" and ", " & ");
		    		StringTokenizer st = new StringTokenizer(line,"|");
		    		f = new Family();
		    		f.setId(row);
		    		p = new Person();
		    		if (row > 0){
		    			while (st.hasMoreTokens())
		    			{
		    				// collect data
		    				switch(col){
	
		    				// Family Name
		    				case 0:
		    					f.setName(formatFamilyName(st.nextToken()));
		    					break;
	
			    			// Phone
			    			case 1:
			    				f.setPhoneNumber1(st.nextToken());
			    				break;
	
			    			// Address 1
			    			case 2:
			    				f.setAddress1(st.nextToken());
			    				break;
		    				
				    			// Address 2
			    			case 3:
			    				String temp = st.nextToken();
			    				f.setCity(formatCity(temp));
			    				f.setState(formatState(temp));
			    				f.setPostal(formatPostal(temp));
			    				dba.insertEntry(f);
			    				break;
		
			    			// Name 1
			    			case 6:
			    				p = new Person();
			    				p.setId(personcount);
			    				personcount++;
			    				p.setFamily(row);
			    				p.setFirstName(formatFirstName(st.nextToken()));
			    				p.setSort(1);
			    				dba.insertEntry(p);
			    				break;
			    			
			    			// Name 2
			    			case 7:
			    				p = new Person();
			    				p.setId(personcount);
			    				personcount++;
			    				p.setFamily(row);
			    				p.setFirstName(formatFirstName(st.nextToken()));
			    				p.setSort(2);
			    				dba.insertEntry(p);
			    				break;		
				    			
			    			// Name 3
			    			case 8:
			    				p = new Person();
			    				p.setId(personcount);
			    				personcount++;
			    				p.setFamily(row);
			    				p.setFirstName(formatFirstName(st.nextToken()));
			    				p.setSort(3);
			    				dba.insertEntry(p);
			    				break;
								    		
					    	// Name 4
			    			case 9:
			    				p = new Person();
			    				p.setId(personcount);
			    				personcount++;
			    				p.setFamily(row);
			    				p.setFirstName(formatFirstName(st.nextToken()));
			    				p.setSort(4);
			    				dba.insertEntry(p);
			    				break;
								    			
			    			// Name 5
			    			case 10:
			    				p = new Person();
			    				p.setId(personcount);
			    				personcount++;
			    				p.setFamily(row);
			    				p.setFirstName(formatFirstName(st.nextToken()));
			    				p.setSort(5);
			    				dba.insertEntry(p);
			    				break;
												    			
							// Name 6
			    			case 11:
			    				p = new Person();
			    				p.setId(personcount);
			    				personcount++;
			    				p.setFamily(row);
			    				p.setFirstName(formatFirstName(st.nextToken()));
			    				p.setSort(6);
			    				dba.insertEntry(p);
			    				break;
														    		
			    			// Name 7
			    			case 12:
			    				p = new Person();
			    				p.setId(personcount);
			    				personcount++;
			    				p.setFamily(row);
			    				p.setFirstName(formatFirstName(st.nextToken()));
			    				p.setSort(7);
			    				dba.insertEntry(p);
			    				break;
									    			
			    			// Name 8
			    			case 13:
			    				p = new Person();
			    				p.setId(personcount);
			    				personcount++;
			    				p.setFamily(row);
			    				p.setFirstName(formatFirstName(st.nextToken()));
			    				p.setSort(8);
			    				dba.insertEntry(p);
			    				break;
				    			
				    		// Name 9
			    			case 14:
			    				p = new Person();
			    				p.setId(personcount);
			    				personcount++;
			    				p.setFamily(row);
			    				p.setFirstName(formatFirstName(st.nextToken()));
			    				p.setSort(9);
			    				dba.insertEntry(p);
			    				break;
					    			
					    	// Name 10
			    			case 15:
			    				p = new Person();
			    				p.setId(personcount);
			    				personcount++;
			    				p.setFamily(row);
			    				p.setFirstName(formatFirstName(st.nextToken()));
			    				p.setSort(10);
			    				dba.insertEntry(p);
			    				break;
						    			
							// Name 11
			    			case 16:
			    				p = new Person();
			    				p.setId(personcount);
			    				personcount++;
			    				p.setFamily(row);
			    				p.setFirstName(formatFirstName(st.nextToken()));
			    				p.setSort(11);
			    				dba.insertEntry(p);
			    				break;
							    			
							// Name 12
			    			case 17:
			    				p = new Person();
			    				p.setId(personcount);
			    				personcount++;
			    				p.setFamily(row);
			    				p.setFirstName(formatFirstName(st.nextToken()));
			    				p.setSort(12);
			    				dba.insertEntry(p);
			    				break;
								    			
			    			// Name 13
			    			case 18:
			    				p = new Person();
			    				p.setId(personcount);
			    				personcount++;
			    				p.setFamily(row);
			    				p.setFirstName(formatFirstName(st.nextToken()));
			    				p.setSort(13);
			    				dba.insertEntry(p);
			    				break;
									    			
			    			// Name 14
			    			case 19:
			    				p = new Person();
			    				p.setId(personcount);
			    				personcount++;
			    				p.setFamily(row);
			    				p.setFirstName(formatFirstName(st.nextToken()));
			    				p.setSort(14);
			    				dba.insertEntry(p);
			    				break;
								
				    		// Name 15
			    			case 20:
			    				p = new Person();
			    				p.setId(personcount);
			    				personcount++;
			    				p.setFamily(row);
			    				p.setFirstName(formatFirstName(st.nextToken()));
			    				p.setSort(15);
			    				dba.insertEntry(p);
			    				break;
								
			    			// Name 16
			    			case 21:
			    				p = new Person();
			    				p.setId(personcount);
			    				personcount++;
			    				p.setFamily(row);
			    				p.setFirstName(formatFirstName(st.nextToken()));
			    				p.setSort(16);
			    				dba.insertEntry(p);
			    				break;
								
				    		// Name 17
			    			case 22:
			    				p = new Person();
			    				p.setId(personcount);
			    				personcount++;
			    				p.setFamily(row);
			    				p.setFirstName(formatFirstName(st.nextToken()));
			    				p.setSort(17);
			    				dba.insertEntry(p);
			    				break;
								
			    			default:
			    				st.nextToken();
			    				break;			
		    		
		    				}
		    				col++;
		    			}
		    		}
		    		row++;
		    		col = 0;
		    	}
	    	}
	    	catch (Exception ex){
	    		String t = ex.getMessage();
	    		String s = ex.getStackTrace().toString();
	    	}
	
	    	dba.close();
	    	refreshList();
    	}
    }
    private void importOrganization(){
    	Context context = getBaseContext();
    	if (!checkForFile(APP_DIRECTORY + "Organization.csv")){
    		throwDialog("File Not Found", "No Organization.csv file found");
    	}
    	else 
    	{
	    	// Open Database
	    	DBAdapter dba = new DBAdapter(context);
	    	dba.open();
	    	// Delete data
	    	dba.deleteTable("Organization");
	    	dba.deleteTable("Position");
		    		    	
	    	// Import data
	    	
	    	String MEMBERSHIP_FILE = APP_DIRECTORY + "/Organization.csv";
	    	try{
	    		BufferedReader bufRdr = new BufferedReader(new FileReader(MEMBERSHIP_FILE));
	    		String line = null;
		    	int row = 0;
		    	int col = 0;
		    	//read each line of text file
		    	
		    	Organization o;
		    	Position p;
		    	String tempString;
		    	int tempInt;
		    	long tempLong;
		    	long lastOrganization = 0;
		    	int counter = 0;
		    	while((line = bufRdr.readLine()) != null)
		    	{
		    		line = line.replaceAll("\",\"", "| ");
		    		line = line.replaceAll("\"", "");
		    		StringTokenizer st = new StringTokenizer(line,"|");
		    		o = new Organization();
		    		p = new Position();
		    		if (row > 0){
		    			while (st.hasMoreTokens())
		    			{
		    				// collect data
		    				switch(col){
	
		    				// Organization Sequence
		    				case 0:
		    					tempInt = Integer.valueOf(st.nextToken().trim()); 
		    					o.setId(tempInt);
		    					p.setOrganization(tempInt);		    					
		    					break;
	
			    			// Organization
			    			case 1:
			    				o.setName(st.nextToken());			    				
			    				break;
			    				
			    			// Position Sequence
			    			case 2:
			    				p.setSequence(Integer.valueOf(st.nextToken().trim()));
			    				break;
			    				
			    			// Position Name
			    			case 3:
			    				p.setName(st.nextToken());
			    				break;
			    			
			    			// Leadership
			    				
			    				
			    			// Individual ID
			    			case 5:
			    				tempString = st.nextToken().trim();
			    				if (tempString.equals("")){
			    					tempString = "0";
			    				}
			    				p.setPerson(Integer.valueOf(tempString));
			    				break;
			    				
			    			// Individual Name
			    				
			    			// Sustained
			    			case 7:
			    				p.setSustained(st.nextToken());
			    				break;
			    				
			    			// Set Apart
			    			case 8:
			    				p.setSetApart(st.nextToken());
			    				break;

			    			default:
		    					st.nextToken();
		    					break;
		    				}
		    				col++;
		    			}
		    			try {
		    				// Check if organization exists
		    				if (lastOrganization != o.getId()){
		    					long i = dba.insertEntry(o);
		    					lastOrganization = o.getId();
		    				} 
		    			    		
		    				// insert position
		    				long j = -1;
		    				if (p.getPerson() != 0 ){
		    					p.setId(counter);
		    					j = dba.insertEntry(p);
		    					counter++;
		    				}
		    				System.out.println(Long.valueOf(j));
		    			}
		    			catch (Exception ex){
		    				// TODO put a popup here
		    				String yo = ex.getMessage();
		    			}
		    		}
		    		row++;
		    		col = 0;
		    	}
	    	}
	    	catch (Exception ex){
	    		String t = ex.getMessage();
	    		String s = ex.getStackTrace().toString();
	    		throwDialog("Organization Import error", ex.getMessage());
	    	}
	
	    	dba.close();
	    	refreshList();
    	}
    }
    private void importHomeTeaching(){
    	Context context = getBaseContext();
    	if (!checkForFile(APP_DIRECTORY + "HomeTeaching.csv")){
    		throwDialog("File Not Found", "No HomeTeaching.csv file found");
    	}
    	else 
    	{
	    	// Open Database
	    	DBAdapter dba = new DBAdapter(context);
	    	dba.open();
		    		    	
	    	// Import data
	    	
	    	String MEMBERSHIP_FILE = APP_DIRECTORY + "/HomeTeaching.csv";
	    	try{
	    		BufferedReader bufRdr = new BufferedReader(new FileReader(MEMBERSHIP_FILE));
	    		String line = null;
		    	int row = 0;
		    	int col = 0;
		    	//read each line of text file
		    	
		    	String homeTeacher1 = "";
		    	String homeTeacher2 = "";
		    	String household = "";
		    	String tempString = "";
		    	int counter = 0;
		    	while((line = bufRdr.readLine()) != null)
		    	{
		    		line = line.replaceAll("\",\"", " | ");
		    		line = line.replaceAll("\"", "");
		    		StringTokenizer st = new StringTokenizer(line,"|");
		    		if (row > 0){
		    			while (st.hasMoreTokens())
		    			{
		    				// collect data
		    				switch(col){
			    				
			    			// Home Teacher 1 Name
			    			case 6:
			    				tempString = st.nextToken();
			    				if(tempString.trim().length() > 0){
			    					homeTeacher1 = tempString;
			    				}
			    				break;
			    				
			    			// Home Teacher 2 Name
			    			case 9:
			    				tempString = st.nextToken();
			    				if(tempString.trim().length() > 0){
			    					homeTeacher2 = tempString;
			    				}
			    				break;
			    			
			    			// Household being taught
			    			case 12:
			    				household = st.nextToken();
			    				break;

			    			default:
		    					st.nextToken();
		    					break;
		    				}
		    				col++;
		    			}
		    			try {
		    				// Pull HT1
		    				Person ht1 = dba.getPerson(homeTeacher1.trim());
		    				
		    				// Pull HT2
		    				Person ht2 = dba.getPerson(homeTeacher2.trim());
		    				
		    				// update family
		    				boolean j = dba.updateHomeTeachers(household.trim(), ht1.getId(), ht2.getId());
		    				System.out.println("");
		    				
		    				
		    			}
		    			catch (Exception ex){
		    				// TODO put a popup here
		    				String yo = ex.getMessage();
		    			}
		    		}
		    		row++;
		    		col = 0;
		    	}
	    	}
	    	catch (Exception ex){
	    		String t = ex.getMessage();
	    		String s = ex.getStackTrace().toString();
	    		throwDialog("Home Teaching Import error", ex.getMessage());
	    	}
	
	    	dba.close();
	    	refreshList();
    	}
    }
    private void importVisitingTeaching(){
    	Context context = getBaseContext();
    	if (!checkForFile(APP_DIRECTORY + "VisitingTeaching.csv")){
    		throwDialog("File Not Found", "No VisitingTeaching.csv file found");
    	}
    	else 
    	{
	    	// Open Database
	    	DBAdapter dba = new DBAdapter(context);
	    	dba.open();
		    		    	
	    	// Import data
	    	
	    	String MEMBERSHIP_FILE = APP_DIRECTORY + "/VisitingTeaching.csv";
	    	try{
	    		BufferedReader bufRdr = new BufferedReader(new FileReader(MEMBERSHIP_FILE));
	    		String line = null;
		    	int row = 0;
		    	int col = 0;
		    	//read each line of text file
		    	
		    	String visitingTeacher1 = "";
		    	String visitingTeacher2 = "";
		    	String sister = "";
		    	String tempString = "";
		    	int counter = 0;
		    	while((line = bufRdr.readLine()) != null)
		    	{
		    		line = line.replaceAll("\",\"", " | ");
		    		line = line.replaceAll("\"", "");
		    		StringTokenizer st = new StringTokenizer(line,"|");
		    		if (row > 0){
		    			while (st.hasMoreTokens())
		    			{
		    				// collect data
		    				switch(col){
			    				
			    			// Visiting Teacher 1 Name
			    			case 5:
			    				tempString = st.nextToken();
			    				if(tempString.trim().length() > 0){
			    					visitingTeacher1 = tempString;
			    				}
			    				break;
			    				
			    			// Visiting Teacher 2 Name
			    			case 8:
			    				tempString = st.nextToken();
			    				if(tempString.trim().length() > 0){
			    					visitingTeacher2 = tempString;
			    				}
			    				break;
			    			
			    			// Sister being taught
			    			case 11:
			    				sister = st.nextToken();
			    				break;

			    			default:
		    					st.nextToken();
		    					break;
		    				}
		    				col++;
		    			}
		    			try {
		    				// Pull VT1
		    				Person vt1 = dba.getPerson(visitingTeacher1.trim());
		    				
		    				// Pull VT2
		    				Person vt2 = dba.getPerson(visitingTeacher2.trim());
		    				
		    				// update family
		    				boolean j = dba.updateVisitingTeachers(sister.trim(), vt1.getId(), vt2.getId());
		    				System.out.println("");
		    				
		    				
		    			}
		    			catch (Exception ex){
		    				// TODO put a popup here
		    				String yo = ex.getMessage();
		    			}
		    		}
		    		row++;
		    		col = 0;
		    	}
	    	}
	    	catch (Exception ex){
	    		String t = ex.getMessage();
	    		String s = ex.getStackTrace().toString();
	    		throwDialog("Visiting Teaching Import error", ex.getMessage());
	    	}
	
	    	dba.close();
	    	refreshList();
    	}    	
    }
    private void refreshList(){
    	// Get all of the notes from the database and create the item list
    	Log.d("ward list", "refreshlist");
    	Context context = getBaseContext();
    	DBAdapter dba = new DBAdapter(context);
    	dba.open();
        Cursor c = dba.getAllFamiles();
        startManagingCursor(c);

        String[] from = new String[] { DBAdapter.KEY_NAME };
        int[] to = new int[] { R.id.text1 };
        
        if(!c.moveToFirst()){
        	offerImport();
        }
        
        // Now create an array adapter and set it to display using our row
        SimpleCursorAdapter notes = new SimpleCursorAdapter(this, R.layout.family_row, c, from, to);
        setListAdapter(notes);    
        

  }
  public String formatFamilyName(String s){	  
	  String temp = null;
	  int col = 0;
	  int ampersand = -1;
	  String result = null;
	  StringTokenizer st = new StringTokenizer(s," ");
	  while(st.hasMoreElements()){
		  temp = st.nextToken();
		  if (col == 0){
			  result = temp;
		  } else if (col == 1){
			  result = result + " " + temp;
		  } else if (temp.toCharArray()[0] == '&'){
			  result = result + " " + temp;
			  ampersand = col;
		  } else if(col == ampersand + 1){
			  result = result + " " + temp;
		  }
		  col++;
	  }
	  return result;
  }
  public String formatFirstName(String s){
	  StringTokenizer st = new StringTokenizer(s," ");
	  while(st.hasMoreElements()){
		  return st.nextToken();
	  }
	  return "";	  
  }
  public String formatCity(String s){
	  StringTokenizer st = new StringTokenizer(s,",");
	  while(st.hasMoreElements()){
		  return st.nextToken();
	  }
	  return "";	  	  
  }
  public String formatState(String s1){
	  Boolean first = true;
	  StringTokenizer st1 = new StringTokenizer(s1,",");
	  while(st1.hasMoreElements()){
		  if(!first){
			  StringTokenizer st2 = new StringTokenizer(st1.nextToken(), " ");
			  while(st2.hasMoreElements()){
				  return st2.nextToken();
			  }
		  }
		  st1.nextToken();
		  first = false;
	  }
	  return "";	  	  
  }
  public String formatPostal(String s1){
	  Boolean first = true;
	  int col = 0;
	  StringTokenizer st1 = new StringTokenizer(s1,",");
	  while(st1.hasMoreElements()){
		  if(!first){
			  StringTokenizer st2 = new StringTokenizer(st1.nextToken(), " ");
			  while(st2.hasMoreElements()){
				  if(col == 1){
					  return st2.nextToken();
				  }
				  st2.nextToken();
				  col++;
			  }
		  } else {
			  st1.nextToken();
			  first = false;
		  }
	  }
	  return "";	  	  	  
  }
  public void setFamilyName(long id, String spouse){
	  Context context = getBaseContext();
	  DBAdapter dba = new DBAdapter(context);
	  dba.open();
	  Person p = dba.getPerson(id, 1);
	  dba.updateName(id, p.getLastName().toUpperCase() + ", " + p.getFirstName() + " & " + spouse);
	  dba.close();
  }
  public void setHousehold(Person spouse){
	  Context context = getBaseContext();
	  DBAdapter dba = new DBAdapter(context);
	  dba.open();
	  Person husband = dba.getPerson(spouse.getFamily(), 1);
	  String remove = spouse.getPreferredName().split(" ")[0];
	  String household = husband.getPreferredName() + " & " + spouse.getPreferredName().replace(remove, "").trim();
	  household = household.replace("  ", " ");
	  dba.updateHousehold(spouse.getFamily(), household);
	  dba.close();
  }
  public String getMiddleName(String s){
	  StringTokenizer st = new StringTokenizer(s," ");
	  int col = 0;
	  while(st.hasMoreElements()){
		  switch (col){
		  	case 2:
		  		return st.nextToken().trim();
		  	default:		  		
		  		break;
		  }
		  st.nextToken();
		  col++;
	  }
	  return "";
  }
  public void throwDialog(String title, String message){
		AlertDialog ad = new AlertDialog.Builder(this).create();
		ad.setTitle(title);
		ad.setMessage(message);
		ad.setButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				return;
			} });
		ad.show();

  }
	public void launchOrganizationList(){
		Intent i = new Intent(this, OrganizationList.class);
		startActivity(i);
   }
	public void launchSettings(){
		Intent i = new Intent(this, Settings.class);
		startActivity(i);
	}
	public void offerImport(){
		AlertDialog ad = new AlertDialog.Builder(this).create();
		ad.setTitle("Import from CSV?");
		ad.setMessage("No data found.  Import from CSV?  (Be patient, this can take a few minutes.  Choose \"Wait\" if Force Close message appears)");
		ad.setButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				importCSV();
				return;
			} });
		ad.setButton2("Cancel", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				return;
			} });
		ad.show();
		
	}
}