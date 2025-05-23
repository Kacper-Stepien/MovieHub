package com.example.movies_api.state.session;

public class LoggedInUserState implements UserSessionState,UserSessionState_canBrowseMovies,UserSessionState_canRateMovies,UserSessionState_canDeleteMovies {

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
        return false;
    }

    @Override
    public String getRoleDescription() {
        return "Użytkownik (przeglądanie i ocenianie)";
    }
}
