package yte.intern.spring.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import yte.intern.spring.security.entity.Question;

import javax.transaction.Transactional;

public interface QuestionRepository  extends JpaRepository<Question, Long> {
    boolean existsByUniqueId(String questionID);
    Question findByUniqueId(String questionID);

    @Transactional
    void deleteByUniqueId(String questionID);
}
