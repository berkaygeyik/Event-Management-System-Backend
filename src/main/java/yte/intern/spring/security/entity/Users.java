package yte.intern.spring.security.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SequenceGenerator(name = "idgen", sequenceName = "USERS_SEQ")
public class Users  extends BaseEntity implements UserDetails {


    @Column(name = "EMAIL", unique = true)
    private String email;

    @Column(name = "USERNAME", unique = true)
    private String username;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "USER_ROLE")
    private String userRole;

    @Column(name = "NAME")
    private String name;

    @Column(name = "SURNAME")
    private String surname;

    @Column(name = "TC_IDENTIFICATION_NUMBER", unique = true)
    private String tcIdentificationNumber;

    @Column(name = "IMAGE_URL")
    private String imageURL;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "USER_AUTHORITIES",
            joinColumns = @JoinColumn(name = "USER_ID"),
            inverseJoinColumns = @JoinColumn(name = "AUTHORITY_ID")
    )
    @Column(name = "AUTHORITIES")
    private Set<Authority> authorities;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "users")
    @Column(name = "EVENTS")
    private Set<Event> events;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "enrolledEvents",
            joinColumns = @JoinColumn(name = "fk_participant"),
            inverseJoinColumns = @JoinColumn(name = "fk_event")
    )
    private Set<Event> enrolledEvents = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "users")
    @Column(name = "QUESTIONS")
    private Set<Question> questions = new HashSet<>();





    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private List<EventPollAnswer> eventPollAnswers = new ArrayList<>();


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
