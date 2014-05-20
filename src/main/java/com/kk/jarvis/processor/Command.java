package com.kk.jarvis.processor;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: kkedari
 * Date: 5/17/14
 * Time: 4:35 PM
 * To change this template use File | Settings | File Templates.
 */
public interface Command {

    public List<String> execute();

    public int getType();

}
