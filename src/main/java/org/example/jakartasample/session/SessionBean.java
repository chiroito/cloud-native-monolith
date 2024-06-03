package org.example.jakartasample.session;

import javax.enterprise.context.SessionScoped;
import java.io.Serializable;

@SessionScoped
public class SessionBean implements Serializable {

    private int count = 0;

    public int incrementCount() {
        return getCount();
    }

    private synchronized int getCount() {
        return ++count;
    }
}
