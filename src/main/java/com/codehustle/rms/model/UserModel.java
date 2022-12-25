package com.codehustle.rms.model;

import com.codehustle.rms.entity.User;
import com.codehustle.rms.types.UserType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class UserModel implements UserDetails {

    private Long userId;
    private String username;
    private final String password;
    private String userType;
    private boolean isActive;

    public UserModel(Long userId, String username,String password, String userType, boolean isActive) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.userType = userType;
        this.isActive = isActive;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return UserType.getAuthorityForUser(getUserType());
    }

    @Override
    @JsonIgnore
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
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
        return isActive;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
