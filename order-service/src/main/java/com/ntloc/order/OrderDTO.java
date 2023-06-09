package com.ntloc.order;

import com.ntloc.coreapi.order.model.OrderState;

import java.math.BigDecimal;
import java.util.List;

public record OrderDTO(String id,
                       Long customerId,
                       List<OrderLineItem> lineItems,
                       BigDecimal moneyTotal,
                       OrderState state,
                       FailedReason failedReason) {

}
