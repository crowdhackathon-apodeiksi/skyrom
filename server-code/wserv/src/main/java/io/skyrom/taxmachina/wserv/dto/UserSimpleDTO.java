package io.skyrom.taxmachina.wserv.dto;

import io.skyrom.taxmachina.wserv.dto.annotations.DTOMapper;
import io.skyrom.taxmachina.wserv.dto.annotations.FieldMapper;

/**
 *
 * @author petros
 */
@DTOMapper
public class UserSimpleDTO implements DTO {

    @FieldMapper( field = "email" )
    private String email;

    @FieldMapper( field = "firstName" )
    private String firstName;

    @FieldMapper( field = "lastName" )
    private String lastName;

    public String getEmail() {
        return email;
    }

    public void setEmail( String email ) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName( String firstName ) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName( String lastName ) {
        this.lastName = lastName;
    }
}
