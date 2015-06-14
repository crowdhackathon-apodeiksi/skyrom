package io.skyrom.taxmachina.wserv.model;

import io.skyrom.taxmachina.wserv.adapter.ReceiptAdapter;
import io.skyrom.taxmachina.wserv.dto.DTO;

/**
 *
 * @author Petros
 */
public interface CashierModel {

    DTO saveReceipt(ReceiptAdapter adapter) throws Exception;
}
