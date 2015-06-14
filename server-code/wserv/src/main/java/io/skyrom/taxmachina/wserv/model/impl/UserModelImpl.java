package io.skyrom.taxmachina.wserv.model.impl;


import io.skyrom.taxmachina.origin.domain.Role;
import io.skyrom.taxmachina.origin.domain.User;
import io.skyrom.taxmachina.wserv.adapter.UserAdapter;
import io.skyrom.taxmachina.wserv.dto.DTO;
import io.skyrom.taxmachina.wserv.dto.DTOFactory;
import io.skyrom.taxmachina.wserv.dto.UserSimpleDTO;
import io.skyrom.taxmachina.wserv.model.UserModel;
import io.skyrom.taxmachina.wserv.service.RoleService;
import io.skyrom.taxmachina.wserv.service.UserService;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author petros
 */
@Component
@EnableTransactionManagement
public class UserModelImpl implements UserModel, ApplicationContextAware {

    private ApplicationContext ac;

    @Autowired( required = true )
    private UserService userService;

    @Autowired( required = true )
    private RoleService roleService;

    @Override
    public void setApplicationContext( ApplicationContext ac ) throws BeansException {
        this.ac = ac;
    }

    @Transactional
    @Override
    public DTO createUser( UserAdapter userAdapter ) throws Exception {
        Set<Role> roles = new HashSet();
        roles.add( roleService.read( Role.ROLE_USER ) );
        User user = userService.create( userAdapter, roles, new Date() );

        DTOFactory dtoFactory = ac.getBean( DTOFactory.class );
        return dtoFactory.build( user, UserSimpleDTO.class );
    }

    @Transactional
    @Override
    public DTO updateUser( long Id, UserAdapter userAdapter ) throws Exception {
        User user = userService.update( Id, userAdapter, null );

        DTOFactory dtoFactory = ac.getBean( DTOFactory.class );
        return dtoFactory.build( user, UserSimpleDTO.class );
    }

    @Transactional
    @Override
    public boolean deleteUser( long Id ) throws Exception {
        return userService.delete( Id );
    }

    @Transactional
    @Override
    public DTO readUser( long Id ) throws Exception {
        User user = userService.read( Id );

        DTOFactory dtoFactory = ac.getBean( DTOFactory.class );
        return dtoFactory.build( user, UserSimpleDTO.class );
    }

    public void setUserService( UserService userService ) {
        this.userService = userService;
    }

    public void setRoleService( RoleService roleService ) {
        this.roleService = roleService;
    }

}
