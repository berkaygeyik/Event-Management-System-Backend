package yte.intern.spring.security.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import yte.intern.spring.security.dto.*;
import yte.intern.spring.security.entity.EventPollQuestion;
import yte.intern.spring.security.entity.Question;
import yte.intern.spring.security.mapper.EventPollQuestionMapper;
import yte.intern.spring.security.mapper.QuestionMapper;
import yte.intern.spring.security.service.QuestionService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;
    private final QuestionMapper questionMapper;
    private final EventPollQuestionMapper eventPollQuestionMapper;

    @GetMapping(value = "/admin/eventQuestions/{eventName}")
    public List<QuestionDTO> getEventQuestions(@PathVariable String eventName) {
        List<Question> questions = questionService.getEventQuestions(eventName, "eventQuestions");
        return questionMapper.mapToDto(questions);
    }

    @GetMapping(value = "/admin/eventPollQuestions/{eventName}")
    public List<EventPollQuestionDTO> getEventPollQuestions(@PathVariable String eventName) {
        List<EventPollQuestion> eventPollQuestions = questionService.getEventPollQuestions(eventName);
        return eventPollQuestionMapper.mapToDto(eventPollQuestions);
    }

    @GetMapping(value = "/user/eventQuestionsUser/{username}/{eventName}")
    public List<QuestionDTO> getEventQuestionsUser(@PathVariable String username, @PathVariable String eventName) {
        List<Question> questions = questionService.getEventQuestionsUser(username, eventName);
        return questionMapper.mapToDto(questions);
    }

    @PostMapping("/user/{username}/eventQuestions/{eventName}")
    public MessageResponse addQuestionToEvent(@PathVariable String username, @PathVariable String eventName, @RequestBody QuestionDTO questionDTO){
        return questionService.addQuestionToEvent(username, eventName, questionMapper.mapToEntity(questionDTO));
    }

    @PostMapping("/user/{username}/eventPollQuestions/{eventName}")
    public MessageResponse addPollQuestionToEvent(@PathVariable String username, @PathVariable String eventName, @RequestBody EventPollQuestionDTO eventPollQuestionDTO){
        return questionService.addPollQuestionToEvent(username, eventName, eventPollQuestionMapper.mapToEntity(eventPollQuestionDTO));
    }

    @DeleteMapping(value = "/user/deleteQuestion/{questionID}")
    public MessageResponse deleteQuestion(@PathVariable String questionID) {
        return questionService.deleteQuestion(questionID);
    }

    @PostMapping("/admin/answerQuestion/{questionID}")
    public MessageResponse answerQuestion(@PathVariable String questionID, @RequestBody AnswerDTO answerDTO){
        return questionService.answerQuestion(questionID, answerDTO);
    }

    @PostMapping("/user/{username}/answerPollQuestions/{eventName}")
    public MessageResponse answerPollQuestions(@PathVariable String username, @PathVariable String eventName, @RequestBody PollAnswersDTO pollAnswersDTO){
        return questionService.answerPollQuestions(username, eventName, pollAnswersDTO);
    }

    @GetMapping("/user/eventPollAnswers/{eventName}")
    public List<PollAnswersDTO> eventPollAnswers(@PathVariable String eventName){
        return questionService.eventPollAnswers(eventName);
    }

    @GetMapping("/user/eventPollUsers/{eventName}")
    public PollAnswersDTO eventPollUsers(@PathVariable String eventName){
        return questionService.eventPollUsers(eventName);
    }
}
