package com.twocity.apps.latte.data.api.model;

public class OAuthToken {

    private String access_token;

    private String expire_in;
    private long uid;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getExpire_in() {
        return expire_in;
    }

    public void setExpire_in(String expire_in) {
        this.expire_in = expire_in;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    @Override
    public String toString() {
        return "OAuthToken{" +
                "access_token='" + access_token + '\'' +
                ", expire_in='" + expire_in + '\'' +
                ", uid=" + uid +
                '}';
    }
}
