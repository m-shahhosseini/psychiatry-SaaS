package com.idea.psychiatry.shared.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApiErrorResponse {

    private boolean success;
    private String message;
    private int status;
    private String error;
    private String path;
    private LocalDateTime timestamp;


}