package com.strish.android.test;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.strish.android.test.database.ArticleBaseHelper;
import com.strish.android.test.database.ArticleCursorWrapper;
import com.strish.android.test.database.ArticleDbSchema.ArticleEntry;

import java.util.ArrayList;
import java.util.List;

public class DataLab {

    private static DataLab sDataLab;
    private Context mContext;
    private SQLiteDatabase mDatabase;

    private Article tempArticle;

    public static DataLab get(Context context) {
        if (sDataLab == null) {
            sDataLab = new DataLab(context);
        }
        return sDataLab;
    }

    private DataLab(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new ArticleBaseHelper(mContext).getWritableDatabase();
    }

    public Article getTempArticle() {
        return tempArticle;
    }

    public void setTempArticle(Article tempArticle) {
        this.tempArticle = tempArticle;
    }

    public List<Article> getArticles() {
        List<Article> articles = new ArrayList<>();
        ArticleCursorWrapper cursor = queryArticles(null, null);
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                articles.add(cursor.getArticle());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return articles;
    }

    public void addArticle(Article v) {
        ContentValues values = getContentValues(v);
        mDatabase.insert(ArticleEntry.TABLE_NAME, null, values);
    }

    public void removeArticle(String fieldValue){
        mDatabase.delete(ArticleEntry.TABLE_NAME, ArticleEntry.COLUMN_TITLE + "='" + fieldValue + "'", null);
    }

    public boolean checkForArticleById(String fieldValue) {
        String[] columns = { ArticleEntry.COLUMN_TITLE };
        String selection = ArticleEntry.COLUMN_TITLE + " =?";
        String[] selectionArgs = { fieldValue };
        String limit = "1";

        Cursor cursor = mDatabase.query(ArticleEntry.TABLE_NAME, columns, selection, selectionArgs, null, null, null, limit);
        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        return exists;
    }

    private static ContentValues getContentValues(Article article) {
        ContentValues values = new ContentValues();
        values.put(ArticleEntry.COLUMN_TITLE, article.getTitle());
        values.put(ArticleEntry.COLUMN_BYLINE, article.getByline());
        values.put(ArticleEntry.COLUMN_ABSTRACT, article.getAbstract());
        values.put(ArticleEntry.COLUMN_DATE, article.getPublishedDate());
        values.put(ArticleEntry.COLUMN_CAPTION, article.getMedia().get(0).getCaption());
        values.put(ArticleEntry.COLUMN_THUMBNAIL_URL, article.getMedia().get(0).getLargestImageURL());
        values.put(ArticleEntry.COLUMN_THUMBNAIL_BYTE, article.getFavoriteThumbnailByte());
        return values;
    }

    private ArticleCursorWrapper queryArticles(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                ArticleEntry.TABLE_NAME,
                null, // Columns - null выбирает все столбцы
                whereClause,
                whereArgs,
                null, // groupBy
                null, // having
                null // orderBy
        );
        return new ArticleCursorWrapper(cursor);
    }

}
