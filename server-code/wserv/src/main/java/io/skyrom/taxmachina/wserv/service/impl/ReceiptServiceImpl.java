package io.skyrom.taxmachina.wserv.service.impl;

import io.skyrom.taxmachina.origin.dao.ReceiptDAO;
import io.skyrom.taxmachina.origin.domain.Branch;
import io.skyrom.taxmachina.origin.domain.Receipt;
import io.skyrom.taxmachina.wserv.adapter.ReceiptAdapter;
import io.skyrom.taxmachina.wserv.service.ReceiptService;
import java.util.Date;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

/**
 *
 * @author Petros
 */
@Service
public class ReceiptServiceImpl implements ReceiptService, ApplicationContextAware {

    private ApplicationContext ac;
    
    @Autowired(required = true)
    private ReceiptDAO receiptDAO;

    @Override
    public void setApplicationContext( ApplicationContext ac ) throws BeansException {
        this.ac = ac;
    }

    @Override
    public Receipt createReceipt( ReceiptAdapter receiptAdapter, Branch branch, String ccn, Date doc ) {
        Receipt receipt = ac.getBean( Receipt.class );
        receipt.setDoc( doc );
        receipt.setPublication( receiptAdapter.getPublication() );
        receipt.setSerial( receiptAdapter.getSerial() );
        receipt.setPrice( receiptAdapter.getValue() );
        receipt.setVat( receiptAdapter.getVat() );
        receipt.setBranch( branch );
        receipt.setCcn( ccn );
        return receiptDAO.add( receipt );
    }

    @Override
    public Receipt findReceipt( String ccn ) {
        return receiptDAO.fetchByCcn( ccn );
    }

    @Override
    public Receipt updateReceipt( Receipt receipt ) {
        return receiptDAO.update( receipt );
    }

    @Override
    public Receipt findReceipt( String tin, double price, int serial ) {
        return receiptDAO.fetchForOcr( tin, price, serial );
    }

}
