package com.streamavailability;

import androidx.lifecycle.ViewModel;

import com.streamavailability.Model.User;

public class MainViewModel extends ViewModel {
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
