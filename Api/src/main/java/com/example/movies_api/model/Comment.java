package com.example.movies_api.model;

import com.example.movies_api.open_close.DefaultCommentValidator;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "movie_comment")
@Getter
@Setter
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "movie_id")
    private Movie movie;

    private String content;

    // Abstract 2
    public void validate() {
        /*
        // Wcześniej walidacja była wykonywana bezpośrednio w encji:
        if (content == null || content.trim().isEmpty()) {
            throw new IllegalArgumentException("Treść komentarza nie może być pusta");
        }
        String trimmed = content.trim();
        if (trimmed.length() < 10) {
            throw new IllegalArgumentException("Komentarz jest za krótki. Minimalna długość to 10 znaków.");
        }
        if (trimmed.length() > 500) {
            throw new IllegalArgumentException("Komentarz jest za długi. Maksymalna długość to 500 znaków.");
        }
        for (String banned : Arrays.asList("spam", "offensive", "bannedWord")) {
            if (trimmed.toLowerCase().contains(banned.toLowerCase())) {
                throw new IllegalArgumentException("Komentarz zawiera zakazane słowo: " + banned);
            }
        }
        */
        new DefaultCommentValidator().validate(this);
    }
}
