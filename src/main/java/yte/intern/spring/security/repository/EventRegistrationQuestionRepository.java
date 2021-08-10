package yte.intern.spring.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import yte.intern.spring.security.entity.EventRegistrationQuestion;

public interface EventRegistrationQuestionRepository  extends JpaRepository<EventRegistrationQuestion, Long> {
}
