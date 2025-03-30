package com.example.movies_api.visitor;

import com.example.movies_api.crew.CrewGroup;
import com.example.movies_api.crew.CrewMember;
import com.example.movies_api.flyweight.RoleName;

import java.util.HashMap;
import java.util.Map;

// Visitor 2 ///////////////////////////////////////////////////////////////////////////////////////////////////////////
public class CrewRoleCountVisitor implements CrewVisitor {
    private final Map<RoleName, Integer> roleCounts = new HashMap<>();

    @Override
    public void visit(CrewGroup group) {
    }

    @Override
    public void visit(CrewMember member) {
        RoleName role = member.getRole();
        roleCounts.merge(role, 1, Integer::sum);
    }

    public Map<RoleName, Integer> getRoleCounts() {
        return roleCounts;
    }

    public String getReport() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<RoleName, Integer> entry : roleCounts.entrySet()) {
            sb.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
        }
        return sb.toString();
    }
}
