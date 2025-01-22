package com.fugro.ogs.infrastructure;

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