package com.example.android.miwok;

/**
 * Created by momintariq on 12/19/16.
 */

public class Word {

    private String defaultTranslation;
    private String miwokTranslation;
    private int imageResourceID;
    private int audioResourceID;
    private boolean isImageSet = false;

    public Word(String defaultWord, String miwokWord, int audioID) {
        defaultTranslation = defaultWord;
        miwokTranslation = miwokWord;
        audioResourceID = audioID;
    }

    public Word(String defaultWord, String miwokWord, int imageID, int audioID) {
        defaultTranslation = defaultWord;
        miwokTranslation = miwokWord;
        imageResourceID = imageID;
        audioResourceID = audioID;
        isImageSet = true;
    }

    public String getDefaultTranslation() {
        return defaultTranslation;
    }

    public String getMiwokTranslation() {
        return miwokTranslation;
    }

    public int getImageResourceID() {
        return imageResourceID;
    }

    public boolean getIsImageSet() {
        return isImageSet;
    }

    public int getAudioResourceID() {
        return audioResourceID;
    }
}
