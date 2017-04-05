package com.learning.words.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by liqilin on 2017/2/16.
 */

public class ChineseDefinition implements Parcelable {

    public static final Creator<ChineseDefinition> CREATOR = new Creator<ChineseDefinition>() {
        @Override
        public ChineseDefinition createFromParcel(Parcel source) {
            return new ChineseDefinition(source);
        }

        @Override
        public ChineseDefinition[] newArray(int size) {
            return new ChineseDefinition[size];
        }
    };

    public String mDefinition;
    public String[] mSentences;

    public ChineseDefinition() {
    }

    public ChineseDefinition(Parcel in) {
        mDefinition = in.readString();
        in.readStringArray(mSentences);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mDefinition);
        dest.writeStringArray(mSentences);
    }
}
