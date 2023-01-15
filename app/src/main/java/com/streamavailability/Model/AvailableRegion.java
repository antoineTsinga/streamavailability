package com.streamavailability.Model;

import com.google.gson.annotations.SerializedName;

public class AvailableRegion {

    @SerializedName("iso_3166_1")
    private String iso;
    @SerializedName("english_name")
    private String englishName;
    @SerializedName("native_name")
    private String nativeName;

    public AvailableRegion() {
    }

    public AvailableRegion(String iso, String englishName, String nativeName) {
        this.iso = iso;
        this.englishName = englishName;
        this.nativeName = nativeName;
    }

    public String getIso() {
        return iso;
    }

    public void setIso(String iso) {
        this.iso = iso;
    }

    public String getEnglishName() {
        return englishName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    public String getNativeName() {
        return nativeName;
    }

    public void setNativeName(String nativeName) {
        this.nativeName = nativeName;
    }
}
