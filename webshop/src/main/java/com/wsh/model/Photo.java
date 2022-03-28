package com.wsh.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.persistence.*;


@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Entity
public class Photo {
    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String url;
    @Column(length = 50)
    private String alt;
    private String thumbnail;
}
