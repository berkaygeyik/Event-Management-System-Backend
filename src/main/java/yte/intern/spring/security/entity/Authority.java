package yte.intern.spring.security.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SequenceGenerator(name = "idgen", sequenceName = "AUTHORITY_SEQ")
public class Authority implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "idgen")
    @Column(name = "ID")
    private Long id;

    @ManyToMany(mappedBy = "authorities")
    @Column(name = "USERS")
    private Set<Users> users;

    @Column(name = "AUTHORITY")
    private String authority;



}
