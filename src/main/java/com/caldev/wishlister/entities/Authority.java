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
    @Column(name = "authority_id")
    private Long authorityId;

    @Column(name = "authority", nullable = false)
    private String authority;
    @ManyToMany(mappedBy="authorities", fetch = FetchType.EAGER)
    @JsonIgnoreProperties({"authorities"})
    private Set<UserAccount> userAccounts;

    protected Authority() {}

    public Authority(String authority) {
        this.authority = authority;
    }

//    Getters and Setters

    public Long getAuthorityId() {
        return authorityId;
    }

    @Override
    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public void setAuthorityId(Long authorityId) {
        this.authorityId = authorityId;
    }

    public Set<UserAccount> getUserAccounts() {
        return userAccounts;
    }

    public void setUserAccounts(Set<UserAccount> userAccounts) {
        this.userAccounts = userAccounts;
    }

    @Override
    public String toString() {
        return "Authority{" +
                "authorityId=" + authorityId +
                ", authority='" + authority + '\'' +
                '}';
    }
}
