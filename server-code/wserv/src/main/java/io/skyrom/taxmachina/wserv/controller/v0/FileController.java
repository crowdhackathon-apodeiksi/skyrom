package io.skyrom.taxmachina.wserv.controller.v0;

import io.skyrom.taxmachina.utils.ApiCode;
import io.skyrom.taxmachina.utils.LocaleCode;
import io.skyrom.taxmachina.utils.MessageCode;
import io.skyrom.taxmachina.utils.ResponseMessage;
import io.skyrom.taxmachina.wserv.auth.UserDetailsManager;
import static io.skyrom.taxmachina.wserv.controller.v0.ReceiptController.logger;
import io.skyrom.taxmachina.wserv.dto.DTO;
import io.skyrom.taxmachina.wserv.model.FileModel;
import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.util.UriTemplate;

/**
 *
 * @author Petros
 */
@Controller
@RequestMapping( value = "/rs/v0/file" )
public class FileController {
    
    static final Logger logger = Logger.getLogger( QRCodeController.class.getName() );
    
    @Value( "${settings.user.tmp.base}" )
    private String tmpBase;
    
    @Autowired(required = true)
    private FileModel fileModel;
    
    @ResponseBody
    @RequestMapping( value = { "/upload" }, method = { RequestMethod.POST } )
    private ResponseMessage handleUploadRequest( @RequestParam("file") MultipartFile file ) {
        ResponseMessage<DTO> rm = new ResponseMessage<>();
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String user = ( ( UserDetailsManager ) auth.getPrincipal() ).getUsername();

            DTO dto = fileModel.upload(user, file );

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
    
    @RequestMapping(value = {"/preview/**"}, method = {RequestMethod.GET})
    @ResponseBody
    public ByteArrayResource handlePreviewRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String restOfTheUrl = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
        UriTemplate template = new UriTemplate("/rs/v0/file/preview/{value}");
        boolean isTemplateMatched = template.matches(restOfTheUrl);
        
        ByteArrayResource byteResponse = null;
        if (isTemplateMatched) {
            Map<String, String> matchTemplate = new HashMap<String, String>();
            matchTemplate = template.match(restOfTheUrl);
            String path = matchTemplate.get("value");
            File f = new File(tmpBase + "/" + path);
            if (!f.exists()) {
                f = new File(tmpBase + "/noimage.png");
            } 
            byte[] bytes = IOUtils.toByteArray(new FileInputStream(f));
            byteResponse = new ByteArrayResource(bytes);
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
        
        return byteResponse; 
    }
    
}
