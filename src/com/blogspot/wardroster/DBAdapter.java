package com.blogspot.wardroster;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.ContentValues;
import android.content.Context;
import android.database.*;
import android.database.sqlite.*;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.util.Log;

public class DBAdapter {
	private static final String DATABASE_NAME = "Ward.db";
	private static final int DATABASE_VERSION = 5;

	public static final String KEY_ROWID = "_id";
	public static final String KEY_FIRSTNAME = "firstname";
	public static final String KEY_LASTNAME = "lastname";
	public static final String KEY_NAME = "name";

	// The index (key) column name for use in where clauses.
	// The name and column index of each column in your database.
	// SQL Statement to create a new database.
	private static final String FAMILY_CREATE = "create table Family ( " +
		"_id integer primary key, " +
		"name text null," +
		"phonenumber1 text null, " +
		"phonenumber2 text null, " +
		"address1 text null, " +
		"address2 text null, " +
		"city text null, " +
		"state text null, " +
		"country text null, " +
		"postal text null, " +
		"hometeacher1 integer null, " +
		"hometeacher2 integer null, " +
		"email text null, " +
		"household text null " +
		");";
	private static final String PERSON_CREATE = "create table Person ( " +
	  	"_id integer primary key, " +
	    "firstname text null, " +
	    "middlename text null, " +
	    "lastname text null, " +
	    "age integer null, " +
	    "gender text null, " +
	    "teachingfamily1 integer null, " +
	    "teachingfamily2 integer null, " +
	    "teachingfamily3 integer null, " +       
	    "teachingfamily4 integer null, " +       
	    "sort integer null, " +       
	    "family integer null, " +       
	    "birth date null, " +       
	    "baptized text null, " +       
	    "confirmed text null, " +       
	    "endowed text null, " +       
	    "priesthood text null, " +       
	    "mission text null, " +       
	    "membershipnumber text null, " +
	    "phonenumber text null, " +
	    "email text null, " +
	    "recommendexpiration text null, " +
	    "spousemember text null, " + 
	    "sealedtospouse text null, " +
	    "marriagestatus text null, " +
	    "preferredname text null," +
		"visitingteacher1 integer null, " +
		"visitingteacher2 integer null " +
	    ");";
	private static final String POSITION_CREATE = "create table Position ( " +
		"_id integer primary key, " +
		"sequence integer null, " +
		"name text null, " +
		"person integer null, " +
		"sustained text null, " +
		"setapart text null, " +
		"organization integer null " +
		");";
	private static final String ORGANIZATION_CREATE = "create table Organization ( " +
		"_id integer primary key, " +
		"sequence integer null, " +
		"name text null " +
		");";    
  
	private SQLiteDatabase db;
	// Context of the application using the database.
	private final Context context;
	// Database open/upgrade helper
	private myDbHelper dbHelper;
	public DBAdapter(Context _context) {
		context = _context;
        dbHelper = new myDbHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	public void close() {
		db.close();
	}
	public boolean deleteTable(String table){    	  
		return db.delete(table, null, null) > 0;
	}    
    public Cursor getAllFamiles () {
        Cursor c = db.query("Family", null, null, null, null, null, "name");
        return c;        
	}
    public Cursor getAllOrganizations () {
        Cursor c = db.query("Organization", null, null, null, null, null, "sequence");
        return c;        
	}
    public Cursor getAllPositions(long organization) {
    	try{
    		Cursor c = db.rawQuery("select per._id, pos.name, per.firstname, per.lastname, per.family, per.sort from position pos join person per on pos.person = per._id where organization = ? order by sequence", new String[] {String.valueOf(organization)});
            //Cursor c = db.query("Position", null, null, null, null, null, "sequence");
            return c;        
    	}
    	catch(Exception ex){
    		System.out.println(ex.getMessage());
    		return null;
    	}
	}
    public Cursor getPositionsForPerson(long person) {
    	try{
    		Cursor c = db.rawQuery("select pos.name calling, org.name organization, pos.sustained sustained from position pos join organization org on pos.organization = org._id where person = ? order by org.sequence, pos.sequence", new String[] {String.valueOf(person)});
            //Cursor c = db.query("Position", null, null, null, null, null, "sequence");
            return c;        
    	}
    	catch(Exception ex){
    		System.out.println(ex.getMessage());
    		return null;
    	}
	}
    public Family getFamily(long rowIndex) {
        Cursor c = db.query("Family", new String[] {"name", "phonenumber1", "phonenumber2", "address1", "address2", "city", "state", "country", "postal", "hometeacher1", "hometeacher2", "email", "household"}, "_id = ?", new String[] {String.valueOf(rowIndex)}, null, null, null);  
        c.moveToNext();
        Family objectInstance = new Family(c.getString(0), c.getString(1), c.getString(2), c.getString(3), c.getString(4), c.getString(5), c.getString(6), c.getString(7), c.getString(8), c.getLong(9), c.getLong(10), c.getString(11), c.getString(12));
        objectInstance.setId(rowIndex);

        return objectInstance;
	}
	public Family getFamilyByPhone(String phone) {
		Cursor c = db.query("Family", new String[] {"Name"}, "phonenumber1 = ?", new String[] {phone}, null, null, null);  
        c.moveToNext();       
        Family objectInstance = new Family(c.getString(0));
        return objectInstance;
	}
	public Cursor getFamilyMembers(long rowIndex){
		Cursor c = db.query("Person", null, "family = ?", new String[] {String.valueOf(rowIndex)}, null, null, "sort");

		return c;
	}
	public Person getPerson(long rowIndex) {
		Cursor c = db.query("Person", new String[] {"_id", "FirstName", "MiddleName", "LastName", "2 Age", "Gender", "TeachingFamily1", "TeachingFamily2", "TeachingFamily3", "TeachingFamily4", "birth", "baptized", "confirmed", "endowed", "priesthood", "mission", "membershipnumber", "phonenumber", "email", "recommendexpiration", "marriagestatus", "spousemember", "sealedtospouse", "preferredname", "sort", "family", "visitingteacher1", "visitingteacher2"}, "_id = ?", new String[] {String.valueOf(rowIndex)}, null, null, null);  
        c.moveToNext();       
        Person objectInstance = new Person(c.getLong(0), c.getString(1), c.getString(2), c.getString(3), c.getLong(4), c.getString(5), c.getLong(6), c.getLong(7), c.getLong(8), c.getLong(9), c.getString(10), c.getString(11), c.getString(12), c.getString(13), c.getString(14), c.getString(15), c.getString(16), c.getString(17), c.getString(18), c.getString(19), c.getString(20), c.getString(21), c.getString(22), c.getString(23), c.getLong(24), c.getLong(25), c.getLong(26), c.getLong(27));
        return objectInstance;
	}
	public Person getPerson(String last, String first, String middle) {
		Cursor c = db.query("Person", new String[] {"_id", "FirstName", "MiddleName", "LastName", "2 Age", "Gender", "TeachingFamily1", "TeachingFamily2", "TeachingFamily3", "TeachingFamily4", "birth", "baptized", "confirmed", "endowed", "priesthood", "mission", "membershipnumber", "phonenumber", "email", "recommendexpiration", "marriagestatus", "spousemember", "sealedtospouse", "preferredname", "sort", "family", "visitingteacher1", "visitingteacher2"}, "firstname = ? and middlename = ? and lastname = ?", new String[] {first, middle, last}, null, null, null);  
        c.moveToNext();       
        Person objectInstance = new Person(c.getLong(0), c.getString(1), c.getString(2), c.getString(3), c.getLong(4), c.getString(5), c.getLong(6), c.getLong(7), c.getLong(8), c.getLong(9), c.getString(10), c.getString(11), c.getString(12), c.getString(13), c.getString(14), c.getString(15), c.getString(16), c.getString(17), c.getString(18), c.getString(19), c.getString(20), c.getString(21), c.getString(22), c.getString(23), c.getLong(24), c.getLong(25), c.getLong(26), c.getLong(27));
        return objectInstance;
	}
	public Person getPerson(String preferred) {
		Cursor c = db.query("Person", new String[] {"_id", "FirstName", "MiddleName", "LastName", "2 Age", "Gender", "TeachingFamily1", "TeachingFamily2", "TeachingFamily3", "TeachingFamily4", "birth", "baptized", "confirmed", "endowed", "priesthood", "mission", "membershipnumber", "phonenumber", "email", "recommendexpiration", "marriagestatus", "spousemember", "sealedtospouse", "preferredname", "sort", "family", "visitingteacher1", "visitingteacher2"}, "preferredname = ?", new String[] {preferred}, null, null, null);  
        c.moveToNext();       
        Person objectInstance = new Person(c.getLong(0), c.getString(1), c.getString(2), c.getString(3), c.getLong(4), c.getString(5), c.getLong(6), c.getLong(7), c.getLong(8), c.getLong(9), c.getString(10), c.getString(11), c.getString(12), c.getString(13), c.getString(14), c.getString(15), c.getString(16), c.getString(17), c.getString(18), c.getString(19), c.getString(20), c.getString(21), c.getString(22), c.getString(23), c.getLong(24), c.getLong(25), c.getLong(26), c.getLong(27));
        return objectInstance;
	}
	public Person getPersonByPhone(String phone) {
		Cursor c = db.query("Person", new String[] {"_id", "FirstName", "LastName"}, "phonenumber = ?", new String[] {phone}, null, null, null);  
        c.moveToNext();       
        Person objectInstance = new Person(c.getLong(0), c.getString(1), c.getString(2));
        return objectInstance;
	}
	public Person getPerson(long family, long sort) {
		try{
			Cursor c = db.query("Person", new String[] {"_id", "FirstName", "MiddleName", "LastName", "age", "Gender", "TeachingFamily1", "TeachingFamily2", "TeachingFamily3", "TeachingFamily4", "birth", "baptized", "confirmed", "endowed", "priesthood", "mission", "membershipnumber", "phonenumber", "email", "recommendexpiration", "marriagestatus", "spousemember", "sealedtospouse", "preferredname", "sort", "family", "visitingteacher1", "visitingteacher2"}, "family = ? and sort = ?", new String[] {String.valueOf(family), String.valueOf(sort)}, null, null, null);  
			c.moveToNext();
			DateFormat df = new SimpleDateFormat("dd MMM yyyy");
			int age = 0;
			if(!c.getString(10).equals("")){
				Date birth = df.parse(c.getString(10));
				Date today = new Date();
				age = today.getYear() - birth.getYear();
				if (today.getMonth() < birth.getMonth()){
					age = age - 1;
				}
			}
			Person objectInstance = new Person(c.getLong(0), c.getString(1), c.getString(2), c.getString(3), age, c.getString(5), c.getLong(6), c.getLong(7), c.getLong(8), c.getLong(9), c.getString(10), c.getString(11), c.getString(12), c.getString(13), c.getString(14), c.getString(15), c.getString(16), c.getString(17), c.getString(18), c.getString(19), c.getString(20), c.getString(21), c.getString(22), c.getString(23), c.getLong(24), c.getLong(25), c.getLong(26), c.getLong(27));
			objectInstance.setFamily(family);
			objectInstance.setSort(sort);
			return objectInstance;
		} catch (Exception ex) {
			System.out.print(ex.getMessage());
		}
		// TODO Return a cursor to a row from the database and
		// use the values to populate an instance of MyObject
		return null;
	}
    public long insertEntry(Family family) {
        ContentValues contentValues = new ContentValues();
        // TODO fill in ContentValues to represent the new row
        contentValues.put("_id", family.getId());
        contentValues.put("name", family.getName());
        contentValues.put("phonenumber1", family.getPhoneNumber1());
        contentValues.put("phonenumber2", family.getPhoneNumber2());
        contentValues.put("address1", family.getAddress1());
        contentValues.put("address2", family.getAddress2());
        contentValues.put("hometeacher1", family.getHomeTeacher1());
        contentValues.put("hometeacher2", family.getHomeTeacher2());
        contentValues.put("city", family.getCity());
        contentValues.put("state", family.getState());
        contentValues.put("country", family.getCountry());
        contentValues.put("postal", family.getPostal());
        contentValues.put("email", family.getEmail());
        contentValues.put("household", family.getHousehold());        
        return db.insert("Family", null, contentValues);
    }
    public long insertEntry(Organization organization) {
		ContentValues contentValues = new ContentValues();
	    contentValues.put("_id", organization.getId());
	    contentValues.put("sequence", organization.getSequence());
	    contentValues.put("name", organization.getName());
	    return db.insert("Organization", null, contentValues);
	}      
	public long insertEntry(Person person) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("_id", person.getId());
        contentValues.put("firstname", person.getFirstName());
        contentValues.put("middlename", person.getMiddleName());
        contentValues.put("lastname", person.getLastName());
        contentValues.put("age", person.getAge());
        contentValues.put("gender", person.getGender());
        contentValues.put("teachingfamily1", person.getTeachingFamily1());
        contentValues.put("teachingfamily2", person.getTeachingFamily2());
        contentValues.put("teachingfamily3", person.getTeachingFamily3());
        contentValues.put("teachingfamily4", person.getTeachingFamily4());
        contentValues.put("sort", person.getSort());
        contentValues.put("family", person.getFamily());
        contentValues.put("birth", person.getBirth());
        contentValues.put("baptized", person.getBaptized());
        contentValues.put("confirmed", person.getConfirmed());
        contentValues.put("endowed", person.getEndowed());
        contentValues.put("priesthood", person.getPriesthood());
        contentValues.put("mission", person.getMission());
        contentValues.put("membershipnumber", person.getMembershipNumber());
        contentValues.put("phonenumber", person.getPhoneNumber());
        contentValues.put("email", person.getEmail());
        contentValues.put("recommendexpiration", person.getRecommendExpiration());
        contentValues.put("spousemember", person.getSpouseMember());
        contentValues.put("sealedtospouse", person.getSealedToSpouse());
        contentValues.put("marriagestatus", person.getMarriageStatus());
        contentValues.put("preferredname", person.getPreferredName());
        return db.insert("Person", null, contentValues);
	}      
	public long insertEntry(Position position) {
		ContentValues contentValues = new ContentValues();
	    // TODO fill in ContentValues to represent the new row
	    contentValues.put("_id", position.getId());
	    contentValues.put("sequence", position.getSequence());
	    contentValues.put("name", position.getName());
	    contentValues.put("person", position.getPerson());
	    contentValues.put("sustained", position.getSustained());
	    contentValues.put("setapart", position.getSetApart());
	    contentValues.put("organization", position.getOrganization());
	    return db.insert("Position", null, contentValues);
	}      
	public DBAdapter open() throws SQLException {
        db = dbHelper.getWritableDatabase();
        return this;
	}
	public boolean removeEntry(long rowIndex, String table) {
		return db.delete(table, "_id = " + rowIndex, null) > 0;
	}
	public boolean updateHomeTeachers(String household, long ht1, long ht2){
	  	  ContentValues args = new ContentValues();
	  	  args.put("hometeacher1", ht1);
	  	  args.put("hometeacher2", ht2);
	  	  return db.update("Family", args, "household" + "= '" + household + "'", null) > 0;		
	}
	public boolean updateVisitingTeachers(String sister, long vt1, long vt2){
	  	  ContentValues args = new ContentValues();
	  	  args.put("visitingteacher1", vt1);
	  	  args.put("visitingteacher2", vt2);
	  	  return db.update("Person", args, "preferredname" + "= '" + sister + "'", null) > 0;		
	}
    public boolean updateHousehold(long id, String household) 
    {
  	  ContentValues args = new ContentValues();
  	  args.put("household", household);
  	  return db.update("Family", args, KEY_ROWID + "=" + id, null) > 0;
    }
    public boolean updateName(long id, String name) 
    {
  	  ContentValues args = new ContentValues();
  	  args.put(KEY_NAME, name);
  	  return db.update("Family", args, KEY_ROWID + "=" + id, null) > 0;
    }
	private static class myDbHelper extends SQLiteOpenHelper {
		public myDbHelper(Context context, String name, CursorFactory factory, int version) {
			super(context, name, factory, version);
		}
	    // Called when no database exists in
	    // disk and the helper class needs
	    // to create a new one.
	    @Override
	    public void onCreate(SQLiteDatabase _db) {
	    	_db.execSQL(FAMILY_CREATE);
	    	_db.execSQL(PERSON_CREATE);
	    	_db.execSQL(POSITION_CREATE);
	    	_db.execSQL(ORGANIZATION_CREATE);
	    }
    
	    // Called when there is a database version mismatch meaning that
	    // the version of the database on disk needs to be upgraded to
	    // the current version.
	    @Override
	    public void onUpgrade(SQLiteDatabase _db, int _oldVersion, int _newVersion) {
	    	// Log the version upgrade.
	    	Log.w("TaskDBAdapter", "Upgrading from version " +
                              _oldVersion + " to " +
                              _newVersion +
                              ", which will destroy all old data");
			// Upgrade the existing database to conform to the new version.
			// Multiple previous versions can be handled by comparing
			// _oldVersion and _newVersion values.
			// The simplest case is to drop the old table and create a
			// new one.
			_db.execSQL("DROP TABLE IF EXISTS Family");
			_db.execSQL("DROP TABLE IF EXISTS Person");
			_db.execSQL("DROP TABLE IF EXISTS Position");
			_db.execSQL("DROP TABLE IF EXISTS Organization");
			// Create a new one.
			onCreate(_db);
	    }
	}
}

