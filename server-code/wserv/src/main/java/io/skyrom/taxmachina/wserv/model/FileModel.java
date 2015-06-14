package io.skyrom.taxmachina.wserv.model;

import io.skyrom.taxmachina.wserv.dto.DTO;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Petros
 */
public interface FileModel {
    
    DTO upload(String uid, MultipartFile file) throws Exception;
}
