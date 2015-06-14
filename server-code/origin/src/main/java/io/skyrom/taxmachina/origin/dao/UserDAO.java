package io.skyrom.taxmachina.origin.dao;

import io.skyrom.taxmachina.origin.commons.GenericDAO;
import io.skyrom.taxmachina.origin.domain.User;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author picoGreedy
 */
public class UserDAO extends GenericDAO<User> {

    public User fetch( long Id ) {
        Session sess = super.getSession();
        Criteria c = sess.createCriteria( User.class );
        c.add( Restrictions.eq( "id", Id ) );
        return ( User ) c.uniqueResult();
    }

    public User fetch( String email ) {
        Session sess = super.getSession();
        Criteria c = sess.createCriteria( User.class );
        c.add( Restrictions.eq( "email", email ) );
        return ( User ) c.uniqueResult();
    }
}
