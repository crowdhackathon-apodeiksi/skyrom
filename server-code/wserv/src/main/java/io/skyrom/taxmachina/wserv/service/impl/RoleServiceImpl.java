package io.skyrom.taxmachina.wserv.service.impl;


import io.skyrom.taxmachina.origin.dao.RoleDAO;
import io.skyrom.taxmachina.origin.domain.Role;
import io.skyrom.taxmachina.wserv.service.RoleService;
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
public class RoleServiceImpl implements RoleService, ApplicationContextAware {

    private ApplicationContext ac;

    @Autowired( required = true )
    private RoleDAO roleDAO;

    @Override
    public void setApplicationContext( ApplicationContext ac ) throws BeansException {
        this.ac = ac;
    }

    @Override
    public Role read( long Id ) {
        return roleDAO.fetch( Id );
    }

    public void setRoleDAO( RoleDAO roleDAO ) {
        this.roleDAO = roleDAO;
    }

}
