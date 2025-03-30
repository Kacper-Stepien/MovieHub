package com.example.movies_api.visitor;

import com.example.movies_api.crew.CrewGroup;
import com.example.movies_api.crew.CrewMember;

// Visitor 1 ///////////////////////////////////////////////////////////////////////////////////////////////////////////
public interface CrewVisitor {
    void visit(CrewGroup group);
    void visit(CrewMember member);
}
