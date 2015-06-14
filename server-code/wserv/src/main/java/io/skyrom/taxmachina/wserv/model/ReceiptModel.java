package io.skyrom.taxmachina.wserv.model;

import io.skyrom.taxmachina.wserv.dto.DTO;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Petros
 */
public interface ReceiptModel {

    DTO qrJoin( String user, String ccn ) throws Exception;
    
    DTO scan( String user, MultipartFile file ) throws Exception;
    
    DTO scanOCR( String user, String tin, String price, String serial, String path ) throws Exception;
    
    DTO scan( String user, String file ) throws Exception;
}
