package yte.intern.spring.security.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Getter
@Setter
public class EventRegistrationQuestionDTO {

    private String uniqueId;

    @Column(name = "QUESTION_TEXT", length = 2048)
    @NotBlank(message = "Question Text field is required!")
    private String questionText;

    private LocalDateTime creationDate;
}
