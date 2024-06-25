package com.caldev.wishlister;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "name")
    private String roleName;
    @JsonIgnoreProperties({"roles"})
    @ManyToMany(mappedBy="roles")
    private Set<User> users;

    protected Role() {}

    public Role(String roleName) {
        this.roleName = roleName;
    }

//    Getters and Setters

    public Long getId() {
        return id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setName(String name) {
        this.roleName = roleName;
    }
}
