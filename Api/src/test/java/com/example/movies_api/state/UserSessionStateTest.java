package com.example.movies_api.state;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;

import com.example.movies_api.model.User;
import com.example.movies_api.model.UserRole;
import com.example.movies_api.state.session.AdminUserState;
import com.example.movies_api.state.session.GuestUserState;
import com.example.movies_api.state.session.LoggedInUserState;
import com.example.movies_api.state.session.UserSessionState;

class UserSessionStateTest {

    @Test
    void guestUser_hasLimitedPermissions() {
        UserSessionState guest = new GuestUserState();

        assertTrue(guest.canBrowseMovies());
        assertFalse(guest.canRateMovies());
        assertFalse(guest.canDeleteMovies());
        assertEquals("Gość (tylko przeglądanie)", guest.getRoleDescription());
    }

    @Test
    void loggedInUser_canBrowseAndRateButNotDelete() {
        UserSessionState user = new LoggedInUserState();

        assertTrue(user.canBrowseMovies());
        assertTrue(user.canRateMovies());
        assertFalse(user.canDeleteMovies());
        assertEquals("Użytkownik (przeglądanie i ocenianie)", user.getRoleDescription());
    }

    @Test
    void adminUser_hasAllPermissions() {
        UserSessionState admin = new AdminUserState();

        assertTrue(admin.canBrowseMovies());
        assertTrue(admin.canRateMovies());
        assertTrue(admin.canDeleteMovies());
        assertEquals("Administrator (pełny dostęp)", admin.getRoleDescription());
    }

    @Test
    void userWithAdminRole_receivesAdminState() {
        User user = new User();
        user.setRoles(Set.of(new UserRole("ADMIN")));
        user.setSessionState(new AdminUserState());

        assertTrue(user.canDeleteMovies());
        assertEquals("Administrator (pełny dostęp)", user.getRoleDescription());
    }

    @Test
    void userWithUserRole_receivesUserState() {
        User user = new User();
        user.setRoles(Set.of(new UserRole("USER")));
        user.setSessionState(new LoggedInUserState());

        assertTrue(user.canRateMovies());
        assertFalse(user.canDeleteMovies());
        assertEquals("Użytkownik (przeglądanie i ocenianie)", user.getRoleDescription());
    }

    @Test
    void userWithNoRoles_receivesGuestState() {
        User user = new User();
        user.setRoles(Set.of());
        user.setSessionState(new GuestUserState());

        assertFalse(user.canRateMovies());
        assertEquals("Gość (tylko przeglądanie)", user.getRoleDescription());
    }
}
