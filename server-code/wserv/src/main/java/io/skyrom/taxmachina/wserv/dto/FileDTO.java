package io.skyrom.taxmachina.wserv.dto;

import io.skyrom.taxmachina.wserv.dto.annotations.DTOMapper;

/**
 *
 * @author Petros
 */
@DTOMapper
public class FileDTO implements DTO{
    
    private String path;

    public String getPath() {
        return path;
    }

    public void setPath( String path ) {
        this.path = path;
    }
    
    
}
