package com.wsh.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.wsh.helper.OrderConverter;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Map;
import java.util.Objects;

@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Getter
@Setter
@AllArgsConstructor
@Entity
@Builder
@Table(name = "orders")
public class Order implements Serializable {
    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private long lastUpdate = System.currentTimeMillis();
    private int status = 0;
    @Getter(AccessLevel.NONE)
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;


    @Setter(AccessLevel.NONE)
    @Convert(attributeName = "key", converter = OrderConverter.class)
    private Map<Item, Integer> itemQuantityMap ;


    @JsonProperty("userId")
    public Long getUserId() {
        if(user==null)return null;
        return user.id;
    }

    @PrePersist
    @PreUpdate
    private void time() {
        lastUpdate = System.currentTimeMillis();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Order order = (Order) o;
        return id != null && Objects.equals(id, order.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
