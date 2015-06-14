package io.skyrom.taxmachina.utils;

/**
 *
 * @author petros
 */
public enum MessageCode {
    OK( "ok" ),
    ERROR( "error" ),
    USER_READ_SUCCESS( "user.read.success" ),
    USER_READ_ERROR( "user.read.error" ),
    USER_CREATE_SUCCESS( "user.create.success" ),
    USER_CREATE_ERROR( "user.create.error" ),
    USER_EDIT_SUCCESS( "user.edit.success" ),
    USER_EDIT_ERROR( "user.edit.error" ),
    USER_DELETE_SUCCESS( "user.delete.success" ),
    USER_DELETE_ERROR( "user.delete.error" ),
    
    RECEIPT_SCAN_SUCCESS( "receipt.scan.success" ),
    RECEIPT_SCAN_ERROR( "receipt.scan.error" ),
    
    RECEIPT_SAVE_SUCCESS( "receipt.save.success" ),
    RECEIPT_SAVE_ERROR( "receipt.save.error" )
    
    
    ;

    private final String value;

    private MessageCode( final String val ) {
        this.value = val;
    }

    public String value() {
        return this.value;
    }
}
