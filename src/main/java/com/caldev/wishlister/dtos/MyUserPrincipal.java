package com.caldev.wishlister.dtos;

import com.caldev.wishlister.entities.UserAccount;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

public class MyUserPrincipal implements UserDetails {

    private UserAccount user;

    public MyUserPrincipal(UserAccount user){
        this.user = user;
    }

    public UUID getId(){
        return user.getId();
    }


    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public String getPassword(){
        return user.getPassword();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return user.getAuthorities();
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
        return user;
    }
}
