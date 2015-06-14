package io.skyrom.taxmachina.origin.dao;

import io.skyrom.taxmachina.origin.commons.GenericDAO;
import io.skyrom.taxmachina.origin.domain.Category;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Petros
 */
public class CategoryDAO extends GenericDAO<Category> {

    public Category fetch( long Id ) {
        Session sess = super.getSession();
        Criteria c = sess.createCriteria( Category.class );
        c.add( Restrictions.eq( "id", Id ).ignoreCase() );
        return ( Category ) c.uniqueResult();
    }

}
