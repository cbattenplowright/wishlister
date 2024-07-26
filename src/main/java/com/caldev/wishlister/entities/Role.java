package com.caldev.wishlister.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name", nullable = false)
    private String roleName;
    @JsonIgnoreProperties({"roles"})
    @ManyToMany(mappedBy="roles")
    private Set<UserAccount> userAccounts;

    protected Role() {}

    public Role(String roleName, Set<UserAccount> userAccounts) {
        this.roleName = roleName;
        this.userAccounts = userAccounts;
    }

//    Getters and Setters

    public Long getId() {
        return id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", roleName='" + roleName + '\'' +
                ", users=" + userAccounts +
                '}';
    }
}
