package com.learning.words;

import android.content.Context;
import android.content.res.Resources;

import com.learning.words.model.ChineseDefinition;
import com.learning.words.model.EnglishDefinition;
import com.learning.words.model.Word;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by liqilin on 2017/2/16.
 */

public class WordsHelper {

    private static WordsHelper sWordsHelper;
    private Resources mResources;

    private List<Word> mWords;

    private WordsHelper(Context context) {
        mResources = context.getResources();

        initWords();
    }

    public static WordsHelper getWordsHelper(Context context) {
        if (sWordsHelper == null) {
            sWordsHelper = new WordsHelper(context);
        }
        return sWordsHelper;
    }

    private void initWords() {
        mWords = new LinkedList<>();
        try {
            String wordsJson = readWordsFromResource();
            JSONArray jsonArray = new JSONArray(wordsJson);
            JSONObject wordObj;
            for (int i = 0; i < jsonArray.length(); i++) {
                wordObj = jsonArray.getJSONObject(i);
                Word word = new Word();
                word.mWord = wordObj.getString("word");
                word.mPronounce = getStringArray(wordObj, "pronounce");
                word.mDefinition = getStringArray(wordObj, "definition");
                word.mEnglishDefinition = getEnglishDefinition(wordObj);
                word.mChineseDefinition = getChineseDefinition(wordObj);
                mWords.add(word);
            }
        } catch (IOException | JSONException e) {

        }
    }

    public List<Word> getWords() {
        return mWords;
    }

    public int alterWordPosition(int position) {
        if (position >= mWords.size()) {
            return -1;
        }

        Word word = mWords.get(position);
        if (word.selected) {
            word.selected = false;
            mWords.remove(word);
            mWords.add(mWords.size(), word);
            return mWords.indexOf(word);
        } else {
            word.selected = true;
            mWords.remove(word);
            int i = 0;
            for (; i < mWords.size(); i++) {
                if (!mWords.get(i).selected) {
                    mWords.add(i==0 ? 0 : i, word);
                    break;
                }
            }
            if (i == mWords.size()) {
                mWords.add(word);
            }
            return mWords.indexOf(word);
        }
    }

    private String[] getStringArray(JSONObject wordObj, String name) throws JSONException {
        if (!wordObj.has(name)) {
            return null;
        }
        JSONArray array = wordObj.getJSONArray(name);
        String[] strs = new String[array.length()];
        for (int i = 0; i < array.length(); i++) {
            strs[i] = array.getString(i);
        }
        return strs;
    }

    private EnglishDefinition[] getEnglishDefinition(JSONObject wordObj) throws JSONException {
        JSONArray array = wordObj.getJSONArray("english-english");
        EnglishDefinition[] definitions = new EnglishDefinition[array.length()];
        EnglishDefinition def;
        JSONObject single;
        for (int i = 0; i < array.length(); i++) {
            single = array.getJSONObject(i);
            def = new EnglishDefinition();
            def.mCharacter = single.getString("character");
            def.mDefinition = getStringArray(single, "definition");
            definitions[i] = def;
        }
        return definitions;
    }

    private ChineseDefinition[] getChineseDefinition(JSONObject wordObj) throws JSONException {
        JSONArray array = wordObj.getJSONArray("english-chinese");
        ChineseDefinition[] definitions = new ChineseDefinition[array.length()];
        ChineseDefinition def;
        JSONObject single;
        for (int i = 0; i < array.length(); i++) {
            single = array.getJSONObject(i);
            def = new ChineseDefinition();
            def.mDefinition = single.getString("definition");
            def.mSentences = getStringArray(single, "sentences");
            definitions[i] = def;
        }
        return definitions;
    }

    private String readWordsFromResource() throws IOException{
        StringBuilder wordsJson = new StringBuilder();
        InputStream inputStream = mResources.openRawResource(R.raw.words);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line;

        while ((line = reader.readLine()) != null) {
            wordsJson.append(line);
        }

        return wordsJson.toString();
    }
}
