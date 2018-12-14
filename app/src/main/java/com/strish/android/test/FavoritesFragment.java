package com.strish.android.test;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import java.util.List;

import maes.tech.intentanim.CustomIntent;

public class FavoritesFragment extends android.support.v4.app.Fragment implements UpdateableFragment {

    public static final String ARGS_TAB_NUM = "args_tab_num";

    private DataLab dataLab;

    private RecyclerView mRecyclerView;
    private ArticleAdapter mAdapter;
    private int scrollPosition;

    private int tab_num;

    public static FavoritesFragment newInstance(int i) {
        Bundle args = new Bundle();
        FavoritesFragment fragment = new FavoritesFragment();
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
        mAdapter = new ArticleAdapter(dataLab.getArticles());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.scrollToPosition(scrollPosition);

        return v;
    }

    @Override
    public void update(int i) {
        if (i != tab_num) {
            mAdapter.setArticles(dataLab.getArticles());
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        int positionView = ((LinearLayoutManager)mRecyclerView.getLayoutManager()).findFirstVisibleItemPosition();
        outState.putInt("scrollPosition", positionView);
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
            mImageButton.setImageResource(R.drawable.ic_favorite_on);
            byte[] data = mArticle.getFavoriteThumbnailByte();
            Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
            mImageView.setImageBitmap(bmp);
            mTitleTextView.setText(mArticle.getTitle());
            mBylineTextView.setText(mArticle.getByline());

            mImageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dataLab.removeArticle(mArticle.getTitle());
                    mAdapter.setArticles(dataLab.getArticles());
                    int pos = getAdapterPosition();
                    mAdapter.notifyItemRemoved(pos);
                    ((MainActivity) getActivity()).updateAdapter(tab_num);
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

        public void setArticles(List<Article> articles) {
            mArticles = articles;
        }

    }
}
