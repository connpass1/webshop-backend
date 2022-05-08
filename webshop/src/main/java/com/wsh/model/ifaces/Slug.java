package com.wsh.model.ifaces;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Slug {
    private Long id;
    private String  name;
    private String  icon;
}
