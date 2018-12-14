package com.strish.android.test.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.strish.android.test.database.ArticleDbSchema.ArticleEntry;


public class ArticleBaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "articlesList.db";
    public static final int DATABASE_VERSION = 1;

    public ArticleBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_ARTICLESLIST_TABLE = "CREATE TABLE " +
                ArticleEntry.TABLE_NAME + " (" +
                ArticleEntry.COLUMN_TITLE + " TEXT NOT NULL, " +
                ArticleEntry.COLUMN_BYLINE + " TEXT NOT NULL, " +
                ArticleEntry.COLUMN_ABSTRACT + " TEXT NOT NULL, " +
                ArticleEntry.COLUMN_DATE + " TEXT NOT NULL, " +
                ArticleEntry.COLUMN_CAPTION + " TEXT, " +
                ArticleEntry.COLUMN_THUMBNAIL_URL + " TEXT, " +
                ArticleEntry.COLUMN_THUMBNAIL_BYTE + " BLOB" +
                ");";
        sqLiteDatabase.execSQL(SQL_CREATE_ARTICLESLIST_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + ArticleEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

}
