package io.skyrom.taxmachina.wserv.dto;

import io.skyrom.taxmachina.wserv.dto.annotations.DTOMapper;
import io.skyrom.taxmachina.wserv.dto.annotations.FieldMapper;

/**
 *
 * @author petros
 */
@DTOMapper
public class RoleSimpleDTO implements DTO {

    @FieldMapper( field = "description" )
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription( String description ) {
        this.description = description;
    }

}
