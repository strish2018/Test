package com.strish.android.test.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.strish.android.test.Article;
import com.strish.android.test.database.ArticleDbSchema.ArticleEntry;

public class ArticleCursorWrapper extends CursorWrapper {

    public ArticleCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Article getArticle() {
        String title = getString(getColumnIndex(ArticleEntry.COLUMN_TITLE));
        String byline = getString(getColumnIndex(ArticleEntry.COLUMN_BYLINE));
        String mAbstract = getString(getColumnIndex(ArticleEntry.COLUMN_ABSTRACT));
        String date = getString(getColumnIndex(ArticleEntry.COLUMN_DATE));
        String caption = getString(getColumnIndex(ArticleEntry.COLUMN_CAPTION));
        String thumbnail_url = getString(getColumnIndex(ArticleEntry.COLUMN_THUMBNAIL_URL));
        byte[] thumbnail_byte = getBlob(getColumnIndex(ArticleEntry.COLUMN_THUMBNAIL_BYTE));

        Article article = new Article(title);
        article.setByline(byline);
        article.setAbstract(mAbstract);
        article.setPublishedDate(date);
        article.setFavoriteCaption(caption);
        article.setFavoriteThumbnailUrl(thumbnail_url);
        article.setFavoriteThumbnailByte(thumbnail_byte);
        return article;
    }
}
