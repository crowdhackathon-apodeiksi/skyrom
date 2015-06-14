package io.skyrom.taxmachina.wserv.auth;

import io.skyrom.taxmachina.origin.dao.UserDAO;
import io.skyrom.taxmachina.origin.domain.User;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 *
 * @author Petros
 */
public class UserDetailsServiceManager implements UserDetailsService, ApplicationContextAware {

    private ApplicationContext ac;
    private UserDAO userDAO;

    @Override
    public UserDetails loadUserByUsername( String email ) {
        User user = ( User ) userDAO.fetch( email );
        if ( user == null ) {
            throw new UsernameNotFoundException( "No such User: " + email );
        } else if ( user.getRoles().isEmpty() ) {
            throw new UsernameNotFoundException( "User " + email + " has no authorities" );
        }
        UserDetailsManager udm = ( UserDetailsManager ) ac.getBean( "userDetailsManager" );
        udm.setUser( user );
        return udm;
    }

    @Override
    public void setApplicationContext( ApplicationContext ac ) throws BeansException {
        this.ac = ac;
    }

    public void setUserDAO( UserDAO userDAO ) {
        this.userDAO = userDAO;
    }
}
