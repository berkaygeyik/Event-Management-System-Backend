package yte.intern.spring.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import yte.intern.spring.security.entity.Comment;

import javax.transaction.Transactional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    Comment findByUniqueCommentKey(String uniqueCommentKey);
    boolean existsByUniqueCommentKey(String uniqueCommentKey);

    @Transactional
    void deleteByUniqueCommentKey(String uniqueCommentKey);

}
