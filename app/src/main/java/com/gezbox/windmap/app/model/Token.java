package com.gezbox.windmap.app.model;

/**
 * Created by zombie on 14-10-29.
 */
public class Token {
    private String token;
    private String expires;
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getExpires() {
        return expires;
    }

    public void setExpires(String expires) {
        this.expires = expires;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
