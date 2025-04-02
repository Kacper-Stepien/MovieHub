package com.example.movies_api.model;

import com.example.movies_api.memento.user_memento.UserMemento;
import com.example.movies_api.state.session.UserSessionState;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Boolean locked;
    private Boolean enabled;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")
    )

    private Set<UserRole> roles = new HashSet<>();


    //observer pattern 1/3 2/3 [added code]
    @ManyToMany
    @JoinTable(
            name = "movie_subscriptions",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "movie_id")
    )
    private Set<Movie> subscribedMovies = new HashSet<>();
    /// ///////////////////

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toSet());
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Transient
    private UserSessionState sessionState;

    public void setSessionState(UserSessionState sessionState) {
        this.sessionState = sessionState;
    }

    public UserSessionState getSessionState() {
        return this.sessionState;
    }

    public boolean canBrowseMovies() {
        return sessionState != null && sessionState.canBrowseMovies();
    }

    public boolean canRateMovies() {
        return sessionState != null && sessionState.canRateMovies();
    }

    public boolean canDeleteMovies() {
        return sessionState != null && sessionState.canDeleteMovies();
    }

    public String getRoleDescription() {
        return sessionState != null ? sessionState.getRoleDescription() : "Nieznany stan sesji";
    }

    //wzorzec pamiątka do zapisywania stanu obiektu użytkownika na wypadek gdyby chciał cofnąć wprowadzone zmiany:
    public UserMemento saveToMemento() {
        return new UserMemento(this.firstName, this.lastName, this.email);
    }

    public void restoreFromMemento(UserMemento memento) {
        this.firstName = memento.getFirstName();
        this.lastName = memento.getLastName();
        this.email = memento.getEmail();
    }
}

