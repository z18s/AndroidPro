package com.example.translatorapp.model.data;

import com.google.gson.annotations.SerializedName;

public class Meanings {

    @SerializedName("translation")
    Translation translation;

    @SerializedName("imageUrl")
    String imageUrl;

    public Meanings(Translation translation, String imageUrl) {
        this.translation = translation;
        this.imageUrl = imageUrl;
    }

    public Translation getTranslation() {
        return translation;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}