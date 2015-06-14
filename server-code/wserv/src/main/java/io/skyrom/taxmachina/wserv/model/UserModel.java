package io.skyrom.taxmachina.wserv.model;

import io.skyrom.taxmachina.wserv.adapter.UserAdapter;
import io.skyrom.taxmachina.wserv.dto.DTO;



/**
 *
 * @author petros
 */
public interface UserModel {

    DTO createUser( UserAdapter userAdapter ) throws Exception ;
    
    DTO updateUser( long Id, UserAdapter userAdapter ) throws Exception;
    
    boolean deleteUser( long Id ) throws Exception;
    
    DTO readUser( long Id ) throws Exception;
}
