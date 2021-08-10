package yte.intern.spring.security.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import yte.intern.spring.security.dto.CommentDTO;
import yte.intern.spring.security.entity.Comment;
import yte.intern.spring.security.mapper.CommentMapper;
import yte.intern.spring.security.service.CommentService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final CommentMapper commentMapper;

    @GetMapping("/user/comments")
    public List<CommentDTO> listAllComments() {
        List<Comment> comments = commentService.listAllComments();
        return commentMapper.mapToDto(comments);
    }

    @PostMapping("/admin/comments")
    public CommentDTO addComment(@Valid @RequestBody CommentDTO commentDTO) {
        Comment comment = commentMapper.mapToEntity(commentDTO);
        Comment addedComment = commentService.addComment(comment);

        return commentMapper.mapToDto(addedComment);
    }

    @GetMapping("/user/comments/{uniqueCommentKey}")
    public CommentDTO getCommentByUniqueCommentKey(@PathVariable String uniqueCommentKey) {
        Comment comment = commentService.getCommentByUniqueCommentKey(uniqueCommentKey);
        return commentMapper.mapToDto(comment);
    }

    @PutMapping("/admin/comments/{uniqueCommentKey}")
    public CommentDTO updateComment(@PathVariable String uniqueCommentKey, @Valid @RequestBody CommentDTO commentDTO) {
        Comment comment = commentService.updateStudent(uniqueCommentKey, commentMapper.mapToEntity(commentDTO));
        return commentMapper.mapToDto(comment);
    }

    @DeleteMapping("/admin/comments/{uniqueCommentKey}")
    public void deleteCommentByUniqueCommentKey(@PathVariable String uniqueCommentKey) {
        commentService.deleteCommentByUniqueCommentKey(uniqueCommentKey);
    }
}
