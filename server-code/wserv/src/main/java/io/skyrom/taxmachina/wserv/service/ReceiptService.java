package io.skyrom.taxmachina.wserv.service;

import io.skyrom.taxmachina.origin.domain.Branch;
import io.skyrom.taxmachina.origin.domain.Receipt;
import io.skyrom.taxmachina.wserv.adapter.ReceiptAdapter;
import java.util.Date;

/**
 *
 * @author Petros
 */
public interface ReceiptService {
    
    Receipt createReceipt(ReceiptAdapter receiptAdapter, Branch branch, String ccn, Date doc);
    
    Receipt updateReceipt(Receipt receipt);
    
    Receipt findReceipt(String ccn);
    
    Receipt findReceipt(String tin, double price, int serial);
}
