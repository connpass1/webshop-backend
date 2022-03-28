package com.wsh.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@NoArgsConstructor
@Entity
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Data
@AllArgsConstructor
@Builder
@Table(name = "userok")
public class User {

    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "profile_id")
    private final Profile profile = new Profile();
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "order_id")
    @Setter(AccessLevel.NONE)
    private final Set<CartOrder> cartOrder = new LinkedHashSet<>();
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    long id;
    @Column(length = 20, unique = true)
    private String name;
    @Column(length = 25)
    private String password;
    @Column(length = 5)
    private String role = "USER";


}