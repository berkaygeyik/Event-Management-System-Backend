package yte.intern.spring.security.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@SequenceGenerator(name = "idgen", sequenceName = "EVENT_POLL_QUESTION_SEQ")
public class EventPollQuestion extends BaseEntity {

    @Column(name = "UNIQUE_ID")
    private String uniqueId;

    @Column(name = "QUESTION_TEXT", length = 2048)
    private String questionText;


    private String option1;

    private String option2;

    private String option3;

    private String option4;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "EVENT")
    private Event event;

    @OneToMany(mappedBy = "question",fetch = FetchType.EAGER)
    private Set<EventPollAnswer> eventPollAnswers;
}















