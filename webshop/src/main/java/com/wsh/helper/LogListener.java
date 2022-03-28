package com.wsh.helper;


import com.wsh.model.Item;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;

@Slf4j
public class LogListener {


    @PostPersist
    @PostUpdate
    private void postUpdate(Item item) {
        log.info("item " + item.getName() + "  was updated");
    }


}