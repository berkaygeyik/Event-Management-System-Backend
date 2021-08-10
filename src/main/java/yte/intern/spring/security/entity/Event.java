package yte.intern.spring.security.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@SequenceGenerator(name = "idgen", sequenceName = "EVENT_SEQ")
public class Event extends BaseEntity{

    @Column(name = "NAME", unique = true)
    private String name;

    @Column(name = "ORGANIZER")
    private String organizer;

    @Column(name = "START_DATE")
    private LocalDateTime startDate;

    @Column(name = "END_DATE")
    private LocalDateTime endDate;

    @Column(name = "DETAILS")
    private String details;

    @Column(name = "LOCATION")
    private String location;

    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "QUOTA")
    private int quota;

    @Column(name = "REGISTERED_USER_COUNT")
    private int registeredUserCount;

    @Column(name = "IMAGE_URL")
    private String imageURL;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USERS")
    private Users users;

    @ManyToMany(mappedBy = "enrolledEvents")
    private Set<Users> participants = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "event")
    @Column(name = "ENROLLMENTS")
    private Set<EventUser> enrollments = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "event")
    @Column(name = "QUESTIONS")
    private Set<Question> questions = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "event")
    @Column(name = "EVENT_REGISTRATION_QUESTIONS")
    private Set<EventRegistrationQuestion> registrationQuestions = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "event")
    @Column(name = "EVENT_POLL_QUESTIONS")
    private Set<EventPollQuestion> eventPollQuestions = new HashSet<>();

    @Column(name = "GIFT_DRAWER_USER")
    private String giftDrawUser;

}
