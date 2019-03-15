package com.teletorflix.app.model;

public enum Role {
    ADMIN, USER, AUTHENTICATED;

    public String roleName() {
        return "Role_" + this.toString();
    }
}
