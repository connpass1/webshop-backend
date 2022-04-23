package com.wsh.model;

import com.wsh.helper.LogListener;
import lombok.*;
import net.minidev.json.annotate.JsonIgnore;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@NoArgsConstructor
@EntityListeners(LogListener.class)
@Getter
@Setter
@AllArgsConstructor
@Entity
@Builder
public class Profile implements Serializable {
    @Id
    @Column(name = "id", nullable = false)
    @Setter(AccessLevel.NONE)
    private Long id;


    @Column(unique = true, nullable = false)
    private Long phone;
    private String address;
    @Column(length = 50, unique = true)
    private String email;

   @JsonIgnore
   @Getter(AccessLevel.NONE)
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "user_id", unique = true)
    private User user;

    @Setter(AccessLevel.NONE)
    @OneToMany(mappedBy = "profile", orphanRemoval = true)
    private List<Order> orders=new LinkedList<>();

    public void setUser(User user){
       this.user=user;
       this.id=user.getId();
   }
    @Transient
    public Profile setOrder( Order order){
        if (orders==null) orders=new LinkedList<>();
        orders.add(order);
        return this;
    }
   @Transient
    public Long getUserId( ){
          return user.getId();
      }
    @Transient
    public String getName( ){
        return user.getName();
    }

    @Override
    public String toString() {
        return "Profile{" +
                "id=" + id +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Profile profile = (Profile) o;
        return id != null && Objects.equals(id, profile.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
