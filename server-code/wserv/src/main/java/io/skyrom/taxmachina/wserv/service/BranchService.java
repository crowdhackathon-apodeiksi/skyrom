package io.skyrom.taxmachina.wserv.service;

import io.skyrom.taxmachina.origin.domain.Branch;
import io.skyrom.taxmachina.wserv.adapter.BranchAdapter;

/**
 *
 * @author Petros
 */
public interface BranchService {
    
    Branch getBranch(long Id);
    Branch getBranchByTin(String tin);
    
    Branch createBranch(BranchAdapter branchAdapter);
}
