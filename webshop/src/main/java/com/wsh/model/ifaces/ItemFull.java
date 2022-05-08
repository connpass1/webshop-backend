package com.wsh.model.ifaces;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class ItemFull {
    private String name;
    private Long id;
    private Slug parent;
    private String icon;
    private Integer price;
    private String description;
    private  int  measure;
    private  int  mass;
    private String caption;
    private String photo[];
    private String composition[];
    private Quantity quantity;


}
