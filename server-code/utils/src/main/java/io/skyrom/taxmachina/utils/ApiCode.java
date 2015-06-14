package io.skyrom.taxmachina.utils;

/**
 *
 * @author petros
 */
public enum ApiCode {

    OK( 0 ),
    ERROR( -1 ),
    UNAUTHORIZED( 401 ),;

    private final int value;

    private ApiCode( final int val ) {
        this.value = val;
    }

    public int value() {
        return this.value;
    }
}
