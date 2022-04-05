package com.wsh.helper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wsh.model.Item;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.io.IOException;
import java.util.Map;
@Slf4j
@Converter(autoApply = true)
public class OrderConverter implements AttributeConverter<Map<Item, Integer>, String> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(Map<Item, Integer> itemQuantityInfo) {
        String itemQuontityInfoJson = null;
        try {
            itemQuontityInfoJson = objectMapper.writeValueAsString(itemQuantityInfo);
        } catch (final JsonProcessingException e) {
            log.error("JSON writing error", e);
        }
        return itemQuontityInfoJson;
    }

    @Override
    public Map<Item, Integer> convertToEntityAttribute(String itemQuantityInfoJSON) {
        Map<Item, Integer> itemQuantityInfo = null;
        try {
            itemQuantityInfo = objectMapper.readValue(itemQuantityInfoJSON, Map.class);
        } catch (final IOException e) {
            log.error("JSON reading error", e);
        }
        return itemQuantityInfo;
    }

    }