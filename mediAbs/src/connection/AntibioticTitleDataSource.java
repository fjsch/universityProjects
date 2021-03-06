package connection;

import java.util.ArrayList;
import android.content.ContentValues;
import android.content.Context;
import net.sqlcipher.Cursor;
import net.sqlcipher.SQLException;
//import android.database.Cursor;
//import android.database.SQLException;
//import android.database.sqlite.SQLiteDatabase;
import net.sqlcipher.database.*;

public class AntibioticTitleDataSource {

  // Database fields
  private SQLiteDatabase database;
  private MySQLiteHelper dbHelper;
  private String[] allColumns = 
	  { 
		  MySQLiteHelper.ANTIBIOTIC_TITLE,
	  };

  public AntibioticTitleDataSource(Context context) {
    dbHelper = new MySQLiteHelper(context);
  }

  public void open() throws SQLException {
    database = dbHelper.getWritableDatabase("bubabilubap");
  }

  public void close() {
    dbHelper.close();
  }
  
  public void clearTable() {
	    database.delete(MySQLiteHelper.TABLE_ANTIBIOTICTITLE, null, null);
	  }

  public void insertAntibioticTitle(String title) {
    
	ContentValues values = new ContentValues();

    values.put(MySQLiteHelper.ANTIBIOTIC_TITLE, title);
    
    database.insert(MySQLiteHelper.TABLE_ANTIBIOTICTITLE, null, values);
  }

  public ArrayList<String> getTitleOfAntibiotics() {
    ArrayList<String> antibioticTitles = new ArrayList<String>();

    Cursor cursor = database.query(MySQLiteHelper.TABLE_ANTIBIOTICTITLE,
        allColumns, null, null, null, null, null);

    cursor.moveToFirst();
    while (!cursor.isAfterLast()) {
   	String newAntibioticTitle = cursorToAntibioticTitle(cursor);
    	antibioticTitles.add(newAntibioticTitle);
    	cursor.moveToNext();
    }
    // Make sure to close the cursor
    cursor.close();
    return antibioticTitles;
  }


  private String cursorToAntibioticTitle(Cursor cursor) {
	  
	String newAntibioticTitle = new String(
			cursor.getString(0));
	
	return newAntibioticTitle;
  }
} 