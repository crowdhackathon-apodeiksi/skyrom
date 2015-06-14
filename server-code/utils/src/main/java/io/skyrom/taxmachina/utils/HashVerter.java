package io.skyrom.taxmachina.utils;

import java.security.MessageDigest;

public class HashVerter {

    public static final String MD5 = "MD5";
    public static final String SHA256 = "SHA-256";
    public static final String SHA1 = "SHA1";

    public String encrypt( String crypto, String hash ) throws Exception {
        byte[] st = crypto.getBytes();
        byte[] sha1hash = new byte[32];
        MessageDigest md;

        md = MessageDigest.getInstance( hash );
        md.update( st, 0, st.length );
        sha1hash = md.digest();

        return convertToHex( sha1hash );
    }

    private String convertToHex( byte[] data ) {
        StringBuilder buf = new StringBuilder();
        for ( int i = 0; i < data.length; i++ ) {
            int halfbyte = ( data[i] >>> 4 ) & 0x0F;
            int two_halfs = 0;
            do {
                if ( ( 0 <= halfbyte ) && ( halfbyte <= 9 ) ) {
                    buf.append( ( char ) ( '0' + halfbyte ) );
                } else {
                    buf.append( ( char ) ( 'a' + ( halfbyte - 10 ) ) );
                }
                halfbyte = data[i] & 0x0F;
            } while ( two_halfs++ < 1 );
        }
        return buf.toString();
    }
}
