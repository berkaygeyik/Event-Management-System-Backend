package yte.intern.spring.security.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import yte.intern.spring.security.entity.Comment;
import yte.intern.spring.security.repository.CommentRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    public List<Comment> listAllComments() {
        return commentRepository.findAll();
    }

    public Comment addComment(Comment comment) {

        if(commentRepository.existsByUniqueCommentKey(comment.getUniqueCommentKey())){
            System.out.println("The comment with this UniqueCommentKey has been already created.");
            return null;
        }
        return commentRepository.save(comment);
    }


    public Comment getCommentByUniqueCommentKey(String uniqueCommentKey) {
        return commentRepository.findByUniqueCommentKey(uniqueCommentKey);
    }

    public Comment updateStudent(String uniqueCommentKey, Comment newComment) {
        Comment commentFromDB = commentRepository.findByUniqueCommentKey(uniqueCommentKey);
        newComment.setId(commentFromDB.getId());
        return commentRepository.save(newComment);
    }

    public void deleteCommentByUniqueCommentKey(String uniqueCommentKey) {
        commentRepository.deleteByUniqueCommentKey(uniqueCommentKey);
    }
}
