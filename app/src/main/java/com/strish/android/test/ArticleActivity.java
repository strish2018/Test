package com.strish.android.test;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import maes.tech.intentanim.CustomIntent;

public class ArticleActivity extends AppCompatActivity {

    private Article mArticle;
    private ImageView mImageView;
    private TextView mTitleTextView;
    private TextView mBylineTextView;
    private TextView mCaptionTextView;
    private TextView mAbstractTextView;
    private TextView mDateTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.article_layout);
        mArticle = DataLab.get(this).getTempArticle();
        mImageView = findViewById(R.id.image_view_item);
        mTitleTextView = findViewById(R.id.item_title_text_view);
        mBylineTextView = findViewById(R.id.item_byline_text_view);
        mCaptionTextView = findViewById(R.id.item_caption_text_view);
        mAbstractTextView = findViewById(R.id.item_abstract_text_view);
        mDateTextView = findViewById(R.id.item_date_text_view);

        mTitleTextView.setText(mArticle.getTitle());
        mBylineTextView.setText(mArticle.getByline());
        mAbstractTextView.setText(mArticle.getAbstract());
        mDateTextView.setText(mArticle.getPublishedDate());

        if(isNetworkAvailableAndConnected()){
            if(mArticle.getFavoriteThumbnailUrl() != null){
                Picasso.get().load(mArticle.getFavoriteThumbnailUrl()).fit().centerCrop().into(mImageView);
                mCaptionTextView.setText(mArticle.getFavoriteCaption());
            } else{
                Picasso.get().load(mArticle.getMedia().get(0).getLargestImageURL()).fit().centerCrop().into(mImageView);
                mCaptionTextView.setText(mArticle.getMedia().get(0).getCaption());
            }
        } else{
            if(mArticle.getFavoriteThumbnailByte() != null){
                byte[] data = mArticle.getFavoriteThumbnailByte();
                Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
                mImageView.setImageBitmap(bmp);
                mCaptionTextView.setText(mArticle.getFavoriteCaption());
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return(super.onOptionsItemSelected(item));
    }

    private boolean isNetworkAvailableAndConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        boolean isNetworkAvailable = cm.getActiveNetworkInfo() != null;
        boolean isNetworkConnected = isNetworkAvailable && cm.getActiveNetworkInfo().isConnected();
        return isNetworkConnected;
    }

    @Override
    public void finish() {
        super.finish();
        CustomIntent.customType(this, "fadein-to-fadeout");
    }
}
