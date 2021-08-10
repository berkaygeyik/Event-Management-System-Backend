package yte.intern.spring.security.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@SequenceGenerator(name = "idgen", sequenceName = "EVENT_REGISTRATION_QUESTION_SEQ")
public class EventRegistrationQuestion extends BaseEntity {
    @Column(name = "UNIQUE_ID")
    private String uniqueId;

    @Column(name = "QUESTION_TEXT", length = 2048)
    private String questionText;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "EVENT")
    private Event event;


}
