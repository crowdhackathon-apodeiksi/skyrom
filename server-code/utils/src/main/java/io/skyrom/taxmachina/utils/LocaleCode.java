package io.skyrom.taxmachina.utils;

/**
 *
 * @author petros
 */
public enum LocaleCode {

    EN( "en_EN" ),
    EL( "el_GR" );

    private final String value;

    private LocaleCode( final String val ) {
        this.value = val;
    }

    public String value() {
        return this.value;
    }
}
