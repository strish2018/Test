package com.strish.android.test;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class MediaItem {


    @SerializedName("media-metadata")
    private List<MediaMetadataItem> mediaMetadata;

    @SerializedName("caption")
    private String caption;

    public void setMediaMetadata(List<MediaMetadataItem> mediaMetadata) {
        this.mediaMetadata = mediaMetadata;
    }

    public List<MediaMetadataItem> getMediaMetadata() {
        return mediaMetadata;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getCaption() {
        return caption;
    }

    public String getLargestImageURL(){
        int maxWidth = mediaMetadata.get(0).getWidth();
        int maxIndex = 0;
        for (int i = 1; i < mediaMetadata.size(); i++){
            if(mediaMetadata.get(i).getWidth() > maxWidth){
                maxWidth = mediaMetadata.get(i).getWidth();
                maxIndex = i;
            }
        }
        return mediaMetadata.get(maxIndex).getUrl();
    }

}