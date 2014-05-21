package com.kk.jarvis.auth;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.concurrent.atomic.AtomicReference;

/**
 * Created with IntelliJ IDEA.
 * User: kkedari
 * Date: 5/20/14
 * Time: 12:51 PM
 * To change this template use File | Settings | File Templates.
 */
public class JarvisAuthToken implements JarvisToken {

    private String algorithm;



    private int userId;

    private AtomicReference<Boolean> authenticated = new AtomicReference<Boolean>(null);
    private AtomicReference<Boolean> authorized = new AtomicReference<Boolean>(null);

    public JarvisAuthToken() {

    }

    @JsonCreator
    public JarvisAuthToken(
            @JsonProperty("user_id") int userId,
            @JsonProperty("algorithm") String algorithm
    )
    {
        this.userId = userId;
        this.algorithm = algorithm;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public boolean isValid() {
        return isAuthenticated() && isAuthorized();
    }

    @Override
    public boolean markAuthenticated(boolean authenticated) {
        return this.authenticated.compareAndSet(null, authenticated);
    }

    @Override
    public boolean markAuthorized(boolean authorized) {
        return this.authorized.compareAndSet(null, authorized);
    }

    @Override
    public boolean isAuthorized() {
        return authorized.get() != null && authorized.get();
    }

    @Override
    public String getAlgorithm() {
        return algorithm;
    }

    @Override
    public boolean isAuthenticated() {
        return authenticated.get() != null && authenticated.get();
    }
}
