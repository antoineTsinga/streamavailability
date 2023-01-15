package com.streamavailability.Model;

import java.util.ArrayList;

public class ProviderResponse {

    private ArrayList<Platforms> results;

    public ProviderResponse(){}

    public ProviderResponse(ArrayList<Platforms> results) {
        this.results = results;
    }

    public ArrayList<Platforms> getResults() {
        return results;
    }

    public void setResults(ArrayList<Platforms> results) {
        this.results = results;
    }
}
