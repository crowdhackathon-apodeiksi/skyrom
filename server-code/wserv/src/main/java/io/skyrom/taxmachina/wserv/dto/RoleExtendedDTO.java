package io.skyrom.taxmachina.wserv.dto;

import io.skyrom.taxmachina.wserv.dto.annotations.DTOMapper;
import io.skyrom.taxmachina.wserv.dto.annotations.FieldMapper;


/**
 *
 * @author petros
 */
@DTOMapper
public class RoleExtendedDTO implements DTO {

    @FieldMapper( field = "description" )
    private String description;

    @FieldMapper( field = "Id" )
    private long Id;

    @FieldMapper( field = "priority" )
    private int priority;

    public String getDescription() {
        return description;
    }

    public void setDescription( String description ) {
        this.description = description;
    }

    public long getId() {
        return Id;
    }

    public void setId( long Id ) {
        this.Id = Id;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority( int priority ) {
        this.priority = priority;
    }

}
