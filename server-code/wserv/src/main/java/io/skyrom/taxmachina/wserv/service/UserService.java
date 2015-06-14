package io.skyrom.taxmachina.wserv.service;

import io.skyrom.taxmachina.origin.domain.Role;
import io.skyrom.taxmachina.origin.domain.User;
import io.skyrom.taxmachina.wserv.adapter.UserAdapter;
import java.util.Date;
import java.util.Set;

/**
 *
 * @author petros
 */
public interface UserService {

    User create( UserAdapter userAdapter, Set<Role> roles, Date doc ) throws Exception;

    User read( long Id ) throws Exception;
    
    User read( String uid ) throws Exception;

    User update( long Id, UserAdapter userAdapter, Set<Role> roles ) throws Exception;

    boolean delete( long Id ) throws Exception;
}
