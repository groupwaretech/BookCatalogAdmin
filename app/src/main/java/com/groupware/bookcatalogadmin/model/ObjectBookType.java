/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.groupware.bookcatalogadmin.model;

import java.io.Serializable;

import android.provider.BaseColumns;

import com.groupware.bookcatalogadmin.utils.Constants;

/**
 *
 * @author heinsohnbusiness
 */
public class ObjectBookType implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;
    private String name;

    public ObjectBookType() {
    }

    public ObjectBookType(Integer id) {
        this.id = id;
    }

    public ObjectBookType(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof ObjectBookType)) {
            return false;
        }
        ObjectBookType other = (ObjectBookType) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return name;
    }
    
    /* METADATA MODEL */
   	public static final String SQL_CREATE_ENTRIES = "CREATE TABLE " + ObjectBookTypeEntry.TABLE_NAME + " (" +
   			ObjectBookTypeEntry._ID                 + Constants.INTEGER_TYPE_PRIMARY_KEY  + Constants.COMMA_SEP +
            ObjectBookTypeEntry.COLUMN_NAME_NAME    + Constants.VARCHAR_TYPE +
               " );";

   	public static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + ObjectBookTypeEntry.TABLE_NAME+" ;";



   	/* Inner class that defines the table contents */
   	public static abstract class ObjectBookTypeEntry implements BaseColumns {
   	    public static final String TABLE_NAME        = "ObjectBookType";
   	    public static final String COLUMN_NAME_NAME  = "name";
       
   	}
    
}
