package io.skyrom.taxmachina.origin.dao;

import io.skyrom.taxmachina.origin.commons.GenericDAO;
import io.skyrom.taxmachina.origin.domain.Receipt;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Petros
 */
public class ReceiptDAO extends GenericDAO<Receipt> {

    public Receipt fetch( long Id ) {
        Session sess = super.getSession();
        Criteria c = sess.createCriteria( Receipt.class );
        c.add( Restrictions.eq( "id", Id ).ignoreCase() );
        return ( Receipt ) c.uniqueResult();
    }
    
    public Receipt fetchByCcn( String ccn ) {
        Session sess = super.getSession();
        Criteria c = sess.createCriteria( Receipt.class );
        c.add( Restrictions.eq( "ccn", ccn ).ignoreCase() );
        return ( Receipt ) c.uniqueResult();
    }
    
    public Receipt fetchForOcr( String tin, double price, int serial ) {
        Session sess = super.getSession();
        Criteria c = sess.createCriteria( Receipt.class ).createAlias( "branch", "b");
        c.add( Restrictions.eq( "b.tin", tin ) );
        c.add( Restrictions.eq( "price", price ) );
        c.add( Restrictions.eq( "serial", serial ) );
        return ( Receipt ) c.uniqueResult();
    }
}
