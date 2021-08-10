package yte.intern.spring.security.mapper;

import org.mapstruct.Mapper;
import yte.intern.spring.security.dto.CommentDTO;
import yte.intern.spring.security.entity.Comment;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    CommentDTO mapToDto(Comment comment);

    Comment mapToEntity(CommentDTO commentDTO);

    List<CommentDTO> mapToDto(List<Comment> commentList);

    List<Comment> mapToEntity(List<CommentDTO> commentDTOList);

}
