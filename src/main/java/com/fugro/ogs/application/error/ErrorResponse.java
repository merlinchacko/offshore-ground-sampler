package com.fugro.ogs.application.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
public class ErrorResponse
{
    private String errorCode;
    private String message;
}