package com.caldev.wishlister.dtos;

import com.caldev.wishlister.entities.UserAccount;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

public class MyUserPrincipal implements UserDetails {

    private UserAccount userAccount;

    public MyUserPrincipal(UserAccount userAccount){
        this.userAccount = userAccount;
    }

    public UUID getId(){
        return userAccount.getId();
    }


    @Override
    public String getUsername() {
        return userAccount.getUsername();
    }

    @Override
    public String getPassword(){
        return userAccount.getPassword();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return userAccount.getAuthorities();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public UserAccount getUser(){
        return userAccount;
    }
}
