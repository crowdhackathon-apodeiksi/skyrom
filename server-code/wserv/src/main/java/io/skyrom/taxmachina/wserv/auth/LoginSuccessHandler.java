package io.skyrom.taxmachina.wserv.auth;

import io.skyrom.taxmachina.utils.ApiCode;
import io.skyrom.taxmachina.utils.LocaleCode;
import io.skyrom.taxmachina.utils.MessageCode;
import io.skyrom.taxmachina.utils.ResponseMessage;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

/**
 *
 * @author petros
 */
public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler implements ApplicationContextAware {
    private ApplicationContext ac;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,  Authentication authentication) 
            throws ServletException, IOException {
        
        ResponseMessage rm = (ResponseMessage) ac.getBean("responseMessage");
        rm.setCode(ApiCode.OK.value());
        rm.setMessage(MessageCode.OK.value());
        rm.setLocale( LocaleCode.EL.value() );

        
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().write(rm.toJson().toString());
        
        clearAuthenticationAttributes(request);
    }

    @Override
    public void setApplicationContext(ApplicationContext ac) throws BeansException {
        this.ac = ac;
    }

}
