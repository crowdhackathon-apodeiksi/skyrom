package io.skyrom.taxmachina.origin.dao;

import io.skyrom.taxmachina.origin.commons.GenericDAO;
import io.skyrom.taxmachina.origin.domain.Role;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author picoGreedy
 */
public class RoleDAO extends GenericDAO<Role> {

    public Role fetch( String description ) {
        Session sess = super.getSession();
        Criteria c = sess.createCriteria( Role.class );
        c.add( Restrictions.eq( "description", description ).ignoreCase() );
        return ( Role ) c.uniqueResult();
    }

    public Role fetch( long Id ) {
        Session sess = super.getSession();
        Criteria c = sess.createCriteria( Role.class );
        c.add( Restrictions.eq( "id", Id ) );
        return ( Role ) c.uniqueResult();
    }
}
