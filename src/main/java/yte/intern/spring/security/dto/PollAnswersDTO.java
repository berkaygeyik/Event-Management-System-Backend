package yte.intern.spring.security.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PollAnswersDTO {
    private List<String> answerList;
    private List<Integer> answerOptions;
    private List<String> userList;
}
