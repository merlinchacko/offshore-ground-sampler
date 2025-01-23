package com.fugro.ogs.application.security;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
@ConfigurationProperties(prefix = "security.user")
@Getter
@Setter
public class SecurityProperties
{
    private String username;
    private String password;
    private String role;
}
