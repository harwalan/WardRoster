package com.blogspot.wardroster;

import com.flurry.android.FlurryAgent;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class OrganizationList extends ListActivity {
	
    private static final int WARD_LIST = 1;
    private static final int SETTINGS = 2;
    private static final int WARD_LIST_SORT = 1;
    private static final int SETTINGS_SORT = 2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      requestWindowFeature(Window.FEATURE_NO_TITLE);
      refreshList();
    }
    public void onListItemClick(ListView l, View v, int position, long id){
		// TODO fix click when searching
    	super.onListItemClick(l, v, position, id);
    	//Cursor c = mFamilyCursor;
    	//c.moveToPosition(position);
    	Intent i = new Intent(this, PositionList.class);
    	i.putExtra("organization", id);
    	//startActivityForResult(i, ACTIVITY_DETAIL);
    	startActivity(i);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	super.onCreateOptionsMenu(menu);
    	// Group ID
    	int groupId = 0;
    	// Create the menu item and keep a reference to it.
    	MenuItem wardList = menu.add(groupId, WARD_LIST, WARD_LIST_SORT, R.string.menu_item_ward_list);
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
       FlurryAgent.onEvent("OrganizationList");
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
    private void launchSettings(){
		Intent i = new Intent(this, Settings.class);
		startActivity(i);
    }

    public void refreshList(){
    	// Get all of the notes from the database and create the item list
    	Context context = getBaseContext();
    	DBAdapter dba = new DBAdapter(context);
    	dba.open();
        Cursor c = dba.getAllOrganizations();
        startManagingCursor(c);

        String[] from = new String[] { DBAdapter.KEY_NAME };
        int[] to = new int[] { R.id.text1 };
        
        // Now create an array adapter and set it to display using our row
        SimpleCursorAdapter notes = new SimpleCursorAdapter(this, R.layout.organization_row, c, from, to);
        setListAdapter(notes);    
    }
    public void setTitle(){
    	TextView title = (TextView)findViewById(R.id.title);
    	title.setText(R.string.organization_list_title);
    }    
}
