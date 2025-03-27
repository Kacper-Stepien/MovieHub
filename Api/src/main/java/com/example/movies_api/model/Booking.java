package com.example.movies_api.model;

import com.example.movies_api.state.booking.BookingState;
import com.example.movies_api.state.booking.ReservedState;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Powiązania
    @ManyToOne
    @JoinColumn(name = "movie_id")
    private Movie movie;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private LocalDateTime bookingTime = LocalDateTime.now();

    // Trwale zapisany status
    @Column(name = "status")
    private String statusName;

    // Nie zapisujemy stanu wprost — tylko jego reprezentację tekstową
    @Transient
    private BookingState state;

    @PostLoad
    public void initStateAfterLoading() {
        this.state = BookingStatusConverter.toState(this.statusName);
    }

    @PrePersist
    @PreUpdate
    public void updateStatusBeforeSave() {
        if (state != null) {
            this.statusName = state.getStatusName();
        }
    }

    public Booking(Movie movie, User user) {
        this.movie = movie;
        this.user = user;
        this.state = new ReservedState();
        this.statusName = state.getStatusName();
        this.bookingTime = LocalDateTime.now();
    }

    public void setState(BookingState state) {
        this.state = state;
        this.statusName = state.getStatusName();
    }

    public void confirm() {
        state.confirm(this);
    }

    public void cancel() {
        state.cancel(this);
    }

    public String notifyUser() {
        return state.notifyUser();
    }

    public String getStatus() {
        return statusName;
    }
}
