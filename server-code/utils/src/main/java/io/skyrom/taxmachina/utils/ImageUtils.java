package io.skyrom.taxmachina.utils;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

/**
 *
 * @author Petros
 */
public class ImageUtils {

    private int maxWidth = 150;
    /*
     * if image width is smaller than newWidth, then force convert defines if it will be converted
     */
    public File saveImage( BufferedImage bi, String path, int newWidth, boolean forceResize ) {
        File aFile = null;
        try {
            if ( path.contains( "/" ) ) {
                String pFolder = path.substring( 0, path.lastIndexOf( "/" ) );
                File fPath = new File( pFolder );
                if ( !fPath.exists() ) {
                    fPath.mkdirs();
                }
            }

            String type = path.substring( path.lastIndexOf( "." ) + 1 );

            if ( newWidth > 0 && ( forceResize || bi.getWidth() > newWidth ) ) {
                bi = this.resize( bi, newWidth );
            }
            aFile = new File( path );
            ImageIO.write( bi, type, aFile );
        } catch ( Exception e ) {
            e.printStackTrace();
        }
        return aFile;
    }

    private BufferedImage resize( BufferedImage bi, int newWidth ) {
        int width = bi.getWidth();
        int height = bi.getHeight();

        int newHeight = newWidth * height / width;

        BufferedImage resizedImage = new BufferedImage( newWidth, newHeight, BufferedImage.TYPE_3BYTE_BGR );
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage( bi, 0, 0, newWidth, newHeight, null );
        g.dispose();
        return resizedImage;
    }

    public void cropImage( BufferedImage bi, String path, int x1, int y1, int heigth, int width, String imagePath ) {
        try {
            boolean forceResize = true;
            BufferedImage buff = bi.getSubimage( x1, y1, width, heigth );

            if ( imagePath.contains( "/" ) ) {
                String pFolder = imagePath.substring( 0, imagePath.lastIndexOf( "/" ) );
                File fPath = new File( pFolder );
                if ( !fPath.exists() ) {
                    fPath.mkdirs();
                }
            }

            String type = path.substring( path.lastIndexOf( "." ) + 1 );

            if ( forceResize || buff.getWidth() > maxWidth ) {
                buff = this.resize( buff, maxWidth );
            }

            ImageIO.write( buff, type, new File( imagePath ) );
        } catch ( Exception ex ) {
            ex.printStackTrace();
        }
    }
}
