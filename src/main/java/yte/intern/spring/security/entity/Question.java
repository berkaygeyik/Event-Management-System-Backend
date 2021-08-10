package yte.intern.spring.security.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@SequenceGenerator(name = "idgen", sequenceName = "QUESTION_SEQ")
public class Question extends BaseEntity {

    @Column(name = "UNIQUE_ID")
    private String uniqueId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "EVENT")
    private Event event;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "USERS")
    private Users users;

    @Column(name = "USERNAME")
    private String username;

    @Column(name = "QUESTION_TEXT")
    private String questionText;

    @Column(name = "ANSWER")
    private String answer;

    @OneToMany(mappedBy = "question",fetch = FetchType.EAGER)
    private Set<QuestionAnswer> questionAnswers;
}







