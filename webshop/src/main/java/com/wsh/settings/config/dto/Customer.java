package com.wsh.settings.config.dto;

import lombok.Data;

@Data

public class Customer {
    private Long id;
    private String role = "USER";
    private String password;
    private String name;
}
