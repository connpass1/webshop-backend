package com.wsh.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
@Getter
@Setter
@Embeddable
public class Embed {
    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String icon;
    @Column(length = 25)

    private String name;


    @Setter(AccessLevel.NONE)
    @ManyToOne
    @JoinColumn(name = "parent")
    @JsonManagedReference
    private Category parent;

    @JsonProperty("parent")
    public String parent() {
        if (parent == null) return null;
        if (parent.getName().contains("root")) return null;
        if ( parent.parent() == null) return  parent.getName()+'@'+parent.getId()+'@'+parent.getIcon();
        return parent.parent()+"$"+ parent.getName()+'@'+parent.getId()+'@'+parent.getIcon();
    }
}
