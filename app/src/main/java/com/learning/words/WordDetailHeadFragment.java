package com.learning.words;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.learning.words.model.Word;

/**
 * Created by liqilin on 2017/3/30.
 */

public class WordDetailHeadFragment extends Fragment {

    public static final String WORD_KEY = "word_key";

    private View mBackground;
    private TextView mText;

    private Word mWord;

    public static WordDetailHeadFragment newInstance(Word word) {
        WordDetailHeadFragment fragment = new WordDetailHeadFragment();

        Bundle bundle = new Bundle();
        bundle.putParcelable(WORD_KEY, word);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pager_item_word, container, false);
        mBackground = view.findViewById(R.id.background);
        mText = (TextView) view.findViewById(R.id.word_text);

        Bundle bundle = getArguments();
        mWord = bundle.getParcelable(WORD_KEY);

        mText.setText(mWord.mWord);
        return view;
    }

    public void onHeadScroll(float fraction) {
        mBackground.setScaleX(fraction * 0.5f + 1.0f);
    }
}
