package com.example.movies_api.state.session;

public class AdminUserState implements UserSessionState {

    @Override
    public boolean canBrowseMovies() {
        return true;
    }

    @Override
    public boolean canRateMovies() {
        return true;
    }

    @Override
    public boolean canDeleteMovies() {
        return true;
    }

    @Override
    public String getRoleDescription() {
        return "Administrator (pełny dostęp)";
    }
}