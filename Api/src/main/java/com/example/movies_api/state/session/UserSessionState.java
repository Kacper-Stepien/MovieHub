package com.example.movies_api.state.session;


//Segregacji interfejsów (1/3) - istniejący "gruby" interfejs który został podzielony na mniejsze interfejsy
public interface UserSessionState {


    //boolean canBrowseMovies();
    //boolean canRateMovies();
    //boolean canDeleteMovies();

    String getRoleDescription();
}
