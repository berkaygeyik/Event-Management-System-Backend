package yte.intern.spring.security.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.stereotype.Service;
import yte.intern.spring.security.dto.AnswerDTO;
import yte.intern.spring.security.dto.MessageResponse;
import yte.intern.spring.security.dto.PollAnswersDTO;
import yte.intern.spring.security.entity.*;
import yte.intern.spring.security.repository.*;

import java.util.*;

import static yte.intern.spring.security.dto.MessageType.SUCCESS;
import static yte.intern.spring.security.dto.MessageType.ERROR;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final EventPollQuestionRepository eventPollQuestionRepository;
    private final EventRegistrationQuestionRepository eventRegistrationQuestionRepository;
    private final UsersRepository usersRepository;
    private final EventRepository eventRepository;

    public List<Question> getEventQuestions(String eventName, String type) {

        if(!eventRepository.existsByName(eventName)){
            System.out.println("There is no event with this name. Chose a different name.");
            return null;
        }

        Event event = eventRepository.findByName(eventName);
        ArrayList<Question> eventQuestions = new ArrayList<>();
        System.out.println(event.getQuestions().size());
        System.out.println(event.getRegistrationQuestions().size());
        if(type.equals("eventQuestions")){
            eventQuestions = new ArrayList<>(event.getQuestions());
            eventQuestions.sort(new CustomComparator());
        }

        return eventQuestions;
    }

    public List<EventRegistrationQuestion> getEventRegistrationQuestions(String eventName) {

        if(!eventRepository.existsByName(eventName)){
            System.out.println("There is no event with this name. Chose a different name.");
            return null;
        }

        Event event = eventRepository.findByName(eventName);

        ArrayList<EventRegistrationQuestion> eventQuestions = new ArrayList<>(event.getRegistrationQuestions());
        eventQuestions.sort(new CustomComparatorRegistration());

        return eventQuestions;
    }


    public List<EventPollQuestion> getEventPollQuestions(String eventName) {

        if(!eventRepository.existsByName(eventName)){
            System.out.println("There is no event with this name. Chose a different name.");
            return null;
        }

        Event event = eventRepository.findByName(eventName);

        ArrayList<EventPollQuestion> eventPollQuestions = new ArrayList<>(event.getEventPollQuestions());
        eventPollQuestions.sort(new CustomComparatorPoll());

        return eventPollQuestions;
    }

    public List<Question> getEventQuestionsUser(String username, String eventName) {

        if(!usersRepository.existsByUsername(username)){
            System.out.println("There is no user with this name. Chose a different name.");
            return null;
        }

        if(!eventRepository.existsByName(eventName)){
            System.out.println("There is no event with this name. Chose a different name.");
            return null;
        }
        ArrayList<Question> userQuestions = new ArrayList<>();

        Event event = eventRepository.findByName(eventName);
        Set<Question> questionSet = event.getQuestions();

        for(Question question: questionSet ){
            if (question.getUsername().equals(username)){
                userQuestions.add(question);
            }
        }
        userQuestions.sort(new CustomComparator());
        return userQuestions;
    }


    public MessageResponse addQuestionToEvent(String username, String eventName, Question question) {

        if(!usersRepository.existsByUsername(username)){
            return new MessageResponse("There is no user with this name. Chose a different name.", ERROR);
        }

        if(!eventRepository.existsByName(eventName)){
            return new MessageResponse("There is no event with this name. Chose a different name.", ERROR);
        }

        Users user = usersRepository.findByUsername(username);
        Event event = eventRepository.findByName(eventName);

        if(!event.getParticipants().contains(user)){
            return new MessageResponse("You must register for this event to ask questions.", ERROR);
        }

        question.setUniqueId(UUID.randomUUID().toString());
        question.setUsers(user);
        question.setEvent(event);
        question.setUsername(username);

        Question savedQuestion = questionRepository.save(question);

        user.getQuestions().add(savedQuestion);
        event.getQuestions().add(savedQuestion);

        usersRepository.save(user);
        eventRepository.save(event);

        /*Set<Users> participantSet = event.getParticipants();
        Set<Event> eventSet = user.getEnrolledEvents();*/
        return new MessageResponse("Question added.", SUCCESS);
    }

    public MessageResponse addEventRegistrationQuestions(String eventName, EventRegistrationQuestion question) {

        if(!eventRepository.existsByName(eventName)){
            return new MessageResponse("There is no event with this name. Chose a different name.", ERROR);
        }

        Event event = eventRepository.findByName(eventName);

        question.setUniqueId(UUID.randomUUID().toString());
        question.setEvent(event);

        EventRegistrationQuestion savedQuestion = eventRegistrationQuestionRepository.save(question);
        event.getRegistrationQuestions().add(savedQuestion);
        eventRepository.save(event);

        /*Set<Users> participantSet = event.getParticipants();
        Set<Event> eventSet = user.getEnrolledEvents();*/
        return new MessageResponse("Question added.", SUCCESS);
    }

    public MessageResponse addPollQuestionToEvent(String username, String eventName, EventPollQuestion eventPollQuestion) {

        if(!usersRepository.existsByUsername(username)){
            return new MessageResponse("There is no user with this name. Chose a different name.", ERROR);
        }

        if(!eventRepository.existsByName(eventName)){
            return new MessageResponse("There is no event with this name. Chose a different name.", ERROR);
        }

        Event event = eventRepository.findByName(eventName);


        eventPollQuestion.setUniqueId(UUID.randomUUID().toString());
        eventPollQuestion.setEvent(event);

        EventPollQuestion savedQuestion = eventPollQuestionRepository.save(eventPollQuestion);

        event.getEventPollQuestions().add(savedQuestion);

        eventRepository.save(event);

        return new MessageResponse("Question added.", SUCCESS);
    }

    public MessageResponse deleteQuestion(String questionID) {
        if(!questionRepository.existsByUniqueId(questionID)){
            return new  MessageResponse("There is no question with this ID. Chose a different Question ID.", ERROR);
        }

        questionRepository.deleteByUniqueId(questionID);

        return new MessageResponse("The question has been successfully deleted.", SUCCESS);
    }


    public MessageResponse answerQuestion(String questionID, AnswerDTO answerDTO) {
        if(!questionRepository.existsByUniqueId(questionID)){
            return new  MessageResponse("There is no question with this ID. Chose a different Question ID.", ERROR);
        }
        Question question = questionRepository.findByUniqueId(questionID);
        question.setAnswer(answerDTO.getAnswer());
        questionRepository.save(question);

        return new MessageResponse("Question answered.", SUCCESS);
    }

    public MessageResponse answerPollQuestions(String username, String eventName, PollAnswersDTO pollAnswersDTO) {
        if(!usersRepository.existsByUsername(username)){
            return new  MessageResponse("There is no user with this Name. Chose a different name.", ERROR);
        }
        if(!eventRepository.existsByName(eventName)){
            return new  MessageResponse("There is no event with this name. Chose a different name.", ERROR);
        }

        Users user = usersRepository.findByUsername(username);
        Event event = eventRepository.findByName(eventName);

        List<EventPollAnswer> eventPollAnswersUser = user.getEventPollAnswers();

        ArrayList<EventPollQuestion> eventPollQuestions = new ArrayList<>(event.getEventPollQuestions());
        eventPollQuestions.sort(new CustomComparatorPoll());

        int index = 0;
        for(EventPollQuestion eventPollQuestion : eventPollQuestions){
            EventPollAnswer eventPollAnswer = new EventPollAnswer();

            eventPollAnswer.setAnswer(pollAnswersDTO.getAnswerList().get(index));
            eventPollAnswer.setQuestion(eventPollQuestion);
            eventPollAnswer.setUser(user);

            eventPollQuestion.getEventPollAnswers().add(eventPollAnswer);
            eventPollAnswersUser.add(eventPollAnswer);

            eventPollQuestionRepository.save(eventPollQuestion);
            index++;
        }

        usersRepository.save(user);
        eventRepository.save(event);

        return new MessageResponse("Poll questions answered.", SUCCESS);
    }

    public List<PollAnswersDTO> eventPollAnswers(String eventName) {
        if(!eventRepository.existsByName(eventName)){
            System.out.println("There is no event with this name. Chose a different name.");
            return null;
        }
        Event event = eventRepository.findByName(eventName);
        ArrayList<EventPollQuestion> eventPollQuestions = new ArrayList<>(event.getEventPollQuestions());
        eventPollQuestions.sort(new CustomComparatorPoll());

        ArrayList<PollAnswersDTO> pollAnswersDTOS = new ArrayList<>();

        for(EventPollQuestion eventPollQuestion: eventPollQuestions){
            PollAnswersDTO pollAnswersDTO = new PollAnswersDTO();
            pollAnswersDTO.setAnswerList(new ArrayList<>());
            pollAnswersDTO.setUserList(new ArrayList<>());
            pollAnswersDTO.setAnswerOptions(new ArrayList<>(Collections.nCopies(4, 0)));
            for(EventPollAnswer eventPollAnswer: eventPollQuestion.getEventPollAnswers()){
                pollAnswersDTO.getAnswerList().add(eventPollAnswer.getAnswer());
                pollAnswersDTO.getUserList().add(eventPollAnswer.getUser().getUsername());
                ArrayList<Integer> optionCounts = (ArrayList<Integer>) pollAnswersDTO.getAnswerOptions();
                if(eventPollQuestion.getOption1().equals(eventPollAnswer.getAnswer())){
                    int count = optionCounts.get(0);
                    optionCounts.set(0, count+1);
                }
                if(eventPollQuestion.getOption2().equals(eventPollAnswer.getAnswer())){
                    int count = optionCounts.get(1);
                    optionCounts.set(1, count+1);
                }
                if(eventPollQuestion.getOption3().equals(eventPollAnswer.getAnswer())){
                    int count = optionCounts.get(2);
                    optionCounts.set(2, count+1);
                }
                if(eventPollQuestion.getOption4().equals(eventPollAnswer.getAnswer())){
                    int count = optionCounts.get(3);
                    optionCounts.set(3, count+1);
                }

            }
            pollAnswersDTOS.add(pollAnswersDTO);
        }
        return pollAnswersDTOS;
    }

    public PollAnswersDTO eventPollUsers(String eventName) {
        if(!eventRepository.existsByName(eventName)){
            System.out.println("There is no event with this name. Chose a different name.");
            return null;
        }
        Event event = eventRepository.findByName(eventName);
        ArrayList<EventPollQuestion> eventPollQuestions = new ArrayList<>(event.getEventPollQuestions());
        eventPollQuestions.sort(new CustomComparatorPoll());

        ArrayList<PollAnswersDTO> pollAnswersUsers = new ArrayList<>();

        PollAnswersDTO pollAnswersDTO = new PollAnswersDTO();
        pollAnswersDTO.setUserList(new ArrayList<>());
        if(eventPollQuestions.size()>0){
            for(EventPollAnswer eventPollAnswer: eventPollQuestions.get(0).getEventPollAnswers()){
                pollAnswersDTO.getUserList().add(eventPollAnswer.getUser().getUsername());
            }
        }

        return pollAnswersDTO;
    }


    static class CustomComparator implements Comparator<Question> {

        @Override
        public int compare(Question question1, Question question2) {
            return question2.getCreationDate().compareTo(question1.getCreationDate());
        }
    }

    static class CustomComparatorRegistration implements Comparator<EventRegistrationQuestion> {

        @Override
        public int compare(EventRegistrationQuestion question1, EventRegistrationQuestion question2) {
            return question1.getCreationDate().compareTo(question2.getCreationDate());
        }
    }

    static class CustomComparatorPoll implements Comparator<EventPollQuestion> {

        @Override
        public int compare(EventPollQuestion question1, EventPollQuestion question2) {
            return question1.getCreationDate().compareTo(question2.getCreationDate());
        }
    }
}

