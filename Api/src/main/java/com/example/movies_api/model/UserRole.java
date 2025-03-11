package com.example.movies_api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
//previously: Lombok builder was used (3/3)
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;

    public UserRole(String name) {
        this.name = name;
    }
}
*/


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;

    public UserRole(String name) {
        this.name = name;
    }

    // Ręczna implementacja wzorca Builder
    public static UserRoleBuilder builder() {
        return new UserRoleBuilder();
    }

    public static class UserRoleBuilder {
        private Long id;
        private String name;
        private String description;

        public UserRoleBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public UserRoleBuilder name(String name) {
            this.name = name;
            return this;
        }

        public UserRoleBuilder description(String description) {
            this.description = description;
            return this;
        }

        public UserRole build() {
            return new UserRole(id, name, description);
        }
    }
}