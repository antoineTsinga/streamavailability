package com.streamavailability.Model;

import java.util.ArrayList;

public class ProviderResponse {

    private ArrayList<Provider> results;

    public ProviderResponse(){

    }

    public ProviderResponse(ArrayList<Provider> results) {
        this.results = results;
    }

    public ArrayList<Provider> getResults() {
        return results;
    }

    public void setResults(ArrayList<Provider> results) {
        this.results = results;
    }
}
