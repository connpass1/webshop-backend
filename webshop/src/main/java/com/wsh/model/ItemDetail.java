package com.wsh.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
public class ItemDetail {
    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private int amount = 0;
    @Column(length = 50)
    private String caption;
    private String description;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "photos_id")
    @Setter(AccessLevel.NONE)
    private Set<Photo> photos = new LinkedHashSet<>();
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "property_id")
    @Setter(AccessLevel.NONE)
    private Set<ItemProperty> properties = new java.util.LinkedHashSet<>();
}
