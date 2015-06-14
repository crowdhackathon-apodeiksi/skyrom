package io.skyrom.taxmachina.wserv.controller.v0;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.util.UriTemplate;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;

/**
 *
 * @author Petros
 */
@Controller
@RequestMapping( value = "/rs/v0/qrcode" )
public class QRCodeController {
    
    static final Logger logger = Logger.getLogger( QRCodeController.class.getName() );
    
    @Value("${settings.qrcode.base}")
    private String qrCodeBaseFile;
    
    @RequestMapping(value = {"/**"}, method = {RequestMethod.GET})
    @ResponseBody
    public ByteArrayResource handleQRRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String restOfTheUrl = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
        UriTemplate template = new UriTemplate("/rs/v0/qrcode/{value}");
        boolean isTemplateMatched = template.matches(restOfTheUrl);
        
        ByteArrayResource byteResponse = null;
        if (isTemplateMatched) {
            Map<String, String> matchTemplate = new HashMap<String, String>();
            matchTemplate = template.match(restOfTheUrl);
            String path = matchTemplate.get("value");
            File f = new File(qrCodeBaseFile + "/" + path);
            if (!f.exists()) {
                f = new File(qrCodeBaseFile + "/noimage.png");
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
