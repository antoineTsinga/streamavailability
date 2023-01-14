package com.streamavailability.Model;

import java.util.HashMap;

public class WatchProvider {
    private  HashMap<String, CountryWatchProvider> results;


    public WatchProvider() {
    }

    public WatchProvider(HashMap<String, CountryWatchProvider> results) {
        this.results = results;
    }

    public HashMap<String, CountryWatchProvider> getResults() {
        return results;
    }

    public void setResults(HashMap<String, CountryWatchProvider> results) {
        this.results = results;
    }
}
