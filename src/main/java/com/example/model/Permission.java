package com.example.model;

public enum Permission {
    DEVELOPERS_READ("DEVELOPERS:READ"),
    DEVELOPERS_WRITE("DEVELOPERS:WRITE");

    private final String permission;

    private Permission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
