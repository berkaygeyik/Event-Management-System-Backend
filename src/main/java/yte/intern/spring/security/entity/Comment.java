package yte.intern.spring.security.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;

@Entity
@Getter
@Setter
@SequenceGenerator(name = "idgen", sequenceName = "COMMENT_SEQ")
public class Comment extends BaseEntity {

    @Column(name = "UNIQUE_COMMENT_KEY", unique = true)
    private String uniqueCommentKey;

    @Column(name = "COMMENT_TEXT")
    private String commentText;
}
