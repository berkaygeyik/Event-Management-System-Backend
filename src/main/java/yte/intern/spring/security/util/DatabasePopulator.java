package yte.intern.spring.security.util;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import yte.intern.spring.security.entity.Authority;
import yte.intern.spring.security.entity.Users;
import yte.intern.spring.security.repository.AuthorityRepository;
import yte.intern.spring.security.service.CustomUserDetailsManager;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class DatabasePopulator {

    private final CustomUserDetailsManager customUserDetailsManager;
    private final AuthorityRepository authorityRepository;

    @Transactional
    public void populateDatabase() {

        // Users admin = new Users(null, "admin", "admin", Set.copyOf(savedAuthorities));

        List<Authority> adminAuthorities = authorityRepository.saveAll(Set.of(
                new Authority(null, new HashSet<>(), "USER"), new Authority(null, new HashSet<>(), "ADMIN")));
        List<Authority> userAuthorities = authorityRepository.saveAll(Set.of(
                new Authority(null, new HashSet<>(), "USER")));
        Users adminUser = new Users("admin@gmail.com", "admin", "admin123","admin", "Berkay", "Geyik", "11111111110", "", Set.copyOf(adminAuthorities),Set.of(),Set.of(),Set.of(),List.of());
        Users normalUser = new Users("user@gmail.com", "user", "user1234","user", "user", "name", "61875049112", "", Set.copyOf(userAuthorities),Set.of(),Set.of(),Set.of(),List.of());


        customUserDetailsManager.createUser(adminUser);
        customUserDetailsManager.createUser(normalUser);
    }
}
