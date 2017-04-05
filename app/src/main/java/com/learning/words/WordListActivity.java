package com.learning.words;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.RecyclerView;

public class WordListActivity extends AppCompatActivity {

    Toolbar mToolbar;
    RecyclerView mRecyclerView;
    WordListAdapter mRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_list);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("Words");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerAdapter = new WordListAdapter(this);
        mRecyclerAdapter.setData(WordsHelper.getWordsHelper(this).getWords());
        mRecyclerAdapter.setItemClickListener(new WordListAdapter.OnItemClickListener() {
            @Override
            public void onItemSelectorClicked(int position) {
                int newPosition = WordsHelper.getWordsHelper(WordListActivity.this).alterWordPosition(position);
                mRecyclerAdapter.notifyItemChanged(position);
                mRecyclerAdapter.notifyItemMoved(position, newPosition);
            }

            @Override
            public void onItemClicked(int position) {
                startActivity(new Intent(WordListActivity.this, WordDetailActivity.class));
            }
        });
        mRecyclerView.setAdapter(mRecyclerAdapter);
    }
}
