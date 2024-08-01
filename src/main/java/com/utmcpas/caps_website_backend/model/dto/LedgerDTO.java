package com.utmcpas.caps_website_backend.model.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class LedgerDTO {
    private String item;
    private BigDecimal amount;
    private LocalDate expenseDate;
    private String notes;
}
