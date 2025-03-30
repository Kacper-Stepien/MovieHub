package com.example.movies_api.visitor;

import com.example.movies_api.crew.CrewGroup;
import com.example.movies_api.crew.CrewMember;

import java.util.ArrayList;
import java.util.List;

public class CrewReportVisitor implements CrewVisitor {
    private final List<String> report = new ArrayList<>();

    @Override
    public void visit(CrewGroup group) {
        report.add("Grupa: " + group.getName());
    }

    @Override
    public void visit(CrewMember member) {
        report.add("Członek: " + member.getName() + " [" + member.getRole() + "]");
    }

    public List<String> getReport() {
        return report;
    }
}