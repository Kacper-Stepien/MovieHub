package com.example.movies_api.state.session;

public interface UserSessionState {
    boolean canBrowseMovies();

    boolean canRateMovies();

    boolean canDeleteMovies();

    String getRoleDescription();
}
