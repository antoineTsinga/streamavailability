
package com.streamavailability.Model;


import com.google.gson.annotations.SerializedName;



public class Cast {

    @SerializedName("adult")
    private Boolean mAdult;
    @SerializedName("cast_id")
    private Long mCastId;
    @SerializedName("character")
    private String mCharacter;
    @SerializedName("credit_id")
    private String mCreditId;
    @SerializedName("gender")
    private Long mGender;
    @SerializedName("id")
    private Long mId;
    @SerializedName("known_for_department")
    private String mKnownForDepartment;
    @SerializedName("name")
    private String mName;
    @SerializedName("order")
    private Long mOrder;
    @SerializedName("original_name")
    private String mOriginalName;
    @SerializedName("popularity")
    private Double mPopularity;
    @SerializedName("profile_path")
    private String mProfilePath;

    public Boolean getAdult() {
        return mAdult;
    }

    public void setAdult(Boolean adult) {
        mAdult = adult;
    }

    public Long getCastId() {
        return mCastId;
    }

    public void setCastId(Long castId) {
        mCastId = castId;
    }

    public String getCharacter() {
        return mCharacter;
    }

    public void setCharacter(String character) {
        mCharacter = character;
    }

    public String getCreditId() {
        return mCreditId;
    }

    public void setCreditId(String creditId) {
        mCreditId = creditId;
    }

    public Long getGender() {
        return mGender;
    }

    public void setGender(Long gender) {
        mGender = gender;
    }

    public Long getId() {
        return mId;
    }

    public void setId(Long id) {
        mId = id;
    }

    public String getKnownForDepartment() {
        return mKnownForDepartment;
    }

    public void setKnownForDepartment(String knownForDepartment) {
        mKnownForDepartment = knownForDepartment;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public Long getOrder() {
        return mOrder;
    }

    public void setOrder(Long order) {
        mOrder = order;
    }

    public String getOriginalName() {
        return mOriginalName;
    }

    public void setOriginalName(String originalName) {
        mOriginalName = originalName;
    }

    public Double getPopularity() {
        return mPopularity;
    }

    public void setPopularity(Double popularity) {
        mPopularity = popularity;
    }

    public String getProfilePath() {
        return mProfilePath;
    }

    public void setProfilePath(String profilePath) {
        mProfilePath = profilePath;
    }

}
