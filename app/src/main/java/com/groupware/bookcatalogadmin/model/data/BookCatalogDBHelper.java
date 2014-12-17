package com.groupware.bookcatalogadmin.model.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.groupware.bookcatalogadmin.model.ObjectBookType;
import com.groupware.bookcatalogadmin.model.ObjectBookType.ObjectBookTypeEntry;

public class BookCatalogDBHelper extends SQLiteOpenHelper {
	public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "BookCatalog.db";

    public BookCatalogDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    
    @Override
    public void onCreate(SQLiteDatabase db) {
    	db.execSQL(ObjectBookType.SQL_CREATE_ENTRIES);
    	
    	db.execSQL("INSERT INTO "+ObjectBookTypeEntry.TABLE_NAME +" ("+ObjectBookTypeEntry.COLUMN_NAME_NAME +") VALUES ('LIBRO');");
    	db.execSQL("INSERT INTO "+ObjectBookTypeEntry.TABLE_NAME +" ("+ObjectBookTypeEntry.COLUMN_NAME_NAME +") VALUES ('REVISTA');");
    	db.execSQL("INSERT INTO "+ObjectBookTypeEntry.TABLE_NAME +" ("+ObjectBookTypeEntry.COLUMN_NAME_NAME +") VALUES ('DVD');");
        
    }
    
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    	db.execSQL(ObjectBookType.SQL_DELETE_ENTRIES);
    	onCreate(db);
    }
    
    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
