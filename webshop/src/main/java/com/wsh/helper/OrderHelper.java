package com.wsh.helper;

import com.wsh.model.Item;
import com.wsh.model.Order;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

import java.util.List;
import java.util.Map;
@Slf4j
@Data
public class OrderHelper {

    private Long id;
    private Integer quantity;
}
