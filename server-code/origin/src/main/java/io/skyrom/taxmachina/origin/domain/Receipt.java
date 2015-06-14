package io.skyrom.taxmachina.origin.domain;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
/**
 *
 * @author Petros
 */
@Entity
@Table( name = "TM_RECEIPT", indexes = {
    @Index( columnList = "branch_id" ),
    @Index( columnList = "user_id" )
} )
public class Receipt implements Serializable {
    
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    @Column( name = "id" )
    private Long Id;
    
    @Column(name = "ccn", nullable = false, unique = true, length = 255)
    private String ccn;
    
    @Column(name = "price")
    private double price;
    
    @Column( name = "publication", nullable = false, updatable = false )
    @Temporal( javax.persistence.TemporalType.TIMESTAMP )
    private Date publication;
    
    @Column(name = "serial")
    private int serial;
    
    @Column(name = "vat")
    private double vat; // fpa
    
    @Column( name = "doc", nullable = false, updatable = false )
    @Temporal( javax.persistence.TemporalType.TIMESTAMP )
    private Date doc;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branch_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Branch branch;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;
    
    public Long getId() {
        return Id;
    }

    public void setId( Long Id ) {
        this.Id = Id;
    }

    public String getCcn() {
        return ccn;
    }

    public void setCcn( String ccn ) {
        this.ccn = ccn;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice( double price ) {
        this.price = price;
    }

    public Date getPublication() {
        return publication;
    }

    public void setPublication( Date publication ) {
        this.publication = publication;
    }

    public int getSerial() {
        return serial;
    }

    public void setSerial( int serial ) {
        this.serial = serial;
    }

    public double getVat() {
        return vat;
    }

    public void setVat( double vat ) {
        this.vat = vat;
    }

    public Date getDoc() {
        return doc;
    }

    public void setDoc( Date doc ) {
        this.doc = doc;
    }

    public Branch getBranch() {
        return branch;
    }

    public void setBranch( Branch branch ) {
        this.branch = branch;
    }

    public User getUser() {
        return user;
    }

    public void setUser( User user ) {
        this.user = user;
    }
    
    
}
