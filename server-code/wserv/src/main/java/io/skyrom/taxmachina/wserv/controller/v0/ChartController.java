package io.skyrom.taxmachina.wserv.controller.v0;

import io.skyrom.taxmachina.utils.ApiCode;
import io.skyrom.taxmachina.utils.LocaleCode;
import io.skyrom.taxmachina.utils.MessageCode;
import io.skyrom.taxmachina.utils.ResponseMessage;
import io.skyrom.taxmachina.wserv.dto.DTO;
import io.skyrom.taxmachina.wserv.model.ChartModel;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Petros
 */
@Controller
@RequestMapping( value = "/rs/v0/chart" )
public class ChartController {

    static final Logger logger = Logger.getLogger( CashierController.class.getName() );

    @Autowired( required = true )
    private ChartModel chartModel;

    @ResponseBody
    @RequestMapping( value = { "/rank" }, method = { RequestMethod.GET } )
    private ResponseMessage handleRankRequest() {
        ResponseMessage<DTO> rm = new ResponseMessage<>();
        try {
            DTO dto = chartModel.rank();

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
