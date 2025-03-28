package com.library.app.entity;

public enum Role {
    USER("USER"),
    ADMIN("ADMIN");

    private final String roleName;

    Role(String roleName){
        this.roleName = roleName;
    }

    public String getRoleName(){
        return roleName;
    }
}
