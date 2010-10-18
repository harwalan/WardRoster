package com.blogspot.wardroster;

import java.util.ArrayList;
import java.util.List;

import com.flurry.android.FlurryAgent;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class PositionList extends ListActivity{
	private long organizationId;
	String[] items={"lorem", "ipsum", "dolor", "sit", "amet",
	        "consectetuer", "adipiscing", "elit", "morbi", "vel",
	        "ligula", "vitae", "arcu", "aliquet", "mollis",
	        "etiam", "vel", "erat", "placerat", "ante",
	        "porttitor", "sodales", "pellentesque", "augue",
	        "purus"};
	Cursor c;
	List persons;

    @Override
	public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
	  	Bundle extras  = getIntent().getExtras();
	    if(extras != null){
	    	organizationId = extras.getLong("organization");
	    }
	    //setContentView(R.layout.main);
      refreshList();
    }
    public void refreshList(){
    	// Get all of the notes from the database and create the item list
    	Context context = getBaseContext();
    	DBAdapter dba = new DBAdapter(context);
    	dba.open();
        c = dba.getAllPositions(organizationId);
        startManagingCursor(c);
        persons = new ArrayList();
        while(c.moveToNext()){
        	persons.add(new Person(c.getLong(0), c.getString(1), c.getString(2), c.getString(3), c.getLong(4), c.getLong(5)));
        }

        
        // Now create an array adapter and set it to display using our row
        //SimpleCursorAdapter notes = new SimpleCursorAdapter(this, R.layout.position_row, c, from, to);
        //ArrayAdapter aa = new ArrayAdapter<String>(this,R.layout.position_row, c);
        c.moveToFirst();
        setListAdapter(new PositionAdapter());    
    }
    class PositionAdapter extends ArrayAdapter {
    	PositionAdapter(){
    		super(PositionList.this, R.layout.position_row, persons.toArray());
    	}
    	public View getView(int position, View convertView, ViewGroup parent) {
    		Person p = (Person)persons.get(position);
    		LayoutInflater inflater=getLayoutInflater();
    		View row=inflater.inflate(R.layout.position_row, parent, false);
    		TextView label=(TextView)row.findViewById(R.id.name);
    		label.setText(p.getLastName() + ", " + p.getFirstName());
    		TextView pos=(TextView)row.findViewById(R.id.position);
    		pos.setText("  -  " + p.getPosition());
    		c.moveToNext();
    		return(row);
    	}
    }
    public void onListItemClick(ListView l, View v, int position, long id){
    	// TODO fix click when searching
    	super.onListItemClick(l, v, position, id);
    	Intent i = new Intent(this, PersonDetails.class);
    	Person p = (Person)persons.get(position);
    	i.putExtra("family", p.getFamily());
    	i.putExtra("sort", p.getSort());
    	startActivity(i);
    }
    public void onStart()
    {
       super.onStart();
       FlurryAgent.onStartSession(this, "YEGD9BWGMZ4SQWY2QTKI");
       FlurryAgent.onEvent("PositionList");
    }
    public void onStop()
    {
       super.onStop();
       FlurryAgent.onEndSession(this);
    }
}
