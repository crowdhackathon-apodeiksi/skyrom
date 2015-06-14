package io.skyrom.taxmachina.origin.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Petros
 */
@Entity
@Table( name = "TM_CATEGORY" )
public class Category implements Serializable {
    
    @Id
    @Column( name = "id" )
    private Long Id;

    @Column( name = "description", length = 255, unique = true, nullable = false )
    private String description;

    public Long getId() {
        return Id;
    }

    public void setId( Long Id ) {
        this.Id = Id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription( String description ) {
        this.description = description;
    }
}
