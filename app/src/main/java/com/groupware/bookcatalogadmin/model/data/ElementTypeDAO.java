package com.groupware.bookcatalogadmin.model.data;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.groupware.bookcatalogadmin.model.ObjectBookType;
import com.groupware.bookcatalogadmin.model.ObjectBookType.ObjectBookTypeEntry;

public class ElementTypeDAO {
	
	private static final String TAG = ElementTypeDAO.class.getSimpleName();
	
	private Context context;
	
	public ElementTypeDAO() {
		
	}
	
	public ElementTypeDAO(Context context) {
		this.context = context;
	}
	
	public List<ObjectBookType> listElementType() {
		List<ObjectBookType> listElementType = new ArrayList<ObjectBookType>();
		
		Cursor cursor           = null;
		SQLiteDatabase db       = null;
		try {
			BookCatalogDBHelper mDbHelper  = new BookCatalogDBHelper(context);
			db = mDbHelper.getReadableDatabase();
			// Define a projection that specifies which columns from the database
			String[] projection = { ObjectBookTypeEntry._ID,  ObjectBookTypeEntry.COLUMN_NAME_NAME};
			String sortOrder    = ObjectBookTypeEntry.COLUMN_NAME_NAME + " DESC";
			
			cursor = db.query(
							ObjectBookTypeEntry.TABLE_NAME,  // The table to query
						    projection,                    // The columns to return
						    null,                          // The columns for the WHERE clause
						    null,                          // The values for the WHERE clause
						    null,                          // don't group the rows
						    null,                          // don't filter by row groups
						    sortOrder                      // The sort order
						    );
			ObjectBookType elementType = null;
			if (cursor != null && cursor.moveToFirst()) {
				do {
					elementType = new ObjectBookType();
					elementType.setId(cursor.getInt(cursor.getColumnIndex(ObjectBookTypeEntry._ID)));
					elementType.setName(cursor.getString(cursor.getColumnIndex(ObjectBookTypeEntry.COLUMN_NAME_NAME)));
					listElementType.add(elementType);
				} while (cursor.moveToNext());
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (cursor != null) {
				cursor.close();
			}
			
			if (db != null) {
				db.close();
			}
		}
		
		return listElementType;
	}

}
