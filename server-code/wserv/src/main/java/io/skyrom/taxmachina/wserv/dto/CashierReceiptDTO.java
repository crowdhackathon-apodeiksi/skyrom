package io.skyrom.taxmachina.wserv.dto;

import io.skyrom.taxmachina.wserv.dto.annotations.DTOMapper;
import io.skyrom.taxmachina.wserv.dto.annotations.FieldMapper;

/**
 *
 * @author Petros
 */
@DTOMapper
public class CashierReceiptDTO implements DTO {
    
    private String tin;
    
    @FieldMapper( field = "ccn" )
    private String ccn;
    
    private String date;
    
    @FieldMapper( field = "serial" )
    private int serial;
    
    @FieldMapper( field = "vat" )
    private double vat;
    
    @FieldMapper( field = "price" )
    private double price;
    
    private String qrcode;

    public String getTin() {
        return tin;
    }

    public void setTin( String tin ) {
        this.tin = tin;
    }

    public String getCcn() {
        return ccn;
    }

    public void setCcn( String ccn ) {
        this.ccn = ccn;
    }

    public String getDate() {
        return date;
    }

    public void setDate( String date ) {
        this.date = date;
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

    public double getPrice() {
        return price;
    }

    public void setPrice( double price ) {
        this.price = price;
    }

    public String getQrcode() {
        return qrcode;
    }

    public void setQrcode( String qrcode ) {
        this.qrcode = qrcode;
    }
    
}
