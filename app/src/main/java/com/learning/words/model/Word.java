package com.learning.words.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by liqilin on 2017/2/16.
 */

public class Word implements Parcelable {

    public static final Creator<Word> CREATOR = new Creator<Word>() {
        @Override
        public Word createFromParcel(Parcel source) {
            return new Word(source);
        }

        @Override
        public Word[] newArray(int size) {
            return new Word[size];
        }
    };

    public String mWord;
    public String[] mPronounce;
    public String[] mDefinition;
    public EnglishDefinition[] mEnglishDefinition;
    public ChineseDefinition[] mChineseDefinition;

    public boolean selected;

    public Word() {
        selected = true;
    }

    public Word(Parcel in) {
        mWord = in.readString();
        in.readStringArray(mPronounce);
        in.readStringArray(mDefinition);
        mEnglishDefinition = in.createTypedArray(EnglishDefinition.CREATOR);
        mChineseDefinition = in.createTypedArray(ChineseDefinition.CREATOR);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mWord);
        dest.writeStringArray(mPronounce);
        dest.writeStringArray(mDefinition);
        dest.writeTypedArray(mEnglishDefinition, flags);
        dest.writeTypedArray(mChineseDefinition, flags);
    }
}
