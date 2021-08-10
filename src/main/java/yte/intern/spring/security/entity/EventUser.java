package yte.intern.spring.security.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SequenceGenerator(name = "idgen", sequenceName = "EVENT_USER_SEQ")
public class EventUser extends BaseEntity {

    @Column(name = "EVENT_USER_NAME", unique = true)
    private String eventUserName;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "EVENT")
    private Event event;
}
