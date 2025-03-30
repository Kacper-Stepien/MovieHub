package com.example.movies_api.visitor;

import com.example.movies_api.model.Genre;

// Visitor 3 ///////////////////////////////////////////////////////////////////////////////////////////////////////////
public interface GenreVisitor {
    void visit(Genre genre, int depth);
}
