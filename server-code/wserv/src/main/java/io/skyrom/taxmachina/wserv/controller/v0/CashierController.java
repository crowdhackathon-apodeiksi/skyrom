package io.skyrom.taxmachina.wserv.controller.v0;

import io.skyrom.taxmachina.utils.ApiCode;
import io.skyrom.taxmachina.utils.LocaleCode;
import io.skyrom.taxmachina.utils.MessageCode;
import io.skyrom.taxmachina.utils.ResponseMessage;
import io.skyrom.taxmachina.wserv.adapter.ReceiptAdapter;
import io.skyrom.taxmachina.wserv.dto.DTO;
import io.skyrom.taxmachina.wserv.model.CashierModel;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Petros
 */
@Controller
@RequestMapping( value = "/rs/v0/cashier" )
public class CashierController {

    static final Logger logger = Logger.getLogger( CashierController.class.getName() );

    @Autowired( required = true )
    private CashierModel cashierModel;

    @ResponseBody
    @RequestMapping( value = { "/saveReceipt" },
            method = { RequestMethod.POST },
            consumes = { MediaType.APPLICATION_FORM_URLENCODED_VALUE } )
    private ResponseMessage handleSaveReceiptRequest( @ModelAttribute ReceiptAdapter receiptAdapter ) {
        ResponseMessage<DTO> rm = new ResponseMessage<>();
        try {
            DTO dto = cashierModel.saveReceipt( receiptAdapter );

            rm.setCode( ApiCode.OK.value() );
            rm.setLocale( LocaleCode.EN.value() );
            rm.setMessage( MessageCode.RECEIPT_SAVE_SUCCESS.value() );
            rm.setData( dto );
        } catch ( Exception e ) {
            System.out.println( e.getMessage() );
            logger.error( e );

            rm.setCode( ApiCode.ERROR.value() );
            rm.setLocale( LocaleCode.EN.value() );
            rm.setMessage( MessageCode.RECEIPT_SAVE_ERROR.value() );
        }
        return rm;
    }
}
