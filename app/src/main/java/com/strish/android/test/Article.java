package com.strish.android.test;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Article {

    @SerializedName("abstract")
    private String mAbstract;
    private List<MediaItem> media;
    private String title;
    private String byline;
    @SerializedName("published_date")
    private String publishedDate;
    private String favoriteCaption;
    private boolean favorite = false;
    private String favoriteThumbnailUrl;
    private byte[] favoriteThumbnailByte;

    public void setAbstract(String mAbstract) {
        this.mAbstract = mAbstract;
    }

    public String getAbstract() {
        return mAbstract;
    }

    public void setMedia(List<MediaItem> media) {
        this.media = media;
    }

    public List<MediaItem> getMedia() {
        return media;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setByline(String byline) {
        this.byline = byline;
    }

    public String getByline() {
        return byline;
    }

    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public Article(String title) {
        this.title = title;
    }

    public String getFavoriteCaption() {
        return favoriteCaption;
    }

    public void setFavoriteCaption(String favoriteCaption) {
        this.favoriteCaption = favoriteCaption;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public String getFavoriteThumbnailUrl() {
        return favoriteThumbnailUrl;
    }

    public void setFavoriteThumbnailUrl(String favoriteThumbnailUrl) {
        this.favoriteThumbnailUrl = favoriteThumbnailUrl;
    }

    public byte[] getFavoriteThumbnailByte() {
        return favoriteThumbnailByte;
    }

    public void setFavoriteThumbnailByte(byte[] favoriteThumbnailByte) {
        this.favoriteThumbnailByte = favoriteThumbnailByte;
    }
}
