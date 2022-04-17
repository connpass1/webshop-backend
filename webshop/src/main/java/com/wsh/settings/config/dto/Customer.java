package com.wsh.settings.config.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Customer {
    private Long id;
    private String role = "USER";
    private String password;
    private String name;
}
