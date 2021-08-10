package yte.intern.spring.security.mapper;

import org.mapstruct.Mapper;
import yte.intern.spring.security.dto.QuestionDTO;
import yte.intern.spring.security.entity.Question;

import java.util.List;

@Mapper(componentModel = "spring")
public interface QuestionMapper {

    QuestionDTO mapToDto(Question question);

    Question mapToEntity(QuestionDTO questionDTO);

    List<QuestionDTO> mapToDto(List<Question> questionList);

    List<Question> mapToEntity(List<QuestionDTO> questionDTOList);
}
