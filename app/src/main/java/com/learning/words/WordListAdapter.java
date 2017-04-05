package com.learning.words;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.learning.words.model.Word;

import java.util.List;

/**
 * Created by liqilin on 2017/2/15.
 */

public class WordListAdapter extends RecyclerView.Adapter<WordListAdapter.ViewHolder> {

    List<Word> mWords;
    LayoutInflater mInflater;
    OnItemClickListener mItemClickListener;

    public WordListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(R.layout.list_item_word, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.setWord(mWords.get(position));

        holder.mLeftBlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mItemClickListener != null) {
                    mItemClickListener.onItemSelectorClicked(holder.getLayoutPosition());
                }
            }
        });

        holder.mRightBlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mItemClickListener != null) {
                    mItemClickListener.onItemClicked(holder.getLayoutPosition());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mWords == null) {
            return 0;
        }
        return mWords.size();
    }

    public void setData(List<Word> words) {
        mWords = words;
    }

    public void setItemClickListener(OnItemClickListener listener) {
        mItemClickListener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View mView;
        public TextView mWord;
        public View mSelector;

        public View mLeftBlock;
        public View mRightBlock;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mWord = (TextView) view.findViewById(R.id.word);
            mSelector = view.findViewById(R.id.selector);
            mLeftBlock = view.findViewById(R.id.left_block);
            mRightBlock = view.findViewById(R.id.right_block);
        }

        public void setWord(Word word) {
            mWord.setText(word.mWord);
            mSelector.setSelected(word.selected);
        }
    }

    public interface OnItemClickListener {
        void onItemSelectorClicked(int position);
        void onItemClicked(int position);
    }
}
