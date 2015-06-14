package io.skyrom.taxmachina.wserv.controller.v0;

import io.skyrom.taxmachina.utils.ApiCode;
import io.skyrom.taxmachina.utils.LocaleCode;
import io.skyrom.taxmachina.utils.MessageCode;
import io.skyrom.taxmachina.utils.ResponseMessage;
import io.skyrom.taxmachina.wserv.auth.UserDetailsManager;
import io.skyrom.taxmachina.wserv.dto.DTO;
import io.skyrom.taxmachina.wserv.model.ReceiptModel;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Petros
 */
@Controller
@RequestMapping( value = "/rs/v0/receipt" )
public class ReceiptController {

    static final Logger logger = Logger.getLogger( ReceiptController.class.getName() );

    @Autowired( required = true )
    private ReceiptModel receiptModel;

    @ResponseBody
    @RequestMapping( value = { "/qrjoin" },
            method = { RequestMethod.POST },
            consumes = { MediaType.APPLICATION_FORM_URLENCODED_VALUE } )
    private ResponseMessage handleQRJoinRequest( @RequestParam( "ccn" ) String ccn ) {
        ResponseMessage<DTO> rm = new ResponseMessage<>();
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String user = ( ( UserDetailsManager ) auth.getPrincipal() ).getUsername();

            DTO dto = receiptModel.qrJoin(user, ccn );

            rm.setCode( ApiCode.OK.value() );
            rm.setLocale( LocaleCode.EN.value() );
            rm.setMessage( MessageCode.RECEIPT_SCAN_SUCCESS.value() );
            rm.setData( dto );
        } catch ( Exception e ) {
            System.out.println( e.getMessage() );
            logger.error( e );

            rm.setCode( ApiCode.ERROR.value() );
            rm.setLocale( LocaleCode.EN.value() );
            rm.setMessage( MessageCode.RECEIPT_SCAN_ERROR.value() );
        }
        return rm;
    }
    
    @ResponseBody
    @RequestMapping( value = { "/scan" }, method = { RequestMethod.POST } )
    private ResponseMessage handleScanRequest( @RequestParam("file") MultipartFile file ) {
        ResponseMessage<DTO> rm = new ResponseMessage<>();
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String user = ( ( UserDetailsManager ) auth.getPrincipal() ).getUsername();

            DTO dto = receiptModel.scan( user, file );

            rm.setCode( ApiCode.OK.value() );
            rm.setLocale( LocaleCode.EN.value() );
            rm.setMessage( MessageCode.RECEIPT_SCAN_SUCCESS.value() );
            rm.setData( dto );
        } catch ( Exception e ) {
            System.out.println( e.getMessage() );
            logger.error( e );

            rm.setCode( ApiCode.ERROR.value() );
            rm.setLocale( LocaleCode.EN.value() );
            rm.setMessage( MessageCode.RECEIPT_SCAN_ERROR.value() );
        }
        return rm;
    }
    
    @ResponseBody
    @RequestMapping( value = { "/scanOCR" }, method = { RequestMethod.POST } )
    private ResponseMessage handleScanOcrRequest( @RequestParam("tin") String tin,
            @RequestParam("price") String price,
            @RequestParam("serial") String serial,
            @RequestParam("path") String path) {
        ResponseMessage<DTO> rm = new ResponseMessage<>();
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String user = ( ( UserDetailsManager ) auth.getPrincipal() ).getUsername();

            DTO dto = receiptModel.scanOCR( user, tin, price, serial, path );

            rm.setCode( ApiCode.OK.value() );
            rm.setLocale( LocaleCode.EN.value() );
            rm.setMessage( MessageCode.RECEIPT_SCAN_SUCCESS.value() );
            rm.setData( dto );
        } catch ( Exception e ) {
            System.out.println( e.getMessage() );
            logger.error( e );

            rm.setCode( ApiCode.ERROR.value() );
            rm.setLocale( LocaleCode.EN.value() );
            rm.setMessage( MessageCode.RECEIPT_SCAN_ERROR.value() );
        }
        return rm;
    }
    
    @ResponseBody
    @RequestMapping( value = { "/scan/android" }, method = { RequestMethod.POST }  )
    private ResponseMessage handleScanAndroidRequest( @RequestParam("file") String file ) {
        ResponseMessage<DTO> rm = new ResponseMessage<>();
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String user = ( ( UserDetailsManager ) auth.getPrincipal() ).getUsername();

            DTO dto = receiptModel.scan( user, file );

            rm.setCode( ApiCode.OK.value() );
            rm.setLocale( LocaleCode.EN.value() );
            rm.setMessage( MessageCode.RECEIPT_SCAN_SUCCESS.value() );
            rm.setData( dto );
        } catch ( Exception e ) {
            System.out.println( e.getMessage() );
            logger.error( e );

            rm.setCode( ApiCode.ERROR.value() );
            rm.setLocale( LocaleCode.EN.value() );
            rm.setMessage( MessageCode.RECEIPT_SCAN_ERROR.value() );
        }
        return rm;
    }
}
