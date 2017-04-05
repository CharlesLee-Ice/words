package com.learning.words;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.learning.words.behavior.ScrollViewBehavior;
import com.learning.words.model.ChineseDefinition;
import com.learning.words.model.EnglishDefinition;
import com.learning.words.model.Word;
import com.learning.words.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liqilin on 2017/2/17.
 */

public class WordDetailActivity extends AppCompatActivity implements HeaderView.OnHeadScroll{

    private HeaderView mHeaderView;
    private ViewPager mViewPager;
    private WordPagerAdapter mPagerAdapter;
    private RecyclerView mScrollView;
    private WordDetailAdapter mRecyclerAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_word_detail);

        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mPagerAdapter = new WordPagerAdapter(getSupportFragmentManager());
        mPagerAdapter.setData(WordsHelper.getWordsHelper(this).getWords());
        mViewPager.setAdapter(mPagerAdapter);

        mViewPager.setOffscreenPageLimit(2);
        mViewPager.setPageMargin((int) Utils.convertDpToPixel(40.0f, this));

        mHeaderView = (HeaderView) findViewById(R.id.header_view);
        mHeaderView.setHeadScrollListener(this);

        mScrollView = (RecyclerView) findViewById(R.id.scroll_view);
        mScrollView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                ViewCompat.offsetTopAndBottom(mScrollView, mHeaderView.getBottom() - mScrollView.getTop());
                CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) mScrollView.getLayoutParams();
                params.height = Utils.getScreenHeight(WordDetailActivity.this) - mHeaderView.getHeight();
                mScrollView.setLayoutParams(params);
                mScrollView.setAlpha(0);
                ScrollViewBehavior behavior = (ScrollViewBehavior)((CoordinatorLayout.LayoutParams) mScrollView.getLayoutParams()).getBehavior();
                behavior.setDependencyTop(mHeaderView.getTop());
            }
        });

        mRecyclerAdapter = new WordDetailAdapter(this);
        mRecyclerAdapter.setData(WordsHelper.getWordsHelper(this).getWords().get(0));
        mScrollView.setAdapter(mRecyclerAdapter);
    }

    @Override
    public void onScrollFraction(float fraction) {
        mPagerAdapter.onScrollFraction(mViewPager.getCurrentItem(), fraction);
    }

    private static class WordDetailAdapter extends RecyclerView.Adapter<WordDetailAdapter.ViewHolder> {

        private Word mWord;
        private List<String> mDefinitions = new ArrayList<>();
        private LayoutInflater mInflater;

        public WordDetailAdapter(Context context) {
            mInflater = LayoutInflater.from(context);
        }

        @Override
        public WordDetailAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(mInflater.inflate(R.layout.list_item_word_detail, parent, false));
        }

        @Override
        public void onBindViewHolder(WordDetailAdapter.ViewHolder holder, int position) {
            holder.mTextView.setText(mDefinitions.get(position));
        }

        @Override
        public int getItemCount() {
            return mDefinitions.size();
        }

        public void setData(Word word) {
            mWord = word;
            mDefinitions.clear();

            int i = 0;
            int j = 0;
            if (mWord.mEnglishDefinition != null && mWord.mEnglishDefinition.length > 0) {
                for (i = 0; i < mWord.mEnglishDefinition.length; i++) {
                    EnglishDefinition english = mWord.mEnglishDefinition[i];
                    if (english == null || english.mDefinition == null || english.mDefinition.length <= 0 ) {
                        continue;
                    }
                    for (j = 0; j < english.mDefinition.length; j++) {
                        mDefinitions.add(english.mDefinition[j]);
                    }
                }
            }

            if (mWord.mChineseDefinition != null && mWord.mChineseDefinition.length > 0) {
                for (i = 0; i < mWord.mChineseDefinition.length; i++) {
                    ChineseDefinition chinese = mWord.mChineseDefinition[i];
                    if (chinese == null) {
                        continue;
                    }
                    if (!TextUtils.isEmpty(chinese.mDefinition)) {
                        mDefinitions.add(chinese.mDefinition);
                    }

                    if (chinese.mSentences == null || chinese.mSentences.length <= 0) {
                        continue;
                    }

                    for (j = 0; j < chinese.mSentences.length; j++) {
                        mDefinitions.add(chinese.mSentences[j]);
                    }
                }
            }
        }

        static class ViewHolder extends RecyclerView.ViewHolder {
            public TextView mTextView;
            public ViewHolder(View view) {
                super(view);
                mTextView = (TextView) view.findViewById(R.id.text_container);
            }
        }
    }
}
