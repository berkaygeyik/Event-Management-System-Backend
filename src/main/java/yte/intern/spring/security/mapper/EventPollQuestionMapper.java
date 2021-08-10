package yte.intern.spring.security.mapper;

import org.mapstruct.Mapper;
import yte.intern.spring.security.dto.EventPollQuestionDTO;
import yte.intern.spring.security.entity.EventPollQuestion;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EventPollQuestionMapper {

    EventPollQuestionDTO mapToDto(EventPollQuestion question);

    EventPollQuestion mapToEntity(EventPollQuestionDTO questionDTO);

    List<EventPollQuestionDTO> mapToDto(List<EventPollQuestion> questionList);

    List<EventPollQuestion> mapToEntity(List<EventPollQuestionDTO> questionDTOList);
}
