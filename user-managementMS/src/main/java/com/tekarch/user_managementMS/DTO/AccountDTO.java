package com.tekarch.user_managementMS.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AccountDTO {

    private Long accountId;
    private String account_number;
    private String account_type;
    private BigDecimal balance = BigDecimal.ZERO;
    private String currency = "USD";
    private String status = "active";
    private LocalDateTime created_at;
    private Long userId;
}
