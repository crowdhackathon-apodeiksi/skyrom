package io.skyrom.taxmachina.origin.commons;

import org.hibernate.Session;

/**
 *
 * @author petros
 * @param <T>
 */
public abstract class GenericDAO<T> extends SessionFactoryObject implements DaoObject<T> {

    @Override
    public T add( T t ) {
        Session session = super.getSession();
        session.save( t );
        return t;
    }

    @Override
    public T addOrUpdate( T t ) {
        Session session = super.getSession();
        session.saveOrUpdate( t );
        return t;
    }

    @Override
    public T update( T t ) {
        Session session = super.getSession();
        session.update( t );
        return t;
    }

    @Override
    public boolean delete( T t ) {
        Session session = super.getSession();
        session.delete( t );
        return true;
    }

    @Override
    public T merge( T t ) {
        Session session = super.getSession();
        session.merge( t );
        return t;
    }

}
