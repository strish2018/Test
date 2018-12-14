package com.strish.android.test.database;

import android.provider.BaseColumns;

public class ArticleDbSchema {

    public ArticleDbSchema(){}

    public static final class ArticleEntry implements BaseColumns {

        public static final String TABLE_NAME = "articlesList";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_BYLINE = "byline";
        public static final String COLUMN_ABSTRACT = "abstract";
        public static final String COLUMN_DATE = "date";
        public static final String COLUMN_CAPTION = "caption";
        public static final String COLUMN_THUMBNAIL_URL = "thumbnail_url";
        public static final String COLUMN_THUMBNAIL_BYTE = "thumbnail_byte";

    }

}
