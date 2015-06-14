package io.skyrom.taxmachina.wserv.adapter;

import java.util.Date;

/**
 *
 * @author Petros
 */
public class ReceiptAdapter {

    private String tin;
    private Integer serial;
    private String date;
    private Double value;
    private Double vat;
    private String ccn;
    
    /* ******************** */
    private Date publication;

    public String getTin() {
        return tin;
    }

    public void setTin( String tin ) {
        this.tin = tin;
    }

    public Integer getSerial() {
        return serial;
    }

    public void setSerial( Integer serial ) {
        this.serial = serial;
    }

    public String getDate() {
        return date;
    }

    public void setDate( String date ) {
        this.date = date;
    }

    public Double getValue() {
        return value;
    }

    public void setValue( Double value ) {
        this.value = value;
    }

    public Double getVat() {
        return vat;
    }

    public void setVat( Double vat ) {
        this.vat = vat;
    }

    public String getCcn() {
        return ccn;
    }

    public void setCcn( String ccn ) {
        this.ccn = ccn;
    }

    public Date getPublication() {
        return publication;
    }

    public void setPublication( Date publication ) {
        this.publication = publication;
    }
    
}
