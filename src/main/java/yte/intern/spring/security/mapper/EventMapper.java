package yte.intern.spring.security.mapper;

import org.mapstruct.Mapper;
import yte.intern.spring.security.dto.EventDTO;
import yte.intern.spring.security.entity.Event;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EventMapper {

    EventDTO mapToDto(Event comment);

    Event mapToEntity(EventDTO commentDTO);

    List<EventDTO> mapToDto(List<Event> commentList);

    List<Event> mapToEntity(List<EventDTO> commentDTOList);
}
