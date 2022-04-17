package com.wsh.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.wsh.helper.LogListener;
import com.wsh.model.ifaces.Role;
import lombok.*;
import org.hibernate.Hibernate;
import org.springframework.security.crypto.password.PasswordEncoder;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@NoArgsConstructor
//@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Getter
@Setter
@AllArgsConstructor
@Entity
@Builder
@Table(name = "users")
@EntityListeners(LogListener.class)

public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    Long id;

    @Column(length = 25,  unique = true,nullable = false)
    private String name;

    @JsonIgnore
    @Column(length = 255)
    private String password;




    @Enumerated(EnumType.STRING)
    @Column( nullable = false)
    private Role role =Role.USER;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        User user = (User) o;
        return id != null && Objects.equals(id, user.id);
    }

    @Transient
    public boolean validate(String password, PasswordEncoder passwordEncoder) {
        return passwordEncoder.matches(password, this.password);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @PrePersist
    public void prePersist() {

    }
}