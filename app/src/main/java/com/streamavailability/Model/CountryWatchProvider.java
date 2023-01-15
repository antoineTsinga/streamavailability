
package com.streamavailability.Model;

import java.util.List;

import com.google.gson.annotations.SerializedName;



public class CountryWatchProvider {

    @SerializedName("flatrate")
    private List<Platforms> mFlatrate;
    @SerializedName("rent")
    private List<Platforms> buy;
    @SerializedName("buy")
    private List<Platforms> rent;

    public CountryWatchProvider() {
    }

    public CountryWatchProvider(List<Platforms> mFlatrate, List<Platforms> buy, List<Platforms> rent) {
        this.mFlatrate = mFlatrate;
        this.buy = buy;
        this.rent = rent;
    }

    public List<Platforms> getmFlatrate() {
        return mFlatrate;
    }

    public void setmFlatrate(List<Platforms> mFlatrate) {
        this.mFlatrate = mFlatrate;
    }

    public List<Platforms> getBuy() {
        return buy;
    }

    public void setBuy(List<Platforms> buy) {
        this.buy = buy;
    }

    public List<Platforms> getRent() {
        return rent;
    }

    public void setRent(List<Platforms> rent) {
        this.rent = rent;
    }
}
