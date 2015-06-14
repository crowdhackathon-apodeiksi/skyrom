package io.skyrom.taxmachina.wserv.auth;

import io.skyrom.taxmachina.origin.domain.User;
import io.skyrom.taxmachina.utils.HashVerter;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Petros
 */
public class AuthenticationManager implements AuthenticationProvider, ApplicationContextAware {

    private ApplicationContext ac;
    private HashVerter hashVerter;
    private UserDetailsService uds;

    @Transactional
    @Override
    public Authentication authenticate( Authentication authentication ) {
        String adm = authentication.getName();
        UserDetailsManager ud = ( UserDetailsManager ) uds.loadUserByUsername( adm );
        if ( !ud.isEnabled() || ud.getAuthorities().isEmpty() || !ud.isAccountNonExpired()
                || !ud.isAccountNonLocked() || !ud.isCredentialsNonExpired() ) {
            UsernameNotFoundException e = new UsernameNotFoundException( "User `" + adm + "` not found!" );
            throw e;
        }

        String passwd = authentication.getCredentials().toString();
        User user = ud.getUser();

        String encrypted = null;
        try {
            encrypted = hashVerter.encrypt( passwd, HashVerter.MD5 );
        } catch ( Exception ex ) {
            ex.printStackTrace();
        }

        if ( !( user.getEmail().equals( adm ) && user.getPassword().equals( encrypted ) ) ) {
            UsernameNotFoundException e = new UsernameNotFoundException( "Wrong credentials for user `" + adm + "`!" );
            throw e;
        }

        Authentication auth = new UsernamePasswordAuthenticationToken( ud, ud.getUsername(), ud.getAuthorities() );
        return auth;
    }

    @Override
    public boolean supports( Class<?> authentication ) {
        return authentication.equals( UsernamePasswordAuthenticationToken.class );
    }

    @Override
    public void setApplicationContext( ApplicationContext ac )
            throws BeansException {
        this.ac = ac;
    }

    public void setHashVerter( HashVerter hashVerter ) {
        this.hashVerter = hashVerter;
    }

    public void setUserDetailsService( UserDetailsService uds ) {
        this.uds = uds;
    }

}
