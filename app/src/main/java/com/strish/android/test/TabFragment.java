package com.strish.android.test;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.strish.android.test.api.MostPopularApi;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import maes.tech.intentanim.CustomIntent;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TabFragment extends android.support.v4.app.Fragment implements UpdateableFragment {

    public static final String ARGS_TAB_NUM = "args_tab_num";

    private DataLab dataLab;

    private RecyclerView mRecyclerView;
    private ArticleAdapter mAdapter;
    private int scrollPosition;

    private ArrayList<Article> data;

    private int tab_num;

    public static TabFragment newInstance(int i) {
        Bundle args = new Bundle();
        TabFragment fragment = new TabFragment();
        args.putInt(ARGS_TAB_NUM, i);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState != null){
            scrollPosition = savedInstanceState.getInt("scrollPosition");
        } else{
            scrollPosition = 0;
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab_fragment_layout, container, false);
        tab_num = getArguments().getInt(ARGS_TAB_NUM);

        dataLab = DataLab.get(getContext());

        mRecyclerView = v.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new ArticleAdapter(new ArrayList<Article>());
        mRecyclerView.setAdapter(mAdapter);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.nytimes.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MostPopularApi mostPopularApi = retrofit.create(MostPopularApi.class);

        Call<JSONResponse> call;
        switch (tab_num) {
            case 0:
                call = mostPopularApi.getMostEmailedArticles();
                break;
            case 1:
                call = mostPopularApi.getMostSharedArticles();
                break;
            case 2:
                call = mostPopularApi.getMostViewedArticles();
                break;
            default:
                call = mostPopularApi.getMostEmailedArticles();
        }
        call.enqueue(new Callback<JSONResponse>() {
            @Override
            public void onResponse(Call<JSONResponse> call, Response<JSONResponse> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getActivity(), "Error code: " + response.code(), Toast.LENGTH_LONG);
                    return;
                }
                JSONResponse jsonResponse = response.body();
                data = new ArrayList<>(Arrays.asList(jsonResponse.getResults()));
                mAdapter = new ArticleAdapter(data);
                mRecyclerView.setAdapter(mAdapter);
                mRecyclerView.scrollToPosition(scrollPosition);
            }

            @Override
            public void onFailure(Call<JSONResponse> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG);
            }
        });

        return v;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        int positionView = ((LinearLayoutManager)mRecyclerView.getLayoutManager()).findFirstVisibleItemPosition();
        outState.putInt("scrollPosition", positionView);
    }

    @Override
    public void update(int i) {
        if(i != tab_num){
            mAdapter.notifyDataSetChanged();
        }
    }

    private class ArticleHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView mImageView;
        private TextView mTitleTextView;
        private TextView mBylineTextView;
        private ImageButton mImageButton;
        public Article mArticle;

        public ArticleHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            mImageView = itemView.findViewById(R.id.image_view_list);
            mTitleTextView = itemView.findViewById(R.id.list_item_title_text_view);
            mBylineTextView = itemView.findViewById(R.id.list_item_byline_text_view);
            mImageButton = itemView.findViewById(R.id.list_item_favorites_button);
        }

        public void bindArticle(Article article) {
            mArticle = article;
            Picasso.get().load(mArticle.getMedia().get(0).getLargestImageURL()).fit().centerCrop().into(mImageView);
            mTitleTextView.setText(mArticle.getTitle());
            mBylineTextView.setText(mArticle.getByline());
            if (dataLab.checkForArticleById(mArticle.getTitle())) {
                mArticle.setFavorite(true);
                mImageButton.setImageResource(R.drawable.ic_favorite_on);
            } else{
                mArticle.setFavorite(false);
                mImageButton.setImageResource(R.drawable.ic_favorite_off);
            }
            mImageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mArticle.setFavorite(!mArticle.isFavorite());
                    if (mArticle.isFavorite()) {
                        mImageButton.setImageResource(R.drawable.ic_favorite_on);

                        Bitmap bitmap = ((BitmapDrawable) mImageView.getDrawable()).getBitmap();
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                        byte[] imageInByte = baos.toByteArray();
                        mArticle.setFavoriteThumbnailByte(imageInByte);
                        dataLab.addArticle(mArticle);

                    } else {
                        mImageButton.setImageResource(R.drawable.ic_favorite_off);
                        dataLab.removeArticle(mArticle.getTitle());
                    }
                    ((MainActivity)getActivity()).updateAdapter(tab_num);
                }
            });
        }

        @Override
        public void onClick(View v) {
            dataLab.setTempArticle(mArticle);
            Intent intent = new Intent(getActivity(), ArticleActivity.class);
            startActivity(intent);
            CustomIntent.customType(getActivity(), "fadein-to-fadeout");
        }
    }

    private class ArticleAdapter extends RecyclerView.Adapter<ArticleHolder> {
        private List<Article> mArticles;

        public ArticleAdapter(List<Article> articles) {
            mArticles = articles;
        }

        @Override
        public ArticleHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.list_item, parent, false);
            return new ArticleHolder(view);
        }

        @Override
        public void onBindViewHolder(ArticleHolder holder, int position) {
            Article article = mArticles.get(position);
            holder.bindArticle(article);
        }

        @Override
        public int getItemCount() {
            return mArticles.size();
        }
    }

}
