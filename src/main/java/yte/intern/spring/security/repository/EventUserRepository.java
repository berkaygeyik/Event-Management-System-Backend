package yte.intern.spring.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import yte.intern.spring.security.entity.EventUser;

import javax.transaction.Transactional;

public interface EventUserRepository extends JpaRepository<EventUser, Long> {

    EventUser findByEventUserName(String eventUserName);
    boolean existsByEventUserName(String eventUserName);

    @Transactional
    void deleteByEventUserName(String eventUserName);
}
