package yte.intern.spring.security.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import yte.intern.spring.security.dto.AnswerDTO;
import yte.intern.spring.security.dto.EventRegistrationQuestionDTO;
import yte.intern.spring.security.dto.MessageResponse;
import yte.intern.spring.security.dto.QuestionDTO;
import yte.intern.spring.security.entity.EventRegistrationQuestion;
import yte.intern.spring.security.mapper.EventRegistrationQuestionMapper;
import yte.intern.spring.security.service.QuestionService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class EventRegistrationQuestionController {

    private final QuestionService questionService;
    private final EventRegistrationQuestionMapper questionMapper;

    @GetMapping(value = "/user/eventRegistrationQuestions/{eventName}")
    public List<EventRegistrationQuestionDTO> getEventRegistrationQuestions(@PathVariable String eventName) {
        List<EventRegistrationQuestion> questions = questionService.getEventRegistrationQuestions(eventName);
        return questionMapper.mapToDto(questions);
    }

    @PostMapping("/admin/eventRegistrationQuestions/{eventName}")
    public MessageResponse addEventRegistrationQuestions(@PathVariable String eventName, @RequestBody EventRegistrationQuestionDTO eventRegistrationQuestionDTO){
        return questionService.addEventRegistrationQuestions(eventName, questionMapper.mapToEntity(eventRegistrationQuestionDTO));
    }

    @PostMapping("/user/answerQuestion/{questionID}")
    public MessageResponse answerQuestion(@PathVariable String questionID, @RequestBody AnswerDTO answerDTO){
        return questionService.answerQuestion(questionID, answerDTO);
    }
}
