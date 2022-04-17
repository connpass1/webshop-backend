package com.wsh.model;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.wsh.helper.LogListener;
import lombok.*;
import javax.persistence.*;

@NoArgsConstructor
//@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Getter
@Setter
@AllArgsConstructor
@Entity
@Builder
@EntityListeners(LogListener.class)
public class OrderItem {
    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private int quantity = 1;
    @ManyToOne
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @JsonProperty("orderId")
    public Long orderId() {
        return order.getId();
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "id=" + id +
                '}';
    }
}
