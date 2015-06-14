package io.skyrom.taxmachina.wserv.model.impl;

import io.skyrom.taxmachina.origin.domain.Receipt;
import io.skyrom.taxmachina.origin.domain.User;
import io.skyrom.taxmachina.utils.ImageUtils;
import io.skyrom.taxmachina.utils.ocr.TesseractWrapper;
import io.skyrom.taxmachina.wserv.dto.CashierReceiptDTO;
import io.skyrom.taxmachina.wserv.dto.DTO;
import io.skyrom.taxmachina.wserv.dto.DTOFactory;
import io.skyrom.taxmachina.wserv.model.ReceiptModel;
import io.skyrom.taxmachina.wserv.service.ReceiptService;
import io.skyrom.taxmachina.wserv.service.UserService;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import javax.imageio.ImageIO;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.json.JSONObject;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Petros
 */
@Component
@EnableTransactionManagement
public class ReceiptModelImpl implements ReceiptModel, ApplicationContextAware {

    private ApplicationContext ac;

    @Value( "${settings.user.scan.base}" )
    private String receiptBaseFile;

    @Value( "${settings.user.tmp.base}" )
    private String tmpBase;

    @Autowired( required = true )
    private ReceiptService receiptService;

    @Autowired( required = true )
    private UserService userService;

    @Autowired( required = true )
    private ImageUtils imageUtils;

    @Autowired( required = true )
    private TesseractWrapper tesseractWrapper;

    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat( "yyyy-MM-dd'T'HH:mm:ss" );

    @Override
    public void setApplicationContext( ApplicationContext ac ) throws BeansException {
        this.ac = ac;
    }

    @Transactional
    @Override
    public DTO qrJoin( String uid, String ccn ) throws Exception {
        User user = userService.read( uid );
        if ( user == null ) {
            throw new Exception( "User does not exist." );
        }

        Receipt receipt = receiptService.findReceipt( ccn );
        if ( receipt == null ) {
            throw new Exception( "Receipt could not be found." );
        }

        receipt.setUser( user );
        receiptService.updateReceipt( receipt );

        DTOFactory dtoFactory = ac.getBean( DTOFactory.class );
        CashierReceiptDTO dto = ( CashierReceiptDTO ) dtoFactory.build( receipt, CashierReceiptDTO.class );
        dto.setTin( receipt.getBranch().getTin() );
        dto.setDate( simpleDateFormat.format( receipt.getPublication() ) );

        return dto;
    }

    @Transactional
    @Override
    public DTO scan( String uid, MultipartFile file ) throws Exception {
        User user = userService.read( uid );
        if ( user == null ) {
            throw new Exception( "User could not be found." );
        }

        InputStream in = new ByteArrayInputStream( file.getBytes() );
        BufferedImage bi = ImageIO.read( in );

        //save the image as user send it
        String path = receiptBaseFile + "/" + user.getId() + "/" + System.currentTimeMillis() + ".png";
        File f = imageUtils.saveImage( bi, path, 600, false );

        String result = tesseractWrapper.recognCMD( f, "jpg" );

        String txtFile = f.getParent() + "/" + f.getName() + ".txt";
        FileUtils.writeStringToFile( new File( txtFile ), result );

        return null;

    }

    @Transactional
    @Override
    public DTO scan( String uid, String file ) throws Exception {
        User user = userService.read( uid );
        if ( user == null ) {
            throw new Exception( "User could not be found." );
        }

        InputStream in = new ByteArrayInputStream( Base64.decodeBase64( file.getBytes() ) );
        BufferedImage bi = ImageIO.read( in );

        String path = receiptBaseFile + "/" + user.getId() + "/" + System.currentTimeMillis() + ".png";
        File f = imageUtils.saveImage( bi, path, 600, false );

        String result = tesseractWrapper.recognCMD( f, "jpg" );

        String txtFile = f.getParent() + "/" + f.getName() + ".txt";
        FileUtils.writeStringToFile( new File( txtFile ), result );

        return null;
    }

    @Transactional
    @Override
    public DTO scanOCR( String uid, String tin, String price, String serial, String path ) throws Exception {
        //{"x":139,"y":130,"x2":236,"y2":182,"w":97,"h":52}
        User user = userService.read( uid );
        if ( user == null ) {
            throw new Exception( "User could not be found." );
        }

        JSONObject tinJson = new JSONObject( tin );
        JSONObject priceJson = new JSONObject( price );
        JSONObject serialJson = new JSONObject( serial );

        File file = new File( tmpBase + path );
        BufferedImage bi = ImageIO.read( file );

        BufferedImage tinBi = bi.getSubimage( tinJson.getInt( "x" ), tinJson.getInt( "y" ), tinJson.getInt( "x2" ) - tinJson.getInt( "x" ), tinJson.getInt( "y2" ) - tinJson.getInt( "y" ) );
        BufferedImage priceBi = bi.getSubimage( priceJson.getInt( "x" ), priceJson.getInt( "y" ), priceJson.getInt( "x2" ) - priceJson.getInt( "x" ), priceJson.getInt( "y2" ) - priceJson.getInt( "y" ) );
        BufferedImage serialBi = bi.getSubimage( serialJson.getInt( "x" ), serialJson.getInt( "y" ), serialJson.getInt( "x2" ) - serialJson.getInt( "x" ), serialJson.getInt( "y2" ) - serialJson.getInt( "y" ) );

        String tinResult = tesseractWrapper.recognCMD( tinBi, "jpg" ).trim().replaceAll( "[\\?]", "7" );
        String serialResult = tesseractWrapper.recognCMD( serialBi, "jpg" ).trim().replaceAll( "[\\.\\,]", "" ).replaceAll( "[\\?]", "7" );
        String priceResult = tesseractWrapper.recognCMD( priceBi, "jpg" ).trim().replaceAll( "[\\,]", "." ).replaceAll( "[\\?]", "7" );

        Receipt receipt = receiptService.findReceipt( tinResult, Double.parseDouble( priceResult ), Integer.parseInt( serialResult ) );
        if ( receipt != null ) {
            receipt.setUser( user );
            receiptService.updateReceipt( receipt );
        } else {
            throw new Exception("Receipt could not be saved!");
        }

        return null;
    }

}
