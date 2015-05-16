package com.vinako.phonegallery.databaseHelper;

import android.provider.BaseColumns;

/**
 * Created by Khue on 15/5/2015.
 */
public class PhoneContract {
    // DB specific constants
    public static final String DB_NAME = "phones.db"; //
    public static final int DB_VERSION = 1; //
    public static final String TABLE = "phones"; //
    public static final String DEFAULT_SORT = Column.CREATED_AT + " DESC"; //
    public class Column { //
        public static final String ID = BaseColumns._ID; //
        public static final String NAME = "name";
        public static final String CATEGORY = "category";
        public static final String LINK = "link";
        public static final String CREATED_AT = "createdAt";
    }
}
