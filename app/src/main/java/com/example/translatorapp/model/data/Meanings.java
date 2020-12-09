package com.example.translatorapp.model.data;

import com.google.gson.annotations.SerializedName;

public class Meanings {

    @SerializedName("translation")
    Translation translation;

    @SerializedName("imageUrl")
    String imageUrl;

    public Translation getTranslation() {
        return translation;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}