package yte.intern.spring.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import yte.intern.spring.security.entity.EventPollQuestion;

public interface EventPollQuestionRepository extends JpaRepository<EventPollQuestion, Long> {
}
