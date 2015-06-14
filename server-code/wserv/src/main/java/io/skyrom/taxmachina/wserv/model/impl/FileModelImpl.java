package io.skyrom.taxmachina.wserv.model.impl;

import io.skyrom.taxmachina.origin.domain.User;
import io.skyrom.taxmachina.utils.ImageUtils;
import io.skyrom.taxmachina.wserv.dto.DTO;
import io.skyrom.taxmachina.wserv.dto.FileDTO;
import io.skyrom.taxmachina.wserv.model.FileModel;
import io.skyrom.taxmachina.wserv.service.UserService;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import javax.imageio.ImageIO;
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
public class FileModelImpl implements FileModel, ApplicationContextAware {
    
    private ApplicationContext ac;
    
    @Value( "${settings.user.tmp.base}" )
    private String tmpBase;
    
    @Autowired(required = true)
    private UserService userService;
    
    @Autowired(required = true)
    private ImageUtils imageUtils;
    
    @Override
    public void setApplicationContext( ApplicationContext ac ) throws BeansException {
        this.ac = ac;
    }

    @Transactional
    @Override
    public DTO upload( String uid, MultipartFile file ) throws Exception {
        User user = userService.read( uid );
        if ( user == null ) {
            throw new Exception( "User could not be found." );
        }
        
        InputStream in = new ByteArrayInputStream( file.getBytes() );
        BufferedImage bi = ImageIO.read( in );

        //save the image as user send it
        String path =  "/" + user.getId() + "/" + System.currentTimeMillis() + ".png";
        File f = imageUtils.saveImage( bi, tmpBase + path, 1200, false );
        
        FileDTO dto = new FileDTO();
        dto.setPath( path );
        
        return ( DTO ) dto;
    }
}
