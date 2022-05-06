package com.wsh.model;

import com.wsh.model.ifaces.Nav;

public interface ArticleInfo {
    Long getId();

    String getIcon();

    String getName();

    Nav getNav();

    Integer getPosition();
}
