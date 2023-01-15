package com.streamavailability.Model;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;

public class Provider {

    @SerializedName("display_priorities")
    private  HashMap<String, Integer> displayPriorities;
    @SerializedName("display_priority")
    private Long displayPriority;
    @SerializedName("logo_path")
    private String logoPath;
    @SerializedName("provider_id")
    private Long providerId;
    @SerializedName("provider_name")
    private String providerName;


    public Provider() {
    }

    public Provider(HashMap<String, Integer> displayPriorities, Long displayPriority, String logoPath, Long providerId, String providerName) {
        this.displayPriorities = displayPriorities;
        this.displayPriority = displayPriority;
        this.logoPath = logoPath;
        this.providerId = providerId;
        this.providerName = providerName;
    }

    public HashMap<String, Integer> getDisplayPriorities() {
        return displayPriorities;
    }

    public void setDisplayPriorities(HashMap<String, Integer> displayPriorities) {
        this.displayPriorities = displayPriorities;
    }

    public Long getDisplayPriority() {
        return displayPriority;
    }

    public void setDisplayPriority(Long displayPriority) {
        this.displayPriority = displayPriority;
    }

    public String getLogoPath() {
        return logoPath;
    }

    public void setLogoPath(String logoPath) {
        this.logoPath = logoPath;
    }

    public Long getProviderId() {
        return providerId;
    }

    public void setProviderId(Long providerId) {
        this.providerId = providerId;
    }

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }
}
