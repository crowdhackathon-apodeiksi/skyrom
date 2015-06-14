package io.skyrom.taxmachina.origin.domain;

import java.io.Serializable;
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
import javax.persistence.UniqueConstraint;

/**
 *
 * @author Petros
 */
@Entity
@Table( name = "TM_BRANCH" )
public class Branch implements Serializable {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    @Column( name = "id" )
    private Long Id;

    @Column( name = "name", nullable = false, length = 255 )
    private String name;

    @Column( name = "tin", nullable = false, length = 45 )
    private String tin;

    @ManyToMany( fetch = FetchType.LAZY )
    @JoinTable( name = "TM_BRANCH_CATEGORY", uniqueConstraints = {
        @UniqueConstraint( columnNames = { "branch_id", "category_id" } ) },
            joinColumns = {
                @JoinColumn( name = "branch_id", nullable = false ) },
            inverseJoinColumns = {
                @JoinColumn( name = "category_id", nullable = false ) }, indexes = {
                @Index( columnList = "branch_id" ),
                @Index( columnList = "category_id" )
            } )
    private Set<Category> categories;

    @OneToMany( cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "branch", orphanRemoval = true )
    private Set<Receipt> receipts;

    public Long getId() {
        return Id;
    }

    public void setId( Long Id ) {
        this.Id = Id;
    }

    public String getName() {
        return name;
    }

    public void setName( String name ) {
        this.name = name;
    }

    public String getTin() {
        return tin;
    }

    public void setTin( String tin ) {
        this.tin = tin;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories( Set<Category> categories ) {
        this.categories = categories;
    }

    public Set<Receipt> getReceipts() {
        return receipts;
    }

    public void setReceipts( Set<Receipt> receipts ) {
        this.receipts = receipts;
    }

}
