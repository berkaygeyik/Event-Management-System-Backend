package yte.intern.spring.security.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
public class QuestionDTO {

    private String uniqueId;

    private LocalDateTime creationDate;

    private String username;

    private String questionText;

    private String answer;

}
