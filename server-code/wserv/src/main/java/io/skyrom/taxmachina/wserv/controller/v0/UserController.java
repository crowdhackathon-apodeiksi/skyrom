package io.skyrom.taxmachina.wserv.controller.v0;


import io.skyrom.taxmachina.utils.ApiCode;
import io.skyrom.taxmachina.utils.LocaleCode;
import io.skyrom.taxmachina.utils.MessageCode;
import io.skyrom.taxmachina.utils.ResponseMessage;
import io.skyrom.taxmachina.wserv.adapter.UserAdapter;
import io.skyrom.taxmachina.wserv.dto.DTO;
import io.skyrom.taxmachina.wserv.model.UserModel;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author petros
 */
@Controller
@RequestMapping( value = "/rs/v0/user" )
public class UserController {

    static final Logger logger = Logger.getLogger( UserController.class.getName() );

    @Autowired( required = true )
    private UserModel userModel;

    @ResponseBody
    @RequestMapping( value = { "/signup" },
            method = { RequestMethod.POST },
            consumes = { MediaType.APPLICATION_FORM_URLENCODED_VALUE } )
    private ResponseMessage handleCreateRequest( @ModelAttribute UserAdapter userAdapter ) {
        ResponseMessage<DTO> rm = new ResponseMessage<>();
        try {
            DTO dto = userModel.createUser( userAdapter );

            rm.setCode( ApiCode.OK.value() );
            rm.setLocale( LocaleCode.EN.value() );
            rm.setMessage( MessageCode.USER_CREATE_SUCCESS.value() );
            rm.setData( dto );
        } catch ( Exception e ) {
            System.out.println( e.getMessage() );
            logger.error( e );

            rm.setCode( ApiCode.ERROR.value() );
            rm.setLocale( LocaleCode.EN.value() );
            rm.setMessage( MessageCode.USER_CREATE_ERROR.value() );
        }
        return rm;
    }

    @ResponseBody
    @RequestMapping( value = { "/{id}" },
            method = { RequestMethod.POST },
            consumes = { MediaType.APPLICATION_FORM_URLENCODED_VALUE } )
    private ResponseMessage handleUpdateRequest( @PathVariable( value = "id" ) long Id,
            @ModelAttribute UserAdapter userAdapter ) {
        ResponseMessage<DTO> rm = new ResponseMessage<>();
        try {
            DTO dto = userModel.updateUser( Id, userAdapter );

            rm.setCode( ApiCode.OK.value() );
            rm.setLocale( LocaleCode.EN.value() );
            rm.setMessage( MessageCode.USER_EDIT_SUCCESS.value() );
            rm.setData( dto );
        } catch ( Exception e ) {
            System.err.println( e.getMessage() );
            logger.error( e );

            rm.setCode( ApiCode.ERROR.value() );
            rm.setLocale( LocaleCode.EN.value() );
            rm.setMessage( MessageCode.USER_EDIT_ERROR.value() );
        }
        return rm;
    }

    @ResponseBody
    @RequestMapping( value = { "/{id}" }, method = { RequestMethod.DELETE } )
    private ResponseMessage handleDeleteRequest( @PathVariable( value = "id" ) long Id ) {
        ResponseMessage<Boolean> rm = new ResponseMessage<>();
        try {
            boolean bool = userModel.deleteUser( Id );

            rm.setCode( ApiCode.OK.value() );
            rm.setLocale( LocaleCode.EN.value() );
            rm.setMessage( MessageCode.USER_DELETE_SUCCESS.value() );
            rm.setData( bool );
        } catch ( Exception e ) {
            System.err.println( e.getMessage() );
            logger.error( e );

            rm.setCode( ApiCode.ERROR.value() );
            rm.setLocale( LocaleCode.EN.value() );
            rm.setMessage( MessageCode.USER_DELETE_ERROR.value() );
        }
        return rm;
    }

    @ResponseBody
    @RequestMapping( value = { "/{id}" }, method = { RequestMethod.GET } )
    private ResponseMessage handleReadRequest( @PathVariable( value = "id" ) long Id ) {
        ResponseMessage<DTO> rm = new ResponseMessage<>();
        try {
            DTO dto = userModel.readUser( Id );

            rm.setCode( ApiCode.OK.value() );
            rm.setLocale( LocaleCode.EN.value() );
            rm.setMessage( MessageCode.USER_READ_SUCCESS.value() );
            rm.setData( dto );
        } catch ( Exception e ) {
            System.err.println( e.getMessage() );
            logger.error( e );

            rm.setCode( ApiCode.ERROR.value() );
            rm.setLocale( LocaleCode.EN.value() );
            rm.setMessage( MessageCode.USER_READ_ERROR.value() );
        }
        return rm;
    }
}
