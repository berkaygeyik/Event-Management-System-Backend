package yte.intern.spring.security.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Size;


@Getter
@Setter
public class CommentDTO {

    @Size(max = 10, message = "Key can't be longer than 10 character")
    private String uniqueCommentKey;

    @Size(max = 255, message = "Comment can't be longer than 255 character")
    private String commentText;

}
