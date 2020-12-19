package com.example.translatorapp.model.data;

import com.google.gson.annotations.SerializedName;

public class Translation {

    @SerializedName("text")
    String translation;

    public Translation(String translation) {
        this.translation = translation;
    }

    public String getTranslation() {
        return translation;
    }
}