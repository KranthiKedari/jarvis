package com.kk.jarvis.auth;

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

    private AtomicReference<Boolean> authenticated = new AtomicReference<Boolean>(null);
    private AtomicReference<Boolean> authorized = new AtomicReference<Boolean>(null);

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
