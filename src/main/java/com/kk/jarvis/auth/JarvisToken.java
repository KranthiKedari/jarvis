package com.kk.jarvis.auth;

/**
 * Created with IntelliJ IDEA.
 * User: kkedari
 * Date: 5/20/14
 * Time: 12:46 PM
 * To change this template use File | Settings | File Templates.
 */
public interface JarvisToken {


    boolean isValid();


    boolean markAuthenticated(boolean authenticated);


    boolean markAuthorized(boolean markAuthorized);


    boolean isAuthenticated();


    boolean isAuthorized();


    String getAlgorithm();
}
