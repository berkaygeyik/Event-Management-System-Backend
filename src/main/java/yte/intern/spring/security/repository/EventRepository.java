package yte.intern.spring.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import yte.intern.spring.security.entity.Event;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {

    Event findByName(String eventName);
    boolean existsByName(String name);
    List<Event> findByStartDateGreaterThanOrderByStartDateDesc(LocalDateTime date);
    List<Event> findByStartDateOrderByStartDateDesc(LocalDateTime date);

    @Transactional
    void deleteByName(String eventName);
}
