package com.kk.jarvis.auth;

import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Created with IntelliJ IDEA.
 * User: kkedari
 * Date: 5/20/14
 * Time: 2:32 PM
 * To change this template use File | Settings | File Templates.
 */
@Component
public class JarvisSecurity {

    private ConcurrentHashMap<String, ConcurrentHashMap<String, String>> secrets;
    private ConcurrentHashMap<String, CopyOnWriteArraySet<String>> acls = new ConcurrentHashMap<String, CopyOnWriteArraySet<String>>();

    public ConcurrentHashMap<String, ConcurrentHashMap<String, String>> getSecrets() {
        return secrets;
    }

    public ConcurrentHashMap<String, CopyOnWriteArraySet<String>> getAcls() {
        return acls;
    }

    public String getSecret(String authProvider, String key) {
        return secrets.containsKey(authProvider) && secrets.get(authProvider).containsKey(key)
                ? (String) secrets.get(authProvider).get(key)
                : null;
    }

    public Collection<String> getAcls(String authProvider) {
        return acls.containsKey(authProvider) ? acls.get(authProvider) : null;
    }
}
