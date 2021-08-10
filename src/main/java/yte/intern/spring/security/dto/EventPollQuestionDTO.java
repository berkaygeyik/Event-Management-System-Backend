package yte.intern.spring.security.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Getter
@Setter
public class EventPollQuestionDTO {

    private String uniqueId;

    @Column(name = "QUESTION_TEXT", length = 2048)
    @NotBlank(message = "Question Text field is required!")
    private String questionText;


    @NotBlank(message = "Option field is required!")
    private String option1;

    @NotBlank(message = "Option field is required!")
    private String option2;

    @NotBlank(message = "Option field is required!")
    private String option3;

    @NotBlank(message = "Option field is required!")
    private String option4;

    private LocalDateTime creationDate;
}
