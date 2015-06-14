package io.skyrom.taxmachina.wserv.auth;

import io.skyrom.taxmachina.utils.ApiCode;
import io.skyrom.taxmachina.utils.LocaleCode;
import io.skyrom.taxmachina.utils.ResponseMessage;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

/**
 *
 * @author Petros
 */
public class LoginFailureHandler extends SimpleUrlAuthenticationFailureHandler implements ApplicationContextAware {

    private ApplicationContext ac;

    @Override
    public void onAuthenticationFailure( HttpServletRequest request, HttpServletResponse response, AuthenticationException authException ) throws IOException, ServletException {
        ResponseMessage rm = ( ResponseMessage ) ac.getBean( "responseMessage" );
        rm.setCode( ApiCode.ERROR.value() );
        rm.setMessage( authException.getMessage() );
        rm.setLocale( LocaleCode.EL.value() );

        response.setStatus( HttpServletResponse.SC_OK );
        response.getWriter().write( rm.toJson().toString() );
    }

    @Override
    public void setApplicationContext( ApplicationContext ac ) throws BeansException {
        this.ac = ac;
    }

}
