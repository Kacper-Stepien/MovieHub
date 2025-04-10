package com.example.movies_api.state.session;

public class GuestUserState implements UserSessionState,UserSessionState_canBrowseMovies,UserSessionState_canRateMovies,UserSessionState_canDeleteMovies {

    @Override
    public boolean canBrowseMovies() {
        return true;
    }

    @Override
    public boolean canRateMovies() {
        return false;
    }

    @Override
    public boolean canDeleteMovies() {
        return false;
    }

    @Override
    public String getRoleDescription() {
        return "Gość (tylko przeglądanie)";
    }
}
