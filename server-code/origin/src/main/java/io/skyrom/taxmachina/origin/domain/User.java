package io.skyrom.taxmachina.origin.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author petros
 */
@Entity
@Table( name = "TM_USER", indexes = {} )
public class User implements Serializable {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    @Column( name = "id" )
    private Long Id;

    @Column( name = "first_name", length = 255, nullable = false )
    private String firstName;

    @Column( name = "last_name", length = 255, nullable = false )
    private String lastName;

    @Column( name = "email", length = 255, nullable = false, unique = true )
    private String email;

    @Column( name = "password", length = 255, nullable = false )
    private String password;

    @Column( name = "enabled", nullable = false )
    private Boolean enabled;

    @Column( name = "doc", nullable = false, updatable = false )
    @Temporal( javax.persistence.TemporalType.TIMESTAMP )
    private Date doc;

    @ManyToMany( fetch = FetchType.LAZY )
    @JoinTable( name = "TM_USER_ROLE", uniqueConstraints = {
        @UniqueConstraint( columnNames = { "user_id", "role_id" } ) },
            joinColumns = {
                @JoinColumn( name = "user_id", nullable = false ) },
            inverseJoinColumns = {
                @JoinColumn( name = "role_id", nullable = false ) }, indexes = {
                @Index( columnList = "user_id" ),
                @Index( columnList = "role_id" )
            } )
    private Set<Role> roles;
    
    @OneToMany( cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user", orphanRemoval = true )
    private Set<Receipt> receipts;

    public Long getId() {
        return Id;
    }

    public void setId( Long Id ) {
        this.Id = Id;
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

    public String getEmail() {
        return email;
    }

    public void setEmail( String email ) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword( String password ) {
        this.password = password;
    }

    public Boolean isEnabled() {
        return enabled;
    }

    public void setEnabled( Boolean enabled ) {
        this.enabled = enabled;
    }

    public Date getDoc() {
        return doc;
    }

    public void setDoc( Date doc ) {
        this.doc = doc;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles( Set<Role> roles ) {
        this.roles = roles;
    }

    public Set<Receipt> getReceipts() {
        return receipts;
    }

    public void setReceipts( Set<Receipt> receipts ) {
        this.receipts = receipts;
    }
    
}
