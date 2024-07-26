package com.caldev.wishlister.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;

import java.util.Set;

@Entity
@Table(name = "authorities")
public class Authority implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "authority", nullable = false)
    private String authority;
    @JsonIgnoreProperties({"authorities"})
    @ManyToMany(mappedBy="authorities", fetch = FetchType.EAGER)
    private Set<UserAccount> userAccounts;

    protected Authority() {}

    public Authority(String authority, Set<UserAccount> userAccounts) {
        this.authority = authority;
        this.userAccounts = userAccounts;
    }

//    Getters and Setters

    public Long getId() {
        return id;
    }

    @Override
    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", roleName='" + authority + '\'' +
                '}';
    }
}
