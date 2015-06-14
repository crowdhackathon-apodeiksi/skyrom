package io.skyrom.taxmachina.origin.commons;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 *
 * @author pk
 */
public abstract class SessionFactoryObject {

    SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory( SessionFactory sessionFactory ) {
        this.sessionFactory = sessionFactory;
    }

    public Session getSession() {
        return sessionFactory.getCurrentSession();
    }
}
