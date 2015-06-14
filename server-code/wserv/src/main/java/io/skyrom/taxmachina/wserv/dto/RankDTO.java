package io.skyrom.taxmachina.wserv.dto;

import io.skyrom.taxmachina.wserv.dto.annotations.DTOMapper;

/**
 *
 * @author Petros
 */
@DTOMapper
public class RankDTO {

    private int rank;
    private int users;

    public int getRank() {
        return rank;
    }

    public void setRank( int rank ) {
        this.rank = rank;
    }

    public int getUsers() {
        return users;
    }

    public void setUsers( int users ) {
        this.users = users;
    }

}
