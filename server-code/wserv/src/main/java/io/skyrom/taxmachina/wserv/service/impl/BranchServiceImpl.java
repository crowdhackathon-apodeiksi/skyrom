package io.skyrom.taxmachina.wserv.service.impl;

import io.skyrom.taxmachina.origin.dao.BranchDAO;
import io.skyrom.taxmachina.origin.domain.Branch;
import io.skyrom.taxmachina.wserv.adapter.BranchAdapter;
import io.skyrom.taxmachina.wserv.service.BranchService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

/**
 *
 * @author Petros
 */
@Service
public class BranchServiceImpl implements BranchService, ApplicationContextAware {
    private ApplicationContext ac;

    @Autowired(required = true)
    private BranchDAO branchDAO;
    
    @Override
    public Branch getBranch( long Id ) {
        return branchDAO.fetch( Id );
    }

    @Override
    public void setApplicationContext( ApplicationContext ac ) throws BeansException {
        this.ac = ac;
    }

    @Override
    public Branch getBranchByTin( String tin ) {
        return branchDAO.fetchByTin( tin );
    }

    @Override
    public Branch createBranch( BranchAdapter branchAdapter ) {
        Branch branch = ac.getBean( Branch.class );
        branch.setName( branchAdapter.getName() );
        branch.setTin( branchAdapter.getTin() );
        return branchDAO.add( branch );
    }

}
