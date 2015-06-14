package io.skyrom.taxmachina.wserv.service.impl;

import io.skyrom.taxmachina.origin.dao.UserDAO;
import io.skyrom.taxmachina.origin.domain.Role;
import io.skyrom.taxmachina.origin.domain.User;
import io.skyrom.taxmachina.utils.HashVerter;
import io.skyrom.taxmachina.wserv.adapter.UserAdapter;
import io.skyrom.taxmachina.wserv.service.UserService;
import java.util.Date;
import java.util.Set;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

/**
 *
 * @author petros
 */
@Service
public class UserServiceImpl implements UserService, ApplicationContextAware {

    private ApplicationContext ac;

    @Autowired( required = true )
    private UserDAO userDAO;

    @Autowired( required = true )
    private HashVerter hashVerter;

    @Override
    public void setApplicationContext( ApplicationContext ac ) throws BeansException {
        this.ac = ac;
    }

    @Override
    public User create( UserAdapter userAdapter, Set<Role> roles, Date doc ) throws Exception  {
        User user = ac.getBean( User.class );
        user.setEmail( userAdapter.getEmail() );
        user.setPassword( hashVerter.encrypt( userAdapter.getPassword(), HashVerter.MD5 ) );
        user.setFirstName( userAdapter.getFirstName() );
        user.setLastName( userAdapter.getLastName() );
        user.setEnabled( true );
        user.setDoc( doc );
        user.setRoles( roles );
        userDAO.add( user );
        return user;
    }

    @Override
    public User read( long Id ) {
        return userDAO.fetch( Id );
    }

    @Override
    public User update( long Id, UserAdapter userAdapter, Set<Role> roles ) {
        User user = read( Id );
        user.setFirstName( userAdapter.getFirstName() );
        user.setLastName( userAdapter.getLastName() );
        return userDAO.addOrUpdate( user );
    }

    @Override
    public boolean delete( long Id ) {
        User user = read( Id );
        return userDAO.delete( user );
    }

    public void setUserDAO( UserDAO userDAO ) {
        this.userDAO = userDAO;
    }

    @Override
    public User read( String uid ) throws Exception {
        return userDAO.fetch( uid );
    }

}
