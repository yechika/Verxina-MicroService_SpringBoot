package com.verxina.common.event;

import java.math.BigDecimal;

public record TransactionCreatedEvent(
        String transactionId,
        String accountId,
        String amount,
        BigDecimal currency,
        String status
) {
}
