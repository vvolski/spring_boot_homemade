package com.colvir.spring_boot_homework.dto;

import com.colvir.spring_boot_homework.model.InternalErrorStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResponse {
    private InternalErrorStatus status;
    private String message;
}
