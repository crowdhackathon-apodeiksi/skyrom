package io.skyrom.taxmachina.origin.dao;

import io.skyrom.taxmachina.origin.commons.GenericDAO;
import io.skyrom.taxmachina.origin.domain.Branch;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Petros
 */
public class BranchDAO extends GenericDAO<Branch> {

    public Branch fetch( long Id ) {
        Session sess = super.getSession();
        Criteria c = sess.createCriteria( Branch.class );
        c.add( Restrictions.eq( "id", Id ) );
        return ( Branch ) c.uniqueResult();
    }
    
    public Branch fetchByTin( String tin ) {
        Session sess = super.getSession();
        Criteria c = sess.createCriteria( Branch.class );
        c.add( Restrictions.eq( "tin", tin ) );
        return ( Branch ) c.uniqueResult();
    }
}
