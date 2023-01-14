
package com.streamavailability.Model;


import com.google.gson.annotations.SerializedName;



public class Platforms {

    @SerializedName("display_priority")
    private Long mDisplayPriority;
    @SerializedName("logo_path")
    private String mLogoPath;
    @SerializedName("provider_id")
    private Long mProviderId;
    @SerializedName("provider_name")
    private String mProviderName;

    public Long getDisplayPriority() {
        return mDisplayPriority;
    }

    public void setDisplayPriority(Long displayPriority) {
        mDisplayPriority = displayPriority;
    }

    public String getLogoPath() {
        return mLogoPath;
    }

    public void setLogoPath(String logoPath) {
        mLogoPath = logoPath;
    }

    public Long getProviderId() {
        return mProviderId;
    }

    public void setProviderId(Long providerId) {
        mProviderId = providerId;
    }

    public String getProviderName() {
        return mProviderName;
    }

    public void setProviderName(String providerName) {
        mProviderName = providerName;
    }

}
