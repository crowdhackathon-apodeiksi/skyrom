package io.skyrom.taxmachina.wserv.model.impl;

import io.skyrom.taxmachina.origin.domain.Branch;
import io.skyrom.taxmachina.origin.domain.Receipt;
import io.skyrom.taxmachina.utils.HashVerter;
import io.skyrom.taxmachina.utils.qrcode.QRCodeBuilder;
import io.skyrom.taxmachina.wserv.adapter.BranchAdapter;
import io.skyrom.taxmachina.wserv.adapter.ReceiptAdapter;
import io.skyrom.taxmachina.wserv.dto.CashierReceiptDTO;
import io.skyrom.taxmachina.wserv.dto.DTO;
import io.skyrom.taxmachina.wserv.dto.DTOFactory;
import io.skyrom.taxmachina.wserv.model.CashierModel;
import io.skyrom.taxmachina.wserv.service.BranchService;
import io.skyrom.taxmachina.wserv.service.ReceiptService;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Petros
 */
@Component
@EnableTransactionManagement
public class CashierModelImpl implements CashierModel, ApplicationContextAware {

    private ApplicationContext ac;

    @Value("${settings.qrcode.base}")
    private String qrCodeBaseFile;
    
    @Autowired( required = true )
    private ReceiptService receiptService;

    @Autowired( required = true )
    private BranchService branchService;

    @Autowired( required = true )
    private HashVerter hashVerter;

    @Autowired( required = true )
    private QRCodeBuilder qrCodeBuilder;

    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat( "yyyy-MM-dd'T'HH:mm:ss" );

    @Override
    @Transactional
    public DTO saveReceipt( ReceiptAdapter receiptAdapter ) throws Exception {
        Branch branch = branchService.getBranchByTin( receiptAdapter.getTin() );

        // create it if doesnt exists
        if ( branch == null ) {
            BranchAdapter branchAdapter = new BranchAdapter();
            branchAdapter.setName( receiptAdapter.getTin() );
            branchAdapter.setTin( receiptAdapter.getTin() );
            branch = branchService.createBranch( branchAdapter );
        }

        String ccn = hashVerter.encrypt( Long.toString( System.currentTimeMillis() ), HashVerter.MD5 );

        receiptAdapter.setPublication( simpleDateFormat.parse( receiptAdapter.getDate() ) );
        Receipt receipt = receiptService.createReceipt( receiptAdapter, branch, ccn, new Date() );

        DTOFactory dtoFactory = ac.getBean( DTOFactory.class );
        CashierReceiptDTO dto = ( CashierReceiptDTO ) dtoFactory.build( receipt, CashierReceiptDTO.class );
        dto.setTin( branch.getTin() );
        dto.setDate( simpleDateFormat.format( receipt.getPublication() ) );

        String relPath = "/" + branch.getId();
        File file = new File( qrCodeBaseFile + relPath );
        if ( !file.exists() ) {
            file.mkdirs();
        }

        String qrPath = qrCodeBaseFile + relPath + "/" + ccn + ".png";
        qrCodeBuilder.build( qrPath, ccn );

        dto.setQrcode( "/wserv/rs/v0/qrcode" + relPath + "/" + ccn + ".png" );
        return dto;
    }

    @Override
    public void setApplicationContext( ApplicationContext ac ) throws BeansException {
        this.ac = ac;
    }

}
