package com.learning.words.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by liqilin on 2017/2/16.
 */

public class EnglishDefinition implements Parcelable{

    public static final Creator<EnglishDefinition> CREATOR = new Creator<EnglishDefinition>() {
        @Override
        public EnglishDefinition createFromParcel(Parcel source) {
            return new EnglishDefinition(source);
        }

        @Override
        public EnglishDefinition[] newArray(int size) {
            return new EnglishDefinition[size];
        }
    };

    public String mCharacter;
    public String[] mDefinition;

    public EnglishDefinition() {
    }

    public EnglishDefinition(Parcel in) {
        mCharacter = in.readString();
        in.readStringArray(mDefinition);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mCharacter);
        dest.writeStringArray(mDefinition);
    }
}
