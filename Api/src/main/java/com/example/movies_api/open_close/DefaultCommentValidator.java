package com.example.movies_api.open_close;

import com.example.movies_api.model.Comment;

import java.util.Arrays;
import java.util.List;

public class DefaultCommentValidator implements CommentValidator {
    private static final int MIN_LENGTH = 10;
    private static final int MAX_LENGTH = 500;

    private static final List<String> BANNED_WORDS = Arrays.asList("spam", "offensive", "bannedWord");

    @Override
    public void validate(Comment comment) {
        String content = comment.getContent();
        if (content == null || content.trim().isEmpty()) {
            throw new IllegalArgumentException("Treść komentarza nie może być pusta");
        }

        String trimmed = content.trim();
        if (trimmed.length() < MIN_LENGTH) {
            throw new IllegalArgumentException("Komentarz jest za krótki. Minimalna długość to " + MIN_LENGTH + " znaków.");
        }

        if (trimmed.length() > MAX_LENGTH) {
            throw new IllegalArgumentException("Komentarz jest za długi. Maksymalna długość to " + MAX_LENGTH + " znaków.");
        }

        for (String banned : BANNED_WORDS) {
            if (trimmed.toLowerCase().contains(banned.toLowerCase())) {
                throw new IllegalArgumentException("Komentarz zawiera zakazane słowo: " + banned);
            }
        }
    }
}
