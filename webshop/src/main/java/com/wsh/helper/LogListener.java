package com.wsh.helper;


import lombok.extern.slf4j.Slf4j;

import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;

@Slf4j
public class LogListener {


    @PostPersist
    @PostUpdate
    private void postUpdate(Object object) {
        log.debug(object.toString() + "  was updated  ");
    }


}