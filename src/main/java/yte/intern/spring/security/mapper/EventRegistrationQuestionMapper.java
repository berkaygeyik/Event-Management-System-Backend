package yte.intern.spring.security.mapper;

import org.mapstruct.Mapper;
import yte.intern.spring.security.dto.EventRegistrationQuestionDTO;
import yte.intern.spring.security.entity.EventRegistrationQuestion;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EventRegistrationQuestionMapper {

    EventRegistrationQuestionDTO mapToDto(EventRegistrationQuestion question);

    EventRegistrationQuestion mapToEntity(EventRegistrationQuestionDTO questionDTO);

    List<EventRegistrationQuestionDTO> mapToDto(List<EventRegistrationQuestion> questionList);

    List<EventRegistrationQuestion> mapToEntity(List<EventRegistrationQuestionDTO> questionDTOList);
}
