package io.skyrom.taxmachina.utils.ocr;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.File;
import javax.imageio.ImageIO;
import net.sourceforge.tess4j.Tesseract;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author Petros
 */
public class TesseractWrapper {

    private Tesseract tesseractInstance;

    public void init() {
        tesseractInstance = Tesseract.getInstance();
    }

    public String recogn( String filePath ) throws Exception {
        BufferedImage bi = ImageIO.read( new File( filePath ) );

        BufferedImage gray = new BufferedImage( bi.getWidth(), bi.getHeight(), BufferedImage.TYPE_BYTE_BINARY );
        Graphics g = gray.getGraphics();
        g.drawImage( bi, 0, 0, null );
        g.dispose();

        BufferedImage dstbimg = new BufferedImage( gray.getWidth(), gray.getHeight(), BufferedImage.TYPE_INT_RGB );
        float[] SHARPEN3x3 = {
            0.f, -1.f, 0.f,
            -1.f, 5.0f, -1.f,
            0.f, -1.f, 0.f
        };

        Kernel kernel = new Kernel( 3, 3, SHARPEN3x3 );
        ConvolveOp cop = new ConvolveOp( kernel, ConvolveOp.EDGE_NO_OP, null );
        cop.filter( gray, dstbimg );

        ImageIO.write( dstbimg, "png", new File( "c:/tax_machina/tmp/_e.png" ) );

        ImageIO.scanForPlugins();
        String result = tesseractInstance.doOCR( gray );
        return result;
    }

    public String recognCMD( File file, String fileType ) throws Exception {
        BufferedImage bi = ImageIO.read( file );

        BufferedImage gray = new BufferedImage( bi.getWidth(), bi.getHeight(), BufferedImage.TYPE_BYTE_BINARY );
        Graphics g = gray.getGraphics();
        g.drawImage( bi, 0, 0, null );
        g.dispose();

        BufferedImage dstbimg = new BufferedImage( gray.getWidth(), gray.getHeight(), BufferedImage.TYPE_INT_RGB );
        float[] SHARPEN3x3 = {
            0.f, -1.f, 0.f,
            -1.f, 5.0f, -1.f,
            0.f, -1.f, 0.f
        };

        Kernel kernel = new Kernel( 3, 3, SHARPEN3x3 );
        ConvolveOp cop = new ConvolveOp( kernel, ConvolveOp.EDGE_NO_OP, null );
        cop.filter( gray, dstbimg );

        String tessPath = Long.toString( System.currentTimeMillis() );
        File destFile = new File( "c:/tax_machina/tesseract/" + tessPath + "." + fileType );
        
        ImageIO.write( dstbimg, fileType, destFile );
 
        String command = "C:/Program Files (x86)/Tesseract-OCR/tesseract -psm 3 "
                + destFile.getAbsolutePath() + " c:/tax_machina/tesseract/" + tessPath;
        Process child = Runtime.getRuntime().exec(command);
        child.waitFor();
        String result = FileUtils.readFileToString( new File( "C:/tax_machina/tesseract/" + tessPath + ".txt" ) );

        return result;
    }
    
    public String recognCMD( BufferedImage bi, String fileType ) throws Exception {

        BufferedImage gray = new BufferedImage( bi.getWidth(), bi.getHeight(), BufferedImage.TYPE_BYTE_BINARY );
        Graphics g = gray.getGraphics();
        g.drawImage( bi, 0, 0, null );
        g.dispose();

        BufferedImage dstbimg = new BufferedImage( gray.getWidth(), gray.getHeight(), BufferedImage.TYPE_INT_RGB );
        float[] SHARPEN3x3 = {
            0.f, -1.f, 0.f,
            -1.f, 5.0f, -1.f,
            0.f, -1.f, 0.f
        };

        Kernel kernel = new Kernel( 3, 3, SHARPEN3x3 );
        ConvolveOp cop = new ConvolveOp( kernel, ConvolveOp.EDGE_NO_OP, null );
        cop.filter( gray, dstbimg );

        String tessPath = Long.toString( System.currentTimeMillis() );
        File destFile = new File( "c:/tax_machina/tesseract/" + tessPath + "." + fileType );
        
        ImageIO.write( dstbimg, fileType, destFile );
 
        String command = "C:/Program Files (x86)/Tesseract-OCR/tesseract -psm 3 "
                + destFile.getAbsolutePath() + " c:/tax_machina/tesseract/" + tessPath;
        Process child = Runtime.getRuntime().exec(command);
        child.waitFor();
        String result = FileUtils.readFileToString( new File( "C:/tax_machina/tesseract/" + tessPath + ".txt" ) );

        return result;
    }
}
