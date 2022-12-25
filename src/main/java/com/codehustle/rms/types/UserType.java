package com.codehustle.rms.types;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.HashSet;
import java.util.Set;

public enum UserType {
    MANAGER("M","MANAGER"),
    SUPERVISOR("S","SUPERVISOR"),
    WORKER("W","WORKER");

    private final String type;
    private final String name;

     UserType(String type,String name){
        this.type = type;this.name= name;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public Set<SimpleGrantedAuthority> getGrantedAuthorities(){
        Set<SimpleGrantedAuthority> simpleGrantedAuthorities = new HashSet<>();
        simpleGrantedAuthorities.add(new SimpleGrantedAuthority(this.getType()));
        return simpleGrantedAuthorities;
    }

    public static Set<SimpleGrantedAuthority> getAuthorityForUser(String userType){
        Set<SimpleGrantedAuthority> simpleGrantedAuthorities = new HashSet<>();
         if(userType == null)
             return null;
         for (UserType type : values()){
             if(type.getType().equals(userType)){
                 simpleGrantedAuthorities.add(new SimpleGrantedAuthority(type.toString()));
             }
         }
         return simpleGrantedAuthorities;
    }
}
