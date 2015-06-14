package io.skyrom.taxmachina.origin.commons;

import org.hibernate.Session;

/**
 *
 * @author pkov
 * @param <T>
 */
public interface DaoObject<T extends Object> {

    Session getSession();

    T add( T t );

    T addOrUpdate( T t );

    T update( T t );

    boolean delete( T t );

    T merge( T t );
}
