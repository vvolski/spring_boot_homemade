package com.colvir.spring_boot_homework.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
@Data
@AllArgsConstructor
public class PayOrdListResponse {
    private List<PayOrdResponse> payOrds;
}
